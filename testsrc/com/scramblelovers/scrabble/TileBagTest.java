package com.scramblelovers.scrabble;

import org.junit.Before;
import org.junit.Test;

public class TileBagTest {

    TileBag bag;
    
    @Before
    public void setUp() throws Exception {
        bag = new TileBag(true);
    }

    @Test
    public void testGetLetter() {
        for (int player = 0; player < 2; player++) {
            System.out.println("Player:" + player + "'s letters:");
            for (int i = 0; i < 7; i++) {
                System.out.println(bag.extractTile());
            }
        }
    }
    @Test
    public void testExtractTile() {
        // ensure that first 100 tiles extracted are exactly the same
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            Tile t = bag.extractTile();
            char c = t.getFaceValueChar();
            sb.append(c);
        }
        String allTiles = sb.toString();
        System.out.println(allTiles);
    }
}
