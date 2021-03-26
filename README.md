# CodingPractice

Classify Leetcode problems into patterns:

## Two Pointers
https://medium.com/outco/how-to-solve-sliding-window-problems-28d67601a66

### Fron/End Pointers

#### [LC] 11. Container With Most Water
https://leetcode.com/problems/container-with-most-water/

#### [LC] 15. 3Sum
https://leetcode.com/problems/3sum/

sort first, then have O(N^2)

#### [LC] 16. 3Sum Closest
https://leetcode.com/problems/3sum-closest/  

Be careful on the conditions when should we move left pointer and when to move right pointer

#### [LC] 19. Remove Nth Node From End of List
https://leetcode.com/problems/remove-nth-node-from-end-of-list/

Be careful on corner case, when n equals to size of list, and when n is only 1

#### [LC] 42. Trapping Rain Water
https://leetcode.com/problems/trapping-rain-water/

Move both front and end pointers when the corresponding wall is lower.

#### [LC] 121. Best Time to Buy and Sell Stock
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

```
origin: 3 2 6 5 0 3
if we sell at this point, what would be the lowest buy price
left:   3 2 2 2 0 0
if we buy at this point, what would be the highest sell price
right:  6 6 6 5 3 3
```
Goes through all the way from left to right to find min price at each day (store in new array), then goes through all the way from right to left to find max price at each day (store in new array), then goes through both new arrays together to get the max gap.


#### [LC] 125. Valid Palindrome 
https://leetcode.com/problems/valid-palindrome/

Be careful that left or right could go beyond the boundary.

#### [LC] 209. Minimum Size Subarray Sum
https://leetcode.com/problems/minimum-size-subarray-sum/
All nums are positive.

```
        // 2,3,1,2,4,3 --> 7
        // 2 --> < target, move right
        // 2,3 --> < target, move right
        // 2,3,1 --> < target, move right
        // 2,3,1,2 --> record it, >= target, move left
        //   3,1,2 --> < target, move right
        //   3,1,2,4 --> record it, >= target, move left
        //     1,2,4 --> record it, >= target, move left
        //       2,4 --> < target, move right
        //       2,4,3 --> record it, >= target, move left
        //         4,3 --> record it, >= target, move left
        //           3 --> < target, at the end
```


#### [LC] 238. Product of Array Except Self    
https://leetcode.com/problems/product-of-array-except-self/solution/

- generate product from left to right left[i] is the product from 0 to left-1 (exlclude i), note that left[0] is 1
- generate product from right to left right[i] is the product from n-1 to i+1 (exlclude i), note that right[n-1] is 1
- now the result for i is `left[i]*right[i]`


#### [LC] 340. Longest Substring with At Most K Distinct Characters
https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

```
        // e c e b a --> k ==2
        // e --> 1 <= 2, record 1, expand
        // e c --> 2 <= 2, record 2, expand
        // e c e --> 2 <= 2, record 3, expand
        // e c e b --> 3 > 2, shrink
        //   c e b --> 3 > 2, shrink
        //     e b --> 2 <=2, record 2, expand
        //     e b a --> 3 >= 2 shrink
        //       b a --> 2 <= 2, record 2, end
```
NOTE:
- using HashSet instead of HashMap if no need to store values
- HashSet contains() has O(1) average time complexity, and O(lgn) worst time complexity, since HashSet is backed by HashMap
- after doing `right++` in while loop, right could be over index, be careful to check the range before using it

#### [LC] 344. Reverse String
https://leetcode.com/problems/reverse-string/

#### [LC] 345. Reverse Vowels of a String
https://leetcode.com/problems/reverse-vowels-of-a-string/

Be careful when using hashmap, only when map.get(key) != null means key exists.

#### [LC] 424. Longest Repeating Character Replacement
https://leetcode.com/problems/longest-repeating-character-replacement/

```

        it equals to: find longest substring that has only k different char
             e c e b a --> k ==2
             e         --> 0 <= 2, record 1, expand
             e c       --> 1 <= 2, record 2, expand
             e c e     --> 1 <= 2, record 3, expand
             e c e b   --> 2 <= 2, record 4, expand
             e c e b a --> 3 > 2, shrink
               c e b a --> 4 > 2, shrink
                 e b a --> 3 > 2, shrink
                   b a --> 2 <= 2 record 2, cannot expand, end

```
NOTE:
- no need to update `mostCountSoFar` when decrementing, the reason is that `mostCountSoFar` recrods the most char count in the history, unless we find a bigger window in the future and increase the `mostCountSoFar`, we cannot do better that current max result which is based on `mostCountSoFar`
- for two pointers template, remember to check right boundary after `right++`, and then increment countMap; but do the decrement before `left--`  

#### [LC] 438 Find All Anagrams in a String 
https://leetcode.com/problems/find-all-anagrams-in-a-string/solution/

- option1: hashmap + sliding window, put dict word into hashmap1, then move left and right pointers to find next dict.legnth substring, and increment/decrement count in hashmap2, compare hashmap1 and hashmap2
- option2: array + sliding window, since it only has lower case letter, hash map can be replaced by 26-length array

#### [LC] 560. Subarray Sum Equals K
https://leetcode.com/problems/subarray-sum-equals-k/

Be careful
- preSum length is nums length + 1
- initially map needs to map.put(k, 1) for preSum[0]
- if multiple occurence of num + k, count all of them
- check if preSum exists in map before adding `preSum[i] + k` to map!!


#### [LC] 581 Shortest Unsorted Continuous Subarray   
- option1 sort: sort in another array, then compare different num 
- option2 sliding window [LC solution](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/solution/zui-duan-wu-xu-lian-xu-zi-shu-zu-by-leetcode/): if we found such a subarray (i,j), then the correct position of the smallest num is i, the correct position of the largest num in array is j.
  - 因此，首先我们需要找到原数组在哪个位置开始不是升序的。我们从头开始遍历数组，一旦遇到降序的元素，我们记录最小元素为 min 。
  - 类似的，我们逆序扫描数组 nums，当数组出现升序的时候，我们记录最大元素为 max。
  - 然后，我们再次遍历 nums 数组并通过与其他元素进行比较，来找到 min 和 max 在原数组中的正确位置。
    - 我们只需要从头开始找到第一个大于 min 的元素，从尾开始找到第一个小于 max 的元素，它们之间就是最短无序子数组。

```
         1 3 7 6 5 10 2 9 7 8 
         
         left min --> start from 7 --> 2
         right max --> start from 9 --> 10
         left correct pos --> 3 > 6 --> 3
         right correct pos --> 8 < 10 --> 8
         need to replace from 3 to 8
```

#### [LC] 680. Valid Palindrome II
https://leetcode.com/problems/valid-palindrome-ii/

- use a global boolean variable to check whether the chance to skip has been used
- use recusion to do something like this when encountering 1st mismatch in two-pointer round.
```
return validPalindrome(s, left + 1, right) || validPalindrome(s, left, right-1);  
``` 

#### [LC] 862. Shortest Subarray with Sum at Least K
[https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/)

Nums could be positive or negative.

Solution in Chinese version: [https://github.com/Shellbye/Shellbye.github.io/issues/41](https://github.com/Shellbye/Shellbye.github.io/issues/41)


NOTE:
- cannot directly use two pointers after prefixSum array!!!
  - the reason is that the prefixSum array is not sorted, so when we reach to index i that is less than index i-1, the left pointer would stuck at index i wihtout moving any more! (see [https://www.taodudu.cc/news/show-2093248.html](https://www.taodudu.cc/news/show-2093248.html))
  - we have to delete such an element at index i, that is why we use Deque!!
- using long here since Integer.MAX_VALUE is 2.1*10^9, but A[i] * A.length < 5*10^9
- prefixSum[0] = 0; // set it to be 0, which means sum before index 0 is 0


## Tree
### Default
1. How to traverse tree
2. How to build tree
3. How to traverse tree with custom return value

#### 94. Binary Tree Inorder Traversal
https://leetcode.com/problems/binary-tree-inorder-traversal/

Remember:
Time complexity : O(n)  
The time complexity is O(n) because the recursive function is   
T(n) = 2 * T(n/2) + 1

Space complexity : 
The worst case space required is O(n), and in the average case it's O(logn) where n is number of nodes.

NOTE: can also use stack to do the inOrder traverse

#### [LC] 101. Symmetric Tree
https://leetcode.com/problems/symmetric-tree/

What makes two tree symmetric? root1 and root2 are the same, root1.left equals to root2.right.
Can create a helper function using two of the same root at beginning.

#### [LC] 102. Binary Tree Level Order Traversal
https://leetcode.com/problems/binary-tree-level-order-traversal/

Standard BFS template.

#### [LC] 104. Maximum Depth of Binary Tree
https://leetcode.com/problems/maximum-depth-of-binary-tree/

Using recursion or BFS template.

Recursin
```
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
```

#### [LC] 110. Balanced Binary Tree
https://leetcode.com/problems/balanced-binary-tree/

#### [LC] 112. Path Sum
https://leetcode.com/problems/path-sum/

When using recusion, be careful to check both `root == null` and `root.left == null && root.right == null`.

When using iterative, prepare another stack to remember the current sum.

#### [LC] 144. Binary Tree Preorder Traversal
https://leetcode.com/problems/binary-tree-preorder-traversal/

#### [LC] 199. Binary Tree Right Side View
https://leetcode.com/problems/binary-tree-right-side-view/

BFS, then output the rightmost node at each layer.

#### [LC] 226. Invert Binary Tree
https://leetcode.com/problems/invert-binary-tree/


#### [LC] 236. Lowest Common Ancestor of a Binary Tree
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

The trick is: if root is p or q, return root; if left or right is p or q, return left or right. The idea is that left or right can represent the top node of a subtree that contains p or q.

algorithm

```
if the root is p or q, just return root; 
If root is not p or q, check if left or right exsits p or q (check if it is null);
If left and right both exists, then current root is LCA, return current root;
If left or right exists, return left or right;
```

NOTE: the time complexity is O(n) since it traverse all nodes, but its space complexity is also O(n), since even though there is no heap space, the stack space would be O(n) if it is a skewed tree (like the height of the tree equals to number of nodes).

#### [LC] 314. Binary Tree Vertical Order Traversal
https://leetcode.com/problems/binary-tree-vertical-order-traversal/

NOTE: the left subtree could grow into right-hand-side

Define a new TreeNode to record coordinate x and y, then using TreeMap to sort the x coordinate:

```
TreeMap<Integer, List<Integer>> orderedMap = new TreeMap<>((a,b) -> {return a-b;});
```

#### [LC] 515. Find Largest Value in Each Tree Row
https://leetcode.com/problems/find-largest-value-in-each-tree-row/

Be careful when using ++variable, it would addup the variable itself! It's better to use variable + 1 most of the time;


#### [LC] 617. Merge Two Binary Trees
https://leetcode.com/problems/merge-two-binary-trees/

#### [LC] 654. Maximum Binary Tree
https://leetcode.com/problems/maximum-binary-tree/

NOTE:
1. Be careful when talking about complexity:
 - time complexity: average is O(nlogn), worst case is O(n^2)
  - if we count the number of `constructor` called. On average perspective there would be logn levels, but for skewed list it would be n^2
  - if we count the operation of `comparison`, it would still be O(n^2), since we do the `findMaxNum` n(n+1)/2 = n^2 times
 - space complexity: for stack space, average is O(logn), worst case is O(n) for skewed list
2. Maybe no need to use a custom class as Result in `findMaxNum`, the `maxPos` could reflect `maxValue`

#### [LC] 987. Vertical Order Traversal of a Binary Tree
https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/

Sort the node by `x coordinate, y coordinate, val` using BFS + TreeMap + Collections.sort().

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
```
        ListNode node = reverseList(head.next);// now head.next is at the end
        head.next.next = head; // the magic part
        head.next = null;
```

#### [LC] 1137. N-th Tribonacci Number
https://leetcode.com/problems/n-th-tribonacci-number/

Be careful to store result that has already been calculated to save effort on re-calculation.

## Backtracking
### Default

Difference between Recursion and Backtracking:
- In recursion, the function calls itself, until it reaches a `base case`. 
- In backtracking, we use recursion to explore all the possibilities, until we get the best result for the problem
  - usually used for bi-directional-array-like problem:
    - the final solution consists of multiple `spots`
      - for each spot, there would be multiple `options` in searching space
      - each `option` can be selected when it meets `searching conditions`
        - e.g. left parentheses is less than total available parentheses
        - e.g. the option is not visited before
    - after one `option` is executed, call recusive function (with void return type, and updated `search conditions`) to move to next `spot`
      - it is not required, but try pruning it first, maybe no need recusive move to next `spot`
    - need to remove the effect of current `option` in `currentSolution` and `searching conditions` before moving to next `option`
    - there is a termination condition when a solution meet target
      - e.g. path length equals to expected string length

Algorithm Pseudocode (all the keywords below are important when thinking about algorihtm): 
- maintain a recursive function with void return type
  - recursive function has at least has 3 params to help us move to next `spot`
  - `currentSolution` that stores current latest solution
    - `currentSolution` is mutable, since we need to prune dead or redundant branches or current `option` in search space 
  - `status` that tells where we are now (this can be combined with `currentSolution`, like to use length of `currentSolution` to tell whiech `spot` we are working on)
    - e.g. it could be the index in a map-like search space
  - `search conditions` that tells the available and valid `options` for this `spot`
    - e.g. it could be a visited array
    - e.g. it could be the number of left parantheses in current status 
  - inside recursive funtion
    - 1. check if `currentSolution` meet target
      - the termination conditions are somthing like "path lenght meet target length", "found exit of maze", etc.
      - if meet, do 2 things:
        - 1) add `currentSolution` to global `globalSolution` 
          - there will be a global variable `globalSolution` to record all possible solutions 
        - 2) return
      - if not meet, do nothing, let it go to next line of code
    - 2. for each `spot`, there should be multiple `options`
   	  - for each `option` (could be a iterative for loop)
   	    - process the option by mutating `currentSolution`
   	    - then call recusive function to move to `next spot` with
          - updated `status` (or tell what new `spot` we will move to by length of  `currentSolution`)
          - update `searching condition`
          - prune it if possible
            - e.g. if the sum of subarray is already larger than target, no need to do this recursion again
   	    - before trying another `option`, do 
          - update the `currentSolution` to remove effect of current `option`
          - update the `search condition` to remove effect of current `option`
        - try next option

Example:
1. options
 － for "22. Generate Parentheses", options are "(" or ")"
2. restraints 
 - for "22. Generate Parentheses", when "(" is less than allowed num, can try "("; when "(" is more than ")", can try ")" 
3. termination condition
 - for "22. Generate Parentheses", when # of parenthese reaches 2 * n

#### [LC] 17. Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/

It can also be done by recursion + hashmap. But backtracking is faster than pure recursion.
Remember to use `StringBuilder` to remove character by `currentSolution.deleteCharAt(currentSolution.length() - 1);`.

#### [LC] 37. Sudoku Solver
https://leetcode.com/problems/sudoku-solver/

Be careful the rowNums, colNums, sectionNums need to be intialized no matter it has filled number or not.

For the output board (which is also input), we cannot reassign board, but can only replace board element in-place.

Convert int to char `char c = (char) (i + 48);`

To split num by digits
```
    public int getRow(int spot) {
        return (int)(spot / 10) - 1;
    }
            
    public int getCol(int spot) {
        return spot % 10 - 1;
    }
```

#### [LC] 46. Permutations
https://leetcode.com/problems/permutations/

- think about the problem in this way:
- there are n spots for the final result:
  - e.g. n == 3, spots are: [] [] []
- for each spot, there could be options 1, 2, 3
- after we fix one option in one spot, we move to next spot
- and next spot would have a new "search conditions" that the previously visited option cannot be reused
- after all "avaialble and valid options" are exausted, remove current option and reset "search conditions" 

#### [LC] 51. N-Queens
https://leetcode.com/problems/n-queens/

Be careful that string is immutable, using StringBuilder to make it mutable.

When checking the diagonal, it has 4 scenarios `row--, col--`, `row++,col++`, `row--,col++`, `row++,col--`.

#### [LC] 282. Expression Add Operators
https://leetcode.com/problems/expression-add-operators/

backtracking for all possibilities.

#### 399. Evaluate Division
https://leetcode.com/problems/evaluate-division/

Convert it to be a problem like this:  
- given two nodes, we are asked to check if there exists a path between them. If so, we should return the cumulative products along the path as the result

NOTE:
- we can use nested map to represent the graph, intead of insisting using 2-directional array

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
```
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
1. dfs with `visited` -- when it's explicit tree/graph style, no need to `backtrack` (e.g. `133. clone graph`)
```
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
2. dfs with `globalSolution` + `path` -- when it's implicit tree/graph style, need to `backtrack`, aka reset current step effect. e.g. `17. Letter Combinations of a Phone Number` or `51. N-Queens`
```
    private globalSolution;
    public vod backtrack(path, status, searchingSpace) {
        if (path meets terminationCondition) {
            globalSolution.add(path);
            return;
        }
        for (option : searchingSpace) {
            // process current option
            path = process(option); // update current solution
            status = update(status); // move to next spot
            searchingSpace = update(searchingSpace); // update searching conditions
            // backtrack
            backtrack(path, status, searchingSpace);
            // revoke when trying next option
            path = revoke(path);
            status = revoke(status);
            searchingSpace =revoke(searchingSpace);
        }
    }
    return globalSolution;
```
3. dfs with `return value` + `visited` -- it is used when matrix represents node itself (e.g. `200. Number of Islands`)
4. dfs with `visited` + `path` (very similar like #1) -- it is used when matrix represents the relationship of nodes instead of nodes themselves (e.g. `547. Number of Provinces`)


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

#### [LC] 1129. Shortest Path with Alternating Colors
https://leetcode.com/problems/shortest-path-with-alternating-colors/

- using BFS, one round `O(M*N)`
- prepare redEdges matrix and blueEdge matrix at beginning
- define a new class for queue node to record both node itself and current color
- be careful node 0 is special, whose current color can be both "R" and "B"
- when checking neighbors, need to enqueue all possible neigbors, including node itself (self-cycle) and visited node (the node could have edge for another color)


## DP
### Default
https://blog.csdn.net/weixin_26723981/article/details/108892305

https://www.jianshu.com/p/4e4ad368ae15

Dynamic Programming (DP) has 2 implementations
- Top-down
  - the same as DFS with `memorization`, which relies on revusion
  - Top-down DP is nothing else than ordinary recursion, enhanced with memorizing the solutions for intermediate sub-problems. When a given sub-problem arises second (third, fourth...) time, it is not solved from scratch, but instead the previously memorized solution is used right away. This technique is known under the name memoization (no 'r' before 'i').
  - e.g. Fibonacci. 
    - Just use the recursive formula for Fibonacci sequence, but build the table of fib(i) values along the way, and you get a Top-down DP algorithm for this problem (so that, for example, if you need to calculate fib(5) second time, you get it from the table instead of calculating it again).
- Bottom-top
  - it's an improvement on Top-down to avoid stack overflow, which relies on iteration and `tabulation`
  - Bottom-top DP is also based on storing sub-solutions in memory, but they are solved in a different order (from smaller to bigger), and the resultant general structure of the algorithm is not recursive. 
  - Bottom-top DP algorithms are usually more efficient, but they are generally harder (**and sometimes impossible**) to build, since it is not always easy to predict which primitive sub-problems you are going to need to solve the whole original problem, and which path you have to take from small sub-problems to get to the final solution in the most efficient way.
  - e.g. Longest common subsequence (LCS) problem is a classic Bottom-top DP example.

#### [LC] 39. Combination Sum
https://leetcode.com/problems/combination-sum/

```
    split target into an array
    dp[0, 1 , 2, 3, .., target - 1, target]
    dp[i] points to all the combination whose sum is i
    e.g.
    candidates = [2,3,5], target = 8
    now build dp array from 1 to 8
        dp[0] = [] // dummy head node
        dp[1] = [] // no combination
        dp[2] = [[2]] // (dp[1] + dp[1]), dp[2]
        dp[3] = [[3]] // (dp[1] + dp[2]), dp[3]
        dp[4] = [[2,2]] // (dp[1] + dp[3]), (dp[2] + dp[2]), dp[4]
        dp[5] = [[2,3],[5]] // dp[1] + dp[4], dp[2] + dp[3], dp[5]
        dp[6] = [[2,2,2],[3,3]] // 
        dp[7] = [[2,2,3],[2,5]] // dp[2] + dp[5], dp[3]+dp[4], has duplicate for [2,2,3]
        dp[8] = [[2,2,2,2],[2,3,3],[3,5]] // could have duplicate, dp[3]+dp[5] has duplicate with dp[2] + dp[6] for [2,3,3]
        for duplicate combination, we can use HashSet<List<Integer>> to cover it
```

#### [LC] 40. Combination Sum II
https://leetcode.com/problems/combination-sum-ii/

NOTE:
- using HashSet to dedupe List<Integer>
- convert hashset to list by `hashset.stream().collect(Collectors.toList())`

#### [LC] 53. Maximum Subarray
https://leetcode.com/problems/maximum-subarray/

[Kadane's algorithm](https://leetcode.com/problems/maximum-subarray/discuss/369797/kadanes-algorithm-with-detailed-explanation-and-example-python)

#### [LC] 62. Unique Paths
https://leetcode.com/problems/unique-paths/

- `T(m*n)`
- `S(m*n)`

#### [LC] 64. Minimum Path Sum
https://leetcode.com/problems/minimum-path-sum/

- backtracking/brute-force
  - T(2 ^ (m+n)) since for each move we have at most 2 options
  - S(m+n) since recusion depth is m + n
- top-down DP with memorization
  - T(mn) since each node would have recursive stack push
  - S(mn) since we use a dp matrix
- bottom-up DP with in-place matrix update
  - T(mn) since each node would be processed
  - S(1) since no extra space


Be careful to check boundary scenarios when `i == -1 && j == -1`.

#### [LC] 77. Combinations
https://leetcode.com/problems/combinations/

Pseudo code:
```
combo(candidates=1234, k = 3) = 1 * combo(candidates=234, k = 2) + combo (candidates=234, k = 3);
```
- be careful when using memory in recusion!! Need to do deep copy of list!!!
- you can use memory like this `List<List<Integer>>[][] memory = new LinkedList[n+2][k+2];`


For similar question like [39. Combination Sum](https://leetcode.com/problems/combination-sum/), we can have this pseudo code (each num can be reused):
```
(candidates=1234, sum=5) = 1 * (candiates=1234, sum=4) + (candidates=234, sum=5)
```

#### [LC] 96. Unique Binary Search Trees
https://leetcode.com/problems/unique-binary-search-trees/

#### [LC] 121. Best Time to Buy and Sell Stock
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

Also using Kadane's algorithm, by converting the prices array into gap array, and treate it as max subarray problem:
```
convert to Kadane's algorithm by calculating the gaps
prices:  7   1   5   3   6   4
  gaps:  -6  4   -2  3   -2

meaning: -6: buy at day 0 and sell at day 1
          4: buy at day 1 and sell at day 2
     -6 + 4: buy at day 0 and sell at day 2 (buy and sell same day would off-set)   
convert it to find max sum of subarray
``` 

#### [LC] 139. Word Break
https://leetcode.com/problems/word-break/

dp[i] = dp[j] && substring(j,i) in wordDict

- Time complexity : O(n^3). There are two nested loops, and substring computation at each iteration. Overall that results in O(n^3) time complexity.
- Space complexity : O(n). Length of dp array is n+1

#### [LC] 140. Word Break II
https://leetcode.com/problems/word-break-ii/

Using the same recursion way as 139, but add a beautiful step to append current found word to all of the previous found word list.

NOTE:
-  using hashmap computeIfAbsent: `allWordPos.computeIfAbsent(i, words-> new ArrayList<String>());`, if key `i` does not exist, put i and new object there

#### [LC] 174. Dungeon Game

https://leetcode.com/problems/dungeon-game/

The followings are copied from https://yutianx.info/2015/04/10/2015-04-10-leetcode-Dungeon-Game/

- dp[i][j]，那么它是从dp[i+1][j]或者dp[i][j+1]走过来的
- 需要将[i+1][j],[i][j+1]中选一个需要血量少的，减去dungeon[i][j]，再和1比较大小
- 我们需要从下标由大到小遍历数组

```
dp[i][j] = max(
  1, 
  min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]
  )
)
```

#### [LC] 300. Longest Increasing Subsequence
https://leetcode.com/problems/longest-increasing-subsequence/

The followings are copied from https://soulmachine.gitbooks.io/algorithm-essentials/content/java/dp/longest-increasing-subsequence.html

我们来尝试用DP来解决这题。最重要的是要定义出状态。首先从状态扩展这方面看，对于数组中的一个元素，它往后走，凡是比它大的元素，都可以作为下一步，因此这里找不到突破口。

我们换一个角度，从结果来入手，我们要求的最长递增子序列，一个递增子序列，肯定是有首尾两个端点的，假设我们定义 f[i] 为以第i个元素为起点的最长递增子序列，那么 f[i]和f[j]之间没有必然联系，这个状态不好用。

定义f[i]为以第i个元素为终点的最长递增子序列，那么如果`i<j`且`nums[i]<nums[j]`，则f[i]一定是f[j]的前缀。这个状态能表示子问题之间的关系，可以接着深入下去。

现在正式开始定义，我们定义状态f[i]为第i个元素为终点的最长递增子序列的长度，那么状态转移方程是

`f[j] = max{f[i], 0 <= i < j && f[i] < f[j]} + 1`

时间复杂度O(n^2)，空间复杂度O(n)

NOTE: initialize all dp to be 1!!!!

```
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // this is important!!!
        int result = 1;
        for (int j = 1; j < nums.length; j++) {
            for (int i = 0; i < j; i++) {
                if (nums[i] < nums[j]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
            result = Math.max(result, dp[j]);
        }
        return result;
```


#### [LC] 403. Frog Jump
https://leetcode.com/problems/frog-jump/

Define a bi-dimensional boolean array to record on stone i whether by step j we can reach it.  
e.g.   
if we have 7 stones, then we can at most make 7 distance per one jump, so we can create a `7*7` array to record true/false we can reach it.

NOTE:  
- though there were 3-layer for loop, the time complexity is acutally O(n^2)

## Sort
### Default

#### [LC] 33. Search in Rotated Sorted Array
https://leetcode.com/problems/search-in-rotated-sorted-array/

recover the array to be ascending.

#### [LC] 34. Find First and Last Position of Element in Sorted Array
https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/

find leftmost matched
```
        while(left < right) {
            int mid = (left + right)/2; // try find mid close to left
            if (nums[mid] >= target) {
                right = mid; // find the leftMost
            } else {
                left = mid + 1;
            }
        }
```
find rightmost matched

```
        while(left < right) {
            int mid = (left + right + 1)/2; // try find mid close to right
            if (nums[mid] <= target) {
                left = mid; // find the rightmost
            } else {
                right = mid - 1;
            }
        }
```

#### [LC] 49. Group Anagrams
https://leetcode.com/problems/group-anagrams/

bucket sort to find all anagrams

#### [LC] 56. Merge Intervals
https://leetcode.com/problems/merge-intervals/

Sort arrays or bi-directional array:
```
Arrays.sort(arrays, (a, b) -> {
  return a[0] - b[0]; // ascending -- positive means b->a, negative means a->b
});
```

Sort objects:
```
Collections.sort(posList, (a, b) -> {
  return a.index - b.index; // return positive means b->a, negateive means a->b
});
```

#### [LC] 252. Meeting Rooms
https://leetcode.com/problems/meeting-rooms/

Simple solution with Array.sort
```
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

#### [LC] 973. K Closest Points to Origin
https://leetcode.com/problems/k-closest-points-to-origin/

sort or heap

## HashMap
### Default
#### [LC] 1. Two Sum
Why "two sum" is not a two-pointers problem? To use two-pointers, we need to know when should we move the pointer, and for problem like "two sum", we cannot know it without sorting the array. But sorting would result in at least O(nlgn) time complexity. Considering by using hashmap we can already achieve O(n) time complexity, there is no need to use two-pointer here.

#### [LC] 128. Longest Consecutive Sequence
https://leetcode.com/problems/longest-consecutive-sequence/

HashSet.

#### [LC] 387 First Unique Character in a String 
https://leetcode.com/problems/first-unique-character-in-a-string/

Easy question, 1st round to go through all characters and count each of them, 2nd round to find the 1st one with count 1;

#### [LC] 1606. Find Servers That Handled Most Number of Requests

https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests/

per https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests/discuss/1054308/Java-Easy-to-understand-solution-with-explanation, we can use `TreeSet` and `TreeMap`

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

## Topological Sort
### Default
Psudocode:
```
1. prepare node to ingressCountMap, and node to egreeDependencyMap
2. traverse once for all possible nodes, and find 0-ingress node (the node that is not in ingressCountMap), record the node in a queue
3. while queue is not empty, poll each node, output the node, and check all dependencies of the node and prune the ingress of the dependency; if found new 0-ingress node, enqueue it
```

NOTE:
- Remember to use hashmap.getOrDefault() to cover possible not existing value in the map, e.g.

```
// put ingressMap
Integer count = ingressMap.getOrDefault(to, 0);
ingressMap.put(to, count + 1);//0->1//1->0
// put egressMap
List<Integer> egressArcs = egressMap.getOrDefault(from, new LinkedList<>());
egressArcs.add(to);
```

- the question does not require to return a "patial path", so either return full path or empty (if there is cycle inside)
- convert List<Integer> to int[] by `resultList.stream().mapToInt(i->i).toArray()`

#### [LC] 207. Course Schedule
https://leetcode.com/problems/course-schedule/

#### [LC] 210. Course Schedule II
https://leetcode.com/problems/course-schedule-ii/

## String
### Default

#### [LC] 616. Add Bold Tag in String
https://leetcode.com/problems/add-bold-tag-in-string/

It is the same as   
[LC] 758. Bold Words in String  
https://leetcode.com/problems/bold-words-in-string/


It is a combination of problem "find matched substring" and problem "merge interval".
1. solution 1: find and store all label pairs and do interval merge
  1. define a new object Pos to store index and left-or-right flag
    1. NOTE: cannot use TreeMap since pairs could share index 
  2. find all pairs of labels by traversing the string -- O(MNK) where M is # of characters in string, N is # of dict words and K is average size of dict words
    1. can use `int foundIndex = s.indexOf(key, index);`, where index is the starting position in string
  3. sort the list of Pos object by index
  4. do interval merge by stack
  5. add label to string by using `stringbuilder.insert(offset, "<br>")`
2. solution 2: define a boolean array to record whether character is in dict
  1. use `s.substring(i, i + dictWord.length()).equals(dictWord)`

NOTE:  
1. The complexity of Java's implementation of indexOf is O(m*n) where n and m are the length of the search string and pattern respectively.
2. The insert operation on a StringBuffer is O(n). This is because it must shift up to n characters out of the way to make room for the element to be inserted.
3. A faster way to match substring is to use trie, or using KMP algorithm to reach O(n) (which is too hard to remember during interview).
  1. https://www.tutorialspoint.com/Knuth-Morris-Pratt-Algorithm#:~:text=Knuth%20Morris%20Pratt%20(KMP)%20is,KMP%20is%20O(n).


Way to compare object:
```
        Collections.sort(positions, new Comparator<Pos>() {
            @Override
            public int compare(Pos p1, Pos p2) {
                if (p1.index > p2.index) {
                    return 1; // p2 before p1
                } else if (p1.index == p2.index) {
                    if (p1.isLeft) {
                        return -1; // p1 before p2
                    } else {
                        return 1; // p2 before p1
                    }
                } else {
                    return -1; // p1 before p2
                }
            }
        });
```

## Stack
### Default
#### [LC] 20. Valid Parentheses

https://leetcode.com/problems/valid-parentheses/

#### [LC] 224. Basic Calculator

https://leetcode.com/problems/basic-calculator/

Similar to `227 Basic Calculator II`, but this time we have parentheses, be careful about `3-(7+1)`, there is `-` before `(7+1)`, and we cannot simply push `-7` to stack.

#### [LC] 227 Basic Calculator II  
https://leetcode.com/problems/basic-calculator-ii/

- traverse string from left to right, if number, add to currentNumber variable, if next is “+” or “-“, push to stack.
- , find next number, push to stack (if it is “-“ before it, push -1*num to stack).
- continue, find “*” or “/“, pop the previous number, calculate it, and push the calculated result into stack.
- finally clean up the stack by adding up all the rest number there

## Heap
### Default


#### [LC] 23. Merge k Sorted Lists
https://leetcode.com/problems/merge-k-sorted-lists/

heap

#### [LC] 253. Meeting Rooms II
https://leetcode.com/problems/meeting-rooms-ii/

Using `Arrays.sort()` to sort by start time, then use PriorityQueue to maintain earliest ending interval at top of heap, the final result is the intervals in the heap.

NOTE:
- `int[]` is also an generic type that can be used in PriorityQueue

```
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
    return a[1]-b[1]; // ascending order
});
```

#### [LC] 767. Reorganize String
https://leetcode.com/problems/reorganize-string/

```
        // aaaccc --> acacac
        // aaacccc --> cacacac
        // aaaccccc --> ""
        // find most common letter, then split it by rest of the letters
        // if difference >= 2, not possible
        
        // option1: count all unique letters (O(N)), sort them (O(MlogM) if M is the # of unique letters) , then asembling string, total time complexity is O(N + MlogM)
        // option2: count and sort all unique letters by TreeMap (O(NlogM)), then asembling string
        // option3: count all unique letters (O(N)), building max-heap to maintain the max at heap top (O(MlogM)); when asembling string, we will do N times pulling from heap, and each pulling takes O(logM) for the heap to reorganize, so the asembling time is O(NlogM).
        // option4: bucket sort, maintain a 500 list of characters
        // since we would have most have 26 characters, so the M could be fixed and at most 26, which makes all the options close to O(N)
```

NOTE:  
PriorityQueue can support `Map.Entry` like:
```
        PriorityQueue<Map.Entry<Character,Integer>> pq = new PriorityQueue<>((a,b) -> {
            return b.getValue() - a.getValue(); // descending
        });
```

Then traverse map like:
```
for (Map.Entry<Character, Integer> entry : map.entrySet()) {}
```

## Array
### Default
#### [LC] 41. First Missing Positive

- The trick is: the max possible "min positve num" is `nums.length + 1`.
- To achieve constant space, we can reuse existing array to change value in place

NOTE: be careful on the algorithm to swap nums in place, need to break when there are duplicate numbers

```
        //     i     0 1  2 3
        // expected  1 2  3 4
        // actual    3 4 -1 1
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0) {
                if (nums[i] - i == 1) { // already matched
                    break;
                } else { // swap
                    int tmp = nums[nums[i] - 1];
                    if (tmp == nums[i]) {
                        break; // same number, skip here in case infinite loop
                    }
                    nums[nums[i] - 1] = nums[i];
                    nums[i] = tmp;
                }
            }
        }
```

## Design
### Default
https://stackoverflow.com/questions/43145395/time-complexity-while-deleting-last-element-from-arraylist-and-linkedlist

| Operation                   | LinkedList  | AraryList  | 
| --------------------------- |:-----------:| ----------:|
| add(Integer obj)            | *O(1)*      | *O(1)*     | 
| add(int index, Integer obj) | O(N)        | O(N)       |
| contains(Integer obj)       | O(N)        | O(N)       |
| get(int index)              | O(N)        | *O(1)*     |
| set(int index, Integer obj) | O(N)        | *O(1)*     |
| remove(int index) -- last   | *O(1)*      | *O(1)*     |
| remove(int index) -- any    | O(N)        | O(N)       |
| remove(Integer obj)         | O(N)        | O(N)       |


#### [LC] 146. LRU Cache
https://leetcode.com/problems/lru-cache/

- option1: linkedhashmap 
- option2: custome class to define double-linked-list and hashmap


#### [LC] 295. Find Median from Data Stream
https://leetcode.com/problems/find-median-from-data-stream/

the followings come from: https://www.sohu.com/a/363477662_355142

让用堆替换朴素方法中的列表：

最小堆保存较大的一半元素，最小值位于根元素
最大堆保存较小的一半元素，最大值位于根元素
现在，可以把传入的整数与最小堆根元素进行比较，并加到对应的一半数列中。接下来，如果插入后两个堆大小差距大于1，可以为堆进行重新平衡，让差距最大等于1：

ifsize(minHeap)> size(maxHeap)+ 1:

移除minHeap根元素,插入maxHeap

ifsize(maxHeap)> size(minHeap)+ 1:

移除maxHeap根元素,插入minHeap

通过这种方法，如果得到的两个堆大小相等，可以中位数等于两个堆的根元素平均值。否则，元素多的那个堆，其根元素就是中位数。

#### [LC] 341. Flatten Nested List Iterator
https://leetcode.com/problems/flatten-nested-list-iterator/

- option1: using recursion
  - Constructor: O(N + L)
  - next(): O(1)
  - hasNext(): O(1)
  - Space complexity : O(N + D) where D is the nesting depth
```
    private void flattern(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return;
        }
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) {
                nums.add(ni.getInteger());
            } else {
                flattern(ni.getList());
            }
        }
    }
```

- option2: using 2 stack

#### [LC] 380. Insert Delete GetRandom O(1)
https://leetcode.com/problems/insert-delete-getrandom-o1/

NOTE:
- to achieve random index to val mapping in `O(1)`, we need `ArrayList`
- to achieve checking val existence in `O(1)`, we need `HashMap`
- to achieve delete arbitrary index from ArrayList, we need to swap val with last element in list, and then remove last element from list
- always update map when doing list operation (add/delete/set)

#### [LC] 381. Insert Delete GetRandom O(1) - Duplicates allowed
https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/

Similar like `380. Insert Delete GetRandom O(1)`, when deleting element, swap with end element to achieve `O(1)`

```
ArrayList<Integer> lst;
    HashMap<Integer, Set<Integer>> idx;
```

#### [LC] 528. Random Pick with Weight
https://leetcode.com/problems/random-pick-with-weight/

prefixSum + search (better to use binary search to improve searching complexity to logN)

```
    //        w =    1    2      3       4 
    //prefiSum  = 0  1    3      6       10
    //percentage= 0% 10%  30%    60%     100%
    // 100% --> random <= 10  --> 6 < random <= 10 --> 40% chance --> 4
    // 60%  --> random <= 6   --> 3 < random <= 6  --> 30% chance --> 3
    // 30%  --> random <= 3   --> 1 < random <= 3  --> 20% chance --> 2
    // 10%  --> random <= 1   --> 0 < random <= 1  --> 10% chance --> 1
```

NOTE:  
When using random function, need to have brackets for both `(int)` and `(Math.random() * (max - min + 1))`
```
    // both end inclusive
    protected int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
```

#### [LC] 588. Design In-Memory File System
https://leetcode.com/problems/design-in-memory-file-system/

- using TreeMap since the question wants to return file/directories in lexicographic order.
- be careful when using `s.slpit("/")`, the first element in the array would be empty string `""`, need to skip this one

#### [LC] 729 My Calendar I
https://leetcode.com/problems/my-calendar-i/

- Using TreeMap
  - key is start of interval, value is the end of interval
  - then check the ordered previous and next interval of the pending interval by `ceilingKey(K key)` and `floorKey(K key)`
- Time Complexity: O(NlogN),where N is the number of events booked. For each new event, we search that the event is legal in O(logN) time, then insert it in O(1) time.
- Space Complexity: O(N), the size of the data structures used.

#### [LC] 731. My Calendar II
https://leetcode.com/problems/my-calendar-ii/

- Using two treemap here, one to store general meetings, another one to store overalapped areas as new intervals
 
## Math
### Default

#### [LC] 172. Factorial Trailing Zeroes
https://leetcode.com/problems/factorial-trailing-zeroes/

```
    // option1: using BigInteger
    // option2: count factors of 5 and 2, then use min(# of 5, # of 2)
    // option3: only count 5, since 2 is always more than 5
    // option4: only count 5, and not traverse 1 to n, traverse at 5 interval
    // option5: count how many "n /= 5" exists, result in O(logn)
```

#### [LC] 311. Sparse Matrix Multiplication
https://leetcode.com/problems/sparse-matrix-multiplication/

Generally time complexity is `O(m*n*k)`, but the followup is to handle super large matrix.
- one improvement is to record all-0 rows and all-0 col so that we can skip them
- another way is to use Strassen algorithm, to use some "plus" to replace "multiple", since multiple is more expensive 
  - https://sites.google.com/a/chaoskey.com/algorithm/02/03

#### [LC] 537. Complex Number Multiplication
https://leetcode.com/problems/complex-number-multiplication/

- split by "+" or "i" using `String[] x = a.split("\\+|i");`
- split by space using `String[] x = a.split("\\s+");`

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

#### [LC] 781. Rabbits in Forest
https://leetcode.com/problems/rabbits-in-forest/

pending baby

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

```
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

```
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


## BitWise
### Default
#### 201. Bitwise AND of Numbers Range
https://leetcode.com/problems/bitwise-and-of-numbers-range/

- option1: stupid way: `Integer.toBinaryString(int i)`
- option2:

```
  public int rangeBitwiseAnd(int m, int n) {
    int shift = 0;
    // find the common 1-bits
    while (m != n) { // remove all different bits in m and n, until they are the same (so that 1 & 1 or  0 & 0 would have no change)
      m = m >>1; 
      n = n >>1;
      ++shift;
    }
    return m << shift; // now shift back
  }
```