import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private Vertex[] vertices = null; //array of vertices
    private int numVertices = 0; //current number of vertices on graph
    private int maxVertices = 0; //max number of vertices

    //constructor
    public Graph(int maxVert) {
        vertices = new Vertex[maxVert];
        maxVertices = maxVert;
    }

    //A class which represent a vertex of the graph
    private class Vertex {
        private String name = null;
        private Hashtable<String, Integer> edges2 = new Hashtable<String, Integer>();
        private boolean encountered = false;
        private Vertex parent = null;

        protected Vertex(String id) {
            name = id;
        }
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

        for(int i = 0; i < vertices.length; i++) {
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
        if (names.length == 0) { //name is empty
            return false;
        }

        int wrong = 0; //counter for false encounters
        for (int i = 0; i < names.length; i++) { //iterates through list of names
            boolean err = this.addNode(names[i]); //boolean for not adding node
            if (err == false) {
                wrong++;
            }
        }
        if (wrong > 0) {
            return false;
        }
        return true; //all nodes were encountered
    }

    //adds an edge from node from to node to
    public boolean addEdge(String from, String to) {
        if (from.compareTo(to) == 0) { //from is to, cannot add this edge
            return false;
        }

        int fi = -1; //index for from vertex
        int ti = -1; //index for to vertex
        for (int i = 0; i < vertices.length; i++) { //iterates through array of vertices
            if (vertices[i] != null && vertices[i].name.compareTo(from) == 0) { //vertex in list matches from
                fi = i;
            }

            if (vertices[i] != null && vertices[i].name.compareTo(to) == 0) { //vertex in list matches to
                ti = i;
            }
        }


        if (fi != -1 && ti != -1) { //if counts are not 0
            if (vertices[fi].edges2.get(to) != null) { //if tail vertex  matches to
                return false;
            }

            vertices[fi].edges2.put(to, ti); //put to into f1 index matrix
            vertices[ti].edges2.put(from, fi); //put from into f2 index matrix
            return true;
        }
        return false;
    }

    //adds an undirected edge from node from to each node in tolist
    public boolean addEdges(String from, String[] toList) {
        if (toList.length == 0) { //tolist is empty
            return false;
        }

        int wrong = 0; //counter for false encounters
        for (int i = 0; i < toList.length; i++) {
            // A flag to check if the operation is carried out successfully
            boolean err = this.addEdge(from, toList[i]); //checks if error is met
            if (err == false) {
                wrong++; //wrong counter incremented
            }
        }
        if (wrong > 0) {
            return true;
        }
        return false;
    }


    //removes a node from the graph along with all connected edges
    public boolean removeNode(String name) {
        int indRem = -1; //index for node to be removed
        for (int i = 0; i < vertices.length; i++) { //iterates through vertex list
            if (vertices[i] != null && vertices[i].name.compareTo(name) == 0) { //if current vertex shares name
                indRem = i; //remove index is i
            }
        }

        if (indRem != -1) {
            LinkedList<Integer> indList = new LinkedList<Integer>(vertices[indRem].edges2.values()); //new linked list of tail values

            while (!indList.isEmpty()) {
                int vertexToRemoveEdge = indList.remove();
                vertices[vertexToRemoveEdge].edges2.remove(name); //removes name from tail edges
            }

            for (int i = indRem; i < vertices.length - 1; i++) { //iterates from index of remove node to one less than array
                vertices[i] = vertices[i + 1]; //shifts all one over
            }

            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i] != null) {
                    Set<String> setNames = vertices[i].edges2.keySet(); //sets name to edges of Java key set
                    for (String n : setNames) {
                        if (vertices[i].edges2.get(n) > indRem) { //if tail edge is greater than index
                            vertices[i].edges2.replace(n, vertices[i].edges2.get(n) - 1);
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
    public boolean removeNodes(String[] nodeList) {
        if (nodeList.length == 0) {
            return false;
        }

        int wrong = 0; //counter for wrong encounters
        for (int i = 0; i < nodeList.length; i++) {
            boolean err = this.removeNode(nodeList[i]);
            if (err == false) {
                wrong++;
            }
        }

        if (wrong > 0) { //if at least one node couldn't be removed
            return false;
        }
        return true;
    }

    //prints the graph in the same adjacency list format in the read method described below
    //nodes and their neighbors and their neighbors should be listed in alphabetical order
    public void printGraph() {
        ArrayList<Vertex> listVertices = new ArrayList<Vertex>(); //stores all vertices of graph into linked list
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                listVertices.add(vertices[i]); //adds vertices into list
            }
        }

        Collections.sort(listVertices, new Comparator<Vertex>() { //overrides compare method to compare names methods
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.name.compareTo(o2.name);
            }
        });

        for (int i = 0; i < listVertices.size(); i++) { //prints vertices, separated by white space
            System.out.print(listVertices.get(i).name);
            if (i != listVertices.size() && listVertices.get(i).edges2.size() != 0) {
                System.out.print(" ");
            }

            ArrayList<String> list = new ArrayList<String>(listVertices.get(i).edges2.keySet()); //stores list of name of edges of the graphs
            Collections.sort(list); //sorts list
            for (int j = 0; j < list.size(); j++) { //iterates through list, printing each name by white space
                System.out.print(list.get(j));
                if (j != list.size() - 1 && list.size() != 0) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    //helper method which sorts edge with respect to their order of their neighbors
    private ArrayList<String> sortEdges(int currentIndex, String nOrder) {
        // Store the list of name from the edges of the graphs
        ArrayList<String> list = new ArrayList<String>(vertices[currentIndex].edges2.keySet());
        if (nOrder.compareTo("alphabetical") == 0) {
            Collections.sort(list); //sorts list in alphabetical order
        } else if (nOrder.compareTo("reverse") == 0) {
            Collections.sort(list, Collections.reverseOrder()); //sorts list in reverse alphabetical order
        }
        return list;
    }

    //constructs a graph from a text file
    public static Graph read(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        Graph graph = new Graph(100);
        while(scan.hasNextLine()){ //scans line by line
            String[] arr = scan.nextLine().split(" "); //creates array for each line, splitting strings by white space
            graph.addNodes(arr); //add nodes from array
            graph.addEdges(arr[0], arr); //adds all edges, indicated as from 0th vertex to rest of vertices
        }
        return graph;
    }

    //returns the path of DFS search between nodes from and to, ordered alphabetically or reverse alphabetically
    public String[] DFS(String from, String to, String neighborOrder) {
        ArrayList<String> dfsList = new ArrayList<String>(); //stores nodes that have been encountered

        int sInd = -1; //start index
        int tInd = -1; //to index
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && from.compareTo(vertices[i].name) == 0) {
                sInd = i;
            }

            if (vertices[i] != null && to.compareTo(vertices[i].name) == 0) {
                tInd = i;
            }
        }

        if (sInd != -1 && tInd != -1) { //if indices are not less than 0
            dfsTrav(sInd, tInd, dfsList, neighborOrder); //recursively calls traversal to switch to and from string
        } else {
            return new String[]{}; //returns empty string
        }

        // The resulting list which contains the path to be returned
        String[] result = new String[dfsList.size()]; //new string of results from dfs list
        for (int i = 0; i < dfsList.size(); i++) {
            result[i] = dfsList.get(i); //adds current element dsflist into result
        }

        for (int i = 0; i < vertices.length; i++) { //iterates to make encouter variables of each vertices true or false
            if (vertices[i] != null && vertices[i].encountered == true) {
                vertices[i].encountered = false;
            }
        }
        return result;
    }

    //helper method to traverse the graph current index to tail index
    private void dfsTrav(int currInd, int tInd, ArrayList<String> list, String neighborOrder) {
        if (list.size() == 0) {
            list.add(vertices[currInd].name);
        }
        vertices[currInd].encountered = true;
        if (currInd == tInd) {
            return;
        }
        // Store the list of edges of the current vertex being considered
        ArrayList<String> sortedListVertices = this.sortEdges(currInd, neighborOrder);
        // An iterator instance to traverse the table of edges
        Iterator<String> tableItr = sortedListVertices.iterator();
        while (tableItr.hasNext()) {
            String next = tableItr.next(); //stores traversal edge
            int nextIndex = vertices[currInd].edges2.get(next); //stores index of traversal edge
            if (vertices[nextIndex].encountered == false){ //vertices of encountered indices are false
                if (list.size() == 0) {
                    list.add(vertices[currInd].name); //indices name
                    list.add(vertices[nextIndex].name); //indices of next index
                    dfsTrav(nextIndex, tInd, list, neighborOrder);
                }

                if (nextIndex == tInd && list.get(list.size()-1).compareTo(vertices[tInd].name) != 0) { //if next index is equal to tail and last element in list is greater than tind
                    list.add(vertices[nextIndex].name); //list adds upon next index
                    return;
                }

                if (list.get(list.size() - 1).compareTo(vertices[tInd].name) != 0) {
                    list.add(vertices[nextIndex].name);
                    dfsTrav(nextIndex, tInd, list, neighborOrder);
                }
            }
        }
        if (list.get(list.size() - 1).compareTo(vertices[tInd].name) != 0) {
            list.remove(list.size() - 1);
        }
    }

    //returns the path of DFS search between nodes from and to, ordered alphabetically or reverse alphabetically
    public String[] BFS(String from, String to, String neighborOrder){
        if(from.compareTo(to) == 0) {
            return new String[] {from};
        }
        int sInd = -1; //start index
        int tInd = -1; //to index
        int end = -1;
        for(int i = 0; i < vertices.length; i++) { //traverses through vert list and adds if from matches
            if(vertices[i] != null && from.compareTo(vertices[i].name) == 0) {
                sInd = i;
            }

            if(vertices[i] != null && to.compareTo(vertices[i].name) == 0){ //traverses through vert list and adds if name matches current name of list of vertices
                tInd = i;
            }
        }
        if(sInd != - 1 && tInd != -1) {
            end = bfsTrav(sInd, sInd, null, tInd, neighborOrder);
            if(end == -1) {
                for(int m = 0; m < vertices.length; m++) {
                    if(vertices[m] != null) {
                        vertices[m].encountered = false;
                        vertices[m].parent = null;
                    }
                }
                return new String[] {};
            }
        }else {
            return new String[] {};
        }

        ArrayList<String> temp = new ArrayList<String>(); //strong that stores the reverse of path
        while(vertices[end].name.compareTo(vertices[sInd].name) != 0) {
            int parentIndex = vertices[end].edges2.get(vertices[end].parent.name);
            temp.add(vertices[end].name);
            end = parentIndex;
        }

        temp.add(from);
        Collections.reverse(temp);
        String[] result = new String[temp.size()]; //stores the path in direction needed from from to to

        for(int i = 0; i < result.length; i++) { //copies temp into result
            result[i] = temp.get(i);
        }

        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] != null) {
                vertices[i].parent = null; //Set parents to null if BFS is called more than once
            }
        }
        return result;
    }

    //helper method to traverse graph in neighbor order from the current index to the tail index, utilizes a parent vertex
    private int bfsTrav(int fromIndex, int currentIndex, Vertex parent, int toIndex, String neighborOrder) {
        vertices[currentIndex].encountered = true;
        LinkedList<Vertex> queue = new LinkedList<Vertex>(); //queue marks all vertices that are considered
        queue.add(vertices[currentIndex]);

        while(queue.size() != 0) {
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null && vertices[i].name.compareTo(queue.getFirst().name) == 0) {
                    currentIndex = i;
                }
            }

            ArrayList<String> sortedListVertices = this.sortEdges(currentIndex, neighborOrder); //array that stores edges of vertex according to neighbor order

            Vertex cons = queue.removeFirst(); //considered vertex
            for(int i = 0; i < sortedListVertices.size(); i++) {
                // Store the index of the neighbors to be checked if they are encountered
                int index = vertices[currentIndex].edges2.get(sortedListVertices.get(i));
                if(vertices[index].encountered == false) {
                    vertices[index].encountered = true;
                    vertices[index].parent = cons;
                    queue.add(vertices[index]);

                    if(vertices[index].name.compareTo(vertices[toIndex].name) == 0) {
                        vertices[index].parent = cons;
                        for(int m = 0; m < vertices.length; m++) {
                            if(vertices[m] != null) {
                                vertices[m].encountered = false;
                            }
                        }
                        return index;
                    }
                }
            }
        }
        return -1;
    }

    //helper method that traverses the graph from from to to and returns all possible paths
    //if no path found, return empty array
    private List<ArrayList<String>> allPathsBFS(String from, String to){
        int sInd = -1; //from or start index
        List<ArrayList<String>> allPaths = new ArrayList<>(); //stores all paths from --> to
        LinkedList<List<String>> queue = new LinkedList<>(); //queue marks paths considered

        ArrayList<String> firstPath = new ArrayList<String>(); //temporary list initialized to store the first path
        firstPath.add(from);
        queue.add(firstPath);
        while(!queue.isEmpty()) {
            List<String> path = queue.removeFirst(); //considered path is dequeued
            String fNode = path.get(path.size() - 1);
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null && vertices[i].name.compareTo(fNode) == 0) {
                    sInd = i;
                }
            }

            if(fNode.compareTo(to) == 0) {
                ArrayList<String> newPath = new ArrayList<String>(path); //stores path from --> to
                allPaths.add(newPath);
            }else {
                ArrayList<String> listNeighbors = new ArrayList<String>(vertices[sInd].edges2.keySet()); //stores list of neighbors into vertex being considered
                for(int i = 0; i < listNeighbors.size(); i++) {
                    if(isVisited(listNeighbors.get(i), path)) { //uses helper method to see is node has been visited
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

    //A method to find the second-shortest path from --> to vertex
    public String[] secondShortestPath(String from, String to) {
        List<String> sSPath = null; //stores the second-shortest path
        int sInd = -1; //start vertex
        if(from.compareTo(to) == 0) {
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i] != null && from.compareTo(vertices[i].name) == 0) {
                    sInd = i;
                }
            }

            if(sInd != -1) {
                ArrayList<String> neighbors = new ArrayList<String>(vertices[sInd].edges2.keySet()); //Store neighbors of start vertex
                if(neighbors.size() == 0) {
                    return new String[] {};
                }
                sSPath = new ArrayList<String>(); //second-shortest path
                sSPath.add(from);
                sSPath.add(neighbors.get(0));
                sSPath.add(to);

                String[] result = new String[sSPath.size()]; //array that stores the second-shortest path
                for(int i = 0; i < sSPath.size(); i++) {
                    result[i] = sSPath.get(i);
                }
                return result;
            }
        }
        List<ArrayList<String>> allPaths = allPathsBFS(from, to); //all potential paths from head to tail vertex

        if(allPaths.size() <= 1) {
            return new String[] {};
        }


        int shortestSize = allPaths.get(0).size(); //size of shortest path

        for(int i = 0; i < allPaths.size(); i++) {
            if(allPaths.get(i).size() > shortestSize) {
                sSPath = allPaths.get(i);
                break;
            }
        }

        if(sSPath == null) {
            return new String[] {};
        }

        String[] result = new String[sSPath.size()]; //stores the resulting list as 2nd shortest path from head to tail vertex
        for(int i = 0; i < sSPath.size(); i++) {
            result[i] = sSPath.get(i);
        }
        return result;
    }



    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph(100);
        System.out.println("Add A using ADDNODE method: ");
        graph.addNode("A");
        System.out.println("Add B,C,D using ADDNODES method: ");
        String[] nodes1 = new String[] {"B", "C", "D"};
        System.out.println("Success of ADDNODES: " + graph.addNodes(nodes1));
        System.out.println("ADD E,F,G,K,E (Duplicate)");
        String[] nodes2 = new String[] {"E", "F", "G", "K", "E"};
        System.out.println("Success of Duplicate ADDNODES: " + graph.addNodes(nodes2));
        System.out.println("Graph printed using PRINTGRAPH method ");
        graph.printGraph();

        System.out.println("ADDEDGE A-D: ");
        graph.addEdge("A", "D");
        System.out.println("ADD EDGES B,G,C from A: ");
        String[] edgesA = new String[] {"B", "G", "C"};
        graph.addEdges("A", edgesA);
        System.out.println("ADD EDGES C,E from B: ");
        String[] edgesB = new String[] {"C", "E"};
        graph.addEdges("B", edgesB);
        System.out.println("ADD EDGES D,E,F from C: ");
        String[] edgesC = new String[] {"D", "E", "F"};
        graph.addEdges("C", edgesC);
        System.out.println("ADD EDGES F-K and G-K ");
        graph.addEdge("F", "K");
        graph.addEdge("G", "K");
        System.out.println("PRINTGRAPH result: ");
        graph.printGraph();

        System.out.println("DFS traversal alphabetically from A-F: ");
        System.out.println(Arrays.toString(graph.DFS("A", "F", "alphabetical")));
        System.out.println("DFS traversal reverse alphabetically from A-F: ");
        System.out.println(Arrays.toString(graph.DFS("A", "F", "reverse")));
        System.out.println("DFS traversal alphabetically from A-D: ");
        System.out.println(Arrays.toString(graph.DFS("A", "D", "alphabetical")));
        System.out.println("DFS traversal alphabetically from A-D: ");
        System.out.println(Arrays.toString(graph.DFS("A", "D", "reverse")));

        System.out.println("BFS traversal alphabetically from A-F: ");
        System.out.println(Arrays.toString(graph.BFS("A", "F", "alphabetical")));
        System.out.println("BFS traversal reverse alphabetically from A-F: ");
        System.out.println(Arrays.toString(graph.BFS("A", "F", "reverse")));
        System.out.println("BFS traversal alphabetically from A-D: ");
        System.out.println(Arrays.toString(graph.BFS("A", "D", "alphabetical")));
        System.out.println("BFS traversal reverse alphabetically from A-D: ");
        System.out.println(Arrays.toString(graph.BFS("A", "D", "reverse")));

        System.out.println("SECONDSHORTESTPATH from A to F: ");
        System.out.println(Arrays.toString(graph.secondShortestPath("A", "F")));
        System.out.println("SECONDSHORTESTPATH from A to D: ");
        System.out.println(Arrays.toString(graph.secondShortestPath("A", "D")));
        System.out.println("SECONDSHORTESTPATH from B to E: ");
        System.out.println(Arrays.toString(graph.secondShortestPath("B", "E")));
        System.out.println("SECONDSHORTESTPATH from A to A: ");
        System.out.println(Arrays.toString(graph.secondShortestPath("A", "A")));

        System.out.println("READ test.txt file as PRINT GRAPH: ");

        Graph.read("test.txt").printGraph();

        System.out.println("REMOVE node A result: ");
        graph.printGraph();

        System.out.println("REMOVE K,G,E result: ");
        String[] removeNodes = new String[] {"K", "G", "E"};
        graph.printGraph();

        System.out.println("REMOVE Z (doesn't exist) result: ");
        graph.printGraph();
    }
}
