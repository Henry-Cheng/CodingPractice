
// https://leetcode.com/problems/battleships-in-a-board/
class Solution {
    public int countBattleships(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                ifa (board[i][j] == 'X') {
                    if ((i == 0 || (i > 0 && board[i-1][j] == '.')) && (j == 0 || (j > 0 && board[i][j-1] == '.'))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}