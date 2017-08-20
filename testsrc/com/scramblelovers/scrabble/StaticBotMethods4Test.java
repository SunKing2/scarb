package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods4Test{

    Board board;
    Dictionary dict;
    
    private static final String[][] sPlays = {
        {"virls", "h4"},  
        {"xeric", "6f"},
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
        // bug where puter gives 'points < 6' rather than fit 5g for 13 pts
        // fit 5g for 13 points, leave:(cddnt) -13.26 = -0.26
        System.out.println(board);
        StaticFunctions.assertBotPlay(board, dict, "CDDFNTT", "fit 5g");
    }
    
    @Test
    public final void testPlayWord2() {
        String[][] sMorePlays =
        {
            {"fit", "5g"},  
            {"ell", "7g"},
            {"cunts", "8d"},
            {"neg", "f8"},
        };
        StaticFunctions.placeThesePlays(board, sMorePlays);
        System.out.println(board);
        // coky   d8 13pts (ddtt lv=-16.11)
        // toddy  8k 33pts?
        // dogy 10d for 9 points, leave:(dktt) -13.89 = -4.89
        // yock d6 for 13 points, leave:(ddtt) -16.11 = -3.11
        // odd g10 for 8 points, leave:(ktty) -8.9 = -0.9
        // dodgy 10c for 11 points, leave:(ktt) -10.54 = 0.46
        // dock d6 for 11 points, leave:(dtty) -9.74 = 1.26
        // tock d6 for 10 points, leave:(ddty) -8.73 = 1.27
        StaticFunctions.assertBotPlay(board, dict, "ddkotty", "tock d6");
    }
}
