package com.scramblelovers.sundog;
import java.util.Collection;


public interface Nodable {

    public Nodable getChild(char letter);
    public Collection<Node> getChildren();
    public String getLettersAsString();
    public char getLetter();
}
