## Array
### Default
#### [LC] 41. First Missing Positive

- The trick is: the max possible "min positve num" is `nums.length + 1`.
- To achieve constant space, we can reuse existing array to change value in place

NOTE: be careful on the algorithm to swap nums in place, need to break when there are duplicate numbers

```java
        //     i     0 1  2 3
        // expected  1 2  3 4
        // actual    3 4 -1 1
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0) {
                if (nums[i] - i == 1) { // already matched
                    break;
                } else { // swap
                    int tmp = nums[nums[i] - 1];
                    if (tmp == nums[i]) {
                        break; // same number, skip here in case infinite loop
                    }
                    nums[nums[i] - 1] = nums[i];
                    nums[i] = tmp;
                }
            }
        }
```

#### [LC][Easy] 66. Plus One
https://leetcode.com/problems/plus-one/

This is simple.

Remmebr to use `Arrays.copyOfRange(result, 1, result.length);`

#### [LC] 88. Merge Sorted Array
https://leetcode.com/problems/merge-sorted-array/

- option1: merge two array then `Arrays.sort()`
- option2: copy one of the array into `nums1Copy`, then use 3 pointers to move into final array one by one


#### [LC][Easy] 163. Missing Ranges
https://leetcode.com/problems/missing-ranges/

This problem is easy, just be careful to increment the lower every time we traverse a num in array `lower = num+1`.  

Also be careful afte all num in array are done, we need to compare lower with upper to append to result list. 

#### [LC][Easy] 228. Summary Ranges
https://leetcode.com/problems/summary-ranges/

Similar like `[Easy] 163. Missing Ranges`, we maintain a variable `prev`  to store previous num, and another variable `anchor` to be the beginning of the sequence.  

#### [LC] 283. Move Zeroes
https://leetcode.com/problems/move-zeroes/

Using while loop to find first zero and first non-zero separately:  
```java
  while(nonZeroPos < nums.length && zeroPos < nums.length) {
      while(nonZeroPos < nums.length && nums[nonZeroPos] == 0) {
          nonZeroPos++;
      }
      while(zeroPos < nums.length && nums[zeroPos] != 0) {
          zeroPos++;
      }
      ...
  }
```

Then if `zeroPos < nonZeroPos`, swap them, and increment both pointers.  
But if `zeroPos > nonZeroPos`, then only increment nonZeroPos


#### [LC] 349. Intersection of Two Arrays
https://leetcode.com/problems/intersection-of-two-arrays/

Using two hashset to find intersection.

NOTE:
for int[] to List<Integer>:  
```java
List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
```
For List<Integer> to int[]:  
```java
int[] arr = list.stream().mapToInt(i->i).toArray();
```