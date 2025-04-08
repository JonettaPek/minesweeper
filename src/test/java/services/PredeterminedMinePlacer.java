package services;

public class PredeterminedMinePlacer implements MinePlacer {
    /**
     * Places mines on the board in a predetermined pattern for unit testing purposes.
     * <p>
     * This method provides a fixed mine layout on the grid, which allows for controlled testing
     * of game logic and ensures consistent results for unit tests. The mine locations are set as:
     * <pre>
     * [false, false, true, false, false]
     * [false, false, false, false, false]
     * [false, false, false, false, false]
     * [false, true, false, false, false]
     * [false, false, false, false, true]
     * </pre>
     * The mine locations are represented by 'true' and 'false' values in a 2D boolean array,
     * where 'true' represents a mine and 'false' represents a safe cell.
     *
     * @param size The size of the board (size x size).
     * @param mineCount The number of mines to be placed on the board. This parameter is ignored
     *                  since the mine positions are predetermined.
     * @return A 2D boolean array representing the board, where 'true' indicates a mine and
     *         'false' indicates an empty cell.
     */
    @Override
    public boolean[][] placeMines(int size, int mineCount) {
        return new boolean[][] {
            {false, false, true, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, true, false, false, false},
            {false, false, false, false, true}
        };
    }
}
