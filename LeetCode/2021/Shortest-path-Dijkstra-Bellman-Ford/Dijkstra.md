## Shortest-path-Dijkstra-Bellman-Ford

- Topologic sort
  - 207. Course Schedule
- Dijkstra Algorithm
  - 743. Network Delay Time
  - 787. Cheapest Flights Within K Stops    
  - 1514. Path with Maximum Probability
  - 1786. Number of Restricted Paths From First to Last Node
- Floyd-Warshall Algorithm
  - 847. Shortest Path Visiting All Nodes
  - 旅行商问题
  - 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
- Bellman-Ford Algorithm
  - 787. Cheapest Flights Within K Stops

其中，Floyd 算法是多源最短路径，即求任意点到任意点到最短路径，
而 Dijkstra 算法和 Bellman-Ford 算法是单源最短路径，即单个点到任意点到最短路径。
这里因为起点只有一个K，所以使用单源最短路径就行了。

这三种算法还有一点不同，就是 Dijkstra算法处理有向权重图时，权重必须为正，而另外两种可以处理负权重有向图，但是不能出现负环，所谓负环，就是权重均为负的环。
为啥呢，这里要先引入松弛操作 Relaxtion，这是这三个算法的核心思想，当有对边 (u, v) 是结点u到结点v，如果 `dist(v) > dist(u) + w(u, v)`，那么 dist(v) 就可以被更新，这是所有这些的算法的核心操作。

1. Dijkstra算法和Bellman-Ford算法用于解决单源最短路径；
2. Floyd算法可以解决多源最短路径；
3. Dijkstra is good for dense graph (adjacency matrix)，因为稠密图问题与顶点关系密切；
  - Time: `O(V^2)` if using adjacency matrix--> `O(ElogE)` if using Heap/PriorityQueue
  - Space: `O(V + E)`
4. Bellman-Ford is good for sparse graph (adjacency table），因为稀疏图问题与边关系密切；
  - Time: `O(VE)`
  - Space: `O(V)`
5. Floyd-Warshall can do in both dense graph (adjacency matrix) and sparse graph (adjacency table) --> 杀鸡用牛刀。。。
  - Time: `O(V^3)`
  - Space: `O(V^2)`

e.g. `743. Network Delay Time`: https://www.youtube.com/watch?v=vwLYDeghs_c



### Single source shortest path
https://cloud.tencent.com/developer/article/1467091

- 拓扑排序+松弛（有向,无回路) -- 
  - `O(E+V)`
  - https://blog.csdn.net/caipeichao2/article/details/34919775?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-4.control&dist_request_id=1332042.22892.16193253459843403&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-4.control
- Bellman-Ford(可无向，可回路，可负权，但不能有负权回路)
  - `O(EV)` 
- Dijkstra(可无向，可回路， 不能有负权)
  - if using heap 
    - for sparse graph, `O(ElogE)`
    - for dense graph `E=V^2` would lead to `O(ElogV)` 
  - https://www.youtube.com/watch?v=vwLYDeghs_c



### Dijkstra and BFS
https://www.youtube.com/watch?v=9wV1VxlfBlI

Dijkstra and BFS are both for "shortest path problem"
- 在求无权图的问题中我们常常使用BFS来求其最短路径，而BFS无法解决网（有权图）中的问题，
- 我们解决网中的最短路径常常使用dijkstra算法来求解。

*换句话说，对于无权值图，dijkstra方法跟bfs是一致的*

dijkstra算法是一种贪心的思想, 只能求得特定节点到其他所有节点之间的最短路。即单源最短路问题.

- support both directed and undirected graph
- suporrted cycles in graph
- does not support negateive weight

Algorithm pseudocode:  
1. construct graph using hashmap or hashmap
  - if using hashmap, the space compelxity is `O(V+E)`
  - if using 2D array, the space compelxity is `O(V^2)` 
2. define `dist` array/hashmap to store dist from each node to source node
  - if using 1D array, remember to set the initiali value to be MAX
3. define `PriorityQueue` to replace genearl queue in BFS algorithm
  - the `PQ` should store both node info and the weight to reach the node
  - NOTE: it's the TOTAL weight from source node to this node!
4. define `visited` array or hashset to record visited nodes
  - NOTE: it could be avoided by using `dist` to check if it's visited
5. run BFS algorithm, and no need to keep layer information since PQ would reorder pending nodes, so that the level info is inaccurate
6. end the algorithm till `PQ` is empty

[Time complexity](https://leetcode.com/problems/network-delay-time/solution/):
- Time Complexity: `O(V^2 + E)` where E is the length of edges, `O(ElogE)` in the heap implementation since potentially every edge gets added to the heap.
  - can also be written as `O(ElogV)` or `O(V^2logV)`, since in worst case `E = V^2`
- Space Complexity: `O(N + E)`
  - the size of the graph `O(E)`, plus the size of the other objects used `O(N)`


```java
    public int networkDelayTime(int[][] times, int n, int k) {
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
```

### Bellman-Ford and DP
https://www.youtube.com/watch?v=2raV0H9KqY8

If we have E edges, initialliy set dist of each node to source node to be MAX_VALUE.  
Then for each edge `u->v`, do `relax` for all edges goes to `v` :
 - `if (dist[v] > dist[u] + weight_of_uv)`
   - `dist[v] = dist[u] + weight_of_uv`
 - since we need to do at most "V-1" times --> the time complexity is `O(EV)`

We don't need to build an adjacency matrix. This algorithm simply iterates over the edges of the graph and that information is already available in the input for the program. So we save on space there as opposed to other algorithms which we've seen.

```java

   1
 a --> b
2|     ^
 |     | -6
 V     |
 c --> d
    3

we have 4 edges: (d,b), (c,d), (a,c), (a,b)
We need to relax all 4 edges V-1=3 times blindly, so the time complexity is O(EV)

if we start from a, then do the following initialization:

now it is:  dist[a] = 0, dist[b] = ∞, dist[c] = ∞, dist[d] = ∞

1st time:
(d,b) -->   dist[a] = 0, dist[b] = ∞ - 6 = ∞, dist[c] = ∞, dist[d] = ∞
(c,d) -->   dist[a] = 0, dist[b] = ∞, dist[c] = ∞, dist[d] = ∞ + 3 = ∞
(a,c) -->   dist[a] = 0, dist[b] = ∞, dist[c] = 0 + 2 = 2, dist[d] = ∞
(a,b) -->   dist[a] = 0, dist[b] = 0 + 1 = 1, dist[c] = 2, dist[d] = ∞

now it is:  dist[a] = 0, dist[b] = 1, dist[c] = 2, dist[d] = ∞

2nd time:
(d,b) -->   (dist[d] - 6 = ∞) < (dist[b] = 1) not update b
(c,d) -->   (dist[c] + 3 = 5) < (dist[d] = ∞) update dist[d] = 5
(a,c) -->   no update to c
(a,b) -->   no update to b

now it is:  dist[a] = 0, dist[b] = 1, dist[c] = 2, dist[d] = 5

3rd time:
(d,b) -->   (dist[d] - 6 = -1) < (dist[b] = 1) not update dist[b] = -1
(c,d) -->   no update to d
(a,c) -->   no update to c
(a,b) -->   no update to b

now it is:  dist[a] = 0, dist[b] = -1, dist[c] = 2, dist[d] = 5

Now we have done V-1=3 relax, we should find the shortest path to each node from a.  
If we continue doing one more round, and find a shorter weight for a node, then it's negative cycle!!!

```


```java

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
```

### Floyd


#### [LC] 743. Network Delay Time
https://leetcode.com/problems/network-delay-time/

Using Dijkstra's algorithm template.

#### [LC] 505. The Maze II
https://leetcode.com/problems/the-maze-ii/

Using Dijkstra's algorithm.

Build the graph in this way: whenever ball reaches a corner, store the path as an edge and the length as weight.

Time complexity is `O(mnlog(mn))`, space complexity is `O(mn)`.

The reason is that Dijkstra's time complexity is `O(ElogE)`, for a `m*n` matrix and we can only move up/down/left/right, the max # of edges would be `4*m*n`, so it's `O(mnlog(mn))`.

#### [LC] 787. Cheapest Flights Within K Stops
https://leetcode.com/problems/cheapest-flights-within-k-stops/

This solution is really smart by using Bellman-Ford. 
Since by original Bellman-Ford, we may relax multiple times in one iteration, and we want to avoid it:
```java
e.g.
Graph: 1 -> 2 -> 3 -> 4
Edges: 1 -> 2; 2 -> 3; 3 -> 4

Then by 1st iteration we can relax 3 times.  
``` 
But in this question, we only want to relax one time by checking `1->2`, and not doing `2->3` since that since that would give us `1->2->3`.  

The trick is to use a backup array to store previous state of dist, then using the backup data int the Math.min() comparison.  
In this way, after we checked `1->2` in dist[2], we will still ceheck `2->3`, but dist[3] would not use updated dist[2] but the backup[2] which is MAX_VALUE !!! 
so that `1->2` and `2->3` are distinct event!!!

```java
        for (int j = 1; j <= K + 1; j++) {// relax K+1 times
            int[] backup = Arrays.copyOf(dist, n+1);
            for (int[] flight : flights) {
                int from = flight[0], to = flight[1], weight = flight[2];
                if (backup[from] != Integer.MAX_VALUE) {
                    // we update dist[to], but using the last round result (which is backup[from]), since we need to make sure only relax once at one stop, otherwise we may relax multiple times at this round and goes above j stops 
                    dist[to] = Math.min(dist[to], backup[from] + weight);
                }
            }
        }
```


#### [LC] 847. Shortest Path Visiting All Nodes
https://leetcode.com/problems/shortest-path-visiting-all-nodes/


#### [LC] 1514. Path with Maximum Probability
https://leetcode.com/problems/path-with-maximum-probability/

#### [LC] 1786. Number of Restricted Paths From First to Last Node
https://leetcode.com/problems/number-of-restricted-paths-from-first-to-last-node/

This algorithm needs to run Dijkstra first to find a `int[] dist` that records shortest dist from each node to node `n`, then using DFS to find all paths that meet the requirement.

Two tricks here to pass TLE:
1. using memorization in DFS
2. re-constrcut the graph to be adjacency table instead of 2D matrix

Also be careful where to use the `modulo 10^9 + 7`
- the Dijkstra algorithm would not result in overflow 
  - reason is that the shortest path has maximum "n-1" edges,  `n <= 2 * 10^4` and `weighti <= 10^5`, so the max dist would be `2*10^9` which is less than Integer.MAX_VALUE
- the DFS may result in overflow
  - reason is that each path from 1 to n would have "n-1" edges, and # of edges could be 2 times of nodes, which means the combination could be "C 2n n" which is `n^n` that is easily overflow
  - `result = (result + dfs(...)) % 1000000007`