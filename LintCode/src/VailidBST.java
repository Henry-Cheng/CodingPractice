import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Definition of TreeNode:
class TreeNode implements Comparable{
	public int val;
	public TreeNode left, right;

	public TreeNode(int val) {
		this.val = val;
		this.left = this.right = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + val;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (val != other.val)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

class TreeNodeInfo {
	TreeNode node;
	int height;
}
public class VailidBST {
	/**
	 * @param root:
	 *            The root of binary tree.
	 * @return: True if the binary tree is BST, or false
	 */
	public boolean isValidBST(TreeNode root) {
		// write your code here
		return validate(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
	}

	public boolean validate(TreeNode root, int upper, int lower) {
		if (root == null) {
			return true;
		}
		boolean isRootValid = true;
		if (root.val > upper || root.val < lower) {
			return false;
		}
		boolean isLeftValid = true;
		if (root.left != null) {
			if (root.left.val < root.val) {
				isLeftValid = validate(root.left, root.val, lower);
			} else {
				return false;
			}
		}
		boolean isRightValid = true;
		if (root.right != null) {
			if (root.right.val > root.val) {
				isRightValid = validate(root.right, upper, root.val);
			} else {
				return false;
			}
		}
		return isRootValid && isLeftValid && isRightValid;
	}

	public static TreeNode constructTree() {
		/*
		 *          1
		 *       2      3
		 *    4    5       6
		 *     7
		 */
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.left.left.right = new TreeNode(7);
		return root;
	}

	public static TreeNode constructHardTree() {
		/*
		 *          1
		 *       2      3
		 *    4     5      6
		 *   7  8     9
		 *  10     11
		 *            12
		 */
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.left.left.left = new TreeNode(7);
		root.left.left.right = new TreeNode(8);
	    root.left.right.right = new TreeNode(9);
	    root.left.left.left.left = new TreeNode(10);
	    root.left.right.right.left = new TreeNode(11);
	    root.left.right.right.left.right = new TreeNode(12);
	    
		return root;
	}
	
	public static void main(String[] args) {
		List<TreeNode> list = findTreeNodesOnTreeDiameter(constructHardTree());
		printList(list);
		//list = findTreeNodesOnTreeDiameter(constructTree());
		//printList(list);
	}
	
	public static void printList(List<TreeNode> list) {
		for (TreeNode node : list) {
			System.out.print(node.val + " ");
		}
		System.out.println();
	}
	/////////////////////////////////////////////////////
	static class DiameterData {
		// the TreeNodes on the longest path from children to current TreeNode
		List<TreeNode> maxPath;
		// the TreeNodes on the diameter path with current TreeNode as root
		List<TreeNode> diameter;
		List<TreeNode> rightSubPath = new ArrayList<TreeNode>();
		
		DiameterData(List<TreeNode> path, List<TreeNode> dia) {
			this.maxPath = path;
			this.diameter = dia;
		}
		
		DiameterData(List<TreeNode> path, List<TreeNode> dia, List<TreeNode> rightSubPath) {
			this.maxPath = path;
			this.diameter = dia;
			this.rightSubPath = rightSubPath;
		}
	}


	public static List<TreeNode> findTreeNodesOnTreeDiameter(TreeNode tree) {
		DiameterData diameterData = findPath(tree);
		List<TreeNode> tmpDiameter = diameterData.diameter;
		int maxValue = Integer.MIN_VALUE;
		int pos = -1;
		for (int i = 0 ; i < tmpDiameter.size() -1 ; i++) {
			int value = map.get(tmpDiameter.get(i));
			if (value > maxValue) {
				pos = i;
			}
		}
		if (pos != -1) {
			int j = tmpDiameter.size() -1;
			for (int i = pos; i < tmpDiameter.size() -1; i++) {
				if (i < j) {
					TreeNode tmp = tmpDiameter.get(j);
					tmpDiameter.set(j, tmpDiameter.get(i));
					tmpDiameter.set(i, tmp);
				}
				j--;
			}
		}
		//printList(diameterData.rightSubPath);
//		List<TreeNode> tmpRightSub = diameterData.rightSubPath;
//		// 0 1 2 3 4 5 6
//		//           0 1
//		for (int i = 0; i < tmpRightSub.size() - 1; i++) {
//			int j = tmpDiameter.size() - 1 - i;
//			tmpDiameter.set(j, tmpRightSub.get(i));
//		}
		return tmpDiameter;
	}

	public static Map<TreeNode, Integer> map = new TreeMap<TreeNode, Integer>();
	public static DiameterData findPath(TreeNode root) {
		if (root == null) {
			return new DiameterData(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
		}
		DiameterData left = findPath(root.left);
		DiameterData right = findPath(root.right);

		int leftMaxPsize = left.maxPath.size();
		int rightMaxPSize = right.maxPath.size();
		int leftMaxSubSize = left.diameter.size();
		int rightMaxSubSize = right.diameter.size();
		int maxSubSize = Math.max(leftMaxPsize + rightMaxPSize + 1, Math.max(leftMaxSubSize, rightMaxSubSize));

		// list of TreeNodes on the diameter for the subtree rooted at current TreeNode: root
		List<TreeNode> diameter = new ArrayList<TreeNode>();
		if (leftMaxSubSize == maxSubSize) {
			diameter = left.diameter;
		} else if (rightMaxSubSize == maxSubSize) {
			diameter = right.diameter;
		} else {
			diameter.addAll(left.maxPath);
			diameter.add(root);
			diameter.addAll(right.maxPath);
		}

		// list TreeNodes on the longest path from leaf to current TreeNode: root
		List<TreeNode> maxPath = new ArrayList<TreeNode>();
		if (leftMaxPsize > rightMaxPSize)
			maxPath.addAll(left.maxPath);
		else
			maxPath.addAll(right.maxPath);
		maxPath.add(root);
		map.put(root, maxPath.size());
		return new DiameterData(maxPath, diameter);
	}
	/*
	 *          1
	 *       2      3
	 *    4     5      6
	 *   7  8     9
	 *  10     11
	 *            12
	 */
	public static List<TreeNode> reverseList(List<TreeNode> list) {
		List<TreeNode> newList = new ArrayList<TreeNode>();
		for (int i = list.size() - 1; i >= 0; i--) {
			newList.add(list.get(i));
		}
		return newList;
	}
}

