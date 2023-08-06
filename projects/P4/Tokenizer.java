import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Tokenizer { //class that provides functionality to read lines of a text file, extract and normalize words
                            //store them using ArrayList class composed of String elements

    private ArrayList<String> wordList = new ArrayList<>(); //returns the word list created from the constructors

    public Tokenizer(String fileName) throws FileNotFoundException{ //
        Scanner scan = new Scanner(new File(fileName)); //new Scanner object from fileName

        try{
            while(scan.hasNext()){
                wordList.add(normalize(scan.next())); //normalizes each word in the file using helper method and adds it to wordList
            }
        }
        finally{
            scan.close(); //closes scan
        }
    }

    public Tokenizer(String[] strArray){ //constructor that obtains words directly from String array
        for(int i = 0; i < strArray.length; i++){ //runs through each index of strArray
            if(strArray[i] != null){ //if element at index i is not empty
                String newStr = normalize(strArray[i]); //normalizes the word
                wordList.add(newStr); //add the word to wordList
            }
        }
    }

    //helper method that normalizes each word in the file name
    private String normalize(String str){
        str = str.toLowerCase(); //lower case name
        str = str.strip(); //removes white spaces at beginning and end of string

        StringBuilder newStr = new StringBuilder(); //new string to be returned

        for(int i = 0; i < str.length(); i++){ //iterates through str
            if(Character.isLetterOrDigit(str.charAt(i))){ //adds character to new string if it is a letter or digit
                newStr.append(str.charAt(i));
            }
        }
        return newStr.toString(); //returns new normalized string
    }

    //getter method for wordList
    public ArrayList<String> wordList(){
        return wordList;
    }

}
