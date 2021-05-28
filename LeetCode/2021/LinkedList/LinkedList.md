## LinkedList
### Default
#### [LC] 2. Add Two Numbers
https://leetcode.com/problems/add-two-numbers/

Be careful on the corner case, like carryOver may result in a new node.


#### [LC] 24. Swap Nodes in Pairs
https://leetcode.com/problems/swap-nodes-in-pairs/

Simple version is to swithc node value, harder version is to switch nodes, remember to add preHead node, super version is to do it recursively.


#### [LC] 21. Merge Two Sorted Lists
https://leetcode.com/problems/merge-two-sorted-lists/

Just find the head node first, then add node in l1 or l2 sequencially (compare their val) to the head.next.


#### [LC] 109. Convert Sorted List to Binary Search Tree
https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/solution/

#### [LC] 203. Remove Linked List Elements
https://leetcode.com/problems/remove-linked-list-elements/

#### [LC][Medium] 445. Add Two Numbers II
https://leetcode.com/problems/add-two-numbers-ii/

Use stack to reverse list and finally reverse it back.

```java
    // 1--> 2 --> 3
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while(head.next != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        head.next = prev;
        return head;
    }
```