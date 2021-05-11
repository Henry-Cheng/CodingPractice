// https://leetcode.com/problems/network-delay-time/
class Solution {
    // Bellman-Ford 
    public int networkDelayTime(int[][] times, int n, int k) {
        // dist[i] means node i+1 
        int[] dist = new int[n]; 
        Arrays.fill(dist, Integer.MAX_VALUE); // initialize dist to be INF
        dist[k-1] = 0; // initialize node k to be dist 0
        
        // NOTE: n-1 relaxation is the outer loop!!
        for (int i = 1; i <= n -1; i++) { // relax n-1 times
            for (int[] edge : times) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];
                if (dist[from-1] == Integer.MAX_VALUE) {
                    continue;
                } else {
                    dist[to-1] = Math.min(dist[from-1] + weight, dist[to-1]);
                }
            }
        }

        int max = 0;
        for (int node : dist) {
            if (node == Integer.MAX_VALUE) {
                return -1;
            } else {
                max = Math.max(max, node);
            }
        }
        return max;
    }
    
    // Dijkstra's algorithm
    public int networkDelayTime_Dijkstra_heap_my_solution(int[][] times, int n, int k) {
        // 1. construct graph O(E+V)
        HashMap<Integer, List<int[]>> graph = new HashMap<>();
        for(int[] time : times) {
            int from = time[0];
            int to = time[1];
            int weight = time[2];
            List<int[]> neighbors = graph.getOrDefault(from, new LinkedList<>());
            neighbors.add(new int[]{to, weight});
            graph.put(from, neighbors);
        }
        //System.out.println(graph);
        // 2. define dist map (also used as visited map)
        HashMap<Integer, Integer> dist = new HashMap<>();
        // 3. define min heap since lower the weight higher the priority
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{return a[1]-b[1];});
        // 4. run BFS
        // 4.1 initialize pq
        pq.offer(new int[]{k, 0});
        
        while(!pq.isEmpty()) {
            int[] node = pq.poll();
            int name = node[0];
            int weight = node[1];
            if (!dist.containsKey(name)) { // skip if already visited
                dist.put(name, weight); 
            }
            
            // traverse all neighbors
            if (graph.containsKey(name)) {
                for (int[] neighbor : graph.get(name)) {
                    int neigName = neighbor[0];
                    int neiWeight = neighbor[1];
                    if (!dist.containsKey(neigName)) { // skip if already visited
                        pq.offer(new int[]{neigName, weight + neiWeight});
                    }
                }
            }

        }
        // 5. check if all nodes are visited
        if (dist.size() != n) {
            return -1;
        }
        // 6. return total sum of all shortest path
        int max = 0;
        for (int cost : dist.values()) {
            max = Math.max(max, cost);
        }
        return max;
    }
    
    public int networkDelayTime_Bellman_Ford(int[][] times, int n, int k) {
        int MAX_VAL = 6000 * 100;
        int[] dist = new int[n];
        Arrays.fill(dist, MAX_VAL);
        dist[k - 1] = 0;
        for (int i = 1; i < n; i++) {
            for (int[] time : times) {
                int u = time[0] - 1;
                int v = time[1] - 1;
                int w = time[2];
                dist[v] = Math.min(dist[v], dist[u] + w);
            }
        }
        int maxDist = Math.max(dist[0], dist[dist.length-1]);
        return maxDist == MAX_VAL ? -1 : maxDist;
    }
    
    // Dijkstra algorithm -- basic mode
    Map<Integer, Integer> dist;
    public int networkDelayTime_dijkstra_basic(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge: times) {
            if (!graph.containsKey(edge[0]))
                graph.put(edge[0], new ArrayList<int[]>());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        dist = new HashMap(); // initialize dist to be max
        for (int node = 1; node <= N; ++node)
            dist.put(node, Integer.MAX_VALUE);

        dist.put(K, 0);
        boolean[] seen = new boolean[N+1];

        while (true) {
            int candNode = -1;
            int candDist = Integer.MAX_VALUE;
            for (int i = 1; i <= N; ++i) {
                if (!seen[i] && dist.get(i) < candDist) {
                    candDist = dist.get(i);
                    candNode = i;
                }
            }

            if (candNode < 0) break;
            seen[candNode] = true;
            if (graph.containsKey(candNode))
                for (int[] info: graph.get(candNode))
                    dist.put(info[0],
                             Math.min(dist.get(info[0]), dist.get(candNode) + info[1]));
        }

        int ans = 0;
        for (int cand: dist.values()) {
            if (cand == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, cand);
        }
        return ans;
    }
    
    // LC Dijkstra algorithm -- Heap mode
    public int networkDelayTime_dijkstra_heap(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge: times) {
            if (!graph.containsKey(edge[0]))
                graph.put(edge[0], new ArrayList<int[]>());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>(
                (info1, info2) -> info1[0] - info2[0]); // min heap
        heap.offer(new int[]{0, K});

        Map<Integer, Integer> dist = new HashMap();

        while (!heap.isEmpty()) {
            int[] info = heap.poll();
            int d = info[0], node = info[1];
            if (dist.containsKey(node)) continue;
            dist.put(node, d);
            if (graph.containsKey(node))
                for (int[] edge: graph.get(node)) {
                    int nei = edge[0], d2 = edge[1];
                    if (!dist.containsKey(nei))
                        heap.offer(new int[]{d+d2, nei});
                }
        }

        if (dist.size() != N) return -1;
        int ans = 0;
        for (int cand: dist.values())
            ans = Math.max(ans, cand);
        return ans;
    }
}