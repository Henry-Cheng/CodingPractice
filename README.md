# CodingPractice

## Hints

if cannot solve easily, sort the array

if one round dfs is not enough, use 2 rounds

## Algorithm details
Be careful on following details:

1. For BFS
  - remember to add entryPoint nodes to visited before traversing queue
  - remember to store queue size into int variable, instead of using `queue.size()` directly
  - remember to add node to visited when traversing the neightbors 
2. For DFS
  - remember to try all entryPoints using for loop, and then for each of them run DFS
  - remember to add all entryPoints to visited at begining
  - at the beginning of the recursive function, do not forget to check termination condition
3. For Tree
  - remember to check if root.left and root.right is null before call dfs for root.left and root.right
4. For recusive + memorization
  - the memorization could be HashMap, or 2D array --> by using 2D array, no need to construct a string key in HashMap

## Java details
1. Java uses unicode, in which each character would occupies 2 bytes, no matter it's English char or Chinese char
  - For some other encoding like GBK, the English char is 1 byte, while Chinese char is 2 bytes
2. `Arrays.copyOf(arr, arr.length)` is shallow copy!!!
  - have to copy the element one by one to implement deep copy
3. to remove ith character from string
  - `String newS = s.substring(0,i) + s.substring(i+1,s.length());`
4. to get random number
```java
    // both end inclusive
    protected int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
```
5. convert List<Integer> to int[] by `resultList.stream().mapToInt(i->i).toArray()`
6. when compare Integer, must use `Integer.equals()`
7. String.split
```java
`string.split()`
- when spliting special characters like `.`, need to escape it like `string.split(\\.)`
- when only right-hand side has characters, result array is of size 2
  - `String s = ".1"; String[] arr = s.split("\\.");`
  - `arr` length is 2
  - `arr[0] == ""; arr[1] == "1"`
- when only left-hand side has characters, result array is of size 1
  - `String s = "1."; String[] arr = s.split("\\.");`
  - `arr` length is 1
  - `arr[0] == "1";`
- when nothing at left-hand side and right-hand side, result array is of size 0
  - `String s = "."; String[] arr = s.split("\\.");`
  - `arr` length is 0
- we can also force `split()` to return 2-element array by `string.split(delimiter, limit)`
  - `String s = "."; String[] arr = s.split("\\.", 2);`
  - `arr` length is 2 and both elements are empty
  - `arr[0] == ""; arr[1] == ""`
- but if no "." at all, then array size would always be 1
```
8. Using `Integer.toBinaryString(int i)` to get the binary string.  
9. `stringBuilder.insert(index, char)` to append character to the front


## Pronuciation
- `n!` --> factorial
  - e.g. recursion method of 

## Force remembering

#### [LC][Hard] 32. Longest Valid Parentheses
https://leetcode.com/problems/longest-valid-parentheses/

This is really hard. Have to force remembering this one.  

It can still use stack, but not like `[Easy] 20. Valid Parentheses` to push brackets, we need to push index of the left brackets to help calculate the distance.  
We also need to maintain a global variable `start` to record the start position of the `current leftMost index of left bracket`.  

```java
for(int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    if (c == '(') { // push left bracket
        stack.push(i);
    } else {
        if (stack.isEmpty()) {
            start = i + 1; // invalid, reset leftMost pos
        } else { // valid
            stack.pop(); // pop every time
            if (stack.isEmpty()) { // e.g. ()(())
                result = Math.max(result, i - start + 1);
            } else { // e.g. (()
                result = Math.max(result, i - stack.peek());
            }
        }
    }
}

```

#### [LC][Hard] 124. Binary Tree Maximum Path Sum
https://leetcode.com/problems/binary-tree-maximum-path-sum/

This question is interseting, here the "path" is defined as any number of connect node:
e.g. for a tree like this, the max path is just node "3"

```java
     -1
  -2    3
      -4  -5

```

e.g. for a tree like this, the max path is "15-20-7"

```java
    -10
  9      20
      15    7
```

- using the same idea like kadane's algorithm
  - maintain a currentMaxSum, and then compare with current node, we can choose to only use current node (by throwing away currentMaxSum), or addup current node
- but this question is a bit different, not like kadane's algorithm which has only one direction, here we have two directions
- `currentMaxSum = max(root, root+left, root+right)` -- we can return it to parent stack in dfs 
- `totalMaxSum = max(totalMaxSum, currentMaxSum, root+left+right)` -- we need to check whether we can directly use `root+left+right`

```java
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = dfs(root.left);
        int rightSum = dfs(root.right);
        int currentMax = Math.max(
            root.val, // only use root node, since child node could be negative
            Math.max(
                root.val + leftSum,  // only use right subtree
                root.val + rightSum  // only use left subtree
            )
        );

        totalMax = Math.max(
            totalMax, 
            Math.max(
                currentMax, 
                root.val + leftSum + rightSum
            )
        );

        return currentMax;
    }
```

#### [LC][Medium] 173. Binary Search Tree Iterator
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

    Deque<TreeNode> stack;
    
    public BSTIterator(TreeNode root) {
        stack = new LinkedList<>();
        while(root != null) { // push root and all its left to stack
            stack.push(root);
            root = root.left;
        }
    }
    
    public int next() {
        TreeNode pop = stack.pop();
        TreeNode right = pop.right;
        while(right != null) { // push pop.right and all its left to stack
            stack.push(right);
            right = right.left;
        }
        return pop.val;
    }
    
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
```

#### [LC][Hard] 224. Basic Calculator
https://leetcode.com/problems/basic-calculator/

NOTE:  
- always check digits first by `Character.isDigit()`, and put all others in else part


1. For `+,-` with parenthesis 
  - using global result variable to addup currentNum
  - do `s = s + " "` to make sure we can collect the space at the end 
  - only push the result when seeing '(', remember to push the sign also for this parenthese
  - only pop when seeing ')', remember to pop both the sign and the cached result
  - finally return result
2. For `+,-,*,/` without parenethsis
  - do not use global result variable, push all currentNum to stack
  - do not use `s = s + " "`, use this instead: `else if (i == (s.length() - 1) || c == '+' || c == '-' || c == '*' || c =='/')`
  - when previous ops is '+', push `currentNum * sign` when 
  - when previous ops is '*', pop previous num and multiple with currentNum, then push back
  - when previous ops is '/', pop previous num and dividended with currentNum, then push back
  - finally return the sum of all nums in stack


#### [LC][Hard] 297. Serialize and Deserialize Binary Tree
https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

- seralize with preOrder
  - using "," as delimiter and "#" for null node
- deserialize with queue to store splitted string by ","
  - be careful if the original root is null, array[0] is ""

```java
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrderSerialize(root, sb);
        return sb.toString();
    }

    private void preOrderSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#,");
            return;
        }
        sb.append(root.val);
        sb.append(",");
        preOrderSerialize(root.left, sb);
        preOrderSerialize(root.right, sb);
    }

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

#### [LC][Medium] 986. Interval List Intersections
https://leetcode.com/problems/interval-list-intersections/

NOTE: 
- only need to compare p1 and p2, no need to compare with last element in result list --> they would never have overlap since we always move the pointer of smaller interval
- how to tell if they are intersected?
  - `int low = Math.max(firstList[p1][0], secondList[p2][0]);`
  - `int high = Math.min(firstList[p1][1], secondList[p2][1]);`
  - `if (low <= high) {return true;}`
- how to move the pointer of the smaller interval?
  - `if (firstList[p1][1] <= secondList[p2][1]) {p1++;}`

```java
        while(p1 < firstList.length && p2 < secondList.length) {
            int low = Math.max(firstList[p1][0], secondList[p2][0]);
            int high = Math.min(firstList[p1][1], secondList[p2][1]);
            if (low <= high) {
                int[] intersection = new int[2];
                intersection[0] = low;
                intersection[1] = high;
                result.add(intersection);
            }
            if (firstList[p1][1] <= secondList[p2][1]) {
                p1++;
            } else {
                p2++;
            }
        }
```

#### [LC][Easy] 543. Diameter of Binary Tree
https://leetcode.com/problems/diameter-of-binary-tree/

This is not easy at all...

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

```java
    int max = Integer.MIN_VALUE;
    public int diameterOfBinaryTree(TreeNode root) {
        // using Kadane's algorithm idea, we may not use root
        int left = diameterHelper(root.left); // longest left path
        int right = diameterHelper(root.right); // longest right path
        
        return Math.max(max, left + right + 1) - 1;
    }
    
    private int diameterHelper(TreeNode root) {// count # of nodes
        if (root == null) {
            return 0;
        }
        int left = diameterHelper(root.left);
        int right = diameterHelper(root.right);
        
        max = Math.max(max, left + right + 1); // if using root
        return Math.max(left, right) + 1; 
    }
```

#### [LC] 333. Largest BST Subtree

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
