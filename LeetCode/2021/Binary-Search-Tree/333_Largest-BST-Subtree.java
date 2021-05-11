// https://leetcode.com/problems/largest-bst-subtree/
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
    private int result = 0;
    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }
        checkTree(root);
        return result;
    }
    
    private BST checkTree(TreeNode root) {
        if (root.left == null && root.right == null) {
            result = Math.max(result, 1);
            return new BST(true, 1, root.val, root.val);
        }
        BST left = null;
        if (root.left != null) {
            left = checkTree(root.left);
        }
        BST right = null;
        if (root.right != null) {
            right = checkTree(root.right);
        }

        if ((left == null || (left.isBST && left.max < root.val)) && 
            (right == null || (right.isBST && right.min > root.val))
           ) {
            // this is a valid BST
            int sum = (left != null ? left.sum : 0) + (right != null? right.sum : 0) + 1;
            int min = left != null ? left.min : root.val;
            int max = right != null ? right.max : root.val;
            result = Math.max(result, sum);
            return new BST(true, sum, min, max);
        } else {
            return new BST(false, 0, root.val, root.val);
        }
    }
    
    private static class BST {
        public boolean isBST;
        public int sum;
        public int min;
        public int max;
        public BST(boolean isBST, int sum, int min, int max) {
            this.isBST = isBST;
            this.sum = sum;
            this.min = min;
            this.max = max;
        }
    }
}