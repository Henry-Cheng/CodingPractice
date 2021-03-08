import java.util.*;

public class MergeKList {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(2);
		head1.next.next = new ListNode(2);
		
		ListNode head2 = new ListNode(1);
		head2.next = new ListNode(1);
		head2.next.next = new ListNode(2);
		
		ListNode[] lists = new ListNode[]{head1, head2};
		ListNode result = new MergeKList().mergeKLists(lists);
	}
	
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        // create priority queue
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, new Comparator<ListNode>() {
            public int compare(ListNode node1, ListNode node2) {
                return node1.val - node2.val; // ascending order/min-heap
            }
        });
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                queue.add(lists[i]);
            }
        }
        ListNode head = new ListNode(0); // dummy head
        ListNode current = head;
        while(!queue.isEmpty()) {
            ListNode node = queue.poll();
            current.next = node;
            current = current.next;
            if (node.next != null) {
                queue.offer(node.next);
            }
        }
        return head.next;
    }
}