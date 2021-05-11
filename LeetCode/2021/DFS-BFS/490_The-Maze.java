// https://leetcode.com/problems/the-maze/
class Solution {
    
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visitedCorner = new boolean[maze.length][maze[0].length];
        return dfs(maze, start, destination, visitedCorner);
    }
    
    
    public boolean dfs(int[][] maze, int[] start, int[] destination, boolean[][] visitedCorner) {
        if (found(start, destination)) {
            return true;
        }
        if (visitedCorner[start[0]][start[1]]) {
            return false;
        }
        visitedCorner[start[0]][start[1]] = true;
     
        int originalRow = start[0];
        int originalCol = start[1];
        // try all 4 directions
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 2; j+=2) {
                do {
                    start[i] += j-1;
                } while(!hitWall(maze, start));
                // come back 1 step before hitting the wall
                start[i] -= j-1;
                
                if (dfs(maze, start, destination, visitedCorner)) {
                    return true;
                }
                // reset
                start[0] = originalRow;
                start[1] = originalCol;
            }
        }
        return false;
    }
    
    private boolean found(int[] start, int[] destination) {
        if (start[0] == destination[0] && start[1] == destination[1]) {
            return true;
        } else{
            return false;
        }
    }
    
    private boolean hitWall(int[][] maze, int[] start) {
        if (start[0] < 0 || start[0] > maze.length -1 || start[1] < 0 || start[1] > maze[0].length - 1 || maze[start[0]][start[1]] == 1) { // outbound or wall
            return true;
        }
        return false;
    }
}