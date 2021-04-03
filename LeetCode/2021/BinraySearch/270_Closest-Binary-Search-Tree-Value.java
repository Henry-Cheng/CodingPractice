// https://leetcode.com/problems/closest-binary-search-tree-value/submissions/
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
    int maxMin = Integer.MIN_VALUE;
    int minMax = Integer.MAX_VALUE;
    
    // Binary Search
    public int closestValue(TreeNode root, double target) {
        while(root != null) {
            if (root.val <= target) {
                maxMin = Math.max(maxMin, root.val);
                root = root.right;
            } else {
                minMax = Math.min(minMax, root.val);
                root = root.left;
            }
        }
        double maxMinAbs = Math.abs(maxMin - target);
        double minMaxAbs = Math.abs(minMax - target);
        return maxMinAbs < minMaxAbs ? maxMin : minMax;
    }
    
    // Interative Traverse
    public int closestValue_iterative(TreeNode root, double target) {
        //inOrder(root, target);
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            while (root.left != null) {
                stack.push(root.left);
                root = root.left;
            }
            root = stack.pop();
            // visit node
            if (root.val <= target) {
                maxMin = Math.max(maxMin, root.val);
            } else {
                minMax = Math.min(minMax, root.val);
            }
            if (root.right != null) {
                stack.push(root.right);
                root = root.right;
            }
        }
        
        double maxMinAbs = Math.abs(maxMin - target);
        double minMaxAbs = Math.abs(minMax - target);
        return maxMinAbs < minMaxAbs ? maxMin : minMax;
    }
    
    // recursive traverse
    public int closestValue_recursive(TreeNode root, double target) {
        inOrder(root, target);
        double maxMinAbs = Math.abs(maxMin - target);
        double minMaxAbs = Math.abs(minMax - target);
        return maxMinAbs < minMaxAbs ? maxMin : minMax;
    }
    
    private void inOrder(TreeNode root, double target) {
        if (root == null) {
            return;
        }
        inOrder(root.left, target);
        if (root.val <= target) {
            maxMin = Math.max(maxMin, root.val);
        } else {
            minMax = Math.min(minMax, root.val);
        }
        inOrder(root.right, target);
    }
}