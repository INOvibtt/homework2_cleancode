import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private static final Random FIRST_AVAILABLE = new Random() {
        @Override public int nextInt(int bound) { return 0; }
    };

    @Test
    void playerCanWin() {
        assertEquals("X", App.play("3 5 9 6", FIRST_AVAILABLE));
    }

    @Test
    void computerCanWin() {
        assertEquals("O", App.play("4 5 7", FIRST_AVAILABLE));
    }

    @Test
    void detectsEveryWinningLine() {
        int[][] lines = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},
                {1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int[] line : lines) {
            char[] board = "         ".toCharArray();
            for (int position : line) board[position] = 'X';
            assertTrue(App.hasWon(board, 'X'));
            assertFalse(App.hasWon(board, 'O'));
        }
    }

    @Test
    void printsCellNumbersAndRejectsBadMoves() {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            assertEquals("X", App.play(new Scanner("bad 10 3 3 5 9 6"), FIRST_AVAILABLE));
        } finally {
            System.setOut(original);
        }
        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("1 | 2 | 3"));
        assertTrue(text.contains("Invalid input"));
        assertTrue(text.contains("already in use"));
    }
}
