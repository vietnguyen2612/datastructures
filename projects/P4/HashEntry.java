public class HashEntry implements Comparable<HashEntry>{

    private String k;
    private int v;
    private HashEntry next; //next parameter implemented in separate chaining of hash table

    public HashEntry(String key, int value){ //constructor
        k = key;
        v = value;
    }

    //getter method for key
    public String getKey(){
        return k;
    }

    //getter method for value
    public int getValue(){
        return v;
    }

    //setter method for value
    public void setValue(int value){
        v = value;
    }

    //getter method for next HashEntry in chaining
    public HashEntry getNext(){
        return next;
    }

    //setter method for next HashEntry in chaining
    public void setNext(HashEntry n){
        next = n;
    }

    //method overrides compareTo so as to organize hash entries with greatest value at beginning of list, hence descending order
    @Override
    public int compareTo(HashEntry o) {
        if(v > o.v ) { //value of entry 1 is greater than value of parameter entry
            return -1; //negative value makes it 'less' in terms of listing to sort in descending order
        }
        else if (v == o.v) { //values are the same
            return k.compareTo(o.k); //returns the comparison of strings for alphabetical ordering (extra credit)
        }
        return 1;
    }

}
