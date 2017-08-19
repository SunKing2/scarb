package com.scramblelovers.scrabble;

public class BoardSquare {
    private char type;
    private int column;
    private int row;
    private Tile tile;
    private boolean occupied;
    // not private, but only for JUnit reasons
    BoardSquare[] adjacentSquares = new BoardSquare[4]; //Top, right, bottom, left

    public BoardSquare(char s, int row, int column) {
        type = s;
        this.column = column;
        this.row = row;
        tile = null;
        occupied = false;
    }
    public int getWordMultiplier() {
        if (type == ' ') return 1;
        else if (type == 'T') return 3;
        else if (type == 'D' || type == 'S') return 2;
        else return 1;
    }
    public int getLetterMultiplier() {
        if (type == ' ') return 1;
        else if (type == 't') return 3;
        else if (type == 'd') return 2;
        else return 1;
    }
    public void setAdjacentSquare(int direction, BoardSquare c) { this.adjacentSquares[direction] = c; }
    public char getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public Tile getTile() { return tile; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    
    public boolean setTile(Tile l) {
        if (occupied) return false;
        occupied = true;
        tile = l;
        return true;
    }

    public boolean hasOccupiedAdjacentSquare() {
        boolean found = false;
        for (int k = 0; k < 4; k++) {
            BoardSquare nb = this.adjacentSquares[k];
            if (nb == null)
                continue;
            if (nb.isOccupied()) found = true;
        }
        return found;
    }
    
    // direction 0=N, 1=W, 2=S, 3=E
    public boolean hasOccupiedAdjacentSquare(int direction) {
        boolean found = false;
        BoardSquare nb = this.adjacentSquares[direction];
        if (nb != null && nb.isOccupied()) {
            found = true;
        }
        return found;
    }
    public boolean hasOccupiedInlineAdjacentSquare(boolean horizontal) {
        if (horizontal) return (hasOccupiedAdjacentSquare(1) || hasOccupiedAdjacentSquare(3));
        else return (hasOccupiedAdjacentSquare(0) || hasOccupiedAdjacentSquare(2));
    }
    public boolean hasOccupiedPrecedingSquare(boolean horizontal) {
        if (horizontal) return hasOccupiedAdjacentSquare(1);
        else return hasOccupiedAdjacentSquare(0);
    }
    public boolean hasOccupiedFollowingSquare(boolean horizontal) {
        if (horizontal) return hasOccupiedAdjacentSquare(3);
        else return hasOccupiedAdjacentSquare(2);
    }
    public BoardSquare getPrecedingSquare(boolean horizontal) {
        if (horizontal) return adjacentSquares[1];
        else return adjacentSquares[0];
    }
    public BoardSquare getFollowingSquare(boolean horizontal) {
        if (horizontal) return adjacentSquares[3];
        else return adjacentSquares[2];
    }
    public String toString() {
        return tile == null ? "!" : "" + tile.getFaceValueChar();
    }
}
