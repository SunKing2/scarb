package com.scramblelovers.scrabble;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.StaticFunctions;
import com.scramblelovers.sundog.Dawg;
import com.scramblelovers.sundog.Node;


import java.util.*;

public class DawgTest {

	static List<String> wds = Arrays.asList(
	"aa",
	"cwm",
	"cwms",
	"cyma",
	"cymae",
	"cymar",
	"cymars",
	"cymas",
	"cymatia",
	"cymatium",
	"cymbal",
	"cymbaleer",
	"cymbaleers",
	"cymbaler",
	"cymbalers",
	"cymbalist",
	"cymbalists",
	"cymbalom",
	"cymbaloms",
	"cymbals",
	"cymbidia",
	"cymbidium",
	"cymbidiums",
	"cymbling",
	"cymblings",
	"cyme",
	"cymene",
	"cymenes",
	"cymes",
	"cymlin",
	"cymling",
	"cymlings",
	"cymlins",
	"cymogene",
	"cymogenes",
	"cymograph",
	"cymographs",
	"cymoid",
	"cymol",
	"cymols",
	"cymophane",
	"cymophanes",
	"cymose",
	"cymosely",
	"cymous",
	"zzz"
	);

	// I use a factory method in case you want to create and test
	// your own Dawg. 
	static Dawg makeDawg(Collection<String> list) {
		return new Dawg(list);
	}
	static Dawg dWds;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public final void testDawg14words() {
		List<String> words = Arrays.asList(
				"ar",
				"arb",
				"arbo",
				"arbs",
				"arc",
				"arcs",
				"ars",
				"or",
				"orb",
				"orbs",
				"orc",
				"orcs",
				"ors",
				"ort"			
		);
		System.out.println("ar list");
		Dawg d1 = makeDawg(words);
		assertEquals(10, d1.getNodeCount());
	}
	@Test
	public final void testDawg3words() {
		List<String> words = Arrays.asList(
				"aals",
				"acks",
				"abck"
		);
		Dawg d1 = makeDawg(words);
		assertEquals(9, d1.getNodeCount());
	}
	@Test
	public final void testDawg5words() {
		List<String> words = Arrays.asList(
				"es",
				"e",
				"nes",
				"oned",
				"oner"
		);
		Dawg d1 = makeDawg(words);
		assertEquals(9, d1.getNodeCount());
	}
	@Test
	public final void testDawg9words() {
		List<String> words = Arrays.asList(
				"aahed",
				"abandoned",
				"abandoner",
				"abettals",
				"abetted",
				"abetter",
				"abetters",
				"abetting",
				"abettor"
		);
		Dawg d1 = makeDawg(words);
		assertEquals(25, d1.getNodeCount());
	}
	@Test
	// this is for testing a complete dictionary.  Great for unit testing,
	// but really too slow to keep in an active test suite.
	public final void testDawgAllWords() {
        Long time = 0L;
		time = System.currentTimeMillis();
		//Dawg d = makeDawg(IOStuff.readFile("dict", 2, 9, true, false));
		time = System.currentTimeMillis() - time;
		
		// tested 20110302 nodes were 120370, pruned 20685
		
        Long expected = 200L;
		assertTrue("time excessive:" + time, time < expected);
	}
	
	@Test
	public final void testGetNextLetter() {
		System.out.println("using cym list:");
		dWds = makeDawg(wds);

		String prefix = "cym";
		Node tn = dWds.getNodeAfterPrefix(prefix);
		// for words starting with cym, the next letter is a,b,e,l,o
		String sNextLetter = tn.getLettersAsString();
		assertEquals("cym following letters:" + sNextLetter + ".", 5, sNextLetter.length());
		assertEquals("cym", "abelo", sNextLetter);

		/*  allowable set starting with cyma
		 * 	"cyma",
			"cymae",
			"cymar",
			"cymars",
			"cymas",
			"cymatia",
			"cymatium",
		 */
		
		// set pointer to 'a' (cym'a') and find letters after that:
		// the pointer is still at the m in cym
		// let's get it to point to the a following cym
		tn = tn.getChild('a');
		// e, r, s , t expected
		sNextLetter = tn.getLettersAsString();
		assertEquals("cyma plus a letter",  4, sNextLetter.length());
		// here's a short cut for determining the letters:
		assertEquals("erst expected to follow cyma", "erst", sNextLetter);
		
		//set the pointer to r and ensure that the result is "s" only
		assertEquals("cymas only expected", "s", tn.getChild('r').getLettersAsString());
		
		// set the pointer to that s, and ensure there are no results
		assertEquals("cymas+? expected none", "", tn.getChild('s').getLettersAsString());
	}
	@Test
	public final void testGetNodeAfterPrefixInvalidPrefix() {
		dWds = makeDawg(wds);
		String prefix = "zx";
		Node tn = dWds.getNodeAfterPrefix(prefix);
		
		// tn should be null
		assertEquals("zx should have a null node", null, tn);
	}
	@Test
	public final void testWhereIsGussy() {
        Dawg dawg = StaticFunctions.readDawgFromFile();
        // TODO remove this crap:
        Node x = dawg.getNodeAfterPrefix("gus");
        Collection<Node> children = x.getChildren();
        for (Node node: children) {
            System.out.println("ldb ctr:" + node.getLetter());
        }
        assertEquals("letters after gus", 3, children.size());
    }
}