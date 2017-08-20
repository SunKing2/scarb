package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTransposeTest {

    private Board board = new Board(0);
    @Before
    public void setUp() throws Exception {
        board.placePlay(StaticFunctions.getPlay("seat", "8h"));
        board.placePlay(StaticFunctions.getPlay("spurn", "h8"), null);
    }

    @Test
    public void testTranspose() {
        Board bt = board.transpose();
        assertBoardAndTransposedBoardHasChar(bt,  7,  8, 'e');
        assertBoardAndTransposedBoardHasChar(bt,  7, 10, 't');
        assertBoardAndTransposedBoardHasChar(bt,  8,  7, 'p');
        assertBoardAndTransposedBoardHasChar(bt, 11,  7, 'n');
    }

    private void assertBoardAndTransposedBoardHasChar(Board bt, int row,
            int column, char expectedChar) {
        assertTile(board, row, column, expectedChar);
        assertTile(bt, column, row, expectedChar);
    }
    
    private void assertTile(Board board, int row, int column, char expectedChar) {
        Tile tile = board.getTileAt(row, column);
        assertNotNull("tile at " + row + " " + column + " is null", tile);
        char charActual = tile.getFaceValueChar();
        assertEquals("wrong char found:", expectedChar, charActual);
    }
}
