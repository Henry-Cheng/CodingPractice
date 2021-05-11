## Floyd's Algorithm
### Default

```java
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // phase 1: 
        // detects the cycle by finding intersection of fast and slow
        // fast pointer traverss 2x faster than slow pointer
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

        // if we hit here then there is a cycle, we can find cycle entrance in phase 2
        // phase2:
        // let slow pointer starts from beginning, fast pointer starts from intersection 
        // they traverse in same speed
        slow = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // or fast
    }
```

#### [LC] 141. Linked List Cycle
https://leetcode.com/problems/linked-list-cycle/

Using Floyd's algorithm template, since we only need to tell if there is a cycle or not, we can simply using phase 1 to tell it.

#### [LC] 142. Linked List Cycle II
https://leetcode.com/problems/linked-list-cycle-ii/

Using Floyd's algorithm template, using 2 phase to find the cycle entrance.

#### [LC] 287. Find the Duplicate Number
https://leetcode.com/problems/find-the-duplicate-number/

Also using the Floyd's algorithm, using fast pointer (or hare pointer) 2x faster like `hare = nums[nums[hare]]`, and slow pointer (or tortois pointer) in normal speed like `tortois = nums[tortois]`. Then using 2-phase to find the entrance of cycle.