public class sudokuSolver {

    private static final int BOX_SIZE = 3;
    private static final int GRID_SIZE = BOX_SIZE * BOX_SIZE;

    public static void main(String[] args) {
        int[][] board = {
                        {0, 0, 0, 6, 0, 0, 4, 0, 0},
                        {7, 0, 0, 0, 0, 3, 6, 0, 0},
                        {0, 0, 0, 0, 9, 1, 0, 8, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 5, 0, 1, 8, 0, 0, 0, 3},
                        {0, 0, 0, 3, 0, 6, 0, 4, 5},
                        {0, 4, 0, 2, 0, 0, 0, 6, 0},
                        {9, 0, 3, 0, 0, 0, 0, 0, 0},
                        {0, 2, 0, 0, 0, 0, 1, 0, 0},
        };

        if (isValidSudoku(board)) {
            if (solve(board)) {
                System.out.println("Yay!😊 Sudoku solved successfully!");
                printResult(board);
            } else {
                System.out.println("Oops☹️ Unable to solve the Sudoku puzzle.");
            }
        } else {
            System.out.println("Invalid Sudoku board. Please check the input.");
        }
    }

    private static boolean isValidSudoku(int[][] board) {
        // Validate rows
        for (int i = 0; i < GRID_SIZE; i++) {
            if (isValidGroup(board[i])) {
                return false;
            }
        }

        // Validate columns
        for (int j = 0; j < GRID_SIZE; j++) {
            int[] column = new int[GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                column[i] = board[i][j];
            }
            if (isValidGroup(column)) {
                return false;
            }
        }

        // Validate boxes
        for (int startRow = 0; startRow < GRID_SIZE; startRow += BOX_SIZE) {
            for (int startCol = 0; startCol < GRID_SIZE; startCol += BOX_SIZE) {
                int[] box = new int[GRID_SIZE];
                int index = 0;
                for (int i = 0; i < BOX_SIZE; i++) {
                    for (int j = 0; j < BOX_SIZE; j++) {
                        box[index++] = board[startRow + i][startCol + j];
                    }
                }
                if (isValidGroup(box)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isValidGroup(int[] group) {
        boolean[] seen = new boolean[GRID_SIZE + 1];
        for (int value : group) {
            if (value != 0 && seen[value]) {
                return true; // Duplicate value found
            }
            seen[value] = true;
        }
        return false;
    }

    private static void printResult(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % BOX_SIZE == 0 && row != 0) {
                System.out.println("-------------------");
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                if (col % BOX_SIZE == 0 && col != 0) {
                    System.out.print("|");
                }
                final int cellValue = board[row][col];
                if (cellValue == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(cellValue);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static boolean allowedInRow(int[][] board, int row, int number) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return false;
            }
        }
        return true;
    }

    private static boolean allowedInColumn(int[][] board, int col, int number) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == number) {
                return false;
            }
        }
        return true;
    }

    private static boolean allowedInBox(int[][] board, int row, int col, int number) {
        final int boxRow = row - (row % BOX_SIZE);
        final int boxCol = col - (col % BOX_SIZE);

        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                if (board[boxRow + i][boxCol + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isAllowed(int[][] board, int row, int col, int number) {
        return allowedInRow(board, row, number) &&
                allowedInColumn(board, col, number) &&
                allowedInBox(board, row, col, number);
    }

    private static boolean solve(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isAllowed(board, row, col, num)) {
                            board[row][col] = num;

                            if (solve(board)) {
                                return true;
                            }

                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
