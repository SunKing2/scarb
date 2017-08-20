package com.scramblelovers.sundog;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.scramblelovers.scrabble.*;

public class DawgStuffTest extends StaticBotMethods0BaseClass {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

  @Test
  public final void testOnRack() {
      StaticFunctions.placePlay(board, "er", "8h");
      System.out.println(board);
      StaticFunctions.assertBotPlay(board, dict, "eilnruu", "unrulier i6");
  }
}
