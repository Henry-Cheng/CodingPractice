import java.util.Deque;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while(root != null || !stack.isEmpty()) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.add(root.val);
            root = root.right;
        }
        return result;
    }
    
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        // prepare result
        List<Integer> result = new LinkedList<>();
        // corner case
        if (root == null) {
            return result;
        }
        // do inorder traverse
        inOrder(root, result);
        return result;
    }
    
    public void inOrder(TreeNode root, List<Integer> result) {
        // corner case
        if (root == null) {
            return;
        }
        // visit left child
        inOrder(root.left, result);
        // visit current node
        result.add(root.val);
        // visit right child
        inOrder(root.right, result);
    }
}