package com.scramblelovers.scrabble;
import org.junit.Test;

import com.scramblelovers.scrabble.Play;
import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods1Test extends StaticBotMethods0BaseClass {

//    @Test
//    public final void testPlayWord00() {
//        String[][] sPlays = {
//        };
//        StaticFunctions.placeThesePlays(board, sPlays);
//        // 
//        StaticFunctions.assertBotPlay(board, dict, "insertrackhere", "fit 5g");
//    }
    @Test
    public final void testPlayWord01() {
        String[][] sPlays = {
              {"HUMPS", "H4"},
              {"TEG", "3H"},
              {"DEAN", "I2"},
              {"FATS", "8E"},
              {"TILED", "5J"},
              {"LIED", "E9"},
              {"BES", "11D"},
              {"IZ", "D12"},
              {"RE", "14C"},
              {"CO", "5J"}
        };
        StaticFunctions.placeThesePlays(board, sPlays);
        // trend 14b for 20 points, leave:(cdft) -8.65 = 11.35
        // lez 13b for 28 points, leave:(iiqst) -3.32 = 24.68
        // qi b13 for 28 points, leave:(eilst) 24.48 = 52.48
        StaticFunctions.assertBotPlay(board, dict, "eiilqst", "qi b13");
    }
    @Test
    public final void testPlayWord02() {
        StaticFunctions.placePlay(board, "scuff", "h8");
        System.out.println(board);
        // ki g7 for 13 points, leave:(eiioz) -0.79 = 12.21
        // zouk 10f for 37 points, leave:(eiii) -20.31 = 16.69
        // kief 11e for 22 points, leave:(iioz) -2.53 = 19.47
        // oik g11 for 22 points, leave:(eiiz) -1.14 = 20.86
        StaticFunctions.assertBotPlay(board, dict, "eiiikoz", "oik g11");
    }
    @Test
    public final void testPlayWord03() {
        String[][] sPlays = {
              {"FERE", "H7"},
              {"JIBE", "8E"},
              {"HOT", "9D"},
              {"SORN", "11H"},
              {"ALEE", "10B"},
              {"EYRY", "10K"},
              {"IE", "C11"},
              {"GRAVID", "13B"},
              {"FAE", "D12"},
              {"FANE", "D12"},
              {"TELEGAS", "14F"},
              {"AM", "15H"},
              {"PIU", "13K"},
              {"WAB", "15A"},
              {"AWE", "15A"},
              {"WOO", "9K"},
              {"ROB", "I10"},
              {"PER", "10G"},
              {"SO", "11E"},
              {"TI", "8M"},
              {"DUNCH", "O5"},
        };
        StaticFunctions.placeThesePlays(board, sPlays);
        // diss l12 for 12 points, leave:(dirz) 3.28 = 15.28
        // friz 7h for 17 points, leave:(dds) -0.11 = 16.89
        // drib g5 for 14 points, leave:(dsz) 4.97 = 18.97
        // jibed 8e for 15 points, leave:(dirsz) 8.35 = 23.35
        System.out.println(board);
        StaticFunctions.assertBotPlay(board, dict, "ddirsz", "jibed 8e");
    }
    @Test
    public final void testPlayWord04() {
        String[][] sPlays = {
                {"ZORIL", "H4"},
                {"LOUTED", "8H"},
                {"VARIED", "6F"},
                {"AEON", "5J"},
        };
        StaticFunctions.placeThesePlays(board, sPlays);
        // where 4k for 43 points, leave:(eo) -2.64 = 40.36
        // wheeze 4d for 42 points, leave:(or) 0.32 = 42.32
        StaticFunctions.assertBotPlay(board, dict, "eeehorw", "wheeze 4d");
    }
    @Test
    public final void testDawgBotDrollooniest() {
        String[][] sPlays = {
                {"droll", "h4"},
                {"doxie", "4h"},
                {"musth", "m2"},
                {"foamed", "2j"},
                {"if", "5j"},
                {"broil", "1g"},
                {"ya", "1n"},
                {"kye", "2f"},
                {"nova", "3c"},
                {"zag", "2b"},
                {"uh", "3m"},
                {"jeer", "4a"},
                {"jawed", "a4"},
        };
        StaticFunctions.placeThesePlays(board, sPlays);
        // error was due to setting playReturn = play somehow playReturn got overwritten later
        // when we changed the value later of play.  Fix was to create a new playReturn object
        // error was:   drollooniest  8d 48 251
        // isotone n6 for 68 points, leave:() 0.0 = 68.0
        // looniest 8h for 77 points, leave:() 0.0 = 77.0
        StaticFunctions.assertBotPlay(board, dict, "einoost", "looniest 8h");
    }
}
