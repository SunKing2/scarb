package com.scramblelovers.scrabble;

public interface Bot {

    Play playWord(Rack rack, Board board, Dictionary dict) throws Exception;

    Play getBotPlay(Board board, Dictionary dict, String rack,
            boolean verbose, boolean throwSimulatedExceptions);
}
