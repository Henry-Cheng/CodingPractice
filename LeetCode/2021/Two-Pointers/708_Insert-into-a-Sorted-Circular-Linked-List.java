// https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/submissions/
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }
};
*/

class Solution {
    public Node insert(Node head, int insertVal) {
        if (head == null) { // no node
            head = new Node(insertVal);
            head.next = head;
            return head;
        } else if (head.next == head) { // 1 node
            head.next = new Node(insertVal);
            head.next.next = head;
            return head;
        }
        // now head has more than 2 nodes
        // NOTE: head is in ascending order, but could has duplicate
        Node originalHead = head;
        HashSet<Node> visited = new HashSet<>();
        boolean inserted = false;
        Node lastMaxNode = head; 
        while(!visited.contains(head.next)) {
            if (head.val >= lastMaxNode.val) {
                lastMaxNode = head;
            }
            visited.add(head.next);
            if (insertVal >= head.val && insertVal <= head.next.val) { // found the place to insert
                Node next = head.next;
                head.next = new Node(insertVal);
                head.next.next = next;
                inserted = true;
                break;
            } else {
                head = head.next; // move to next
            }
        }
        //  1
        //  ^
        //  |
        //  4  <- 3
        // inserted: 5
        if (!inserted) { // not found places to insert, the insert value should be either smallest or largest, we can simply insert it to the next of lastMaxNode
            Node next = lastMaxNode.next;
            lastMaxNode.next = new Node(insertVal);
            lastMaxNode.next.next = next;
        }
        return originalHead;
    }
}