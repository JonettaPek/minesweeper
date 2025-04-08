import services.RandomMinePlacer;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to Minesweeper!");

            int size = getGridSizeFromUser(scanner);

            int mines = getMineCountFromUser(scanner, size);

            Minesweeper game = new Minesweeper(new RandomMinePlacer(), size, mines);

            runGameLoop(game, scanner, size);

            System.out.println("Press any key to play again, or type 'exit' to quit.");
            scanner.nextLine();
            String playAgain = scanner.nextLine();
            if (playAgain.equalsIgnoreCase("exit")) {
                break;
            }
        }
        scanner.close();
    }

    /**
     * Prompts the user for the grid size and returns it as an integer.<br><br>
     * Only integer values are accepted. If the user enters a non-integer,
     * they will be prompted again until a valid input is provided.
     *
     * @param scanner The Scanner object for user input.
     * @return The size of the grid.
     */
    private static int getGridSizeFromUser(Scanner scanner) {
        int size;
        while (true) {
            System.out.println("\nEnter the size of the grid (e.g. 4 for a 4x4 grid):");
            String input = scanner.nextLine();

            try {
                size = Integer.parseInt(input);
                return size;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    /**
     * Prompts the user for the number of mines and ensures it does not exceed 35% of the grid.<br><br>
     * Only integer values are accepted. If the user enters a non-integer, they will be prompted
     * again until a valid input is provided.
     *
     * @param scanner The Scanner object for user input.
     * @param size The size of the grid.
     * @return The number of mines.
     */
    private static int getMineCountFromUser(Scanner scanner, int size) {
        int mines;
        while (true) {
            System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares):");
            String input = scanner.nextLine();

            try {
                mines = Integer.parseInt(input);
                int maxMines = (int) (size * size * 0.35);
                if (mines <= maxMines) {
                    return mines;
                } else {
                    System.out.println("Too many mines! Please enter an integer less than or equal to " + maxMines);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    /**
     * Runs the main game loop, allowing the player to select cells until they win or lose.<br><br>
     *
     * - The player selects a cell to reveal.<br>
     * - If the selected cell contains a mine, the game ends with a loss.<br>
     * - Otherwise, the cell is uncovered, and the number of adjacent mines is displayed.<br>
     * - After each move, the game checks if all non-mine cells have been uncovered.<br>
     * - If all safe cells are revealed, the player wins the game.<br>
     *
     * @param game The Minesweeper game instance.
     * @param scanner The Scanner object for user input.
     * @param size The size of the grid.
     */
    private static void runGameLoop(Minesweeper game, Scanner scanner, int size) {
        boolean isFirstRound = true;
        while (true) {
            System.out.println(isFirstRound ? "\nHere is your minefield:" : "\nHere is your updated minefield:");
            game.displayBoard();
            isFirstRound = false;

            String cell = getUserSelectedCell(scanner, size);

            int row = cell.charAt(0) - 'A';
            int col = cell.charAt(1) - '1';
            if (!game.uncoverCell(row, col)) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                break;
            }

            if (game.isGameWon()) {
                System.out.println("\nHere is your updated minefield:");
                game.displayBoard();
                System.out.println("\nCongratulations, you have won the game!");
                break;
            }
        }
    }

    /**
     * Prompts the user to select a cell and ensures the input is valid.
     *
     * @param scanner The Scanner object for user input.
     * @param size The size of the grid.
     * @return A valid cell selection in the format "A1".
     */
    private static String getUserSelectedCell(Scanner scanner, int size) {
        String cell;
        while (true) {
            System.out.print("\nSelect a square to reveal (e.g. A1): ");
            cell = scanner.next();
            if (!isValidCell(cell, size)) {
                System.out.println("Invalid square.");
            } else {
                return cell;
            }
        }
    }

    /**
     * Validates whether the user's cell selection is within the grid bounds.
     *
     * @param input The user's input string (e.g., "A1").
     * @param size The size of the grid.
     * @return True if the input is a valid cell, otherwise false.
     */
    private static boolean isValidCell(String input, int size) {
        if (input.length() != 2) {
            return false;
        }
        char rowChar = input.charAt(0);
        if (rowChar < 'A' || rowChar >= 'A' + size) {
            return false;
        }
        try {
            int col = Integer.parseInt(input.substring(1));
            return col >= 1 && col <= size;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
