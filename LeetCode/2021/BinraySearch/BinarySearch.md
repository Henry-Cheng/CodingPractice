## BinarySearch
### Default

https://zhuanlan.zhihu.com/p/79553968

https://blog.csdn.net/Bean771606540/article/details/107533165

NOTE: need to understand `searching space` for binary searching problem, since we cannot use the template blindly, somtimes the searching space requries us to set `right = mid`  instead of `right = mid-1` since the `mid` could also be the final result (aka in the searching space)
  - e.g. `153. Find Minimum in Rotated Sorted Array`

Binary Search looks easy, but it could be very hard in details, since most ppl don't know  
- initially, whether to use `right = nums.length` or `right = nums.length - 1`
- in while method, whether to use `left < right` or `left <= right` 
- when move left and right pointer, whether to do `left = mid` or `left = mid + 1`
- after while loop is done, can I use `left` or `right` directly?

Explaination:  
- what does `right = nums.length` or `right = nums.length - 1` mean?
  - when `right = nums.length`
    - it means our searching space is `[left,right)` 
    - we have to use `while(left < right)` 
      - it means the ending condition is when `left == right`
  - when `right = nums.length - 1`
    - it means our searching space is `[left, right]`
    - we have to use `while(left <= right)`
      - it means the ending condition is when `left == right + 1`

Consider it in this way (using right inclusive algorithm):  
```
Finally we have searching space [right+1, right], so the "middle" of left and right must be the boundary:

if target exists (target==5), we find left most boundary (left points to target):

[1,    3,       |        5,     5,    7,    9]
     right  (boundary)  left


if taget exists (taget==5), we find right most boundary (right points to target):

[1,    3,      5,     5,       |        7,    9]
                    right  (boundary)  left


if key not exists (key==4), we can also find the boundary (left or right at two sides of target):
[1,    3,       |        5,     5,      7,    9]
     right  (boundary)  left


if key out of boundary (key==10)

[1,    3,     5,     5,      7,    9]      |
                                right  (boundary)  left

if key out of boundary (key==0)
           |        [1,    3,     5,     5,      7,    9]
right  (boundary)  left

```
 

If we use all-inclusive searching space, here are the code:
```java
int left_bound(int[] nums, int target) {
    int left = 0, right = nums.length - 1; // right inclusive
    while (left <= right) { // searching space is [left,right]
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else if (nums[mid] == target) {
            right = mid - 1; // move left
        }
    }
    /**
      * now searching space is [right+1, right], nothing is searchable, so we end.
      * if target exists, nums[left] should equal to the target
      * if not, check overflow situation
      * 1. when target is larger than any num, then left is at length --> [length, length-1]
      * 2. when target is less than any num, then right is at -1 --> [-1, 0]
      *
      * NOTE: can also return right finally, then we need to check if right < 0
    **/
    if (left >= nums.length || nums[left] != target)
        return -1;
    return left;
}
```

```java
int right_bound(int[] nums, int target) {
    int left = 0, right = nums.length - 1; // right inclusive
    while (left <= right) { // searching space is [left,right]
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else if (nums[mid] == target) {
            left = mid + 1; // move right
        }
    }
    /**
      * now searching space is [right+1, right], nothing is searchable, so we end.
      * if target exists, nums[right] should equal to the target
      * if not, check overflow situation
      * 1. when target is larger than any num, then left is at length --> [length, length-1]
      * 2. when target is less than any num, then right is at -1 --> [-1, 0]
      *
      * NOTE: can also return left finally, then we need to check if left > length-1
    **/
    if (right < 0 || nums[right] != target)
        return -1;
    return right;
}
```


If we use right-exclusive searching space, here are the code:

```java
int left_bound(int[] nums, int target) {
    if (nums.length == 0) return -1;
    int left = 0;
    int right = nums.length; // right exclusive

    while (left < right) { // // searching space is [left,right)
        int mid = left + (right - left) / 2; 
        if (nums[mid] == target) {
            right = mid; // move left
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid; // NOTE!!!!
        }
    }
  // target is larger than all num
  if (left == nums.length) return -1;
  return nums[left] == target ? left : -1;
}
```

```java
int right_bound(int[] nums, int target) {
    if (nums.length == 0) return -1; 
    int left = 0, right = nums.length; // right exclusive

    while (left < right) { // searching space is [left,right)
        int mid = (left + right) / 2;
        if (nums[mid] == target) {
            left = mid + 1; // move right
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid;
        }
    }
    // target is less than all num
    if (left == 0) return -1;
    return nums[left-1] == target ? (left-1) : -1;
}
```

#### [LC] 4. Median of Two Sorted Arrays
https://leetcode.com/problems/median-of-two-sorted-arrays/

Try to find the partition in two arrays, that makes nums in left section (both a and b) equals to nums in right section (both a and b).  
NOTE: the partition could be in different position in a or b, but the total num must be the same.  
e.g.  
When total number is odd
- a has 5 nums, b has 8 nums, total is 13 nums 
  - --> expected median is 13/2=6th num 
    - --> the left-hand side should have 6 + 1 = 7 nums, and right-hand side should have 6
- if we put partition in a by 5/2 = 2 (in front of a2), then we have 2 nums in left-hand side
- then the partiion in b must be 7 - 2 = 5 (in front of b5) 

```
(7 nums)         (6 nums)   
a0 a1          | a2 a3 a4   
b0 b1 b2 b3 b4 | b5 b6 b7   

now check if a1<=b5 && b4<=a2, if yes, then we found the median by max(a1,b4)  
if not, then check if we need to move partition in a to left or right, and change b correspondingly
```

When total number is even
- a has 6 nums, b has 8 nums, total is 14 nums
  - --> expected median is average of 14/2 = 7th and 7+1=8th nums
    - --> the left-hand side should have 7 nums, and right-hand side should also have 7
- if we put partion in a by 6/3=3, then we have 3 in left-hand side
- then the partition in b must be 7 - 3 = 4 (in front of b4) 

```
(7 nums)      (7 nums)    
a0 a1 a2    | a3 a4 a5  
b0 b1 b2 b3 | b4 b5 b6 b7   

now check if a2<=b4 && b3<=a3, if yes, then we found the median by avg (max(a2,b3), min(a3,b4))
```

For a corner case that we moved partition in a to the end of array  

```python
If total number is even:

(7 nums)            (7 nums)   
a0 a1 a2 a3 a4 a5 | MAX_VALUE  
b0                | b1 b2 b3 b4 b5 b6 b7  

median = avg(max(a5, b0), min(MAX_VALUE, b1))

we set the empty right-hand set of a as MAX_VALUE, and vice versa for empty left-hand-side emtpy set which should be MIN_VALUE

(7 nums)               (7 nums)  
MIN_VALUE            | a0 a1 a2 a3 a4 a5  
b0 b1 b2 b3 b4 b5 b6 | b7  

median = avg(max(MIN_VALUE, b0), min(a0, b7))

If total number is odd:

(7 nums)         (6 nums)  
a0 a1 a2 a3 a4 | MAX_VALUE  
b0 b1          | b2 b3 b4 b5 b6 b7   

median = max(a4, b1)

(7 nums)                (6 nums)  
MIN_VALUE             | a0 a1 a2 a3 a4   
b0 b1 b2 b3 b4 b5 b6  | b7  

median = max(MIN_VALUE, b6)
```

#### [LC][Medium] 29. Divide Two Integers
https://leetcode.com/problems/divide-two-integers/

NOTE: 

- convert all int to long first, since `MIN_VALUE * -1` would be overflow
- do not use `Math.abs()`, since `Math.abs()` only works for int, using naive solution to convert long value to be absolute long value
- using "binary search" idea to addup `divisorAbs` over and over itself, until it reaches the "max fit" or `dividendAbs`
  - e.g. `dividendAbs = 25` and `divisorAbs = 4`
    - 1st round, the max fit is `(4 + 4) + (4 + 4) = 16`, quotient would `+=4`, and the reminder is `25-16=9`
    - 2nd round, the max fit is `(4 + 4) = 8`, quotient would `+=2`, and the reminder is `9-8=1`
    - since `1 < 4`, we reach the end, the final quotient is `4 + 2 = 6`
- there is an improvement here, by using a list to store all multiplies of `divisorAbs`, since the max fit to the reminder must be the previous multiple in the list!!!!
  - we can prove it in math
    - 25 --> max fit is 16, and reminder is 9
    - 9 --> max fit is 8, which is the previous multiple of 4 (4 --> 8 --> 16)

```java
// NOTE: must convert to long first!!! otherwise MIN_VALUE * -1 would be overflow
long dividendCopy = dividend;
long divisorCopy = divisor;

long quotient = 0;
// NOTE: Math.abs() would not work since it only works for int
long dividendAbs = dividendCopy > 0 ? dividendCopy : (dividendCopy * -1);
long divisorAbs = divisorCopy > 0 ? divisorCopy : (divisorCopy * -1);
```

```java
long quotient = 0;
while (dividendAbs >= divisorAbs) {
    long divisorAbsMultiple = divisorAbs;
    long multiple = 1;
    while (dividendAbs >=  divisorAbsMultiple) {
        if (dividendAbs < divisorAbsMultiple + divisorAbsMultiple) {
            break;
        } else {
            divisorAbsMultiple += divisorAbsMultiple;
            multiple += multiple;
        }
    }
    dividendAbs -= divisorAbsMultiple;
    quotient+=multiple;
}
```

#### [LC][Medium] 153. Find Minimum in Rotated Sorted Array
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/

The template need to be adjusted here: 

--> the problem is like: where is the min num? left hand of `mid` or right hand of `mid` or just `mid`?   
--> then we can set `right = mid` when finding that min could be at left-hand side or mid or the mid itself
--> finally we can break at `left==right` since that is the min we found.

#### [LC][Medium] 33. Search in Rotated Sorted Array
https://leetcode.com/problems/search-in-rotated-sorted-array/

Similar like `153. Find Minimum in Rotated Sorted Array`, using binary search template, and inside the loop, check which side is sorted:  
- if right hand side is sorted, check `if (target > nums[mid] && target <= nums[r])`
  - if yes then move right, otherwise move left
- if left hand side is sorted, check `if (target < nums[mid] && target >= nums[l])`
  - if yes then move left, otherwise move right

#### [LC] 35. Search Insert Position
https://leetcode.com/problems/search-insert-position/

Standard binary search. Be careful that if target is larger than any num, then target is at nums.length instead of nums.length-1; 

#### [LC] 74. Search a 2D Matrix
https://leetcode.com/problems/search-a-2d-matrix/

Do 1st binary searach for the col, then do the 2nd binary search for the corresponding row. 
Time complexity is `O(log(m) + log(n))`.

#### [LC][Medium] 162. Find Peak Element
https://leetcode.com/problems/find-peak-element/

This is a very smart question, and hard to think about using binary earch. 

Now how can we find the peak?  
1. Linear search
2. Binary search


THe question has 2 constraints that gives us the hints to use binary search:  
- no 2 num next to each other are the same, aka `nums[i] != nums[i-1] && nums[i] != nums[i+1]`
- the out-of-index num is infinite, aka `nums[-1] = -∞`, and `nums[nums.length] = +∞`

In this way, we can tell that:  
1. if `nums[mid-1] < nums[mid] > nums[mid+1]`, mid is a peak
2. if `nums[mid-1] > nums[mid]`, then left-hand side must exist a peak
3. if `nums[mid+1] > nums[mid]`, then right-hand side must exist a peak

We can prove #2 and #3 by Disproval(反证法):
https://www.kancloud.cn/kancloud/data-structure-and-algorithm-notes/72972  
e.g. 若左侧不存在peak，则A[mid]左侧元素必满足A[-1] > A[0] > A[1] > ... > A[mid -1] > A[mid]，与已知A[-1] < A[0]矛盾，证毕。


Be careful the mid+1 could be over array length, that means left==right by then, we can simply return left at the point.


#### [LC] 240. Search a 2D Matrix II
https://leetcode.com/problems/search-a-2d-matrix-ii/

This looks similar but very different from `74. Search a 2D Matrix`, since here we cannot simply using binary search.

```java
    1, 4, 7, 11,15
    2, 5, 8, 12,19
    3, 6, 9, 16,22
    10,13,14,17,24
    18,21,23,26,30
```

Looking at the graph we can find 2 interesting positions: left-bottom and right-top.

left-bottom: 18, every num right to it is larger, every num up to it is smaller
right-top: 15, every num left to it is smaller, every num down to it is larger.

We can simply search from left-bottom to right-top
- if it's larger, then we move to right
- if it's smaller, then move to up
- if finally over the size of matrix, it does not exist

Time complexity is `O(m + n)`

#### [LC] 81. Search in Rotated Sorted Array II
https://leetcode.com/problems/search-in-rotated-sorted-array-ii/

It is similar like `33. Search in Rotated Sorted Array`, but since it has duplicates, we may see problem like this: 

```java
[1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1]
2
```

So at beginning of each loop, we can move left pointer to the last duplicate num, and so is the right pointer to the first duplicate num:  

```java
        while(l <= r) {
            // special handling: dedup nums at left and right
            while(l+1 < nums.length && nums[l] == nums[l+1]) {
                l++;
            } // l is at the end of distinct num
            if (l == r) {
                return nums[l] == target;
            }
            while(r - 1 >= 0 && nums[r] == nums[r-1]) {
                r--;
            } // r is at the beginning of distinct num
            if (l == r) {
                return nums[l] == target;
            }
            // do the standard binary search
        }
```


#### [LC] 154. Find Minimum in Rotated Sorted Array II
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/

This is a harder version of `153. Find Minimum in Rotated Sorted Array` since it would have duplciates. We use the same idea as `81. Search in Rotated Sorted Array II` by moving left and right pointers over duplicate nums.  

The worst time complexity is  `O(N)` but averagely it is `O(logN)`

#### [LC] 34. Find First and Last Position of Element in Sorted Array
https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/



#### [LC][Medium] 50. Pow(x, n)
https://leetcode.com/problems/powx-n/

- for `i = n ~ 1`
  - if `i % 2 == 0`, the can reuse recursive result of `i/2`, 
  - if `i % 2 != 0`, then multiple x once and then leverage binary search
- be careful to set n to be `long`!!!
  - since n could be Integer.MIN_VALUE, when we remove the symbol to make `Math.abs(n)`, it would be over Integer.MAX_VALUE


#### [LC] 270. Closest Binary Search Tree Value
https://leetcode.com/problems/closest-binary-search-tree-value/

Binary search in a BST is very easy:

```java
        while(root != null) {
            if (root.val <= target) {
                // do something
                root = root.right;
            } else {
                // do something
                root = root.left;
            }
        }
```

#### [LC][Easy] 278. First Bad Version
https://leetcode.com/problems/first-bad-version/

NOTE!!!
- use `mid = left + (right - left) / 2` instead of `mid = (left + right) / 2`, since when it's over Integer.MAX_VALUE, mid becomes negative due to overflow, that will result in calling the API a lot of times without getting the actual result


#### [LC] 658. Find K Closest Elements
https://leetcode.com/problems/find-k-closest-elements/

Binary search + two pointers

Initially, we check if x is less or equal to arr[0], then just return first k element.
Then, we check if x is larger or equal to arr[arr.length-1], then just return last k elements.
If x is within range, do binary search to find closest index for x.  
Now we can use two pointers to search left-hand k pos and right-hand k pos, and finally return the `[left+1, right)` subarry


#### [LC][Medium] 875. Koko Eating Bananas
https://leetcode.com/problems/koko-eating-bananas/

We use binary search, set `l = 1` and `r = max(nums[i])`, using h as target, and function `getHourByK()` as the nums funtion.

Be careful:

```python
the final situation is the l = r + 1, but lHour and rHour are reversed, since the larger the k is, the smaller the hour would be, so we should return r finally 

h = 4, 
        r   <    l
nums: 1 3   |    5  7 9
        lH  kH  rH

h = 5
        r   <    l
nums: 1 3   |    5  7 9
        lH  kH   rH

h = 1
nums:   |    3  5 7 9 11
     lH kH  rH
```

#### [LC] 1428. Leftmost Column with at Least a One
https://leetcode.com/problems/leftmost-column-with-at-least-a-one/

An improvement on the binary searching function: if at row i we found leftmost 1 at j, then we can check `(i+1,j)` directly 
- if `(i+1,j)` is 0, then we can skip this row and move to `i+2`
- if `(i+1,j)` is 1, then do binary search at row `i+1` from 0 to j


#### [LC][Easy] 1539. Kth Missing Positive Number
https://leetcode.com/problems/kth-missing-positive-number/

This is definitely not an easy solution.  

This is a tricky binary search algorithm use case.   
Since here the evaluation function is not to compare arr[mid] with target, but to compare "missing positive from 0 till arr[mid]" with target.  

Also, we need to find the leftmost mid that meets the requriement, and then return the `arr[r] + k - missingFromZero(arr, r)`. --> if r is less than 0, then just return k.

#### [LC][Medium] 1060. Missing Element in Sorted Array
https://leetcode.com/problems/missing-element-in-sorted-array/

Similar like `1539. Kth Missing Positive Number`, but a bit harder on the evaluation function.  
The evaluation function is to find the # of missing num from 0 to mid, the using BinarySearch to find the left most element.  

#### [LC][Medium] 1855. Maximum Distance Between a Pair of Values
https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/

This problem is not easy. It's part of the weekly contest: https://leetcode.com/discuss/general-discussion/1198637/weekly-contest-240

Since both the arrays are sorted, our initial idea is to use binary search.  
But it's not easy to figure out how to use binary search.  

```java
nums1:   55 30  5 4 2
nums2:  100 20 10 6 3 1 0

firstly we compare 55 and 100, since 100>=55, we can binary search 55 from 100~0 in nums2.
then we compare 30 and 20, since 20<30, ignore, since no num after 20 can be larger than 30.
then we compare 5 and 10, do binary search 10~0
then we check 4 and 6, ignore
then 2 and 3, binary search
...
```

Be careful that the array is descending order, so when moving left and right pointers in binarysearch function, we need to reverse the direction.


