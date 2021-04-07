// https://leetcode.com/problems/copy-list-with-random-pointer/submissions/
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    HashMap<Node, Node> oldNodeToNewNode = new HashMap<>();
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // traverse once to create all nodes
        Node originalHead = head;
        Node newHead = copyNode(head);
        // now assign random node
        assignRandom(head, newHead); 
        return newHead;
    }
    
    private Node copyNode(Node head) {
        if (head == null) {
            return null;
        }
        Node newHead = new Node(head.val);
        oldNodeToNewNode.put(head, newHead);
        newHead.next = copyNode(head.next);
        return newHead;
    }
    
    private void assignRandom(Node head, Node newHead) {
        if (head == null) {
            return;
        }
        if (head.random == null) {
            newHead.random = null;
        } else {
            newHead.random = oldNodeToNewNode.get(head.random);
        }
        assignRandom(head.next, newHead.next);
    }
}