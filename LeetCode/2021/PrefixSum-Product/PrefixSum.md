## PrefixSum-Product
### Default

#### [LC] 238. Product of Array Except Self
https://leetcode.com/problems/product-of-array-except-self/

Using array get prefix-product left to right, then using another array from right to left, the `result[i] = leftToRight[i] * rightToLeft[i+1]`.
- an improvement here is to not use array for rightToLeft, we can calculate it on the fly when traversing from right to left (after we store leftToRight array in result array).

#### [LC] 523. Continuous Subarray Sum
https://leetcode.com/problems/continuous-subarray-sum/

Using prefixSum and HashMap to store the mapping from `prefixSum % k` to `index`.  
- we must use hashmap instead of hashset!!! since we need to know if the index distance is >= 2
- there is a special case that prefixSum itself can mod k to get 0, it is also a true case

#### [LC][Medium] 325. Maximum Size Subarray Sum Equals k
https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/

1. It is similar like `[Medium] 560. Subarray Sum Equals K`, but is harder since there could be duplicate records in prefixSum, we need to only keep the earliest index in hashmap for the same prefixSum.
2. Not only consider the "prefixSum[i] - k" situation, we can also consider "prefixSum[i] == k" situation, it means the prefixSum itself is the longest distance so fra
3. Also, we don't need to maintain prefixSum array, we can calcualte the cumulative sum in the same for loop as calculating "prefixSum[i] - k"

#### [LC][Medium] 560. Subarray Sum Equals K
https://leetcode.com/problems/subarray-sum-equals-k/

1. It is similar like `[Medium] 325. Maximum Size Subarray Sum Equals k`
2. using hashmap to maintain "prefixSum[i] to its occurence" mapping
3. using a global variable to maintain prefixSum (no need to maintain a prefixSum array)
4. if `prefixSum==k`, count++
5. if `prefixSum-k` exists, count+=(occurence of prefixSum-k)
6. if `prefixSum` already in hashmap, increment the occurence in hashmap

#### [LC][Medium] 525. Contiguous Array
https://leetcode.com/problems/contiguous-array/

It is similar like `[Medium] 325. Maximum Size Subarray Sum Equals k`, if we treat 0 as -1, then we can do prefix sum, and then check the longest distance of the same prefixSum
```java
original nums:    0  1  0  0  0 1   1
mutated nums:     -1 1 -1 -1 -1 1   1
prefixSum:      0 -1 0 -1 -2 -3 -2 -1
longest dist      from             to             
```


#### [LC] 862. Shortest Subarray with Sum at Least K
https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/

This is a hard version of `209. Minimum Size Subarray Sum`, since it could have negative num.

Solution in Chinese version: [https://github.com/Shellbye/Shellbye.github.io/issues/41](https://github.com/Shellbye/Shellbye.github.io/issues/41)


NOTE:
- cannot directly use two pointers after prefixSum array!!!
  - the reason is that the prefixSum array is not sorted, so when we reach to index i that is less than index i-1, the left pointer would stuck at index i wihtout moving any more! (see [https://www.taodudu.cc/news/show-2093248.html](https://www.taodudu.cc/news/show-2093248.html))
  - we have to delete such an element at index i, that is why we use Deque!!
- using long here since Integer.MAX_VALUE is 2.1*10^9, but A[i] * A.length < 5*10^9
- prefixSum[0] = 0; // set it to be 0, which means sum before index 0 is 0

#### [LC] 528. Random Pick with Weight
https://leetcode.com/problems/random-pick-with-weight/

prefixSum + search (better to use binary search to improve searching complexity to logN)

```java
            w =    1    2      3       4 
    prefiSum  = 0  1    3      6       10
    percentage= 0% 10%  30%    60%     100%
     100% --> random <= 10  --> 6 < random <= 10 --> 40% chance --> 4
     60%  --> random <= 6   --> 3 < random <= 6  --> 30% chance --> 3
     30%  --> random <= 3   --> 1 < random <= 3  --> 20% chance --> 2
     10%  --> random <= 1   --> 0 < random <= 1  --> 10% chance --> 1
```

NOTE:  
- When using random function, need to have brackets for both `(int)` and `(Math.random() * (max - min + 1))`
- the following would give inclusive `[min, max]`
```java
    // both end inclusive
    protected int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
```

##### [LC][Easy] 724. Find Pivot Index
https://leetcode.com/problems/find-pivot-index/

This is easy, but do not over-think to simplify the code!! Just do left-to-righ prefixSum and then right-to-left prefixSum, then using 3rd for loop to find the index with the same sum in both arrays.
