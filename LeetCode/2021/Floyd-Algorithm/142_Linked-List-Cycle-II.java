// https://leetcode.com/problems/linked-list-cycle-ii/
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
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // phase 1, find entrance of the cycle and detects the cycle
        ListNode fast = head;
        ListNode slow = head;
        do {
            if (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            } else {
                return null;
            }
        } while(fast != slow);
        // if we hit her then there is cycle, we can find it in phase 2
        // let slow pointer starts from beginning, fast pointer starts from entrance of cycle
        slow = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // or fast
    }
}