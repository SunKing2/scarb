package com.scramblelovers.scrabble;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class IOStuffTest {

	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testGetSuperleaves() {
		String file = "superleaves.txt";
		Map<String, Double> map = IOStuff.xgetSuperleaves(file, false);
		String word = "abekr";
		Double leave = map.get(word);
		assertEquals(8.12, leave, .01);
	}
	@Test
	public void testGetSuperleaves2() {
		String file = "superleaves.txt";
		Map<String, Double> map = IOStuff.xgetSuperleaves(file, false);
		String word = "egu";
		Double leave = map.get(word);
		assertNotNull("retrieving egu from superleaves is null", leave);
		assertEquals(-6.67, leave, .01);
	}
	@Test
	public void testReadFile() {
		List<String> words;
		
		String sFileName = "testdata.txt";
		
		words = IOStuff.readFile(sFileName);
		assertNotNull("readFile returned null", words);
		int count = words.size();
		assertEquals("expected # words", 3, count);
		System.out.println("here are words read:" + words);
	}
	@Test
	public void testReadFileMinAndMaxWordLengths() {
		List<String> words;
		
		String sScrabbleWordList = "dict";
		
		final int[] wordsOfLength = {0, 0, 105, 1081, 4214, 9403, 16564, 25257, 31475 };
		
		for (int i = 0; i < wordsOfLength.length; i++) {
			words = IOStuff.readFile(sScrabbleWordList, i, i);
			assertNotNull("readFile returned null for words of length:" + i, words);
			int iActualWordCount = words.size();
			int iExpectedWordCount = wordsOfLength[i];
			assertEquals("expected # words", iExpectedWordCount, iActualWordCount);
			if (iActualWordCount < 102) {
				System.out.println("here are words read:" + words);
			}
		}
	}
}
