import services.MinePlacer;

import java.util.Arrays;

/**
 * The Minesweeper class represents a game of Minesweeper with a square grid.<br><br>
 *
 * - The game board consists of hidden cells, some containing mines.<br>
 * - The player uncovers cells to reveal numbers indicating adjacent mines.<br>
 * - If a mine is uncovered, the game is lost.<br>
 * - The game is won when all non-mine cells are uncovered.<br><br>
 *
 * This class handles:<br>
 * - Initializing the game board and placing mines randomly.<br>
 * - Uncovering cells and revealing adjacent mine counts.<br>
 * - Checking game status (win/loss conditions).<br>
 * - Displaying the board state.<br>
 */
public class Minesweeper {
    private final int size; // Size of the grid
    private final char[][] board; // Stores the board state: '_' if not uncovered, or '<number>' indicating adjacent mines if uncovered.
    private final boolean[][] mines; // True if a cell contains a mine
    private final boolean[][] revealed; // True if a cell has been uncovered

    /**
     * Constructs a Minesweeper game with the given grid size and mine count.
     *
     * @param size The size of the grid (size x size).
     * @param mineCount The number of mines to be placed on the board.
     */
    public Minesweeper(MinePlacer minePlacer, int size, int mineCount) {
        this.size = size;
        this.board = new char[size][size];
        this.mines = minePlacer.placeMines(size, mineCount);
        this.revealed = new boolean[size][size];
        initializeBoard();
    }

    /**
     * Initializes the board with hidden cells.<br>
     * All cells are initially represented by '_'.
     */
    private void initializeBoard() {
        for (int i = 0; i < this.size; i++) {
            Arrays.fill(this.board[i], '_');
        }
    }

    /**
     * Uncovers a cell at the specified row and column.<br><br>
     *
     * - If the cell contains a mine, the game is lost, and the method returns false.<br>
     * - Otherwise, the cell is revealed, and the number of adjacent mines is displayed.<br>
     * - If the cell has no adjacent mines, its neighbors are uncovered recursively.<br>
     *
     * @param row The row index of the cell to uncover.
     * @param col The column index of the cell to uncover.
     * @return True if the cell is successfully uncovered, false if a mine is detonated.
     */
    public boolean uncoverCell(int row, int col) {
        if (this.revealed[row][col]) {
            System.out.println("This cell has been revealed. Please select another cell.");
            return true;
        }

        if (this.mines[row][col]) return false; // Game Over

        int adjacentMines = reveal(row, col, true);
        if (adjacentMines != -1) System.out.println("This square contains " + adjacentMines + " adjacent mines.");
        return true;
    }

    /**
     * Reveals the number of adjacent mines of the specified cell and that of its adjacent cells, if necessary.<br><br>
     *
     * If the specified cell does not have adjacent mines, it recursively uncovers all surrounding cells, up to the maximum of 8.<br>
     * The recursion continues for all adjacent cells that also have no adjacent mines, effectively revealing large safe areas.<br>
     *
     * @param row The row index of the cell to reveal.
     * @param col The column index of the cell to reveal.
     * @param isUserSelection A flag indicating whether the cell was selected by the user.<br>
     *                        - If true, the method returns the number of adjacent mines for the selected cell.<br>
     *                        - If false, the method is invoked recursively for neighboring cells and returns -1.<br>
     * @return The number of adjacent mines if `isUserSelection` is true, otherwise -1.
     */
    private int reveal(int row, int col, boolean isUserSelection) {
        if (row < 0 || row >= this.size || col < 0 || col >= this.size || this.revealed[row][col]) {
            return -1;
        }

        this.revealed[row][col] = true;
        int adjacentMines = countAdjacentMines(row, col);
        this.board[row][col] = (adjacentMines > 0) ? (char) ('0' + adjacentMines) : '0';


        if (adjacentMines == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        reveal(row + dr, col + dc, false);
                    }
                }
            }
        }

        return isUserSelection ? adjacentMines : -1;
    }

    /**
     * Counts the number of mines adjacent to a given cell.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The number of mines surrounding the given cell.
     */
    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < this.size && newCol >= 0 && newCol < this.size && this.mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Prints the current state of the game board to the console.<br><br>
     *
     * The board is displayed with each row labeled alphabetically (A, B, C, ...) and each column numbered (1, 2, 3, ...).<br>
     * - Unrevealed cells are represented by an underscore ('_').<br>
     * - Revealed cells display the number of adjacent mines as a character ('1' to '8'), or '0' if the cell has no adjacent mines.<br><br>
     */
    public void displayBoard() {
        System.out.print("  ");
        for (int i = 0; i < this.size; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        char rowLabel = 'A';
        for (int i = 0; i < this.size; i++) {
            System.out.print(rowLabel++ + " ");
            for (int j = 0; j < this.size; j++) {
                if (j >= 9 && j <= 98 ) {
                    System.out.print("__" + " ");
                } else {
                    System.out.print(this.board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks if the player has won the game.<br>
     * The game is won if all non-mine cells are uncovered.
     *
     * @return True if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (!this.mines[i][j] && !this.revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
