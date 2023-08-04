public class NumArrayList implements NumList{
    private double[] list;
    private int size = 0;

    public NumArrayList(){ //constructor
        list = new double[0];
    }

    public NumArrayList(int capacity){ //constructor that initializes list with capacity from the integer argument
        list = new double[capacity];
    }


    //helper method that increases space of list (capacity) from integer argument
    public void space(int newSpace){
        if(newSpace < 0){
            return;
        }
        double[] newList = new double[newSpace]; //creates new array of size newSpace
        for(int i = 0; i < size(); i++){
            newList[i] = list[i]; //iterates through old list, copying each element into new list
        }
        list = newList; //list has the same values but with new capacity of newList
    }

    //setter method that sets size()
    public void setSize(int n){
        size = n;
    }

    //method that returns size of list (number of elements in the sequence)
    public int size() {
        return size;
    }

    //method that returns capacity of array (number of elements array can currently hold)
    public int capacity(){
        return list.length; //length of array
    }

    //method that adds new element to end of list
    public void add(double value){
        space(size()+1); //adds additional space to array for the new element
        list[size()] = value; //value is placed into the last index of the array
        setSize(size()+1); //adjusts size through setter method
    }

    //method that inserts element value into index i
    public void insert(int i, double value){
        if(i < 0){
            return;
        }
        space(size()+1); //adds additional space to array for the new element
        if(i >= size() ) { //checks if index i is greater than number of elements in array
            add(value); // adds list to the end
        }
        else {
            for(int k = size()-1; k > i-1; k--){ //starts from the last and second last courses in the array, iterates until it reaches index i
                list[k+1] = list[k]; //shifts current element at index k forward by one index, last shift is element at index i
            }
            list[i] = value; //places value from parameter at index i

            setSize(size()+1); //adjusts size through setter method
        }
    }

    //method that removes element in list at index i
    public void remove(int i){

        if(i < size() && i >= 0 ){
            for(int k = i; k < size()-1; k++){ //iterates from index i to the last course in array
                list[k] = list[k+1]; //replaces current course at index k with following course, effectively removing initial course at index i
            }
            setSize(size()-1); //adjusts size through setter method
        }

    }

    //method that returns true of value is found in list
    //returns false if value is not found
    public boolean contains(double value){
        for(int i = 0; i < size(); i++){
            if(list[i] == value){ //iterates through list, comparing each value at index i to value
                return true; //if match is found, return true
            }
        }

        return false; //return false, no match was found
    }

    //method that returns value at index i from integer parameter
    public double lookup(int i) throws IndexOutOfBoundsException{
        if(i < 0 || size() <= i){ //checks if integer parameter is more than size of list
            throw new IndexOutOfBoundsException(); //out of bounds exception, there is no i index in the list
        }
        return list[i]; //returns value at index i
    }

    //method that returns true if parameter list matches all values of list at the correct indices
    public boolean equals(NumList otherList){
        if(size() != otherList.size() ){ //checks if both lists are of same size
            return false; //returns false if they do not match size
        }
        else if( size() == 0 && otherList.size() == 0){ //checks if both lists are empty
            return true; //returns true, both lists are empty so they match
        } else{
            int counter = 0; //int counter to represent index to be iterated and compared with same index of list
            for(int i = 0; i < size(); i++){
                if(list[i] != otherList.lookup(counter) ){ //iterates through both lists, compares values of each list at the same index
                    return false; //if values don't match at the same index, lists are not equal so return false
                }
                counter++; //increments counter
            }
        }

        return true; //returns true if all values match between lists at the same respective indices
    }

    //method that removes duplicates of any elements in the list
    //preserves the list order and the position of the first of the duplicates.
    public void removeDuplicates(){
        for(int i = 0; i < size()-1; i++){ //outer loop traverses through all values of list except the final one
            for(int j = i+1; j < size(); j++){ //inner loop traverses list starting from the second element till the final one
                if(list[i] == list[j]){ //compares element at index i with index j
                    remove(j); //uses remove method to remove element at index j if it matches with element at index i, effectively removing it as a duplicate
                    j--; //decrements index j to compare the nearest right element to the removed element to element at index i since remove shifts all the following elements to the left
                }
            }
        }
    }

    //method that converts elements of list into a String, separated by spaces
    public String toString(){ //use StringBuilder
        StringBuilder str = new StringBuilder(); //initializes new StringBuilder class
        if(size() == 0){ //if list is empty
            return ""; //return empty string
        }
        for(int i = 0; i < size(); i++){
            str.append(list[i]); //iterates through list, appending value at index i to String str
            if(i < size()-1){ //checks if index i has yet to reached the final element
                str.append(" "); //includes space between values
            }
        }
        return str.toString(); //returns string
    }

    //method that returns true if array list is in sorted in ascending order, returns false otherwise
    public boolean isSorted(){
        for(int i = 0; i < size()-1; i++){
            if(list[i] > list[i+1]){
                return false;
            }
        }

        return true;
    }

    //method that reverses the elements of the list
    public void reverse(){
        if(size() > 1){
            int start = 0;
            double temp = 0.0;
            int end = size()-1;
            while(start < end){
                temp = list[start];
                list[start] = list[end];
                list[end] = temp;
                start++;
                end--;
            }
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

        NumArrayList unionList = new NumArrayList();
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
        NumArrayList list1 = new NumArrayList();
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
        NumArrayList list2 = new NumArrayList();
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
        System.out.println("UNION of List and List2");
        System.out.println("Union List: " + list1.union(list1, list2).toString());

    }
}
