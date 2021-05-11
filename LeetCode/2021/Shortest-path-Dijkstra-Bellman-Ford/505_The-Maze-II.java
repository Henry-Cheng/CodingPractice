// https://leetcode.com/problems/the-maze-ii/
class Solution {
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int[][] dist = new int[maze.length][maze[0].length];
        for (int[] arr : dist) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
            return a[2] - b[2]; // ascending, min heap
        });
        
        // row, col, weight
        pq.offer(new int[]{start[0], start[1], 0});
        while(!pq.isEmpty()) {
            int[] pos = pq.poll();
            // if not signed off
            if (dist[pos[0]][pos[1]] == Integer.MAX_VALUE) { 
                if (pos[0] == destination[0] && pos[1] == destination[1]) {
                    return pos[2];
                }
                dist[pos[0]][pos[1]] = pos[2]; // store in dist
                // try 4 directions
                int originalRow = pos[0];
                int originalCol = pos[1];
                for(int i = 0; i <=1; i++) {
                    for (int j = 0; j <= 2; j+=2) {
                        int count = 0;
                        while(!hitWall(pos, maze)) {
                            pos[i] += j-1;
                            count++;
                        }
                        pos[i] -= j-1; // step back 1 step;
                        count--; // step back 1 count
                        if (dist[pos[0]][pos[1]] == Integer.MAX_VALUE) {
                            // if not signed off
                            pq.offer(new int[]{pos[0], pos[1], pos[2] + count});
                        }
                        // reset
                        pos[0] = originalRow;
                        pos[1] = originalCol;
                    }
                }
            } 
        }
        return -1;
    }
    
    private boolean hitWall(int[] pos, int[][] maze) {
        if (pos[0] < 0 || pos[0] > maze.length - 1 || pos[1] < 0 || pos[1] > maze[0].length - 1 || maze[pos[0]][pos[1]] == 1) {
            return true;
        }
        return false;
    }
}