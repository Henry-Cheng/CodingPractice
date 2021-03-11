// https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45551/preorder-inorder-and-postorder-iteratively-summarization

public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(!stack.isEmpty() || root != null) {
        if(root != null) {
            stack.push(root);
            result.add(root.val);  // visit before going to children
            root = root.left;
        } else {
            root = stack.pop();
            root = node.right;   
        }
    }
    return result;
}

public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(!stack.isEmpty() || root != null) {
        if(root != null) {
            stack.push(root);
            root = root.left;
        } else {
            root = stack.pop();
            result.add(root.val);  // visite after all left children
            root = root.right;   
        }
    }
    return result;
}

public List<Integer> postorderTraversal(TreeNode root) {
    LinkedList<Integer> result = new LinkedList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(!stack.isEmpty() || root != null) {
        if(root != null) {
            stack.push(root);
            result.addFirst(root.val);     // Reverse the process of preorder
            root = root.right;             // Reverse the process of preorder
        } else {
            root = stack.pop();
            root = root.left;             // Reverse the process of preorder
        }
    }
    return result;
}