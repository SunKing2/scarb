package com.scramblelovers.sundog;

import java.util.Set;
import java.util.TreeSet;
import java.io.IOException;
import java.util.HashSet;

import com.scramblelovers.scrabble.*;
import com.scramblelovers.types.*;

public class LouieDawgBot implements Bot {
    
    private Dawg dawg;
    private DawgStuff dawgStuff;
    Leaves leaves = Leaves.makeLeaves();
    
    public LouieDawgBot() {
        dawg = readDawgFromFile();
        dawgStuff = makeDawgStuff();
    }
    
    @Override
    public Play playWord(Rack rack, Board board,
            Dictionary dict)
            throws Exception {
        
        String alphagram = rack.toString().toLowerCase();
        Play play = null;
        play = getBotPlay(board, dict, alphagram, false, true);
        return play;
    }
    @Override
    public Play getBotPlay(Board board, Dictionary dict, String realRealRack,
            boolean verbose, boolean throwSimulatedExceptions) {
        //System.out.println("ldb.gbp real rack:" + realRack);
        // probably excessive:
        String realRack = new String(realRealRack);
        double dScoreHighest = -30.0;
        Play playReturn = null;
        
        Play play;
        double dScore = -30.0;

        for (String rack : replaceBlanksInRack(realRack)) {
            //System.out.println("ldb.gbp current rack:" + rack);
        
            play = null;
            try {
                play = getBotPlayHorizontal(board, dict, realRack, rack, verbose, throwSimulatedExceptions);
            }
            catch (Exception exc) { }
            // 20110310 replaced rack with realRack here and below
            dScore = leaves.getScoreWithLeave(board, realRack, play);
            //System.out.println("ldb.gbp sifting thru:" + play + ' ' + dScore);
            if (play != null && dScore > dScoreHighest) {
                playReturn = new Play(play.isHorizontal(), play.getRow(), play.getColumn(), play.getWord());
                dScoreHighest = dScore;
            }
            
            play = null;
            try {
                play = getBotPlayHorizontal(board.transpose(), dict, realRack, rack, verbose, throwSimulatedExceptions);
                play = play.transpose(); // NOTE TRANSPOSE!
            }
            catch (Exception exc) { }
            // 20110310 replaced rack with realRack here and above
            dScore = leaves.getScoreWithLeave(board, realRack, play);
            //System.out.println("ldb.gbp sifting thru vert:" + play + ' ' + dScore);
            if (play != null && dScore > dScoreHighest) {
                playReturn = new Play(play.isHorizontal(), play.getRow(), play.getColumn(), play.getWord());
                dScoreHighest = dScore;
            }
        }
        if (playReturn == null) {
            throw new RuntimeException(Msg.msg("noPlay"));
        }
        if (dScoreHighest < -3.00) {
            throw new RuntimeException(Msg.msg("noGoodPlay"));
        }
        //System.out.println("ldb.gbp returning:" + playReturn);
        return playReturn;
    }

    private Set<String> replaceBlanksInRack(String realRack) {
        Set<String> setReturn = new HashSet<String>();
        for (char chBlank: blankLettersToTry(realRack)) {
    //        int iVowels = StringStuff.countVowels(rack);
    //        if (iVowels > 2) {
    //            chBlank = 's';
    //        }
            
            String rack = new String(realRack);
            // can't handle blanks so replace up to 2 of them all with 'e'
            rack = StringStuff.replaceAll(rack, ' ', chBlank, 2);
            // can't handle blanks so replace up to 2 of them all with 'e'
            rack = StringStuff.replaceAll(rack, '?', chBlank, 2);
            // TODO remove toLowerCase it is wasteful
            rack = StringStuff.alphagram(rack).toLowerCase();  // in case some routine needs it.
            setReturn.add(rack);
        }
        return setReturn;
    }
    private char[] blankLettersToTry(String rack) {
        // removed DCGMHV because the score calculation bug will be compounded
        if (StringStuff.countVowels(rack) >= 3) {
            return Globals.blanksToTryNoVowels;
        }
        else {
            return Globals.blanksToTryWithVowels;
        }
    }
    
    private Play getBotPlayHorizontal(Board board, Dictionary dict, String realRack, String rack,
            boolean verbose, boolean throwSimulatedExceptions) {
        Set<Play> plays = generateAllPlays(board, dict, rack);
        return getBestPlay(board, dict, realRack, rack, plays);
    }

    private Play getBestPlay(Board board, Dictionary dict, String realRack,
            String rack, Set<Play> plays) {
        Play playReturn = null;
        double dBestPoints = -30.0;
        for (Play play: plays) {
            //System.out.println("ldb.gbp looking at play:" + play);
            
            // FIXME this should not be necessary.  It is here coz there is a bug in the play maker
            if (!Validator.validPositionAndWords(play, board, dict, true, true)) {
                //System.out.println("ldb.gbph: validPositionAndWords failed:" + rack + ' ' + play);
//                System.out.println(board);
//                System.exit(1);
                continue;
            }
            
            // FIXME another! this should no longer be necessary.  It is here in case a bug reappears
            if (!onRack(board, rack, play)) { 
              System.out.println("ldb.gbph: onrack failed:" + rack + ' ' + play);
              System.out.println(board);
              System.exit(1);
                continue;
            }
            
            double pts = getValueOfPlay(board, realRack, play);
            //System.out.println("ldb.gbp pts:" + pts);
            if (pts > dBestPoints) {
                playReturn = play;
                //System.out.println("ldb.gbp best play now:" + playReturn);
                dBestPoints = pts;
            }
        }
        if (playReturn == null) {
            throw new RuntimeException("ldb.gbp nuthin here");
        }
        //System.out.println("ldb.gbp play:" + playReturn);
        return playReturn;
    }

    private double getValueOfPlay(Board board, String realRack, Play play) {
        double pts = leaves.getScoreWithLeave(board, realRack, play);
        return pts;
    }

    private Set<Play> generateAllPlays(Board board, Dictionary dict, String rack) {
        Set<Play> plays = new TreeSet<Play>(); 
        for (int row = 0; row < Globals.TILES_HIGH; row++) {
            Row rowObject = new Row(board, dict, row);
            
            //System.out.println("ldb.gbph:" + row);
            
            // FIXME this one line fix was created 20110309 coz of not on rack eilnruu unrulier 9a:
            dawgStuff.thisHorribleMethodWouldNotBeNecessaryIfWeRetrievedPlaysRight();
            
            rowObject.spew(dawgStuff, dawg, rack, true);
            Set<Play> playsRow = dawgStuff.getLegalPlays(row);
            plays.addAll(playsRow);
        }
        return plays;
    }

    private boolean onRack(Board board, String rack, Play play) {
        boolean containsAll;
        containsAll = new Rack(rack).containsAll(board.asLetters(play));
        return containsAll;
    }
    public static Dawg readDawgFromFile() {
        return readDawgFromFile("sundawg.txt");
    }
    public static Dawg readDawgFromFile(String fileName) {
        Dawg dawg = null;
        try {
            dawg = DawgSerializer.readDawg(fileName);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return dawg;
    }
    public static DawgStuff makeDawgStuff() {
        //return new DawgStuff(makeDawg());
        //return new DawgStuff(StaticFunctions.readDawgFromFile());
        return new DawgStuff();
    }
}
