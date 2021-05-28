## Recursion
### Default
https://www.jianshu.com/p/1395fae8a1ae
How to think about recursion algorithm?

1) Find termination condition  
e.g.  
 - for LC 24 "Swap Nodes in Pairs", the termination condition is when "there is no node to swap"
 - for LC 110 "Balanced Binary Tree", the termination condition is when "root is null so that we cannot see child tree", or we can say "root is null so that it is a balanced tree already"

2) Find return value  
e.g.  
- for LC 24 `Swap Nodes in Pairs`, the return value is a linked list that is already swapped.
- for LC 110 "Balanced Binary Tree", the return value needs to be both a boolean value isBalanced and an int value about the height, so that we can build a new class to handle it

3) What to do at current recursion level  
- for LC 24 "Swap Nodes in Pairs", the work at current level is to swap top two nodes
- for LC 110 "Balanced Binary Tree", the work at current level is to check left subtree and right subtree; if they are both balanced, check their height difference is less than 1, then the whole root tree is balanced


#### [LC] 17. Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/

Be careful when using `substring(startIndexInclusive, endIndexExclusive)`.
This can also be a backtracking problem which is faster.

#### [LC] 24. Swap Nodes in Pairs
https://leetcode.com/problems/swap-nodes-in-pairs/

Handle the first 2 nodes, call recursion function on 3rd ndoe, then point 1st node next to recursion result of 3rd node.  


#### [LC] 46. Permutations
https://leetcode.com/problems/permutations/

Explicitly define LinkedList instead of List, since generic List would loose the ability to `addFirst` and `addLast`. Just remember to add LinkedList back to list like
```
return result.stream().map(linkedList -> linkedList).collect(Collectors.toList());
```

List can do `remove(indexToRemove)` to remove element.

If you want to remove element of list iteratively, the traverse from end to start.

Be careful on Java pass-by-reference: when remove element from list, if the other rounds need to use the same list, you need to copy the list to new space and remove it there

#### [LC] 83. Remove Duplicates from Sorted List
https://leetcode.com/problems/remove-duplicates-from-sorted-list/



#### [LC] 206. Reverse Linked List
https://leetcode.com/problems/reverse-linked-list/

The magic part is:
```java
        ListNode node = reverseList(head.next);// now head.next is at the end
        head.next.next = head; // the magic part
        head.next = null;
```

#### [LC] 247. Strobogrammatic Number II
https://leetcode.com/problems/strobogrammatic-number-ii/

This question could also use DP, but DP is not space efficient since we don't need to keep the result from i to n -1.

There are two trick here: 
1. there are 2 initial states:
  - n == 0, result = {""}
  - n == 1, result = {"0", "1", "8"}
2. if n is even, it starts building from `n==0`, by adding "0"+num+"0", "1"+num+"1", "8"+num+"8", "6"+num+"9", "9"+num+"6"
3. if n is odd, it starts building from `n==1`
4. at each recursion, check "should we add 0 around the num"? --> only the top function does not add zero around, all the inner nums would need to add zero around

NOTE1:  
- `list = Arrays.asList("0", "1", "8")` is allowed
- `String[] arr = {"0", "1", "8"}; list = Arrays.asList(arr)` is allowed
- `int[] nums = new int[]{num1, num2}` is allowed
- `Arrays.asList({"0", "1", "8"})` is NOT allowed!!!!!!!

NOTE2:
- string plus is good enough!! do not always use stringBuilder
  - `String s = s1 + s2` would be translated inside JVM to be `String s = (new StringBuffer()).append(s1).append(s2).toString()`
- https://stackoverflow.com/questions/10078912/best-practices-performance-mixing-stringbuilder-append-with-string-concat


#### [LC][Medium] 394. Decode String
https://leetcode.com/problems/decode-string/

This problem is not easy...  

It can use the same idea as `[Hard] 772. Basic Calculator III`, whenever seeing left bracket "[", find the corresponding right bracket and recusively do it.

#### [LC] 1137. N-th Tribonacci Number
https://leetcode.com/problems/n-th-tribonacci-number/

Be careful to store result that has already been calculated to save effort on re-calculation.


#### [LC] 1849. Splitting a String Into Descending Consecutive Values
https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/

This is one the medium question in LeetCode weekly contest 239: https://leetcode.com/discuss/general-discussion/1186784/weekly-contest-239

The idea is simple: for each possible initial num (traversing from left to right), using recusion to check if num-1, num-2, num-3, ... exists in substring.  

The reason I fail in the contest is that I forgot to set the num to be `long` since it would overflow integer....

It is `O(N^2)`, since we can find up to `n-1` initial num, and for each num with `k` digits we check the next `n-k` num, which result in `(n-1) + (n-2) + (n-3) + ... + 1 = n^2`.

