// https://leetcode.com/problems/shortest-distance-from-all-buildings/
class Solution {
    public int shortestDistance(int[][] grid) {
        int[][][] oneToZeroDist = new int[grid.length][grid[0].length][2];
        int numOfBuilding = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    numOfBuilding++;
                    bfs(grid, new int[]{i, j}, oneToZeroDist);
                }
            }
        }
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < oneToZeroDist.length; i++) {
            for (int j = 0; j < oneToZeroDist[0].length; j++) {
                if (grid[i][j] == 0 && oneToZeroDist[i][j][1] == numOfBuilding) {
                    result = Math.min(result, oneToZeroDist[i][j][0]);
                }
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private void bfs(int[][] grid, int[] start, int[][][] oneToZeroDist) {
        //System.out.println("look for 1 which is at " + start[0] + " " + start[1]);
        Deque<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        queue.offer(start);
        visited[start[0]][start[1]] = true;
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            //System.out.println("found " + size + " neighbours");
            for(int index = 0; index < size; index++) {
                int[] pos = queue.poll();
                //System.out.println("dist to " + pos[0] + "," + pos[1] + " is " + level);
                oneToZeroDist[pos[0]][pos[1]][0] += level; // dist of this 0 to start which is 1
                oneToZeroDist[pos[0]][pos[1]][1]++; // reach one more building for this 0
                //System.out.println(pos[0] + "," + pos[1] + " has " + oneToZeroDist[pos[0]][pos[1]][1] + " times 1, and the length is " + oneToZeroDist[pos[0]][pos[1]][0]);
                // try all options
                for(int i = 0; i<=1; i++) {
                    for (int j = -1; j<=1; j+=2) {
                        pos[i] += j;
                        if (reachable(grid, pos) && !visited[pos[0]][pos[1]]) {
                            visited[pos[0]][pos[1]] = true;
                            queue.offer(new int[]{pos[0], pos[1]});
                        }
                        pos[i] -= j;
                    }
                }
            }
            level++;
        }
    }
    
    private boolean reachable(int[][] grid, int[] pos) {
        if (pos[0] < 0 || pos[0] >= grid.length || pos[1] < 0 || pos[1] >= grid[0].length || grid[pos[0]][pos[1]] == 1 || grid[pos[0]][pos[1]] == 2) {
            return false;
        }
        return true;
    }
}