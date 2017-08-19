package com.scramblelovers.io;

import java.util.*;
import java.io.*;

public class IOStuff {
    
    // returns a list of Strings, one for each line of a file
    public static List<String> readFile(String fileName) {
        return readFile(fileName, 0, 512);
    }
    // asASet = true returns a TreeSet (which is a sorted list of unique words)
    // asASet = false returns an ArrayList (each record as it is read, repeats allowed)
    public static Collection<String> readFile(String fileName, 
            int minimumLineLength, int maximumLineLength, boolean asASet, boolean toUppercase) {
        Collection<String> col;
        if (asASet) {
            col = new TreeSet<String>();
        }
        else {
            col = new ArrayList<String>();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String sLine = null;
            while ((sLine = in.readLine()) != null) {
                int iLineLength = sLine.length();
                if (iLineLength >= minimumLineLength &&
                    iLineLength <= maximumLineLength) {
                    if (toUppercase) {
                        col.add(sLine.toUpperCase());
                    }
                    else {
                        col.add(sLine.toLowerCase());
                    }
                }
            }
        }
        catch (Exception exc) {System.out.println("readfile:" + exc); }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }        
        System.out.println("read file" + fileName + " " + col.size() + " records read.");
        return col;
    }
    
    // returns a list of Strings, one for each line of a file
    public static List<String> readFile(String fileName, int minimumLineLength, int maximumLineLength) {
        return (List<String>)readFile(fileName, minimumLineLength, maximumLineLength, false, false);
    }
    
//    public static Map<String, Double> getSuperleaves(String file) {
//        return getSuperleaves(file, false);
//    } 
//  
    // returns a map of all alphagrams and their leaves
    // key: the alphagram
    // value: the leave for this alphagram
    public static Map<String, Double> xgetSuperleaves(String file, boolean includeBlanks) {
        Map<String, Double> map = new HashMap<String, Double>();
        int iRead = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String sLine = null;
            while ((sLine = in.readLine()) != null) {
                sLine = sLine.toLowerCase();
                iRead++;
                if (sLine.length() < 6) {
                    continue;
                }
                //if (sLine.charAt(5) != ' ' && sLine.charAt(2) != ' ') continue;
                int ixOfQm = sLine.indexOf('?');
                if (ixOfQm > -1 && !includeBlanks) continue;
                int ix = sLine.indexOf(' ');
                String wd = sLine.substring(0, ix);
                //System.out.println("wd:" + wd);
                Double value = new Double(sLine.substring(ix));
                
                map.put(wd, value);
            }
        }
        catch (Exception exc) {System.out.println("readfile:" + exc); }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }        
        System.out.println("IOStuff.getSuperleaves read superleaves:" + iRead);
        System.out.println("IOStuff.getSuperleaves map size in read:" + map.size());
        return map;
    }
}
