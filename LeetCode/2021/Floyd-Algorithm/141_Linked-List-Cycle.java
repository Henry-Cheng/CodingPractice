// https://leetcode.com/problems/linked-list-cycle/
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) { // no node or only one node
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        do {
            if (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            } else {
                return false;
            }
        } while(fast != slow);
        // now find intersection, it means there is a cycle
        return true;
    }
}