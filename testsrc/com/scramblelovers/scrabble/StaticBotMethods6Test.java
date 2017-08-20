package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods6Test{

    Board board;
    Dictionary dict;
    
    private static final String[][] sPlays = {
        {"mewler", "e3"},  
        {"curie", "h4"},  
        {"ceric", "4d"},  
        {"poop", "b7"},  
        {"baits", "g7"},  
        {"zaxes", "11c"},  
        {"woad", "12a"},  
        {"goes", "13c"},  
        {"home", "8a"},  
        {"aft", "10e"},  
        {"nos", "14a"},  
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
        // apo 7a 16pts          (agkot)
        // talk 6c for 18 points, leave:(agoo) -11.28 = 6.72
        // oka 14f for 13 points, leave:(agot) -3.52 = 9.48
        // kor f2 for 21 points, leave:(aagot) -9.67 = 11.33
        // wonk a12 for 33 points, leave:(aagot) -9.67 = 23.33
        // karoo f2 for 34 points, leave:(agt) -1.92 = 32.08
        StaticFunctions.assertBotPlay(board, dict, "AAGKOOT", "karoo f2");
    }
}
