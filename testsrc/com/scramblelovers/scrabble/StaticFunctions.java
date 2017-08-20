package com.scramblelovers.scrabble;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.scramblelovers.sundog.*;

public class StaticFunctions {

    //private static String rack = StringStuff.alphagram("CYMESOL");

    private static Leaves superleaves = Leaves.makeLeaves();

    public static List<String> wds = Arrays.asList(
            "aa",
            "ah",
            "cwm",
            "cwms",
            "cyma",
            "cymae",
            "cymar",
            "cymars",
            "cymas",
            "cymatia",
            "cymatium",
            "cymbal",
            "cymbaleer",
            "cymbaleers",
            "cymbaler",
            "cymbalers",
            "cymbalist",
            "cymbalists",
            "cymbalom",
            "cymbaloms",
            "cymbals",
            "cymbidia",
            "cymbidium",
            "cymbidiums",
            "cymbling",
            "cymblings",
            "cyme",
            "cymene",
            "cymenes",
            "cymes",
            "cymlin",
            "cymling",
            "cymlings",
            "cymlins",
            "cymogene",
            "cymogenes",
            "cymograph",
            "cymographs",
            "cymoid",
            "cymol",
            "cymols",
            "cymophane",
            "cymophanes",
            "cymose",
            "cymosely",
            "cymous",
            "eh",
            "he",
            "hi",
            "loom",
            "looms",
            "moc",
            "mol",
            "mom",
            "slam",
            "zzz"
            );
	public static List<Tile> placePlay(Board board, Play play) {
		List<Tile> lets = board.placePlay(play, null);
		return lets;
	}
	public static List<Tile> placePlay(Board board, String word, String location) {
        return placePlay(board, getPlay(word, location));
	}
	
	public static Play getPlay(String word, String location) {
        boolean b = Pattern.compile("[a-o][1-9][0-5]*|[1-9][0-5]*[a-o]").matcher(location).matches();
        if (!b) throw new IllegalArgumentException(Msg.msg("invalidRcFormat"));
        
        word = word.toLowerCase();
        location = location.toLowerCase();
        Matcher m = Pattern.compile("[a-o]").matcher(location);
        int column = m.find( ) ? m.group(0).charAt(0) - 'a' : -1;
        
        m = Pattern.compile("[1-9][0-5]*").matcher(location);
        int row = m.find() ? Integer.parseInt(m.group(0)) - 1 : -1;
        
        boolean horizontal = location.charAt(0) <= '9';
        return new Play(horizontal, row, column, word);
	}
    public static Play getPlay(String wordAndLocation) {
        int space = wordAndLocation.indexOf(' ');
        String word = wordAndLocation.substring(0, space);
        String location = wordAndLocation.substring(space + 1);
//        System.out.println("Play ctr:" + wordAndLocation);
//        System.out.println("Play ctr word:" + word);
//        System.out.println("Play ctr location:" + location);
        return getPlay(word, location);
    }	
	public static Play placeAndRecordMoveWithLetters(Controller controller, int playerNumber, boolean horizontal, int row, int column, String word) {
		Play play = new Play(horizontal, row, column, word);
		controller.placeAndRecordMove(playerNumber, play, false);
		return play;		
	}
	
	
    // pass me an array of plays such as:
	//  private static final String[][] sPlays = {
	//      {"kafir", "h4"},  
	//      {"fazes", "6h"},
	//      {"troth", "n2"},
	//  };
    // place all plays in sPlays[][] on the board
    public static void placeThesePlays(Board board, String[][] sPlays, int howManyOfThese) {
        for (int i = 0; i < howManyOfThese; i++) {
            String word = sPlays[i][0];
            String location = sPlays[i][1];
            Play play = getPlay(word.toLowerCase(), location.toLowerCase());
            StaticFunctions.placePlay(board, play);
        }
        System.out.println(board);
    }
    public static void placeThesePlays(Board board, String[][] sPlays) {
        placeThesePlays(board, sPlays, sPlays.length);
    }

    public static Play assertBotPlay(Board board, Dictionary dict, String rack, String expectedPlay) {
        //System.out.println("sf.abp rack:" + rack);
        boolean verbose = true;
        rack = rack.toLowerCase();
        expectedPlay = expectedPlay.toLowerCase();
        Play play = Globals.bot.getBotPlay(board, dict, rack, verbose, false);
        assertNotNull("play for rack:" + rack + " is null when expected:" + expectedPlay, play);
        
        Play playExpected = getPlay(expectedPlay);
        System.out.println(StaticFunctions.getPlayStats(board, rack, playExpected, "expected"));
        
        
        String actualPlay = play.toString();
        String msg = getPlayStats(board, rack, play, "bot generated");
        System.out.println(msg);
        assertEquals("plays differ", expectedPlay, actualPlay);
        
        return play;
    }
    public static String getPlayStats(Board board, String rack, Play play, String prelude) {
        int points = ScrabbleScorer.getScore(board, play, rack);

        String lettersRemainingAfterPlay = getLettersRemainingAfterPlay(board, rack, play);
        //System.out.println("sf.getPlayStats remaining before:" + lettersRemainingAfterPlay + '.');
        lettersRemainingAfterPlay = StringStuff.replaceAll(lettersRemainingAfterPlay, ' ', '?', 2);
        lettersRemainingAfterPlay = StringStuff.alphagram(lettersRemainingAfterPlay);
        //System.out.println("sf.getPlayStats remaining after:" + lettersRemainingAfterPlay + '.');
        double leave = getLeave(lettersRemainingAfterPlay);
        double pointsPlusLeave = addAndRoundTo2(points, leave);
        
        String msg = "" + prelude + ' ' + play + " for " + points + " points, leave:(" + 
        lettersRemainingAfterPlay + ") " + leave + " = " + pointsPlusLeave;
        return msg;
    }
    public static String getLettersRemainingAfterPlay(Board board, String rack, Play play) {
        String lettersUsedInPlay = new Rack(board.asLetters(play)).toString();
        String lettersRemainingAfterPlay = StringStuff.subtractWord(rack, lettersUsedInPlay);
        int iExpectedRemaining = rack.length() - lettersUsedInPlay.length();
        int iActualRemaining = lettersRemainingAfterPlay.length();
        //System.out.println("Board.getLettersRemainingAfterPlay exp:" + iExpectedRemaining + " actual:" + iActualRemaining); 
        int iBlanks = iActualRemaining - iExpectedRemaining;
        char blankChar = '?';
        if (lettersRemainingAfterPlay.indexOf(' ') >=0) {
            blankChar = ' ';
        }
        for (int i = 0; i < iBlanks; i++) {
            System.out.println("Board.getLettersRemainingAfterPlay deleting a '" + blankChar + "'");
            lettersRemainingAfterPlay = StringStuff.subtractLetter(lettersRemainingAfterPlay, blankChar);
        }
        return lettersRemainingAfterPlay;
    }
    public static double addAndRoundTo2(int points, double leave) {
        // get it to 2 decimal places... there must be a more elegant way to 'round' a double to 2 places
        double pointsPlusLeave = ( Math.round(100*(points + leave)) / 100.0);
        return pointsPlusLeave;
    }
    public static double getLeave(String theseLetters) {
        double leave = 0.0;
        Double lv = superleaves.getLeave(theseLetters);
        if (lv != null) leave = lv.doubleValue();
        return leave;
    }

    // I use a factory method in case you want to create and test
    // your own Dawg. 
    public static Dawg makeDawg(Collection<String> list) {
        return new Dawg(list);
    }
    public static Dawg makeDawgFromScratch() {
        Set<String> ww = new TreeSet<String>();
        Set<String> listTo4 = (Set<String>) IOStuff.readFile("dict", 2, 15, true, false);
        ww.addAll(listTo4);
        ww.addAll(wds);
        return makeDawg(ww);
    }
    public static Dawg readDawgFromFile() {
        return StaticFunctions.readDawgFromFile("sundawg.txt");
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
    public static void setCrossCheckLetters(Row row, int column, String letters) {
    	row.getRowSquares().get(column).setCrossCheckLetters(letters);
    }
    public static void setLetter(Row row, int column, char letter) {
        row.getRowSquares().get(column).setLetter(letter);
    }
    public static void addAnchor(Row row, int column) {
        Square sq = row.getRowSquares().get(column);
        sq.setAnchor(true);
        //System.out.println("row.addAnchor:" + column);
        row.getAnchors().add(sq);
    }
    
}
