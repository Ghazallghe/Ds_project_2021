import Banks.*;
import Exceptions.*;
import KD_Tree.KD_Tree;
import Map.MyMap;
import Neighborhood.Neighborhood;
import Undo.UndoImp;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        KD_Tree.banks = new KD_Tree();
        MyMap.mainBanks = new MyMap();
        MyMap.allBranches = new MyMap();
        MyMap.neighborhood = new MyMap();
        System.out.println("Hello welcome to my bank manager!");
        outer:
        while (true) {
            menu();
            System.out.print("command: ");
            boolean flag = true;
            int input = 0;

            do {
                try {
                    input = sc.nextInt();
                    flag = false;
                }catch (InputMismatchException e){
                    System.out.println("You should enter a number");
                    menu();
                    System.out.print("command: ");
                    sc.next();
                }
            }while (flag);

            switch (input){
                case 0: break outer;
                case 1: addN();
                break;
                case 2: addB();
                break;
                case 3: addBr();
                break;
                case 4: delBr();
                break;
                case 5: listB();
                break;
                case 6: listBrs();
                break;
                case 7: nearB();
                break;
                case 8: nearBr();
                break;
                case 9: availB();
                break;
                case 10: MainBanks.mostBr();
                break;
                case 11: undo();
                break;
                default: menu();
            }
        }


    }

    static void menu () {
        System.out.println();
        System.out.println("Select a command!");
        System.out.println("0) exit");
        System.out.println("1) addN (for adding a neighborhood)");
        System.out.println("2) addB (for adding main banks)");
        System.out.println("3) addBr (for adding branches to a main bank)");
        System.out.println("4) delBr (for removing a branch)");
        System.out.println("5) listB (for having all existing banks in a neighborhood");
        System.out.println("6) listBrs (for having all branches of a main bank");
        System.out.println("7) nearB (for finding nearest bank from your selected point");
        System.out.println("8) nearBr (for finding nearest branch of a main bank from your selected point");
        System.out.println("9) availB (for finding banks with r distance from your selected point");
        System.out.println("10) mostBrs (for finding the bank with most branches)");
        System.out.println("11) undo (for undoing your actions)");
    }

    static String nameGetter(String message) throws IllegalNameException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Names should only consist of lowercase alphabets with no spaces");
        System.out.print(message);
        String name = sc.next();
        for (int i = 0; i < name.length(); i++)
            if (name.charAt(i) < 'a' || name.charAt(i) > 'z')
                throw new IllegalNameException();

        return name;
    }

    static double[] coordinateGetter () throws InputMismatchException {
        Scanner sc = new Scanner(System.in);
        double[] points = new double[2];
        System.out.println("Coordination should be floating point numbers");
        System.out.print("Enter x: ");
        points[0] = sc.nextDouble();
        System.out.print("Enter y: ");
        points[1] = sc.nextDouble();
        return points;
    }

    static void addN () {
        System.out.println("For adding a neighborhood you should enter a name and 2 coordinates top left & bottom right");
        String name;
        double[] points = new double[4], p1, p2;
        try {
            name = nameGetter("Neighborhood's name: ");
            System.out.println("Enter top right");
            p1 = coordinateGetter();
            System.out.println("Enter bottom left");
            p2 = coordinateGetter();
            points[0] = p1[0]; points[1] = p1[1]; points[2] = p2[0]; points[3] = p2[1];
            Neighborhood n = new Neighborhood();
            n.addN(name, points);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void addB () {
        System.out.println("For adding a main bank you should enter a name and a coordinate");
        String name;
        double[] points;
        try {
            name = nameGetter("Main Bank's name: ");
            points = coordinateGetter();
            MainBanks.addB(name, points);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void addBr () {
        System.out.println("For adding branches to a main bank you should enter you main bank's name you branch's name and your branch's coordinate");
        String name;
        String branchName;
        double[] points;
        try {
            branchName = nameGetter("Branch's name: ");
            name = nameGetter("Main Bank's name: ");
            points = coordinateGetter();
            Branches.addBr(branchName, name, points, true);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void delBr () {
        double[] points;
        System.out.println("For deleting a branch insert its coordinates");
        try {
            points = coordinateGetter();
            Branches branches = new Branches();
            branches.delBr(points, true);
        }catch (InputMismatchException e){
            System.out.println("Your entered name is not accepted please try again!");
        }
    }

    static void listB () {
        System.out.println("For having all banks in a neighborhood you should insert the neighborhood's name");
        String name;
        try {
            name = nameGetter("Neighborhood's name: ");
            Neighborhood n = new Neighborhood();
            n.listB(name);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }
    }

    static void listBrs () {
        System.out.println("For having all branches of a main bank you should insert the main bank's name");
        String name;
        try {
            name = nameGetter("Main bank's name: ");
            MainBanks mainBanks = new MainBanks();
            mainBanks.listBr(name);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }
    }

    static void nearB () {
        System.out.println("For having nearest bank to your selected coordination you should enter your selected coordination");
        double[] points;
        try {
            points = coordinateGetter();
            Bank bank = new Bank();
            bank.nearB(points);
        } catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void nearBr () {
        System.out.println("For having nearest branch of your selected bank from your selected location you should enter you main bank's name and you selected coordination");
        String name;
        double[] points;
        try {
            name = nameGetter("Main Bank's name: ");
            System.out.println("Enter your selected coordination");
            points = coordinateGetter();
            MainBanks mainBanks = new MainBanks();
            mainBanks.nearBr(name, points);
        } catch (IllegalNameException e) {
            System.out.println("Your entered name is not accepted please try again!");
        }catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void availB () {
        System.out.println("For having all banks in r distance of you selected location you should enter a coordination and a r distance");
        Scanner sc = new Scanner(System.in);
        double[] points;
        double r;
        try {
            System.out.println("Enter your coordination and your expected distance");
            points = coordinateGetter();
            System.out.print("Enter r: ");
            r = sc.nextDouble();
            Bank bank = new Bank();
            bank.availB(points, r);
        }catch (InputMismatchException e){
            System.out.println("You should enter numbers for coordination");
        }
    }

    static void undo () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter which command you want to go back to: ");
        UndoImp undoImp = new UndoImp();
        undoImp.undo(sc.nextInt());
    }
}
