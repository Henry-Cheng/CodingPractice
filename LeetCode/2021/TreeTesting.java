import java.util.*;
import java.lang.Math;

public class HelloWorld{

     public static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }
 
     public static class TreeNodeWithRow {
        public TreeNode node;
        public int row;
        public TreeNodeWithRow(TreeNode node, int row) {
            this.node = node;
            this.row = row;
        }
    }
    
    public static List<Integer> largestValues(TreeNode root) {
        // it's like BFS, find node at each row and store the largest one
        // corner case
        if (root == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        
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
            int maxVal = Math.max(node.val, currentValInRow);
            System.out.println("set maxVal: " + maxVal + " at row " + row);
            result.set(row, maxVal);//0->1,1->3,2->9
            if (node.left != null) {
                queue.offer(new TreeNodeWithRow(node.left, ++row));//3-1//2-1,5-2
            }
            if (node.right != null) {
                queue.offer(new TreeNodeWithRow(node.right, ++row));//3-1, 2-1//2-1,5-2,3-2//5-2,3-2,9-2
            }
        }
        
        // return result
        return result;
    }
    
     public static void main(String []args){
         List<Integer> list = new LinkedList<>();
         System.out.println(list.size());
         
        System.out.println("Hello World");
        
        TreeNode node3 = new TreeNode(3,null,null);
        TreeNode node5 = new TreeNode(5,null,null);
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node6 = new TreeNode(6,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node0 = new TreeNode(0,null,null);
        TreeNode node8 = new TreeNode(8,null,null);
        TreeNode node7 = new TreeNode(7,null,null);
        TreeNode node4 = new TreeNode(4,null,null);
        
        node3.left = node5;
        node3.right = node1;
        node5.left = node6;
        node5.right = node2;
        node2.left = node7;
        node2.right = node4;
        node1.left = node0;
        node1.right = node8;
        
        TreeNode result = lca(node3, node5, node4);
        System.out.println(result);
        
        System.out.println(result.val);
     }
     
    public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        Evaluation e = postOrder(root, p, q);
        return e.parent;
    }
    
    public static Evaluation postOrder(TreeNode root, TreeNode p, TreeNode q) {
        Evaluation e = new Evaluation();
        if (root == null) {
            return e;
        }
        if (root == p || root == q) {
            e.found = 1;
        }
        
        Evaluation leftE = postOrder(root.left, p, q);
        Evaluation rightE = postOrder(root.right, p, q);

        System.out.println("Visit " + root.val + ", left found is " + leftE.found + ", right found is " + rightE.found);

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
        
        if (leftE.found == 1 || rightE.found == 1) {
            if (e.found == 1) {
                e.found++;
                e.parent = root;
            }
        }

        // visit root
        System.out.println("Visit " + root.val + ", found " + e.found + ", parent is " + e.parent);
        return e;
    }
    
    public static class Evaluation {
        public int found = 0;
        public TreeNode parent = null;
        public Evaluation() {
        }
    }
}