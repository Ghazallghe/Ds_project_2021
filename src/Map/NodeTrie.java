package Map;

import Banks.Bank;

public class NodeTrie {
    double[] points;
    Bank bank;
    public char data;

    public Bank getBank() {
        return bank;
    }

    boolean isEnd;
    NodeTrie[] children;

    public boolean isEnd() {
        return isEnd;
    }

    public NodeTrie[] getChildren() {
        return children;
    }

    NodeTrie(){
        data = ' ';
        bank = null;
        isEnd = false;
        children = new NodeTrie[28];
        for (int i = 0; i < 28; i++) {
            children[i] = null;
        }
    }

    NodeTrie(Bank bank){
        this.bank = bank;
        isEnd = true;
        children = null;
    }

    NodeTrie (double[] points) {
        this.points = points;
        isEnd = true;
    }
}