package com.scramblelovers.scrabble;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.ScrabbleScorer;

public class ScrabbleScorerTest {
	
	Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(0);
	}

	@Test
	public void testComputePoints() {
        StaticFunctions.placePlay(board, new Play(false, 7, 7, "ai"));          // AI     H8
        StaticFunctions.placePlay(board, new Play(true , 7, 7, "aplasias"));    // ALASIS 8H
        StaticFunctions.placePlay(board, new Play(false, 3,14, "foxes"));       // FOXES  4D
        
	    System.out.println("occupied 9 7:" + board.isOccupied(9, 7));
		Play play = new Play(true, 9, 4, "cyan");					// CYAN  10e
		System.out.println(board);
		int pt = ScrabbleScorer.getScore(board, play);
        System.out.println("occupied 9 7:" + board.isOccupied(9, 7));
		assertEquals("cyan play points", 20, pt);
	}
	
    @Test
    public void testOneBlankWithZ() {
        assertPlayGetsPoints("blank z", " eatins", "zeatins", "a1", 71);
    }
    @Test
    public void testOneBlankWithE() {
        assertPlayGetsPoints("blank e", "ainstz ", "zeatins", "a1", 98);
    }

    @Test
    public void testOneBlank3LettersOnStart() {
        assertPlayGetsPoints("blank g on start", "abcdig ", "igg", "8g", 6);
    }
    @Test
    public void testOneBlank3LettersOnTls() {
        // we don't actually know where the player placed the blank, but we'll have to assume
        // that the blank was not placed on the bonus square, otherwise it'll count less
        assertPlayGetsPoints("blank g on tls", "abcdig ", "igg", "10a", 7);
    }
    @Test
    public void testAppendToOnboardLetter() {
        board.placePlay(StaticFunctions.getPlay("quad", "l2"));
        assertPlayGetsPoints("q+ua l2", "aeilnru", "qua", "2l", 24);
    }
    @Test
    public void testPrependAllButOne() {
        board.placePlay(StaticFunctions.getPlay("tardy", "11g"));
        assertPlayGetsPoints("levanted", "levante", "levanted", "j4", 72);
    }
    
    
    private void assertPlayGetsPoints(String msg, String rack, String sWord, String sPosition,
            int scoreExpected) {
        Play play = StaticFunctions.getPlay(sWord, sPosition);
        int scoreActual = ScrabbleScorer.getScore(board, play, rack);
        assertEquals(msg, scoreExpected, scoreActual);
    }
    @Test
    public void testBlankOnDWSWithCrossplay() {
        board.placePlay(StaticFunctions.getPlay("folia", "12a"));
        System.out.println(board);
        assertPlayGetsPoints("folia", "siyotl ", "mos", "11e", 6);
    }
}
