package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.ScrabbleScorer;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods7Test{

    Board board;
    Dictionary dict;
    private static final String[][] sPlays = {
        {"anoa", "g6"},
        {"tide", "f6"},
        {"eaved", "10b"},
        {"gunmen", "h1"},
        {"slojd", "a6"},
        {"few", "e9"},
        {"inbound", "3g"},
        

//        {"anoa", "g6"},
//        {"tide", "f6"},
//        {"eaved", "10b"},
//        {"gunmen", "h1"},
//        {"slojd", "a6"},
//        {"few", "e9"},
//        {"vodun", "d10"},
//        {"aboma", "c10"},
//        {"miaou", "4h"},
//        {"yeti", "b12"},
//        {"satori", "15d"},
//        {"uteri", "b2"},
//        {"torero", "c2"},
//        {"hate", "d1"},
//        {"bit", "5j"},
//        {"cig", "3l"},
//        {"vase", "o1"},
//        {"grew", "n3"},
//        {"chunk", "2f"},
//        {"razer", "10h"},
//        {"fley", "k8"},
//        {"qi", "m2"},
//        {"fling", "8k"},
//        {"silex", "12k"},
//        
//        
//        {"anoa", "g6"},
//        {"tide", "f6"},
//        {"eaved", "10b"},
//        {"gunmen", "h1"},
//        {"slojd", "a6"},
//        {"few", "e9"},
//        {"inbound", "3g"},
//        {"jam", "9a"},
//        {"vau", "d10"},
//        {"toe", "4k"},
//        {"rooty", "2k"},
//        {"ria", "1m"},
//        {"bets", "i3"},
//        {"euro", "b3"},
//        {"airn", "a1"},
//        {"act", "c2"},
//        {"shogi", "d4"},
//        {"nice", "10h"},
//        {"five", "11k"},
//        {"twerp", "o8"},
//        {"haver", "m9"},
//        {"ye", "n10"},
//        {"in", "l11"},
//        {"ark", "2c"},
//        {"sib", "i9"},
    };
    
    private static final String[] racks = {
        
        "aeoaeni",
        "eeideet",
        "eeemavd",
        "emnngus",
        "sljdfon",
        "fnwadio",
        "nadiobu",
        "aiboeim",
        "ieiooua",
        "eiortyt",
        "ortaisi",
        "i eteuo",
        "eorora ",
        "ataiech",
        "aicgsbt",
        "acgscin",
        "ascnvew",
        "cnwfhre",
        "cnfhpzk",
        "fpzraer",
        "fpqylng",
        "pqngsll",
        "pngslli",
        "pslipxe",
//        
//        
//        "aeoaeni",
//        "eeideet", 
//        "eeemavd",
//        "emnngus", 
//        "sljdfon", 
//        "fnwadio", 
//        "nadiobu", 
//        "aoeimoo", 
//        "oeiooua", 
//        "oeioort", 
//        "iooryta", 
//        "iaisi e", 
//        "isieteu", 
//        "iieuoro", 
//        "iiora t", 
//        "iotaiec", 
//        "ioiehgs", 
//        "iebtcin", 
//        "btivewf", 
//        "btwhrep", 
//        "bhzkrae", 
//        "bzkrqyl", 
//        "bzkrqln", 
//        "bzkrqlg", 
//        "bzqlgsl", 
    };
    
    @Before
    public void setUp() throws Exception {
        board = new Board(0);
        dict = new Dictionary("dict");
        StaticFunctions.placePlay(board, "er", "h8");
    }

    @Test
    // expect vodun d10 23 points
    // error here would be due to scrabblerbot's computePotential routine not working correctly
    public final void testComputePotential() {
        int i = 6;
        // abound 10h for 14 points, leave:(i) -1.49 = 12.51
        // vodun d10 for 23 points, leave:(aib) 0.0 = 23.0
        // inbound 3g for 28 points, leave:(a) -0.4 = 27.6
        assertCorrectPlay(i);
    }

    private void assertCorrectPlay(int iPlaced) {
        StaticFunctions.placeThesePlays(board, sPlays, iPlaced);
        // show board
        System.out.println(board);
        
        // by strange coincidence, the NEXT rack is the same index # as the number of plays placed
        String currentRack = racks[iPlaced];
        String currentWord = sPlays[iPlaced][0];
        String currentPosition = sPlays[iPlaced][1];

        Play playExpected = StaticFunctions.getPlay(currentWord, currentPosition);
        int iExpectedScore = ScrabbleScorer.getScore(board, playExpected);
        System.out.println("Rack:" + currentRack);
        System.out.println("expecting:" + currentWord + ' ' + currentPosition + ' ' + iExpectedScore + " points.");
        // for some reason pre-feb 22 this produced VODUN D10 23 194
        // vodun d10 for 23 points, leave:(aib) 0.0 = 23.0
        StaticFunctions.assertBotPlay(board, dict, currentRack, currentWord + ' ' + currentPosition);
    }
}
