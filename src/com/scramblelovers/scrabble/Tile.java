package com.scramblelovers.scrabble;

public class Tile implements Comparable<Tile> {
    private char faceValueChar;
    private int points;
    private char intendedValueChar = 'e'; // what you intended the blank to be 
    private int row;
    private int column;

    public Tile(char faceValueChar, int points) {
        // -1 means it has not yet been placed on the board during a player's turn
        this(faceValueChar, points, -1, -1);
    }
    public Tile(char faceValueChar, int points, int row, int column) {
        if (faceValueChar == '?' || faceValueChar == ' ') {
            faceValueChar = ' ';
            this.intendedValueChar = 'e';
        }
        else {
            this.intendedValueChar = faceValueChar;  // yes assumes same char
        }
        this.faceValueChar = faceValueChar;
        this.points = points;
        this.row = row;
        this.column = column;
    }
    public void setIntendedValueChar(char l) { intendedValueChar = l; }
    public char getIntendedValueChar() { return intendedValueChar; }

    public char getFaceValueChar() { return faceValueChar; }
    public int getPoints() { return points; }
    public int getRow() { return row; }
    public int getColumn() { return column; }
    // TODO fix the Board.asLetters() bug that made this stupid method necessary, and remove this method
    public void makeReallyABlank() { 
        this.faceValueChar = ' ';
        this.points = 0;
        // TODO this might break the computer's stuff, i added this line feb 15:
        this.intendedValueChar = 'e';
    }
    // added 20110511 as part of updating board with blank when play and rack contain blank
    public void makeABlank() { 
        this.faceValueChar = ' ';
        this.points = 0;
    }

    @Override
    public int compareTo(Tile o) {
        int iReturn = 0;
        char cThis = this.faceValueChar;
        char cOther = o.faceValueChar;
        if (cThis > cOther) iReturn = 1;
        else if (cThis < cOther) iReturn = -1;
        else iReturn = 0;
        return iReturn;
    }
    public String toString() {
        return Character.toString(faceValueChar);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + faceValueChar;
        result = prime * result + intendedValueChar;
        result = prime * result + points;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (faceValueChar != other.faceValueChar)
            return false;
        if (intendedValueChar != other.intendedValueChar)
            return false;
        if (points != other.points)
            return false;
        return true;
    }
}
