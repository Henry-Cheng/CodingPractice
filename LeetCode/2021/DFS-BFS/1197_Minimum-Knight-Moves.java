// https://leetcode.com/problems/minimum-knight-moves/
class Solution { 
    public int minKnightMoves(int x, int y) {
        /**
        [] 2 []
      []   1   []
       2 1 0 1 2
      []   1   []
        [] 2 []
        **/
        int[][] directions = {
            {-1, 2},
            {-2,1},
            {1, 2},
            {2, 1},
            {2, -1},
            {1, -2},
            {-1, -2},
            {-2, -1}
        };
        
        Deque<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0});
        //HashSet<String> visited = new HashSet<>();
        //visited.add("0-0");
        boolean[][] visited = new boolean[605][605];
        visited[0+302][0+302] = true;
        int step = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                if (pos[0] == x && pos[1] == y) {
                    return step;
                }
                for(int[] direction: directions) {
                    int[] newPos = new int[2];
                    newPos[0] = pos[0] + direction[0];
                    newPos[1] = pos[1] + direction[1];
                    //String key = String.format("%s-%s", newPos[0], newPos[1]);
                    if (!visited[newPos[0] + 302][newPos[1] + 302]) {
                        visited[newPos[0] + 302][newPos[1] + 302] = true;
                        queue.offer(newPos);
                    }
                }
            }
            step++;
        }
        return step;
    }
}