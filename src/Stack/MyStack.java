package Stack;

import Undo.Undo;

public class MyStack {

    private Undo[] stack;
    private int size;
    private int index;

    public int getSize() {
        return index;
    }

    public MyStack() {
        this.size = 20;
        this.index = 0;
        stack = new Undo[size];
    }

    public void push(Undo undo) {
        if (index == size - 1)
            resize();
        stack[index++] = undo;


    }

    public Undo pop() {
        if (index != 0)
           return stack[--index];
        return null;
    }

    void resize() {
        size *= 2;
        Undo[] new_stack = new Undo[size];
        for (int i = 0; i < size / 2; i++)
            new_stack[i] = stack[i];
        stack = new_stack;
    }

}
