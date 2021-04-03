// https://leetcode.com/problems/closest-binary-search-tree-value/submissions/
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
    int maxMin = Integer.MIN_VALUE;
    int minMax = Integer.MAX_VALUE;
    public int closestValue(TreeNode root, double target) {
        inOrder(root, target);
        double maxMinAbs = Math.abs(maxMin - target);
        double minMaxAbs = Math.abs(minMax - target);
        return maxMinAbs < minMaxAbs ? maxMin : minMax;
    }
    
    private void inOrder(TreeNode root, double target) {
        if (root == null) {
            return;
        }
        inOrder(root.left, target);
        if (root.val <= target) {
            maxMin = Math.max(maxMin, root.val);
        } else {
            minMax = Math.min(minMax, root.val);
        }
        inOrder(root.right, target);
    }
}