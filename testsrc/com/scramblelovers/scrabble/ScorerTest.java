package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.ScrabbleScorer;

public class ScorerTest {

    Board board;
	@Before
	public void setUp() throws Exception {
	    board = new Board(0);
	}

	@Test
	// It seems weird that I have to mark tiles as owned after "place-ing" them, but 
	// read Board.placePlay notes.
	public final void testComputePoints() {
	    // nb:removed setOwned feb21/22
	    board.placePlay(new Play(true, 7, 6, "were"), null);

        String word = "zapateo";
		int row = 2;
		int column = 7;
		boolean horizontal = false;
		Play play = new Play(horizontal, row, column, word);
		System.out.println("testComputePoints play=" + play);

		int points = ScrabbleScorer.getScore(board, play);
		System.out.println("test points:" + points);
		System.out.println(board);
		assertEquals(19, points);
	}

	@Test
	// bug shows up that doubles the horizontal play but
	// does not double the vertical play (or vice versa)
	public final void testComputePointsDLSDoubled() {
		Play play = new Play (true, 7, 7, "satinet");
		System.out.println(board);
		int points = ScrabbleScorer.getScore(board, play);
		assertEquals(66, points);
	}
	@Test
	// bug where bingo on first word is wrong
	public final void testComputePointsBingoFirstWord() {
		board.placePlay(new Play(true, 7, 6, "er"), null);
		Play play = new Play(true, 6, 4, "cur");
		int points = ScrabbleScorer.getScore(board, play);
		System.out.println("test points:" + points);
		assertEquals(9, points);
	}
}
