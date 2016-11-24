import java.util.*;

public class SerializeDeserializeBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TreeNode root = deserialize("[1,3,null,null,4]");
		//System.out.println(serialize(root));
		TreeNode root = deserializePreOrder("[1,2,4,null,null,null,3,null,null]");
		System.out.println(serializePreOrder(root));
	}
	
    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node != null ? node.val : null);
            if (node != null) {
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return list.toString();
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        String[] array = data.substring(1, data.length() - 1).split("\\s*,\\s*");
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if (array == null || array[0].equals("null")) {
        	return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(array[0]));
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < array.length) {
            TreeNode node = queue.poll();
            if (!array[i].equals("null")) {
                node.left = new TreeNode(Integer.parseInt(array[i]));
            }
            i++;
            if (i < array.length) {
                if (!array[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(array[i]));
                }
            }
            i++;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return root;
    }
    
    // pre-order example
    // Encodes a tree to a single string.
    public static String serializePreOrder(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        preOrderSerialization(root, list);
        return list.toString();
    }

    public static void preOrderSerialization(TreeNode root, List<Integer> list) {
        if (root != null) {
            list.add(root.val);
            preOrderSerialization(root.left, list);
            preOrderSerialization(root.right, list);
        } else {
            list.add(null);
        }
    }
    
    // Decodes your encoded data to tree.
    public static TreeNode deserializePreOrder(String data) {
        String[] nums = data.substring(1, data.length() - 1).split("\\s*,\\s*");
        TreeNode root = null;
        root = preOrderDeserialization(root, nums, new int[]{0});
        return root;
    }
    
    // if we are not allowed to use global variable, need to set i as array 
    public static TreeNode preOrderDeserialization(TreeNode root, String[] nums, int[] i) {
        if (i[0] >= nums.length) {
            return null;
        }
        if (!nums[i[0]].equals("null")) {
            root = new TreeNode(Integer.parseInt(nums[i[0]]));
            i[0]++;
            root.left = preOrderDeserialization(root.left, nums, i);
            i[0]++;
            root.right = preOrderDeserialization(root.right, nums, i);
        } else {
            root = null;
        }
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));