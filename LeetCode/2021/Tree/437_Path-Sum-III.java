// https://leetcode.com/problems/path-sum-iii/
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
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        return findSum(root, targetSum, new ArrayList<>());
    }
    
    private int findSum(TreeNode root, int targetSum, List<Integer> prefixSum) {
        int count = 0;
        if (root.val == targetSum) {
            count++;
        }
        List<Integer> newPrefixSum = new ArrayList<>();
        for (int prevSum : prefixSum) {
            if (prevSum + root.val == targetSum) {
                count++;
            }
            newPrefixSum.add(prevSum + root.val);
        }
        newPrefixSum.add(root.val);
        
        // now check left and right subtree
        if (root.left != null) {
            count += findSum(root.left, targetSum, newPrefixSum);
        }
        if (root.right != null) {
            count += findSum(root.right, targetSum, newPrefixSum);
        } 
        return count;
    }
}