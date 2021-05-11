// https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
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
    public int maxAncestorDiff(TreeNode root) {
        dfs(root, root.val, root.val);
        return result;
    }
    private int result = 0;
    private void dfs(TreeNode root, int max, int min) {
        if (root != null) {
            max = Math.max(root.val, max);
            min = Math.min(root.val, min);
        }
        if (root.left == null && root.right == null) {
            result = Math.max(result, Math.abs(max-min));
        }
        if (root.left != null) {
            dfs(root.left, max, min);
        }
        if (root.right != null) {
            dfs(root.right, max, min);
        }
    }
    
    private int max = 0;
    private void dfs_with_all_parent(TreeNode root, HashMap<Integer, Integer> parentValCount) {
        if (!parentValCount.isEmpty()) {
            max = Math.max(max, getMax(parentValCount, root.val));
        }
        if (root.left == null && root.right == null) {
            return;
        }
        
        parentValCount.put(root.val, parentValCount.getOrDefault(root.val, 0) + 1);
        if (root.left != null) {
            dfs_with_all_parent(root.left, parentValCount);
        }
        if (root.right != null) {
            dfs_with_all_parent(root.right, parentValCount);
        }
        parentValCount.put(root.val, parentValCount.get(root.val) - 1);
    }
    
    private int getMax(HashMap<Integer, Integer> map, int num) {
        int max = 0;
        for (Integer key : map.keySet()) {
            if (map.get(key) > 0) {
                max = Math.max(max, Math.abs(key - num));
            }
        }
        return max;
    }
}