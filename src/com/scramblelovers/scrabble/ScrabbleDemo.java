package com.scramblelovers.scrabble;

import java.util.*;

public class ScrabbleDemo {
    
    //  302 seconds to do this simulation until it finds a word repeating  21 times
    // 1214 seconds to do this simulation until it finds a word repeating 100 times
    private static final int minOutput = 25; //50;
    private static final int stopOnFirst = 18000; // 8037; //2400; // stop condition for # times repeating
    private static final boolean showBoard = false;
    private static final boolean showWordsDuring = false;
    private static final boolean bShowTooSmall = showWordsDuring;
    private static HashMap<String, Integer> wc = new HashMap<String, Integer>();
    private Board board;
    private Dictionary dict;
    private Bot bot;
    public ScrabbleDemo(Board board, Dictionary dict) {
        this.board = board;
        this.dict = dict;
        bot = Globals.bot;
    }
    public final void demo(boolean stopOnConsistentAverageScore, boolean showGameOver) {


        long iStartTime = System.currentTimeMillis();
        int nCompletedGames = 0;
        int accumulatedPoints = 0;
        int score = 0;
        int average = 0;
        
        new Stack<Integer>();
        while (score != -1) {
            try {
                score = playOneGame(false);
            } 
            catch (Exception e) {
                System.out.println("sd.demo:" + e);
                continue; // not interested in a score of incomplete game
            }
            if (score != -1) {
                nCompletedGames++;
                accumulatedPoints += score;
                average =  accumulatedPoints/nCompletedGames;
                if (showBoard) System.out.println(board);

            }
        }
        System.out.println("Game over. Score: " + score + 
                " games:" + nCompletedGames + " avg:" + average);
        showElapsedTime(iStartTime);
        showWordsResults();
        
    }

    public int playOneGame(boolean sameGame) throws Exception {
        board = new Board(0);
        TileBag bag = new TileBag(sameGame);
        
        String rack = "";

        // Embarrassingly, I haven't coded the ability of bot to start the game,
        // so I play this starter paly.
        Play playBot = new Play(false, 7, 7, "er");
        placePlay(board, playBot);

        int score = playGame(bag, rack, 100);
        return score;
    }
    public int playGame(TileBag bag, String rack, int maxTurns) throws Exception {
        Play playBot;
        int score = 0;
        int iTurn = 0;
        try {
            while(bag.getSize() > 6 && iTurn++ < maxTurns) {

                rack = fillRack(rack, bag);
                playBot = getBotPlay(rack, false);

                int points = ScrabbleScorer.getScore(board, playBot, rack);
                score += points;
//                System.out.println("Rack:" + rack + " remining:" + bag.getSize());
//                System.out.println("  " + playBot + ' ' + points + ' ' + score);
                List<Tile> lettersPlayed = placePlay(board, playBot);
                rack = removeTiles(rack, lettersPlayed);

                if (playBot == null) {
                    System.out.println("ScrabbleDemo.pog has null play, expect exception:");
                }
                String botWord = playBot.getWord();
                
                if(botWord.length() > 2) {
                    if (showWordsDuring) System.out.println("bot: " + botWord);

                    int iCount = 1;
                    Integer iMap = wc.get(botWord);
                    if (iMap != null) {
                        iCount = iMap + 1;
                    }
                    wc.put(botWord, iCount);
                    if (iCount >= stopOnFirst) {
                        return -1;
                    }
                }
                else {
                    if (bShowTooSmall) System.out.println("too small: " + botWord.length()  + botWord);
                }
            }
            // game over
        }
        catch (Exception exc) {
            System.out.println("Exception throwing ScrabbleDemo.playGame:" + exc);
            throw (exc);
        }
        return score;
    }
    public double showElapsedTime(long iStartTime) {
        long iEndTime = System.currentTimeMillis();
        long iElapsedTime = iEndTime - iStartTime;
        Double dElapsed = iElapsedTime/1000.0;
        System.out.println("Elapsed seconds:" + dElapsed);
        return dElapsed;
        
    }
    private void showWordsResults() {
        System.out.println("results:");
        for (String sKey: wc.keySet() ) {
            int quant = wc.get(sKey);
            if (quant >= minOutput) {
                System.out.println(sKey + ' ' + quant);
            }
        }
    }
    private String fillRack(String existingRack, TileBag bag) {
        //System.out.println("rack filling:" + existingRack);
        StringBuilder sb = new StringBuilder(existingRack);
        int iMissing = 7 - existingRack.length();
        for (int j = 0; j < iMissing; j++) {
            ;
            sb.append(bag.extractTile().getFaceValueChar());
        }
        String rack = sb.toString();
        //System.out.println("rack filling: is now:" + rack);
        return rack;
    }
    private Play getBotPlay(String rack, boolean verbose) {
        return bot.getBotPlay(board, dict, rack, verbose, false);
    }
    
    private String removeTiles(String existingRack, List<Tile> letters) {
        for (Tile let: letters) {
            char ch = let.getFaceValueChar();
            existingRack = removeTile(existingRack, ch);
        }
        return existingRack;
    }

    private static String removeTile(String rack, char letter) {
        int iStartLen = rack.length();
        String sReturn = StringStuff.subtractLetter(rack, letter);
        if (sReturn.length() == iStartLen) {
            sReturn = StringStuff.subtractLetter(sReturn, ' '); // must be a blank, so try blank
        }
        return sReturn;
    }
    public void showBoard() {
        System.out.println(board);
    }
    public static void main(String[] args) {
        Board board = new Board(0);
        String dictionaryFileName = "dict";
        Dictionary dict = new Dictionary(dictionaryFileName);
        ScrabbleDemo bot = new ScrabbleDemo(board, dict);
        //bot.demoTryingLeaves();
        
//      int numberOfGamesToPlay = 1;
//      boolean stopOnConsistentAverageScore = false;
//      boolean showGameOver = true;
//        int numberOfGamesToPlay = 50000;
        boolean stopOnConsistentAverageScore = false;
        boolean showGameOver = false;
        
        bot.demo(stopOnConsistentAverageScore, showGameOver);
        System.out.println("All done.");
    }
    public static List<Tile> placePlay(Board board, Play play) {
        List<Tile> lets = board.placePlay(play, null);
        return lets;
    }    
}
