## DFS and BFS

### Default

Depth-First-Search (DFS) is a specific form of backtracking (backtracking is like a brute-force solution):
- when it is bactracking?
  - implicit tree
  - need to prune but not have to (e.g. when sun of subarray is larger than target, no need to move forward)
  - work with global variable
- when it is DFS?
  - explicit tree
  - no need to prune (tree structure already throws/prunes unacceptable cases)
  - can work with both global variable or local variable
- when it is Dynamic Programming (DP)?
  - top-to-bottom DP is like a backtracking/DFS with memory
  - bottom-to-top DP need to have `optimial substructure` and `overlapping subproblem`
  - not all problem can be resolved by DP
    - e.g. N-Queens problem can be resolved by DP, though it has optimial sub-structure, but it has no `overlapping subproblem`, so with DP memory we cannot optimze anything. It's better to just use backtracking.

NOTE:
- graph could have circles, we need to maintain a `visited` array/set when using BFS/DFS, but for tree, we can skip `visited`
- if the `node` is not an integer, we cannot build an `adajacency matrix` to access it by index, instead we can build an `adajacency map` by hashmap and `visited` variable by hashset
- `entryPoints` is important
  - no matter DFS or BFS, need to start with all `entrypoints` and then call dfs or bfs
- BFS
  - is good to look for shortest path (e.g. `1129. Shortest Path with Alternating Colors`), no matter it's directed or not, and there is no weight on edges
  - a graph could have multiple entryPoints, we need to enqueue all of them at beginning, e.g. `542. 01 Matrix`
  - a graph could have mutltiple entryPoints when looking at neighbors, need to enqueue all of them, e.g.  for `1129. Shortest Path with Alternating Colors`, a node itself could have self-circle, a visited node could have another color edege, we need to enqueue both of them when looking at neighbors
  - sometimes we need to define custome queue object to record more information, e.g. for `1129. Shortest Path with Alternating Colors`, we need to record color of the entryPoints in the queue.
  - for tree, no need to define `visited`, since there is no cycle
- DFS 
  - is good to look for a valid path and output the path (using "path" param)
  - we need to do DFS for all `entryPoints`, e.g. for `200. Number of Islands`, we start with all "1" in matrix and do dfs.

BFS template (T(N) where N is the # of nodes, S(D) while D is the tree diameter)

```java
    public voic BFS(Node node) {
        if (node == null) {
            return node;
        }

        // Put all the entryPoints to the queue!!
        Deque<Node> queue = new LinkedList<Node> ();
        queue.offer(node); // a graph could have multiple entryPoints

        // visit the node
        Set<Node> visited = new HashSet<>();
        visited.add(node); // initial visited node, record it!!!

        // Start BFS traversal
        int levelCount = 1;
        while (!queue.isEmpty()) {
            int totalInCurrentLevel = queue.size();
            for (int i = 0; i < totalInCurrentLevel; i++) { // only visite one level
                Node n = queue.poll();
                // do the processing here!!!!!
                // if found the node, we can break now, the node is found by levelCount steps
                for (Node neighbor: n.neighbors) { // all possible neighbors need to enqueue
                    if (!visited.contains(neighbor) && isValid(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor); // already pushed to queue, record visited!!!
                    }
                }
            }
            levelCount++; // increment the level count
        }
    }
```

DFS can have 3 types of templates:
1. dfs with `visited`
  - e.g. `133. clone graph`
    - when it's explicit tree/graph style, no need to `backtrack`

```java
    private Set<Node> visited = new HashSet<>();
    // for all entryPoints, do DFS
    public void DFS(Node node) {
        if (visited.contains(node)) {
            return;
        }
        // add visiting logic here!!!
        // visited node
        visited.add(node);
        // traverse all neightbors
        for (Node neighbor : node.neighbors) {
            DFS(neighbor);
        }
    }
```
2. dfs with `globalSolution` + `path` (optional: + `visited`)
  - when it's implicit tree/graph style, need to `backtrack`, aka reset current step effect, sometimes 
  - e.g. `17. Letter Combinations of a Phone Number`
  - e.g. `51. N-Queens`

```java
    private globalSolution;
    private Set<Node> visited = new HashSet<>();
    public vod backtrack(path, status, searchingSpace) {
        if (path meets terminationCondition) {
            globalSolution.add(path);
            return;
        }
        for (option : searchingSpace) { 
            if (visited(option)) { // skip visited option
              continue;
            }
            // process current option
            path = process(option); // update current solution
            status = update(status); // move to next spot
            searchingSpace = update(searchingSpace); // update searching conditions
            visited.add(option); // reset visited
            // backtrack
            backtrack(path, status, searchingSpace);
            // revoke when trying next option
            path = revoke(path);
            status = revoke(status);
            searchingSpace =revoke(searchingSpace);
            visited.remove(option); // reset visited
        }
    }
    return globalSolution;
```
3. dfs with `return value` + `visited`
  - e.g. `200. Number of Islands`
    - it is used when matrix represents node itself
4. dfs with `visited` + `path` (very similar like #1)
  - e.g. `547. Number of Provinces`
    - it is used when matrix represents the relationship of nodes instead of nodes themselves
5. dfs with `return value` + `memory`
  - e.g. `126. Word Ladder II`

```java
    private boolean dfs(minLevel, currentLevel, adjacenceMap, path, visited, memory) {
        if (path.size() == minLevel) { // termination condition
            if (beginWord.equals(endWord)) {
                result.add(new LinkedList<>(path));
                return true;
            }
            return false;
        }
        
        boolean foundByBeginWord = false;
        // try all options
        if (adjacenceMap.containsKey(beginWord)) {
            for (String nextWord : adjacenceMap.get(beginWord)) {
                
                if (!visited.contains(nextWord) && memory.getOrDefault(nextWord, true)) {
                    
                    // try nextWord
                    path.add(nextWord);
                    visited.add(nextWord);
                    
                    boolean foundPath = dfs(nextWord, endWord, minLevel, currentLevel+1, adjacenceMap, path, visited, memory);
                    
                    // put into memory
                    memory.put(nextWord, foundPath);
                    foundByBeginWord = foundByBeginWord | foundPath;
                    
                    // reset nextWord
                    path.remove(path.size() - 1);
                    visited.remove(nextWord);
                }
            }
        } 

        // put into memory
        memory.put(beginWord, foundByBeginWord);
        return foundByBeginWord;
    }
```


Another way to think about DFS:
1. Bottom-up
  1. Ask answer from subproblem, e.g. `104. Maximum Depth of Binary Tree`, `64. Minimum Path Sum`, we can throw the question to subtree
2. Top down
  1. use answer passed from parent node to make decision, e.g. `129. Sum Root to Leaf Numbers`


#### [LC] 79. Word Search
https://leetcode.com/problems/word-search/

- option1: DFS with a new visited array passed to DFS function every time
- option2: backtracking, similar like DFS, but using only one visited array and reset the `visited[i][j]` if not found for a character
- option3: trie

#### [LC] 126. Word Ladder II
https://leetcode.com/problems/word-ladder-ii/

This problem would easity got "Time Limit Exceeded", so simply using BFS to maintain shortest path is not doable, we need to do the following improvement:

1. using BFS to find relationship of each word to its next avaliable word list (`hashmap(string, list)`)
  - inside BFS, instead of using "*" pattern like "79. Word Search", we need to traverse all 26 letters for each char in a word, since that is faster when we have lots of words in dictionary
  - inside BFS, instead of using `hashset visited`, we need to use `HashMap<String, Integer> levelRecorder` to record the shortest level to reach a word
  - the output of BFS would be 2 things:  
    - `hashmap(string, list)` that maintains `word` to its next available words mapping
    - `minLevel` that tells the min length of the valid path
2. using DFS to print all path by the 2 output of BFS
  - but simply using DFS would fail the time limitation, we need to add memory into DFS to record whether the current word can reach target end word


BFS trick part to use `levelRecorder`:  

```java
 if (dict.contains(option)) {
        // record the shortest level
        Integer optionLevel = levelRecorder.get(option);
        if (optionLevel == null) { //1st time see the option, enqueue, add to levelRecorder
            queue.offer(option); // enque
            
            nextAvailable.add(option);
            levelRecorder.put(option, level + 1);
        } else { // seen option before, no need to enqueue
            if (optionLevel > level) { // find a shorter path to reach "option"
                nextAvailable.add(option);
                levelRecorder.put(option, level + 1);
            }
        }
  }
```
#### [LC] 127. Word Ladder
https://leetcode.com/problems/word-ladder/

Treat the wordList as a graph by converting it into a hashmap: `pattern (a*z) --> words (abz, acz, adz, ...)`. When selecting options, we can use the pattern to find limited number of options, then with BFS and visited hashset we can easily get the final `level` when reaching `endWord`.

The way to generate pattern is like this:  
```
String newWord = word.substring(0, i) + '*' + word.substring(i + 1, word.length());

```

NOTE:  
1. we don't need to check `if (i > 0)` when using `substring(0,i)`, the function has already make it edge-case-safe for us
2. an improved version of this question is bi-directional-BFS, starting from 2 directions at the same time 
3. Time Complexity: `O(M^2*N)`, where MM is the length of each word and NN is the total number of words in the input word list.
4. Space Complexity: `O(M^2*N)`

#### [LC] 133. Clone Graph
https://leetcode.com/problems/clone-graph/

Both DFS and BFS are T(M + N) and S(N), where N is the number of nodes and M is number of edges.

- BFS solution is to convert visited set to be a hashmap that stores original node to clone node mapping
  - be carefule hashmap uses `containsKey()` while hashset only uses `contains`


#### [LC] 200. Number of Islands
https://leetcode.com/problems/number-of-islands/

Traverse all grids, if found 1, trigger dfs to find all 1s -- could return sum of 1 in dfs.

Time complexity : O(M * N) where M is the number of rows and N is the number of columns.
Space complexity : worst case  O(M * N) in case that the grid map is filled with lands where DFS goes by M * N deep.

#### [LC] 286. Walls and Gates
https://leetcode.com/problems/walls-and-gates/

To find the min distance of all rooms to all gates.

This is a very smart quesiton.    
There is a trick here by using BFS and treate all gates together as entrypoints at beginning, then we can start searching from all gates to all rooms.

The time complexity is `O(m*n)` since we only need to traverse all room once (if a room is already marked by one gate, then it must already be the shortest path, we can ignore).


#### [LC] 317. Shortest Distance from All Buildings
https://leetcode.com/problems/shortest-distance-from-all-buildings/

Similar like `286. Walls and Gates`, we can start with all buildings at once with BFS, to find dist from buildings to each land.

It's a bit different here since we need to count the shortest path, so we need to sum up when multiple buildings reachse the same empty land.


#### [LC][Hard] 329. Longest Increasing Path in a Matrix
https://leetcode.com/problems/longest-increasing-path-in-a-matrix/

This problem is easy actually, no panic.  
We can use dfs to traverse each num in matrix, and using memory matrix to record "what is the distance if I start from this node", then if we reached a node that already has the memory, we can use the memory directly.  

Trick to travse up/down/left/right
```java
    private static int[][] DIRECTIONS = {
        {-1,0},{1,0},{0,-1},{0,1}
    };
    ...
    for (int i = 0; i < 4; i++) {
        int newRow = row + DIRECTIONS[i][0];
        int newCol = col + DIRECTIONS[i][1];
        ...
    }
``` 




#### [LC] 339. Nested List Weight Sum
https://leetcode.com/problems/nested-list-weight-sum/

This is a good question that shows a special use case of BFS!!!  
Since we use `int size = queue.size()` and `for (int i = 0; i < size; i++)` to traverse at each level, the `size` helps us to only access the node at this level, which means we can continue adding nodes to queue in the loop!!

The trick here is that
- at beginning we enqueue *all nodes* (not just the integer nodes!!!)
- then we use the `size` to control the "# of nodes at each level"
  - the "nodes at each level" are the nodes with `node.isInteger() == true`
  - if it is a list, we enqueue all elements in the list to the queue
    - it would not impact counting at each level!!!

#### [LC] 490. The Maze
https://leetcode.com/problems/the-maze/

Since the ball will always roll until hit the wall, so our visited array only records the position of "corners" where the ball stops.

how to try 4 directions?
```java
if (found(start, destination)) {
    return true;
}
if (visitedCorner[start[0]][start[1]]) {
    return false;
}
visitedCorner[start[0]][start[1]] = true;

int originalRow = start[0];
int originalCol = start[1];
for (int i = 0; i <= 1; i++) {
    for (int j = 0; j <= 2; j+=2) {
        do {
            start[i] += j-1;
        } while(!hitWall(maze, start));
        // come back 1 step before hitting the wall
        start[i] -= j-1;

        if (dfs(maze, start, destination, visitedCorner)) {
          return true;
        }
        // reset
        start[0] = originalRow;
        start[1] = originalCol;
    }
}
``` 


#### [LC] 542. 01 Matrix
https://leetcode.com/problems/01-matrix/

```
// option1: treat 1 as entryPoint, using bfs/dfs to find all nearby 0, time complexity is O(MN * MN) since it could be full of 1 in matrix, and for each 1 we need to traverse whole matrix
// option2: treat 0 as entryPoint, using bfs to find all nearby 1, traverse once for whole matrix and record minimum distance for that "1"-cell. It is O(MN)
```

#### [LC] 547. Number of Provinces
https://leetcode.com/problems/number-of-provinces/

For each node (0 ~ n-1), traverse from itself to all neighbor nodes, if found 1 then call dfs with the found node, maintain a hashset as path and passed to dfs function.

#### [LC] 695. Max Area of Island
https://leetcode.com/problems/max-area-of-island/

Same solutin as LC 200.

#### [LC] 752. Open the Lock
https://leetcode.com/problems/open-the-lock/

This is a question to find shortest path, which is perfect for BFS:
- we can think that each node would have 8 positions to move to (each slot we can move `+1` or `-1`)
  - be careful to check boundary case that `-1` should be `9`, while `10` should be `1`
- all the deadends are like obstacles in graph 
  - be careful that the initial node `0000` would also be in deadends

NOTE: StringBuilder could append integer!!!

#### [LC] 785. Is Graph Bipartite?
https://leetcode.com/problems/is-graph-bipartite/

Maintain 2 hashset left and right to record nodes in left and nodes in right.  
Then tranverse all edges by DFS.  
NOTE!!!
1. rememebr the DFS template, all nodes can be entry point!!!! we will use visited array to record visited edges
2. for this question, we can skip disconnected nodes whose edges is empty

#### [LC] 827. Making A Large Island
https://leetcode.com/problems/making-a-large-island/

1. for each 1 grid, using dfs to label all 1-grid and record the size in hashmap
2. for each 0 grid, check its neighbor to see if we can connect labels

#### [LC] 1129. Shortest Path with Alternating Colors
https://leetcode.com/problems/shortest-path-with-alternating-colors/

- using BFS, one round `O(M*N)`
- prepare redEdges matrix and blueEdge matrix at beginning
- define a new class for queue node to record both node itself and current color
- be careful node 0 is special, whose current color can be both "R" and "B"
- when checking neighbors, need to enqueue all possible neigbors, including node itself (self-cycle) and visited node (the node could have edge for another color)


#### [LC][Medium] 1197. Minimum Knight Moves
https://leetcode.com/problems/minimum-knight-moves/

This problem is simple by using BFS, but not easy to pass LTE since "HashSet + string key" is not as fast as 2D boolean array.  

To use 2D boolean array as `visited` variable, we can define the array to be 2 times of x and y, since the negative half would also need to be put in `visited`

```java
// since |x| + |y| <= 300, the negative par
boolean[][] visited = new boolean[605][605];
...

// when using visited, addup 302 here
if (!visited[newPos[0] + 302][newPos[1] + 302]) {
    visited[newPos[0] + 302][newPos[1] + 302] = true;
    queue.offer(newPos);
}

```

Some fast coding techniques to initialize 2D array:  

1. Initialize 2D array:
```java
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
```

2. Initialzie 1D array when new int[] arary:  
```java
queue.offer(new int[]{0,0});
```



