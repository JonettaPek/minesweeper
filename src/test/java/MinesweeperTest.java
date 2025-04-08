import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.MinePlacer;
import services.PredeterminedMinePlacer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {

    private Minesweeper game;

    @BeforeEach
    public void setUp() {
        // Arrange: Create a Minesweeper game with a predetermined mine placement
        MinePlacer minePlacer = new PredeterminedMinePlacer(); // Mine locations: A3, D2, E5
        game = new Minesweeper(minePlacer, 5, 3);
    }

    @Test
    public void testUncoverCell_whenSafeCellIsUncovered_shouldReturnTrue() { // test<MethodName>_when<Condition>_should<ExpectedOutcome>
        // Act: Uncover a cell that is not a mine
        boolean result = game.uncoverCell(0, 1); // A2

        assertTrue(result, "The cell should be uncovered successfully and adjacent mines should be counted and printed.");
    }

    @Test
    public void testUncoverCell_whenMineCellIsUncovered_shouldReturnFalse() {
        // Act: Uncover a cell that is a mine
        boolean result = game.uncoverCell(0, 2); // A3

        assertFalse(result, "The cell should not be uncovered and the game is lost when hitting a mine.");
    }

    @Test
    public void testUncoverCell_whenCellHasAlreadyBeenRevealed_shouldReturnTrue() {
        // Act: Uncover the same safe cell twice
        game.uncoverCell(0, 1); // A2
        boolean result = game.uncoverCell(0, 1);

        assertTrue(result, "The cell has already been uncovered and adjacent mines should not be counted and printed.");
    }

    @Test
    public void testIsGameWon_whenNotAllSafeCellsAreUncovered_shouldReturnFalse() {
        // Act: Uncover one cell only
        game.uncoverCell(0, 1); // A2

        assertFalse(game.isGameWon(), "The game should not be won yet.");
    }

    @Test
    public void testIsGameWon_whenAllSafeCellsAreUncovered_shouldReturnTrue() {
        // Act: Uncover all non-mine cells
        game.uncoverCell(0, 1); // A2
        game.uncoverCell(0, 4); // A5
        game.uncoverCell(1, 0); // B1
        game.uncoverCell(3, 0); // D1
        game.uncoverCell(3, 2); // D3
        game.uncoverCell(4, 0); // E1
        game.uncoverCell(4, 1); // E2
        game.uncoverCell(4, 2); // E3
        game.uncoverCell(4, 3); // E4

        assertTrue(game.isGameWon(), "The game should be won when all non-mine cells are uncovered.");
    }

    @Test
    public void testDisplayBoard_whenGameStarts_shouldShowInitialState() {
        try {
            // Act & Assert: Test that the board displays correctly and prints to the console without throwing an exception
            game.displayBoard();
        } catch (Exception e) {
            // This test will fail if an exception is thrown when calling displayBoard
            fail("Exception thrown while displaying the board: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayBoard_whenSafeCellsUncovered_shouldRevealNumbers() {
        // Redirect the output stream to an object instead of the console to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);  // Redirect System.out to the outputStream

        // Act: Uncover one cell
        game.uncoverCell(0, 1);  // A2 is a safe cell with one adjacent mine ('1')

        // Act: Call the displayBoard method to print the current board state
        game.displayBoard();

        // Assert: Verify that the board displays correctly
        String output = outputStream.toString();

        // \s is a whitespace
        assertTrue(
            output.contains(
                """
                  1 2 3 4 5\s
                A _ 1 _ _ _\s
                B _ _ _ _ _\s
                C _ _ _ _ _\s
                D _ _ _ _ _\s
                E _ _ _ _ _\s
                """
            ),
            "Cell (0, 1) should show '1' indicating 1 adjacent mine."
        );

        assertTrue(
            output.contains(
                """
                  1 2 3 4 5\s
                A _ 1 _ _ _\s
                B _ _ _ _ _\s
                C _ _ _ _ _\s
                D _ _ _ _ _\s
                E _ _ _ _ _\s
                """
            ),
            "Cell (0, 0) should remain hidden, which is represented by '_'."
        );

        // Reset the output stream to its original state
        System.setOut(System.out);
    }

}