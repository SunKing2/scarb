package com.scramblelovers.scrabble;
import i10n.Msg;

public class StartupParameters {
    private String[] playerName = { "humanny", "computery" };
    private String dictionaryName = Globals.dictionaryName;
    private boolean displayComputersLetters = false;
    private boolean computerStarts = false;
    private int boardType = 0;
    private String difficultyLevel = Msg.msg("defaultDifficultyLevel");
    private String terminatingCondition = Msg.msg("letterBagEmpty");
    private int boardWidth = Globals.TILES_WIDE;
    private boolean sameRandomSeed = false;

    public String getPlayerName(int playerNumber) { return playerName[playerNumber]; }
    public void setPlayerName(int playerNumber, String name) { playerName[playerNumber] = name; }
    public String getDictionaryName() { return dictionaryName; }
    public void setDictionaryName(String dictionaryName) { this.dictionaryName = dictionaryName; }
    public boolean isDisplayComputersLetters() { return displayComputersLetters; }
    public void setDisplayComputersLetters(boolean displayComputersLetters) { this.displayComputersLetters = displayComputersLetters; }
    public boolean isComputerStarts() { return computerStarts; }
    public void setComputerStarts(boolean computerStarts) { this.computerStarts = computerStarts; }
    public int getBoardType() { return boardType; }
    public void setBoardType(int boardType) { this.boardType = boardType; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public String getTerminatingCondition() { return terminatingCondition; }
    public void setTerminatingCondition(String terminatingCondition) { this.terminatingCondition = terminatingCondition; }
    public void setBoardWidth(int boardWidth) { this.boardWidth = boardWidth; }
    public int getBoardWidth() { return boardWidth; }
    public void setSameRandomSeed(boolean sameRandomSeed) { this.sameRandomSeed  = sameRandomSeed; }
    public boolean getSameRandomSeed() { return sameRandomSeed; }
}
