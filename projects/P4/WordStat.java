import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

public class WordStat {
    private HashTable wordTable; //hash table for word entries
    private HashTable pairTable; //hash table for pair entries
    private HashTable rankTable; //hash table for ranked word entries
    private HashTable rankPairTable; //hash table for ranked pair entries
    private ArrayList<String> script;

    private ArrayList<HashEntry> wordRankList = new ArrayList<HashEntry>(); //list that orders words from most to least frequent
    private ArrayList<HashEntry> pairRankList = new ArrayList<HashEntry>(); //list that orders pairs from most to least frequent

    //constructor with String argument computes statistics from the file name
    public WordStat(String stats){
        try{
            Tokenizer token = new Tokenizer(stats); //instantiates tokenizer object to compute statistics from file name
            script = token.wordList(); //renamed wordList from token as script for easier calling

            wordTable = new HashTable(script.size()); //hashTable for words of the file has size of amount of words in file
            pairTable = new HashTable(script.size()); //hashTable for pairs of words
            rankTable = new HashTable(script.size()); //hashTable for ranked words
            rankPairTable = new HashTable(script.size()); //hashTable for ranked pairs

            for(int i = 0; i < script.size(); i++){ //for loop for wordTable and pairTable entry
                if(wordTable.get(script.get(i)) > 0){ //checks if word is found in hashtable
                    wordTable.update(script.get(i), wordTable.get(script.get(i))+1); //increments value, thereby adds to frequency count
                }
                else{ //word has to be added into hashTable
                    wordTable.put(script.get(i), 1); //puts word into hash table, adds 1 to value to indicate that there is one instance of the word
                }

                int prev = i-1; //index for previous word to make pairs
                if(i > 0) {
                    String pair = script.get(prev) + " " + script.get(i); //combined string of two consecutive words
                    if (pairTable.get(pair) > 0) { //if pair is found
                        pairTable.update(pair, pairTable.get(pair) + 1); //increments frequency of pair in hash table by one
                    } else {
                        pairTable.put(pair, 1); //puts pair into hash table, increments frequency by one
                    }
                }
            }

            HashTable entryTable = new HashTable(script.size()); //traversing table that holds unique words to be added into rankList
            HashTable entryPairTable = new HashTable(script.size()); //traversing table that holds unique pairs to be added into pairRankList
            entryTable.put(script.get(0), 69);
            wordRankList.add(new HashEntry(script.get(0), wordTable.get(script.get(0))));
            for(int i = 1; i < script.size(); i++){ //for loop entry for wordRank and pairRank
                String word = script.get(i);
                int prev = i-1; //index for previous word to make pairs
                String pair = script.get(prev) + " " + script.get(i);

                if(entryTable.get(word) < 0){ //checks if word is NOT found in entry table
                    entryTable.put(word, 69); //added into entryTable
                    wordRankList.add(new HashEntry(word, wordTable.get(word))); //adds word into arrayList with correct frequency
                }

                if(entryPairTable.get(pair) < 0) { //checks if pair is found in entry pair table
                    entryPairTable.put(pair, 69); //added into entry pair table
                    pairRankList.add(new HashEntry(pair, pairTable.get(pair))); //adds pair into arrayList with correct frequency
                }
            }
            Collections.sort(wordRankList); //words are now sorted in arraylist with most frequent words at beginning of list
            Collections.sort(pairRankList); //pairs are now sorted in arraylist with most frequent pairs at beginning of list

            for(int i = 0; i < wordRankList.size(); i++){ //words are added into hashtable with their corresponding ranking
                String word = wordRankList.get(i).getKey(); //the word at index i from wordRankList
                rankTable.put(word, i+1); //puts word from rankList into hash table, making value added by one so that 1st common word gets 1 in ranking
            }

            for(int i = 0; i < pairRankList.size(); i++){ //pairs are added into hashtable with their corresponding ranking
                String pair = pairRankList.get(i).getKey(); //the pair at index i from wordRankList
                rankPairTable.put(pair, i+1); //puts word from rankList into hash table, making value added by one so that 1st common word gets 1 in ranking
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    //constructor with String array argument computes statistics from file name
    public WordStat(String[] arr){
        Tokenizer token = new Tokenizer(arr); //instantiates tokenizer object to compute statistics from array of Strings
        script = token.wordList(); //renamed wordList from token as script for easier calling

        wordTable = new HashTable(script.size()); //hashTable for words of the file has size of amount of words in file
        pairTable = new HashTable(script.size()); //hashTable for pairs of words
        rankTable = new HashTable(script.size()); //hashTable for ranked words
        rankPairTable = new HashTable(script.size()); //hashTable for ranked pairs

        for(int i = 0; i < script.size(); i++){ //for loop for wordTable and pairTable entry
            if(wordTable.get(script.get(i)) > 0){ //checks if word is found in hashtable
                wordTable.update(script.get(i), wordTable.get(script.get(i))+1); //increments value, thereby adds to frequency count
            }
            else{ //word has to be added into hashTable
                wordTable.put(script.get(i), 1); //puts word into hash table, adds 1 to value to indicate that there is one instance of the word
            }

            int prev = i-1; //index for previous word to make pairs
            if(i > 0) {
                String pair = script.get(prev) + " " + script.get(i); //combined string of two consecutive words
                if (pairTable.get(pair) > 0) { //if pair is found
                    pairTable.update(pair, pairTable.get(pair) + 1); //increments frequency of pair in hash table by one
                } else {
                    pairTable.put(pair, 1); //puts pair into hash table, increments frequency by one
                }
            }
        }
        HashTable entryTable = new HashTable(script.size()); //traversing table that holds unique words to be added into rankList
        HashTable entryPairTable = new HashTable(script.size()); //traversing table that holds unique pairs to be added into pairRankList
        entryTable.put(script.get(0), 69);
        wordRankList.add(new HashEntry(script.get(0), wordTable.get(script.get(0))));
        for(int i = 1; i < script.size(); i++){ //for loop entry for wordRank and pairRank
            String word = script.get(i);
            int prev = i-1; //index for previous word to make pairs
            String pair = script.get(prev) + " " + script.get(i);

            if(entryTable.get(word) < 0){ //checks if word is NOT found in entry table
                entryTable.put(word, 69); //added into entryTable
                wordRankList.add(new HashEntry(word, wordTable.get(word))); //adds word into arrayList with correct frequency
            }

            if(entryPairTable.get(pair) < 0) { //checks if pair is found in entry pair table
                entryPairTable.put(pair, 69); //added into entry pair table
                pairRankList.add(new HashEntry(pair, pairTable.get(pair))); //adds pair into arrayList with correct frequency
            }
        }
        Collections.sort(wordRankList); //words are now sorted in arraylist with most frequent words at beginning of list
        Collections.sort(pairRankList); //pairs are now sorted in arraylist with most frequent pairs at beginning of list

        for(int i = 0; i < wordRankList.size(); i++){ //words are added into hashtable with their corresponding ranking
            String word = wordRankList.get(i).getKey(); //the word at index i from wordRankList
            rankTable.put(word, i+1); //puts word from rankList into hash table, making value added by one so that 1st common word gets 1 in ranking
        }

        for(int i = 0; i < pairRankList.size(); i++){ //pairs are added into hashtable with their corresponding ranking
            String pair = pairRankList.get(i).getKey(); //the pair at index i from wordRankList
            rankPairTable.put(pair, i+1); //puts word from rankList into hash table, making value added by one so that 1st common word gets 1 in ranking
        }

    }



    //returns count of parameter argument word in the table, returns 0 if word is not found
    public int wordCount(String word){
        if(wordTable.get(word) < 0){ //if word is NOT found in hashtable
            return 0;
        }

        return wordTable.get(word);
    }

    //returns count of the word pair w1 w2, returns 0 if word pair is not found
    public int wordPairCount(String w1, String w2){
        String pair = w1 + " " + w2;
        if(pairTable.get(pair) < 0){ //if pair is NOT found in hash table
            return 0;
        }

        return pairTable.get(pair);
    }

    //returns rank of word in terms of commonality
    public int wordRank(String word){
        if(rankTable.get(word) > 0){ //word exists in table
            return rankTable.get(word); //returns rank of word
        }
        return 0; //word not found
    }

    //returns rank of pair in terms of commonality, returns 0 if word pair is not in table
    public int wordPairRank(String w1, String w2){
        String pair = w1 + " " + w2;
        if(rankPairTable.get(pair) > 0){ //word exists in rank table of pairs
            return rankPairTable.get(pair); //returns rank of pair
        }

        return 0; //pair not found
    }

    //returns a String array of the k MOST common words in DECREASING order of their count
    String[] mostCommonWords(int k){
        String[] wordArr; //initialize array
        if(k < 0){ //input is invalid, negative number
            wordArr = new String[0]; //return empty array
        }
        else if(k >= wordRankList.size()){ //greater than number of individual words
            wordArr = new String[wordRankList.size()]; //size of list
            for(int i = 0; i < wordRankList.size(); i++)
                wordArr[i] = wordRankList.get(i).getKey();
        }
        else{
            wordArr = new String[k]; //size of k
            for(int i = 0; i < k; i++){ //traverses through k number of ranked words
                wordArr[i] = wordRankList.get(i).getKey(); //returns the word of ith commonality
            }
        }

        return wordArr; //returns array

    }

    //returns a String array of the k LEAST common words in INCREASING order of their count
    public String[] leastCommonWords(int k){
        String[] wordArr; //initialize array
        if(k < 0){ //input is invalid, negative number
            wordArr = new String[0]; //return empty array
        }
        else if(k >= wordRankList.size()){ //greater than number of individual words
            wordArr = new String[wordRankList.size()]; //size of list
            for(int i = 0; i < wordRankList.size(); i++)
                wordArr[i] = wordRankList.get(wordRankList.size()-i-1).getKey(); //returns from back of arraylist, hence increasing order
        }
        else{
            wordArr = new String[k]; //size of k
            for(int i = 0; i < k; i++){ //traverses through k number of ranked words
                wordArr[i] = wordRankList.get(wordRankList.size()-i-1).getKey(); //returns from back of arraylist, hence increasing order
            }
        }

        return wordArr; //returns array
    }

    //returns the k most common word pairs in a String array
    public String[] mostCommonWordPairs(int k){
        String[] pairArr; //initialize array
        if(k < 0){ //input is invalid, negative number
            pairArr = new String[0]; //return empty array
        }
        else if(k >= pairRankList.size()){ //greater than number of individual words
            pairArr = new String[pairRankList.size()]; //size of list
            for(int i = 0; i < pairRankList.size(); i++)
                pairArr[i] = pairRankList.get(i).getKey(); //returns the pair of ith commonality
        }
        else{
            pairArr = new String[k]; //size of k
            for(int i = 0; i < k; i++){ //traverses through k number of ranked words
                pairArr[i] = pairRankList.get(i).getKey(); //returns the pair of ith commonality
            }
        }

        return pairArr; //returns array
    }

    //returns k most common words at a given relative position i to baseword
    public String[] mostCommonCollocs(int k, String baseWord, int i) {
        if(k < 0 || k > pairRankList.size()){ //input is invalid, either negative number or greater than number of individual pairs
            throw new IndexOutOfBoundsException();
        }

        String[] wordArr = new String[k]; //initialize array of size k
        HashTable collocTable = new HashTable(script.size()); //traversing table that holds unique collocs to be added into rankList
        ArrayList<HashEntry> collocRankList = new ArrayList<HashEntry>(); //list that orders collocs from most to least frequent

        if (i < 0) { //i is negative or -1
            for (int j = 1; j < script.size(); j++) { //runs through script
                String word = script.get(j);
                String prev = script.get(j-1); //index for previous word to make pairs
                String phrase = prev + " " + word;
                if (word.equals(baseWord)) { //baseword found
                    if (collocTable.get(prev) < 0) { //previous word hasn't been added into collocTable
                        collocTable.put(prev, 69); //added into collocTable
                        collocRankList.add(new HashEntry(prev, pairTable.get(phrase))); //adds word into arrayList with correct frequency
                    }
                }
            }
        } else { //i is positive or 1
            for (int j = 0; j < script.size()-1; j++) { //runs through script
                String word = script.get(j);
                String foll = script.get(j + 1);
                String phrase = word + " " + foll;
                if (word.equals(baseWord)) { //baseword found
                    if (collocTable.get(foll) < 0) { //following word hasn't been added into collocTable
                        collocTable.put(foll, 69); //added into collocTable
                        collocRankList.add(new HashEntry(foll, pairTable.get(phrase))); //adds word into arrayList with correct frequency
                    }
                }
            }
        }
        Collections.sort(collocRankList); //pairs are now sorted in arraylist with most frequent pairs at beginning of list

        for(int m = 0; m < k; m++){
            wordArr[m] = collocRankList.get(m).getKey(); //returns the pair of ith commonality
        }

        return wordArr; //return
    }

    //
    //String[] mostCommonCollocsExc(int k, String baseWord, int i, String[] exclusions){

    //}

    //returns a string composed of k words from startword
    public String generateWordString(int k, String startWord){
        StringBuilder strWords = new StringBuilder();
        while(k > 0){
            startWord = mostCommonCollocs(1, startWord, 1)[0]; //first element of array
            strWords.append(startWord);
            strWords.append(" ");
            k--;
        }

        return strWords.toString().trim();
    }



    public static void main(String[] args) {
        String[] demo = {"hi", "this", "assignment", "is", "some", "crazy", "stuff", "I", "had", "Covid", "and", "it", "hindered",
        "my", "abilities", "to", "complete", "the", "task", "therefore", "I", "am", "so", "glad", "I", "asked", "professor", "l", "for", "an", "extension",
        "and", "he", "was", "kind", "enough", "to", "give", "it", "to", "me", "thank", "you", "g", "so", "yeah", "I", "am", "just",
        "rambling", "to", "add", "text", "this", "demo", "has", "reached", "its", "conclusion"};

        WordStat demonstration = new WordStat(demo);
        System.out.println("demo of wordstat");
        System.out.println("times the word TO appeared: " + demonstration.wordCount("to"));
        System.out.println("times the pair I AM appeared: " + demonstration.wordPairCount("i", "am") );
        System.out.println("rank of word I: " + demonstration.wordRank("i"));
        System.out.println("rank of pair I AM: " + demonstration.wordPairRank("i", "am"));
        System.out.println("top 10 most common words are: " + Arrays.toString(demonstration.mostCommonWords(10)) );
        System.out.println("top 10 least common words are: " + Arrays.toString(demonstration.leastCommonWords(10)) );
        System.out.println("top 3 most common pairs are: " + Arrays.toString(demonstration.mostCommonWordPairs(10)) );
        System.out.println("3 most common collocations are: " + Arrays.toString(demonstration.mostCommonCollocs(3, "i", 1)));
    }

}
