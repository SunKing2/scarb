package com.scramblelovers.scrabble;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;

public class ScrabbleDemoTest {

    ScrabbleDemo demo;
    
    @Before
    public void setUp() throws Exception {
        Board board = new Board(0);
        String dictionaryFileName = "dict";
        Dictionary dict = new Dictionary(dictionaryFileName);
        demo = new ScrabbleDemo(board, dict);
    }
    @Test 
    public void playOneGameTest() {
        int score = -1;
        try {
            score = demo.playOneGame(true);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("score:" + score);
    }
    @Test
    public void timeSeveralGames() throws Exception {
        int nGames;
        long iStartTime;
        
        // warmup
        demo.playOneGame(true);
        demo.playOneGame(true);
        
        // warmup
        iStartTime = System.currentTimeMillis();
        demo.playOneGame(true);
        demo.showElapsedTime(iStartTime);
        
        // warmup also
        iStartTime = System.currentTimeMillis();
        demo.playOneGame(true);
        demo.showElapsedTime(iStartTime);
        
        // this shows the time it takes to play nGames
        // I do it more than once so you can see if the figure is accurate
        nGames = 2;
        for (int j = 0; j < 2; j++) {
            iStartTime = System.currentTimeMillis();
            for (int i = 0; i < nGames; i++) {
                demo.playOneGame(true);
            }
            demo.showElapsedTime(iStartTime);
        }
    }
}
