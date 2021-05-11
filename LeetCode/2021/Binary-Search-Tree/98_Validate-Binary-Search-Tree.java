// https://leetcode.com/problems/validate-binary-search-tree/
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
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        BST result = isValidBSTHelper(root);
        return result.isBST;
    }
    
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
}