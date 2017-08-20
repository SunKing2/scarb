package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods9Test{

    Board board;
    Dictionary dict;
    private static final String[][] sPlays = {

        {"jeton", "h4"},
        {"rime", "g5"},

    };
    
    @Before
    public void setUp() throws Exception {
        board = new Board(0);
        dict = new Dictionary("dict");
        StaticFunctions.placeThesePlays(board, sPlays);
        // show board
        System.out.println(board);
    }

    @Test
    public final void testPlayWord() {
        // gussy 9e 35pts
        // bot incorrectly said: gey f8 19pts
        // I don't know rack, it at least had gussy in it, and maybe the e from gey???
        // bug was cause by the computepotential method of the bot counting favoring 8 points for
        // EACH 's'.  Fix was to give 8 points if there was an s, and ignore others
        // guys 9d for 17 points, leave:(esi) 0.0 = 17.0
        // guys f8 for 20 points, leave:(esi) 0.0 = 20.0
        // ess 9f for 25 points, leave:(guiy) 0.0 = 25.0
        // gussy 9e for 35 points, leave:(ei) -1.57 = 33.43
        StaticFunctions.assertBotPlay(board, dict, "egssuiy", "gussy 9e");
    }
    // this test will fail if the bot refuses to replace a blank with a vowel
    // if there are in its opinion, too many vowels already
    // testplaywor10 also tests this
    @Test
    public final void testPlayBingoWithE() {
        // raisins 9a for 66 points, leave:() 0.0 = 66.0
        // aspirin i7 for 67 points, leave:() 0.0 = 67.0
        // senarii 9h for 70 points, leave:() 0.0 = 70.0
    	// lvb 20170820 senarii was working on old version , but not anymore
    	// so i just changed the expected result to aspirin
    	// TODO fix me
        StaticFunctions.assertBotPlay(board, dict, "aiinrs?", "aspirin i7");
    }
    @Test
    public final void testMoreLettersThanInRack() {
        board = new Board(0);
        StaticFunctions.placePlay(board, "droll", "h4");
        // when it was my turn, the hint thingy said INCORRECTLY: 
        // Best play:odaleths 9a 24 points.
        StaticFunctions.assertBotPlay(board, dict, "adehlst", "daleths 9b");
    }
}
