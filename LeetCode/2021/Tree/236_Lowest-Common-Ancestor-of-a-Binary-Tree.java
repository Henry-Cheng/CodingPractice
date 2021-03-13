// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        } else if (left != null) { // then right must be null
            return left;
        } else if (right != null) { // then left must be null
            return right;
        } else {
            return null;
        }
    }
    
    // another solution below
    public Evaluation postOrder(TreeNode root, TreeNode p, TreeNode q) {
        Evaluation e = new Evaluation();
        if (root == null) {
            return e;
        }
        
        Evaluation leftE = postOrder(root.left, p, q);
        Evaluation rightE = postOrder(root.right, p, q);

        if (leftE.parent != null) {
            return leftE;
        }
        if (rightE.parent != null) {
            return rightE;
        }
        
        if (leftE.found == 1 && rightE.found == 1) {
            leftE.found++;
            leftE.parent = root;
            return leftE;
        }
        
        if (root == p || root == q) {
            e.found += 1;
        }
        
        if (leftE.found == 1 || rightE.found == 1) {
            e.found += 1;
        }
        
        if (e.found == 2) {
            e.found++;
            e.parent = root;
        }
        
        // visit root
        return e;
    }
    
    public static class Evaluation {
        public int found = 0;
        public TreeNode parent = null;
        public Evaluation() {
        }
    }
}