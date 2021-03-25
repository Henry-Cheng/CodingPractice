// https://leetcode.com/problems/binary-tree-vertical-order-traversal/submissions/
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
    // look at example 3, the left subtree could grow into right-subtree-area!!!
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        // bfs
        Deque<NodePos> queue = new LinkedList<>();
        queue.offer(new NodePos(0, 0, root));
        int level = 0;
        TreeMap<Integer, List<Integer>> orderedMap = new TreeMap<>((a,b) -> {return a-b;});
        while(!queue.isEmpty()) {
            int nodeAtCurrentLevel = queue.size();
            for (int i = 0; i < nodeAtCurrentLevel; i++) {
                NodePos nodePos = queue.poll();
                // visit
                TreeNode node = nodePos.node;
                int x = nodePos.x;
                int y = nodePos.y;
                // put into tree map
                List<Integer> values = orderedMap.get(x);
                if (values == null) {
                    values = new LinkedList<>();
                }
                values.add(node.val);
                orderedMap.put(x, values); // O(logD) where D is diameter of tree
                // check all children
                if (node.left != null) {
                    queue.offer(new NodePos(x - 1, y + 1, node.left));
                }
                if (node.right != null) {
                    queue.offer(new NodePos(x + 1, y + 1, node.right));
                }
            }
            level++;
        }
        return orderedMap.keySet().stream().map(k -> orderedMap.get(k)).collect(Collectors.toList());
    }
    
    private static class NodePos {
        int x;
        int y;
        TreeNode node;
        public NodePos(int x, int y, TreeNode node) {
            this.x = x;
            this.y = y;
            this.node = node;
        }
    }
}