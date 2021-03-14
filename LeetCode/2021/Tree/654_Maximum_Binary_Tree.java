// https://leetcode.com/problems/maximum-binary-tree/

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
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        int n = nums.length;
        if (nums.length == 1) {
            return new TreeNode(nums[0], null, null);
        }
        return f(nums, 0, n-1);
    }
    public TreeNode f(int[] nums, int start, int end) {
        // termination condition
        // 3 2 1
        // round1: start 0, end 2
        // round2: start 0, end 0-1 == -1
        if (start > end) {
            return null;
        } else if (start == end) {
            return new TreeNode(nums[start], null, null);
        }
        // 1. find max num in array
        Result result = findMaxNum(nums, start, end);
        int max = result.max;
        int maxPos = result.maxPos;
        // 2. recursion for left subarray
        TreeNode left = f(nums, start, maxPos - 1);
        TreeNode right = f(nums, maxPos + 1, end);
        // 3. build tree
        TreeNode root = new TreeNode(max, left, right);
        // 4. return tree
        return root;
    }
    
    public static class Result {
        public int max;
        public int maxPos;
        public Result(int max, int maxPos) {
            this.max = max;
            this.maxPos = maxPos;
        }
    }
    
    public Result findMaxNum(int[] nums, int start, int end) {
        int max = Integer.MIN_VALUE;
        int maxPos = -1;
        for (int i = start; i <= end; i++) {
            if (nums[i] >= max) { // "equal" would not happen per question
                max = nums[i];
                maxPos = i;
            }
        }
        return new Result(max, maxPos);
    }
}