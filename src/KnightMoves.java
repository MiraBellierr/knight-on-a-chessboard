import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

public class KnightMoves {
    static Scanner scanner = new Scanner(System.in);

    // The following static array represents the 8 possible moves a knight can make
    // This is an 8x2 array
    static int[][] moves = { {-2, +1}, {-1, +2}, {+1, +2}, {+2, +1}, {+2, -1}, {+1, -2}, {-1, -2}, {-2, -1} };

    public static void main(String[] args) {
        System.out.println("Welcome to the " + "knight move calculator.\n");

        do {
            showKnightMoves();
        } while (getYesOrNo("Do it again?"));
    }

    public static void showKnightMoves() {
        // The first dimension is the file (a, b, c, d, e, f, g, h)
        // The second dimension is the rank (1, 2, 3, 4, 5, 6, 7, 8)
        // Thus, board[3][4] is d5
        // A value of 0 means the square is empty
        // 1 means the knight is in the square
        // 2 means the knight could move to the square
        int[][] board = new int[8][8];

        String knightSquare; // The knight's position as a square
        Position knightPosition; // The knight's position as a Pos

        // Get the knight's initial position
        do {
            System.out.print("Enter knight's position: ");
            knightSquare = scanner.nextLine();
            knightPosition = convertSquareToPosition(knightSquare);
        } while (knightPosition == null);

        board[knightPosition.x][knightPosition.y] = 1;

        System.out.println("\nThe knight is at square " + convertPositionToSquare(knightPosition));
        System.out.println("From here the knight can move to:");

        for (int[] ints : moves) {
            int x = ints[0];
            int y = ints[1];
            Position position = calculateNewPosition(knightPosition, x, y);

            if (position != null) {
                System.out.println(convertPositionToSquare(position));
                board[position.x][position.y] = 2;
            }
        }

        printBoard(board);
    }

    // This method converts squares such as a1 or d5 to x, y coordinates such as [0][0] or [3][4]
    public static @Nullable Position convertSquareToPosition(@NotNull String square) {
        int x = -1;
        int y = -1;
        char file = square.charAt(0);
        char rank = square.charAt(1);

        switch (file) {
            case 'a' -> x = 0;
            case 'b' -> x = 1;
            case 'c' -> x = 2;
            case 'd' -> x = 3;
            case 'e' -> x = 4;
            case 'f' -> x = 5;
            case 'g' -> x = 6;
            case 'h' -> x = 7;
        }

        switch (rank) {
            case '1' -> y = 0;
            case '2' -> y = 1;
            case '3' -> y = 2;
            case '4' -> y = 3;
            case '5' -> y = 4;
            case '6' -> y = 5;
            case '7' -> y = 6;
            case '8' -> y = 7;
        }

        if (x == -1 || y == -1) return null;
        else return new Position(x, y);
    }

    // This method converts x, y coordinates such as [0][0] or [3][4] to squares such as a1 or d5
    @Contract(pure = true)
    public static @NotNull String convertPositionToSquare(@NotNull Position position) {
        String file = "";

        switch (position.x) {
            case 0 -> file = "a";
            case 1 -> file = "b";
            case 2 -> file = "c";
            case 3 -> file = "d";
            case 4 -> file = "e";
            case 5 -> file = "f";
            case 6 -> file = "g";
            case 7 -> file = "h";
        }

        return file + (position.y + 1);
    }

    // This method calculates a new position
    // Given a starting position, an x move, and a y move
    // It returns null if the resulting move would be off the board
    @Contract(pure = true)
    public static @Nullable Position calculateNewPosition(@NotNull Position position, int x, int y) {
        //rule out legal moves
        if (position.x + x < 0 || position.x + x > 7 || position.y + y < 0 || position.y + y > 7) return null;

        // return new position
        return new Position(position.x + x, position.y + y);
    }

    public static void printBoard(int[][] board) {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (board[x][y] == 1) System.out.print(" X ");
                else if (board[x][y] == 2) System.out.print(" ? ");
                else System.out.print(" - ");
            }
            System.out.println();
        }
    }

    public static boolean getYesOrNo(String prompt) {
        while (true) {
            String answer;

            System.out.print("\n" + prompt + " (Y or N) ");
            answer = scanner.nextLine();

            return answer.equalsIgnoreCase("Y");
        }
    }
}

// This class represents x, y coordinates on the board
class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
