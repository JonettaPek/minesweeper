package services;

import java.util.Random;

public class RandomMinePlacer implements MinePlacer {
    /**
     * Randomly places a specified number of mines on the board.<br><br>
     *
     * The method ensures that each mine is placed in a unique location, avoiding any duplicates.<br>
     * It uses a random number generator to determine the row and column for each mine placement.<br>
     * The process continues until the specified number of mines have been placed on the grid.<br><br>
     *
     * @param size The size of the board (size x size).
     * @param mineCount The number of mines to place on the board.
     * @return A 2D boolean array representing the board, where `true` indicates a mine and `false` indicates a safe cell.
     */
    @Override
    public boolean[][] placeMines(int size, int mineCount) {
        boolean[][] mines = new boolean[size][size];
        Random random = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (!mines[row][col]) {
                mines[row][col] = true;
                placed++;
            }
        }
        return mines;
    }
}
