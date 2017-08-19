package com.scramblelovers.scrabble;

public class Globals {
    public static final int TILES_WIDE = 15;
    public static final int TILES_WIDE_LESS1 = TILES_WIDE - 1;
    public static final int TILES_HIGH = TILES_WIDE;
    public static final int TILES_HIGH_LESS1 = TILES_HIGH - 1;
    public static final String dictionaryName = "dict";
    public static int RACK_SIZE = 7;

    // *************** CHANGE ME WITH NEW BOT *******
    //public static final Bot bot = new ScrabblerBot();
    //public static final Bot bot = new demo.LouieBot();
    //public static final Bot bot = new demo.LouieBot2();
    //public static final Bot bot = new demo.LouieBot3();
    //public static final Bot bot = new jabble.JabbleBot();
    public static final Bot bot = new com.scramblelovers.sundog.LouieDawgBot();
    //public static final Bot bot = new demo.SuperBot();
    
//    public static final char[] blanksToTryNoVowels = "srntl".toCharArray();
//    public static final char[] blanksToTryWithVowels = "esarintlou".toCharArray();
//      public static final char[] blanksToTryNoVowels = "srntlhkcydgpmbf".toCharArray();
//      public static final char[] blanksToTryWithVowels = "esarintlouhkcydgpmbf".toCharArray();
      public static final char[] blanksToTryNoVowels = "spcdnthrmkgl".toCharArray();
/*    218 s
      109 e
      94  p
      93  c
      90  d
      87  n
      86  t
      81  h
      81  a
      78  i
      77  r
      77  m
      77  k
      73  g
      64  l
      58 ch:f now blank
      55 ch:b now blank
      49 ch:u now blank
      47 ch:o now blank
      46 ch:y now blank
*/      
      // really it is sepcdnthairmkglfbouy h=81, k=77 g=83, l=64, f=68, b=55, u=49, o=47, y=46
      // Here I take the most favorable and add all the vowels just coz this item will not
      // occur that often, and so can afford to use more letters
      public static final char[] blanksToTryWithVowels = "sepcdnthairmkgluo".toCharArray();
    
    public static final int[][] leaves = {
        {1, 5, 0,  1, 3, 0,   1, -8, 0}, // 704  696in440                                               702in4500
        {1, 6, 0,  1, 3, 0,   1, -8, 0}, // 703  703in220            704in1200  //698in1900 //702in2600 705in4500
        {1, 5, 0,  1, 2, 0,   1, -8, 0}, // 704  704in440  702in700  706in1200  //700in1900 //702in2600 699in4500
        {1, 5, 0,  1, 4, 0,   1, -8, 0}, // 704  705in440  703in700  704in1200  //704in1900 //702 in2600 698in4500
    };
    public static int [] lv = leaves[1]; 
    
    public static final String BOARD_VALUES[][] = {
        {
            "T  d   T   d  T",
            " D   t   t   D ",
            "  D   d d   D  ",
            "d  D   d   D  d",
            "    D     D    ",
            " t   t   t   t ",
            "  d   d d   d  ",
            "T  d   S   d  T",
            "  d   d d   d  ",
            " t   t   t   t ",
            "    D     D    ",
            "d  D   d   D  d",
            "  D   d d   D  ",
            " D   t   t   D ",
            "T  d   T   d  T",
            },

            {
            "T  D   T   D  T",
            " D   T   T   D ",
            "  D   D D   D  ",
            "D  D   D   D  D",
            "    D     D    ",
            " T   T   T   T ",
            "  D   D D   D  ",
            "T  D   S   D  T",
            "  D   D D   D  ",
            " T   T   T   T ",
            "    D     D    ",
            "D  D   D   D  D",
            "  D   D D   D  ",
            " D   T   T   D ",
            "T  D   T   D  T",
            },

            {
            "T  d  T",
            " D   D ",
            "  t t  ",
            "d  S  d",
            "  t t  ",
            " D   D ",
            "T  d  T",
            }
    };
    public static final char[][] LETTER_STATS = {
        { 'a',9,1},
        { 'b',2, 3},
        { 'c',2, 3},
        { 'd',4, 2},
        { 'e',12,1},
        { 'f',2, 4},
        { 'g',3, 2},
        { 'h',2, 4},
        { 'i',9, 1},
        { 'j',1, 8},
        { 'k',1, 5},
        { 'l',4, 1},
        { 'm',2, 3},
        { 'n',6, 1},
        { 'o',8, 1},
        { 'p',2, 3},
        { 'q',1,10},
        { 'r',6, 1},
        { 's',4, 1},
        { 't',6, 1},
        { 'u',4, 1},
        { 'v',2, 4},
        { 'w',2, 4},
        { 'x',1, 8},
        { 'y',2, 4},
        { 'z',1,10},
        { ' ',2, 0}
    };

    // for a letter c, what is the point value: example: Z=10, D=2
    public static int getPoints(char c) {
        int iReturn = 0;
        if (c == ' ' || c == '?') {
            iReturn = 0;
        }
        else  {
            int ix = c - 'a';
            try {
                iReturn = Globals.LETTER_STATS[ix][2];
            }
            catch (Exception exc) {
                System.out.println("handled and ignored Globals.getPoints exception for character whose ascii is:" + (int)c);
            }
        }
        return iReturn;
    }
}
