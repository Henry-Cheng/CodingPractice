// https://leetcode.com/problems/number-of-islands/solution/
class Solution {
    /**
    Time complexity : O(M * N) where MM is the number of rows and NN is the number of columns. Note that Union operation takes essentially constant time[1] when UnionFind is implemented with both path compression and union by rank.

Space complexity : O(M * N) as required by UnionFind data structure.
    **/
    boolean[][] visited;
    public int numIslands(char[][] grid) {
        visited = new boolean[grid.length][grid[0].length];
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j]) {
                    if (grid[i][j] == '1') {
                        int islandArea = dfs(grid, i, j);
                        count++;
                    }
                    visited[i][j] = true;
                }
            }
        }
        return count;
    }
    public int dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        if (grid[i][j] == '1') {
            // try 4 directions
            return 1 + dfs(grid, i - 1, j) + dfs(grid, i + 1, j) +
                dfs(grid, i, j - 1) + dfs(grid, i, j + 1);
        } else {
            return 0;
        }
    }
}