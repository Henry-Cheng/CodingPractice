// https://leetcode.com/problems/swap-nodes-in-pairs/
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
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // for every two nodes, we do the swap, and the 3rd node would be put into recursion
        // 1st = head, 2nd = head.next, 3rd = head.next.next
        ListNode first = head;
        ListNode second = head.next;
        ListNode third = head.next.next;
        first.next = swapPairs(second.next);
        second.next = first;
        return second;
    }
    
}