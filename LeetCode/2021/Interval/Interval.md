## Interval
### Default

#### [LC][Medium] 56. Merge Intervals
https://leetcode.com/problems/merge-intervals/

Sort 2d array or array of object:
```java
Arrays.sort(arrays, (a, b) -> {
  return a[0] - b[0]; // ascending -- positive means b->a, negative means a->b
});
```

Sort objects:
```java
Collections.sort(posList, (a, b) -> {
  return a.index - b.index; // return positive means b->a, negateive means a->b
});
```

#### [LC] 252. Meeting Rooms
https://leetcode.com/problems/meeting-rooms/

Simple solution with Array.sort
```java
        Arrays.sort(intervals, (a, b)->{
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
``` 

#### [LC][Medium] 763. Partition Labels
https://leetcode.com/problems/partition-labels/

Need to covert this problem into interval problem.  

Problem:
```
Input: s = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
```

1. The idea is to traverse each character and find its starting pos and ending pos, aka we can build a list of intervals.  
2. Sort intervals by sorting algorithm or heap, and compare each current interval with prev interval:
  1. if `current[0] > prev[1]`, it means we can partition here, record the prev interval lenght, and sed prev to be current
  2. if `current[0] < prev[1]`, it means we can merge intervals, set the `prev[1] = Math.max(current[1],prev[1])`

Finally return the paritioning result.

Time complexity is `O(NlogN)` since we use heap to sort

#### [LintCode] 850. Employee Free Time

https://www.lintcode.com/problem/850/

Use custom comapritor, and be careful when union two intervals, set the end of the new interval to be the max one of original two intervals.


#### [LC][Medium] 986. Interval List Intersections
https://leetcode.com/problems/interval-list-intersections/

NOTE: 
- only need to compare p1 and p2, no need to compare with last element in result list --> they would never have overlap since we always move the pointer of smaller interval
- how to tell if they are intersected?
  - `int low = Math.max(firstList[p1][0], secondList[p2][0]);`
  - `int high = Math.min(firstList[p1][1], secondList[p2][1]);`
  - `if (low <= high) {return true;}`
- how to move the pointer of the smaller interval?
  - `if (firstList[p1][1] <= secondList[p2][1]) {p1++;}`

```java
        while(p1 < firstList.length && p2 < secondList.length) {
            int low = Math.max(firstList[p1][0], secondList[p2][0]);
            int high = Math.min(firstList[p1][1], secondList[p2][1]);
            if (low <= high) {
                int[] intersection = new int[2];
                intersection[0] = low;
                intersection[1] = high;
                result.add(intersection);
            }
            if (firstList[p1][1] <= secondList[p2][1]) {
                p1++;
            } else {
                p2++;
            }
        }
```