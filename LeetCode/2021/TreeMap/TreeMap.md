## TreeMap

### Default

#### [LC] 239. Sliding Window Maximum
https://leetcode.com/problems/sliding-window-maximum/

Option1: TreeMap is `O((N-k+1)*logN)`

`TreeMap<Integer,HashSet<Integer>>` can be used as multiset in C++ to maintain order of elements and also the occurence (index of elements) as value.


Option2: Monotonic Queue is `amortized O(1)`

The algorithm is quite straigthforward, only maintains nums in the window in the queue, and whenever offering new num, remove all the nums less than it, since those deleted num would never has chance being used .

```
Window position              Monotonic Queue     Max
---------------                                 -----
[1] 3  -1 -3  5  3  6  7        [1]               -
[1  3] -1 -3  5  3  6  7        [3]               -
[1  3  -1] -3  5  3  6  7       [3 -1]            3
 1 [3  -1  -3] 5  3  6  7       [3 -1 -3]         3
 1  3 [-1  -3  5] 3  6  7       [5]               5
 1  3  -1 [-3  5  3] 6  7       [5 3]             5
 1  3  -1  -3 [5  3  6] 7       [6]               6
 1  3  -1  -3  5 [3  6  7]      [7]               7
```

