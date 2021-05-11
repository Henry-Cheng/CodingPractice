// https://leetcode.com/problems/number-of-restricted-paths-from-first-to-last-node/
class Solution {
    public int countRestrictedPaths(int n, int[][] edges) {
        // 0. construct graph to access graph faster, otherwise it's TLE
        List<int[]>[] graph = new List[n+1];
        for(int i=0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int[] edge: edges) {
            graph[edge[0]].add(new int[]{edge[1],edge[2]});
            graph[edge[1]].add(new int[]{edge[0],edge[2]});
        }
        
        // 1. Dijkstra algorithm once to find dist from node n to others
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1]; // ascending, min heap
        });
        pq.offer(new int[]{n, 0});
        while(!pq.isEmpty()) {
            int[] node = pq.poll();
            if (dist[node[0]] == Integer.MAX_VALUE) { // if not signed off
                //System.out.println(node[0] + " has weight " + node[1]);
                dist[node[0]] = node[1];
                // try all edges
                for (int[] edge : graph[node[0]]) {
                    if (dist[edge[0]] == Integer.MAX_VALUE) {
                        pq.offer(new int[]{edge[0], node[1] + edge[1]});
                    }
                }
            }
        }
        // for(int i = 1; i < dist.length; i++) {
        //     System.out.print(dist[i] + ", ");
        // }
        // System.out.println();
        // 2. run DFS to find path from 1 to n and the dist is descending
        boolean[] visited = new boolean[n+1];
        return dfs(1, n, graph, dist, visited, new Integer[n+1]);
    }
    
    private int dfs(int start, int n, List<int[]>[] graph, int[] dist, boolean[] visited, Integer[] memorization) {
        if (start == n) {
            return 1;
        }
        if (memorization[start] != null) {
            return memorization[start];
        }
        int result = 0;
        for(int[] edge : graph[start]) {
            if (!visited[edge[0]] && dist[start] > dist[edge[0]]) {
                visited[edge[0]] = true;
                result = (result + dfs(edge[0], n, graph, dist, visited, memorization)) % 1000000007;
                visited[edge[0]] = false;
            }
        }
        memorization[start] =  result;
        return result;
    }
}