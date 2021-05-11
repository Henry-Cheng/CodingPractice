# CodingPractice

Be careful on following details:

1. For BFS
  - remember to add entryPoint nodes to visited before traversing queue
  - remember to store queue size into int variable, instead of using `queue.size()` directly
  - remember to add node to visited when traversing the neightbors 
2. For DFS
  - remember to try all entryPoints using for loop, and then for each of them run DFS
  - remember to add all entryPoints to visited at begining
3. For Tree
  - remember to check if root.left and root.right is null before call dfs for root.left and root.right


Be careful on Java fundation:
1. Java uses unicode, in which each character would occupies 2 bytes, no matter it's English char or Chinese char
  - For some other encoding like GBK, the English char is 1 byte, while Chinese char is 2 bytes
2. `Arrays.copyOf(arr, arr.length)` is shallow copy!!!
  - have to copy the element one by one to implement deep copy


## Binary Search Tree

### Default

BST has its special use case, it must satisfy the following 3 conditions:  

1. no duplicate num allowed --> left subtree is strictly less then root, so is right subtree
2. `max value of left subtree` < root < `min value of right subtree`
3. left subtree is BST, and right subtree is BST

```java
    private BST isValidBSTHelper(TreeNode root) {
        if (root.left == null && root.right == null) {
            return new BST(true, root.val, root.val);
        }
        BST left = root.left != null ? isValidBSTHelper(root.left) : null;
        BST right = root.right != null ? isValidBSTHelper(root.right) : null;
        if (
            (left == null || (left.isBST && left.max < root.val)) 
            && 
            (right == null || (right.isBST && right.min > root.val))
           ) { // when left and right are both BST, and left max and right min are around root.val, it is a valid BST
            int min = left != null ? left.min : root.val;
            int max = right != null ? right.max : root.val;
            return new BST(true, min, max);
        } else {
            return new BST(false, root.val, root.val);
        }
    }

    private static class BST {
        public boolean isBST;
        public int min;
        public int max;
        public BST(boolean isBST, int min, int max) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    } 
```

#### [LC] 98. Validate Binary Search Tree
https://leetcode.com/problems/validate-binary-search-tree/

Using standard template to check.

#### [LC] 333. Largest BST Subtree
https://leetcode.com/problems/largest-bst-subtree/

Using standard template, and add one more field in `BST` to record the num of nodes if it is BST.

#### [LC] 173. Binary Search Tree Iterator
https://leetcode.com/problems/binary-search-tree-iterator/

Option1 is to flattern the tree into an arrayList, then move pointer to next. It makes constructor to be O(N) and all the others to be O(1).

Option2 is to using a stack to store all the left node to stack, and pop when next() is called.
This one makes all operations to be O(1) or amortized O(1).

NOTE: why next() is `amortized O(1)` ? --> next() involves two major operations  
1. one is where we pop an element from the stack which becomes the next smallest element to return. This is a O(1) operation. 
2. another one is that when node has right, we need to push all left to stack, this is clearly a linear time operation O(N) in the worst case. 
  - *However*, the important thing to note here is that we only make such a call for nodes which have a right child. Otherwise, we simply return. 
  - Also, even if we end up calling the helper function, it won't always process N nodes. They will be much lesser. 
  - Only if we have a skewed tree would there be N nodes for the root. But that is the only node for which we would call the helper function.
  - Thus, the amortized (average) time complexity for this function would still be `O(1)` which is what the question asks for. We don't need to have a solution which gives constant time operations for every call. We need that complexity on average and that is what we get.

```java
class BSTIterator {
    Deque<TreeNode> stack = null;
    public BSTIterator(TreeNode root) {
        stack = new LinkedList<>();
        TreeNode node = root; // push all left nodes to stack
        while(node != null) {
            stack.push(node);
            node = node.left;
        }
    }   
    public int next() {
        TreeNode next = stack.pop();
        if (next.right != null) { 
            TreeNode node = next.right; // push all left of next.right to stack
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return next.val;
    } 
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
```


#### [LC] 1586. Binary Search Tree Iterator II
https://leetcode.com/problems/binary-search-tree-iterator-ii/

Using the stack idea like `173. Binary Search Tree Iterator`, but also maintain `ArrayList alreadyTraversed` for `prev()`.

Let's look at the complexities one by one:  
- O(1) for the constructor()
- O(1) for hasPrev()
- O(1) for prev()
- O(1) for hasNext()
- Amortized O(1) but worst-case O(N) for next()
  - in the worst-case of the skewed tree one has to parse the entire tree, all N nodes. 
  - *However*, we only make such a call when we need to pop() from stack. If the next node is alerady in the arrayList, we can easily get it by array index. So it's amortized O(1)


#### [LC] 1382. Balance a Binary Search Tree
https://leetcode.com/problems/balance-a-binary-search-tree/

Convert the original BST into ArrayList by inOrder serialization, then do inOrder to deserialize into a tree.

## Recursion
### Default
https://www.jianshu.com/p/1395fae8a1ae
How to think about recursion algorithm?

1) Find termination condition  
e.g.  
 - for LC 24 "Swap Nodes in Pairs", the termination condition is when "there is no node to swap"
 - for LC 110 "Balanced Binary Tree", the termination condition is when "root is null so that we cannot see child tree", or we can say "root is null so that it is a balanced tree already"

2) Find return value  
e.g.  
- for LC 24 `Swap Nodes in Pairs`, the return value is a linked list that is already swapped.
- for LC 110 "Balanced Binary Tree", the return value needs to be both a boolean value isBalanced and an int value about the height, so that we can build a new class to handle it

3) What to do at current recursion level  
- for LC 24 "Swap Nodes in Pairs", the work at current level is to swap top two nodes
- for LC 110 "Balanced Binary Tree", the work at current level is to check left subtree and right subtree; if they are both balanced, check their height difference is less than 1, then the whole root tree is balanced


#### [LC] 17. Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/

Be careful when using `substring(startIndexInclusive, endIndexExclusive)`.
This can also be a backtracking problem which is faster.

#### [LC] 24. Swap Nodes in Pairs
https://leetcode.com/problems/swap-nodes-in-pairs/

Handle the first 2 nodes, call recursion function on 3rd ndoe, then point 1st node next to recursion result of 3rd node.  


#### [LC] 46. Permutations
https://leetcode.com/problems/permutations/

Explicitly define LinkedList instead of List, since generic List would loose the ability to `addFirst` and `addLast`. Just remember to add LinkedList back to list like
```
return result.stream().map(linkedList -> linkedList).collect(Collectors.toList());
```

List can do `remove(indexToRemove)` to remove element.

If you want to remove element of list iteratively, the traverse from end to start.

Be careful on Java pass-by-reference: when remove element from list, if the other rounds need to use the same list, you need to copy the list to new space and remove it there

#### [LC] 83. Remove Duplicates from Sorted List
https://leetcode.com/problems/remove-duplicates-from-sorted-list/



#### [LC] 206. Reverse Linked List
https://leetcode.com/problems/reverse-linked-list/

The magic part is:
```java
        ListNode node = reverseList(head.next);// now head.next is at the end
        head.next.next = head; // the magic part
        head.next = null;
```

#### [LC] 247. Strobogrammatic Number II
https://leetcode.com/problems/strobogrammatic-number-ii/

This question could also use DP, but DP is not space efficient since we don't need to keep the result from i to n -1.

There are two trick here: 
1. there are 2 initial states:
  - n == 0, result = {""}
  - n == 1, result = {"0", "1", "8"}
2. if n is even, it starts building from `n==0`, by adding "0"+num+"0", "1"+num+"1", "8"+num+"8", "6"+num+"9", "9"+num+"6"
3. if n is odd, it starts building from `n==1`
4. at each recursion, check "should we add 0 around the num"? --> only the top function does not add zero around, all the inner nums would need to add zero around

NOTE1:  
- `list = Arrays.asList("0", "1", "8")` is allowed
- `String[] arr = {"0", "1", "8"}; list = Arrays.asList(arr)` is allowed
- `int[] nums = new int[]{num1, num2}` is allowed
- `Arrays.asList({"0", "1", "8"})` is NOT allowed!!!!!!!

NOTE2:
- string plus is good enough!! do not always use stringBuilder
  - `String s = s1 + s2` would be translated inside JVM to be `String s = (new StringBuffer()).append(s1).append(s2).toString()`
- https://stackoverflow.com/questions/10078912/best-practices-performance-mixing-stringbuilder-append-with-string-concat


#### [LC] 1137. N-th Tribonacci Number
https://leetcode.com/problems/n-th-tribonacci-number/

Be careful to store result that has already been calculated to save effort on re-calculation.


#### [LC] 1849. Splitting a String Into Descending Consecutive Values
https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/

This is one the medium question in LeetCode weekly contest 239: https://leetcode.com/discuss/general-discussion/1186784/weekly-contest-239

The idea is simple: for each possible initial num (traversing from left to right), using recusion to check if num-1, num-2, num-3, ... exists in substring.  

The reason I fail in the contest is that I forgot to set the num to be `long` since it would overflow integer....

It is `O(N^2)`, since we can find up to `n-1` initial num, and for each num with `k` digits we check the next `n-k` num, which result in `(n-1) + (n-2) + (n-3) + ... + 1 = n^2`.




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

## Topological Sort

In a Directed Acyclic Graph (DAG), there must exist Topological Sort. 
 - 且若在有向图 G 中从顶点 u -> v有一条路径，则在拓扑排序中顶点 u 一定在顶点 v 之前，而因为在DAG图中没有环，所以按照DAG图的拓扑排序进行序列最短路径的更新，一定能求出最短路径。
 - if each vertex has weight, we can convert it into edge weight
 - then Topological Sort can find a shortest edge weighted path.

Topological Sort 也应用了BFS遍历思想、以达成按依赖关系排序的目的.

### Default
Psudocode:
```
1. prepare node to ingressCountMap, and node to egreeDependencyMap
2. traverse once for all possible nodes, and find 0-ingress node (the node that is not in ingressCountMap), record the node in a queue
3. while queue is not empty, poll each node, output the node, and check all dependencies of the node and prune the ingress of the dependency; if found new 0-ingress node, enqueue it
```

NOTE:
- Remember to use hashmap.getOrDefault() to cover possible not existing value in the map
- prepare egressMap from `from-node` to `list of to-node`
- preapare ingressMap from `node` to `ingress-count`
- maintain a queue to store all `0-ingress node` 
- maintain a result list to store final result in order

```java

// put ingressMap
Integer count = ingressMap.getOrDefault(to, 0);
ingressMap.put(to, count + 1);//0->1//1->0
// put egressMap
List<Integer> egressArcs = egressMap.getOrDefault(from, new LinkedList<>());
egressArcs.add(to);
egressMap.put(from, egressArcs);


while (!queue.isEmpty()) {
    Character c = queue.poll();
    result.add(c);
    for (Character to : egressArcs.get(c)) {
        ingressMap.put(to, ingressMap.get(next) - 1);
        if (ingressMap.get(to).equals(0)) {
            queue.add(to);
        }
    }
}
```

- the question does not require to return a "patial path", so either return full path or empty (if there is cycle inside)
- convert List<Integer> to int[] by `resultList.stream().mapToInt(i->i).toArray()`

#### [LC] 207. Course Schedule
https://leetcode.com/problems/course-schedule/

#### [LC] 210. Course Schedule II
https://leetcode.com/problems/course-schedule-ii/

#### [LC] 269. Alien Dictionary
https://leetcode.com/problems/alien-dictionary/

Convert the order between words into adjacencyList or egressMap.

#### [LC] 445. Add Two Numbers II
https://leetcode.com/problems/add-two-numbers-ii/

Use stack to reverse list and finally reverse it back.


## Sort
### Default

#### [LC] 49. Group Anagrams
https://leetcode.com/problems/group-anagrams/

bucket sort to find all anagrams

#### [LC] 973. K Closest Points to Origin
https://leetcode.com/problems/k-closest-points-to-origin/

sort or heap


## Interval
### Default

#### [LC] 56. Merge Intervals
https://leetcode.com/problems/merge-intervals/

Sort 2d array or array of object:
```java
Arrays.sort(arrays, (a, b) -> {
  return a[0] - b[0]; // ascending -- positive means b->a, negative means a->b
});
```

Sort objects:
```java
Collections.sort(posList, (a, b) -> {
  return a.index - b.index; // return positive means b->a, negateive means a->b
});
```

#### [LC] 252. Meeting Rooms
https://leetcode.com/problems/meeting-rooms/

Simple solution with Array.sort
```java
        Arrays.sort(intervals, (a, b)->{
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
``` 

#### [LintCode] 850. Employee Free Time

https://www.lintcode.com/problem/850/

Use custom comapritor, and be careful when union two intervals, set the end of the new interval to be the max one of original two intervals.


#### [LC] 986. Interval List Intersections
https://leetcode.com/problems/interval-list-intersections/

NOTE: 
- only need to compare p1 and p2, no need to compare with last element in result list --> they would never have overlap since we always move the pointer of smaller interval
- how to tell if they are intersected?
  - `int low = Math.max(firstList[p1][0], secondList[p2][0]);`
  - `int high = Math.min(firstList[p1][1], secondList[p2][1]);`
  - `if (low <= high) {return true;}`
- how to move the pointer of the smaller interval?
  - `if (firstList[p1][1] <= secondList[p2][1]) {p1++;}`


## LinkedList
### Default
#### [LC] 2. Add Two Numbers
https://leetcode.com/problems/add-two-numbers/

Be careful on the corner case, like carryOver may result in a new node.


#### [LC] 24. Swap Nodes in Pairs
https://leetcode.com/problems/swap-nodes-in-pairs/

Simple version is to swithc node value, harder version is to switch nodes, remember to add preHead node, super version is to do it recursively.


#### [LC] 21. Merge Two Sorted Lists
https://leetcode.com/problems/merge-two-sorted-lists/

Just find the head node first, then add node in l1 or l2 sequencially (compare their val) to the head.next.


#### [LC] 109. Convert Sorted List to Binary Search Tree
https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/solution/

#### [LC] 203. Remove Linked List Elements
https://leetcode.com/problems/remove-linked-list-elements/


## Trie/Prefix Tree/Digital Tree

Trie is used in following scenarios:
1. Autocomplete
2. Spell checker
3. IP routing
4. T9 keyboard predictive text
5. Sovling word games (like `212. word search II`)

The time complexity is `O(m)`, in which `m` means the length of the word.

Comparison with HashTable
- HashTable is not ideal when all searching keys have common prefix
- HashTable may have confliction, which also result in O(n)

### Default
https://en.wikipedia.org/wiki/Trie

[LC] 208. Implement Trie
[LC] 211. Design Add and Search Words Data Structure
[LC] 212. Word Search II
[LC] 425. Word Square
[LC] 642. Design Search Autocomplete System
[LC] 676. Implement Magic Dictionary
[LC] 745. Prefix and Suffix Search
[LC] 1032. Stream of Characters
[LC] 1233. Remove Sub-Folders from the Filesystem
[LC] 421. Maximum XOR of Two Numbers in an Array
[LC] 1707. Maximum XOR With an Element From Array

```java
// each TrieNode would have 26 child nodes, TrieNode itself is empty, it only makes sense when being used to check child links (check if links array has the index)
class TrieNode {

    // R links to node children
    private TrieNode[] links;

    private final int R = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}
```

```java
class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
           char curLetter = word.charAt(i);
           if (node.containsKey(curLetter)) {
               node = node.get(curLetter);
           } else {
               return null;
           }
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node != null && node.isEnd();
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
```

#### [LC] 211. Design Add and Search Words Data Structure
https://leetcode.com/problems/design-add-and-search-words-data-structure/

Since it supports "." to match any character, we can make search() a recursive function to try every trie.children when seeing "."

- Time complexity: `O(M)` for the "well-defined" words without dots, where M is the key length, and N is a number of keys, and `O(N*26 ^ M)` for the "undefined" words. 
- Space complexity: `O(1)` for the search of "well-defined" words without dots, and up to `O(M)` for the "undefined" words, to keep the recursion stack.

#### [LC] 212. Word Search II
https://leetcode.com/problems/word-search-ii/

NOTE:
1. Using hashmap to simplify the trie build
2. when building trie
  - don't forget to move node to its child for next letter in word
  - don't forget to set the word in `TrieNode` when find complete word
3. stll using the backtracking template: start with all entry points, using visited array to record visited node, remember to reset the visited array, remember to try all 4 directions
4. using hashset to maintain global solution, since there could be duplicate words
5. in backtracking method, do not return immediately after finding the word, since we could search further sometimes, e.g. `dict = {oa, oaa} `

```
    private static class Trie{
        public HashMap<Character,Trie> children = new HashMap<>();
        public String word = null;
        public Trie() {}
    }
```

#### [LC] 648. Replace Words
https://leetcode.com/problems/replace-words/

- use trie
- Time Complexity: O(N) where N is the length of the sentence. Every query of a word is in linear time.
- Space Complexity: O(N), the size of our trie.

## MiniMax
### Default

So far 5 questions on LC:

https://blog.csdn.net/weixin_30755709/article/details/98892479

https://www.liuin.cn/2018/06/30/LeetCode%E6%80%BB%E7%BB%93%E2%80%94%E2%80%94Minimax%E7%AE%97%E6%B3%95/

#### [LC] 486 Predict the Winner  
https://leetcode.com/problems/predict-the-winner/

If the current turn belongs to, say Player 1, we pick up an element, say xx, from either end, and give the turn to Player 2. Thus, if we obtain the score for the remaining elements(leaving xx), this score, now belongs to Player 2. Thus, since Player 2 is competing against Player 1, this score should be subtracted from Player 1's current(local) score(xx) to obtain the effective score of Player 1 at the current instant.

Similar argument holds true for Player 2's turn as well i.e. we can subtract Player 1's score for the remaining subarray from Player 2's current score to obtain its effective score. By making use of this observation, we can omit the use of turnturn from winner to find the required result by making the slight change discussed above in the winner's implementation.

While returning the result from winner for the current function call, we return the larger of the effective scores possible by choosing either the first or the last element from the currently available subarray. Rest of the process remains the same as the last approach.

Now, in order to remove the duplicate function calls, we can make use of a 2-D memoization array, memomemo, such that we can store the result obtained for the function call winner for a subarray with starting and ending indices being ss and ee ] at memo[s][e]memo[s][e]. This helps to prune the search space to a great extent.


```
    public boolean PredictTheWinner(int[] nums) {
        Integer[][] memory = new Integer[nums.length][nums.length];
        return getMaxScoreDiff(nums, 0, nums.length - 1, memory) >= 0;
    }
    
    // find max diff for (player1_score - player2_score)
    public int getMaxScoreDiff(int[] nums, int i, int j, Integer[][] memory) {
        if (memory[i][j] != null) {
            return memory[i][j];
        }
        if (i == j) {
            memory[i][j] = nums[i];
            return nums[i];
        }

        int maxScoreDiff = Math.max(
            nums[i] - getMaxScoreDiff(nums, i + 1, j, memory), 
            nums[j] - getMaxScoreDiff(nums, i, j - 1, memory)
        );
        memory[i][j] = maxScoreDiff;
        return maxScoreDiff;
    }
```




## Union-Find / Disjoint-Set
https://segmentfault.com/a/1190000022952886

### Default
Though most of UnionFind problem can be resolved by DFS, it is still good to remember this data structure, so that you don't need to convert each question into a graph problem.

NOTE:
- After all `union()` are called, we still need to call `find()` to traverse the DJS, since at the moment all `union()` are called, not every direct parent is the final parent.
- UnionFind would not maintain the same graph structure, it only maintains parent-to-child structure


Why not update rank when doing compression?
- rank is not the height of the tree, it's more like an upper bound (can be proved by `inverse Ackermann function`); the rank is allowed to get out of sync with the depth.
- https://stackoverflow.com/questions/25317156/why-path-compression-doesnt-change-rank-in-unionfind

What are the time complexity?
- after "path compression" and "merge by rank", disjoint-set can achieve `O(log* n)`
  - `log* n` is called [Iterated logarithm](https://en.wikipedia.org/wiki/Iterated_logarithm) which is much faster than general `O(logn)` and a bit slower than `O(1)`, usually we treat it as `amortized O(1)`
- another way to describe the time complexity is to call it `O(α(n))` where `α` points to [`inverse Ackermann function`](https://en.wikipedia.org/wiki/Disjoint-set_data_structure)
  - in this universe, `α(n) < 5 `, that is why `O(α(n))` almost equal to `O(1)`

Template:  
```java
class DJS { // disjoint-set
  int[] parent;
  int[] rank; // relative height of the parent node
  public int disjointCount = 0;
  public DJS(int size) {
      parent = new int[size];
      for (int i = 0; i < size; i++) {
          parent[i] = i; // initialize parent of each node to be itself
      }
      rank = new int[size]; // initialize rank array
      disjointCount = size; // initialize disjointCount to be size
  }

  public int find(int x) {
      if (x != parent[x]) {
          parent[x] = find(parent[x]); // path compression 
      }
      return parent[x];
  }
  
  public boolean union(int x, int y) { // NOTE: everything is done at parentX/parentY level
      int parentX = find(x);
      int parentY = find(y);
      if (parentX == parentY) { 
          return true; // already unioned
      } else {
          if (rank[parentX] < rank[parentY]) { // union x to y
              parent[parentX] = parentY;
          } else if (rank[parentX] > rank[parentY]) { // union y to x
              parent[parentY] = parentX;
          } else {
              parent[parentY] = parentX; // can choose either x or y here
              rank[parentX]++; // since we choose x as parent, increment x
          }
          disjointCount--;
          return false; // new union is built
      }
  }
```

Actually the template can also be implemented by HashMap like 
- `HashMap<String, String> parent`
- `HashMap<String, Integer> rank` 

As long as the key and value of `parent` is the same, we can even define a custom class `Node` to make it  
- `HashMap<Node, Node> parent`
- `HashMap<Node, Integer> rank`

```java
class DJS {
    public HashMap<String, String> parent = new HashMap<>();
    public HashMap<String, Integer> rank = new HashMap<>();
    public int disjointSize = 0;
    public DJS(List<List<String>> accounts) {
        for (List<String> account : accounts) {
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                parent.put(email, email); // initialize parent
                rank.put(email, 0); // initialize rank
            }
        }
        disjointSize = parent.size(); // initialize disjointSize
    }
    public String find(String x) {
        if (parent.get(x).equals(x)) {
            return x; // find parent email
        } else {
            parent.put(x, find(parent.get(x))); // compression
            return parent.get(x);
        }
    }
    public boolean union(String x, String y) {
        String parentX = find(x);
        String parentY = find(y);
        if (parentX.equals(parentY)) {
            return true; // already unioned
        } else {
            if (rank.get(parentX) < rank.get(parentY)) {
                parent.put(parentX, parentY);
            } else if (rank.get(parentX) > rank.get(parentY)) {
                parent.put(parentY, parentX);
            } else {
                parent.put(parentY, parentX);
                rank.put(parentX, rank.get(parentX) + 1);
            }
            return false;
        }
    }
}
```

#### [LC] 684. Redundant Connection
https://leetcode.com/problems/redundant-connection/

Be careful that in `union()` method, everythign is done at parentX/parentY level.

#### [LC] 721. Accounts Merge
https://leetcode.com/problems/accounts-merge/

Be careful that after all `union()` are called, we cannot directly use `parent[x]` since the parent may not be the finaly parent, we need to use `find(x)` to get parent when traversing.


## Shifted String & Anagrams
### Default
For this type of problem, we usually use hashmap to group string, and use an `encoding` function to identify `similar` strings.

#### [LC] 49. Group Anagrams
https://leetcode.com/problems/group-anagrams/

The encoding function is "char-count#". Using `int[] dict = new int[26]` since it could have duplicate char.

#### [LC] 249. Group Shifted Strings
https://leetcode.com/problems/group-shifted-strings/

The idea is to use HashMap key to maintain distance of characters in a string. But the tricky part is the the string could be rotated. So we need to `+ 26` is the next char is smaller than previous char:  

```java
    private int getDistance(char c1, char c2) {
        int num1 = c1 - 'a';
        int num2 = c2 - 'a';
        //  w    y    a   --> x    z    b
        // 23   25    1       24   26   2
        //    2    2             2    2
        if (num2 < num1) {
            return num2 - num1 + 26;
        } else {
            return num2 - num1;
        }
    }
```

#### [LC] 438. Find All Anagrams in a String
https://leetcode.com/problems/find-all-anagrams-in-a-string/

We can use the same "encoding funtion" idea to check if they are anagrams, and while moving from left to right, we can using "sliding window" so that we don't need to recalculate every char, then we can use this magic array equals function!!!!

```java
Arrays.equals(pCount, sCount); // time complexity is O(n), when n is 26, it's O(26)=O(1)
```  

#### [LC] 242. Valid Anagram
https://leetcode.com/problems/valid-anagram/

Similar like `438. Find All Anagrams in a String`, using `new int[26]` to build two array, and then using the `Arrays.equals()` to compare.

For the follow-up question about handling Unicode char, we can use HashMap to record char to couner mapping. NOTE that we only need 1 HashMap, and we can increase count by traversing one string and then decrease count when traversing anohter.

## Floyd's Algorithm
### Default

```java
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // phase 1: 
        // detects the cycle by finding intersection of fast and slow
        // fast pointer traverss 2x faster than slow pointer
        ListNode fast = head;
        ListNode slow = head;
        do {
            if (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            } else {
                return null;
            }
        } while(fast != slow);

        // if we hit here then there is a cycle, we can find cycle entrance in phase 2
        // phase2:
        // let slow pointer starts from beginning, fast pointer starts from intersection 
        // they traverse in same speed
        slow = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // or fast
    }
```

#### [LC] 141. Linked List Cycle
https://leetcode.com/problems/linked-list-cycle/

Using Floyd's algorithm template, since we only need to tell if there is a cycle or not, we can simply using phase 1 to tell it.

#### [LC] 142. Linked List Cycle II
https://leetcode.com/problems/linked-list-cycle-ii/

Using Floyd's algorithm template, using 2 phase to find the cycle entrance.

#### [LC] 287. Find the Duplicate Number
https://leetcode.com/problems/find-the-duplicate-number/

Also using the Floyd's algorithm, using fast pointer (or hare pointer) 2x faster like `hare = nums[nums[hare]]`, and slow pointer (or tortois pointer) in normal speed like `tortois = nums[tortois]`. Then using 2-phase to find the entrance of cycle.


## TreeMap

### Default

#### [LC] 239. Sliding Window Maximum
https://leetcode.com/problems/sliding-window-maximum/

Option1: TreeMap is `O((N-k+1)*logN)`

`TreeMap<Integer,HashSet<Integer>>` can be used as multiset in C++ to maintain order of elements and also the occurence (index of elements) as value.


Option2: Monotonic Queue is `amortized O(1)`

The algorithm is quite straigthforward, only maintains nums in the window in the queue, and whenever offering new num, remove all the nums less than it, since those deleted num would never has chance being used .

```
Window position              Monotonic Queue     Max
---------------                                 -----
[1] 3  -1 -3  5  3  6  7        [1]               -
[1  3] -1 -3  5  3  6  7        [3]               -
[1  3  -1] -3  5  3  6  7       [3 -1]            3
 1 [3  -1  -3] 5  3  6  7       [3 -1 -3]         3
 1  3 [-1  -3  5] 3  6  7       [5]               5
 1  3  -1 [-3  5  3] 6  7       [5 3]             5
 1  3  -1  -3 [5  3  6] 7       [6]               6
 1  3  -1  -3  5 [3  6  7]      [7]               7
```


