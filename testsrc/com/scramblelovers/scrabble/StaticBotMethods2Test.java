package com.scramblelovers.scrabble;

import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.StaticFunctions;

public class StaticBotMethods2Test extends StaticBotMethods0BaseClass {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @Test
    public final void testPlayWord02() {
        String[][] sPlays = {
                {"hedge", "h4"},
                {"outlined", "6a"},
                {"zoology", "a4"},
                {"fundi", "e2"},
                {"qat", "i3"},
                {"pew", "8g"},
                {"vex", "d1"},
                {"reefy", "2b"},
                {"aecia", "1f"},
                {"guv", "9a"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // anything but section 2i coz section creates a word isqat which is invalid
        // I DON'T THINK THIS IS LEGAL:section 2i for 90 points, leave:( ) 0.0 = 90.0
        // woe i8 for 7 points, leave:( cist) 0.0 = 7.0
        // ow i7 for 10 points, leave:( ceist) 0.0 = 10.0
        // so 9h for 20 points, leave:( ceit) 0.0 = 20.0
        // picotees g8 for 63 points, leave:( ) 0.0 = 63.0
        // society d9 for 74 points, leave:( ) 0.0 = 74.0
        // cosiest j6 for 75 points, leave:( ) 0.0 = 75.0
        // erotics 9g for 82 points, leave:( ) 0.0 = 82.0
        StaticFunctions.assertBotPlay(board, dict, " ceiost", "erotics 9g");
    }
    @Test
    public final void testPlayWord03() {
        String[][] sPlays = {
                {"er", "h8"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // endorsed 9d for 62 points, leave:() 0.0 = 62.0
        StaticFunctions.assertBotPlay(board, dict, "neddoes", "endorsed 9d");
    }
    @Test
    public final void testPlayWord04() {
        String[][] sPlays = {
                {"er", "h8"},
                {"endorsed", "9d"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // avert 8k for 39 points, leave:(gk) -6.64 = 32.36
        StaticFunctions.assertBotPlay(board, dict, "gtvaekr", "avert 8k");
    }
    @Test
    public final void testPlayWord05() {
        String[][] sPlays = {
                {"er", "h8"},
                {"endorsed", "9d"},
                {"avert", "8k"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        StaticFunctions.assertBotPlay(board, dict, "gkapcna", "knap 8a");
    }
    @Test
    public final void testPlayWord06() {
        String[][] sPlays = {
                {"er", "h8"},
                {"endorsed", "9d"},
                {"avert", "8k"},
                {"knap", "8a"},
                {"vague", "10b"},
                {"teg", "11e"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        StaticFunctions.assertBotPlay(board, dict, "ctnruti", "tincture m1");
    }
    @Test
    public final void testPlayWord07() {
        String[][] sPlays = {
                {"er", "h8"},
                {"endorsed", "9d"},
                {"avert", "8k"},
                {"knap", "8a"},
                {"vague", "10b"},
                {"teg", "11e"},
                {"tincture", "m1"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        StaticFunctions.assertBotPlay(board, dict, "isxmoef", "ox 10i");
    }
    @Test
    public final void testPlayWord08() {
        String[][] sPlays = {
                {"er", "h8"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // frank 10f for 33 points, leave:(at) 1.31 = 34.31
        // fan 10f for 17 points, leave:(?art) 35.13 = 52.13
        // fragrant 9g for 66 points, leave:() 0.0 = 66.0
        // I think superbot said this was the best play, but I think there is a calculation
        // error in superbot
        // StaticFunctions.assertBotPlay(board, dict, "f ratna", "frank 10f");
        StaticFunctions.assertBotPlay(board, dict, "f ratna", "fragrant 9g");
    }
    @Test
    public final void testPlayWord09() {
        String[][] sPlays = {
                {"er", "h8"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // wired 10f for 24 points, leave:(?su) 29.95 = 53.95
        // whid 7g for 22 points, leave:(esu) 4.07 = 26.07
        // wud g6 for 11 points, leave:(?eis) 38.13 = 49.13
        StaticFunctions.assertBotPlay(board, dict, " wisdeu", "wud g6");
    }
    // this will fail if the bot is limiting blanks to consonants when there are >2 vowels
    // this is also tested by testPlayBingoWithE
    @Test
    public final void testPlayWord10() {
        String[][] sPlays = {
                {"bb", "6m"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // urbanize means blank is 'b'
        // brulzie n6 for 38 points, leave:(n) 0.67 = 38.67
        // zine l3 for 33 points, leave:(?ru) 24.95 = 57.95
        // urbanize n4 for 89 points, leave:() 0.0 = 89.0
        // brunizem m6 for 90 points, leave:() 0.0 = 90.0
        StaticFunctions.assertBotPlay(board, dict, "ruizne ", "brunizem m6");
    }
    // this will fail if the bot doesn't replace blanks with unlikely letters such as x
    @Test
    public final void testPlayWord11() {
        String[][] sPlays = {
                {"folia", "12a"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // moistly 11e for 10 points, leave:(?)  this was produced by not calling scorer with 'rack' parm
        // xylitols c6 for 96 points, leave:() 0.0 = 80.0
        // moistly 11e for 88 points, leave:() 0.0 = 88.0
        // tylosin 11e for 90 points, leave:() 0.0 = 90.0
        StaticFunctions.assertBotPlay(board, dict, "siyotl ", "tylosin 11e");
    }
    @Test
    public final void testPlayWord12() {
        String[][] sPlays = {
                {"xebec", "n10"},
                {"hairy", "o8"},
                {"hay", "m11"},
                {"vye", "13l"},
                {"qh", "8n"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        StaticFunctions.assertBotPlay(board, dict, "naihr a", "hisn 15l");
    }
    @Test
    public final void testPlayWord13() {
        String[][] sPlays = {
                {"teiid", "h8"},
                {"ay", "g8"},
                {"hoopers", "f6"},
                {"luau", "e3"},
                {"tumid", "4d"},
                {"tilak", "3c"},
          };
        
        StaticFunctions.placeThesePlays(board, sPlays);
        // djinn 12h for 26 points, leave:(lrt) -0.47 = 25.53
        // jam f2 for 28 points, leave:(?lnnrt) 14.63 = 42.63
        StaticFunctions.assertBotPlay(board, dict, " jlnnrt", "jam f2");
    }
}
