// https://leetcode.com/problems/invert-binary-tree/
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
    public TreeNode invertTree(TreeNode root) {
        // corner case
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        // get left subtree an dright subtree by recusion
        root.left = invertTree(root.left);
        root.right = invertTree(root.right);
        // current recusive layer work
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        // return
        return root;
    }
}