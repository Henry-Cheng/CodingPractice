// https://leetcode.com/problems/construct-string-from-binary-tree/
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
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        } else if (t.left == null && t.right == null) {
            return String.valueOf(t.val);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(t.val);
        sb.append("(" + tree2str(t.left) + ")");
        if (t.right != null) {
            sb.append("(" + tree2str(t.right) + ")");
        }
        return sb.toString();
    }
}