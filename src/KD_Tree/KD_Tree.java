package KD_Tree;

import Banks.Bank;
import Exceptions.*;
import Neighborhood.Neighborhood;

public class KD_Tree {

    public static KD_Tree banks;
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void insert(Bank bank) throws CoordinateIsOccupiedException {
        root = insertR(root, bank, 0);
    }

    Node insertR(Node root, Bank bank, int depth) throws CoordinateIsOccupiedException {
        if (root == null)
            return new Node(bank);

        if (bank.getPoints()[0] == root.bank.getPoints()[0] && bank.getPoints()[1] == root.bank.getPoints()[1])
            throw new CoordinateIsOccupiedException();

        int index = depth % 2;

        if (bank.getPoints()[index] > root.bank.getPoints()[index])
            root.right = insertR(root.right, bank, ++depth);
        else
            root.left = insertR(root.left, bank, ++depth);

        return root;
    }

    public void preorder (Node root, int print) {
        if (root == null)
            return;
        if (print == 4){
            System.out.println();
            print = 0;
        }
        System.out.print("(" + root.bank.getPoints()[0] + ", " + root.bank.getPoints()[1] + ")" + " ");
        preorder(root.left, print + 1);
        preorder(root.right, print + 1);
    }

    public Node nearestNeighbor (Node root, double[] points, int depth) {
        Node nextBranch;
        Node otherBranch;

        if (root == null)
            return null;
        if (points[depth % 2] < root.bank.getPoints()[depth % 2]){
            nextBranch = root.left;
            otherBranch = root.right;
        }else {
            nextBranch = root.right;
            otherBranch = root.left;
        }

        Node tmp = nearestNeighbor(nextBranch, points, depth + 1);
        Node best = closest(points, tmp, root);

        double r = calculateDistance(points, best);
        double r2 = points[depth % 2] - root.bank.getPoints()[depth % 2];

        if (r > r2 * r2) {
            tmp = nearestNeighbor(otherBranch, points, depth + 1);
            best = closest(points, tmp, best);
        }

        return best;
    }

    Node closest (double[] points, Node first, Node second) {
        if (first == null)
            return second;
        else if (second == null)
            return first;

        double f = calculateDistance(points, first);
        double s = calculateDistance(points, second);

        return f > s ? second : first;
    }

    public void inRectangle (Node root, double[] pointsRightUp, double[] pointsLeftDown, String name, int depth) {
        if (root == null)
            return;

        if (root.bank.getPoints()[depth % 2] > pointsRightUp[depth % 2])
            inRectangle(root.left, pointsRightUp, pointsLeftDown, name, depth + 1);

        else if (root.bank.getPoints()[depth % 2] < pointsLeftDown[depth % 2])
            inRectangle(root.right, pointsRightUp, pointsLeftDown, name, depth + 1);

        else {

            if (root.bank.getPoints()[(depth + 1) % 2] <= pointsRightUp[(depth + 1) % 2] && root.bank.getPoints()[(depth + 1) % 2] >= pointsLeftDown[(depth + 1) % 2]) {
                System.out.println("Banks name: " + root.bank.getBankName());
                System.out.println(root.bank.getBankName() + " is located at: (" + root.bank.getPoints()[0] + ", " + root.getBank().getPoints()[1] + ") in " + name + " neighborhood");
                Neighborhood.noBank = false;
            }
            inRectangle(root.left, pointsRightUp, pointsLeftDown, name, depth + 1);
            inRectangle(root.right, pointsRightUp, pointsLeftDown, name, depth + 1);
        }
    }

    public void rDistance (Node root, double[] points, double r, int depth) {
        if (root == null)
            return;

        if (root.bank.getPoints()[depth % 2] <= (points[depth % 2] + r) && root.bank.getPoints()[depth % 2] >= (points[depth % 2] - r)) {
            if (root.bank.getPoints()[(depth + 1 ) % 2] <= (points[(depth + 1 ) % 2] + r) && root.bank.getPoints()[(depth + 1 ) % 2] >= (points[(depth + 1 ) % 2] - r)) {
                double distance = calculateDistance(points, root);
                if (distance == r * r) {
                    System.out.println("Banks name: " + root.bank.getBankName());
                    System.out.println(root.bank.getBankName() + " is located at: (" + root.bank.getPoints()[0] + ", " + root.getBank().getPoints()[1] + ") and has a distance of " + r + " from you selected point");
                    Bank.noBank = false;
                }
            }
            rDistance(root.left, points, r, depth + 1);
            rDistance(root.right, points, r, depth + 1);

        }
        else if (root.bank.getPoints()[depth % 2] < points[depth % 2])
            rDistance(root.right, points, r, depth + 1);
        else if (root.bank.getPoints()[depth % 2] > points[depth % 2])
            rDistance(root.left, points, r, depth + 1);
    }

    double calculateDistance (double[] points, Node root) {
        double x = (points[0] - root.bank.getPoints()[0]);
        double y = (points[1] - root.bank.getPoints()[1]);

        return x * x + y * y;
    }


    Node min3Nodes (Node x, Node y, Node z, int d) {
        Node min = x;
        if (y != null && y.bank.getPoints()[d] < min.bank.getPoints()[d])
            min = y;
        if (z != null && z.bank.getPoints()[d] < min.bank.getPoints()[d])
            min = z;
        return min;
    }

    Node minR (Node root, int d, int depth) {
        if (root == null)
            return null;

        if (depth % 2 == d)
        {
            if (root.left == null)
                return root;
            return minR(root.left, d, depth + 1);
        }

        return min3Nodes(root, minR(root.left, d, depth + 1), minR(root.right, d, depth + 1), d);
    }

    Node min (Node root, int d) {
        return minR(root, d, 0);
    }


    boolean samePoints (double[] point1, double[] point2) {
        for (int i = 0; i < 2; i++)
            if (point1[i] != point2[i])
                return false;
        return true;
    }

    Node deleteR(Node root, double[] point, int depth) {
        if (root == null)
            return null;

        if (samePoints(root.bank.getPoints(), point)) {
            if (root.right != null) {
                Node min = min(root.right, depth % 2);

                Node tmp = root;
                root = min;
                min = tmp;

                root.right = deleteR(root.right, min.bank.getPoints(), depth + 1);
            }
            else if (root.left != null) {
                Node min = min(root.left, depth % 2);

                Node tmp = root;
                root = min;
                min = tmp;

                root.right = deleteR(root.left, min.bank.getPoints(), depth + 1);
            }
            else {
                return null;
            }
            return root;
        }

        if (point[depth % 2] < root.bank.getPoints()[depth % 2])
            root.left = deleteR (root.left, point, depth + 1);
        else
            root.right = deleteR (root.right, point, depth + 1);

        return root;
    }

    public Node delete(Node root, double[] point) {
        return deleteR (root, point, 0);
    }

    Node searchR(Node root, double[] point, int depth) {
        if (root == null)
            return null;
        if (samePoints(root.bank.getPoints(), point))
            return root;

        if (point[depth % 2] < root.bank.getPoints()[depth % 2])
            return searchR(root.left, point, depth + 1);

        return searchR(root.right, point, depth + 1);
    }

    public Node search(Node root, double[] point) {
        return searchR(root, point, 0);
    }

}