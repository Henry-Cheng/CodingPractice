## Two Pointers
https://medium.com/outco/how-to-solve-sliding-window-problems-28d67601a66

### Fron/End Pointers

#### [LC] 3. Longest Substring Without Repeating Characters
https://leetcode.com/problems/longest-substring-without-repeating-characters/

We can use HashMap to store char to index mapping, whenever seeing char exists in map, we jump left pointer to the char's position+1, and there is no need to clean up the hashmap from left pointer to position+1, since next time we see existing char, the position is either before the current left pointer, or we continue jumping.
 

#### [LC] 11. Container With Most Water
https://leetcode.com/problems/container-with-most-water/

#### [LC] 15. 3Sum
https://leetcode.com/problems/3sum/

sort first, then have O(N^2)

#### [LC] 16. 3Sum Closest
https://leetcode.com/problems/3sum-closest/  

Be careful on the conditions when should we move left pointer and when to move right pointer

#### [LC] 19. Remove Nth Node From End of List
https://leetcode.com/problems/remove-nth-node-from-end-of-list/

Be careful on corner case, when n equals to size of list, and when n is only 1

#### [LC][Hard] 42. Trapping Rain Water
https://leetcode.com/problems/trapping-rain-water/

Move both front and end pointers when the corresponding wall is lower.

#### [LC] 76. Minimum Window Substring
https://leetcode.com/problems/minimum-window-substring/

This is a smart use of HashMap and sliding window!!

If we do brute force, we will need to maintain two hashMap for dict string and processing string:  
```java
HashMap<Character, Integer> dictCharToCountMap;
HashMap<Character, Integer> currentCharToCountMap;
```
Then every time our sliding window moves, we will need to compare the two hashMap using for loop, which would result in `O(M*N)` time complexity.

But there is a smart way to compare it, by using a counter called `uniqueCharEnough`, and we increment it when one of the unique char is completed (which means we have found enough count for this unique char)
```java
if (currentCharToCountMap.get(c).equals(dictCharToCountMap.get(c))) {
    uniqueCharEnough++;
}
```
If `uniqueCharEnough == dictCharToCountMap.size()`, it means we have found a substring, then we record the substring in a globale variable.  

How do we decrement the `uniqueCharEnough`? 
It also happens when we find `uniqueCharEnough == dictCharToCountMap.size()`, since at this moment we can move left pointer to right.  
We will check whether moving left pointer would impact the `uniqueCharEnough` or not --> looking at the count of the char is less than the count in `dictCharToCountMap`
```java
while(right < s.length()) {
    ...
    ...
    while(uniqueCharEnough == dict.size()) {// all unique char has been matched
        // update minStr and minLength
        ...

        // move left pointer
        if (impacted) {
            //System.out.println("remove " + originalLeft);
            uniqueCharEnough--;
        }
        left++;
    }
    right++;
}

```

NOTE:  
1. when using two pointers, there is an inner while loop to continue moving left pointer to right, it ends until we cannot maintain enough `uniqueCharEnough`
2. when comparing the count of 2 maps, cannot using `==` since it is `Integer`!!!! must use `equals()`  here

#### [LC] 121. Best Time to Buy and Sell Stock
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

```
origin: 3 2 6 5 0 3
if we sell at this point, what would be the lowest buy price
left:   3 2 2 2 0 0
if we buy at this point, what would be the highest sell price
right:  6 6 6 5 3 3
```
Goes through all the way from left to right to find min price at each day (store in new array), then goes through all the way from right to left to find max price at each day (store in new array), then goes through both new arrays together to get the max gap.


#### [LC] 125. Valid Palindrome 
https://leetcode.com/problems/valid-palindrome/

Be careful that left or right could go beyond the boundary.


#### [LC] 161. One Edit Distance
https://leetcode.com/problems/one-edit-distance/

This is not a standard two pointer problem, it just uses two pointers.  
We can firstly compare if two string are equal or their length diff is larger than 1.
If yes then return false.
Then when we traverse both string, if we found different character, using the string length to decide which pointer should we move forward --> since we want the two pointers to reach the end at same time. 
We also use a boolean flag to check if `mismatch` already happened.


#### [LC] 209. Minimum Size Subarray Sum
https://leetcode.com/problems/minimum-size-subarray-sum/
All nums are positive.

```java
         2,3,1,2,4,3 --> 7
         2 --> < target, move right
         2,3 --> < target, move right
         2,3,1 --> < target, move right
         2,3,1,2 --> record it, >= target, move left
           3,1,2 --> < target, move right
           3,1,2,4 --> record it, >= target, move left
             1,2,4 --> record it, >= target, move left
               2,4 --> < target, move right
               2,4,3 --> record it, >= target, move left
                 4,3 --> record it, >= target, move left
                   3 --> < target, at the end
```

#### [LC] 246. Strobogrammatic Number
https://leetcode.com/problems/strobogrammatic-number/

Similar like `788. Rotated Digits`, rotate the number 180 degree to check if it's valid.  
Using two pointers at left and right, and move to te mid together.  
Check if the left char and right char are `0 / 1 / 8` or `6 / 9`.


#### [LC] 1056. Confusing Number
https://leetcode.com/problems/confusing-number/

The same as `246. Strobogrammatic Number`, but not only check if it's valid, but also check if it's different than original one. 

#### [LC][Medium] 340. Longest Substring with At Most K Distinct Characters
https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

```java
        e c e b a --> k ==2
        e --> 1 <= 2, record 1, expand
        e c --> 2 <= 2, record 2, expand
        e c e --> 2 <= 2, record 3, expand
        e c e b --> 3 > 2, shrink
          c e b --> 3 > 2, shrink
            e b --> 2 <=2, record 2, expand
            e b a --> 3 >= 2 shrink
              b a --> 2 <= 2, record 2, end
```
NOTE:
- using HashSet instead of HashMap if no need to store values
- HashSet contains() has O(1) average time complexity, and O(lgn) worst time complexity, since HashSet is backed by HashMap
- after doing `right++` in while loop, right could be over index, be careful to check the range before using it

#### [LC] 344. Reverse String
https://leetcode.com/problems/reverse-string/

#### [LC] 345. Reverse Vowels of a String
https://leetcode.com/problems/reverse-vowels-of-a-string/

Be careful when using hashmap, only when map.get(key) != null means key exists.

#### [LC] 424. Longest Repeating Character Replacement
https://leetcode.com/problems/longest-repeating-character-replacement/

```java

        it equals to: find longest substring that has only k different char
             e c e b a --> k ==2
             e         --> 0 <= 2, record 1, expand
             e c       --> 1 <= 2, record 2, expand
             e c e     --> 1 <= 2, record 3, expand
             e c e b   --> 2 <= 2, record 4, expand
             e c e b a --> 3 > 2, shrink
               c e b a --> 4 > 2, shrink
                 e b a --> 3 > 2, shrink
                   b a --> 2 <= 2 record 2, cannot expand, end

```
NOTE:
- no need to update `mostCountSoFar` when decrementing, the reason is that `mostCountSoFar` recrods the most char count in the history, unless we find a bigger window in the future and increase the `mostCountSoFar`, we cannot do better that current max result which is based on `mostCountSoFar`
- for two pointers template, remember to check right boundary after `right++`, and then increment countMap; but do the decrement before `left--`  

#### [LC] 438 Find All Anagrams in a String 
https://leetcode.com/problems/find-all-anagrams-in-a-string/solution/

- option1: hashmap + sliding window, put dict word into hashmap1, then move left and right pointers to find next dict.legnth substring, and increment/decrement count in hashmap2, compare hashmap1 and hashmap2
- option2: array + sliding window, since it only has lower case letter, hash map can be replaced by 26-length array


#### [LC] 581 Shortest Unsorted Continuous Subarray   
- option1 sort: sort in another array, then compare different num 
- option2 sliding window [LC solution](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/solution/zui-duan-wu-xu-lian-xu-zi-shu-zu-by-leetcode/): if we found such a subarray (i,j), then the correct position of the smallest num is i, the correct position of the largest num in array is j.
  - 因此，首先我们需要找到原数组在哪个位置开始不是升序的。我们从头开始遍历数组，一旦遇到降序的元素，我们记录最小元素为 min 。
  - 类似的，我们逆序扫描数组 nums，当数组出现升序的时候，我们记录最大元素为 max。
  - 然后，我们再次遍历 nums 数组并通过与其他元素进行比较，来找到 min 和 max 在原数组中的正确位置。
    - 我们只需要从头开始找到第一个大于 min 的元素，从尾开始找到第一个小于 max 的元素，它们之间就是最短无序子数组。

```java
         1 3 7 6 5 10 2 9 7 8 
         
         left min --> start from 7 --> 2
         right max --> start from 9 --> 10
         left correct pos --> 3 > 6 --> 3
         right correct pos --> 8 < 10 --> 8
         need to replace from 3 to 8
```


#### [LC][Medium] 708. Insert into a Sorted Circular Linked List
https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/

This is not a standard two-pointer problem, in this question we only need pointer as flag to mark some place for us:

1. pointer to mark the original head, it is used to decide whether we have goes around 1 time
2. pointer to mark the `lastMaxNode` in the circle, that can help us insert the value which is either max of min of all existing values
3. a boolean flag to check if we have inserted the node already
3. the logic is to traverse the circular list until we reaches a circle
  - we will always maintain the `lastMaxNode` in the while loop
  - if we found a place inside the circle that the insertValue is larger than previous one and lower than next one, we can insert it, set the boolean flag to be true, and then break;
  - after we break or finishes a circle, check boolean flag
    - if already inserted, return original head
    - if not inserted yet, the insertValue must of either max of min of all nodes, we can just insert it after `lastMaxNode` (since the next node of `lastMaxNode` is the `firstMinNode`)

#### [LC] 845. Longest Mountain in Array
https://leetcode.com/problems/longest-mountain-in-array/

It is not a standard two-pointer problem, we just need to check every 2 elements in the array to check if it's uphill or downhill or flat.  
We use two flag/variable to maintain `upHillLength` and `downHillLength`  
- if it's uphill, `upHillLength++`
  - if currently `downHillLength > 0`, then we have found a mountain, store  `upHillLength + downHillLength + 1` to global result
  - reset the `upHillLength` and `downHillLength` 
- if it's downhill, only do `downHillLength++` when `upHillLength>0`, which means we found the peak
- if it's flat, we need to reset everything
  - but before resetting, check if `downHillLength > 0`, if yes then we store previous mountatin
- finally after the loop is done, don't forget to check  `downHillLength > 0` again to record the corner mountain
  - e.g. [1,2,1], we will not have chance to record the mountain inside loop

#### [LC] 977. Squares of a Sorted Array
https://leetcode.com/problems/squares-of-a-sorted-array/

It needs to sort square value of a sorted array, and the array could have negative, e.g. 
```java
[-4,-1,0,3,10]
```
we can use two pointers, one at beginning and another at the end, then move into center together, and add the larger one to the end of new array.

#### [LC][Medium] 1004. Max Consecutive Ones III
https://leetcode.com/problems/max-consecutive-ones-iii/


#### [LC] 1838. Frequency of the Most Frequent Element
https://leetcode.com/problems/frequency-of-the-most-frequent-element/

The trick here is to maintain sum of nums between l and r, and then calculate the diff to fill all nums to reach `nums[r]` by math:

```java

[1 2 4 4 6]
 l   r

 to make nums from l to r-1 to reach nums[r] which is 4, we need add up `4 * 2 - (1 + 2)`, where `4 *2` means there are 2 nums needs to reach nums[r]

```