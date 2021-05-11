// https://leetcode.com/problems/balance-a-binary-search-tree/
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
    public TreeNode balanceBST(TreeNode root) {
        ArrayList<TreeNode> list = new ArrayList<>();
        inOrderSerialize(root, list);
        return inOrderDeserialize(list, 0, list.size() - 1);
    }
    
    private void inOrderSerialize(TreeNode root, ArrayList<TreeNode> list) {
        if (root == null) {
            return;
        }

        inOrderSerialize(root.left, list);
        list.add(root);
        inOrderSerialize(root.right, list);
    }
    
    private TreeNode inOrderDeserialize(ArrayList<TreeNode> list, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) / 2;
        TreeNode node = list.get(mid);  
        node.left = inOrderDeserialize(list, left, mid -1);
        node.right = inOrderDeserialize(list, mid+1, right);
        return node;
    }
    
}