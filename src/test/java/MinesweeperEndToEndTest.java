import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.MinePlacer;
import services.PredeterminedMinePlacer;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperEndToEndTest {
    private Minesweeper game;

    @BeforeEach
    public void setUp() {
        // Arrange: Create a Minesweeper game with a predetermined mine placement
        MinePlacer minePlacer = new PredeterminedMinePlacer(); // Mine locations: A3, D2, E5
        game = new Minesweeper(minePlacer, 5, 3);
    }

    @Test
    public void testGameLoss_whenMineIsUncovered_shouldEndGame() {
        // Act: Uncover some safe cells first
        assertTrue(game.uncoverCell(0, 1));  // A2
        assertTrue(game.uncoverCell(1, 0));  // B1
        assertTrue(game.uncoverCell(4, 0));  // E1

        assertFalse(game.isGameWon(), "The game should be ongoing and not be won yet");

        // Act: Uncover a mine cell, which should trigger game loss
        assertFalse(game.uncoverCell(0, 2));  // A3

        assertFalse(game.isGameWon(), "The game should be lost after hitting a mine");
    }

    @Test
    public void testGameWin_whenAllSafeCellsAreUncovered_shouldDeclareVictory() {
        // Act: Uncover all safe cells
        assertTrue(game.uncoverCell(0, 1));  // A2
        assertTrue(game.uncoverCell(0, 1));  // A2
        assertTrue(game.uncoverCell(0, 4));  // A5
        assertTrue(game.uncoverCell(1, 0));  // B1
        assertTrue(game.uncoverCell(3, 0));  // D1
        assertTrue(game.uncoverCell(4, 0));  // E1
        assertTrue(game.uncoverCell(4, 1));  // E2
        assertTrue(game.uncoverCell(4, 2));  // E3
        assertTrue(game.uncoverCell(4, 3));  // E4

        game.displayBoard();
        assertTrue(game.isGameWon(), "The game should be won after uncovering all safe cells");
    }
}
