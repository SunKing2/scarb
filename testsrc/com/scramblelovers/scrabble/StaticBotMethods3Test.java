package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods3Test{

    Board board;
    Dictionary dict;
    private static final String[][] sPlays = {
        {"kafir", "h4"},  
        {"fazes", "6h"},
        {"boo", "5k"},
        {"zoril", "j6"},
        {"podia", "g7"},
        {"racing", "8j"},
        {"gleet", "h11"},
        {"caged", "l8"},
        {"troth", "n2"},
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
        // both tardiest 15a and gradient O8 score 80 points which kinda makes
        // consistent testing tough.
        // tied o1 for 30 points, leave:(ar?) 0.0 = 30.0
        // iterated 15c for 60 points, leave:() 0.0 = 60.0
        // diptera f8 for 71 points, leave:() 0.0 = 71.0
        // tardiest 15a for 80 points, leave:(?) 0.0 = 80.0
    	// gradient o8 for 80 points, leave:() 0.0 = 80.0
        StaticFunctions.assertBotPlay(board, dict, "adeitr ", "gradient o8");
    }
    
    @Test
    public final void testPlayWord2() {
        StaticFunctions.placePlay(board, "titrated", "15h");
        StaticFunctions.placePlay(board, "qi", "10f");
        System.out.println(board);
        // yew k11 for 31 points, leave:(inr) 3.55 = 34.55
        // yew 14l for 35 points, leave:(inr) 3.55 = 38.55
        // wye o4 for 37 points, leave:(inr) 3.55 = 40.55
        StaticFunctions.assertBotPlay(board, dict, "EINRWY", "wye o4");
    }
    
    @Test
    public final void testPlayWord3() {
        
        String[][] sMorePlays =
        {
            {"titrated", "15h"},  
            {"qi", "10f"},
            {"yew", "14l"},
            {"cloy", "4l"},
        };
        StaticFunctions.placeThesePlays(board, sMorePlays);
        // block any play at 13a
        StaticFunctions.placePlay(board, "xxx", "a13");
        System.out.println(board);
        // lo 13m for 16 points, leave:(einr?) 0.0 = 16.0
        // xi o1 for 29 points, leave:(elnor) 2.9 = 31.9
        // eloiners 13c for 70 points, leave:(?) 0.0 = 70.0
        // hornlike 4b for 80 points, leave:() 0.0 = 80.0
        StaticFunctions.assertBotPlay(board, dict, "eilnor?", "hornlike 4b");
    }
    @Test
    public void testPlayWord4() {
        String[][] sMorePlays =
        {
            {"titrated", "15h"},  
            {"qi", "10f"},
            {"yew", "14l"},
            {"cloy", "4l"},
            {"eloiners", "13c"},
            {"thir", "14b"},
        };
        StaticFunctions.placeThesePlays(board, sMorePlays);
        System.out.println(board);
        // this test was created because the bot had said ganevs O8 14pts during a game
        // apparently it's fixed now.
        // aves n9 for 15 points, leave:(en) 2.6 = 17.6
        // nave n8 for 15 points, leave:(ens) 12.88 = 27.88
        // van 13m for 30 points, leave:(ees) 6.22 = 36.22
        // vee 13m for 30 points, leave:(ans) 9.98 = 39.98
        // this reveals a bug in the extendRight where I used to generate a play if square was null
        // It worked fine when I did that, but when I updated it 20110309, it started failing, but
        // only here:
        System.out.println(board);
        StaticFunctions.assertBotPlay(board, dict, "AEENSV", "vee 13m");
    }
}
