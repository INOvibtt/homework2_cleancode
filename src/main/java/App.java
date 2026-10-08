import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    private static final int SIZE = 9;
    private static final int[][] WINNING_LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public static void main(String[] args) {
        System.out.println("Enter box number to select. Enjoy!");
        try (Scanner scanner = new Scanner(System.in)) {
            play(scanner, new Random());
        }
    }

    static String play(String moves, Random random) {
        return play(new Scanner(moves), random);
    }

    static String play(Scanner scanner, Random random) {
        char[] board = new char[SIZE];
        Arrays.fill(board, ' ');

        while (true) {
            printBoard(board);
            int position = readMove(scanner, board);
            board[position] = 'X';

            if (hasWon(board, 'X')) return finish(board, "X", "You won the game!");
            if (isFull(board)) return finish(board, "DRAW", "It's a draw!");

            makeComputerMove(board, random);
            if (hasWon(board, 'O')) return finish(board, "O", "You lost the game!");
            if (isFull(board)) return finish(board, "DRAW", "It's a draw!");
        }
    }

    private static int readMove(Scanner scanner, char[] board) {
        while (scanner.hasNext()) {
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Enter again.");
                continue;
            }
            int position = scanner.nextInt() - 1;
            if (position < 0 || position >= SIZE) {
                System.out.println("Invalid input. Enter again.");
            } else if (board[position] != ' ') {
                System.out.println("That one is already in use. Enter another.");
            } else {
                return position;
            }
        }
        throw new IllegalStateException("No more moves were provided.");
    }

    private static void makeComputerMove(char[] board, Random random) {
        int available = 0;
        for (char cell : board) if (cell == ' ') available++;
        int choice = random.nextInt(available);
        for (int position = 0; position < SIZE; position++) {
            if (board[position] == ' ' && choice-- == 0) {
                board[position] = 'O';
                return;
            }
        }
    }

    static boolean hasWon(char[] board, char player) {
        for (int[] line : WINNING_LINES) {
            if (board[line[0]] == player && board[line[1]] == player && board[line[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFull(char[] board) {
        for (char cell : board) if (cell == ' ') return false;
        return true;
    }

    private static void printBoard(char[] board) {
        for (int row = 0; row < 3; row++) {
            if (row > 0) System.out.println("-----------");
            int start = row * 3;
            System.out.println(" " + display(board, start) + " | " + display(board, start + 1)
                    + " | " + display(board, start + 2));
        }
        System.out.println();
    }

    private static char display(char[] board, int position) {
        return board[position] == ' ' ? Character.forDigit(position + 1, 10) : board[position];
    }

    private static String finish(char[] board, String result, String message) {
        printBoard(board);
        System.out.println(message + "\nCreated by Shreyas Saha. Thanks for playing!");
        return result;
    }
}
