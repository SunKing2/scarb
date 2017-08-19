package com.scramblelovers.sundog;
import java.util.*;

import com.scramblelovers.scrabble.*;
import com.scramblelovers.types.*;

public class DawgStuff {
    private Set<Play> legalPlays = new HashSet<Play>();
    
    public void leftPart(int row, int column, String rack, String partialWord, Node n, int limit, Square anchorSquare) {
        
//        if (row == 6) {
//            System.out.println("ds.lp:" + row + ' ' + column + ' ' + rack + ' ' + partialWord + ' ' + limit);
//        }

        extendRight(row, column, rack, partialWord, n, anchorSquare);
        
        if (limit > 0) {
            for (Node nApostrophe: n.getChildren()) {
                char letter = nApostrophe.letter;
                if (inRack(rack, letter)) {
                    rack = removeRackLetter(rack, letter);
                    leftPart(row, column - 1 , rack, partialWord + letter, nApostrophe, limit - 1, anchorSquare);
                    rack = replaceRackLetter(rack, letter);
                }
            }
        }
    }

    private void extendRight(int row, int column, String rack, String partialWord, Node n, Square square) {

//        if (row == 6) {
//            System.out.println("ds.er:" + row + ' ' + column + ' ' + rack + ' ' + partialWord);
//        }
        // this whole mess may be able to be fixed if there was a fake square at the end of a row
        if (square == null) {
            // FIXME this is a very dangerous assumption when starting a play with rack of size 6:
            if (n.isTerminal() && rack.length() < Globals.RACK_SIZE) {
                //legalMove(partialWord, row, column);
                // 20110309: replaced preceding line with this:, still a problem with test sbm3t:tpw4
                if (rack.length() < 1) legalMove(partialWord, row, column);
                //System.out.println("ds.er ignoring play on null square:" + ' ' + row + ' ' + column + ' ' + partialWord );
                if (column + partialWord.length() == Globals.TILES_WIDE) {
                    // this will fail miserably if you started play with rack size 6
                    // because it will then include a word when you haven't used any tiles
                    //System.out.println("ds.er I'm not ignoring a word that shud prolly be included:" + ' ' + row + ' ' + column + ' ' + partialWord);
                    legalMove(partialWord, row, column);
                }
            }
            return; // always return if the square is null
        }
        
        if (square.isVacant()) {
            if (n.isTerminal() && rack.length() < Globals.RACK_SIZE) {
                legalMove(partialWord, row, column);
            }
            for (Node nApostrophe: n.getChildren()) {
                char letter = nApostrophe.letter;
                if (inRack(rack, letter) && square.hasLetterInCrossCheck(letter)) {
                    rack = removeRackLetter(rack, letter);
                    Square nextSquare = square.getNextSquare();
                    extendRight(row, column, rack, partialWord + letter, nApostrophe, nextSquare);
                    rack = replaceRackLetter(rack, letter);
                }
            }
        }
        else {
            char letter = square.getLetter();
            Node nApostrophe = n.getChild(letter);
            if (nApostrophe != null) {
                Square nextSquare = square.getNextSquare();
                extendRight(row, column, rack, partialWord + letter, nApostrophe, nextSquare);
            }
        }
    }
    public void legalMove(String word, int row, int column) {
        Play play = new Play(true, row, column, word);
        //System.out.println("ds: true " + row + ' ' + column + ' ' + word + ' ' + play);
        legalPlays.add(play);
    }

    public boolean inRack(String rack, char ch) {
        return rack.indexOf(ch) >= 0;
    }
    public String removeRackLetter(String rack, char ch) {
        rack = StringStuff.subtractLetter(rack, ch);
        return rack;
    }
    public String replaceRackLetter(String rack, char ch) {
        String newRack = rack + ch;
        rack = StringStuff.alphagram(newRack);
        return rack;
    }

    // FIXME very poor excuse for design.  This should be returned on the call which generates
    // the legal moves, not as a separated call.
    public Set<Play> getLegalPlays(int row) {
        return legalPlays;
    }
    // reinitializes the collection of legal plays to nuthin'
    public void thisHorribleMethodWouldNotBeNecessaryIfWeRetrievedPlaysRight() {
        legalPlays.clear();
    }
}
