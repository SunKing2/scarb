package com.scramblelovers.scrabble;

//import static org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Bot;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.scrabble.Globals;
import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods0Test{

	Board board;
	Dictionary dict;
	private static final boolean verbose = true;
	private static Play play = null;
	private Bot bot;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(0);
		dict = new Dictionary("dict");
		bot = Globals.bot;
	}

	@Test
	public final void testPlayWord() {

		// set up the board with several plays:
		StaticFunctions.placePlay(board, new Play(true, 2, 2, "jaggs"));
		StaticFunctions.placePlay(board, new Play(true, 4, 3, "fe"));
		StaticFunctions.placePlay(board, new Play(true, 4, 6, "al"));
		StaticFunctions.placePlay(board, new Play(true, 5, 4, "syn"));
		
		StaticFunctions.placePlay(board, new Play(false, 2, 4, "gees"));
		StaticFunctions.placePlay(board, new Play(false, 2, 6, "snan"));

		System.out.println(board);
		// ho 2d for 16 points, leave:(gijru) -9.21 = 6.79
		// pa g9 for 7 points, leave:(ghijoru) 0.0 = 7.0
		// jig 8h for 22 points, leave:(horu) -3.75 = 18.25
		// gore 4b for 31 points, leave:(hiju) -8.4 = 22.6
		// not verified: pa g9 for 14 points, leave:(ailss) 12.67 = 26.67
        StaticFunctions.assertBotPlay(board, dict, "ghijoru", "gore 4b");
	}
	@Test
	public final void testPlayWord_Bingo_() {
		// set up the board with several plays:
		StaticFunctions.placePlay(board, new Play(false, 7, 7, "ai"));

		// pas 10f for 14 points, leave:(ails) 11.66 = 25.66
		// palais 10f for 19 points, leave:(s) 7.35 = 26.35
		// aplasias 8b for 61 points, leave:() 0.0 = 61.0
		// plasias 8i for 80 points, leave:() 0.0 = 80.0
		// aplasias 8h for 83 points, leave:() 0.0 = 83.0
        StaticFunctions.assertBotPlay(board, dict, "aailpss", "aplasias 8h");
	}
}
