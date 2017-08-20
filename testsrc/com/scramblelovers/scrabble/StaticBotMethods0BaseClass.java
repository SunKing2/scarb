package com.scramblelovers.scrabble;

//import static org.junit.Assert.*;
import org.junit.Before;

import com.scramblelovers.scrabble.Board;
import com.scramblelovers.scrabble.Dictionary;

public class StaticBotMethods0BaseClass{

  protected Board board;
  protected Dictionary dict;
  
  @Before
  public void setUp() throws Exception {
      board = new Board(0);
      dict = new Dictionary("dict");
  }
}
