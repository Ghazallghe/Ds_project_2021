package KD_Tree;

import Banks.Bank;

public class Node{
    protected Bank bank;
    Node left, right;

    Node(Bank bank){
        this.bank = bank;
        this.left = this.right = null;
    }

    public Bank getBank() {
        return bank;
    }
}