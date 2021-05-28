// https://leetcode.com/problems/house-robber-iii/
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
    HashMap<TreeNode, Integer> robbed = new HashMap<>();
    HashMap<TreeNode, Integer> notRobbed = new HashMap<>();
    public int rob(TreeNode root) {
        return recursive(root, false);
    }
    
    private int recursive(TreeNode root, boolean isParentRobbed) {
        int result = 0;
        if (root == null) {
            return result;
        }
        if (isParentRobbed) { // cannot rob root
            if (robbed.containsKey(root)) {
                return robbed.get(root);
            } else {
                result += recursive(root.left, false) + 
                    recursive(root.right, false);
                robbed.put(root, result);
            }
        } else {
            if (notRobbed.containsKey(root)) {
                return notRobbed.get(root);
            } else {
                // option1: rob root
                int tmp1 = root.val +
                    recursive(root.left, true) + 
                    recursive(root.right, true);
                // option2: do not rob root
                int tmp2 = recursive(root.left, false) + 
                    recursive(root.right, false);
                result = Math.max(tmp1, tmp2);  
                notRobbed.put(root, result);
            }

        }
        return result;
    }
}