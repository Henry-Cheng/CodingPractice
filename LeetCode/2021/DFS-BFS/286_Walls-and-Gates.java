// https://leetcode.com/problems/walls-and-gates/
class Solution {
    // start from all gates
    public void wallsAndGates(int[][] rooms) {
        Deque<int[]> queue = new LinkedList<>();
        for(int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j}); 
                }
            }
        }
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int index = 0; index < size; index++) {
                int[] pos = queue.poll();
                if (rooms[pos[0]][pos[1]] > 0 && rooms[pos[0]][pos[1]] == Integer.MAX_VALUE) { // this is a room and not visited
                    rooms[pos[0]][pos[1]] = level;
                }
                // try all directions
                for (int i = 0; i <=1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        pos[i] += j;
                        if (canReach(rooms, pos[0], pos[1]) && rooms[pos[0]][pos[1]] == Integer.MAX_VALUE) {
                            queue.offer(new int[]{pos[0], pos[1]});
                        }
                        pos[i] -= j;
                    }
                }
            }
            level++;
        }
    }
    
    private boolean canReach(int[][] rooms, int i, int j) {
        if (i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length || rooms[i][j] <= 0) {
            return false;
        }
        return true;
    }
    
    
    public void wallsAndGates_k_times(int[][] rooms) {
        int[][] gateToRoomDist = new int[rooms.length][rooms[0].length];
        for(int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                if (rooms[i][j] == 0) {
                    bfs_k_times(rooms, new int[]{i, j});
                }
            }
        }
    }
    
    private void bfs_k_times(int[][] rooms, int[] start) {
        Deque<int[]> queue = new LinkedList<>();
        queue.offer(start);
        boolean[][] visited = new boolean[rooms.length][rooms[0].length];
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int index = 0; index < size; index++) {
                int[] pos = queue.poll();
                if (rooms[pos[0]][pos[1]] > 0) { // this is a room
                    rooms[pos[0]][pos[1]] = Math.min(rooms[pos[0]][pos[1]], level);
                }
                // try all 4 directions
                for (int i = 0; i <=1; i++) {
                    for (int j = -1; j <=2; j+=2) {
                        pos[i] += j;
                        if(reachable(rooms, pos[0], pos[1]) && !visited[pos[0]][pos[1]]) {
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
    
    private boolean reachable(int[][] rooms, int i, int j) {
        if (i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length || rooms[i][j] <=0 ) {
            return false;
        }
        return true;
    }
}