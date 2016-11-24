
import java.util.*;
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class LCA {
    public static TreeNode ancester = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val == q.val) {
            return p;
        }
        if (root == null) {
            return null;
        }
        TreeNode min = p.val < q.val ? p : q;
        TreeNode max = p.val < q.val ? q : p;
        postOrder(root, min, max);
        return ancester;
    }
    
    public static void postOrder(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return;
        }
        postOrder(root.left, min, max);
        postOrder(root.right, min, max);
        if(root.val <= max.val && root.val > min.val) {
            if (ancester == null) {
                ancester = root;
            }
        }
    }

}