package com.scramblelovers.scrabble;
import java.util.*;

public class Rack {
    private List<Tile> tiles = new ArrayList<Tile>();
    
    public Rack() { }  // main ctr for most of the main parts of the game
    public Rack(Collection<Tile> tilesToAdd) { addTiles(tilesToAdd); }
    public Rack(String rackLetters) {
        for (char c: rackLetters.toCharArray()) {
            this.add(new Tile(c, Globals.getPoints(c)));
        }
    }
    
    public void addTiles(Collection<Tile> tilesToAdd) { tiles.addAll(tilesToAdd); }
    public Tile get(int tileNumber) { return tiles.get(tileNumber); }
    public boolean remove(Tile tile) { return tiles.remove(tile); }
    public Tile remove(int tileNumber) { return tiles.remove(tileNumber); }
    public boolean removeBlank() { return tiles.remove(getBlankTileInRack()); }
    public int size() { return tiles.size(); }
    private void add(Tile tile) { tiles.add(tile); }
    
    public Tile getBlankTileInRack() {
        for (Tile t: tiles) {
            if (t.getFaceValueChar() == ' ') {
                return t;
            }
        }
        return null;
    }
    
    public boolean containsAll(Collection<Tile> tilesUsed) {
        boolean bReturn = true;
        // all this to ensure tiles are on rack
        Rack clonedRack = this.myClone();
        for (Tile tile: tilesUsed) {
            bReturn = clonedRack.remove(tile);
            if (bReturn == false) {
                bReturn = clonedRack.removeBlank();
            }
        }
        return bReturn;
    }
 
   // sorts rack and returns it as a String
    public String toString() {
        String sReturn = "";
        // TODO this has side effect of permanently sorting rack
        Collections.sort(tiles);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tiles.size(); i++) {
            sb.append(tiles.get(i).toString());
        }
        sReturn = sb.toString();
        return sReturn;
    }
    public Rack myClone() {
        Rack rack = new Rack();
        rack.addTiles(tiles);
        return rack;
    }
}
