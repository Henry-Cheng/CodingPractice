// https://leetcode.com/problems/binary-search-tree-iterator-ii/
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
class BSTIterator {

    Deque<TreeNode> stack = null;
    List<TreeNode> alreadyTraversed = null;
    Integer pointer = -1; // points to current value

    public BSTIterator(TreeNode root) {
        stack = new LinkedList<>();
        alreadyTraversed = new ArrayList<>();
        
        TreeNode node = root;
        while(node != null) {
            stack.push(node);
            node = node.left;
        }
    }
    
    public boolean hasNext() {
        return !stack.isEmpty() || pointer < alreadyTraversed.size() - 1;
    }
    
    public int next() {
        //System.out.println("call next(), now array is " + alreadyTraversed.size() + ", pointer is " + pointer);
        if (pointer >= 0 && pointer < alreadyTraversed.size() - 1) {
            pointer++;
            //System.out.println("call next(), only in array, array is " + alreadyTraversed.size() + ", pointer is " + pointer);
            return alreadyTraversed.get(pointer).val;
        } else { // pointer==-1, or pointer already at the end of array
            TreeNode next = stack.pop();
            //System.out.println("pop " + next.val);
            if (next.right != null) {
                TreeNode node = next.right;
                while(node != null) {
                    //System.out.println("push " + node.val);
                    stack.push(node);
                    node = node.left;
                }
            }
            alreadyTraversed.add(next);
            pointer = alreadyTraversed.size() - 1;
            //System.out.println("call next(), stack handling, array is " + alreadyTraversed.size() + ", pointer is " + pointer);
            return next.val;
        }
    }
    
    public boolean hasPrev() {
        return pointer > 0;
    }
    
    public int prev() {
        pointer--;
        //System.out.println("call prev(), move pointer to left, now it is " + pointer);
        return alreadyTraversed.get(pointer).val;
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * boolean param_1 = obj.hasNext();
 * int param_2 = obj.next();
 * boolean param_3 = obj.hasPrev();
 * int param_4 = obj.prev();
 */