public class sudokuSolver {

    private static final int boxSize = 3;
    private static final int gridSize = boxSize * boxSize;


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
                {0, 2, 0, 0, 0, 0, 1, 0, 0}, };

        if(solve(board)) {
            System.out.println();
            System.out.println("Yay!😊");
            System.out.println();
            printResult(board);
        } else {
            System.out.println("Oops☹️");
        }
    }

    private static void printResult(int[][] board) {
        for (int row = 0; row < gridSize; row++) {
            if(row % boxSize == 0 && row != 0) {
                System.out.println("-------------------");
            }
            for (int col = 0; col < gridSize; col++) {
                if(col % boxSize == 0 && col != 0) {
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

    //    condition 1
    private static boolean allowedInRow(int[][] board, int row, int number) {
        for (int i = 0; i < gridSize; i++) {
            if (board[row][i] == number) {
                return false;
            }
        }
        return true;
    }

//    condition 2
    private static boolean allowedInColumn(int[][] board, int col, int number) {
        for (int i = 0; i < gridSize; i++) {
            if (board[i][col] == number) {
                return false;
            }
        }
        return true;
    }

//    condition 3
    private static boolean allowedInBox(int[][] board, int row, int col, int number) {

        final int boxRow = row - (row % boxSize);
        final int boxCol = col - (col % boxSize);

        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                if (board[boxRow + i][boxCol + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

//    checks all the 3 conditions
    private static boolean isAllowed(int[][] board, int row, int col, int number) {
        boolean b = allowedInRow(board, row, number) &&
                allowedInColumn(board, col, number) &&
                allowedInBox(board, row, col, number);

        return b;
    }

    private static boolean solve(int[][] board)
    {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if(board[row][col] == 0) {
                    for (int num = 1; num <= gridSize; num++) {
                        if(isAllowed(board, row, col, num)) {

                            board[row][col] = num;

                            if(solve(board))
                                return true;

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