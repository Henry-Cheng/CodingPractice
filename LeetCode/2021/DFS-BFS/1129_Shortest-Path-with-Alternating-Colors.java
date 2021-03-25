// https://leetcode.com/problems/shortest-path-with-alternating-colors/submissions/
class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        /**
          n = 3
             0 1 2
           0   R
           1     R
           2
           
             0 1 2
           0   R
           1
           2   B
           
            0 1 2
          0   R
          1     B
          2
          
          0 -R-> 1 -R-> 2 -R-> 3
                   -R--------> 3
          0 ----B-----> 2
          
          for 0
          - check 1
          - check 2
          
          red_edges = [[0,1]]
        **/
        // dfs idea: start from 0, find all possible path to X, record it and compare
        // bfs idea: start from 0, find all 1-step neighbors, it helps find the shortest path faster
        // corner case
        int[] result = new int[n];
        if (n == 1) {
            result[0] = 0;
            return result;
        }
        Arrays.fill(result, -1); // initialize result array
        // build graph for future access
        boolean[][] redEdgesMatrix = new boolean[n][n];
        for (int i = 0; i < red_edges.length; i++) {
            redEdgesMatrix[red_edges[i][0]][red_edges[i][1]] = true;
        }
        boolean[][] blueEdgesMatrix = new boolean[n][n];
        for (int i = 0; i < blue_edges.length; i++) {
            blueEdgesMatrix[blue_edges[i][0]][blue_edges[i][1]] = true;
        }
        // run bfs
        Deque<Node> queue = new LinkedList<>();
        // enqueue entry point (the node 0 is special, it supports both R and B)
        queue.offer(new Node(0, "RB"));
        // set initial result
        result[0] = 0;
        // initialize level
        int level = 1;
        while(!queue.isEmpty()) {
            int nodeAtCurrentLevel = queue.size();
            for (int i = 0; i < nodeAtCurrentLevel; i++) {
                Node node = queue.poll();
                // visit node
                int from = node.value;
                String currentColor = node.color;
                for (int to = 0; to < n; to++) { // all entry points need to enqueue, including itself or some nodes that are already visited
                    if ((currentColor.equals("R") || currentColor.equals("RB")) && blueEdgesMatrix[from][to]) {
                        // update matrix
                        blueEdgesMatrix[from][to] = false;
                        // set result
                        if (result[to] == -1) {
                            result[to] = level;
                        } else {
                            result[to] = Math.min(result[to], level); 
                        }
                        // enqueue
                        queue.offer(new Node(to, "B"));
                    }
                    if ((currentColor.equals("B") || currentColor.equals("RB")) && redEdgesMatrix[from][to]) {
                        // update matrix
                        redEdgesMatrix[from][to] = false;
                        // set result
                        if (result[to] == -1) {
                            result[to] = level;
                        } else {
                            result[to] = Math.min(result[to], level); 
                        }
                        // enqueue
                        queue.offer(new Node(to, "R"));
                    }
                }
            }
            level++;
        }
        return result;
    }
    
    private static class Node {
        public int value;
        public String color;
        public Node(int value, String color) {
            this.value = value;
            this.color = color;
        }
    }
}