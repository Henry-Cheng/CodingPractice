// https://leetcode.com/problems/symmetric-tree/
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
/**
         1
      2        2
    3    4   4    3
  5  6  7      7 6 5 
  
  536274    472635
**/
/**
   1
  2  2
2   2 
**/
class Solution {
    
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
        
    }
    
    public boolean isSymmetric(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        }
        if (n1 == null || n2 ==null) {
            return false;
        }
        if (isSymmetric(n1.left, n2.right) && isSymmetric(n1.right, n2.left)) {
            if (n1.val == n2.val) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isSymmetricInOrder(TreeNode root) {
        // termination condition
        if (root.left == null && root.right == null) {
            return true;
        }
        Deque<TreeNode> left = new LinkedList<>();
        inOrder(root.left, left, true);
        Deque<TreeNode> right = new LinkedList<>();
        inOrder(root.right, right, false);
        
        if (left.size() == right.size()) {
            while(!left.isEmpty()) {
                if (!compareTwoPointers(left.poll(),right.poll())) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    
    public void inOrder(TreeNode root, Deque<TreeNode> list, boolean addLast) {
        if (root == null) {
            return;
        }
        inOrder(root.left, list, addLast);
        if(addLast) {
            list.addLast(root);
        } else {
            list.addFirst(root);
        }
        inOrder(root.right, list, addLast);
    }
    
    public boolean compareTwoPointers(TreeNode left, TreeNode right) {
        Integer leftLeftVal = null;
        if (left.left != null) {
            leftLeftVal = left.left.val;
        }
        Integer leftRightVal = null;
        if (left.right != null) {
            leftRightVal = left.right.val;
        }
        Integer rightLeftVal = null;
        if (right.left != null) {
            rightLeftVal = right.left.val;
        }
        Integer rightRightVal = null;
        if (right.right != null) {
            rightRightVal = right.right.val;
        }
        if (leftLeftVal == rightLeftVal && leftRightVal == rightRightVal) {
            return true;
        } else {
            return false;
        }
    }    
}