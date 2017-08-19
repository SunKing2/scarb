package demo;

import java.util.*;
import com.scramblelovers.scrabble.*;
import com.scramblelovers.scrabble.Dictionary;
import com.scramblelovers.types.StringStuff;

public class ScrabbleDemo {
    
    //  302 seconds to do this simulation until it finds a word repeating  21 times
    // 1214 seconds to do this simulation until it finds a word repeating 100 times
    private static final int minOutput = 12;
    private static final int stopOnFirst = 10000; // stop condition for # times repeating
    private static final boolean showBoard = false;
    private static final boolean showWordsDuring = false;
    private static final boolean bShowTooSmall = showWordsDuring;
    private static HashMap<String, Integer> wc = new HashMap<String, Integer>();
    private Board board;
    private Dictionary dict;
    private Bot bot;
    private Stack<Integer> score100;
    
    public ScrabbleDemo(Board board, Dictionary dict) {
        this.board = board;
        this.dict = dict;
        bot = Globals.bot;
    }
    public final void demo(int maxGames, boolean stopOnConsistentAverageScore, boolean showGameOver) {

        System.out.println("Playing this many games:" + maxGames);

        long iStartTime = System.currentTimeMillis();
        int nCompletedGames = 0;
        int accumulatedPoints = 0;
        int score = 0;
        int average = 0;
        
        score100 = new Stack<Integer>();
        while (score != -1 && nCompletedGames < maxGames) {
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
                // TODO this is really removing any verbosity:
                if (showGameOver) {
                    System.out.println("Game over. Score: " + score + 
                    " games:" + nCompletedGames + " avg:" + average);
                }
                if (showBoard) System.out.println(board);
                if (nCompletedGames % 100 == 0) {
                    System.out.println("                               adding to stack:" + average);
                    score100.add(average);
                    int sumOfDifferences = 0;
                    int size = score100.size();
                    if (size > 7) {
                        int compareTo = score100.elementAt(size - 7);
                        for (int i = 0; i < 4; i++) {
                            int iStack = score100.elementAt(size - i - 1);
                            System.out.println("stack " + i + ". " + iStack + " vs. " + compareTo);
                            sumOfDifferences += Math.abs(iStack - compareTo);
                        }
                        if (sumOfDifferences < 6 && stopOnConsistentAverageScore) return;
                    }
                }
            }
        }
        System.out.println("Played this many games:" + maxGames);
        System.out.println("Game over. Score: " + score + 
                " games:" + nCompletedGames + " avg:" + average);
        showElapsedTime(iStartTime);
        showWordsResults();
        
    }
    // this demo is applicable for ScrabblerBot which relies on leaves that are in Global.java
    // I think this plays x many games for each leave set in the Global file, and compares results
    public void demoTryingLeaves() {
        for (int i =  0; i < Globals.leaves.length; i++) {
            long iStartTime = System.currentTimeMillis();
            int nCompletedGames = 0;
            int accumulatedPoints = 0;
            int score = 0;
            int gamesPerLeave = 4500;
            // here's where I change the leave values:
            Globals.lv = Globals.leaves[i];

            while (nCompletedGames < gamesPerLeave && score != -1) {
                try {
                    score = playOneGame(false);
                } 
                catch (Exception e) {
                    continue;  // TODO do I really wanna continue or to just ignore the error?
                }
                if (score != -1) {
                    nCompletedGames++;
                    accumulatedPoints += score;
                }
            }
            System.out.println("!!!!! Results for round:" + i);
            System.out.println("Round:" + i + " over  games:" + nCompletedGames + " avg:" + accumulatedPoints/nCompletedGames);
            if (showBoard) System.out.println(board);
            showElapsedTime(iStartTime);
        }
    }   // plays a game until an error occurs, or bag is sorta emptyish
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
        catch (ArrayIndexOutOfBoundsException exc) {
            System.out.println("oh shit, i'm outa here:" + exc);
            exc.printStackTrace();
            throw new RuntimeException(exc);
        }
        catch (Exception exc) {
            // TODO this really kills any verbosity
            //exc.printStackTrace();
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
        int numberOfGamesToPlay = 50000;
        boolean stopOnConsistentAverageScore = false;
        boolean showGameOver = false;
        
        bot.demo(numberOfGamesToPlay, stopOnConsistentAverageScore, showGameOver);
        System.out.println("All done.");
    }
    public static List<Tile> placePlay(Board board, Play play) {
        List<Tile> lets = board.placePlay(play, null);
        return lets;
    }    
}
