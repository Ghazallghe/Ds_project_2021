package Banks;

import Exceptions.*;
import KD_Tree.*;
import Map.MyMap;
import Undo.*;

public class Branches extends Bank {
    private String mainBank;

    public String getMainBank() {
        return mainBank;
    }

    public Branches () {}

    public Branches(String name, String mainBank,double[] points) {
        this.name = name;
        this.points = points;
        this.mainBank = mainBank;
    }

    public static void addBr (String name, String mainBank, double[] points, boolean print) {
        Branches b = new Branches(name, mainBank, points);
        MainBanks mb = (MainBanks) MyMap.mainBanks.get(mainBank);

        boolean addToMap = true;

        if (MyMap.allBranches.get(name) == null) {
            try {
                KD_Tree.banks.insert(b);
                mb.branches.insert(b);
                mb.numberOfBranches++;
                mb.calculateMostNumberOfBranches(mainBank, mb.numberOfBranches);
            } catch (CoordinateIsOccupiedException e) {
                System.out.println("This coordinate is already occupied with other bank please try other commands!!!");
                addToMap = false;
            }catch (NullPointerException e){
                System.out.println("Main bank doesn't exist!");
                addToMap = false;
            }
        }

        if (addToMap) {
            try {
                MyMap.allBranches.put(name, b);
                UndoImp.stack.push(new AddBrUndo(b));
                if (print)
                    System.out.println("Your branch is added successfully!");
            } catch (BankExistenceException e) {
                System.out.println("This bank already exist please try other commands!!!");
            }
        }
    }

    public void delBr (double[] points, boolean print) {
        KD_Tree kd_tree = new KD_Tree();
        Node check = kd_tree.search(KD_Tree.banks.getRoot(), points);

        try {
            deletingExceptions(check);
            kd_tree.delete(KD_Tree.banks.getRoot(), points);
            MainBanks mb = (MainBanks) MyMap.mainBanks.get(findMainBank(check));
            mb.numberOfBranches--;
            MainBanks.maxBranchNumber = 0;
            if (mb.name.equals(MainBanks.mostBrName))
                mb.checkAfterBranchDeletion(MyMap.mainBanks.getRoot());
            Node del = kd_tree.delete(mb.branches.getRoot(), points);
            if (del == null)
                mb.branches = new KD_Tree();
            if (print) {
                System.out.println("Your chosen branch is deleted successfully!");
                UndoImp.stack.push(new DelBrUndo((Branches) check.getBank()));
            }
        }catch (NoBankException e){
            System.out.println("There is no bank located at this coordination");
        }catch (MainBankException e){
            System.out.println("You can't delete main banks");
        }
    }


    void deletingExceptions (Node check) throws NoBankException, MainBankException {
        if (check == null)
            throw new NoBankException();
        else if (MyMap.mainBanks.get(check.getBank().name) != null)
            throw new MainBankException();
        else {
            MyMap m = new MyMap();
            m.remove(MyMap.allBranches.getRoot(), check.getBank().getBankName());
        }
    }

    String findMainBank (Node check) {
        Branches b = (Branches) check.getBank();
        return b.mainBank;
    }
}
