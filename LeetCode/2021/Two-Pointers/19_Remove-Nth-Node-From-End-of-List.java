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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // corner case1: n == sz, e.g. [1], 1, e.g. [1,2], 2
        ListNode left = head;
        ListNode right = head;
        for(int i = 0; i < n; i++) {
            right = right.next;
        }
        if(right == null) {
            return head.next;
        }
        // corner case2: [1,2], 2
        
        // 1 2 3 4 5 --> n ==3
        // l     r
        // move l and r to end, then l is the nth node
        while(right.next != null) {
            left = left.next;
            right = right.next;
        }
        // 1 2 3 4 5 --> n ==3
        //   l     r.next == null
        // now remove l.next
        left.next = left.next.next;
        return head;
    }
}