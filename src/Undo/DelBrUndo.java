package Undo;

import Banks.Branches;

public class DelBrUndo implements Undo {
    private Branches branches;

    public DelBrUndo (Branches branches) {
        this.branches = branches;
    }

    @Override
    public void undo() {
        Branches.addBr(branches.getBankName(), branches.getMainBank(), branches.getPoints(), false);
    }
}
