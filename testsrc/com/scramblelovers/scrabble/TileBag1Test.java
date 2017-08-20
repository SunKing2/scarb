package com.scramblelovers.scrabble;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TileBag1Test {

	TileBag bag;
	@Before
	public void setUp() throws Exception {
		bag = new TileBag(false);
	}
	@Test
	public void testLetterQuantities() throws Exception {
        assertEquals("expected # letters", 100, bag.getSize());
		assertContains(9, 'a');
		assertContains(2, 'b');
		assertContains(2, 'c');
		assertContains(4, 'd');
		assertContains(12, 'e');
		assertContains(2, 'f');
		assertContains(3, 'g');
		assertContains(2, 'h');
		assertContains(9, 'i');
		assertContains(4, 'l');
		assertContains(2, 'm');
		assertContains(6, 'n');
		assertContains(8, 'o');
		assertContains(2, 'p');
		assertContains(2, 'p');
		assertContains(6, 'r');
		assertContains(4, 's');
		assertContains(6, 't');
		assertContains(4, 'u');
		assertContains(2, 'v');
		assertContains(2, 'w');
        assertContains(2, 'y');
        assertContains(2, ' ');
	}
	private String getBagAsString() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 100; i++) {
	        sb.append(bag.extractTile());
	    }
	    return sb.toString();
	}
	private boolean assertContains(int iExpected, char letter) {
	    // a hack that I have to make a new bag each time
	    bag = new TileBag(false);
		int iCount = 0;
		int iLen = bag.getSize();
		String bagAsString = getBagAsString();
		for (int i = 0; i < iLen; i++) {
			if (bagAsString.charAt(i) == letter) iCount++;
		}
		boolean bOk = (iCount == iExpected);
		if (!bOk) System.out.println("oops, expected:" + iExpected + " but was:" + iCount + ", for letter:" + letter);
		return bOk;
	}
	@Test
	// test that it returns an even distribution of letters from bag
	public void testGetRackRandomTest() {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		double iterations = 100000;
		for (int i = 0; i < iterations; i++) {
		    // create a new bag on every iteration to ensure we are picking from full bag;
		    bag = new TileBag(false);
            if (bag.extractTile() == null) System.out.println("bag.getLetter() null");
			char ch = bag.extractTile().getFaceValueChar();
			int iCount = 0;
			Integer IC = map.get(ch);
			if (IC != null) {
				iCount = IC + 1;
			}
			map.put(ch, iCount);
		}
		assertLetterQuantity(map, iterations, 'a', 9);
		assertLetterQuantity(map, iterations, 'z', 1);
        assertLetterQuantity(map, iterations, ' ', 2);
	}
	private void assertLetterQuantity(Map<Character, Integer> map,
            double iterations, char chCheck, int quantityOfEach) {
	    if (map == null) System.out.println("lb1t.assertLetterQuantity, map null");
	    Integer actualCount = map.get(chCheck);
	    
	    // I use doubles to do a comparison that's approximately equal
	    double iActualBlanks = 0.0;
	    if (actualCount != null) {
	        iActualBlanks = actualCount;
	    }
		double iExpectedBlanks = quantityOfEach * iterations / 100;
		System.out.println("character:" + chCheck + " expected:" + iExpectedBlanks + " actual:" + iActualBlanks);

		assertEquals("character:" + chCheck, iExpectedBlanks, iActualBlanks, 55 * quantityOfEach);
    }
}
