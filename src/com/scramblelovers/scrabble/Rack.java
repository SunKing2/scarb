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
    public boolean removeAllandFill(TileBag bag, Collection<Tile> tilesUsed) {
        boolean bRemoved, bFilled;
        bRemoved = removeAll(tilesUsed);
        bFilled = fillRack(bag);
        return bRemoved && bFilled;
    }
    public boolean removeAll(Collection<Tile> tilesUsed) {
        boolean bReturn = true;
        // all this to ensure tiles are on rack
        for (Tile tile: tilesUsed) {
            boolean x = this.remove(tile);
            if (x == false) {
                x = this.removeBlank();
                if (x == false) {
                    bReturn = false;
                    System.out.println("Rack.removeAll: I can't remove tile:" + tile);
                }
            }
        }
        return bReturn;
    }
    
    public boolean fillRack(TileBag bag) {
        boolean completelyFilled = true;

        int amountInBag = bag.getSize();
        int amountToAdd = Globals.RACK_SIZE - tiles.size();
        if (amountInBag < amountToAdd) {
            completelyFilled = false;
            amountToAdd = amountInBag;
        }
        for (int i = 0; i < amountToAdd; i++) {
            Tile ll = bag.extractTile();
            if (ll.getFaceValueChar() == ' ')
                ll.setIntendedValueChar('e');
            tiles.add(ll);
        }
        return completelyFilled;
    }
    public boolean change(TileBag bag) {
        boolean bReturn = false;
        if (bag.getSize() >= Globals.RACK_SIZE) {
            for (int i = 0; i < Globals.RACK_SIZE; i++)
                tiles.add(bag.extractTile());
            for (int i = 0; i < Globals.RACK_SIZE; i++) {
                bag.returnTile((Tile) tiles.get(0));
                tiles.remove(0);
            }
            bReturn = true;
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
