package Undo;

import Stack.MyStack;

public class UndoImp {
    public static MyStack stack = new MyStack();

    public void undo (int number) {
        stack.push(null);
        int numberOfPop = stack.getSize() - number;
        for (int i = 0; i < numberOfPop; i++) {
            Undo u = stack.pop();
            if (u != null)
                u.undo();
        }
    }
}
