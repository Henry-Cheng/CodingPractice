
public class Flattern {

	public static void main(String[] args) {
		TreeNode root = SerializeDeserializeBT.deserialize("[1,2,3,4,null]");
		flatten(root);
		System.out.println(SerializeDeserializeBT.serialize(root));
	}

    public static void flatten(TreeNode root) {
        root = preOrder(root);
    }
    
    public static TreeNode preOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        // save root.left and root.right as temp value in case infinite loop
        TreeNode tmpLeft = root.left;
        TreeNode tmpRight = root.right;
        
        TreeNode left = preOrder(tmpLeft);
        root.right = left;
        root.left = null;
        if (left != null) left.left = null;
        TreeNode right = preOrder(tmpRight);
        if (left != null) {
            while(left.right != null) {
                left=left.right; // let left points to its rightMost node
            }
        }
        if (left == null) {
            root.right = right;
        } else {
            left.right = right;
        }
        if(right != null) right.left = null;
        return root;
    }
}