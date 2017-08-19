package com.scramblelovers.scrabble;
import java.util.Collection;
import java.util.Map;
import com.scramblelovers.io.IOStuff;
import com.scramblelovers.types.StringStuff;

public class Leaves {
    
    private static Leaves singleton;
    
    private Leaves() {
        
    }
    public static Leaves makeLeaves() {
        if (singleton == null) {
            singleton = new Leaves();
            mapLeaves = IOStuff.xgetSuperleaves("superleaves.txt", true);            
        }
        return singleton;
    }

    // note I include blanks in the leave file, making the in-memory file huge!
    static Map<String, Double> mapLeaves = null;
    
    public double getScoreWithLeave(Board board, String rack, Play play) {
        int iScore;
        double dScore;
        
        // basic score
        // 20110317 added rack as a parameter here:
        iScore = ScrabbleScorer.getScore(board, play, rack);
        
        // find out which letters remain in rack after this play:
        String leaveLetters = getLeaveLetters(board, rack, play);
        
        //System.out.println("Leaves.gswl play:" + play + " rack:[" + rack + "] score:" + iScore + " RAW leaveLetters:[" + leaveLetters + "]");

        // convert all spaces to ? because the leave file requires it
        // then sort them coz the sorting would be screwed up after replacement
        leaveLetters = StringStuff.replaceAll(leaveLetters, ' ', '?', 2);
        leaveLetters = StringStuff.alphagram(leaveLetters);
        
        // lookup the letters in the superleaves file and retrieve their leave points
        double dLeave = getLeave(leaveLetters);
        
        // add basic score to the leave score and return it
        dScore = 0.0 + iScore + dLeave;
        //System.out.println("Leaves.gswl:" + play + ' ' + iScore + " + " + dLeave + " = " + dScore);
        return dScore;
    }

    public double getLeave(String leaveLetters) {
        double dLeave = 0.0;
        Double d = mapLeaves.get(leaveLetters);
        if (d != null) {
            dLeave = d;
        }
        else {
            // TODO : one day commenting this out will bite ya in the ass
            //System.out.println("map doesn't contain string:" + leaveLetters + '.');
        }
        return dLeave;
    }

    private String getLeaveLetters(Board board, String rack, Play play) {
        //System.out.println("Leaves.gll rack:" + rack);
        Collection<Tile> tilesUsed = board.asLetters(play);
        String lettersUsed = (new Rack(tilesUsed)).toString();
        //System.out.println("Leaves.gl used this many letters:" + tilesUsed.size());
        // 20110310 added the ' ' wildcard and created the subtractWord method with the wildcard parameter
        String leaveLetters = StringStuff.subtractWord(rack, lettersUsed, ' ');
        //System.out.println("Leaves.getLeaveLetters play:" + play + " =[" + rack + "] - [" + lettersUsed + "] = [" + leaveLetters + ']');
        return leaveLetters;
    }
    
}
