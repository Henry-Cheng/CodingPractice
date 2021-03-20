// https://leetcode.com/problems/unique-paths/
class Solution {
    public int uniquePaths(int m, int n) {
        // [] [] [] []
        // [] [] [] []
        // [] [] [] []
        
        // initialize a bi-directional array
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) { // initial position
                    dp[i][j] = 1;
                } else if (i - 1 < 0 && j - 1 >= 0) { // no left node
                    dp[i][j] = dp[i][j-1];
                } else if (i - 1 >= 0 && j -1 < 0) { // no up node 
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[m -1][n - 1];
    }
}