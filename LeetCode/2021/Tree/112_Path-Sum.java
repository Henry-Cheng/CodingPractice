// https://leetcode.com/problems/path-sum/
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
    boolean result = false;
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        preOrder(root, targetSum, 0);
        return result;
    }
    
    public void preOrder(TreeNode root, int targetSum, int currentSum) {
        if (root == null) {
            return;
        } else if (root.left == null && root.right == null) { // find leaf node
            if (currentSum + root.val == targetSum) {
                result = true;
            }
            return;
        }
        preOrder(root.left, targetSum, currentSum + root.val);
        preOrder(root.right, targetSum, currentSum + root.val);
    }
}