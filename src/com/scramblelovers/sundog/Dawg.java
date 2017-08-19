package com.scramblelovers.sundog;
import java.io.Serializable;
import java.util.*;


// A dawg takes awhile to create, more than a minute on a fast computer, so we
// typically store it in a file.
// Look at DawgSerializer to re-create the serialized Dawg object, so it can be read
// in case of a dictionary change, for example
public class Dawg implements Serializable {
    private static final long serialVersionUID = 5057641363471882621L;
    
    Node[][] pruneList = new Node[450000][2];
    private Node nRoot = new Node(0, '^');
    
    public Dawg(Collection<String> words) {
        int id = 0;
        int ip = 0;
        for (String word : words) {
            word = word.toLowerCase();
            Node nParent = nRoot;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                // if there is no node create one
                int ix = ch - 'a';
                Node nKid = nParent.kid[ix];
                if (nKid == null) {
                    ip++;
                    nKid = new Node(++id, ch); // create new node
                    nParent.kid[ix] = nKid; // add this node to its parent
                    pruneList[id][0] = nKid; // add it to prunelist
                    pruneList[id][1] = nParent; // add it to prunelist
                }
                nParent = nKid;
            }
            nParent.terminator = true;
        }
        System.out.println("starting prune..." + getNodeCount());
        // find identical nodes and prune them
        for (id = ip; id > 0; id--) { // go backwards, ignore #0
            for (int j = 1; j < id; j++) { // search thru nodes up to id
                // search all nodes for dupes
                if (Node.identical(pruneList[id][0], pruneList[j][0])) {
                    int iWhichKid = pruneList[id][0].letter - 'a';
                    // make parent of node point to (found) node
                    pruneList[id][1].kid[iWhichKid] = pruneList[j][0];
                    break;
                }
            }
        }
        System.out.println("done prune:" + getNodeCount());
        // added 20110307
        pruneList = null;
    }

    public Node getNodeAfterPrefix(String prefix) {
        //System.out.println("dawg.gnap:" + prefix);
        prefix = prefix.toLowerCase();
        Node tn = nRoot;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            tn = tn.getChild(ch);
        }
        return tn;
    }

    public int getNodeCount() {
        ArrayList<Integer> iAlreadyCounted = new ArrayList<Integer>();
        return count(iAlreadyCounted, nRoot);
    }

    private int count(ArrayList<Integer> iAlreadyCounted, Node n) {
        int iReturn = 0;
        if (n.id != 0 && !iAlreadyCounted.contains(n.id)) {
            iReturn = 1;
            iAlreadyCounted.add(n.id);
        }
        for (int i = 0; i < 26; i++) {
            Node kid = n.kid[i];
            if (kid != null) {
                iReturn += count(iAlreadyCounted, kid);
            }
        }
        return iReturn;
    }
    
    public Node getRootNode() {
        return nRoot;
    }

}