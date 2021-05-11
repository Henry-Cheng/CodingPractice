## Monotone stack

Just build a general stack, it's only the elements in stack would be monotone, e.g. increasing or decreasing.  
It is very useful on two types of questions:
1. find next greater num in array by `O(N)`
  - maintain a monotone-decreasing stack
  - compare current with stack.peek()
  - if `current > stack.peek()`, then pop all num that is less than current and update the result array
  - if `current <= stack.peek()`, push into stack

2. find a ramp or valley in array by `O(N)` --> not only pop, but also push current num to replace poped one, and also push the current num
  - maintain a monotone-increasing stack
  - 


- 当要压栈的数不满足规定的单调性，就不把这个数压栈。
- 当压栈的数破坏了单调性，就不停地弹出栈中的元素，直到新数字压进去也不会破坏单调性为止。这么说可能有点绕，举个例子，比如我要在一个单调递增的栈中压入新数字3，而栈顶元素是4，此时直接压入3就不是单调栈了，这时候我就弹出4，看新的栈顶元素，以此类推，直到栈顶元素小于3为止。
- 复杂度是O（n）, 因为每个元素最多进行一次入栈一次出栈

### Default


#### [LC][Easy] 496. Next Greater Element I
https://leetcode.com/problems/next-greater-element-i/

It is marked as easy, but actually medium level.  

The 1st half is exactly like `[Medium] 739. Daily Temperatures`, to find the next greater element in nums2 array, and we can store the num to nextGreater mapping in hashmap.
Then 2nd half is to traverse nums1 array by hashmap to store the nextGreaterNum in result.


#### [LC][Medium] 739. Daily Temperatures
https://leetcode.com/problems/daily-temperatures/

This is similar to `496. Next Greater Element I`.

- compare nums[i] with nums[stack.peek()]
- push i to stack if nums[i] >= nums[i+1]
- set result[i] = 1 if nums[i] < nums[i+1]


#### [LC][Medium] 503. Next Greater Element II
https://leetcode.com/problems/next-greater-element-ii/

This is a harder version than `[Easy] 496. Next Greater Element I`, since this time the array is a circular array.  We can imagine the array can be concated, so that we can traverse twice by some trick here:  
```java
// traverse from 0 to 2*length
for(int j = 0; j < nums.length * 2; j++) {
	// make sure the i is 0~length-1 for 2 rounds
    int i = (j >= nums.length ? j-nums.length : j);
    // make sure iPlus is 0 when i is length-1 at 1st round, otherwise it's i+1
    int iPlus = (j == nums.length-1 ? 0 : i+1);
    while(!stack.isEmpty()) {
    	...
    }
    if (iPlus < nums.length) {
    	..
    }
}
```

#### [LC][Hard] 84. Largest Rectangle in Histogram
https://leetcode.com/problems/largest-rectangle-in-histogram/

https://blog.csdn.net/Tc_To_Top/article/details/52247947


Check each num with stack.peek(), to verify if stack.peek() has chance to be a larger rectangle:

```java
nums: 2 1 5 6 2 3
                        (index) (index)
    stack.peek()   num  left  right
            []      2    0     []      --> 2 has chance --> push 2
             2      1    0     0       --> 2 no chance --> pop 2, calculate 2, push 1
             1      5    1     []      --> 1 has chance --> push 5
           1 5      6    2     [3]     --> 5 has chance --> push 6
         1 5 6      2    3     3       --> 6 no chance --> pop 6 --> 5 has no chance, push 2
           1 2      3    4     []      --> 2 has chance --> push 3
         1 2 3           5     []

--> now for the rest num in stack: 1,2,3, their right boundary are all nums.length-1

// We can also maintain the following 2 boundary arrays
// for ith num, the leftmost boundary
int[] left = new int[heights.length];
// for ith num, the rightmost boundary
int[] right = new int[heights.length];
```




#### [LC][Medium] 1856. Maximum Subarray Min-Product
https://leetcode.com/problems/maximum-subarray-min-product/



#### [LC][Medium] 962. Maximum Width Ramp
https://leetcode.com/problems/maximum-width-ramp/


#### [LC][Hard] 42. Trapping Rain Water
https://leetcode.com/problems/trapping-rain-water/


This can also be done by two pointers at beginning and end and move to the middle.  
Here we can also use monotone stack to achieve.







