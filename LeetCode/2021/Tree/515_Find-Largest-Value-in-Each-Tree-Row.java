// https://leetcode.com/problems/find-largest-value-in-each-tree-row/
import java.lang.Math;

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
    public List<Integer> largestValues(TreeNode root) {
        // it's like BFS, find node at each row and store the largest one
        // corner case
        List<Integer> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        // do BFS, and record the row at each round
        Deque<TreeNodeWithRow> queue = new LinkedList<>();
        queue.offer(new TreeNodeWithRow(root, 0));// 1-0, 
        while(queue.size() > 0) {
            // poll one node from queue
            TreeNodeWithRow nodeWithRow = queue.poll();//1-0,3-1,2-1,5-2,3-2,9-2
            TreeNode node = nodeWithRow.node;//1,3,2,5,3,9
            int row = nodeWithRow.row;//0,1,1,2,2,2
            
            // compare the node val with existing max value in result per row
            if (result.size() <= row) {// 0<=0,1<=1,2>1,2<=2,3>2
                result.add(Integer.MIN_VALUE);//min//0->1,1->min//0->1,1->3,2->min
            }
            int currentValInRow = result.get(row);//min//min//3//min//5//5
            result.set(row, Math.max(node.val, currentValInRow));//0->1,1->3,2->9
            if (node.left != null) {
                queue.offer(new TreeNodeWithRow(node.left, row + 1));//3-1//2-1,5-2
            }
            if (node.right != null) {
                queue.offer(new TreeNodeWithRow(node.right, row + 1));//3-1, 2-1//2-1,5-2,3-2//5-2,3-2,9-2
            }
        }
        
        // return result
        return result;
    }
    
    public static class TreeNodeWithRow {
        public TreeNode node;
        public int row;
        public TreeNodeWithRow(TreeNode node, int row) {
            this.node = node;
            this.row = row;
        }
    }
}