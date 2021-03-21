// https://leetcode.com/problems/number-of-islands/solution/
class Solution {
    /**
    Time complexity : O(M * N) where M is the number of rows and N is the number of columns.
    Space complexity : worst case  O(M * N) in case that the grid map is filled with lands where DFS goes by M * N deep.
    **/
    boolean[][] visited;
    public int maxAreaOfIsland(int[][] grid) {
        visited = new boolean[grid.length][grid[0].length];
        int maxArea = Integer.MIN_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j]) {
                    if (grid[i][j] == 1) {
                        int islandArea = dfs(grid, i, j);
                        maxArea = Math.max(maxArea, islandArea);
                    }
                    visited[i][j] = true;
                }
            }
        }
        return Math.max(maxArea, 0);
    }
    public int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        if (grid[i][j] == 1) {
            // try 4 directions
            return 1 + dfs(grid, i - 1, j) + dfs(grid, i + 1, j) +
                dfs(grid, i, j - 1) + dfs(grid, i, j + 1);
        } else {
            return 0;
        }
    }
}