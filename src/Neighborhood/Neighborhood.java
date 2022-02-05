package Neighborhood;

import Exceptions.NeighborhoodAlreadyExistException;
import Exceptions.WrongRectangleException;
import KD_Tree.KD_Tree;
import Map.MyMap;
import Undo.AddNUndo;
import Undo.UndoImp;

public class Neighborhood {
    private String name;
    private double[] points;
    public static boolean noBank;

    public String getName() {
        return name;
    }

    public Neighborhood () {}

    Neighborhood (String name, double[] points) {
        this.name = name;
        this.points = points;
    }

    public void addN (String name, double[] points) {
        Neighborhood n = new Neighborhood(name, points);
        try {
            rectangleValidation(points);
            MyMap.neighborhood.put(name, points);
            System.out.println("Your neighborhood is added successfully");
            UndoImp.stack.push(new AddNUndo(n));
        }catch (WrongRectangleException e){
            System.out.println("Your points aren't valid for our required rectangle!");
        } catch (NeighborhoodAlreadyExistException e){
            System.out.println("This neighborhood already exists please try other commands!!!");
        }
    }

    public void listB (String name) {
        double[] points = MyMap.neighborhood.getN(name);

        if (points == null)
            System.out.println("This neighborhood doesn't exist please try other commands");

        else {
            noBank = true;

            double[] rightUp = new double[2];
            double[] leftDown = new double[2];
            rightUp[0] = points[0]; rightUp[1] = points[1]; leftDown[0] = points[2]; leftDown[1] = points[3];
            KD_Tree rectangle = new KD_Tree();
            System.out.println();
            rectangle.inRectangle(KD_Tree.banks.getRoot(), rightUp, leftDown, name, 0);

            if (noBank)
                System.out.println("There is no bank in " + name + " neighborhood!");
        }
    }

    void rectangleValidation (double[] points) throws WrongRectangleException {
        double[] rightUp = new double[2];
        double[] leftDown = new double[2];
        rightUp[0] = points[0]; rightUp[1] = points[1]; leftDown[0] = points[2]; leftDown[1] = points[3];
         if (rightUp[0] <= leftDown[0] || rightUp[1] <= leftDown[1])
             throw new WrongRectangleException();
    }


    public void delN (String name) {
        MyMap myMap = new MyMap();
        myMap.remove(MyMap.neighborhood.getRoot(), name);
    }
}


