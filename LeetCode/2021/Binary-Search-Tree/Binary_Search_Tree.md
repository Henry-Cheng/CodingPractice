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