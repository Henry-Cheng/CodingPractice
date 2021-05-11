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

#### [LC] 113. Path Sum II
https://leetcode.com/problems/path-sum-ii/

when using backtrack, remember to remove last element in path when return to previous stack.

#### [LC] 124. Binary Tree Maximum Path Sum
https://leetcode.com/problems/binary-tree-maximum-path-sum/

- using the same idea like kadane's algorithm
  - maintain a currentMaxSum, and then compare with current node, we can choose to only use current node (by throwing away currentMaxSum), or addup current node
- but this question is a bit different, not like kadane's algorithm which has only one direction, here we have two directions
- `currentMaxSum = max(root, root+left, root+right)` -- we can return it to parent stack in dfs 
- `totalMaxSum = max(totalMaxSum, currentMaxSum, root+left+right)` -- we need to check whether we can directly use `root+left+right`

#### [LC][Medium] 129. Sum Root to Leaf Numbers
https://leetcode.com/problems/sum-root-to-leaf-numbers/

This problem is simple.  
Doing preOrder traverse on this tree, and using the idea of `sum = prevSum*10 + root.val` to cumulate the result, and when we see leaf node, just addup the global variable.


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


#### [LC] 270. Closest Binary Search Tree Value
https://leetcode.com/problems/closest-binary-search-tree-value/


#### [LC] 297. Serialize and Deserialize Binary Tree
https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

- seralize with preOrder
  - using "," as delimiter and "#" for null node
- deserialize with queue to store splitted string by ","
  - be careful if the original root is null, array[0] is ""

```java
    public TreeNode deserialize(String data) {
        String[] array = data.split(",");
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            queue.offer((array[i].equals("#") || array[i].equals("")) ? null : Integer.valueOf(array[i])); // "" means the root is null
        }
        return preOrderDeserialize(queue);
    }
    
    private TreeNode preOrderDeserialize(Deque<Integer> queue) {
        Integer val = queue.poll();
        if (val == null) {
            return null;
        }
        TreeNode root = new TreeNode(val);
        root.left = preOrderDeserialize(queue);
        root.right = preOrderDeserialize(queue);
        return root;
    }
```

#### [LC] 314. Binary Tree Vertical Order Traversal
https://leetcode.com/problems/binary-tree-vertical-order-traversal/

NOTE: the left subtree could grow into right-hand-side

Define a new TreeNode to record coordinate x and y, then using TreeMap to sort the x coordinate:

```
TreeMap<Integer, List<Integer>> orderedMap = new TreeMap<>((a,b) -> {return a-b;});
```

#### [LC] 426. Convert Binary Search Tree to Sorted Doubly Linked List
https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/

- set a dummyHead node first, then use a global variabl `prev` to build the double connection with inOrder node
- be careful when we only have one node in the tree, the one node needs to have circle left/right to itself

#### [LC] 449. Serialize and Deserialize BST
https://leetcode.com/problems/serialize-and-deserialize-bst/

It can use the same solution as in `297. Serialize and Deserialize Binary Tree`, but it could be further improved to compress by bits
- considering each char in Java contains 2 bytes (in C a char is 1 byte), so that for a 32-bit integer, we only need 2 char to present it (aka 4 bytes or 32 bits)
  - if an integer is 1234, to convert into String it would be "1234" which occupies 4 char, but actually 2 char is enough to present them
- since we used 2 char to present int, theer is no room to tell whether it is null or not, so we need another char here to present whether it is null
- there is no need to use delimiter either, since for the encoded string, every 3 char would represent a node --> char[0] is top 16 bits, char[1] is bottom 16 bit, char[2] means whether it is null
- it cannot guarantee the length is smaller, but it saves time to convert integer to string

```java
    private static String intToString(int value) {
        char[] chars = new char[2];
        /**
          * since an integer is 32-bit, we shift right 16 bits, so that the top 16 bits could be placed at the bottom 16 bit
          * then we store this integer into char, only the bottom 16 bits would be stored in char
          * 0xffff is 1111...1 (there are 16 1 in total), and `& 0xffff` means convert it into a hex number
        **/
        chars[0] = (char) ((value >> 16) & 0xffff);
        /**
          * if we directly store integer into char, only the bottom 16 bits would be stored
        **/ 
        char[1] = value;
        return new String(chars); // char[] to String
    }
```

```java
    private static int stringToInt(String str) {
        char[] array = str.toCharArray();
        char top16Bits = array[0];
        char bottom16Bits = array[1];

        int result = 0;
        result = (int) top16Bits; 
        result = (result << 16) + (int) bottom16Bits;

        return result;
    }
```

#### [LC] 515. Find Largest Value in Each Tree Row
https://leetcode.com/problems/find-largest-value-in-each-tree-row/

Be careful when using ++variable, it would addup the variable itself! It's better to use variable + 1 most of the time;

#### [LC] 536. Construct Binary Tree from String
https://leetcode.com/problems/construct-binary-tree-from-string/

Using the same idea as `772. Basic Calculator III`, we treat the string like this:  
```java

1(2(3)(4))(5)

1 --> root
(2(3)(4)) --> root.left
(5) --> root.right
```

Then we just need to find the matched left and right brackets and using `substring` to extract them and send for recursion. The way to find corresponding right bracket is to record the count of left and right brackets.

As for the num, we can use `currentNum = currentNum * 10 + c - '0'`.

The finaly time complexity is `O(NlogN)`, since we need to traverse the whole string at each layer, and there are `logN` layer in total --> here `logN == height of the tree`

#### [LC] 543. Diameter of Binary Tree
https://leetcode.com/problems/diameter-of-binary-tree/

It is similar like "124. Binary Tree Maximum Path Sum".


Be careful that the longest path may not pass the root.  
e.g.
```
    longest path may not pass root
        1
     2      3
         4     5
        6  7  8 9
      10         11

```

- Using Kadane's idea, maintain a gloable variable maxPath, inside each recursive, check whether we want to use the current root
- we can record # of nodes in path, but final result needs # of edges, so finally need to minus 1 to get number of edges

#### [LC] 617. Merge Two Binary Trees
https://leetcode.com/problems/merge-two-binary-trees/


#### [LC] 606. Construct String from Binary Tree
https://leetcode.com/problems/construct-string-from-binary-tree/

The base problem is very simple, just do preOrder to traverse the tree, but the requirement to not record redundant nodes is tricky:

1. if node has both left and right, then recusively call left and right
2. if node does not have left and right, then only return itself
3. if node only has left, then ignore right --> since ignoring right would not impact the shape decision
4. if node only has right, then record left as empty brackets --> since empty left needs to be recorded to maintain the shape

#### [LC] 654. Maximum Binary Tree
https://leetcode.com/problems/maximum-binary-tree/

NOTE:
1. Be careful when talking about complexity:
 - time complexity: average is O(nlogn), worst case is O(n^2)
  - if we count the number of `constructor` called. On average perspective there would be logn levels, but for skewed list it would be n^2
  - if we count the operation of `comparison`, it would still be O(n^2), since we do the `findMaxNum` n(n+1)/2 = n^2 times
 - space complexity: for stack space, average is O(logn), worst case is O(n) for skewed list
2. Maybe no need to use a custom class as Result in `findMaxNum`, the `maxPos` could reflect `maxValue`


#### [LC] 863. All Nodes Distance K in Binary Tree
https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/

This is a tree question, but it's also a very standard DFS+BFS.  
Firstly we use DFS to convert the tree into a graph, by adding left/right/parent of each node into an adjacent graph/map.  
Secondly we use BFS to calculate the k-distance node from target node.

Be careful when using BFS, we need to get queue size by `int size = queue.size()` first, since if we use `queue.size()` in the for loop then the size will change!!!!


#### [LC][Medium] 865. Smallest Subtree with all the Deepest Nodes
https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/

The logic is simple. 
Run 1st DFS to record height of each node in hashmap.  
Run 2nd DFS to check if a node can have leftHeight and rightHeight equals to current deepest height.

#### [LC][Medium] 1123. Lowest Common Ancestor of Deepest Leaves
https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/


Exactly the same as `[Medium] 865. Smallest Subtree with all the Deepest Nodes`.

#### [LC] 938. Range Sum of BST
https://leetcode.com/problems/range-sum-of-bst/

#### [LC] 958. Check Completeness of a Binary Tree
https://leetcode.com/problems/check-completeness-of-a-binary-tree/

This is a smart use case of BFS.

If we ever find a null node in the queue, then set the flag to be true, and if we ever find another non-null node afterwards, we know it's not a complete tree, so that we can return false.

e.g. the following 2 trees are not a complete tree, since when doing BFS, we can find null before 7 or null before 6.

```java
        1
    2        3
  4   5    6   null 
7 

        1
    2        3
  4   5  null  6 

```

#### [LC] 987. Vertical Order Traversal of a Binary Tree
https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/

Sort the node by `x coordinate, y coordinate, val` using BFS + TreeMap + Collections.sort().

#### [LC] 1022. Sum of Root To Leaf Binary Numbers
https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/

For each root to leaf path, do `sum = sum * 2 + root.val`, and add it up to global variable if it's leaf node.

#### [LC][Medium] 1026. Maximum Difference Between Node and Ancestor
https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/

This is simple.  
We can traverse the tree and pass `int max` and `int min` varaibles. When we reach leaf node, compute the diff of current max and min.

#### [LC][Medium] 1120. Maximum Average Subtree
https://leetcode.com/problems/maximum-average-subtree/

This problem is simple.  
Just do a dfs for each node, and return `int[]` to record the sum of all nodes and the count of all nodes.  
Then use a global variable to record the max average.


#### [LC] 1361. Validate Binary Tree Nodes
https://leetcode.com/problems/validate-binary-tree-nodes/

This problem is very interesting.  
We can run DFS once to tell if it's connected (if `visited.size() == n`) and if it has cycle (if we viste an already visited node).  
But we need to find a root first before running the DFS.  
And the way to find root is similar like the 1st step of topological sort, which is to find the 0-ingress node.  


NOTE:
- do not try to validate without DFS, by simplying traversing leftChild array and rightChild array would not work!!! 


Here is an example that tells why we have to use DFS or BFS:
```java

1 --> 0 --> 2   3
<-----------

``` 


#### [LC] 1650. Lowest Common Ancestor of a Binary Tree III
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/

1. find the height of p and q
2. if p's height is lower, move p up to the same level as q, otherwise move q up to the same level
3. now p and q are at same height, if `p==q` then return p or q, otherwise move p and q up until `p.parent == q.parent` then return `p.parent`