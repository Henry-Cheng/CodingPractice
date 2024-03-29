// https://leetcode.com/problems/design-tic-tac-toe/
class TicTacToe {

    /**
    n==5
    04
    13
    22
    31
    40
    **/
    int[][] rowCount;
    int[][] colCount;
    int[][] diagonalCount;
    int n;
    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        this.n = n;
        rowCount = new int[n][2]; // rowCount[i][j] means at row i-1, player j-1 has how many score
        colCount = new int[n][2]; // colCount[i][j] means at col i-1, player j-1 has how many score
        diagonalCount = new int[2][2]; // diagonalCount[i][j] means at diagnoal i-1, player j-1 has how many score
    }
    
    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    public int move(int row, int col, int player) {
        //System.out.println("row is " + row);
        rowCount[row][player-1]++;
        colCount[col][player-1]++;
        if (row == col) {
            diagonalCount[0][player-1]++;
        }
        if (row+col == n-1) {
            diagonalCount[1][player-1]++;
        }
        if (rowCount[row][player-1] == n || colCount[col][player-1] == n || diagonalCount[0][player-1] == n || diagonalCount[1][player-1] == n) {
            return player;
        }
        return 0;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */