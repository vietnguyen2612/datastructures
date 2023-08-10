import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class WeightedGraph {
    private Vertex[] vertices = null; //array of vertices
    private int numVertices = 0; //current number of vertices on graph
    private int maxVertices = 0; //max number of vertices

    //constructor
    public WeightedGraph(int maxVert) {
        vertices = new Vertex[maxVert];
        maxVertices = maxVert;
    }

    // A class which represent a vertex of the graph
    private class Vertex {
        private String name = null;
        private Hashtable<String, Edge> edges = new Hashtable<String, Edge>(); //hash table in which string is the key and edges are the values

        //constructor
        protected Vertex(String n) {
            name = n;
        }
    }

    //class for edge
    private class Edge {
        private int tInd = -1; //index of tail vertex
        private int cost = 0; //cost associated with edge

        protected Edge(int toIndex, int amt) {
            tInd = toIndex;
            cost = amt;
        }
    }

    //adds an edge from node from to node to with weight
    public boolean addWeightedEdge(String from, String to, int weight) {
        if(from.compareTo(to) == 0) {
            return false;
        }

        int sInd = -1;
        int tInd = -1;

        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null && vertices[i].name.compareTo(from) == 0) {
                sInd = i;
            }

            if(vertices[i] != null && vertices[i].name.compareTo(to) == 0) {
                tInd = i;
            }
        }

        if(sInd != -1 && tInd != - 1) {
            if(vertices[sInd].edges.get(to) != null) {
                return false;
            }

            Edge edge = new Edge(tInd, weight); //tail edge with tail index and its weight
            vertices[sInd].edges.put(to, edge);
            return true;
        }
        return false;
    }

    //adds directed edges from node from to each node in tolist and weight list
    public boolean addWeightedEdges(String from, String[] tolist, int[] weightlist) {
        if(tolist.length == 0 || weightlist.length == 0) {
            return false;
        }

        if(tolist.length != weightlist.length) {
            return false;
        }

        int wrong = 0; //counters all all nodes that cannot be added
        for(int i = 0; i < tolist.length; i++) {
            // A flag to check if the operation is carried out successfully
            boolean err = this.addWeightedEdge(from, tolist[i], weightlist[i]);
            if(err == false) {
                wrong++;
            }
        }

        if(wrong > 0) {
            return false;
        }

        return true;
    }

    //adds a node to the graph and checks for duplicates
    //returns true if node is added
    //returns false if node is not added
    public boolean addNode(String name) {
        if(numVertices == maxVertices) {
            maxVertices *= 2;
            Vertex[] temp = new Vertex[maxVertices];
            for(int i = 0; i < vertices.length; i++) {
                temp[i] = vertices[i];
            }
            vertices = temp;
        }

        for(int i = 0; i < vertices.length; i++) { //iterates through list and returns false of any name cannot be found
            if(vertices[i] != null && vertices[i].name.compareTo(name) == 0) {
                return false;
            }
        }
        vertices[numVertices] = new Vertex(name);
        numVertices++;
        return true;
    }

    //adds a node to the graph and checks for duplicates
    //returns true if node is added
    //returns false if node is not added
    public boolean addNodes(String[] names) {
        if(names.length == 0) {
            return false;
        }


        int wrong = 0; //counter for unadded nodes
        for(int i = 0; i < names.length; i++) {
            // A flag to check if the operation is carried out successfully
            boolean err = this.addNode(names[i]);
            if(err == false) {
                wrong++;
            }
        }
        if(wrong > 0) {
            return false;
        }
        return true;
    }

    //removes a node from the graph along with all connected edges
    public boolean removeNode(String name) {
        int indRem = -1; //index of removed node
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null && vertices[i].name.compareTo(name) == 0) {
                indRem = i;
            }
        }

        if(indRem != -1) {
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null) {
                    vertices[i].edges.remove(name);
                }
            }

            for(int i = indRem; i < vertices.length - 1; i++) {
                vertices[i] = vertices[i + 1];
            }

            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null) {
                    Set<String> setOfNames = vertices[i].edges.keySet();
                    for(String eachName : setOfNames) {
                        if(vertices[i].edges.get(eachName).tInd > indRem) {
                            Edge newEdge = new Edge(vertices[i].edges.get(eachName).tInd - 1, vertices[i].edges.get(eachName).cost);
                            vertices[i].edges.replace(eachName, newEdge);
                        }
                    }
                }
            }
            numVertices--;
            return true;
        }
        return false;
    }

    //removes a node from the graph along with all connected edges
    public boolean removeNodes(String[] nodelist) {
        if(nodelist.length == 0) {
            return false;
        }

        int wrong = 0; //wrong counter for unsuccessfully added nodes
        for(int i = 0; i < nodelist.length; i++) {
            boolean err = this.removeNode(nodelist[i]);
            if(err == false) {
                wrong++;
            }
        }

        if(wrong > 0) {
            return false;
        }
        return true;
    }

    //prints the graph in the same adjacency list format in the read method described below
    //nodes and their neighbors and their neighbors should be listed in alphabetical order
    //array is added with each name and their associated weights as below
    // <name> <weight> <neighbor1> <weight> <neighbor2>
    public static WeightedGraph readWeighted(String filename) throws FileNotFoundException {
        // A file instance initialized to receive the file input
        File fr = new File(filename);
        // An empty weighted graph instance to be returned
        WeightedGraph graph = new WeightedGraph(100);
        try {
            // A scanner to read the input bytes from the file
            Scanner scanner = new Scanner(fr, "UTF-8");

            while (scanner.hasNextLine()) {
                //single line from the input array
                String line = scanner.nextLine().replaceAll("\\p{Punct}", "").replaceAll("\\s+", " ").trim();
                //individual word from the line
                String word = "";
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == ' ') {
                        break;
                    }

                    if(Character.isLetter(line.charAt(i))) {
                        word += line.charAt(i);
                    }
                }
                if(word.compareTo("") != 0) {
                    graph.addNode(word);
                }
            }
            scanner.close();
            scanner = new Scanner(fr, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().replaceAll("\\p{Punct}", "").replaceAll("\\s+", " ").trim();
                String word = "";
                int startEIndex = 0; //starting index for an edge
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == ' ') {
                        startEIndex++;
                        break;
                    }

                    if(Character.isLetter(line.charAt(i))) {
                        word += line.charAt(i);
                    }
                    startEIndex++;
                }

                String edge = "";
                int weight = -1;
                String temp = "";

                for(int i = startEIndex; i < line.length(); i++) {
                    if((line.charAt(i) == ' ' && line.charAt(i - 1) == ' ')) {
                        continue;
                    }

                    if(Character.isDigit(line.charAt(i))){
                        temp += Character.getNumericValue(line.charAt(i));
                    }

                    weight = Integer.parseInt(temp);

                    if(line.charAt(i) == ' ' && !Character.isDigit(line.charAt(i - 1))) {
                        graph.addWeightedEdge(word, edge, weight);
                        temp = "";
                        weight = 0;
                        edge = "";
                        continue;
                    }

                    if(Character.isLetter(line.charAt(i))) { //if first character is a letter, add as an edge
                        edge += line.charAt(i);
                    }
                }

                if(edge.compareTo("") != 0) {
                    graph.addWeightedEdge(word, edge, weight);
                    temp = "";
                    weight = 0;
                }
            }
            scanner.close();
        }catch(Exception e) {
            throw new FileNotFoundException("The input file cannot be read");
        }
        return graph;
    }

    //prints vertices and edges in alphabetical order
    public void printWeightedGraph() {
        ArrayList<Vertex> listVertices = new ArrayList<Vertex>(); //stores list of vertices of the graph
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null) {
                listVertices.add(vertices[i]);
            }
        }

        Collections.sort(listVertices, new Comparator<Vertex>() { //overrides compare method to compare te name of two vertices
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.name.compareTo(o2.name);
            }
        });

        for(int i = 0; i < listVertices.size(); i++) {
            System.out.print(listVertices.get(i).name);
            if(i != listVertices.size() && listVertices.get(i).edges.size() != 0) {
                System.out.print(" ");
            }

            LinkedList<String> list = new LinkedList<String>(listVertices.get(i).edges.keySet()); //stores list of names from edges of the graph
            Collections.sort(list);

            while(!list.isEmpty()) {
                String neighbor = list.remove();
                System.out.print(listVertices.get(i).edges.get(neighbor).cost);
                System.out.print(" ");
                System.out.print(neighbor);

                if(list.size() != 0) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    //method that finds the shortest path from --> vertex, returns empty string if path not found
    public String[] shortestPath(String from, String to) {
        if(from.compareTo(to) == 0) {
            return new String[] {from};
        }

        final int NO_PARENT = -1; //vertex traverses through graph until there are no parents
        ArrayList<Integer> path = new ArrayList<Integer>(); //array of integers that stores the cost of each edge encountered

        int[] encountered = new int[numVertices]; //array stores the encountered
        boolean[] parentEnc = new boolean[numVertices]; //array of parents that have been encountered
        int[] parents = new int[numVertices];

        for(int i = 0; i < numVertices; i++) {
            encountered[i] = Integer.MAX_VALUE;
            parentEnc[i] = false;
        }

        int sInd = -1; //start index
        int tInd = -1; //tail index
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null && vertices[i].name.compareTo(from) == 0) {
                sInd = i;
            }

            if(vertices[i] != null && vertices[i].name.compareTo(to) == 0) {
                tInd = i;
            }
        }

        List<ArrayList<String>> paths = allPathsBFS(from, to);
        if(paths.size() == 0) {
            return new String[] {};
        }

        if(sInd == -1 || tInd == -1) {
            return new String[] {};
        }else if(vertices[sInd].edges.size() == 0) {
            return new String[] {};
        }

        boolean iso = true; //if current vertex has no edge that connects 'to'
        for (int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null && vertices[i].edges.get(to) != null) {
                iso = false;
            }
        }

        if(iso == true && vertices[tInd].edges.size() == 0) { //if node is isolated and edge cost for tail index is 0
            return new String[] {}; //return empty string
        }

        encountered[sInd] = 0;
        parents[sInd] = NO_PARENT;

        for(int i = 1; i < numVertices; i++) {
            int pre = -1;
            int min = Integer.MAX_VALUE;
            for(int v = 0; v < numVertices; v++) {
                if(!parentEnc[v] && encountered[v] < min) {
                    pre = v;
                    min = encountered[v];
                }
            }
            if(pre == -1) {
                continue;
            }

            parentEnc[pre] = true;
            int dist = -1; //stores distance between vertex and neighbors

            ArrayList<String> keys = new ArrayList<String>(vertices[pre].edges.keySet());
            ArrayList<Edge> possibleIndexPaths = new ArrayList<Edge>(vertices[pre].edges.values());

            int conInd = 0; //stores vertex to be considered adding into path
            for(int v = 0; v < possibleIndexPaths.size(); v++) {
                int index = possibleIndexPaths.get(v).tInd; //stores neighbor vertex to be considered adding into path
                dist = vertices[pre].edges.get(keys.get(conInd)).cost;
                if(dist > 0 && (min + dist < encountered[index])) {
                    parents[index] = pre;
                    encountered[index] = min + dist;
                }
                conInd++;
            }
            conInd = 0;
        }

        addPath(tInd, parents, path); //adds the path using the tail index, parent, and the path created above

        String[] result = new String[path.size()]; //array holding the resulting path
        int indexResult = 0; //index of resulting path out of all the paths
        for(int i = 0; i < result.length; i++) {
            result[indexResult] = vertices[path.get(i)].name;
            indexResult++;
        }
        return result;
    }

    //helper method that adds path to all indices of parents to the path
    private static void addPath(int i, int[] parents, ArrayList<Integer> path) {
        if(i == -1) {
            return;
        }
        addPath(parents[i], parents, path); //called recursively to add all paths properly
        path.add(i); //adds path
    }

    //helper method that returns arraylist that holds all the paths of BFS traversal
    private List<ArrayList<String>> allPathsBFS(String from, String to){
        int sInd = -1; //start index
        List<ArrayList<String>> allPaths = new ArrayList<>(); //list of all possible paths
        LinkedList<List<String>> queue = new LinkedList<>(); //queue of all the paths to be considered

        ArrayList<String> firstPath = new ArrayList<String>(); //stores the first path found
        firstPath.add(from);
        queue.add(firstPath);
        // The current path that is being considered
        while(!queue.isEmpty()) {
            List<String> path = queue.removeFirst();
            String lastVert = path.get(path.size() - 1); //last vertex found in path
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null && vertices[i].name.compareTo(lastVert) == 0) { //if current vertex is same as last vertex, increment start index
                    sInd = i;
                }
            }

            if(lastVert.compareTo(to) == 0) {
                ArrayList<String> newPath = new ArrayList<>(path); //stores possible path from --> to
                allPaths.add(newPath);
            }else {
                ArrayList<String> listNeighbors = new ArrayList<>(vertices[sInd].edges.keySet()); //stores list of neighbors considered to be added to path
                for(int i = 0; i < listNeighbors.size(); i++) {
                    if(isVisited(listNeighbors.get(i), path)) {
                        List<String> list = new ArrayList<String>(path);
                        list.add(listNeighbors.get(i));
                        queue.add(list);
                    }
                }
            }
        }
        return allPaths;
    }

    //helper method to check if a node has been visited or not
    private static boolean isVisited(String considered, List<String> path) {
        for(int i = 0; i < path.size(); i++) {
            if(path.get(i).compareTo(considered) == 0) {
                return false;
            }
        }
        return true;
    }

    //method to find the second-shortest path from --> to vertex
    public String[] secondShortestPath(String from, String to) {
        int sInd = -1; //start index
        int tInd = -1; //tail index

        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null && vertices[i].name.compareTo(from) == 0) { //sInd becomes current index if from is at that index
                sInd = i;
            }

            if(vertices[i] != null && vertices[i].name.compareTo(to) == 0) { //tInd becomes current index if to is at that index
                tInd = i;
            }
        }

        if(sInd == -1 || tInd == -1) {
            return new String[] {};
        }

        int temp = sInd;
        List<ArrayList<String>> allPaths = allPathsBFS(from, to); //list stores all paths from --> to
        List<Integer> allPathsCost = new ArrayList<Integer>(); //list stores all costs from --> to
        for(int i = 0; i < allPaths.size(); i++) {
            int pathCost = 0;
            for(int j = 1; j < allPaths.get(i).size(); j++) {
                ArrayList<String> keyList = new ArrayList<String>(vertices[sInd].edges.keySet());
                ArrayList<Edge> valList = new ArrayList<Edge>(vertices[sInd].edges.values());
                for(int k = 0; k < keyList.size(); k++) {
                    if(keyList.get(k).compareTo(allPaths.get(i).get(j)) == 0) {
                        pathCost += valList.get(k).cost;
                        sInd = vertices[sInd].edges.get(keyList.get(k)).tInd;
                        break;
                    }
                }
            }
            allPathsCost.add(pathCost);
            pathCost = 0; //cost of the path
            sInd = temp;
        }

        int minDistance = Integer.MAX_VALUE;
        ArrayList<ArrayList<String>> tempAllLists = new ArrayList<ArrayList<String>>();
        ArrayList<Integer> tempAllCosts = new ArrayList<Integer>();
        for(int i = 0; i < allPathsCost.size(); i++) {
            if(allPathsCost.get(i) < minDistance) {
                minDistance = allPathsCost.get(i);
            }
        }

        for(int i = 0; i < allPathsCost.size(); i++) {
            if(allPathsCost.get(i) != minDistance) {
                tempAllLists.add(allPaths.get(i));
                tempAllCosts.add(allPathsCost.get(i));
            }
        }

        if(tempAllLists.size() == 0) {
            return new String[] {};
        }

        int secondMin = Integer.MAX_VALUE;
        for(int i = 0; i < tempAllCosts.size(); i++) {
            if(tempAllCosts.get(i) < secondMin) {
                secondMin = tempAllCosts.get(i);
            }
        }

        int indexSecondMin = -1;
        for(int i = 0; i < allPathsCost.size(); i++) {
            if(allPathsCost.get(i) == secondMin) {
                indexSecondMin = i;
            }
        }

        String[] result = new String[allPaths.get(indexSecondMin).size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = allPaths.get(indexSecondMin).get(i);
        }

        return result;
    }



    public static void main(String[] args) throws FileNotFoundException {
        WeightedGraph weightedGraph = new WeightedGraph(100);
        System.out.println("ADD A B C USING ADDNODE METHOD: ");
        weightedGraph.addNode("A");
        weightedGraph.addNode("B");
        weightedGraph.addNode("C");
        System.out.println("ADDEDGE A-B with weight 20: ");
        System.out.println("Success of ADDNODE A-B: " + weightedGraph.addWeightedEdge("A", "B", 2));
        System.out.println("ADDEDGE A-C with weight 10: ");
        System.out.println("SUCCESS of ADDNODE A-C: " + weightedGraph.addWeightedEdge("A", "C", 1));
        weightedGraph.printWeightedGraph();

        System.out.println("ADDNODES C,E,G,F: ");
        String[] listNodes = new String[] {"C", "E", "G", "F"};
        System.out.println("Success of ADDNODES (wrong): "  + weightedGraph.addNodes(listNodes));

        String[] edgesD = new String[] {"C", "E", "G", "F"};
        int[] weightListD = {20, 20, 40, 80};
        System.out.println("Success of ADDWEIGHTEDEDGES C(2), E(20), G(40), F(80) (wrong): " + weightedGraph.addWeightedEdges("D", edgesD, weightListD));
        String[] edgesB = new String[] {"D", "E"};
        int[] weightListB = {3, 10};
        System.out.println("SUCCESS of: ADDWEIGHTEDEDGES D(30), E(100) (wrong): " + weightedGraph.addWeightedEdges("B", edgesB, weightListB));
        String[] edgesC = new String[] {"A", "F"};
        int[] weightListC = {4, 5};
        System.out.println("Success of ADDEDWEIGHTEDEDGES A(40), F(50): " + weightedGraph.addWeightedEdges("C", edgesC, weightListC));
        String[] edgesE = new String[] {"E"};
        int[] weightListE = {60};
        System.out.println("Success of ADDEDWEIGHTEDEDGES E(60) (wrong): " + weightedGraph.addWeightedEdges("E", edgesE, weightListE));
        String[] edgesG = new String[] {"G"};
        int[] weightListG = {10};
        System.out.println("Result when all operations are successful: " + weightedGraph.addWeightedEdges("G", edgesG, weightListG));
        System.out.println("PRINTWEIGHTED GRAPH: ");
        weightedGraph.printWeightedGraph();

        System.out.println("ADDWEIGHTEDEDGE that already exists C-A(1): ");
        System.out.println("Success (wrong): " + weightedGraph.addWeightedEdge("C", "A", 1));

        System.out.println("READWEIGHTED graph of test2.txt and PRINTED: ");
        WeightedGraph graphTest = WeightedGraph.readWeighted("test2.txt");
        graphTest.printWeightedGraph();

        System.out.println("SHORTESTPATH from A to B: " + Arrays.toString(graphTest.shortestPath("A", "B")));
        System.out.println("SHORTESTPATH from C to E: " + Arrays.toString(graphTest.shortestPath("C", "E")));
        System.out.println("SHORTESTPATH from C to Z (empty): " + Arrays.toString(graphTest.shortestPath("C", "L")));
        System.out.println("SHORTESTPATH from B to B: " + Arrays.toString(graphTest.shortestPath("B", "B")));

        System.out.println("2ndSHORTESTPATH from A to E: " + Arrays.toString(graphTest.secondShortestPath("A", "E")));
        System.out.println("2ndSHORTESTPATH from C to B (single path): " + Arrays.toString(graphTest.secondShortestPath("C", "B")));
        System.out.println("2ndSHORTESTPATH from C to Z (empty): " + Arrays.toString(graphTest.secondShortestPath("C", "W")));
        System.out.println("2ndSHORTESTPATH from C to D: " + Arrays.toString(graphTest.secondShortestPath("C", "D")));

        System.out.println("REMOVENODE E from graph: " + graphTest.removeNode("C"));
        String[] nodesToRemove = new String[] {"B", "G", "A"};
        System.out.println("REMOVENODES A,G,B from graph " + graphTest.removeNodes(nodesToRemove));
        System.out.println("Result: ");
        graphTest.printWeightedGraph();


        System.out.println();
        System.out.println();
        System.out.println("EXTRA CREDIT: THAILAND FLIGHTS TO AROUND THE WORLD");
        WeightedGraph extCred = new WeightedGraph(100);
        System.out.println("Countries: UK, US, Spain, Vietnam, Australia, Indonesia, Singapore, Kenya, Thailand, Canada ");
        String[] nodes = new String[] {"UK", "US", "Spain", "Vietnam", "Australia", "Indonesia", "Singapore", "Kenya", "Thailand", "Canada"};
        extCred.addNodes(nodes);
        System.out.println("Flights from each city: ");
        String[] edgesThailand = new String[] {"UK", "US", "Vietnam"};
        int[] weightFromThailand = new int[] {9209, 13432, 1612};
        extCred.addWeightedEdges("Thailand", edgesThailand, weightFromThailand);
        String[] edgesSpain = new String[] {"Singapore", "Kenya"};
        int[] weightFromSpain = new int[] {7401, 8627};
        extCred.addWeightedEdges("Spain", edgesSpain, weightFromSpain);
        String[] edgesSingapore = new String[] {"Vietnam", "Kenya"};
        int[] weightFromSingapore = new int[] {1134, 6682};
        extCred.addWeightedEdges("Singapore", edgesSingapore, weightFromSingapore);
        extCred.addWeightedEdge("Indonesia", "Vietnam", 6706);
        String[] edgesAustralia = new String[] {"Indonesia", "Thailand"};
        int[] weightFromAustralia = new int[] {754, 7689};
        extCred.addWeightedEdges("Australia", edgesAustralia, weightFromAustralia);
        extCred.addWeightedEdge("UK", "Spain", 2207);
        String[] edgesUS = new String[] {"UK", "Canada"};
        int[] weightFromUS = new int[] {6010, 485};
        extCred.addWeightedEdges("US", edgesUS, weightFromUS);
        extCred.addWeightedEdge("Vietnam", "Spain", 11123);

        System.out.println("BEST FLIGHTS DEPARTING Thailand: ");
        extCred.printWeightedGraph();
        System.out.println("SHORTEST flight from Thailand to Spain: " + Arrays.toString(extCred.shortestPath("Thailand", "Spain")));
        System.out.println("SHORTEST flight from Thailand to Kenya: " + Arrays.toString(extCred.shortestPath("Thailand", "Kenya")));
        System.out.println("SHORTEST flight from Kenya to Thailand (no direct flight to Thailand): " + Arrays.toString(extCred.shortestPath("Kenya", "Thailand")));
        System.out.println("SHORTEST flight from Singapore to Thailand (no direct flight to Thailand): " + Arrays.toString(extCred.shortestPath("Singapore", "Thailand")));
        System.out.println("SHORTEST flight from Thailand to Vietnam: " + Arrays.toString(extCred.shortestPath("Thailand", "Vietnam")));
        System.out.println("SHORTEST flight from Thailand to Canada: " + Arrays.toString(extCred.shortestPath("Thailand", "Canada")));
        System.out.println("2nd SHORTEST flight from Thailand to Spain: " + Arrays.toString(extCred.secondShortestPath("Thailand", "Spain")));
        System.out.println("2nd SHORTEST flight from Thailand to Vietnam: " + Arrays.toString(extCred.secondShortestPath("Thailand", "Vietnam")));
        System.out.println("2nd SHORTEST flight from Thailand to Canada (no direct flight to Canada): " + Arrays.toString(extCred.secondShortestPath("Thailand", "Canada")));
        System.out.println("2nd SHORTEST flight from Australia to UK: " + Arrays.toString(extCred.shortestPath("Australia", "UK")));
        System.out.println("2nd SHORTEST flight from Thailand to Kenya: " + Arrays.toString(extCred.secondShortestPath("Thailand", "Kenya")));

    }
}
