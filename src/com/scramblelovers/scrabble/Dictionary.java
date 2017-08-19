package com.scramblelovers.scrabble;
import java.util.*;

public class Dictionary {
    Set<String> dictionary;

    // read file, one line per string, converting line to lowercase
    public Dictionary(String fileName) {
        dictionary = (TreeSet<String>)IOStuff.readFile(fileName, 2, 15, true, false);
        System.out.println("Dictionary:" + dictionary.size());
    }
    public boolean contains(String s) {
        boolean bReturn = dictionary.contains(s);
//        if (bReturn == false) {
//            System.out.println("Dictionary.contains: word not found:" + s + '.');
//        }
        return bReturn; 
    }
    public Set<String> getWordsAsCollection() {
        return dictionary;
    }
}
