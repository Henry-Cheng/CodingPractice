// https://leetcode.com/problems/remove-linked-list-elements/
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
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode headAnchor = dummyHead;
        while(dummyHead.next != null) {
            ListNode next = dummyHead.next;
            if (next.val != val) {
                dummyHead = next;
                next = next.next;
            } else {
                dummyHead.next = next.next;
            }
        }
        return headAnchor.next;
    }
}