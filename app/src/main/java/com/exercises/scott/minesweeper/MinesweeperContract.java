package com.exercises.scott.minesweeper;

import java.util.List;

/**
 * Created by scott on 9/6/16.
 */
public interface MinesweeperContract {
    interface View {
        void setPresenter(Presenter presenter);
        void updateBoard();
    }

    interface Presenter {
        void clickMine(int pos);
        List<Cell> getCells();
        void createNewGame(int width);
    }
}
