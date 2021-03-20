// https://leetcode.com/problems/minimum-path-sum/
class Solution {
    int minSum = Integer.MAX_VALUE;
    public int minPathSum(int[][] grid) {
        // only m + n - 1 spots,
        /**
        1 -> 3 -> 1
        |    |    |
        -> 1 -> 5 -> 1
           |    |    |   
           -> 4 -> 2 -> 1
        **/
        // 1. backtracking/brute force solution:
        //sumPathBacktracking(grid, 0, 0, grid.length, grid[0].length, grid[0][0]);
        //return minSum;
        // 2. DP top-down solution:
        // int maxI = grid.length;
        // int maxJ = grid[0].length;
        // return minPathSumDP(grid, maxI -1, maxJ - 1, maxI, maxJ, new Integer[maxI][maxJ]);
        // 3. DP bottom-up solution:
        return minPathSumBottomUp(grid);
    }
    
    // LC bottom-up solution
    // replace the grid value in place without using extra 2-dimensional array
    // T(mn) since each node would be processed, S(1) since no extra space
    protected int minPathSumBottomUp(int[][] grid) {
        int maxI = grid.length;
        int maxJ = grid[0].length;
        for (int i = 0; i < maxI; i++) {
            for (int j = 0; j < maxJ; j++) {
                if (i - 1 < 0 && j - 1 < 0) { // for node 0,0
                    // grid[0][0], do nothing
                } else if (i - 1 < 0 && j - 1 >=0) { // no left node
                    grid[i][j] = grid[i][j] + grid[i][j-1];
                } else if (i -1 >= 0 && j - 1 < 0) { // no up node
                    grid[i][j] = grid[i][j] + grid[i - 1][j];
                } else { // has left and up node
                    grid[i][j] = grid[i][j] + Math.min(grid[i - 1][j], grid[i][j-1]);
                }
            }
        }
        return grid[maxI -1][maxJ - 1];
    }
    
    // top-down DP with memorization
    // T(mn) since each node would have stack push, S(mn) since we use a dp matrix
    protected int minPathSumDP(int[][] grid, int i, int j, int maxI, int maxJ, Integer[][] dp) {
        if (i == -1 || j == -1) { // NOTE: be careful we are doing i-- and j--
            return Integer.MAX_VALUE;
        } else if (i == 0 && j == 0) { // NOTE: handle it specially since 0,-1 and -1,0 would both return MAX_VALUE
            return grid[i][j];
        } else if (dp[i][j] != null) {
            return dp[i][j];
        }
        int result = grid[i][j] + Math.min(
            minPathSumDP(grid, i-1, j, maxI, maxJ, dp), 
            minPathSumDP(grid, i, j-1, maxI, maxJ, dp));
        dp[i][j] = result;
        //System.out.println("i = " + i + ", j = " + j + ", dp[i][j] = " + dp[i][j] + ", grid[i][j] = " + grid[i][j]);
        return result;
    }
    
    // backtracking, the same as brute force, T(2 ^ (m+n)) since for each move we have at most 2 options, S(m+n) -- recusion depth is m + n
    protected void sumPathBacktracking(int[][] grid, int i, int j, int maxI, int maxJ, int currentSum) {
        //System.out.println("i = " + i + ", j = " + j);
        // we can move either right or down
        if(j < maxJ - 1) { // move right
            sumPathBacktracking(grid, i, j + 1, maxI, maxJ, currentSum + grid[i][j + 1]); 
        } else { // j has reached boundary
            while (i < maxI - 1) {
                i++;
                currentSum += grid[i][maxJ - 1];
            }
            minSum = Math.min(currentSum, minSum);
            return;
        }
        if (i < maxI - 1) {// move down
            sumPathBacktracking(grid, i + 1, j, maxI, maxJ, currentSum + grid[i + 1][j]); 
        } else { //i has raechd boundary
            while (j < maxJ - 1) {
                j++;
                currentSum += grid[maxI - 1][j];
            }
            minSum = Math.min(currentSum, minSum);
            return;
        }
    }
}