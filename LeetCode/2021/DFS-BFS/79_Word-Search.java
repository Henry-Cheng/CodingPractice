// https://leetcode.com/problems/word-search/solution/
// backtracking O(N⋅3^L) where N is the number of cells and L is the length of the word (it is 3^L instead of 4^L since we do not need to check the incomding direction )
class Solution {
    public boolean exist(char[][] board, String word) {
        // find all entryPoints for word
        char startingChar = word.charAt(0);
        boolean[][] visited  = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == startingChar) {
                    visited[i][j] = true;
                    boolean exists = dfs(board, word, i, j, visited);
                    if (exists) {
                        return true;
                    } else {
                        visited[i][j] = false;
                    }
                    // if not exists for this entry point, try another one
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, boolean[][] visited) {
        if (word.length() == 1) {
            return true;
        }
        String newWord = word.substring(1, word.length());
        char startingChar = newWord.charAt(0);
        // try up
        if (i - 1 >= 0 && !visited[i-1][j] && board[i -1][j] == startingChar) {
            visited[i-1][j] = true;
            boolean found = dfs(board, newWord, i - 1, j, visited);
            if (!found) {
                visited[i-1][j] = false;
            } else {
                return true;
            }
        }
        // try down
        if (i + 1 < board.length && !visited[i+1][j] && board[i+1][j] == startingChar) {
            visited[i+1][j] = true;
            boolean found = dfs(board, newWord, i + 1, j, visited);
            if (!found) {
                visited[i+1][j] = false;
            } else {
                return true;
            }
        }
        // try left
        if (j - 1 >= 0 && !visited[i][j-1] && board[i][j-1] == startingChar) {
            visited[i][j-1] = true;
            boolean found = dfs(board, newWord, i, j-1, visited);
            if (!found) {
                visited[i][j-1] = false;
            } else {
                return true;
            }
        }
        // try right
        if (j + 1 < board[0].length && !visited[i][j+1] && board[i][j+1] == startingChar) {
            visited[i][j+1] = true;
            boolean found = dfs(board, newWord, i, j+1, visited);
            if (!found) {
                visited[i][j+1] = false;
            } else {
                return true;
            }
        }
        return false;
    }
}