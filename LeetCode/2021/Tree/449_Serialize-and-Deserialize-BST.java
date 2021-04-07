// https://leetcode.com/problems/serialize-and-deserialize-bst/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    private static final String DELIMITER = ",";
    private static final String NULLSYMBOL = "#";
    
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrderSerialize(root, sb);
        return sb.toString();
    }

    private void preOrderSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULLSYMBOL + DELIMITER);
            return;
        }
        sb.append(root.val + DELIMITER);
        preOrderSerialize(root.left, sb);
        preOrderSerialize(root.right, sb);
    }
    
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<Integer> queue = new LinkedList<Integer>();
        String[] array = data.split(DELIMITER);
        for (int i = 0; i < array.length; i++) {
            if (NULLSYMBOL.equals(array[i]) || array[i] == "" ) {
                queue.offer(null);
            } else {
                queue.offer(Integer.valueOf(array[i]));
            }
        }
        return preOrderDerialize(queue);
    }
    
    private TreeNode preOrderDerialize(Deque<Integer> queue) {
        Integer val = queue.poll();
        if (val == null) {
            return null;
        }
        TreeNode node = new TreeNode(val);
        node.left = preOrderDerialize(queue);
        node.right = preOrderDerialize(queue);
        return node;
    }
    
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// String tree = ser.serialize(root);
// TreeNode ans = deser.deserialize(tree);
// return ans;