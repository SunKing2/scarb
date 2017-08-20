package com.scramblelovers.scrabble;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.Globals;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.StaticBotMethods0BaseClass;
import com.scramblelovers.scrabble.StaticFunctions;
import com.scramblelovers.sundog.*;

public class DawgTestLeftRight extends StaticBotMethods0BaseClass {
    static Dawg dawg;
    DawgStuff bot;
    private Node rootNode;
    private Dictionary dict;
    private static final String[][] sPlays = {
            {"anoa", "g6"},
            {"tide", "f6"},
            {"eaved", "10b"},
            {"gunmen", "h1"},
            {"slojd", "a6"},
            {"few", "e9"},
            {"vodun", "d10"},
            {"aboma", "c10"},
            {"miaou", "4h"},
            {"yeti", "b12"},
            {"satori", "15d"},
            {"uteri", "b2"},
//            {"torero", "c2"},
//            {"hate", "d1"},
//            {"bit", "5j"},
//            {"cig", "3l"},
//            {"vase", "o1"},
//            {"grew", "n3"},
//            {"chunk", "2f"},
//            {"razer", "10h"},
//            {"fley", "k8"},
//            {"qi", "m2"},
//            {"fling", "8k"},
//            {"silex", "12k"},
    };
    
    // this is not all of them, but at least these must be in the found words
    public static String[] firstRowWords = {
        "aa",
        "aah",
        "aal",
        "ab",
        "abos",
        "ae",
        "ah",
        "al",
        "alb",
        "alp",
        "ape",
        "apos",
        "be",
        "bel",
        "boos",
        "bos",
        "eh",
        "el",
        "epos",
        "hae",
        "he",
        "hep",
        "hoe",
        "hos",
        "loos",
        "obe",
        "oe",
        "ope",
        "pe",
        "pea",
        "peh",
        "poos",
    };
    public static List<String> lisFirstRowWords = Arrays.asList(firstRowWords);
    public Set<String> setFirstRowWords = new TreeSet<String>();
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        dawg = StaticFunctions.readDawgFromFile();
        bot = StaticFunctions.makeDawgStuff();
        rootNode = dawg.getRootNode();
        dict = new Dictionary("dict");
        setFirstRowWords.addAll(lisFirstRowWords);
    }
    
    @Test
    public final void testMo() {
        Node node = dawg.getNodeAfterPrefix("MO");
        String sResult = node.getLettersAsString();
        System.out.println("words after MO:" + sResult);
        assertEquals("abcdfghijklmnopqrstuvwxz", sResult);
    }
    
    @Test
    public final void testLeftPart() {
        String partialWord = "";
        int limit = 3;
        int row = 0;
        int column = 4;

        // get first row squares
        Row rowObject = new Row(row, 15);
        Square anchor = rowObject.getRowSquares().get(column);
        anchor.setCrossCheckLetters("m");
        System.out.println("anchor=" + anchor);
        String rack = StringStuff.alphagram("CYMESOL");

        bot.leftPart(row, column, rack, partialWord, rootNode, limit, anchor);
    }
    @Test 
    // get the real first row of the board, and look only at its first anchor
    public final void testLeftPart02() {
        
        // let's place , um arbitrarily a few plays
        StaticFunctions.placeThesePlays(board, sPlays);
        
        String partialWord = "";
        int limit = 3;
        int row = 0;
        Row rowObject = new Row(board, dict, 0);
        List<Square> anchors = rowObject.getAnchors();
        System.out.println("row has these anchors:" + anchors.size());
        
        for (Square anchor: anchors) {
            System.out.println("anchor=" + anchor);
            String rack = StringStuff.alphagram("adeirst");
    
            if (anchor.hasCrossCheckLetters()) {
                bot.leftPart(row, anchor.getColumn(), rack, partialWord, rootNode, limit, anchor);
            }
            else {
                //System.out.println("dltr, no crosscheck letters here");
            }
        }
    }
    
    @Test
    public final void testLeftPart04() {

        // get first row squares
        Row row = new Row(0, 15);
        
        StaticFunctions.setCrossCheckLetters(row, 1, "abdfhmnoprwy");
        StaticFunctions.setLetter(row, 2, 'e');
        StaticFunctions.setCrossCheckLetters(row, 3, "abfghklmprtvwz");
        StaticFunctions.setLetter(row, 6, 'o');
        StaticFunctions.setLetter(row, 7, 's');
        StaticFunctions.setLetter(row, 10, 'e');
        StaticFunctions.setLetter(row, 12, 'a');
        StaticFunctions.setCrossCheckLetters(row, 14, "bcdghlnpstz");

        StaticFunctions.addAnchor(row, 1);
        StaticFunctions.addAnchor(row, 2);
        StaticFunctions.addAnchor(row, 3);
        StaticFunctions.addAnchor(row, 5);
        StaticFunctions.addAnchor(row, 6);
        StaticFunctions.addAnchor(row, 7);
        StaticFunctions.addAnchor(row, 9);
        StaticFunctions.addAnchor(row, 10);
        StaticFunctions.addAnchor(row, 12);
        
        String rack = StringStuff.alphagram("abehlop");
        row.spew(bot, dawg, rack, true);
        
        System.out.println("here are the limits and anchors:");
        for (Square square: row.getRowSquares()) {
            System.out.println(square);
        }
        
        // just assert it contains a subset of words above plus correct # found
        assertContainsAll();
    }

    private void assertContainsAll() {
        Set<Play> plays = bot.getLegalPlays(0);
        Set<String> setMoves = new TreeSet<String>();
        for (Play play : plays) {
            setMoves.add(play.getWord());
        }
        
        for (String word: setFirstRowWords) {
            boolean bContain = setMoves.contains(word);
            if (!bContain) System.out.println("missing:" + word);
        }
            
        boolean bContainsAll = setMoves.containsAll(setFirstRowWords);
        System.out.println("bContainsAll:" + bContainsAll);
        assertTrue("first row containsAll expected words", bContainsAll);
        
        for (String word: setMoves) {
            boolean bContain = setFirstRowWords.contains(word);
            if (!bContain) System.out.println("x missing:" + word);
        }
        
        // lvb 20170819 changed 71 to 76 probably due to owl3 word list
        assertEquals("not nuff words found", 76, setMoves.size());
    }
    @Test
    public final void testLeftPart05() {
        String[][] sPlays = {
                {"eta", "2b"},
                {"de", "2g"},
                {"selections", "3d"},
                {"one", "4m"},
                {"et", "c1"},
                {"as", "d2"},
                {"ode", "g1"},
                {"sec", "h1"},
                {"ego", "k1"},
                {"also", "m1"},
                {"one", "o2"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays); 
        Row row = new Row(board, dict, 0);
        String rack = StringStuff.alphagram("abehlop");
        row.spew(bot, dawg, rack, true);
        for (int i = 0; i < Globals.TILES_WIDE; i++) {
            Square square = row.getRowSquares().get(i);
            System.out.println(square);
        }
        // just assert it contains a subset of words above plus correct # found
        assertContainsAll();
    }
}