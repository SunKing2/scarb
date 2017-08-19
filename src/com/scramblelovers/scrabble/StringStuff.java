package com.scramblelovers.scrabble;
import java.util.*;

public class StringStuff {

    // if a letter of smallWord is not found in bigword, subtract the wildcard letter and return that
    public static String subtractWord(String bigWord, String smallWord, char wildcard) {
        String sReturn = bigWord;
        for (int i = 0; i < smallWord.length(); i++) {
            sReturn = subtractLetter(sReturn, smallWord.charAt(i), wildcard);
        }
        return sReturn;
    }

    // if the letter is not found in bigword, subtract the wildcard letter and return that
    public static String subtractLetter(String bigWord, char letter, char wildcard) {
        String sReturn = new String(bigWord);
        int ix = bigWord.indexOf(letter);
        if (ix >=0 ) {
            sReturn = bigWord.substring(0, ix) + bigWord.substring(ix + 1);
        }
        else {
            //System.out.println("StringStuff.subtractLetter(String, char, char)");
            sReturn = subtractLetter(bigWord, wildcard);
        }
        return sReturn;
    }

    public static String subtractLetter(String bigWord, char letter) {
        String sReturn = new String(bigWord);
        int ix = bigWord.indexOf(letter);
        if (ix >=0 ) {
            sReturn = bigWord.substring(0, ix) + bigWord.substring(ix + 1);
        }
        return sReturn;
    }

    public static String alphagram(String word) {
        char[] cc = word.toCharArray();
        Arrays.sort(cc);
        return new String(cc);
    }

    public static int countVowels(String string) {
        string = string.toLowerCase();
        int iCount = 0;
        char[] vowels = {'a','e','i','o','u'};
        Set<Character> set = new TreeSet<Character>();
        for (char vowel: vowels) {
            set.add(vowel);
        }
        for (int i = 0; i < string.length(); i++) {
            Character ch = string.charAt(i);
            if (set.contains(ch)) {
                iCount++;
            }
        }
        //System.out.println("stringstuff.countVowels:" + iCount + " vowels.");
        return iCount;
    }
    public static String replaceAll(String alphagram, char searchChar, char replacementChar, int maxTries) {
        for (int i = 0; i < maxTries; i++) {
            if (alphagram.indexOf(searchChar) >= 0) {
                alphagram = StringStuff.subtractLetter(alphagram, searchChar);
                alphagram = StringStuff.alphagram(alphagram +  replacementChar);
            }
        }
        return alphagram;
    }

    
}
