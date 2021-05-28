## Design
### Default
https://stackoverflow.com/questions/43145395/time-complexity-while-deleting-last-element-from-arraylist-and-linkedlist

| Operation                   | LinkedList  | AraryList  | 
| --------------------------- |:-----------:| ----------:|
| add(Integer obj)            | *O(1)*      | *O(1)*     | 
| add(int index, Integer obj) | O(N)        | O(N)       |
| contains(Integer obj)       | O(N)        | O(N)       |
| get(int index)              | O(N)        | *O(1)*     |
| set(int index, Integer obj) | O(N)        | *O(1)*     |
| remove(int index) -- last   | *O(1)*      | *O(1)*     |
| remove(int index) -- any    | O(N)        | O(N)       |
| remove(Integer obj)         | O(N)        | O(N)       |


Some tricks when using Collections:
1. remove range of list by `O(N)`
```java
arayList.subList(inclusiveIndex, exclusiveIndex).clear();
```

2. get max key of TreeMap --> by default TreeMap is asceding, so last is the max and first is min:  
```java
treeMap.lastKey(); // in ascending sorting, it is the max key
treeMap.lastEntry(); // max entry

treeMap.firstKey(); // min key
treeMap.firstEntry(); // min entry
```

some other usefule ones:

```java
c. TreeMap.ceilingKey(K key)或者TreeMap.ceilingEntry()
返回>=key的最小的key或者entry, 也就是找对应参数的天花板

d. TreeMap.floorKey(K key)或者TreeMap.floorEntry()
返回<=key的最大的key或者entry, 也就是找对应参数的地板

e. TreeMap.lowerKey(K key)或者TreeMap.lowerEntry()
返回 < key的最大key或者entry， 这个和floorKey的区别是floorKey的返回值可能和参数一样， lowerKey的话一定和参数不一样

f. TreeMap.higherKey(K key)或者TreeMap.higherEntry()
返回 > key的最大key或者entry, 这个和ceilingKey的区别是ceiling的返回值可能和参数一样， higherKey的返回值和参数不一样
```

TreeMap can achieve `O(logN)` for put()/get()/remove() since the red-black-tree needs to re-balance itself (not like HashMap and LinkedHashMap who supports `O(1)` for all operations)

3. PriorityQueue

PQ can achieve `O(logN)` for offer()/poll(), while `O(1)` for peek() and `O(n)` for contains().
 

4. Deque

Deque has two ways of implementation, and it's known that `ArrayQueue` is better than `LinkedList`:  
https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist  
The reason is that whenever addFirst()/removeLast(), `LinkedList` would dynamically create new node, which makes CPU cache cannot make much benefit of it. 
Since `ArrayQueue` allocates the memory at beginning, or resizing when it's full, so it can leverage CPU cache to hit the memory block more efficiently. The addFirst()/removeLast() operation on `ArrayQueue` is amortized `O(1)`.

An interesting thing is that, even though `ArrayQueue` can have `O(1)` for arbitrary array element access, but Java does not provide API/method to arbitrary acess element!!! 

It makes no much different when choosing between `ArrayQueue` and `LinkedList`, not to mention that `LinkedList` can provide `indexOf(object)` or `remove(index)`.


#### [LC][Medium] 146. LRU Cache
https://leetcode.com/problems/lru-cache/

- option1: linkedhashmap 
- option2: custome class to define double-linked-list and hashmap

#### [LC] 432. All Oone Data Structure
https://leetcode.com/problems/all-oone-data-structure/

Without considering the `O(1)` requirements, we can simply use `TreeMap` to get max key and min key. 
But here we need to use the idea of the option2 in `146. LRU Cache`, by using a double-linked-list and a hashmap.

The reason why we cannot use `LinkedHashMap` is that we have to count the frequency/occurency of each key, not like in `146. LRU Cache` that we can append the newly visited item to end and remove "least used item" from top. 

NOTE:
- there is a good trick by setting dummy nodes `max` and `min`, so that we never need to worry about moving `min` and `max`, and all the nodes would have valid `next` and `prev`!!!!

```java
    HashMap<String, Layer> keyToLayerMap;
    Layer min = null;
    Layer max = null;
    
    private static class Layer {
        public int count; // count of the keys at this layer
        public HashSet<String> keys; // hashset to store key, so that get/remove are O(1)
        public Layer next;
        public Layer prev;
        public Layer(int count) {
            this.count = count;
            this.keys = new HashSet<>();
        }
    }
    
    /** Initialize your data structure here. */
    public AllOne() {
        keyToLayerMap = new HashMap<>();
        min = new Layer(0); // set dummy head
        max = new Layer(0); // set dummy tail
        min.next = max;
        max.prev = min;
    }
```



#### [LC] 460. LFU Cache
https://leetcode.com/problems/lfu-cache/

https://www.jianshu.com/p/437f53341f67

It's too hard, since it needs to define custom `Layer` class and another custom inner `Node` class to maintain inner linked list.

#### [LC] 157. Read N Characters Given Read4
https://leetcode.com/problems/read-n-characters-given-read4/

#### [LC][Hard] 158. Read N Characters Given Read4 II - Call multiple times
https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/

- define 3 global variables `carryOverOrBuf4`, `carryOverValidSize` and `carryOverValidIndex`
- then copy the character from `carryOverOrBuf4` array to result buf array, until the processed number reaches n

```
char[] carryOverOrBuf4 = new char[4]; // we can only carryOver 3 char at most, but we set the space to be 4 to reuse it for read4
int carryOverValidSize = 0; // valid carryOver char in carryOverOrBuf4 array
int carryOverValidIndex = 0; // last time we stop here in carryOverOrBuf4 array
```


#### [LC][Hard] 295. Find Median from Data Stream
https://leetcode.com/problems/find-median-from-data-stream/

the followings come from: https://www.sohu.com/a/363477662_355142

Two heap:

最小堆保存较大的一半元素，最小值位于根元素
最大堆保存较小的一半元素，最大值位于根元素
现在，可以把传入的整数与最小堆根元素进行比较，并加到对应的一半数列中。接下来，如果插入后两个堆大小差距大于1，可以为堆进行重新平衡，让差距最大等于1：

if (size(minHeap)> size(maxHeap)+ 1) {
  // 移除minHeap根元素,插入maxHeap
}

if (size(maxHeap)> size(minHeap)+ 1) {
  // 移除maxHeap根元素,插入minHeap
}

通过这种方法，如果得到的两个堆大小相等，可以中位数等于两个堆的根元素平均值。否则，元素多的那个堆，其根元素就是中位数。


#### [LC] 348. Design Tic-Tac-Toe
https://leetcode.com/problems/design-tic-tac-toe/

We only need to maintain the count of each player at each row/col/2-diagnoals by arrays, and whenever a player makes a move, check if any of the count in the 4 arrays reach n:

```java
rowCount = new int[n][2]; // rowCount[i][j] means at row i, player j-1 has how many score
colCount = new int[n][2]; // colCount[i][j] means at col i-1, player j-1 has how many score
diagonalCount = new int[2][2]; // diagonalCount[i][j] means at diagnoal i-1, player j-1 has how many score
```


#### [LC] 794. Valid Tic-Tac-Toe State
https://leetcode.com/problems/valid-tic-tac-toe-state/

This is not a design question, but I put it here since it's the same idea as in `348. Design Tic-Tac-Toe`, we use rowCount, colCount and 2 diagnoalCount arrays to calculate who wins. 
- if x wins, then xCount=oCount+1
- if o wins, then xCount=oCount-1
- x and o cannot both win
- we also need to check the count of x and o is less than 1

#### [LC] 480. Sliding Window Median
https://leetcode.com/problems/sliding-window-median/


Similar idea like `295. Find Median from Data Stream`, and add this logic:
每次滑动时, 由于堆不能 O(1)的删除特定元素, 考虑用一个 balance记录, 再用一个 map记录需要删除的元素, 等待删除元素在堆顶时再删除

#### [LC] 304. Range Sum Query 2D - Immutable
https://leetcode.com/problems/range-sum-query-2d-immutable/

This is not a real DP question actually, it is only that `sumRegion()` would be called multiple times, so that we don't need to recalcualte overlap regions.

The idea is to maintain a `dp[][]` arrar which stores sum from `0,0` to all `i,j`, then the region sum could be calculated in this way:

`Sum(ABCD)=Sum(0D)−Sum(0B)−Sum(OC)+Sum(0A)`

#### [LC][Medium] 341. Flatten Nested List Iterator
https://leetcode.com/problems/flatten-nested-list-iterator/

- option1: using recursion
  - Constructor: O(N + L)
  - next(): O(1)
  - hasNext(): O(1)
  - Space complexity : O(N + D) where D is the nesting depth
```java
    private void flattern(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return;
        }
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) {
                nums.add(ni.getInteger());
            } else {
                flattern(ni.getList());
            }
        }
    }
```

- option2: using 2 stack

#### [LC] 380. Insert Delete GetRandom O(1)
https://leetcode.com/problems/insert-delete-getrandom-o1/

NOTE:
- to achieve random index to val mapping in `O(1)`, we need `ArrayList`
- to achieve checking val existence in `O(1)`, we need `HashMap`
- to achieve delete arbitrary index from ArrayList, we need to swap val with last element in list, and then remove last element from list
- always update map when doing list operation (add/delete/set)

#### [LC] 381. Insert Delete GetRandom O(1) - Duplicates allowed
https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/

Similar like `380. Insert Delete GetRandom O(1)`, when deleting element, swap with end element to achieve `O(1)`

```java
ArrayList<Integer> list;
HashMap<Integer, Set<Integer>> valToPosSetMap;
```

Be careful  
1. hashset cannot remove by index, has to use iterator like this `int val = hashset.iterator().next()`
2. be careful to not use `list.size() - 1` at multiple places, especially when we need to delete it from both list and hashset, since the list size would have already changed when it's removed once.

e.g.

```java
int pos = posList.iterator().next();
int end = list.size() - 1;
swap(pos, end, list, map);
list.remove(end); 
posList.remove(end); // we cannot use "list.size() - 1", since list size has changed!!!
```


#### [LC] 398. Random Pick Index
https://leetcode.com/problems/random-pick-index/

hashmap to store num[i] to list of index, then random pick an index in list by target.


#### [LC][Hard] 489. Robot Room Cleaner
https://leetcode.com/problems/robot-room-cleaner/

This is really hard, but also very interesting.  

The trick is:  
- using relative position to record the visited hashset
  - using `x+""+y` as string key in hashset
- always use the current direction to move, until we have to stop
- when we have to stop, try another 3 directions
  - when trying different directions, always turnRight, and it requires the direction array is also always turn right: `{-1,0},{0,1},{1,0},{0,-1}` which means up->right->down->left
- after recursive call backtrack, need to reset, and the way to reset is to take one step back:

```java
private void goBack(Robot robot) {
    // turn 180
    robot.turnRight();
    robot.turnRight();
    // move 1 step
    robot.move();
    // turn 180
    robot.turnRight();
    robot.turnRight();
}
```


#### [LC] 588. Design In-Memory File System
https://leetcode.com/problems/design-in-memory-file-system/

- using TreeMap since the question wants to return file/directories in lexicographic order.
- be careful when using `s.slpit("/")`, the first element in the array would be empty string `""`, need to skip this one

#### [LC] 716. Max Stack
https://leetcode.com/problems/max-stack/

- option1: using two stacks, one to store the original nums, and one to store max num at current level
  - when push(), push the `Math.max(maxStack.peek(), num)` to maxStack
  - when popMax(), using a tmpStck to store original nums, then pop both original stack and maxStack until `numStack.peek().equals(maxStack.peek())` which means we find the top most max value
    - it is `O(N)`
    - NOTE: Integer cannot use `==`, using `equals()` instead!!!
- option2: using cusomized class doubleLinkedList and TreeMap,
  - TreeMap keeps the num sorted in keySet, and the values are list of `DoubleLinkedNode`
  - the trick is that, when we do `popMax()`, treeMap can return a list of `DoubleLinkedNode`, and the node can find it self in the doubleLinkedList by `O(1)`, and then remove itself in the doubleLinkedList
    - it makes pop(), push(), popMax() are all `O(logN)` since TreeMap needs to sort, while peek() and peekMax() are `O(1)`

#### [LC] 155. Min Stack
https://leetcode.com/problems/min-stack/

Similar like `716. Max Stack`, but we can play a trick here, since we don't need to maintain 2 stack, but using one stack to store `int[]` like this to store both original num and min num so far.

```java
Deque<int[]> stack = new LinkedList<>(); 
```

#### [LC] 729 My Calendar I
https://leetcode.com/problems/my-calendar-i/

- Using TreeMap
  - key is start of interval, value is the end of interval
  - then check the ordered previous and next interval of the pending interval by `ceilingKey(K key)` and `floorKey(K key)`
- Time Complexity: O(NlogN),where N is the number of events booked. For each new event, we search that the event is legal in O(logN) time, then insert it in O(1) time.
- Space Complexity: O(N), the size of the data structures used.

#### [LC] 731. My Calendar II
https://leetcode.com/problems/my-calendar-ii/

- Using two treemap here, one to store general meetings, another one to store overalapped areas as new intervals
 

#### [LC] 1472. Design Browser History
https://leetcode.com/problems/design-browser-history/

Just using an ArrayList to store history and pointer for current position. Whenver visit() is called, we remove everything from `current+1` to end of the history, and then append the visited url.

```java
history.subList(current+1, history.size()).clear();
```

#### [LC][Medium] 1570. Dot Product of Two Sparse Vectors
https://leetcode.com/problems/dot-product-of-two-sparse-vectors/

using HashMap to store `non-0-index to num` mapping, when doing dot product, just check if the index key exists in both vectors.

#### [LC][Medium] 311. Sparse Matrix Multiplication
https://leetcode.com/problems/sparse-matrix-multiplication/

Similar like `1570. Dot Product of Two Sparse Vectors`, we can maintain hashmap for each row of matrix1, and hashmap for each col of matrix2, then do the multiplication.  

Without any optimization it is `O(m*n*k)`, but after our improvement it can be improved (hard to tell how much improvement, depends on sparse it is).


Another way is to use Strassen algorithm, to use some "plus" to replace "multiple", since multiple is more expensive 
  - https://sites.google.com/a/chaoskey.com/algorithm/02/03

