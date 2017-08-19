package com.scramblelovers.scrabble;
import java.util.*;
public class Board {

    private BoardSquare[][] squares;
    private Board transposedBoard;
    private boolean recalculateTransposedBoard = true;
    private int boardType;
    private int rows;
    private int columns;
    // direction yes I know about enums, but I prefer these:
    private static final int TOP = 0;
    private static final int RIGHT = 3;
    private static final int BOTTOM = 2;
    private static final int LEFT = 1;

    // boardType = 0 for Scrabble board layout.  1 for alternate layout
    public Board(int boardType) {
        this(boardType, Globals.TILES_HIGH, Globals.TILES_WIDE);
    }
    public Board(int boardType, int rows, int columns) {
        squares =new BoardSquare[rows][columns];
        this.boardType = boardType;
        this.rows = rows;
        this.columns = columns;
        for (int row = 0; row < rows; row++) {
            String sRow = Globals.BOARD_VALUES[boardType][row];
            for (int column = 0; column < columns; column++) {
                char c = sRow.charAt(column);
                BoardSquare square = new BoardSquare(c, row, column);
                squares[row][column] = square;
                // set the adjacent squares to each square:
                if (column > 0) square.setAdjacentSquare(LEFT, squares[row][column-1]);
                if (column > 0) squares[row][column -1].setAdjacentSquare(RIGHT, square );
                if (row > 0) square.setAdjacentSquare(TOP, squares[row - 1][column]);
                if (row > 0) squares[row - 1][column].setAdjacentSquare(BOTTOM, square);
            }
        }
    }

    public List<Tile> placePlay(Play play, String rack) {
        return placePlay(play);
    }
    public List<Tile> placePlay(Play play) {
        //System.out.println("Board.placePlay:" + play);
        
        List<Tile> lisReturn = new ArrayList<Tile>();
        for (Tile let: asLetters(play)) {
            int letColumn = let.getColumn();
            int letRow = let.getRow();
            squares[letRow][letColumn].setTile(let);
            lisReturn.add(let);
        }
        recalculateTransposedBoard = true;
        return lisReturn;
    }

    /**
     * Returns only those tiles added to the board for this play.
     * (This differs from a Play object which includes all the letters)
     * @return <code>Tile</code>s excluding letters already on the board; in other words it returns
     * only those <code>Tile</code>s that you actually placed down during a play.
     */
    // doesn't really return actual Tiles, it creates new ones.
    public Collection<Tile> asLetters(Play play) {
        ArrayList<Tile> colTiles = new ArrayList<Tile>();
        if (play == null) return colTiles;
        String word = play.getWord();
        int iLen = word.length();
        for (int i = 0; i < iLen; i++){
            char ch = word.charAt(i);
            int points = Globals.getPoints(ch);

            // determine the row and column for the next letter in this play
            int letRow = play.getRow();
            int letColumn = play.getColumn();
            if (play.isHorizontal()) {
                letColumn = letColumn + i;
            }
            else {
                letRow = letRow + i;
            }

            // only add it if this letter is not already on the board
            if (!squares[letRow][letColumn].isOccupied()) {
                colTiles.add(new Tile(ch, points, letRow, letColumn));
            }
        }
        return colTiles;
    }

    /**
     * Get all <code>Play</code> and secondary Play objects that were made by
     * placing the letters down for this <code>Play</code>.  Useful for
     *     - validation
     *     - you can invoke a method on each of these Play's to get their scores
     * @return a single dimensional array of <code>Play</code> objects
     * including this <code>Play</code>.
     */
    public Collection<Play> getPlays(Play play) {
        //System.out.println("Board.getPlays:" + play);
        ArrayList<Play> plays = new ArrayList<Play>();
        // TODO this is very rare:
        if (play == null) {
            System.out.println("Board.getPlays can't get play on null play");
            return plays;  // returning a Collection of length 0
        }
        plays.add(play);
        boolean bHor = play.isHorizontal();
        String word = play.getWord();
        int ir = play.getRow();
        int ic = play.getColumn();
        int dr = 0;
        int dc = 0;
        if (bHor) dc = 1; else dr = 1;
        int iWordLen = word.length();
        // for each letter in (this) Play
        for(int i = 0; i < iWordLen; i++) {
            char ch = word.charAt(i);
            //System.out.println("checkin" + ch);
            if (!squares[ir][ic].isOccupied() && squares[ir][ic].hasOccupiedInlineAdjacentSquare(!bHor)) {
                Play p = getInlinePlayTo(ir, ic, !bHor, "" + ch);
                //System.out.println("adding:" + p);
                plays.add(p);
            }
            ir += dr;  ic += dc;
        }
        return plays;
    }
    public Play getInlinePlayTo(int row, int column, boolean horizontal, String word) {
        BoardSquare startSquare = squares[row][column];
        
        StringBuffer sb = new StringBuffer();
        sb.append(word);
        // go west/north young man
        BoardSquare square = startSquare;
        while(square.hasOccupiedPrecedingSquare(horizontal)) {
            square = square.getPrecedingSquare(horizontal);
            sb.insert(0, square.getTile().getFaceValueChar());
            row = square.getRow();
            column = square.getColumn();
        }
        // go east/south 
        square = startSquare;
        
        // advance to the end of the passed word
        for (int i = 0; i < word.length() - 1; i++) {
            square = square.getFollowingSquare(horizontal);
        }
        
        while(square.hasOccupiedFollowingSquare(horizontal)) {
            square = square.getFollowingSquare(horizontal);
            sb.append(square.getTile().getFaceValueChar());
        }
        Play play = new Play(horizontal, row, column, sb.toString());
        return play;
    }    
    
    private void setTile(int row, int column, Tile tile) {
        squares[row][column].setTile(tile);
    }
    public int getBoardType() { return boardType; }
    public boolean isOccupied(int row, int column) { return this.squares[row][column].isOccupied(); }
    public Tile getTileAt(int row, int column) { return squares[row][column].getTile(); }
    public int getLetterMultiplier(int row, int column) { return squares[row][column].getLetterMultiplier(); }
    public int getWordMultiplier(int row, int column) { return squares[row][column].getWordMultiplier(); }
    public boolean hasOccupiedAjacentSquare(int row, int column) { return squares[row][column].hasOccupiedAdjacentSquare(); }
    public boolean hasOccupiedInlineAdjacentSquare(int row, int column, boolean horizontal) { return squares[row][column].hasOccupiedInlineAdjacentSquare(horizontal); }
    public char getTypeAt(int row, int column) { return squares[row][column].getType(); }

    public char getCharAt(int row, int column) {
        char ch = (char)0;
        if (row < 0 || row >= Globals.TILES_WIDE || column < 0 || column >= Globals.TILES_HIGH) {
            return ch;
        }
        Tile t = getTileAt(row, column);
        if (t != null) {
            ch = t.getFaceValueChar();
        }
        else {
            //System.out.println("row ctr at rc" + row + ' ' + column + " has no tile");
        }
        return ch;
    }
    
    public Board transpose() {
        if (!recalculateTransposedBoard) {
            return transposedBoard;
        }
        Board bt = new Board(boardType, rows, columns);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Tile tileToPlace = this.getTileAt(row, column);
                if (tileToPlace != null) {
                    bt.setTile(column, row, tileToPlace);
                }
            }
        }
        transposedBoard = bt;
        recalculateTransposedBoard = false;
        return bt;
    }
    public Board clone() {
        // cleverly lazy and sloppy
        return this.transpose().transpose();
    }
}
