// https://leetcode.com/problems/01-matrix/submissions/
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length]; // by default is 0 for all of them, so no need to further initialize
        if (matrix == null) {
            return null;
        }
        // option1: treat 1 as entryPoint, using bfs/dfs to find all nearby 0, time complexity is O(MN * MN) since it could be full of 1 in matrix, and for each 1 we need to traverse whole matrix
        // option2: treat 0 as entryPoint, using bfs to find all nearby 1, traverse once for whole matrix and record minimum distance for that "1"-cell. It is O(MN)
        // bfs
        Deque<Pos> queue = new LinkedList<>(); // queue to record node
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        // traverse matrix to find all entry points, which is 0
        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new Pos(i,j));
                    visited[i][j] = true;
                }
            }
        }
        int level = 1;
        // now do bfs
        while(!queue.isEmpty()) {
            int nodeAtCurrentLevel = queue.size();
            for (int k = 0; k < nodeAtCurrentLevel; k++) {
                Pos pos = queue.poll();
                // check neighbors of pos, if found 1, add to the result array
                // check up
                if (pos.x - 1 >= 0 && !visited[pos.x - 1][pos.y]) {
                    result[pos.x - 1][pos.y] = level;
                    visited[pos.x - 1][pos.y] = true;
                    queue.offer(new Pos(pos.x - 1, pos.y)); // don't forget
                }
                // check down
                if (pos.x + 1 < matrix.length && !visited[pos.x + 1][pos.y]) {
                    result[pos.x + 1][pos.y] = level;
                    visited[pos.x + 1][pos.y] = true;
                    queue.offer(new Pos(pos.x + 1, pos.y)); // don't forget
                }
                // check left
                if (pos.y - 1 >= 0 && !visited[pos.x][pos.y - 1]) {
                    result[pos.x][pos.y - 1] = level;
                    visited[pos.x][pos.y - 1] = true;
                    queue.offer(new Pos(pos.x, pos.y-1)); // don't forget
                }
                // check right
                if (pos.y + 1 < matrix[0].length && !visited[pos.x][pos.y + 1]) {
                    result[pos.x][pos.y + 1] = level;
                    visited[pos.x][pos.y + 1] = true;
                    queue.offer(new Pos(pos.x, pos.y+1)); // don't forget
                }
            }
            level++;
        }
        return result;
    }
    
    private static class Pos {
        public int x;
        public int y;
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}