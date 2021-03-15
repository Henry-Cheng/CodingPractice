# CodingPractice

Classify Leetcode problems into patterns:

## Two Pointers
https://medium.com/outco/how-to-solve-sliding-window-problems-28d67601a66

### Fron/End Pointers

#### [LC] 11. Container With Most Water
https://leetcode.com/problems/container-with-most-water/

#### [LC] 15. 3Sum
https://leetcode.com/problems/3sum/

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

Goes through all the way from left to right to find min price at each day (store in new array), then goes through all the way from right to left to find max price at each day (store in new array), thne goes throw both new arrays together to get the max gap.

#### [LC] 344. Reverse String
https://leetcode.com/problems/reverse-string/

#### [LC] 345. Reverse Vowels of a String
https://leetcode.com/problems/reverse-vowels-of-a-string/

Be careful when using hashmap, only when map.get(key) != null means key exists.

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

#### [LC] 104. Maximum Depth of Binary Tree
https://leetcode.com/problems/maximum-depth-of-binary-tree/

#### [LC] 110. Balanced Binary Tree
https://leetcode.com/problems/balanced-binary-tree/


#### [LC] 144. Binary Tree Preorder Traversal
https://leetcode.com/problems/binary-tree-preorder-traversal/

#### [LC] 226. Invert Binary Tree
https://leetcode.com/problems/invert-binary-tree/


#### 236. Lowest Common Ancestor of a Binary Tree
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
- for LC 24 "
Swap Nodes in Pairs", the return value is a linked list that is already swapped.
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

## DFS
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
  - bottom-to-top DP need to have `optimial sub-structure` and `overlapping subproblem`
  - not all problem can be resolved by DP
    - e.g. N-Queens problem can be resolved by DP, though it has optimial sub-structure, but it has no `overlapping subproblem`, so with DP memory we cannot optimze anything. It's better to just use backtracking.


## DP
### Default
https://www.jianshu.com/p/4e4ad368ae15

Dynamic Programming (DP) has 2 implementations
- Top-to-bottom
  - the same as DFS with memory, which relies on revusion
  - Top-to-bottom DP is nothing else than ordinary recursion, enhanced with memorizing the solutions for intermediate sub-problems. When a given sub-problem arises second (third, fourth...) time, it is not solved from scratch, but instead the previously memorized solution is used right away. This technique is known under the name memoization (no 'r' before 'i').
  - e.g. Fibonacci. 
    - Just use the recursive formula for Fibonacci sequence, but build the table of fib(i) values along the way, and you get a Top-to-bottom DP algorithm for this problem (so that, for example, if you need to calculate fib(5) second time, you get it from the table instead of calculating it again).
- Bottom-to-top
  - it's an improvement on top-to-bottom to avoid stack overflow, which relies on iteration
  - Bottom-to-top DP is also based on storing sub-solutions in memory, but they are solved in a different order (from smaller to bigger), and the resultant general structure of the algorithm is not recursive. 
  - Bottom-to-top DP algorithms are usually more efficient, but they are generally harder (**and sometimes impossible**) to build, since it is not always easy to predict which primitive sub-problems you are going to need to solve the whole original problem, and which path you have to take from small sub-problems to get to the final solution in the most efficient way.
  - e.g. Longest common subsequence (LCS) problem is a classic Bottom-to-top DP example.





## Sort
### Default
#### [LintCode] 850. Employee Free Time

https://www.lintcode.com/problem/850/

Use custom comapritor, and be careful when union two intervals, set the end of the new interval to be the max one of original two intervals.

## HashMap
### Default
#### [LC] 1. Two Sum
Why "two sum" is not a two-pointers problem? To use two-pointers, we need to know when should we move the pointer, and for problem like "two sum", we cannot know it without sorting the array. But sorting would result in at least O(nlgn) time complexity. Considering by using hashmap we can already achieve O(n) time complexity, there is no need to use two-pointer here.


## LinkedList
### Default
#### [LC] 2. Add Two Numbers
https://leetcode.com/problems/add-two-numbers/

Be careful on the corner case, like carryOver may result in a new node.


#### [LC] 109. Convert Sorted List to Binary Search Tree
https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/solution/

#### [LC] 24. Swap Nodes in Pairs
https://leetcode.com/problems/swap-nodes-in-pairs/

Simple version is to swithc node value, harder version is to switch nodes, remember to add preHead node, super version is to do it recursively.


