package com.scramblelovers.scrabble;
public class Scores {

    private String[] playerName = new String[2];
    private int[] playerScore = new int[2];
    private int[] playerMoves = new int[2];
    
    public void initScoreWithName(int i, String playerName, int score, int moves) {
        this.playerName[i] = playerName;
        this.playerScore[i] = score;
        this.playerMoves[i] = moves;
    }
    public int incrementScore(int playerNumber, int amount) {
        playerScore[playerNumber] += amount;
        if (playerScore[playerNumber] < 0) {
            playerScore[playerNumber] = 0;
        }
        return playerScore[playerNumber];
    }
    public void incrementMoves(int playerNumber) { playerMoves[playerNumber]++; }
    public String[] getPlayerName() { return playerName; }
    public int[] getPlayerScore() { return playerScore; }
    public int[] getPlayerMoves() { return playerMoves; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerScore.length; i++) {
            sb.append("Player:" + playerName[i] + ' ' + playerScore[i] + ' ' + playerMoves[i]);
            sb.append('\n');
        }
        return sb.toString();
    }
}
