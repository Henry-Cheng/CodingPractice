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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        // 1 (head)  2 (l1) 4
        // 1 (l2)    3      4
        ListNode head = null;
        if (l1.val <= l2.val) {
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }
        ListNode fixHead = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                ListNode tmp1 = l1.next;
                head.next = l1;
                l1 = tmp1;
                head = head.next;
            } else {
                ListNode tmp2 = l2.next;
                head.next = l2;
                l2 = tmp2;
                head = head.next;
            }
        }
        if (l1 == null) {
            head.next = l2;
        } else if (l2 == null) {
            head.next = l1;
        }
        return fixHead;
    }
}