package com.scramblelovers.sundog;

import java.io.Serializable;
import java.util.*;


public class Node implements Serializable {
    private static final long serialVersionUID = 942436792401292679L;

    public char letter;
    
    // package protection on these so Dawg can reference it directly:
    Node[] kid = new Node[26];
    int id;
    boolean terminator = false;

    Node(int id, char letter) {
        this.id = id;
        this.letter = letter;
    }

    public Node getChild(char ch) {
        //System.out.println("node:getChild()" + ch);
        return kid[ch - 'a'];
    }
    public Collection<Node> getChildren() {
        Collection<Node> col = new ArrayList<Node>();
        for (Node n: kid) {
            if (n != null) {
                col.add(n);
            }
        }
        return col;
    }
    public boolean isTerminal() {
        return terminator;
    }

    public String getLettersAsString() {
        char[] cTmp = new char[26];
        for (int i = 0, iCount = 0; i < 26; i++) {
            if (kid[i] != null) {
                cTmp[iCount++] = kid[i].letter;
            }
        }
        return new String(cTmp).trim();
    }
    public char getLetter() {
        return letter;
    }
    public static boolean identical(Node n1, Node n2) {
        if (n1.id == n2.id) {
            return true;
        }
        if (n1.letter != n2.letter || n1.terminator != n2.terminator) {
            return false;
        }
        boolean bReturn = true;
        for (int i = 0; i < 26; i++) {
            if (n1.kid[i] == null && n2.kid[i] == null)
                ; // equal
            else if (n1.kid[i] != null && n2.kid[i] != null) {
                if (!identical(n1.kid[i], n2.kid[i])) {
                    bReturn = false;
                    break;
                }
            } else { // one is null, other is not
                bReturn = false;
                break;
            }
        }
        return bReturn;
    }
}