public class NumLinkedList implements NumList{
    private Node head; // head of list
    private Node tail; //tail of list
    private int size = 0;

    /* Linked list Node*/
    static class Node {
        double data;
        Node next;

        // Constructor to create a new node
        // Next is by default initialized
        // as null
        Node(double d) {
            data = d;
            next = null;
        }

        public double getData(){
            return data;
        }

    }

    public NumLinkedList(){ //constructor
        head = tail = null;
    }

    public Node getHead(){
        return head;
    } //returns head

    public void setHead(Node h){ //set head to parameter Node h
        head = h;
    }

    public Node getTail(){
        return tail;
    } //returns head

    public void setTail(Node t){ //set head to parameter Node h
        tail = t;
    }

    //setter method that sets size()
    private void setSize(int n){
        size = n;
    }

    //method that returns size of list (number of elements in the sequence)
    public int size() {
        return size;
    }

    //method that returns capacity of array (number of elements array can currently hold)
    public int capacity(){
        return 1000000; //length of array
    }

    //method that adds new element to end of list
    public void add(double value){
        Node newNode = new Node(value); //instantiates newNode as node with data from parameter

        if(getHead() == null){ //linked list is empty
            setHead(newNode); //set newNode as head
        }
        else{
            tail.next = newNode; //make newNode the last Node
        }
        setTail(newNode); //set newNode as tail

        setSize(size()+1); //adjusts size through setter method
    }

    //method that inserts element value into index i
    public void insert(int i, double value){
        Node newNode = new Node(value); //instantiates newNode as node with data from parameter

        if(i < 0){ //returns if index i is negative number
            return; //returns to not increment size
        }

        if(i == 0){ //index is at beginning of array
            newNode.next = head; //set pointer of newNode to head
            setHead(newNode); //set newNode as head of linkedlist
        }

        if(i >= size() ){  //index is greater than size
            add(value); //add to beginning of list according to add method
            return; //return to avoid invoking setSize twice
        }

        if(i > 0 && i < size()){
            Node temp = head;
            int j = 0;
            while(j < i-1) { //traverse until reaches Node before Node at index i
                temp = temp.next;
                j++; //increments counter
            }
            newNode.next = temp.next; //Node to be inserted points towards Node at index i
            temp.next = newNode; //pointer Node points towards newNode, linking it into the linked list
        }

        setSize(size()+1); //adjusts size through setter method
    }

    //method that removes element in list at index i
    public void remove(int i){

        if(i >= size() || i < 0){ //index i is greater than size of linked list or negative
            return;
        }

        if (i == 0) { //removing the first element
            head = head.next;
        } else {
            Node curr = head;
            for (int j = 0; j < i-1; j++) { //traverses through linked list until Node prior to Node needed to be removed
                curr = curr.next;
            }

            if(i == size()-1) { //removed item is at the end of the array
                setTail(curr); //set curr as new tail
            }
            curr.next = curr.next.next; //Node points to Node after the Node to be removed, effectively removing it
        }

            setSize(size()-1); //adjusts size through setter method
    }

    //method that returns true of value is found in list
    //returns false if value is not found
    public boolean contains(double value){
        Node temp = head;

        while(temp != null){
            if(temp.getData() == value){
                return true;
            }
            temp = temp.next;
        }

        return false; //return false, no match was found
    }

    //method that returns value at index i from integer parameter
    public double lookup(int i) throws IndexOutOfBoundsException{
        if(i < 0 || size() <= i){ //checks if integer parameter is more than size of list
            throw new IndexOutOfBoundsException(); //out of bounds exception, there is no i index in the list
        }
        Node temp = head;
        int count = 0;

        while(count < i){
            temp = temp.next;
            count++;
        }

        return temp.getData(); //returns value at index i
    }

    //method that returns true if parameter list matches all values of list at the correct indices
    public boolean equals(NumList otherList){
        if(size() != otherList.size() ){ //checks if both lists are of same size
            return false; //returns false if they do not match size
        }
        else if( size() == 0 && otherList.size() == 0){ //checks if both lists are empty
            return true; //returns true, both lists are empty so they match
        } else{
            for(int i = 0; i < size(); i++){
                if(lookup(i) != otherList.lookup(i) ){ //iterates through both lists, compares values of each list at the same index
                    return false; //if values don't match at the same index, lists are not equal so return false
                }
            }
        }

        return true; //returns true if all values match between lists at the same respective indices
    }

    //method that removes duplicates of any elements in the list
    //preserves the list order and the position of the first of the duplicates.
    public void removeDuplicates(){
        if(size() <= 1){ //linked list only has one or no elements
            return;
        }

        int curr = 0; //counter and index for current node
        int foll = 1; //counter and index for following node
        Node temp = head; //Node corresponding with curr counter
        Node check = temp.next; //Node corresponding with foll counter
        while(curr < size()-1){
            while(foll < size()){
                if(temp.getData() == check.getData()){ //if Node at check matches value of that of temp
                    check = check.next;
                    remove(foll);
                    foll--;
                }
                else{
                    check = check.next;
                }
                foll++;
            }
            if(temp.next != null){  //checks as long as following node is not null (temp is not at the end)
                temp = temp.next;
            }
            check = temp.next;
            curr++;
            foll = curr+1;
        }
    }

    //method that converts elements of list into a String, separated by spaces
    public String toString(){ //use StringBuilder
        StringBuilder str = new StringBuilder(); //initializes new StringBuilder class
        if(size() == 0){ //if list is empty
            return ""; //return empty string
        }
        Node temp = head;
        int count = 0;
        while(count < size() && temp != null){
            str.append(temp.getData()); //iterates through list, appending value at index i to String str
            temp = temp.next;
            count++;
            if(count < size()){ //checks if index i has yet to reached the final element
                str.append(" "); //includes space between values
            }
        }
        return str.toString(); //returns string
    }

    //method that returns true if linked list is in sorted in ascending order, returns false otherwise
    public boolean isSorted(){
        if(size() == 0){ //if list is empty, return true
            return true;
        }

        Node temp = head;
        Node check = temp.next; //check will always be the following node to temp
        int count = 0;
        while(count < size-1){
            if(temp.getData() > check.getData()){ //if the following node is less than the previous node
                return false; //return false
            }
            temp = temp.next;
            check = check.next; //iterate
            count++;
        }

        return true;
    }

    //method that reverses the elements of the list
    public void reverse(){
        if(size() > 1){
            Node prev = null; //set variable prev as null, represents the pointer that will point backwards
            Node curr = head; //set variable curr as head of list, represents current node to be iterated
            Node following; //set variable following to represent following node
            if( curr == null){ //checks if curr, which is now the head of list, is empty, indicating if the list is empty
                return; //returns list
            }
            while(curr != null){ //runs through the code in the while loop as long as the node represented by variable curr is not null
                following = curr.next; //makes the node following the value of  after curr
                curr.next = prev; //makes the value of the curr.next the previous node, effectively making the 'pointer' of the current node point to the previous node
                prev = curr; //prev takes on the value of the current node, first part of iteration
                curr = following; //curr takes on the value of current node, second part of iteration, effectively makes curr the following node and prev the current node from the line above
            }
            head = prev; //list is now reversed, the head of the final node takes on the value of prev, effectively making the 'pointer' of the last node point to the previous node, making it the first node
        }
    }

    //method that creates new NumList that is union of list1 and list 2 without duplicates
    public NumList union(NumList list1, NumList list2){
        if(list1.size() == 0){ //if list 1 is empty, return list 2
            return list2;
        }
        else if(list2.size() == 0){ //if list 2 is empty, return list 1
            return list1;
        }

        NumLinkedList unionList = new NumLinkedList();
        int i = 0; //counter for list i
        int j = 0; //counter for list j
        if(list1.isSorted() && list2.isSorted()){ //lists are both sorted
            while(i < list1.size() && j < list2.size()){ //while both counters are less than their list sizes
                if(list1.lookup(i) < list2.lookup(j)){ //if value at i less than j
                    unionList.add(list1.lookup(i++)); //add Node at index i from list1
                }
                else if(list1.lookup(i) == list2.lookup(j)){ //if values are equal
                    unionList.add(list2.lookup(j++)); //add Node at index j from list2
                    i++;
                }
                else{ //if value at j less than i
                    unionList.add(list2.lookup(j++)); //add Node at index j from list2
                }
            }

            while(j < list2.size()){ //iterates through rest of list 2
                if(list2.lookup(j) != unionList.lookup(size()-1)){
                    unionList.add(list2.lookup(j));
                }
                j++;
            }
            while(i < list1.size()){ //iterates through rest of list 1
                if(list1.lookup(i) != unionList.lookup(size()-1)){
                    unionList.add(list1.lookup(i));
                }
                j++;
            }
        }
        else{ //lists are unsorted
            while(i < list1.size()){
                unionList.add(list1.lookup(i)); //add all elements of list1 to unionList
                i++;
            }
            i = 0;
            while(i < list2.size()){
                unionList.add(list2.lookup(i)); //add all elements of list2 to unionList
                i++;
            }
            unionList.removeDuplicates();
        }

        return unionList;
    }

    public static void main(String[] args) {
        NumLinkedList list1 = new NumLinkedList();
        list1.add(9);
        list1.add(2);
        list1.add(19);
        list1.add(4.3);
        list1.add(18);
        list1.add(5);
        list1.add(3.6);
        System.out.println("List1 (elements added using ADD method, printed using toString): " + list1.toString());

        list1.remove(-1);
        System.out.println("REMOVED element at INDEX -1 (Invalid Index)");
        System.out.println("List1: " + list1.toString());
        list1.remove(0);
        System.out.println("REMOVED element at INDEX 0 (First element)");
        System.out.println("List1: " + list1.toString());
        list1.remove(5);
        System.out.println("REMOVED element at INDEX 5 (Last element)");
        System.out.println("List1: " + list1.toString());
        list1.remove(2);
        System.out.println("REMOVED element at INDEX 2 (element in middle of array)");
        System.out.println("List1: " + list1.toString());
        System.out.println();

        list1.insert(-1, 1.0);
        System.out.println("INSERT value 1.0 at INDEX -1 (invalid index)");
        System.out.println("List1: " + list1.toString());
        list1.insert(0, 1.0);
        System.out.println("INSERT value 1.0 at INDEX 0 (start of array)");
        System.out.println("List1: " + list1.toString());
        list1.insert(10, 1.0);
        System.out.println("INSERT value 1.0 at INDEX 10 (greater than array size)");
        System.out.println("List1: " + list1.toString());
        list1.insert(3, 1.0);
        System.out.println("INSERT value 1.0 at INDEX 3 (middle of array)");
        System.out.println("List1: " + list1.toString());
        System.out.println();

        list1.insert(2, 1.0);
        list1.insert(4, 2.0);
        list1.insert(5, 18.0);
        System.out.println("List1 (added some duplicates): " + list1.toString());
        list1.removeDuplicates();
        System.out.println("Removed DUPLICATES");
        System.out.println("List1: " + list1.toString());
        System.out.println("SIZE (printed using method): " + list1.size() );
        System.out.println("CAPACITY (printed using method): " + list1.capacity() );
        System.out.println();

        System.out.println("List1: " + list1.toString());
        System.out.println("Check if current list1 CONTAINS value 3.6");
        System.out.println("Result: " + list1.contains(3.6));
        System.out.println("Check if current list1 CONTAINS value 19.0");
        System.out.println("Result: " + list1.contains(19.0));
        System.out.println();

        NumArrayList compareList = new NumArrayList();
        compareList.add(1.0);
        compareList.add(2.0);
        compareList.add(19.0);
        compareList.add(18.0);
        compareList.add(5.0);
        System.out.println("compareList: " + compareList.toString());
        System.out.println("List1: " + list1.toString());
        System.out.println("Compare if List1 is EQUAL to identical compareList");
        System.out.println("Result: " + list1.equals(compareList));
        compareList.remove(4);
        compareList.add(17.0);
        System.out.println("Updated compareList: " + compareList.toString());
        System.out.println("List1: " + list1.toString());
        System.out.println("Compare if List1 is EQUAL to nonidentical compareList");
        System.out.println("Result: " + list1.equals(compareList));
        System.out.println();

        System.out.println("List1: " + list1.toString());
        System.out.println("LOOKUP value at index 0 (beginning of array)");
        System.out.println("Value @ Index 0: " + list1.lookup(0));
        System.out.println("LOOKUP value at index 2 (middle of array)");
        System.out.println("Value @ Index 2: " + list1.lookup(2));
        System.out.println("LOOKUP value at index 4 (last element of array)");
        System.out.println("Value @ Index 4: " + list1.lookup(4));
        System.out.println("LOOKUP value at index 10 (beyond array size)");
        try {
            list1.lookup(10); //lookup element at index 0 for empty list
        }catch(IndexOutOfBoundsException e) {
            System.out.println("No element found at specified index");
        }
        System.out.println("LOOKUP value at index -1 (Invalid index)");
        try {
            list1.lookup(10); //lookup element at index 0 for empty list
        }catch(IndexOutOfBoundsException e) {
            System.out.println("Negative index");
        }
        System.out.println();

        System.out.println("List: " + list1.toString());
        System.out.println("Checks if List1 is SORTED in ascending order");
        System.out.println("Result: " + list1.isSorted());
        NumLinkedList list2 = new NumLinkedList();
        list2.add(1.0);
        list2.add(3);
        list2.add(5);
        list2.add(7);
        list2.add(12.8);
        list2.add(18.0);
        System.out.println("List2: " + list2.toString());
        System.out.println("Checks if List2 is SORTED in ascending order");
        System.out.println("Result: " + list2.isSorted());
        System.out.println();

        System.out.println("List1: " + list1.toString());
        System.out.println("REVERSE the linked list");
        list1.reverse();
        System.out.println("Result: " + list1.toString());
        System.out.println();

        System.out.println("List1: " + list1.toString());
        System.out.println("List2: " + list2.toString());
        System.out.println("UNION of List1 and List2");
        System.out.println("Union List: " + list1.union(list1, list2).toString());


    }
}
