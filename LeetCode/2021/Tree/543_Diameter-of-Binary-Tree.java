// https://leetcode.com/problems/diameter-of-binary-tree/
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
    /**
    longest path may not pass root
        1
     2     3
         4    5
        6  7 8 9
       10        11
    **/
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
}