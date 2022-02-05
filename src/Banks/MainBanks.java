package Banks;

import Exceptions.*;
import KD_Tree.*;
import Map.MyMap;
import Map.NodeTrie;
import Undo.*;

public class MainBanks extends Bank {
    protected KD_Tree branches;
    protected int numberOfBranches;
    static String mostBrName;
    static int maxBranchNumber = 0;

    public MainBanks () {}

    public MainBanks(String name, double[] points) {
        this.name = name;
        this.points = points;
        this.branches = new KD_Tree();
        this.numberOfBranches = 0;
    }

    public static void addB(String name, double[] points) {
        MainBanks b = new MainBanks(name, points);
        boolean addToMap = true;

        if (MyMap.mainBanks.get(name) == null) {
            try {
                KD_Tree.banks.insert(b);
            } catch (CoordinateIsOccupiedException e) {
                System.out.println("This coordinate is already occupied with other bank please try other commands!!!");
                addToMap = false;
            }
        }

        if (addToMap) {
            try {
                MyMap.mainBanks.put(name, b);
                System.out.println("Your bank is added successfully!");
                UndoImp.stack.push(new AddBUndo(b));
            } catch (BankExistenceException e) {
                System.out.println("This bank already exist please try other commands!!!");
            }
        }
    }

    public void listBr (String name) {
        MainBanks b = (MainBanks) MyMap.mainBanks.get(name);
        KD_Tree t = new KD_Tree();
        System.out.println("All branches in your selected main bank: ");
        t.preorder(b.branches.getRoot(), 0);
        System.out.println();
        UndoImp.stack.push(null);
    }

    public void nearBr (String name, double[] points) {
        MainBanks mb = (MainBanks) MyMap.mainBanks.get(name);
        KD_Tree kd_tree = new KD_Tree();
        Node nearest = kd_tree.nearestNeighbor(mb.branches.getRoot(), points, 0);

        if (nearest == null)
            System.out.println("There isn't any banks in the region.");
        else{
            System.out.println("Nearest bank's name is: " + nearest.getBank().getBankName());
            System.out.println("And it's located at: (" + nearest.getBank().getPoints()[0] + ", " + nearest.getBank().getPoints()[1] + ")");
        }
        UndoImp.stack.push(null);
    }

    void calculateMostNumberOfBranches (String name, int number) {
        if (number > maxBranchNumber){
            mostBrName = name;
            maxBranchNumber = number;
        }
    }

    public static void mostBr (){
        System.out.println(mostBrName + " has the most number of branches among all banks with " + maxBranchNumber + " branches");
        UndoImp.stack.push(null);
    }

    void checkAfterBranchDeletion (NodeTrie root) {
        if (root == null)
            return;
        if (root.isEnd()){
            MainBanks mainBanks = (MainBanks) root.getChildren()[27].getBank();
            calculateMostNumberOfBranches(mainBanks.name, mainBanks.numberOfBranches);
        }
        for (int i = 0; i < 26; i++)
            checkAfterBranchDeletion(root.getChildren()[i]);
    }

    public void delB (double[] points) {
        KD_Tree kd_tree = new KD_Tree();
        Node check = kd_tree.search(KD_Tree.banks.getRoot(), points);
        Node del = kd_tree.delete(KD_Tree.banks.getRoot(), points);
        if (del == null)
            KD_Tree.banks = new KD_Tree();
        MyMap myMap = new MyMap();
        myMap.remove(MyMap.mainBanks.getRoot(), check.getBank().getBankName());

    }
}