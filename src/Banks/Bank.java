package Banks;

import KD_Tree.*;
import Undo.UndoImp;


public class Bank {
    protected String name;
    protected double[] points;
    public static boolean noBank;

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }

    public double[] getPoints() {
        return points;
    }

    public String getBankName() {
        return name;
    }

    public void nearB (double[] target) {
        KD_Tree near = new KD_Tree();
        Node nearest = near.nearestNeighbor(KD_Tree.banks.getRoot(), target, 0);

        if (nearest == null)
            System.out.println("There aren't any banks in the region.");
        else{
            System.out.println("Nearest bank's name is: " + nearest.getBank().getBankName());
            System.out.println("And it's located at: (" + nearest.getBank().getPoints()[0] + ", " + nearest.getBank().getPoints()[1] + ")");
        }
        UndoImp.stack.push(null);
    }

    public void availB (double[] points, double r) {
        noBank = true;
        KD_Tree rDist = new KD_Tree();
        rDist.rDistance(KD_Tree.banks.getRoot(), points, r, 0);
        if (noBank)
            System.out.println("No bank exist in " + r + " distance from your selected point");

        UndoImp.stack.push(null);
    }
}
