package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods8Test{

    Board board;
    Dictionary dict;
    private static final String[][] sPlays = {

        {"bruited", "h4"},
        {"hag", "g9"},
        {"bikie", "4h"},
        {"inti", "5j"},
        {"areae", "3k"},
        {"urn", "6h"},
        {"feet", "o1"},
        {"geum", "7j"},
        {"eve", "8m"},
        {"vendees", "n8"},
        {"qadi", "10f"},
        {"aery", "m10"},
        {"fido", "l11"},
        {"mewls", "15h"},

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
        // bot incorrectly said: dig 11e 21pts
        // dig 11e for 21 points, leave:(nooot) -12.12 = 8.88
        // din o13 for 23 points, leave:(ooot) -13.67 = 9.33
        // too 14f for 9 points, leave:(dino) 2.75 = 11.75
        // don o13 for 23 points, leave:(ioot) -9.01 = 13.99
        // dot o13 for 23 points, leave:(inoo) -7.15 = 15.85
        // ono 14h for 18 points, leave:(diot) -0.62 = 17.38
        String rack = "dinooot";
        StaticFunctions.assertBotPlay(board, dict, rack, "ono 14h");
    }
}
