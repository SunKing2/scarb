package com.scramblelovers.scrabble;

public interface Model {

    public void setStartupParameters(StartupParameters startupParameters);
    public StartupParameters getStartupParameters();
    
    public Dictionary getDictionary();
    public Board getBoard();
    public void setBoard(Board board);
    public void setEmpty(boolean empty);
    public boolean isEmpty();
    public Rack getRack(int playerNumber);
    public String getRackAsString(int playerNumber);
    public void setPlayer(int playerNumber, PlayerModel player);
    public PlayerModel getPlayer(int playerNumber);
    public TileBag getLetterBag();
    public void setScores(Scores scores);
    public Scores getScores();
}
