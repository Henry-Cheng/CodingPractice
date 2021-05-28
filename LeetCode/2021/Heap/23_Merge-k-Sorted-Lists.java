// https://leetcode.com/problems/merge-k-sorted-lists/
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
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<ListNode>((a,b)->{
            return a.val - b.val; // ascending, min heap
        });
        // initially put k top nodes into heap
        for(int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
            }
        }
        ListNode dummyHead = null;
        ListNode dummyTail = null;
        // poll and add new nodes
        while(!heap.isEmpty()) {
            // poll
            ListNode min = heap.poll();
            // offer
            if (min.next != null) {
                heap.offer(min.next);
            }
            // aggregate result
            if (dummyHead == null) {
                dummyHead = min;
                dummyTail = min;
            } else {
                dummyTail.next = min;
                dummyTail = dummyTail.next;
                dummyTail.next = null;
            }
        }
        return dummyHead;
    }
}