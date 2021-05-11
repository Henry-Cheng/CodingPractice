## Interval
### Default

#### [LC] 56. Merge Intervals
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

#### [LintCode] 850. Employee Free Time

https://www.lintcode.com/problem/850/

Use custom comapritor, and be careful when union two intervals, set the end of the new interval to be the max one of original two intervals.


#### [LC] 986. Interval List Intersections
https://leetcode.com/problems/interval-list-intersections/

NOTE: 
- only need to compare p1 and p2, no need to compare with last element in result list --> they would never have overlap since we always move the pointer of smaller interval
- how to tell if they are intersected?
  - `int low = Math.max(firstList[p1][0], secondList[p2][0]);`
  - `int high = Math.min(firstList[p1][1], secondList[p2][1]);`
  - `if (low <= high) {return true;}`
- how to move the pointer of the smaller interval?
  - `if (firstList[p1][1] <= secondList[p2][1]) {p1++;}`