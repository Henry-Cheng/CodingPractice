

// Definition for singly-linked list.
  class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
 
public class LinkedListToBST {
	
	public static ListNode listHead = null;
	
	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4, 5, 6, 7};
		ListNode head = arrayToList(a);
		TreeNode root = sortedListToBST(head);
		printTreeInOrder(root);
	}
	
    public static TreeNode sortedListToBST(ListNode head) {
        int len = 0;
        ListNode traverse = head;
        while (traverse != null) {
            len++;
            traverse = traverse.next;
        }
        listHead = head; // we need a global variable. if it is C++, then the pointer in a stack would be changed too
        return buildTree(len);
    }
    
    public static TreeNode buildTree(int n) { // n is No. of nodes in list
        if (n <= 0) {
            return null;
        }
        TreeNode left = buildTree(n/2);
        TreeNode parent = new TreeNode(listHead.val);
        parent.left = left;
        listHead = listHead.next;
        parent.right = buildTree(n - n/2 -1); //NOTE: 1/2 is 0 but 3/2 is 2
        return parent;
    }
    
    public static ListNode arrayToList(int[] a) {
		ListNode head = new ListNode(a[0]);
		ListNode current = head;
		for (int i = 1; i < a.length; i++) {
			ListNode next = new ListNode(a[i]);
			current.next = next;
			current = current.next;
		}
		return head;
    }
    
    public static void printTreeInOrder(TreeNode root) {
    	if (root == null) {
    		return;
    	}   	
    	printTreeInOrder(root.left);
    	System.out.print(root.val + " ");
    	printTreeInOrder(root.right);
    }
}