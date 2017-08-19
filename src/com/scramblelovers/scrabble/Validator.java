package com.scramblelovers.scrabble;
import i10n.Msg;

/**
 * <code>Validator</code> ensures legal plays in play position and words in dictionary:
 *   - for the tiles being in a straight line without gaps and 
 *   - ensuring the formed word is touching an existing tile.  
 *   - validates any formed words against a dictionary.
 */
public class Validator {

     // this class has static methods only so shall never be 'created'  
    private Validator() {
        throw new AssertionError();
    }
    
    private static final int START_SQUARE_ROW = Globals.TILES_HIGH/2;
    private static final int START_SQUARE_COLUMN = Globals.TILES_WIDE/2;

    public static boolean validPositionAndWords(Play play, Board board, Dictionary dict, boolean checkCrossPlays, boolean cleanUpPlay) {
        boolean bReturn = false;
        
        if (cleanUpPlay) {
            play = play.cleanUp(board, false);
        }
        
        try {
            Validator.validPosition(play, board, false, true);
            
            // ok so now we have a pretty good position for the play, but 
            // we need to ensure its crossplays are valid
            // (unfortunately it checks the main play too, which is not necessary)
            //System.out.println("Validator.vpaw checking:" + play);
            if (checkCrossPlays) {
                Validator.validWords(play, board, dict);
            }
            else {
                if (!dict.contains(play.getWord())) {
                    return false;
                }
            }
            //System.out.println("ok Validator.vpaw checking:" + play);
            bReturn = true;  // otherwise it would have caused an exception
        }
        catch (Exception exc) {
            //System.out.println("Validator.vpaw ignored for play:" + play + ' ' + exc);
            // ignore it, it will just return false;
        }
        return bReturn;
    }
    //FIXME this may report true even if some of the crossplays are invalid
    public static boolean validPositionAndWords(Play play, Board board, Dictionary dict)  {
        boolean bReturn = false;
        try {
            Validator.validPosition(play, board, false, true);
            Validator.validWords(play, board, dict);
            bReturn = true;
        }
        catch (Exception exc) { } // yup ignore, coz it can only return true with no exceptions
        return bReturn;
    }
    
    /**
     * Validates a <code>Play</code> 
     */
    public static void validPosition(Play play, Board board, 
            boolean checkUsedStartSquare, boolean checkOverwriting) 
            throws Exception {

        // ensure start square is occupied
        if (checkUsedStartSquare && !board.contains(START_SQUARE_COLUMN, START_SQUARE_ROW,  play)) {
            throwMsg("startSquare");
        }
        
        // ensure that at least one of the placed tiles are touching
        // an existing tile on the board
        // don't perform this test on the game's first play
        // TODO should this section really be a method of Board?
        if (!checkUsedStartSquare) {
            boolean bFound = false;
            for (Tile let: board.asLetters(play)) {
                if (board.hasOccupiedAjacentSquare(let.getRow(), let.getColumn())) {
                    bFound = true;
                }
            }
            if (!bFound) {
                throwMsg("touchExisting");
            }
        }

        boolean horizontal = play.isHorizontal();
        int dc = horizontal ? 1 : 0;
        int dr = horizontal ? 0 : 1;
        int row = play.getRow();
        int column = play.getColumn();
        int length = play.getWord().length();
        int endColumn = column + dc * length - 1;
        int endRow = row + dr * length - 1;
        
        // 20110222 check if a client submitted main word that omitted a prefix or suffix
        if ( length < 2 ||
             (horizontal && column > 0 && board.isOccupied(row, column - 1)) ||
             (horizontal && endColumn < Globals.TILES_WIDE_LESS1 && board.isOccupied(row, endColumn + 1)) ||
             (!horizontal && row > 0 && board.isOccupied(row - 1, column)) ||
             (!horizontal && endRow < Globals.TILES_HIGH_LESS1 && board.isOccupied(endRow + 1, column))
        ) {
            throwMsg("badName");
        }
        
        // 20110222 check it's not writing on top of tiles on board
        if (checkOverwriting) {
            char[] cc = play.getWord().toCharArray();
            for (int ix = 0; ix < length; ix++) {
                Tile tile = board.getTileAt(row, column);
                
                // I once had an inkling that this is not complete, but I forget now:
                if (tile != null && (tile.getIntendedValueChar() != 0 && tile.getIntendedValueChar() != cc[ix])) {
                    throwMsg("overwriting");
                }
                row += dr;
                column += dc;
            }
        }
    }
    
    private static void throwMsg(String messageID) throws Exception {
        throw new Exception(Msg.msg(messageID));
    }
    /**
     * Validates all the words of a play by computing their crossplays and validating with 'dictionary'
     * @param play
     * @param board
     * @param dictionary
     * @throws Exception thrown if any word is not in the dictionary
     */
    public static void validWords(Play play, Board board, Dictionary dictionary) throws Exception {
        for (Play p: board.getPlays(play)) {
            if (! dictionary.contains(p.getWord())) {
                // FIXME put this back:
                //System.out.println("Validator.validWords, not found:" + p.getWord());
                // FIXME create a new throwMsg method for embedded strings
                throwMsg("notFound");
            }
        }
    }
}
