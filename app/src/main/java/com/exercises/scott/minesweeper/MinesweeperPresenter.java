package com.exercises.scott.minesweeper;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scott on 9/6/16.
 */
public class MinesweeperPresenter implements MinesweeperContract.Presenter {
    private GameState mGameState;
    private MinesweeperContract.View mView;

    public MinesweeperPresenter(MinesweeperContract.View view) {
        mGameState = GameState.getInstance();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void clickMine(int pos) {
        int y = pos / mGameState.getWidth();
        int x = pos % mGameState.getWidth();

        mGameState.revealMine(y, x, new GameState.ClickMineCallback() {
            @Override
            public void onBoardUpdated() {
                mView.updateBoard();

            }

            @Override
            public void onWin() {

            }
        }, true);
    }

    public List<Cell> getCells() {
        mGameState = GameState.getInstance();
        Cell[][] cells = mGameState.getCells();
        List<Cell> cellList = new ArrayList<>();

        for (int i = 0; i < mGameState.getHeight(); ++i) {
            for (int j = 0; j < mGameState.getWidth(); ++j) {
                cellList.add(cells[i][j]);
            }
        }

        return cellList;
    }

    public void createNewGame(int width) {
       if (GameState.getInstance() == null) {
           GameState.newGame(10, width, width);
       }
    }
}
