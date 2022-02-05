package Undo;

import Neighborhood.Neighborhood;

public class AddNUndo implements Undo {
    private Neighborhood neighborhood;

    public AddNUndo (Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public void undo() {
        neighborhood.delN(neighborhood.getName());
    }
}
