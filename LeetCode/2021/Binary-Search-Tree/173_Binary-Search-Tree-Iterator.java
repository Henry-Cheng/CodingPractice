// https://leetcode.com/problems/binary-search-tree-iterator/
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
import java.util.ArrayList;

class BSTIterator {
    Deque<TreeNode> stack = null;
    public BSTIterator(TreeNode root) {
        stack = new LinkedList<>();
        // push all left nodes to stack
        TreeNode node = root;
        while(node != null) {
            stack.push(node);
            node = node.left;
        }
    }   
    public int next() {
        TreeNode next = stack.pop();
        if (next.right != null) { 
            TreeNode node = next.right; // push all left of next.right to stack
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return next.val;
    } 
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}

// class BSTIterator {

//     int pointer;
//     ArrayList<Integer> list = null;
    
//     public BSTIterator(TreeNode root) {
//         this.pointer = 0;
//         this.list = new ArrayList<Integer>();
//         traverseTree(root);
//     }
    
//     public int next() {
//         if (this.pointer > list.size() - 1) {
//             return 0; // this would not happen per question
//         } else {
//             Integer val = this.list.get(this.pointer);
//             this.pointer++;
//             return val;
//         }
//     }
    
//     public boolean hasNext() {
//         return this.pointer <= this.list.size() - 1;
//     }
    
//     private void traverseTree(TreeNode root) {
//         if (root == null) {
//             return;
//         }
//         traverseTree(root.left);
//         this.list.add(root.val);
//         traverseTree(root.right);
//     }
// }

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */