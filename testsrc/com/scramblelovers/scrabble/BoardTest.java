package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.Tile;
import java.util.*;
public class BoardTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testGetPlays() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testMakeBoard() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testPlacePlayPlayCellArrayArray() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testGetInlinePlayTo() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testGetSuffix() {
		//fail("Not yet implemented");
	}
	@Test
	public final void testAsLetters1() {
		Board board = new Board(0);
		Play play = new Play(true, 7, 7, "hello");
		board.placePlay(play, null);

		Tile[] let = assertPlayLetters(board, 4, false, 7, 7, "honey");
		assertEquals('o', let[0].getFaceValueChar());
		assertEquals('y', let[3].getFaceValueChar());
		int iRow = let[3].getRow();
		int iColumn = let[3].getColumn();
		assertEquals(11, iRow);
		assertEquals(7, iColumn);
	}
	@Test
	public final void testAsLetters2() {
		Board board = new Board(0);
		Play play = new Play(true, 7, 7, "hello");
		board.placePlay(play, null);
		Tile[] let = assertPlayLetters(board, 2, true, 7, 7, "helloes");
		assertEquals('e', let[0].getFaceValueChar());
		assertEquals('s', let[1].getFaceValueChar());
		int iRow = let[1].getRow();
		int iColumn = let[1].getColumn();
		assertEquals(7, iRow);
		assertEquals(13, iColumn);
	}

	private Tile[] assertPlayLetters(Board board, int expectedNewLettersOnBoard, 
			boolean horizontal, int row, int column, String word) {
		Play play2 = new Play(horizontal,  row, column, word);
	    Collection<Tile> col = board.asLetters(play2);
		Tile[] let = new Tile[col.size()];
		col.toArray(let);
		assertEquals(expectedNewLettersOnBoard, let.length);
	    return let;
    }

	@Test
	public final void testAsLetters3() {
		Board board = new Board(0);
		Play play = new Play(true, 7, 7, "hello");
		board.placePlay(play, null);
		Tile[] let = assertPlayLetters(board, 1, false, 6, 8, "be");
		assertEquals('b', let[0].getFaceValueChar());
		int iRow = let[0].getRow();
		int iColumn = let[0].getColumn();
		assertEquals(6, iRow);
		assertEquals(8, iColumn);
	}

	@Test
	public final void testAsLettersParallelPlay() {
		Board board = new Board(0);
		Play play = new Play(true, 7, 7, "hasp");
		board.placePlay(play, null);
		Tile[] let = assertPlayLetters(board, 3, true, 6, 8, "piu");
		assertEquals('p', let[0].getFaceValueChar());
		assertEquals('u', let[2].getFaceValueChar());
		int iRow = let[2].getRow();
		int iColumn = let[2].getColumn();
		assertEquals(6, iRow);
		assertEquals(10, iColumn);
		
		iRow = let[0].getRow();
		iColumn = let[0].getColumn();
		assertEquals(6, iRow);
		assertEquals(8, iColumn);
	}
	@Test
	public final void testPlacePlayPlay() {
		Board board = new Board(0);
		board.placePlay(new Play(false, 7, 7, "ai"), null);
		boolean startSquareOccupied = board.isOccupied(7, 7);
		assertTrue("after placing first play, start square shud be occupied", startSquareOccupied);
	}
}
