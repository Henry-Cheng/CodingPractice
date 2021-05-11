// https://leetcode.com/problems/valid-tic-tac-toe-state/
class Solution {
    public boolean validTicTacToe(String[] board) {
        int[][] rowCount = new int[board.length][2];
        int[][] colCount = new int[board.length][2];
        int[][] diagnoalCount = new int[2][2];
        int xCount = 0;
        int oCount = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (board[i].charAt(j) == 'X') {
                    xCount++;
                    rowCount[i][0]++;
                    colCount[j][0]++;
                    if (i == j) {
                        diagnoalCount[0][0]++;
                    }
                    if (i+j == board.length-1) {
                        diagnoalCount[1][0]++;
                    }
                } else if (board[i].charAt(j) == 'O') {
                    oCount++;
                    rowCount[i][1]++;
                    colCount[j][1]++;
                    if (i == j) {
                        diagnoalCount[0][1]++;
                    }
                    if (i+j == board.length-1) {
                        diagnoalCount[1][1]++;
                    }
                }
            }
        }
        if (oCount < xCount - 1 || oCount > xCount) {
            return false;
        }
        boolean xWin = diagnoalCount[0][0] == board.length || diagnoalCount[1][0] == board.length;
        boolean oWin = diagnoalCount[0][1] == board.length || diagnoalCount[1][1] == board.length;
        for(int i = 0; i < board.length; i++) {
            if (rowCount[i][0] == board.length || colCount[i][0] == board.length) {
                xWin = true;
            }
            if (rowCount[i][1] == board.length || colCount[i][1] == board.length) {
                oWin = true;
            }
        }
        if (xWin && oWin) {
            return false;
        } else if (xWin && oCount == xCount) {
            return false;
        } else if (oWin && oCount == xCount -1) {
            return false;
        }
        return true;
    }
}