// https://leetcode.com/problems/path-sum-ii/
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
    private List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return result;
        }
        List<Integer> path = new LinkedList<>();
        preOrder(root, targetSum, 0, path);
        return result;
    }
    
    private void preOrder(TreeNode root, int targetSum, int sum, List<Integer> path) {
        // visit root
        path.add(root.val);
        sum += root.val;
        if (root.left == null && root.right == null) {
            if (sum == targetSum) {
                result.add(new LinkedList<>(path));
            }
        } else {
            if (root.left != null) {
                preOrder(root.left, targetSum, sum, path);
            }
            if (root.right != null) {
                preOrder(root.right, targetSum, sum, path);
            }
        }
        path.remove(path.size() - 1); // backtrack
    }
}