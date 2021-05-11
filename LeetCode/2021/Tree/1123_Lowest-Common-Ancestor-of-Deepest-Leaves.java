// https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
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
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        HashMap<TreeNode, Integer> heightMap = new HashMap<>();
        int height = dfsForHeight(root, heightMap);
        return dfsForCommonAncester(root, heightMap, height);
    }
    
    private int dfsForHeight(TreeNode root, HashMap<TreeNode, Integer> heightMap) {
        if (root.left == null && root.right == null) {
            heightMap.put(root, 1);
            return 1;
        }
        int leftHeight = 0;
        if (root.left != null) {
            leftHeight = dfsForHeight(root.left, heightMap);
        }
        int rightHeight = 0;
        if (root.right != null) {
            rightHeight = dfsForHeight(root.right, heightMap);
        }
        int height = Math.max(leftHeight, rightHeight) + 1;
        heightMap.put(root, height);
        return height;
    }
    
    private TreeNode dfsForCommonAncester(TreeNode root, HashMap<TreeNode, Integer> heightMap, int height) {
        if (root.left == null && root.right == null) {
            return root;
        }
        int leftHeight = root.left == null ? 0 : heightMap.get(root.left);
        int rightHeight = root.right == null ? 0 : heightMap.get(root.right);
        if (leftHeight == height - 1 && rightHeight == height -1) {
            return root;
        } else if (leftHeight == height - 1 && rightHeight != height -1) {
            return dfsForCommonAncester(root.left, heightMap, height-1);
        } else if (leftHeight != height - 1 && rightHeight == height -1) {
            return dfsForCommonAncester(root.right, heightMap, height-1);
        }
        return null;
    }
}