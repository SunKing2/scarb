package com.scramblelovers.scrabble;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods5Test{

    Board board;
    Dictionary dict;
    
    private static final String[][] sPlays = {
        {"pies", "8g"},  
        {"gay", "9e"},  
        {"delfs", "i7"},  
        {"slojd", "11i"},  
        {"dis", "m11"},  
        {"rotaries", "13f"},  
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
        // bug where puter gives oi 12l 24pts rather than oy 10e 29
        // I remembered the rack was O and I but I don't know the other letters
        // I'm going to skip this test, because we don't need the bot searching for every
        // single 2 letter word.
        // toy h13 for 18 points, leave:(iiiii) -34.52 = -16.52
        // joe l11 for 24 points, leave:(iiiiiy) -35.28 = -11.28
        // oi 12l for 24 points, leave:(iiiiiy) -35.28 = -11.28
        // oy 10e for 29 points, leave:(iiiii) -34.52 = -5.52
        // joey l11 for 32 points, leave:(iiiii) -34.52 = -2.52

        StaticFunctions.assertBotPlay(board, dict, "OIIIIIY", "joey l11");
    }
}
