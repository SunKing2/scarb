package com.scramblelovers.types;
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
    public static String subtractWord(String bigWord, String smallWord) {
        String sReturn = bigWord;
        for (int i = 0; i < smallWord.length(); i++) {
            sReturn = subtractLetter(sReturn, smallWord.charAt(i));
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
    static void comb(Set<String> set, int outputLength,  String string, String in)
    {
        //System.out.println();
        //System.out.println();
        //char qt = '"';
        //System.out.println("(" + qt + string + qt + "," + qt + in + qt + ")");
        
        int iString = string.length();
        //int iIn = in.length();
        
        //System.out.println("  iString=" + iString);
        //System.out.println("      iIn=" + iIn);
        
        int iMax = iString;  // FIXME
        
        for (int i = 0; i < iMax; i++) {
            String newIn = in + string.charAt(i);
            
            //System.out.println("    newIn=" + newIn);
            
            if (newIn.length() == outputLength) {
                //System.out.println("" + newIn);
                set.add(newIn);
            }
            else {
                String newString = string.substring(i+1);
                //System.out.println("newString=" + newString);
                comb(set, outputLength, newString, newIn);
            }
        }
    }
    public static Set<String> getCombinations(String alphagram, int combinationSize) {
        String string = alphagram; 
        int iChoose = combinationSize;
        String  in = "";
        Set<String> set = new HashSet<String>();
        StringStuff.comb(set, iChoose, string, in);
        return set;
    }

    public static int countOccurencesAnyOf(String big, String anyLetters, boolean noRepeats) {
        
        int iReturn = 0;
        for (char ch: anyLetters.toCharArray()) {
            int occ = countOccurrences(big, ch);
            if (noRepeats) {
                if (occ > 0) {
                    iReturn +=1;
                }
            }
            else {
                iReturn += countOccurrences(big, ch);
            }
        }
        return iReturn;
    }
    public static boolean containsAnyOf(String big, String anyLetters) {
        boolean bReturn = false;
        char[] cc = anyLetters.toCharArray();
        for (char c: cc) {
            if (big.indexOf(c) > -1) {
                bReturn = true;
                break;
            }
        }
        return bReturn;
    }
    public static String alphagram(String word) {
        char[] cc = word.toCharArray();
        Arrays.sort(cc);
        return new String(cc);
    }
    public static String addLetter(String word, char letter) {
        // TODO Auto-generated method stub
        String sReturn = "";
        sReturn = word + letter;
        return sReturn;
    }
    public static Collection<String> addLetterToEachWord(Collection<String> words, char letter) {
        ArrayList<String> lisReturn = new ArrayList<String>();
        for (String inWord: words) {
            String sOutWord = StringStuff.addLetter(inWord, letter);
            lisReturn.add(sOutWord);
        }
        return lisReturn;
    }
    public static Collection<String> addTheseLettersToEachWord(Collection<String> words, String letters) {
        ArrayList<String> lisReturn = new ArrayList<String>();
        for (char letter: letters.toCharArray()) {
            for (String inWord: words) {
                String sOutWord = StringStuff.addLetter(inWord, letter);
                lisReturn.add(sOutWord);
            }
        }
        return lisReturn;
    }
    public static Collection<String> removeDuplicates(Collection<String> wordList) {
        Set<String> setReturn = new TreeSet<String>();
        // setReturn.addAll(wordList);  this doesn't cut it... because of case
        for (String word: wordList) {
            String outWord = word.toLowerCase().trim();
            if (outWord.length() > 0) {
                setReturn.add(outWord);
            }
        }
        return setReturn;
    }
    public static List<Character> StringAsList(String sRack) {
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < sRack.length(); i++) {
            list.add(sRack.charAt(i));
        }
        return null;
    }
    
    public static Set<String> combPerm(String string, int maxPrefixSize) {
        Set<String> set = new HashSet<String>();
        int iIn = string.length();
        if (iIn == 2) {
            char[] cc = string.toCharArray();
            set.add(string);
            set.add("" + cc[1] + cc[0]);
            set.add("" + cc[0]);
            set.add("" + cc[1]);
        }
        else {
            // for each letter, add that letter and all the perms of the remaining letters to the returning set
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                String prefix = "" + ch;
                String remainingLetters = StringStuff.subtractLetter(string, ch);
                Set<String> setRemainingCombinations = combPerm(remainingLetters, 99);
                Collection<String> colRemainingCombinations = 
                    StringStuff.prefixAll(setRemainingCombinations, prefix, maxPrefixSize);
                set.addAll(colRemainingCombinations);
                // now just add the remaining combinations, sans prefix
                set.addAll(setRemainingCombinations);
            }
        }
        return set;
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
    public static boolean containsAnyOf(String word, String...substrings) {
        boolean bReturn = false;
        for (String s: substrings) {
            if (word.indexOf(s) >= 0) {
                bReturn = true;
                break;
            }
        }
        return bReturn;
    }
    public static int countOccurrences(String word, char ch) {
        int iReturn = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) iReturn++;
        }
        return iReturn;
    }
    public static List<String> prefixAll(Collection<String> set, String prefix, int maxLengthOfFinalWords) {
        List<String> list = new ArrayList<String>();
        for (String word: set) {
            String finalWord = prefix + word;
            int iLen = finalWord.length();
            if (iLen <= maxLengthOfFinalWords) {
                list.add(prefix + word);
            }
        }
        return list;
    }
}
