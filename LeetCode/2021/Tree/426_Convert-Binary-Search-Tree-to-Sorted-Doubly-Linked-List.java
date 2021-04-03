// https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/

class Solution {
    
    public Node prev;
    
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        } else if (root.left == null && root.right == null) {
            root.left = root;
            root.right = root;
            return root;
        }
        Node dummyHead = new Node(-1);
        prev = dummyHead;
        inOrder(root);
        // now prev is at bottom, need to connect prev and dummyHead
        // dummyHead <--> 1 <--> 2 <--> prev(3) --> null
        
        Node realHead = dummyHead.right;
        prev.right = realHead;
        realHead.left = prev;
        return realHead;
    }
    
    public void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        root.left = prev;
        //System.out.println(prev.val + "<--" + root.val);
        if (prev != null) {
            prev.right = root;
            //System.out.println(prev.val + "-->" + root.val);
        }
        prev = root;
        //System.out.println("now prev is " + prev.val);
        inOrder(root.right);
    }
}