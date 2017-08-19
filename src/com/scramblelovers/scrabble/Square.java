package com.scramblelovers.scrabble;

public class Square {
    private char letter = 0;
    private Square nextSquare;
    private String crossCheckLetters = "abcdefghijklmnopqrstuvwxyz";
    // this is how many squares before the current square are blank
    private int limit;
    
    // don't rely on these, they are for testing purposes:
    private int column;
    private boolean anchor; // is this an anchor?
    
    public Square(char letter, Square previousSquare) {
        this.letter = letter;
        if (previousSquare != null) {
            previousSquare.nextSquare = this;
        }
    }
    public void setLetter(char letter) { this.letter = letter; }
    public void setCrossCheckLetters(String crossCheckLetters) {
        this.crossCheckLetters = crossCheckLetters;
    }
    public boolean hasCrossCheckLetters() {
        return crossCheckLetters.length() > 0;
    }
    public boolean isVacant() {
        return letter == 0;
    }
    public char getLetter() {
        return letter;
    }
    public Square getNextSquare() {
        return nextSquare;
    }
    public boolean hasLetterInCrossCheck(char letter2) {
        return crossCheckLetters.indexOf(letter2) >= 0;
    }
    public String toString() {
        char ch = letter == 0 ? '!' : letter;
        return "Column:" + column + " Letter:" + ch + 
        " Anchor:" + anchor + " limit:" + limit + 
        " Crosscheck:" + crossCheckLetters;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getLimit() { return limit; }
    public void setColumn(int column) { this.column = column; }
    public int getColumn() { return column; }
    public void setAnchor(boolean anchor) { this.anchor = anchor; }
    public boolean getAnchor() { return anchor; }
}
