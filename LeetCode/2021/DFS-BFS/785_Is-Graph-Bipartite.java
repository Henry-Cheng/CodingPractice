// https://leetcode.com/problems/is-graph-bipartite/
class Solution {
    public boolean isBipartite(int[][] graph) {
        /**
        0 --> 1 3
        1 --> 0 2
        2 --> 1 3
        3 --> 0 2
        **/
        // remove the disconnected nodes and only 1-edge node
        HashSet<Integer> left = new HashSet<>();
        HashSet<Integer> right = new HashSet<>();
        boolean[][] visited = new boolean[100][100];
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].length > 0) {
                left.add(i);
                boolean result = dfs(graph, i, true, left, right, visited);
                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean dfs(int[][] graph, int from, boolean fromIsLeft, HashSet<Integer> left, HashSet<Integer> right, boolean[][] visited) {
        boolean result = true;
        for(int to: graph[from]) {
            if (!visited[from][to] && !visited[to][from]) {
                visited[from][to] = true;
                visited[to][from] = true;
                //System.out.println(from + " is " + fromIsLeft + ", " + to + " is " + !fromIsLeft);
                //System.out.println("left is " + left );
                //System.out.println("right is " + right );
                if (fromIsLeft) {
                    if (left.contains(to)) {
                        return false;
                    } else {
                        right.add(to);
                        result = result && dfs(graph, to, false, left, right, visited);
                    }
                } else {
                    if (right.contains(to)) {
                        return false;
                    } else {
                        left.add(to);
                        result = result && dfs(graph, to, true, left, right, visited);
                    }  
                }
            }
        }
        return result;
    }
}