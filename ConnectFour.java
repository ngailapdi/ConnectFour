/**
 * This file contains the logic for playing a connect four game
 * either against another player or a random agent, depending on
 * the given command line args.
 *
 * "I worked on the homework assignment alone, using only course materials."
 *
 * @author Ngoc Anh Thai
 * @author Julia Ting
 */
import java.util.Scanner;
import java.util.Random;
public class ConnectFour {

    /*
     * Static variables to use throughout printBoard()
     * and the main method. You MUST use "X" and "O",
     * so you might as well use TOKEN1 and TOKEN2 variables!
     */
    private static final int GAME_WIDTH = 7;
    private static final int GAME_HEIGHT = 6;
    private static final String TOKEN1 = "X";
    private static final String TOKEN2 = "O";
    private static int a1 = 0, a2 = 0, a3 = 0, a4 = 0, a5 = 0, a6 = 0, a7 = 0;
    private static int mode;

    /**
     * Alter this variable when making changes to your
     * connect four board!!
     */
    private static String[][] board = new String[GAME_HEIGHT][GAME_WIDTH];

    /**
     * This enumeration is used as outcomes for findWinner().
     *
     * Read the pdf for a complete description. Think of how
     * GameStatus.QUIT might help you in your game functionality!
     */
    private enum GameStatus {
        ONE, TWO, TIE, ONGOING, QUIT
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (args.length == 0) {
            ret("ask");
            mode = scan.nextInt();
            scan.nextLine();
        } else {
            mode = Integer.parseInt(args[0]);
        }
        System.out.printf("Welcome to %s-player mode of Connect Four!%n", mode);
        ret("instruct");
        System.out.println("Type 'q' if you would like to quit.");
        String input;
        int num, a = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = "";
            }
        }
        printBoard();
        if (mode == 2) {
            ret("p1");
            input = scan.nextLine();
            outerloop:
            while (!input.equals("q")) {
                num = Integer.parseInt(input);
                if (!s(num).equals(TOKEN1) && !s(num).equals(TOKEN2)) {
                    ret("instruct");
                    System.out.println("Type 'q' if you would like to quit.");
                    if (a % 2 == 1) {
                        boardHelper(num, TOKEN1);
                    } else {
                        boardHelper(num, TOKEN2);
                    }
                    printBoard();
                    switch (findWinner()) {
                    case ONE:
                        System.out.println("Player 1 wins!"); break outerloop;
                    case TWO:
                        System.out.println("Player 2 wins!"); break outerloop;
                    case TIE:
                        System.out.println("It's a tie!"); break outerloop;
                    default: break;
                    } a = a + 1;
                    if (a % 2 == 0) {
                        ret("p2");
                    } else {
                        ret("p1");
                    }
                    input = scan.nextLine();
                } else {
                    while (s(num).equals(TOKEN1) || s(num).equals(TOKEN2)) {
                        ret("full");
                        if (a % 2 == 0) {
                            ret("p2");
                        } else {
                            ret("p1");
                        }
                        input = scan.nextLine();
                        if (!input.equals("q")) {
                            num = Integer.parseInt(input);
                        }
                    }
                }
            }
            if (input.equals("q")) {
                printBoard();
                System.out.println("Goodbye!");
            }
        } else {
            ret("p1");
            input = scan.nextLine();
        outerloop1:
            while (!input.equals("q")) {
                num = Integer.parseInt(input);
                if (!s(num).equals(TOKEN1) && !s(num).equals(TOKEN2)) {
                    ret("instruct");
                    System.out.println("Type 'q' if you would like to quit.");
                    boardHelper(num, TOKEN1);
                    printBoard();
                    switch (findWinner()) {
                    case ONE:
                        System.out.println("Player 1 wins!"); break outerloop1;
                    case TWO:
                        System.out.println("Computer wins!"); break outerloop1;
                    case TIE:
                        System.out.println("It's a tie!"); break outerloop1;
                    default: break;
                    }
                    Random rand = new Random();
                    int c = rand.nextInt(7) + 1;
                    if (!s(c).equals(TOKEN1) && !s(c).equals(TOKEN2)) {
                        boardHelper(c, TOKEN2);
                    } else if (s(c).equals(TOKEN1) || s(c).equals(TOKEN2)) {
                        while (s(c).equals(TOKEN1) || s(c).equals(TOKEN2)) {
                            c = rand.nextInt(7) + 1;
                        }
                        boardHelper(c, TOKEN2);
                    }
                    printBoard();
                    switch (findWinner()) {
                    case ONE:
                        System.out.println("Player 1 wins!"); break outerloop1;
                    case TWO:
                        System.out.println("Computer wins!"); break outerloop1;
                    case TIE:
                        System.out.println("It's a tie!"); break outerloop1;
                    default: break;
                    }
                    ret("p1");
                    input = scan.nextLine();
                } else if (s(num).equals(TOKEN1) || s(num).equals(TOKEN2)) {
                    while (s(num).equals(TOKEN1) || s(num).equals(TOKEN2)) {
                        ret("full");
                        ret("p1");
                        input = scan.nextLine();
                        if (!input.equals("q")) {
                            num = Integer.parseInt(input);
                        } else {
                            break outerloop1;
                        }
                    }
                }
            }
            if (input.equals("q")) {
                printBoard();
                System.out.println("Goodbye!");
            }
        }
    }
    private static void ret(String cond) {
        switch (cond) {
        case "ask":
            String str1 = "Would you like to play single player ";
            String str2 = "or two player mode? (Enter 1 or 2)";
            System.out.println(str1 + str2);
            break;
        case "instruct":
            String str3 = "Choose where to go by ";
            String str4 = "entering the slot number.";
            System.out.println(str3 + str4);
            break;
        case "p1":
            String str5 = "Player 1, where would ";
            String str6 = "you like to go?";
            System.out.println(str5 + str6);
            break;
        case "p2":
            String str7 = "Player 2, where would ";
            String str8 = "you like to go?";
            System.out.println(str7 + str8);
            break;
        case "full":
            String str9 = "The column is full. ";
            String str10 = "Please pick another!";
            System.out.println(str9 + str10);
            break;
        default: break;
        }
    }
    private static int geta1() {
        a1++;
        return a1;
    }
    private static int geta2() {
        a2++;
        return a2;
    }
    private static int geta3() {
        a3++;
        return a3;
    }
    private static int geta4() {
        a4++;
        return a4;
    }
    private static int geta5() {
        a5++;
        return a5;
    }
    private static int geta6() {
        a6++;
        return a6;
    }
    private static int geta7() {
        a7++;
        return a7;
    }
    private static String s(int number) {
        return board[0][number - 1];
    }
    private static void boardHelper(int number, String play) {
        switch (number) {
        case 1: ConnectFour.geta1(); board[6 - a1][0] = play; break;
        case 2: geta2(); board[6 - a2][1] = play; break;
        case 3: geta3(); board[6 - a3][2] = play; break;
        case 4: geta4(); board[6 - a4][3] = play; break;
        case 5: geta5(); board[6 - a5][4] = play; break;
        case 6: geta6(); board[6 - a6][5] = play; break;
        case 7: geta7(); board[6 - a7][6] = play; break;
        default: break;
        }
    }

    /**
     * Prints the current state of the board array.
     *
     * @return void
     **/
    private static void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j].isEmpty()) {
                    System.out.print("|   " + board[i][j] + "  ");
                } else {
                    System.out.print("|  " + board[i][j] + "  ");
                }
            }
            if (board[i][6].isEmpty()) {
                System.out.print("|   " + board[i][6] + "  |");
            } else {
                System.out.print("|  " + board[i][6] + "  |");
            }
            System.out.println();
        }
        for (int i = 1; i <= 43; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 1; i <= 7; i++) {
            System.out.print("   " + i + "  ");
        }
        System.out.println();
    }
    /**
     * Determines what the current result of the game is
     * given the current state of the board.
     *
     * @return GameStatus enumeration value that determines
     * if player one has won, player two has won, a tie exists,
     * or there is no result yet.
     */
    private static GameStatus findWinner() {
        if (isColumnVictory(TOKEN1) || isRowVictory(TOKEN1)
                 || isTopLeftDiagonalVictory(TOKEN1)
                 || isTopRightDiagonalVictory(TOKEN1)) {
            return GameStatus.ONE;
        } else if (isColumnVictory(TOKEN2) || isRowVictory(TOKEN2)
                 || isTopLeftDiagonalVictory(TOKEN2)
                 || isTopRightDiagonalVictory(TOKEN2)) {
            return GameStatus.TWO;
        } else if (isBoardFull()) {
            return GameStatus.TIE;
        } else {
            return GameStatus.ONGOING;
        }
    }

    /*
     * ~~~~~~YOU SHOULD NOT BE CALLING METHODS BELOW THIS POINT~~~~~
     */

    /**
     * Determines whether or not the board has any moves
     * remaining.
     *
     * @return true or false
     */
    private static boolean isBoardFull() {
        for (int col = 0; col < GAME_WIDTH; col++) {
            for (int row = 0; row < GAME_HEIGHT; row++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Determines whether or not a player has won via 4 in a row
     * in columns.
     *
     * @return true or false
     */
    private static boolean isColumnVictory(String token) {
        for (int col = 0; col < GAME_WIDTH; col++) {
            int count = 0;
            for (int row = 0; row < GAME_HEIGHT; row++) {
                if (board[row][col] != null) {
                    if (board[row][col].equals(token)) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Determines whether or not a player has won via 4 in a row
     * in rows.
     *
     * @return true or false
     */
    private static boolean isRowVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            int count = 0;
            for (int col = 0; col < GAME_WIDTH; col++) {
                if (board[row][col] != null) {
                    if (board[row][col].equals(token)) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Determines whether or not a player has won via 4 in a row
     * in top to left diagonals.
     *
     * @return true or false
     */
    private static boolean isTopLeftDiagonalVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            for (int col = 0; col < GAME_WIDTH; col++) {
                int count = 0;
                for (int delta = 0; delta < 5; delta++) {
                    if (withinBounds(row + delta, col + delta)
                             && board[row + delta][col + delta] != null) {
                        if (board[row + delta][col + delta].equals(token)) {
                            count++;
                        } else {
                            count = 0;
                        }
                    } else {
                        count = 0;
                    }
                    if (count == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Determines whether or not a player has won via 4 in a row
     * in top to right diagonals.
     *
     * @return true or false
     */
    private static boolean isTopRightDiagonalVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            for (int col = GAME_WIDTH - 1; col >= 0; col--) {
                int count = 0;
                for (int delta = 0; delta < 5; delta++) {
                    if (withinBounds(row + delta, col - delta)
                             && board[row + delta][col - delta] != null) {
                        if (board[row + delta][col - delta].equals(token)) {
                            count++;
                        } else {
                            count = 0;
                        }
                    } else {
                        count = 0;
                    }
                    if (count == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Small bounds checker helper method.
     */
    private static boolean withinBounds(int row, int col) {
        return (row < GAME_HEIGHT && row >= 0)
             && (col < GAME_WIDTH && col >= 0);
    }
}
