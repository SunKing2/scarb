package com.scramblelovers.scrabble;
import java.util.*;

public class TileBag {
    private Random random;
    private List<Tile> letters;
    
    public TileBag(boolean sameRandomNumbers) {
        if (sameRandomNumbers) {
            random = new Random(2);
        }
        else {
            random = new Random();
        }

        // make new set of letters
        letters = new ArrayList<Tile>();
        // for each letter (each item in the array LETTER_STATS)  {char, quantity, score}
        for (int i = 0; i < Globals.LETTER_STATS.length; i++) {
            // create this many of that letter
            for (int j = 0; j < Globals.LETTER_STATS[i][1]; j++) {
                Tile ll = new Tile(Globals.LETTER_STATS[i][0], Globals.LETTER_STATS[i][2]);
                letters.add(ll);
            }
        }
    }
    public Tile extractTile() {
        if (letters.size() == 0) {
            return null;
        }
        int r = random.nextInt(letters.size());
        Tile l = (Tile) letters.get(r);
        letters.remove((Tile) letters.get(r));
        //System.out.println("TileBag.extractTile() returning:" + l);
        return l;
    }
    public void returnTile(Tile l) { letters.add(l); }
    public int getSize() { return letters.size(); }
    public TileBag clone() {
        List<Tile> let2 = new ArrayList<Tile>();
        let2.addAll(letters);
        TileBag t2 = new TileBag(false);
        t2.letters = let2;
        return t2;
    }
}
