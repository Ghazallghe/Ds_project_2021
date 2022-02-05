package Map;

import Banks.Bank;
import Exceptions.*;

public class  MyMap {

    public static MyMap mainBanks;
    public static MyMap allBranches;
    public static MyMap neighborhood;

    NodeTrie root;

    public NodeTrie getRoot() {
        return root;
    }

    public MyMap(){
        this.root = new NodeTrie();
    }

    public void put(String key, Bank bank) throws BankExistenceException {

        NodeTrie tmp = root;

        int length = key.length();

        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'a';
            if (tmp.children[index] == null)
                tmp.children[index] = new NodeTrie();

            tmp = tmp.children[index];
        }

        if (tmp.children[27] != null && tmp.children[27].isEnd)
            throw new BankExistenceException();

        tmp.isEnd = true;
        tmp.children[27] = new NodeTrie(bank);
    }

    public void put (String key, double[] points) throws NeighborhoodAlreadyExistException {

        NodeTrie tmp = root;

        int length = key.length();

        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'a';
            if (tmp.children[index] == null)
                tmp.children[index] = new NodeTrie();

            tmp = tmp.children[index];
        }

        if (tmp.children[27] != null && tmp.children[27].isEnd)
            throw new NeighborhoodAlreadyExistException();

        tmp.isEnd = true;
        tmp.children[27] = new NodeTrie(points);
    }

    public Bank get(String key) {
        NodeTrie tmp = root;

        int length = key.length();

        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'a';

            if (tmp.children[index] == null)
                return null;

            tmp = tmp.children[index];
        }

        if (tmp.isEnd){
            tmp = tmp.children[27];
            return tmp.bank;
        }

        return null;
    }

    public double[] getN (String key) {
        NodeTrie tmp = root;

        int length = key.length();

        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'a';

            if (tmp.children[index] == null)
                return null;

            tmp = tmp.children[index];
        }
        if (tmp.isEnd){
            tmp = tmp.children[27];
            return tmp.points;
        }
        return null;
    }

    boolean isEmpty(NodeTrie root) {
        for (int i = 0; i < 27; i++)
            if (root.children[i] != null)
                return false;
        return true;
    }

    NodeTrie removeR(NodeTrie root, String key, int depth) {
        if (root == null)
            return null;

        if (depth == key.length()) {

            if (root.isEnd){
                root.isEnd = false;
                root.children[27] = null;
            }

            if (isEmpty(root)) {
                root.children[27] = null;
                root = null;
            }

            return root;
        }

        int index = key.charAt(depth) - 'a';
        root.children[index] = removeR(root.children[index], key, depth + 1);

        if (isEmpty(root) && !root.isEnd){
            root = null;
        }

        return root;
    }

    public void remove (NodeTrie root, String name) {
        root = removeR (root, name, 0);
    }

}
