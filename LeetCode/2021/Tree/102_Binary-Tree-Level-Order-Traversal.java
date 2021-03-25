// https://leetcode.com/problems/binary-tree-level-order-traversal/
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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        // do BFS
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // no need to use visited since there is no cycle in tree
        int level = 0;
        while(!queue.isEmpty()) {
            int countAtThisLevel = queue.size();
            List<Integer> nodeAtThisLevel = new LinkedList<>();
            for (int i = 0; i < countAtThisLevel; i++) {
                TreeNode node = queue.poll();
                nodeAtThisLevel.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (nodeAtThisLevel.size() > 0) {
                result.add(nodeAtThisLevel);
            }
            level++;
        }
        return result;
    }
}