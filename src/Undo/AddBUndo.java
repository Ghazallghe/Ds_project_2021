package Undo;

import Banks.MainBanks;

public class AddBUndo implements Undo{
    private MainBanks mainBanks;

    public AddBUndo (MainBanks mainBanks) {
        this.mainBanks = mainBanks;
    }

    @Override
    public void undo() {
        mainBanks.delB(mainBanks.getPoints());
    }
}
