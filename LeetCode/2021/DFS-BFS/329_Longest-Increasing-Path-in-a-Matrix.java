// https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
class Solution {
    private static int[][] DIRECTIONS = {
        {-1,0},{1,0},{0,-1},{0,1}
    };
    public int longestIncreasingPath(int[][] matrix) {
        int[][] memo = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (memo[i][j] == 0) {
                    int dist = dfs(matrix, i, j, memo);
                    max = Math.max(max, memo[i][j]);
                }   
            }
        }
        return max;
    }
    
    private int dfs(int[][] matrix, int row, int col, int[][] memo) {
        //System.out.println("dfs on [" + row + "," + col + "]  is " + matrix[row][col]);
        int dist = 1;
        // try 4 directions:
        for (int i = 0; i < 4; i++) {
            int newRow = row + DIRECTIONS[i][0];
            int newCol = col + DIRECTIONS[i][1];
            if (isValid(matrix, newRow, newCol) && 
                matrix[newRow][newCol] > matrix[row][col]) {
                int nextDist = 1;
                if (memo[newRow][newCol] > 0) {
                    nextDist += memo[newRow][newCol];
                } else {
                    nextDist += dfs(matrix, newRow, newCol, memo);
                }
                //System.out.println("try " + newRow+ " and " + newCol + " which is " + matrix[newRow][newCol] + " and dist is " + (nextDist- 1));
                dist = Math.max(dist, nextDist);
            }
        }
        memo[row][col] = dist;
        //System.out.println("[" + row + "," + col + "]  is " + matrix[row][col]  + "-->" + memo[row][col]);
        return dist;
    }
    
    private boolean isValid(int[][] matrix, int row, int col) {
        if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length) {
            return true;
        }
        return false;
    }
}
