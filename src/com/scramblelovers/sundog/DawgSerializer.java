package com.scramblelovers.sundog;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import com.scramblelovers.scrabble.IOStuff;

public class DawgSerializer {
    public static void main(String[] args) throws IOException {
        String fileName = "sundawg.txt";

        Dawg dawg = makeDawgFromScratch();
        writeDawg(fileName, dawg);
        
        Dawg d2 = readDawg(fileName);
        System.out.println("read in dawg with nodes:" + d2.getNodeCount());
        System.out.println("program done.");
    }
    public static void writeDawg(String fileName, Dawg dawg) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(dawg);
        oos.close();
        System.out.println("dawg successfully written to:" + fileName);
    }
    public static Dawg readDawg(String fileName) throws IOException {
        FileInputStream fos = new FileInputStream(fileName);
        ObjectInputStream oos = new ObjectInputStream(fos);

        Dawg dawg = null;
        try {
            dawg = (Dawg) oos.readObject();
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        oos.close();
        return dawg;
    }
    public static Dawg makeDawgFromScratch() {
        Set<String> ww = new TreeSet<String>();
        Set<String> listTo4 = (Set<String>) IOStuff.readFile("dict", 2, 15, true, false);
        ww.addAll(listTo4);
        // TODO lvb 20170818 this next line i commented out.  why is it here?
        //ww.addAll(wds);
        return new Dawg(ww);
    }
}
