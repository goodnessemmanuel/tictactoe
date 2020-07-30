package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    //declaration of global state fields
    final static String EMPTY_CELL = " ";
    final static String PLAY_X = "X";
    final static String PLAY_O = "O";
    private static String[][] coordinates;
    private static String[][] gameBoard;

    public static void main(String[] args) {
        gameBoard = new String[3][3];
        //start with empty cells
        for (String[] strings : gameBoard) {
            Arrays.fill(strings, EMPTY_CELL);
        }
        boolean letterXHas3InRow = false;
        boolean letterOHas3InRow = false;
        int countEmptyCells = 0;

        setGameStateCoordinates(gameBoard);
        displayGame(gameBoard);
        boolean checkWinOrDraw = false;
        String message = "";

        while (!checkWinOrDraw) {
            requestGameFromUserAndInsertAtGivenCoordinate();
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (PLAY_X.equals(gameBoard[i][j]) && !letterXHas3InRow) {
                        if (countLeft(gameBoard, PLAY_X, i, j) >= 2 || countRight(gameBoard, PLAY_X, i, j) >= 2 ||
                                countUp(gameBoard, PLAY_X, i, j) >= 2 || countDown(gameBoard, PLAY_X, i, j) >= 2 ||
                                countDiagonal(gameBoard, PLAY_X, i, j) >= 2 ) {
                            letterXHas3InRow = true;
                        }
                    } else if (PLAY_O.equals(gameBoard[i][j]) && !letterOHas3InRow) {
                        if (countLeft(gameBoard, PLAY_O, i, j) >= 2 || countRight(gameBoard, PLAY_O, i, j) >= 2 ||
                                countUp(gameBoard, PLAY_O, i, j) >= 2 || countDown(gameBoard, PLAY_O, i, j) >= 2 ||
                                countDiagonal(gameBoard, PLAY_O, i, j) >= 2 ) {
                            letterOHas3InRow = true;
                        }
                    }
                }
            }
            displayGame(gameBoard);
            countEmptyCells = countPlayer(gameBoard, EMPTY_CELL);
            //definition of the game state
            if (countEmptyCells == 0 && !letterOHas3InRow && !letterXHas3InRow) {
                message = "Draw";
                checkWinOrDraw = true;
            } else if (letterXHas3InRow) {
                message = PLAY_X + " wins";
                checkWinOrDraw = true;
            } else if (letterOHas3InRow) {
                message = PLAY_O + " wins";
                checkWinOrDraw = true;
            }
        }
        System.out.println(message);
    }
    private static int countPlayer(String[][] games, String targetPlayer) {
        int playerCount = 0;
        for (String[] game: games) {
            for (String player: game) {
                if (targetPlayer.equals(player)) {
                    playerCount++;
                }
            }
        }
        return playerCount;
    }

    public static int countRight(String[][] games, String player, int r, int c) {
        int count = 0;
        for (int k = 1; k < games.length; k++){
            if (!player.equals(games[r][(c + k) % games.length])) {
                break;
            }
            count++;
        }
        return count;
    }

    public static int countLeft(String[][] games, String player, int r, int c) {
        int count = 0;
        for (int k = 1; k < games.length; k++){
            if (!player.equals(games[r][(c - k + games.length) % games.length])) {
                break;
            }
            count++;
        }
        return count;
    }

    public static int countUp(String[][] games, String player, int r, int c) {
        int count = 0;
        for (int k = 1; k < games.length; k++){
            if (!player.equals(games[(r - k + games.length) % games.length][c])) {
                break;
            }
            count++;
        }
        return count;
    }

    public static int countDown(String[][] games, String player, int r, int c) {
        int count = 0;
        for (int k = 1; k < games.length; k++){
            if (!player.equals(games[(r + k) % games.length][c])) {
                break;
            }
            count++;
        }
        return count;
    }

   public static int countDiagonal(String[][] games, String player, int r, int c) {
        int count = 0;
        if (r == 0 && c == 0) {
            for (int k = 1; k < games.length; k++){
                if (!player.equals(games[(r + k) % games.length][(c + k) % games.length])) {
                    break;
                }
                count++;
            }
        } else if (r== 0  && c == games.length - 1) {
            for (int k = 1; k < games.length; k++) {
                if (!player.equals(games[(r + k) % games.length][(c - k + games.length) % games.length])) {
                    break;
                }
                count++;
            }
        }
        return count;
    }
    private static void displayGame(String[][] gameData) {
        System.out.println("---------");
        for (String[] rows : gameData) {
            System.out.print("| ");
            for (String row : rows) {
                System.out.printf("%s ", row);
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
    private static String[][] insertGameToCoordinate(String coordinate, String value) {
        int len = gameBoard.length;
        for (int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if (coordinate.equals(coordinates[i][j]) && EMPTY_CELL.equals(gameBoard[i][j])) {
                    gameBoard[i][j] = value;
                    return gameBoard;
                }
            }
        }
        return gameBoard;
    }
    private static void setGameStateCoordinates(String[][] board) {
        int len = board.length;
        coordinates = new String[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                //store the coordinates according to the problem statement, column is left to right and row is bottom to top
                coordinates[i][j] = String.format("%d,%d", j + 1, Math.abs(i - (len - 1)) + 1);
            }
        }
    }
    //this method when invoked, calls itself repeatedly until user enters proper input is entered.
    private static void requestGameFromUserAndInsertAtGivenCoordinate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coordinates: ");
        int row = 0;
        int col = 0;
        if (scanner.hasNextInt()) {
            row = scanner.nextInt();
            col = scanner.nextInt();
            String coordinate = String.format("%d,%d", row, col);
            if (row > 3 || col > 3) {
                System.out.println("Coordinates should be 1 to 3!");
                requestGameFromUserAndInsertAtGivenCoordinate();
            } else if (isSpaceAtSelectedCordinate(coordinate)) {
                gameBoard = insertGameToCoordinate(coordinate, nextPlay(gameBoard));
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                requestGameFromUserAndInsertAtGivenCoordinate();
            }
        } else {
            System.out.println("You should enter numbers!");
            requestGameFromUserAndInsertAtGivenCoordinate();
        }
    }

    private static boolean isSpaceAtSelectedCordinate(String coordinate) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (coordinates[i][j].equals(coordinate) && EMPTY_CELL.equals(gameBoard[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String nextPlay(String[][] board) {
        return countPlayer(board, EMPTY_CELL) == 9 ? PLAY_X :
                countPlayer(board, PLAY_X) > countPlayer(board, PLAY_O) ? PLAY_O : PLAY_X;
    }
}