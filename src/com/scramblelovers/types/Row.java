package com.scramblelovers.types;
import java.util.*;

import com.scramblelovers.scrabble.*;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.sundog.Dawg;
import com.scramblelovers.sundog.DawgStuff;
import com.scramblelovers.sundog.Node;

public class Row {
    private List<Square> rowSquares;
    private List<Square> anchors = new ArrayList<Square>();
    private int row = 0;
    
    // create a row using an existing board and a row #
    public Row(Board board, Dictionary dict, int row) {
        this.row = row;
        rowSquares = new ArrayList<Square>();
        Square square;
        Square previous = null;

        for (int column = 0; column < Globals.TILES_WIDE; column++) {
            // find the char that is on the board in this column
            char ch = board.getCharAt(row, column);

            square = new Square(ch, previous);
            square.setColumn(column);
            // if it has a char, it's an anchor and don't need to determine cross checks
            if (ch >= 'a' && ch <= 'z') {
                square.setLetter(ch);
                square.setAnchor(true);
                //System.out.println("row. ctr adding anchor1:" + column);
                anchors.add(square);
            }
            else {
                // it's an anchor if it has a neighbor
                if (board.hasOccupiedAjacentSquare(row, column)) {
                    // calc crosschecks for square if it has inline vertical neighbors
                    if (board.hasOccupiedInlineAdjacentSquare(row, column, false)) {
                        square.setAnchor(true);
                        anchors.add(square);
                        //System.out.println("row. ctr adding anchor2:" + column);
                        setCrossCheckLetters(board, dict, row, square, column);
                    }
                }
            }
            previous = square;
            rowSquares.add(square);
        }
        // A square can't be an anchor if its previous square is an anchor
//        int anchorSize = anchors.size();
//        for (int i = anchorSize - 1; i >= 0; i--) {
//            //System.out.println("Row ctr i:" + i);
//            Square anc = anchors.get(i);
//            Square next = anc.getNextSquare();
//            if (next != null && next.getColumn() == anc.getColumn() + 1) {
//                //System.out.println("Row ctr maybe unnecessary anchor at:" + i);
//                //anchors.remove(anc);
//            }
//        }
    }

    private void setCrossCheckLetters(Board board, Dictionary dict, int row,
            Square square, int column) {
        String crossCheckLetters = "";
        for (char c = 'a'; c <= 'z'; c++) {
            Play play = new Play(false, row, column, "" + c);
            // false = check crossplays, true = clean up play
            if (Validator.validPositionAndWords(play, board, dict, false, true)) {
                crossCheckLetters += c;
            }
        }
        square.setCrossCheckLetters(crossCheckLetters);
    }
    
    public void spew(DawgStuff bot, Dawg dawg, String rack, boolean setLimits) {
        if (setLimits) determineAndSetLimitsForRow();
        Node rootNode = dawg.getRootNode();
        for (Square anchor: anchors) {
            int limit = anchor.getLimit();
            //System.out.println("results for current anchor:");
            String partialWord = "";
            bot.leftPart(row, anchor.getColumn(), rack, partialWord, rootNode, limit, anchor);
        }
    }
    // sets for each square a value for limit by counting its blank preceding squares
    private void determineAndSetLimitsForRow() {
        int iLastOccupied = -2;
        int iCurrentSquare = 0;
        for (Square square: rowSquares) {
            int iLimit = iCurrentSquare - iLastOccupied - 2;
            if (iLimit < 0) iLimit = 0;
            square.setLimit(iLimit);
            if (!square.isVacant()) {
                iLastOccupied = iCurrentSquare;
            }
            iCurrentSquare++;
        }
        // all squares that follow anchor squares have a limit of zero:
        for (Square square: anchors) {
            Square follow = square.getNextSquare();
            
            if (follow != null) {
                follow.setLimit(0);
            }
        }
    }
}
