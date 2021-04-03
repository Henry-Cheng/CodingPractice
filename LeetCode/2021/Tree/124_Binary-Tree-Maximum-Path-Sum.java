// https://leetcode.com/problems/binary-tree-maximum-path-sum/submissions/
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
    private int totalMax = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        // using the idea from kadane's algorithm, maintain the previous sum, and then compare with current node, we can choose to only use current node (by throwing away prev result), or addup current 
        // this question is a bit different, not like kadane's algorithm has only one direction, here we have two directions
        // soFarMax[i] = max (soFarMax[i-1] + nums[i], nums[i])
        // totalMax = max (totalMax, soFarMax[i])
        
        // soFarMax[node] = max (node + soFarMax[l], node + soFarMax[r], node + soFarMax[l] + soFarMax[r], node)
        // totalMax = max(totalMax, node + soFarMax[l], node + soFarMax[r])
        if (root == null) {
            return 0;
        }
        dfs(root);
        return totalMax;
    }
    
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = dfs(root.left);
        int rightSum = dfs(root.right);
        int currentMax = Math.max(
            root.val, // only use root node
            Math.max(
                root.val + leftSum,  // only use right subtree
                root.val + rightSum  // only use left subtree
            )
        );

        totalMax = Math.max(
            totalMax, 
            Math.max(
                currentMax, 
                root.val + leftSum + rightSum // when check totalMax, do not forget to compare the `root.val + leftSum + rightSum` scenario
            )
        );

        return currentMax;
    }
    
    // another explaination
    private int dfs2(TreeNode root) {
        if (root == null) return 0;
        int l = dfs2(root.left);
        int r = dfs2(root.right);
        int sum = root.val;
        if (l > 0) sum += l;
        if (r > 0) sum += r;
        totalMax = Math.max(totalMax, sum);
        return Math.max(r, l) > 0 ? Math.max(r, l) + root.val : root.val;
    }
}