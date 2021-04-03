// https://leetcode.com/problems/add-two-numbers/
import java.lang.Math;
import java.util.Deque;
import java.util.LinkedList;
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
        ListNode dummyHead = new ListNode(0);
        ListNode pointer = dummyHead;
        int carryOver = 0;
        while(l1 != null || l2 != null) {
            if (l1 == null) {
                while(l2 != null) {
                    int num = l2.val + carryOver;
                    l2.val = num % 10;
                    carryOver = num / 10;
                    pointer.next = l2;
                    l2 = l2.next;
                    pointer = pointer.next;
                    pointer.next = null;
                }
            } else if (l2 == null) {
                while(l1 != null) {
                    int num = l1.val + carryOver;
                    l1.val = num % 10;
                    carryOver = num / 10;
                    pointer.next = l1;
                    l1 = l1.next;
                    pointer = pointer.next;
                    pointer.next = null;
                } 
            } else {
                int num = l1.val + l2.val + carryOver;
                l1.val = num % 10;
                carryOver = num / 10;
                pointer.next = l1;
                l1 = l1.next;
                l2 = l2.next;
                pointer = pointer.next;
                pointer.next = null;
            }
        }
        if (carryOver > 0) {
            pointer.next = new ListNode(carryOver);
        }
        return dummyHead.next;
    }
    
    public ListNode addTwoNumbers_2021_03_30(ListNode l1, ListNode l2) {
        // [2,1,3]
        // [3,2,6]
        //  5 3 9
        ListNode head = null;
        ListNode last = null;
        int carryOver = 0;
        while(!(l1 == null && l2 == null)) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            
            int sum = val1 + val2 + carryOver;
            int remain= sum % 10;
            carryOver = (int) Math.floor( sum / 10);
            ListNode node = new ListNode(remain); // 
            if (head == null) {
                head = node;
                last = head;
            } else {
                last.next = node;
                last = last.next;
            }
        }
        if (carryOver != 0) {
            last.next = new ListNode(carryOver); 
        }
        return head;
    }
    
    public ListNode addTwoNumbersInReverseOrder(ListNode l1, ListNode l2) {
        // [9,9,9,9,9,9,9]
        //       [9,9,9,9]
        //1,0,0,0,9,9,9,8 --> 89990001
        // maintain stack for both list, then add up, and link from stack top to bottom
        Deque<Integer> stack1 = new LinkedList<>();
        Deque<Integer> stack2 = new LinkedList<>();
        while(l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while(l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        ListNode head = null;
        ListNode last = null;
        int carryOver = 0;
        while(!(stack1.peek() == null && stack2.peek() == null)) {
            int val1 = stack1.peek() != null ? stack1.pop() : 0;
            int val2 = stack2.peek() != null ? stack2.pop() : 0;
            int sum = val1 + val2 + carryOver;
            int remain= sum % 10;
            carryOver = (int) Math.floor( sum / 10);
            ListNode node = new ListNode(remain);
            if (head == null) {
                head = node;
                last = head;
            } else {
                last.next = node;
                last = last.next;
            }
        }
        if (carryOver != 0) {
            last.next = new ListNode(carryOver); 
        }
        return head;
    }
}