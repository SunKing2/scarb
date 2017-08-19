package com.scramblelovers.scrabble;

public class ScrabbleScorer {

    private ScrabbleScorer() { } // prevent creating instance
    
    // FIXME this shameful method is included to support historical method calls, and
    // it shamefully uses a trick to fill in the rack variable... note this rack variable 
    // is not the real rack, and if pointsForWord ever changes, it will produce fire and brimstone
    public static int getScore(Board board, Play play) {
        if (play == null) return 0;
        return getScore(board, play, play.getWord());
    }
    
    // get the score of this play plus all crossplays
    public static int getScore(Board board, Play play, String realRack) {
        String rack = new String(realRack); // probably excessive
        //System.out.println("ScrabbleScorer.getScore:" + play + " rack:[" + rack + "]");
        int iReturn = 0;
        
        if (play == null) return 0;

        for (Play p: board.getPlays(play)) {
            // if the play is the original play, the rack will be the original rack
            if (p == play) {
                iReturn += pointsForWord(board, p, rack);
            }
            // if the play is crossplay, we aren't really concerned about a rack, so we'll fake one
            // update 20110316, yes we really need the real rack
            else {
                //iReturn += pointsForWord(board, p, p.getWord());
                iReturn += pointsForWord(board, p, rack);
            }
        }
        if (play.getWord().length() > 6 && board.asLetters(play).size() == 7) iReturn +=50;
        
        return iReturn;
    }
    // rack is being passed here only to see if the value for the letter should be zero
    // If you ever use rack for something else, remove the getScore() method with 2 parameters
    private static int pointsForWord(Board board, Play play, String realRack) {
        String rack = new String(realRack);
        //System.out.println("ScrabbleScorer.pfw:" + play + " rack:[" + rack + "]");
        int iReturn = 0;
        int wordFactor = 1;

        // FIXME code here is the same as Board.asLetters.  shameful redundancy!
        String word = play.getWord();
        int iLen = word.length();
        for (int i = 0; i < iLen; i++){
            char ch = word.charAt(i);
            
            int pts = Globals.getPoints(ch);

            // determine the row and column for the next letter in this play
            int row = play.getRow();
            int column = play.getColumn();
            if (play.isHorizontal()) {
                column = column + i;
            }
            else {
                row = row + i;
            }
            //System.out.println("ss.pfw for char:" + ch + " points:" + pts + " occupied:" + board.isOccupied(row, column));

            // only add it if this letter is not already on the board
            if (board.isOccupied(row, column)) {
                iReturn += pts;
            }
            else {
                
                // With the blank the same as another letter on rack, 
                // we don't actually know where the player placed the blank.
                // FIXME if you make a blank into a letter that's already on the rack, this routine
                // will have to intelligently place the non-blank letter on the most favorable bonus square.
                // And when doing that, it will return the wrong results, if the player actually placed the
                // blank on a dls, tls for example.
                if (rack.indexOf(ch) < 0) {
                    //System.out.println("ch:" + ch + " now blank");
                    ch = ' '; // used to remove letter from rack 
                    pts = 0;
                }
                rack = StringStuff.subtractLetter(rack, ch); // used because there cud be duplicates   
                
                
                iReturn += pts * board.getLetterMultiplier(row, column);
                wordFactor *= board.getWordMultiplier(row, column);
            }
        }
        //System.out.println("ScrabbleScorer:pfw: " + word + " points:" + iReturn + " wf:" + wordFactor);
        return iReturn * wordFactor;
    }
}
