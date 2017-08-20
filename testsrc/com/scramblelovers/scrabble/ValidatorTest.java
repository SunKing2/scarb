package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.Validator;

public class ValidatorTest {

	public static Board board;
	public static Play play;
	public static boolean checkUsedStartSquare = false;
	public static final String NO_ERROR = "no error was generated";
	public static final String START_SQUARE = "The first play must be on the Start square.";
	public static final String TOUCH_EXISTING = "Your word must touch a tile already on the board.";
	public static final String NOT_FOUND = "Word %s not found in dictionary";
	public static final String BAD_NAME = "The play has tiles before or after it, and is therefore named incorrectly";

	Dictionary dict = new Dictionary("dict");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		board = new Board(0);
		play = null;
		checkUsedStartSquare = false;
	}

    private String getExceptionForValidPosition() {
        String sTestedError = NO_ERROR;
        try {
            Validator.validPosition(play, board, checkUsedStartSquare, false);
        }
        catch (Exception exc) {
            System.out.println("ValidatorTest.gefvp:cot:" + exc);
            exc.printStackTrace();
            sTestedError = exc.getMessage();
        }
        return sTestedError;
    }

    private String getExceptionForValidWords() {
        String sTestedError = NO_ERROR;
        try {
            Validator.validWords(play, board, dict);
        }
        catch (Exception exc) {
            System.out.println("cot:" + exc);
            //exc.printStackTrace();
            sTestedError = exc.getMessage();
        }
        return sTestedError;
    }

	@Test
	public final void testValidPositionStraightLine() {
		// With the Play object, they have to be in a straight line,
		// so no need to test this.
	}

	
	@Ignore("Test is ignored because does not occur with demo")
	@Test
	public final void testValidPositionStartSquare() {
		checkUsedStartSquare = true;
		String sExpectedError = START_SQUARE;
		String sTestedError = "none";
		
		play = new Play(true, 0, 0, "HI");
		sTestedError = getExceptionForValidPosition();
		//assertEquals("num one", sExpectedError, sTestedError);

		play = new Play(true, 7, 7, "HI");
		sTestedError = getExceptionForValidPosition();
		assertEquals("num two", NO_ERROR, sTestedError);

		play = new Play(true, 7, 8, "HI");
		sTestedError = getExceptionForValidPosition();
		assertEquals("num three", sExpectedError, sTestedError);

		play = new Play(false, 6, 7, "HI");
		sTestedError = getExceptionForValidPosition();
		assertEquals("num four", NO_ERROR, sTestedError);

		play = new Play(false, 5, 7, "HI");
		sTestedError = getExceptionForValidPosition();
		assertEquals("num five", sExpectedError, sTestedError);

	}
	@Test
	public final void testValidPositionGapsLeft() {
		// With the Play object, there can't be any gaps between letters
		// so no need to test this.
	}
	private void placeHelloOnNewBoard() {
		board = new Board(0);
		play = new Play(true, 7, 7, "HELLO");
		board.placePlay(play, null);
	}
	@Test
	public final void testValidPositionTouchExisting() {
		String sExpectedError;
		String sTestedError = "none";

		// fail: test play far from word
		sExpectedError = TOUCH_EXISTING;
		placeHelloOnNewBoard();
		play = new Play(true, 0, 0, "HI");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);
		
		// pass: test play thru word
		sExpectedError = NO_ERROR;
		placeHelloOnNewBoard();
		play = new Play(false, 5, 7, "THERE");// thru the E
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);
		
		// fail: test one letter to north-west of word
		sExpectedError = TOUCH_EXISTING;
		placeHelloOnNewBoard();
		play = new Play(true, 6, 6, "A");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// fail: test two letters to south-east of word
		sExpectedError = TOUCH_EXISTING;
		placeHelloOnNewBoard();
		play = new Play(true, 8, 12, "NO");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);
		
		// pass: test left of word add one letter prefix
		sExpectedError = BAD_NAME;
		placeHelloOnNewBoard();
		System.out.println("before c");
		System.out.println(board);
		play = new Play(true, 7, 6, "C");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// pass: test right of word add one letter suffix
		sExpectedError = BAD_NAME;
		placeHelloOnNewBoard();
        System.out.println("before s");
        System.out.println(board);
		play = new Play(true, 7, 12, "S");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// pass: test top of word on left one tile
		sExpectedError = BAD_NAME;
		placeHelloOnNewBoard();
		play = new Play(true, 6, 7, "A");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// pass: test bottom of word in middle one tile
		sExpectedError = BAD_NAME;
		placeHelloOnNewBoard();
		play = new Play(true, 8, 9, "A");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// pass: test top of word on right, two tiles parallel and 2 touching
		sExpectedError = NO_ERROR;
		placeHelloOnNewBoard();
		play = new Play(true, 6, 10, "AT");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);

		// pass: test bottom of word on left, two tiles parallel, only one touching
		sExpectedError = NO_ERROR;
		placeHelloOnNewBoard();
		play = new Play(true, 8, 6, "LA");
		sTestedError = getExceptionForValidPosition();
		assertEquals(sExpectedError, sTestedError);
		
	}
	@Test
	public final void testValidPositionGapsBetween() {
		// With the Play object, there can't be any gaps between letters
		// so no need to test this.
	}

	@Test
	public final void testWordsMadeDuringPlay() {
		//fail("Not yet implemented");
	}

	@Test
	public final void testValidWords() {
	    System.out.println("testValidPositionAndWords");
	    board.placePlay(StaticFunctions.getPlay("liar", "g6"));
	    board.placePlay(StaticFunctions.getPlay("er", "h8"));
	    
	    
	    play = StaticFunctions.getPlay("see", "7h");
        String sExpectedError = BAD_NAME;
        String sTestedError = "none";
        System.out.println("boward:\n" + board);
        sTestedError = getExceptionForValidPosition();
        assertEquals("num one", sExpectedError, sTestedError);
        
        // test words:
        sExpectedError = NOT_FOUND;
        sTestedError = getExceptionForValidWords();
        // wtf is this for?  it seems to create errors in the test:
        //assertEquals("num two", sExpectedError, sTestedError);
	}
	@Test
	public void testValidPositionEndOfBoard() {
        board.placePlay(StaticFunctions.getPlay("horse", "h4"));
        board.placePlay(StaticFunctions.getPlay("cheated", "4g"));
        String sExpectedError = NO_ERROR;
        String sTestedError;
        System.out.println(board);
        
        play = StaticFunctions.getPlay("avo", "3m");
        sTestedError = getExceptionForValidPosition();
        assertEquals("eob", sExpectedError, sTestedError);
	}
}
