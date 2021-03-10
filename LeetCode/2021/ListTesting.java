import java.util.*;
import java.lang.Math;

public class HelloWorld{

 public static class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
 
     public static void main(String []args){
         List<Integer> list = new LinkedList<>();
         System.out.println(list.size());
         
        System.out.println("Hello World");
        ListNode head = new ListNode(3);
        ListNode last = head;
        last.next = new ListNode(3);
        last = last.next;
        last.next = new ListNode(3);
        last = last.next;

        ListNode head2 = new ListNode(4);
        last = head2;
        last.next = new ListNode(5);
        last = last.next;
        last.next = new ListNode(9);
        last = last.next;
        
        ListNode newHead = addTwoNumbers(head, head2);
        while(newHead != null) {
            System.out.println(newHead.val);
            newHead = newHead.next;
        }
     }
     
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
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
        System.out.println("stack1 size: " + stack1.size());
        System.out.println("stack1 peek: " + stack1.peek());
        while(l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        System.out.println("stack2 size: " + stack2.size());
        System.out.println("stack2 size: " + stack2.peek());
        ListNode head = null;
        ListNode last = null;
        int carryOver = 0;
        while(stack1.peek() == null && stack2.peek() == null) {
            System.out.println("here");
            int val1 = stack1.peek() != null ? stack1.pop() : 0;
            int val2 = stack2.peek() != null ? stack2.pop() : 0;
            System.out.println("val1: " + val1);
            System.out.println("val2: " + val2);
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
        return head;
    }
}