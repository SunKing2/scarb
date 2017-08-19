package com.scramblelovers.scrabble;

/**
 * A View acts as the client layer.
 * It is a MVC view where the game is the controller.
 * The methods are intended to be called by a controller in order
 * to display changes to the game's state.
 * 
 * This interface may also be reused in a way such as:
 * The client layer delegates its view methods to the actual gui 
 * which may also be an instance of a View. 
 * @author Louie van Bommel
 *
 */
public interface View {

    public void setTitle(String title);
    //public StartupParameters getStartupParameters();
    public void updateBoard(char[][] board);
    
    public void updateRack(int playerNumber, String letters);
    public void updateScore(int playerNumber, int score);
    public void showFinish(int playerNumber, Scoresable scores);
    public void showMessage(int playerNumber, String message, boolean undoPlay);
    public void setPlayEnabled(int playerNumber, boolean playEnabled);


}