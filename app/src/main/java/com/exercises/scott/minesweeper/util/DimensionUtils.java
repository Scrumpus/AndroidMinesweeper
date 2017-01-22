package com.exercises.scott.minesweeper.util;

/**
 * Created by sschwalbe on 1/21/17.
 */

public class DimensionUtils {
    private static int DESIRED = 50;
    private static int OFFSET = 5;

    public static int getCellDimen(int screenWidth) {
        for (int i = DESIRED + OFFSET; i < DESIRED - OFFSET; --i) {
            if (screenWidth % i <= OFFSET) {
                return screenWidth / i;
            }
        }
        return screenWidth / DESIRED;
    }

    public static int getCellRows(int screenHeight) {
        return 0;
    }
}
