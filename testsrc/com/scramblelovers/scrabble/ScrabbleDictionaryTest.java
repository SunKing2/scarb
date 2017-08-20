package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class ScrabbleDictionaryTest {

    private Dictionary dict;
    private Set<String> ioDict;
    private String dictionaryFileName = "dict";
    
    @Before
    public void setUp() throws Exception {
        dict = new Dictionary(dictionaryFileName);
        ioDict = (Set<String>)IOStuff.readFile(dictionaryFileName, 2, 15, true, false);
    }

    @Test
    public final void testWords() {
        Set<String> set = dict.getWordsAsCollection();
        
        int iExpected, iActual;
        
        //iExpected = 69653;
        //iExpected = 178691; // twl06
        iExpected = 88099;    // owl3 2017 words 2-9 letters
        iActual = set.size();
        assertEquals("#words", iExpected, iActual);
        
        // now try the raw data
        iActual = ioDict.size();
        assertEquals("#words IOStuff.readFile", iExpected, iActual);
        
        boolean containsAll = set.containsAll(ioDict);
        assertTrue("words not same", containsAll);
    }
}
