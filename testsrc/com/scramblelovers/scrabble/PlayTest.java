package com.scramblelovers.scrabble;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Play;

public class PlayTest {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}


	@Test
	public final void testContains() {
		//fail("Not yet implemented");
	}
	
    private static final String REGULAR_PROBLEM = "Must be in the format: one of a9, a12, 9a, 12a, letters must be between 'a' and 'o' and numbers must be between 1 an 15";
//    private static final String ROW_PROBLEM = "Row Problem? " + REGULAR_PROBLEM;
//    private static final String COLUMN_PROBLEM = "Column Problem? " + REGULAR_PROBLEM;
    private static final String ROW_PROBLEM = REGULAR_PROBLEM;
    private static final String COLUMN_PROBLEM = REGULAR_PROBLEM;
	private static final String ANY_OLD_WORD = "seat";

	private static final String sExpectedWords[][] = {
		{"seat"},
		{"at"},
		{"mat"},
		{"seats", "tao", "so"},
		{"ma", "ma", "at"},
		{"seats", "site"},
		{"ta", "ai"},
		{"rest"},
		{"testy", "sei", "rest"},
	};
	
	@Test
	public final void testPlayStringStringAllValidCombinations() {
		String sWord = ANY_OLD_WORD;
		
		for (int iRowString = Play.LOWEST_NSA_RC_NUMBER; iRowString <= Play.HIGHEST_NSA_RC_NUMBER; iRowString++) {
			for (char cColumn = Play.LOWEST_NSA_RC_LETTER; cColumn <= Play.HIGHEST_NSA_RC_LETTER; cColumn++) {
				String sPlay;
				int iRowExpected;
				int iColumnExpected;
				Play p;

				// when row first is 1, it tests all things like 1A 14M 15O
				// when row first is 0, it tests all things like A2 L12 O15
				for (int rowFirst = 0; rowFirst < 2; rowFirst++) { 
					// row first followed by column
					if (rowFirst == 1) {
						sPlay = "" + iRowString + cColumn;
					}
					else {
						sPlay = "" + cColumn + iRowString;
					}
					System.out.println("new Play(" + sWord + ", " + sPlay + ")");
				
					p = StaticFunctions.getPlay(sWord, sPlay);
					iRowExpected = iRowString - 1;
					iColumnExpected = cColumn - 'a';
					assertEquals("row of rc:" + sPlay, iRowExpected, p.getRow());
					assertEquals("column of rc:" + sPlay, iColumnExpected, p.getColumn());
				}
			}
		}
	}
	@Test
	public void testPlayStringStringIllegal01Low() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "a0");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(ROW_PROBLEM, exc.getMessage());
		}
	}
	@Test
	public void testPlayStringStringIllegal01High() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "a16");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(ROW_PROBLEM, exc.getMessage());
		}
	}
	@Test
	public void testPlayStringStringIllegal02Low() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "0a");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
	@Test
	public void testPlayStringStringIllegal02High() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "16a");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(ROW_PROBLEM, exc.getMessage());
		}
	}
	// typical case where you mean O3 but enter 03 instead
	@Test
	public void testPlayStringStringIllegal04() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "03");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
	// typical case where you mean 4O but enter 40 instead
	@Test
	public void testPlayStringStringIllegal05() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "40");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(COLUMN_PROBLEM, exc.getMessage());
		}
	}
	// space RC
	@Test
	public void testPlayStringStringIllegal06() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "4 a");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
		
	// space CR
	@Test
	public void testPlayStringStringIllegal07() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "a 4");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
		
	// comma RC
	@Test
	public void testPlayStringStringIllegal08() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "4,a");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
		
	// comma CR
	@Test
	public void testPlayStringStringIllegal09() throws Exception {
		try {
			StaticFunctions.getPlay(ANY_OLD_WORD, "a,4");
			fail("should have thrown IllegalArgumentException" );
		} catch (IllegalArgumentException exc) {
			assertEquals(REGULAR_PROBLEM, exc.getMessage());
		}
	}
		
	@Test
	public final void testGetPlaysAddSO() {
		Board board = new Board(0);
		Collection<Play> plays = null;
		Play play;

		/*	Adding word "SO" to board at row 7, col 11:
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!SEATs!!!
			!!!!!!!!!TAo!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
		 */
		//setup stuff before real play
		play = new Play(true, 7, 7, "seat");
		board.placePlay(play, null);
		play = new Play(true, 8, 9, "ta");
		board.placePlay(play, null);
		play = new Play(false, 7, 11, "so");
		
		//board.spewBoard();
		plays = board.getPlays(play);

		int ix = 3;
		String[] sExpected = sExpectedWords[ix];
		String sTestMessage = "adding SO to seat/ta expected:" + Arrays.toString(sExpected);
		List<String> vExpected = Arrays.asList(sExpected);
//        System.out.println("plays:" + plays);
//        System.out.println("vExpected:" + vExpected);
		assertTrue(sTestMessage, equals(plays, vExpected));
	}
	@Test
	public final void testGetPlaysAddMA() {
		Board board = new Board(0);
        Collection<Play> plays = null;
		Play play;

		/*	Adding word "MA" to board
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!ma!!!!
			!!!!!!!SEAT!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
		 */
		//setup stuff before real play
		play = new Play(true, 7, 7, "seat");
		board.placePlay(play, null);
		play = new Play(true, 6, 9, "ma");
		
		//board.spewBoard();
		plays = board.getPlays(play);

		int ix = 4;
		String[] sExpected = sExpectedWords[ix];
		String sTestMessage = "adding MA to seat expected:" + Arrays.toString(sExpected);
		List<String> vExpected = Arrays.asList(sExpected);
		assertTrue(sTestMessage, equals(plays, vExpected));
	}
	@Test
	public final void testGetPlaysAddAI() {
		Board board = new Board(0);
        Collection<Play> plays = null;
		Play play;

		/*	Adding word "AI" to board 
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!SEATS!!!
			!!!!!!!!!!aI!!!
			!!!!!!!!!!!T!!!
			!!!!!!!!!!!E!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
		 */
		//setup stuff before real play
		play = new Play(true, 7, 7, "seat");
		board.placePlay(play, null);
		play = new Play(false, 7, 11, "site");
		board.placePlay(play, null);
		play = new Play(true, 8, 10, "ai");
		
		//board.spewBoard();
		plays = board.getPlays(play);

		int ix = 6;
		String[] sExpected = sExpectedWords[ix];
		String sTestMessage = "adding expected:" + Arrays.toString(sExpected);
		List<String> vExpected = Arrays.asList(sExpected);
		assertTrue(sTestMessage, equals(plays, vExpected));
	}

	@Test
	public final void testGetPlaysAddREST() {
		Board board = new Board(0);
        Collection<Play> plays = null;
		Play play;

		/*	Adding word "REST" to board 
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!SEATS!!!
			!!!!!!!!!S!I!!!
			!!!!!!!!rEsT!!!
			!!!!!!!!!A!E!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
		 */
		//setup stuff before real play
		play = new Play(true, 7, 7, "seat");
		board.placePlay(play, null);
		play = new Play(false, 7, 11, "site");
		board.placePlay(play, null);
		play = new Play(false, 7, 9, "asea");
		board.placePlay(play, null);
		play = new Play(true, 9, 8, "rest");
		
		//board.spewBoard();
		plays = board.getPlays(play);

		int ix = 7;
		String[] sExpected = sExpectedWords[ix];
		String sTestMessage = "adding expected:" + Arrays.toString(sExpected);
		List<String> vExpected = Arrays.asList(sExpected);
		assertTrue(sTestMessage, equals(plays, vExpected));
	}
	@Test
	public final void testGetPlaysAddTESTY() {
		Board board = new Board(0);
		Collection<Play> plays = null;
		Play play;

		/*	Adding word "TESTY" to board 
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!SEATS!!!
			!!!!!!!!!SeI!!!
			!!!!!!!!REsT!!!  // yes this is right, RE was added, not REST
			!!!!!!!!!ATE!!!
			!!!!!!!!!!y!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!!
		 */
		//setup stuff before real play
		play = new Play(true, 7, 7, "seat");
		board.placePlay(play, null);
		play = new Play(false, 7, 11, "site");
		board.placePlay(play, null);
		play = new Play(false, 7, 9, "asea");
		board.placePlay(play, null);
		// !!! don't get confused here, the word is now RE not REST!!!!
		play = new Play(true, 9, 8, "re");
		board.placePlay(play, null);
		play = new Play(true, 10, 9, "ate");
		board.placePlay(play, null);
		play = new Play(false, 7, 10, "testy");
		
		//board.spewBoard();
		plays = board.getPlays(play);

		int ix = 8;
		String[] sExpected = sExpectedWords[ix];
		String sTestMessage = "adding expected:" + Arrays.toString(sExpected);
		List<String> vExpected = Arrays.asList(sExpected);
		assertTrue(sTestMessage, equals(plays, vExpected));
	}

	private boolean equals(Collection<Play> plays, List<String> wordsList) {
		ArrayList<String> vWords = new ArrayList<String>();
		vWords.addAll(wordsList);
		if (plays.size() < vWords.size()) return false;
		boolean bReturn = true;
		for (Play play: plays) {
			int ix = vWords.indexOf(play.getWord());
			if (ix >= 0) {
				vWords.remove(ix);
			}
			else {
				bReturn = false;
			}
		}
		return bReturn;
	}
}
