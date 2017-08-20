package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;

public class Board2Test {

	
	Board board; 
	@Before
	public void setUp() throws Exception {
		board = new Board(0);
	}

	@Test
	public void testGetPlays() {
		StaticFunctions.placePlay(board, "acne", "n9");
		StaticFunctions.placePlay(board, "foin", "o12");
		Play pNew = StaticFunctions.getPlay("dew", "m12");
		//System.out.println("b2t results:" );
		String s1 = "def";
		String s2 = "dew";
		int iRight = 0;
		for (Play p: board.getPlays(pNew)) {
			System.out.println(p);
			String word = p.getWord();
			if (word.equals(s1) || word.equals(s2)) {
				iRight++;
			}
		}
		assertEquals("number of correct words in play", 2, iRight);
	}
	@Test
	public void testGetPlays2() {
		StaticFunctions.placePlay(board, "ai", "h7");
		StaticFunctions.placePlay(board, "xi", "8m");
		StaticFunctions.placePlay(board, "fen", "9l");
		//Play pNew = new Play("l9", "feijoa");
		System.out.println(board);
	}
}
