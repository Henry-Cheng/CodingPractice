// https://leetcode.com/problems/add-two-numbers-ii/submissions/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new LinkedList<>();
        Deque<ListNode> stack2 = new LinkedList<>();
        while (l1 != null) {
            stack1.push(l1);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2);
            l2 = l2.next;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode pointer = dummyHead;
        int carryOver = 0;
        while(!stack1.isEmpty() || !stack2.isEmpty()) {
            if (stack1.isEmpty()) {
                while(!stack2.isEmpty()) {
                    ListNode node = stack2.pop();
                    int sum = node.val + carryOver;
                    node.val = sum % 10;
                    carryOver = sum / 10;
                    pointer.next = node;
                    pointer = pointer.next;
                    pointer.next = null;
                }
            } else if (stack2.isEmpty()) {
                while(!stack1.isEmpty()) {
                    ListNode node = stack1.pop();
                    int sum = node.val + carryOver;
                    node.val = sum % 10;
                    carryOver = sum / 10;
                    pointer.next = node;
                    pointer = pointer.next;
                    pointer.next = null;
                }  
            } else {
                ListNode node1 = stack1.pop();
                ListNode node2 = stack2.pop();
                int sum = node1.val + node2.val + carryOver;
                node1.val = sum % 10;
                carryOver = sum / 10;
                pointer.next = node1;
                pointer = pointer.next;
                pointer.next = null;
            }
        }
        if (carryOver > 0) {
            pointer.next = new ListNode(carryOver);
        }
        return reverseList(dummyHead.next);
    }
    
    // 1--> 2 --> 3
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while(head.next != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        head.next = prev;
        return head;
    }
}