// https://leetcode.com/problems/maximum-average-subtree/
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
    private double max = Integer.MIN_VALUE;
    public double maximumAverageSubtree(TreeNode root) {
        dfs(root);
        return max;
    }
    private int[] dfs(TreeNode root) {
        if (root.left == null && root.right == null) {
            max = Math.max(max, root.val);
            return new int[]{root.val, 1};
        }
        int[] left = null;
        if (root.left != null) {
            left = dfs(root.left);
        }
        int[] right = null;
        if (root.right != null) {
            right = dfs(root.right);
        }
        int sum = (left != null ? left[0] : 0)+ (right != null ? right[0] : 0) + root.val;
        int count = (left != null ? left[1] : 0)+ (right != null ? right[1] : 0) + 1;
        
        //System.out.println("sum is " + sum + ", count is " + count + ", avg is " + average);
        max = Math.max(max, (double)sum / count);
        return new int[]{sum, count};
    }
}