// https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        Deque<NodePos> queue = new LinkedList<>();
        queue.offer(new NodePos(0, 0, root));
        TreeMap<Integer, List<NodePos>> orderedMap = new TreeMap<>((a,b) -> {return a-b;});// ascending order
        while(!queue.isEmpty()) {
            NodePos nodePos = queue.poll();
            // visit the node coordinate and output to result
            TreeNode node = nodePos.node;
            int x = nodePos.x;
            int y = nodePos.y;
            List<NodePos> list = orderedMap.get(x);
            if (list == null) {// 1st time see this x
                list = new LinkedList<>();
            }
            list.add(nodePos); // we will sort the list latter
            orderedMap.put(x, list);
            // check children
            if (node.left != null) {
                queue.offer(new NodePos(x-1, y + 1, node.left));
            }
            if (node.right != null) {
                queue.offer(new NodePos(x + 1, y + 1, node.right));
            }
        }
        // now we have x ordered list in orderedMap
        // NOTE: we still need to maintain the row order in each column
        return orderedMap.keySet().stream().map(key -> {
            List<NodePos> list = orderedMap.get(key);
            Collections.sort(list, (a,b) -> {
                if (a.y != b.y) {
                    return a.y - b.y; // ascending by y
                } else {
                    return a.node.val - b.node.val; // ascending by node.val
                }
            }); // sort at row order, but for nodes at same row, sort them by val
            return list.stream().map(nodePos -> nodePos.node.val).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }
    
    private static class NodePos {
        public int x; // x coordinate
        public int y; // y coordinate
        public TreeNode node;
        public NodePos(int x, int y, TreeNode node) {
            this.x = x;
            this.y = y;
            this.node = node;
        }
    }
}