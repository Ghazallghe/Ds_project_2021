package Undo;

import Banks.Branches;

public class AddBrUndo implements Undo {
    private final Branches branches;

    public AddBrUndo (Branches branches) {
        this.branches = branches;
    }

    @Override
    public void undo() {
        branches.delBr(branches.getPoints(), false);
    }
}
