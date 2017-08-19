package com.scramblelovers.scrabble;
import views.View;

public interface Controller {
    public void setModel(Model model);
    public Model getModel();
    
    public void setView(View view);
    public View getView();
    public void placeAndRecordMove(int playerNumber, Play play, boolean cleanUpPlay);
    public boolean processPlayerChange(int playerNumber);
    public void requestFinish();
    public void processMessageForPlayer(int playerNumber, String message, boolean undoPlay);
    boolean showHint(int playerNumber);
    boolean finishCheck();
}
