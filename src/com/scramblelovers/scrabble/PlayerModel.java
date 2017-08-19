package com.scramblelovers.scrabble;

public class PlayerModel {
    private Rack rack;
    private String name;
    boolean active = false;
    
    public PlayerModel() { this("PlayerX"); }
    public PlayerModel(String s) {
        name = s;
        rack = new Rack();
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public Rack getRack() {
        return rack;
    }
    // get the letters as a String
    public String getRackAsString() {
        return rack.toString();
    }
}
