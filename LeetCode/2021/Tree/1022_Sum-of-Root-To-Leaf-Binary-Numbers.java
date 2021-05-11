// https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
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
    public int sumRootToLeaf(TreeNode root) {
        sumRootToLeafHelper(root, 0);
        return totalSum;
    }
    
    int totalSum = 0;
    private void sumRootToLeafHelper(TreeNode root, int sum) {
        sum = sum * 2 + root.val;
        if (root.left == null && root.right == null) {
            totalSum += sum;
        }
        if (root.left != null) {
            sumRootToLeafHelper(root.left, sum);
        }
        if (root.right != null) {
            sumRootToLeafHelper(root.right, sum);
        }
    }
}