// https://leetcode.com/problems/sum-root-to-leaf-numbers/
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
    public int sumNumbers(TreeNode root) {
        sumNumbersHelper(root, 0);
        return result;
    }
    
    private int result = 0;
    public void sumNumbersHelper(TreeNode root, int prevSum) {
        int sum = prevSum * 10 + root.val;
        if (root.left == null && root.right == null) {
            result += sum;
            return;
        }
        if (root.left != null) {
            sumNumbersHelper(root.left, sum);
        }
        if (root.right != null) {
            sumNumbersHelper(root.right, sum);
        }
    }
}