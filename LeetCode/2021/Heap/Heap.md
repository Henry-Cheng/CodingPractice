## Heap
### Default


#### [LC] 23. Merge k Sorted Lists
https://leetcode.com/problems/merge-k-sorted-lists/

heap

#### [LC] 215. Kth Largest Element in an Array
https://leetcode.com/problems/kth-largest-element-in-an-array/

min heap --> top/first is the minimum --> so ascending in list

#### [LC] 347. Top K Frequent Elements
https://leetcode.com/problems/top-k-frequent-elements/

Very classical heap use!!! 

Using hashmap to counter unique num, then using min-heap to keep top-k frequence num.

```java
        PriorityQueue<Map.Entry<Integer,Integer>> heap = new PriorityQueue<>((a,b)->{
            return a.getValue() - b.getValue(); // ascending, min-heap
        });
```

#### [LC][Hard] 480. Sliding Window Median
https://leetcode.com/problems/sliding-window-median/

This is a harder version of `[Hard] 295. Find Median from Data Stream`, since here we need to remove nums when window moves.  

But, it's not that hard as I think actually, don't be panic.  

We can use the same way as in `[Hard] 295. Find Median from Data Stream`,  by maintaining 2 heaps for half-larger and half-smaller.  
We just need to remove the num from heap when window moves, and we can use this simple way:  

```java
pq.remove(numToBeRemoved);
```

It is linear time!!! But it is really simple!!!

Some other tricks here:  
1. Separate the functions to make the code simpler

```java
private void add(int num, PriorityQueue<Integer> low, PriorityQueue<Integer> high)
private void remove(int num, PriorityQueue<Integer> low, PriorityQueue<Integer> high)
private void balance(PriorityQueue<Integer> low, PriorityQueue<Integer> high) 
private double getMedian(PriorityQueue<Integer> low, PriorityQueue<Integer> high)
```

2. Be careful that when adding num to heaps, there are 3 scenarios (if no considering the empty heap scenario):
```java
if (num <= low.peek()) {
    low.offer(num);
} else if (num >= high.peek()) {
    high.offer(num);
} else { // add to any, we will rebalance it, e.g. low=[1,2], high=[3,4]
    high.offer(num);
}
```

3. Be careful on the boundary on heap definition:

If we defin heap in this way, the when a or b is MAX_VALUE and MIN_VALUE, it would be wrong.

```java
PriorityQueue<Integer> low = new PriorityQueue<Integer>((a,b)->{return b-a;});
PriorityQueue<Integer> high = new PriorityQueue<Integer>((a,b)->{return a-ab;});
```

it is better to use this way:

```java

PriorityQueue<Integer> low = new PriorityQueue<Integer>(Collections.reverseOrder());
PriorityQueue<Integer> high = new PriorityQueue<Integer>();
```

4. Be careful on the boundary when calcualte mean value:

```java
// using this 
(double)low.peek()/2 + (double)high.peek()/2;
// DO NOT use this!!
(double)(low.peek() + high.peek())/2;
```

#### [LC] 767. Reorganize String
https://leetcode.com/problems/reorganize-string/

```python
        // aaaccc --> acacac
        // aaacccc --> cacacac
        // aaaccccc --> ""
        // find most common letter, then split it by rest of the letters
        // if difference >= 2, not possible
        
        // option1: count all unique letters (O(N)), sort them (O(MlogM) if M is the # of unique letters) , then asembling string, total time complexity is O(N + MlogM)
        // option2: count and sort all unique letters by TreeMap (O(NlogM)), then asembling string
        // option3: count all unique letters (O(N)), building max-heap to maintain the max at heap top (O(MlogM)); when asembling string, we will do N times pulling from heap, and each pulling takes O(logM) for the heap to reorganize, so the asembling time is O(NlogM).
        // option4: bucket sort, maintain a 500 list of characters
        // since we would have most have 26 characters, so the M could be fixed and at most 26, which makes all the options close to O(N)
```

NOTE:  
PriorityQueue can support `Map.Entry` like:
```
        PriorityQueue<Map.Entry<Character,Integer>> pq = new PriorityQueue<>((a,b) -> {
            return b.getValue() - a.getValue(); // descending
        });
```

Then traverse map like:
```
for (Map.Entry<Character, Integer> entry : map.entrySet()) {}
```

#### [LC] 973. K Closest Points to Origin
https://leetcode.com/problems/k-closest-points-to-origin/

Be careful when traverse heap, do not use `for (int i=0; i<heap.size();i++)`, since after you do `heap.poll()` the `heap.size()` would change -- set the heap size before the for loop!!!
