package com.exercises.scott.minesweeper;

import android.content.res.Configuration;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by scott on 9/6/16.
 */
public class GameState {

    public static int MINE_CLEAR = 0;
    public static int MINE_BOMB = 1;
    public static int MINE_HIDDEN = 2;
    public static int MINE_REVEALED = 3;

    private int numMines;
    private int width;
    private int height;
    private int numRevealed;
    private int[][] mines;
    private Cell[][] cells;

    private static GameState INSTANCE = null;

    // create a new game
    private GameState(int numMines, int height, int width) {
        this.numMines = numMines;
        this.width = width;
        this.height = height;
        this.numRevealed = 0;
        this.mines = setMines(this.numMines, this.height, this.width);
        this.cells = newBoard(mines, height, width);
    }

    public static void newGame(int numMines, int height, int width) {
        INSTANCE = new GameState(numMines, height, width);
    }

    public static GameState getInstance() {
        return INSTANCE;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void revealMine(int y, int x, ClickMineCallback callback, boolean wasClicked) {
        if (y < 0 || x < 0 || y >= this.height || x >= this.width) return;

        Cell cell = cells[y][x];
        cell.setRevealed(true);
        --numRevealed;

        if (cell.isMine()) {
            if (wasClicked) {
                callback.onBoardUpdated();
            } else {
                return;
            }
        } else {
            if (cell.getNumAdjacent() == 0) {
                revealIfHidden(y - 1, x, callback, false);
                revealIfHidden(y - 1, x - 1, callback, false);
                revealIfHidden(y - 1, x + 1, callback, false);
                revealIfHidden(y, x + 1, callback, false);
                revealIfHidden(y, x - 1, callback, false);
                revealIfHidden(y + 1, x - 1, callback, false);
                revealIfHidden(y + 1, x, callback, false);
                revealIfHidden(y + 1, x + 1, callback, false);
            } else {
                callback.onBoardUpdated();
            }
        }
    }

    private void revealIfHidden(int y, int x, ClickMineCallback callback, boolean wasClicked) {
        if (y < 0 || x < 0 || y >= this.height || x >= this.width) return;
        Cell cell = cells[y][x];
        if (!cell.isRevealed()) {
            revealMine(y, x, callback, wasClicked);
        }
    }

    public static int countAdjacent(int[][] mines, int height, int width, int y, int x) {
        int adjacent = 0;

        int xLeft = x - 1;
        int xRight = x + 1;
        int yUp = y - 1;
        int yDown = y + 1;

        boolean xLeftInBounds = xLeft >= 0;
        boolean xRightInBounds = xRight < width;
        boolean yUpInBounds = yUp >= 0;
        boolean yDownInBounds = yDown < height;

        if (xLeftInBounds) {
            if (mines[y][xLeft] == GameState.MINE_BOMB) {
                ++adjacent;
            }

            if (yUpInBounds) {
                if (mines[yUp][xLeft] == GameState.MINE_BOMB) {
                    ++adjacent;
                }
            }

            if (yDownInBounds) {
                if (mines[yDown][xLeft] == GameState.MINE_BOMB) {
                    ++adjacent;
                }
            }
        }

        if (yUpInBounds) {
            if (mines[yUp][x] == GameState.MINE_BOMB) {
                ++adjacent;
            }

            if (xRightInBounds) {
                if (mines[yUp][xRight] == GameState.MINE_BOMB) {
                    ++adjacent;
                }
            }
        }

        if (yDownInBounds) {
            if (mines[yDown][x] == GameState.MINE_BOMB) {
                ++adjacent;
            }

            if (xRightInBounds) {
                if (mines[yDown][xRight] == GameState.MINE_BOMB) {
                    ++adjacent;
                }
            }
        }

        if (xRightInBounds) {
            if (mines[y][xRight] == GameState.MINE_BOMB) {
                ++adjacent;
            }
        }

        return adjacent;
    }

    public static int[][] setMines(int numMines, int height, int width) {
        int minesLeftToPlace = numMines;

        int[][] mines = new int[height][width];
        for (int[] row : mines) {
            Arrays.fill(row, GameState.MINE_CLEAR);
        }

        Random random = new Random();
        while (minesLeftToPlace > 0) {
            int randWidth = random.nextInt(width);
            int randHeight = random.nextInt(height);

            if (mines[randHeight][randWidth] == GameState.MINE_CLEAR) {
                mines[randHeight][randWidth] = GameState.MINE_BOMB;
                --minesLeftToPlace;
            }
        }

        return mines;
    }

    public static int[][] getAdjacent(int[][] mines, int height, int width) {
        int[][] adjacent = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                adjacent[i][j] = GameState.countAdjacent(mines, height, width, i, j);
            }
        }

        return adjacent;
    }

    public static Cell[][] newBoard (int[][] mines, int height, int width) {
        Cell[][] board = new Cell[height][width];
        int[][] adjacent = getAdjacent(mines, height, width);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                board[i][j] = new Cell(false, mines[i][j] == GameState.MINE_BOMB, adjacent[i][j]);
            }
        }
        return board;
    }

    public interface ClickMineCallback {
        void onBoardUpdated();
        void onWin();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
