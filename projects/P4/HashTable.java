public class HashTable {
    private int maxSize;
    private HashEntry[] table;

    public HashTable(){ //constructor with no arguments, initialize table of size 1024 (reasonable default size)
        maxSize = 1024;
        table = new HashEntry[maxSize];
    }

    //constructor with integer argument for size
    public HashTable(int size){
        maxSize = size;
        table = new HashEntry[maxSize];
    }

    //method that puts new Hash Entry into the Hash Table
    public void put(String key, int value){ //separate chaining method to
        HashEntry newEntry = new HashEntry(key, value);
        int index = (Math.abs(key.hashCode())) % maxSize; //index for entering the hash entry
        if(table[index] == null){
            table[index] = newEntry; //stashes entry into the index
        }
        else{ //collision
            HashEntry trav = table[index];
            while(trav.getNext() != null){ //traverses till end of linked list
                trav = trav.getNext();
            }

            trav.setNext(newEntry); //sets new entry at the end of linked list
        }
    }

    //additional put method that sorts Hash Entry according to a given parameter hashCode
    public void put(String key, int value, int hashCode){
        HashEntry newEntry = new HashEntry(key, value);
        int index = (Math.abs(hashCode)) % maxSize; //index for entering the hash entry
        if(table[index] == null){
            table[index] = newEntry; //stashes entry into the index
        }
        else{ //collision
            HashEntry trav = table[index];
            while(trav.getNext() != null){ //traverses till end of linked list
                trav = trav.getNext();
            }

            trav.setNext(newEntry); //sets new entry at the end of linked list
        }
    }

    //method that updates value associated with parameter key in the hash table
    //key doesn't exist, it should be added into the hashtable
    public void update(String key, int value){
        int index = (Math.abs(key.hashCode())) % maxSize; //index for entering the hash entry

        if(table[index] == null){ //no entries at index, meaning no matching key found
            put(key, value);
        }
        else{
            HashEntry trav = table[index];
            while(trav != null){ //traverses through linked list
                if(trav.getKey().equals(key)) { //if key is found in linked list
                    trav.setValue(value); //updates value of Hash Entry with matching key
                    return;
                }
                else{
                    trav = trav.getNext(); //continues traversing if match not found
                }
            }
            put(key,value); //no entries found in linked list, uses put to enter the Hash Entry
        }
    }

    //method that returns value associated with parameter matched key, returns -1 if match does not exist
    public int get(String key){
        int index = (Math.abs(key.hashCode())) % maxSize; //index for said key using Java's hash function
        if(table[index] != null){ //there exists entries at the specified index, meaning potential match key
            HashEntry trav = table[index]; //sets trav hash Entry to go through the chain at specified index
            while(trav != null){
                if(trav.getKey().equals(key)){ //match found
                    return trav.getValue(); //returns value
                }
                else{
                    trav = trav.getNext();
                }
            }
        }

        return -1; //match not found, return -1
    }


    //same get method as above except with parameter hashCode to be used to calculate specified index
    public int get(String key, int hashCode){
        int index = (Math.abs(hashCode)) % maxSize; //index for said key using input hash code
        if(table[index] != null){ //there exists entries at the specified index, meaning potential match key
            HashEntry trav = table[index]; //sets trav hash Entry to go through the chain at specified index
            while(trav != null){
                if(trav.getKey().equals(key)){ //match found
                    return trav.getValue(); //returns value
                }
                else{
                    trav = trav.getNext();
                }
            }
        }

        return -1; //match not found, return -1
    }

}
