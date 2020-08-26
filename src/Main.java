import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final String BEGINNER_HEADER = "Current Status of Board :\n  0 1 2 3 4 5 6 7 8";
    public static final String IMEDIATE_HEADER = "Current Status of Board :\n  0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15";
    public static final String ADVANCED_HEADER = "Current Status of Board :\n  0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = 0;
        int cols = 0;
        int fieldSize = 0;
        int win = 0;
        int mines = 0;
        Random random = new Random();

        System.out.println("Enter the Difficulty Level");
        System.out.println("Press 0 for BEGINNER (9 * 9 Cells and 10 mines)");
        System.out.println("Press 1 for INTERMEDIATE (16 * 16 Cells and 40 mines)");
        System.out.println("Press 2 for ADVANCED (24 * 24 and 99 mines)");

        fieldSize = Integer.parseInt(scanner.nextLine());
        String finalHeader = "";

        //Setting the game
        if (fieldSize == 0) {
            rows = 9;
            cols = 9;
            mines = 10;
            finalHeader = BEGINNER_HEADER;
            win = 71;
        } else if (fieldSize == 1) {
            rows = 16;
            cols = 16;
            mines = 40;
            finalHeader = IMEDIATE_HEADER;
            win = 216;
        } else if (fieldSize == 2) {
            rows = 24;
            cols = 24;
            mines = 99;
            finalHeader = ADVANCED_HEADER;
            win = 477;
        }


        //Creating a field with bombs
        char[][] bombField = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                bombField[r][c] = '-';
            }
        }

        //Creating a player's field
        char[][] playerField = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                playerField[r][c] = '-';
            }
        }

        //Intro
        System.out.println(finalHeader);
        for (int i = 0; i < rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(playerField[i][j] + " ");
            }
            System.out.println();
        }

        //Next few lines are insurance that we can't die in the first round
        System.out.println("Enter your move, (row, column)");
        int currRow = Integer.parseInt(scanner.nextLine());
        int currCol = Integer.parseInt(scanner.nextLine());

        /*Putting the bombs on the field*/
        for (int i = 0; i < mines; i++) {
            int rndRow = random.nextInt(rows);
            int rndCol = random.nextInt(cols);

            if (bombField[rndRow][rndCol] != 11332) {
                bombField[rndRow][rndCol] = 11332;
            } else {
                i--;
            }
        }



        // Printing the mines on screen

        //for (int i = 0; i < rows; i++) {
        //    System.out.print(i + " ");
        //    for (int j = 0; j < cols; j++) {
        //        System.out.print(bombField[i][j] + " ");
        //    }
        //    System.out.println();
        //}

        while (bombField[currRow][currCol] == 11332) {
            for (int i = 0; i < mines; i++) {
                int rndRow = random.nextInt(rows);
                int rndCol = random.nextInt(cols);

                if (bombField[rndRow][rndCol] != 11332) {
                    bombField[rndRow][rndCol] = 11332;
                } else {
                    i--;
                }
            }
        }

        System.out.println(finalHeader);
        for (int i = 0; i < rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(playerField[i][j] + " ");
            }
            System.out.println();
        }


        boolean playerAlive = true;

        while (playerAlive) {
            int bombCounter = 0;

            System.out.println("Enter your move, (row, column)");

            int chosenRow = Integer.parseInt(scanner.nextLine());
            int chosenCol = Integer.parseInt(scanner.nextLine());

            //Checking for mines
            if (chosenRow > 0 && chosenRow < rows) {
                if (chosenCol > 0 && chosenCol < cols) {
                    bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                    bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                    bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                    bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                    bombCounter = checkUpLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol - 1);
                    bombCounter = checkDownLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol - 1);
                    bombCounter = checkDownRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol + 1);
                    bombCounter = checkUpRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol + 1);
                }
            }
            if (chosenRow == 0 && chosenCol == 0) {
                bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                bombCounter = checkDownRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol + 1);
            }
            if (chosenRow == 0 && chosenCol == cols) {
                bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                bombCounter = checkDownLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol - 1);
            }
            if (chosenRow == rows && chosenCol == 0) {
                bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                bombCounter = checkUpRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol + 1);
            }
            if (chosenRow == rows && chosenCol == cols) {
                bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                bombCounter = checkUpLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol - 1);
            }
            if (chosenRow == 0 && chosenCol > 0 && chosenCol < cols) {
                bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                bombCounter = checkDownLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol - 1);
                bombCounter = checkDownRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol + 1);
            }
            if (chosenRow == rows && chosenCol > 0 && chosenCol < cols) {
                bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                bombCounter = checkUpRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol + 1);
                bombCounter = checkUpLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol - 1);
            }
            if (chosenRow > 0 && chosenRow < rows && chosenCol == 0) {
                bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                bombCounter = checkColUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol + 1);
                bombCounter = checkDownRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol + 1);
                bombCounter = checkUpRightCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol + 1);
            }
            if (chosenRow > 0 && chosenRow < rows && chosenCol == cols) {
                bombCounter = checkRowDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol);
                bombCounter = checkRowUp(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol);
                bombCounter = checkColDown(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow, chosenCol - 1);
                bombCounter = checkUpLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow - 1, chosenCol - 1);
                bombCounter = checkDownLeftCorner(bombCounter, bombField, playerField, chosenRow, chosenCol, chosenRow + 1, chosenCol - 1);
            }

            win--;
            if (win == 0) {
                System.out.println("You win!");
                break;
            }

            if (bombField[chosenRow][chosenCol] == 11332) {
                playerAlive = false;
                System.out.println("You lost!");
                break;
            }

            char currChar = (char) (bombCounter + '0');
            playerField[chosenRow][chosenCol] = currChar;

            //Printing the field
            System.out.println(finalHeader);
            for (int i = 0; i < rows; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < cols; j++) {
                    System.out.print(playerField[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    private static int checkUpRightCorner(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int i3) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][i3] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkDownRightCorner(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int i3) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][i3] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkDownLeftCorner(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int i3) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][i3] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkUpLeftCorner(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int i3) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][i3] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkColUp(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int chosenRow2, int i2) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[chosenRow2][i2] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkColDown(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int chosenRow2, int i2) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[chosenRow2][i2] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkRowUp(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int chosenCol2) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][chosenCol2] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }

    private static int checkRowDown(int bombCounter, char[][] bombField, char[][] playerField, int chosenRow, int chosenCol, int i2, int chosenCol2) {
        if (playerField[chosenRow][chosenCol] == bombField[chosenRow][chosenCol] &&
                bombField[i2][chosenCol2] == 11332) {
            bombCounter++;
        }
        return bombCounter;
    }
}



