// https://leetcode.com/problems/balanced-binary-tree/submissions/
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
    public boolean isBalanced(TreeNode root) {
        Result result = isBalancedHelper(root);
        return result.isBalanced;
    }
    
    public Result isBalancedHelper(TreeNode root) {
        Result result = new Result();
        if (root == null) {
            result.height = 0;
            result.isBalanced = true;
            return result;
        }
        Result leftResult = isBalancedHelper(root.left);
        Result rightResult = isBalancedHelper(root.right);
        if (leftResult.isBalanced && rightResult.isBalanced) {
            if (Math.abs(leftResult.height - rightResult.height) <= 1) {
                result.isBalanced = true;
            }
        }
        result.height = Math.max(leftResult.height, rightResult.height) + 1;
        return result;
    }
    
    public static class Result {
        public int height = 0;
        public boolean isBalanced = false;
        public Result() {}
    }
}