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
        // corner case
        if (head == null || head.next == null) {
            return head;
        }
        // traverse from beginning
        ListNode preHead = new ListNode(-1,head);
        ListNode preLeft = preHead;
        ListNode left = head;
        ListNode right = head.next;
        boolean beginning = true;
        while(true) {
            // swap left and right node
            // preLeft --> left --> right --> next
            left.next = right.next; // preLeft --> left --> next, right --> next
            preLeft.next = right; // preLeft --> right --> next, left --> next
            right.next = left; // preLeft --> right --> left --> next
            
            ListNode tmp = left;
            left = right;
            right = tmp; // preLeft --> left --> right --> next
            
            if (beginning) {
                preHead = preLeft;
                beginning = false;
            }
            
            // move left and right pointer
            if (right.next != null && right.next.next != null) {
                preLeft = preLeft.next.next;
                left = left.next.next;
                right = right.next.next;
            } else {
                break;
            }
        }
        return preHead.next;
    }
    
    public ListNode swapPairsByChangingValue(ListNode head) {
        // corner case
        if (head == null || head.next == null) {
            return head;
        }
        // traverse from beginning
        ListNode left = head;
        ListNode right = head.next;
        while(true) {
            // swap left and right val
            int tmp = left.val;
            left.val = right.val;
            right.val = tmp;
            // move left and right pointer
            if (right.next != null && right.next.next != null) {
                left = left.next.next;
                right = right.next.next;
            } else {
                break;
            }
        }
        return head;
    }
}