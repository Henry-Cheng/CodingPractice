import java.util.ArrayList;
import java.util.List;

public class DiameterBST {
	public static int globalDiameter = 0;
	public static void main(String[] args) {
		TreeNode root = initializeTree();
		int diameter = diameter(root);
		System.out.println(diameter);
		//System.out.println(diameterOpt(root, globalDiameter));
	}
	public static int height(TreeNode root, List<TreeNode> path) {
		if (root == null) {
			return 0;
		}
		int left = height(root.left, path) + 1;
		int right = height(root.right, path) + 1;
		if (left > right) {
			path.add(root.left);
		} else if (left < right) {
			path.add(root.right);
		} else {
			path.add(root);
		}
		return Math.max(left, right);
	}
	public static int diameter(TreeNode root) {
		if (root == null) {
			return 0;
		}
		List<TreeNode> leftPath = new ArrayList<TreeNode>();
		List<TreeNode> rightPath = new ArrayList<TreeNode>();
		int leftHeight =  height(root.left, leftPath);
		int rightHeight = height(root.right, rightPath);
		//printPath(leftPath);
		//printPath(rightPath);
		int overRoot = leftHeight + rightHeight + 1;
		int leftDiameter = diameter(root.left);
		int rightDiameter = diameter(root.right);
		return Math.max(overRoot, 
				Math.max(leftDiameter, rightDiameter));
	}
	public static void printPath(List<TreeNode> path) {
		for (TreeNode node : path) {
			System.out.print(node.val + " ");
		}
		System.out.println();
		
	}
    public static int diameterOpt(TreeNode root, int globalDiameter) { 
        if (root == null) {
        	return 0;
        }
        int left, right;
        left = diameterOpt(root.left, globalDiameter);
        right = diameterOpt(root.right, globalDiameter);
        if (left + right > globalDiameter) {
        	globalDiameter = left + right;
        }
        return Math.max(left, right) + 1;
    }
	public static TreeNode initializeTree() {
		/*
		 *        6
		 *   5          2
		 * 4     3    1
		 *     7
		 */
		TreeNode root = new TreeNode(6);
		root.left = new TreeNode(5);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(3);
		root.left.right.left = new TreeNode(7);
		root.right.left = new TreeNode(1);
		return root;
	}
}
