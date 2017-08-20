package com.scramblelovers.scrabble;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StaticBotMethodsExceptionsTest {

	Board board;
	Dictionary dict;
	private final boolean verbose = true;
	private Play play = null;
	private Bot bot;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(0);
		dict = new Dictionary("dict");
		bot = Globals.bot;
		// set up the board with several plays:
	}

	@Test
	public final void testPlayWordNoWordsFormable() {
		StaticFunctions.placePlay(board, "cwm", "h7");
		String sRack = "gfjkvvw";
		String sExpectedExceptionKey = "noPlay";
		assertGetBotPlayGeneratesException(board, dict, sRack, sExpectedExceptionKey);
	}
	@Test
	public final void testPlayLowPoints() {
        StaticFunctions.placePlay(board, "up", "h7");
        System.out.println(board);
		String sRack = "biiuuuv";
		String sExpectedExceptionKey = "noGoodPlay";
		assertGetBotPlayGeneratesException(board, dict, sRack, sExpectedExceptionKey);
	}
    @Test
    public final void testPlayWord01() {
        
        String[][] sPlays = {
                {"er", "h8"},
                {"butling", "10b"},
                {"tenorite", "d4"},
                {"angulose", "6c"},
                {"aurae", "9f"},
                {"jews", "12a"},
                {"ipomoea", "7g"},
                {"hang", "8l"},
                {"brrr", "8a"},
                {"dicty", "10j"},
                {"oho", "13a"},
                {"ye", "5c"},
                {"smartie", "14c"},
                {"fix", "h13"},
                {"coted", "15a"},
                {"eking", "e2"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // inn k10 for 6 points, leave:(spqvl) 0.0 = 6.0
        // lev 2d for 14 points, leave:(spqnn) 0.0 = 14.0 hmmm it says lev 2d -3.879999999999999
        // you could argue that this should throw a noGoodPlay exception
        // but that is up to the particular bot.  Superbot throws it
        //StaticFunctions.assertBotPlay(board, dict, "spqvlnn", "lev 2d");
        String sRack = "spqvlnn";
        String sExpectedExceptionKey = "noGoodPlay";
        assertGetBotPlayGeneratesException(board, dict, sRack, sExpectedExceptionKey);
    }

	//============no tests below here============
	// sExpectedKey is not the exceptions's message, 
	// but a key in the [localMessageBundle] message file 
	//   which contains the exception's message
	public void assertGetBotPlayGeneratesException(
			Board board, Dictionary dict,
			String sRack,
            String sExpectedExceptionKey) {
	    String sException = "";
	    boolean throwPhonyExceptions = true;
	    // this is junit pattern of determining correct exception message
		try {
			play = bot.getBotPlay(board, dict, sRack, verbose, throwPhonyExceptions);
			System.out.println("StaticBotMethodsExceptionTest.agpge: Unexpected Play:" + play);
	        System.out.println(StaticFunctions.getPlayStats(board, sRack, play, "bot generated"));
		}
		catch (Exception exc) {
			System.out.println("StaticBotMethodsExceptionTest.assetGetBotPlayGeneratesException:" + exc);
			// FIXME remove this print of trace:
			exc.printStackTrace();
			sException = exc.getMessage();
		}
		String sExceptionExpected = Msg.msg(sExpectedExceptionKey);
		// assert correct exception message is generated
		assertEquals("bot generated", sExceptionExpected, sException);
    }
	public void myFail(String error) {
		System.out.println(error);
		// fail(error);
	}
	//============no tests below here============
}
