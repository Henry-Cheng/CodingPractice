## Greedy
### Default

#### [LC][Medium] 31. Next Permutation
https://leetcode.com/problems/next-permutation/

It is a greedy solution, since we initially needs to find the first descending pos when looking from right to left: 

```
        example 1
                        j-1  j
               i  i+1     
            6  2   5  4  3   1
        --> 6 [3]  5  4 [2]  1
        --> 6  3  [1  2  4   5]
        
        
        example2
                  j-1 j
               i  i+1
            6  4   5  3 2 1
        --> 6 [5] [4] 3 2 1
        --> 6  5  [1  2 3 4]
```

1. find the last ascending pair, like the `2~5` in example 1 (if we lookup from right to left, then it is the first ascending pair)
2. starting from `i+1`, find the next minimum num `j-1` that is strictly larger than nums[i], be careful it cannot be `>=`, must be `>` !!
3. swap `i` and `j-1`
4. now the `i+1 ~ nums.length-1` is already in descending order, reverse it
  - after swaping, the right hand side subarray is still sorted in descending order, since when traversing from right to left, we skipped the ascending order

```java
//code to swap -- it is not that hard
private void swap(int[] nums, int a, int b) {
    int tmp = nums[a];
    nums[a] = nums[b];
    nums[b] = tmp;
}
```

```java
// code to reverse -- it is not that hard
private void reverse(int[] nums, int left, int right) {
    if (left < right) {
        swap(nums, left, right);
        reverse(nums, left + 1, right - 1); 
    }
}
```

#### [LC][Medium] 419. Battleships in a Board
https://leetcode.com/problems/battleships-in-a-board/

Battleships can be 
```
X
X
X
```

or 

```
XXXX
```

and no 2 Battleships are next to each other.  

So we just need to fine the head of a battleship, by finding all 'X' whose upper and left position is empty, then we can count++ for it.



#### [LC] 1850. Minimum Adjacent Swaps to Reach the Kth Smallest Number
https://leetcode.com/contest/weekly-contest-239/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/

This is one of the weekly-contest-239 problem.  
The 1st half of the problem is the same as `31. Next Permutation`, and the 2nd half is to brute force find "how many adjacent swap we need to make two string the same".  

The 2nd half code is smart, which uses two pointers in `char[] arr1` and `char[] arr2`, it finds the p1 index in arr1 which equals to arr2, and then using swap to move p1 into the position of p2.
- need to see the code for details.

#### [LC] 134. Gas Station
https://leetcode.com/problems/gas-station/

If the starting point exists, it must start from the position where we lose the most of the gas, so that it can start to gain gas first to gather all the gas we need before we start losing.

Take an example like (here the number refers to the value gas[i] - cost[i])

[3,4,-12,4,-5,6]

The minimum tank value will happen at index 4, where the value is -5, because at there our tank is at the minimum value -6, which means if the result exists, it must start to gather gas at index 5 so that we can cover all the gas loss before we reach index 4.

#### [LC][Medium] 253. Meeting Rooms II
https://leetcode.com/problems/meeting-rooms-ii/

This problem is hard to think about.  

Using `Arrays.sort()` to sort by start time, then use PriorityQueue to maintain earliest ending interval at top of heap, then traverse the meetings:
  - if no overlap, then extend the end time of top meeting in the queue
  - if found overlap, then enqueue both meetings
  - the final result is the intervals in the heap.

NOTE:
- `int[]` is also an generic type that can be used in PriorityQueue

```
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
    return a[1]-b[1]; // ascending order
});
```

#### [LC] 388. Longest Absolute File Path
https://leetcode.com/problems/longest-absolute-file-path/

NOTE: `\t` and `\n` are only 1 char, not 2 char!!!

```java
        dir
            \tsubdir1
                \t\tfile1.ext
                \t\tsubsubdir1
            \tsubdir2
                \t\tsubsubdir2
                    \t\t\tfile2.ext
        dir2
            \tfile3.ext
        file4.ext
        
        using hashmap to store the "level" to "path length till current level" mapping:
        1 --> dir
        2 --> subdir1
        3 --> file1.ext  --> record path length
        3 --> subsubdir1 --> it will override level3, but doesn't matter!!
        2 --> subsidr2 --> it will override level2, but doesn't matter!
        3 --> subsubdir2 
        4 --> file2.ext --> record path length
        1 --> dir2
        2 --> file2.ext --> record path length
        1 --> file4.ext --> record path length
```

#### [LC] 455. Assign Cookies
https://leetcode.com/problems/assign-cookies/

Sort the two arrays and then using two pointers to move from largest to smallest.

Be careful, for primirity array, that `Arrays.sort()` can only work for ascending order, for descending order we cannot use comparator directly --> we have to convert it into `Integer[]`:

```java
int[] arr = Arrays.stream(originalArr) // convert to stream
                  .boxed() // convert to Integer[]
                  .sort((a,b) -> {b - a;}) // descending
                  .mapToInt(i -> i) // convert to int[]
                  .toArray();
```

#### [LC][Easy] 605. Can Place Flowers
https://leetcode.com/problems/can-place-flowers/

This is simple.  
Using 2 flags `boolean leftOk` and `boolean rightOk` to check if the pos ok, if yes, do count++ and set the pos to be 1.


#### [LC][Medium] 621. Task Scheduler
https://leetcode.com/problems/task-scheduler/

```python
if we only consider the char with max frequence:
A4 B4 C3 D3 E2
n=3
A _ _ _ A _ _ _ A _ _ _ A 
potentially we have max (4 - 1) * 3 = 9 idle slots, we need to fill the slots by other char

check B: B can contribute 3 to fill slots (B has one more left, but that can be executed after last A, so no worry)
check C: C can contribute 3
check D: D can contribute 3
check E: E can contirbute 2

so in total we have 9 - 3 - 3 - 3 - 2 = -2 idel slots left, it means we can fill all idle, and the rest chars can be executed after last A by one permutation

the total unit of time we need would be max(0, -2) + tasks.length = 16
```

#### [LC][Medium] 670. Maximum Swap
https://leetcode.com/problems/maximum-swap/

This question looks easy, but definitely NOT easy...

Using this greedy idea: for each digit left to right, if I can find a digit that is larger than this one, and appears after its position, then I can swap.  

e.g.

```java
9864758

if we look from left to right, when we found 4, and check if any num among 9~5 appears after 4, then we found 8 and 7 after 4, but 8 is larger, so we swap 8 and 4.

```

Three tick here:
1. convert num to char array to easy maintain it
2. maintain an integer array for the last appearance of each digit `int[] lastAppearPos`
3. for each digit, traverse from 9 to digit+1, and inside each traverse lookup in `int[] lastAppearPos` to figure out if there is a larger num appears after digit.

NOTE:
- to create string from char array, just do `String s = new String(charArry)`

The time complexity is `O(9*N)` which is `O(N)`.   
The space complexity is `O(# of digits)`, but considering an integer can only have up to 10 digits, so it is `O(10)` which is just `O(1)`

#### [LC] 1094. Car Pooling
https://leetcode.com/problems/car-pooling/

The idea is that: 

- treat each start_location and end_location as index to `++passanger` or `--passanger`
- then we can use either TreeMap or bucket sort (using `new int[1000]`) to store the total operation at each location --> either add xx passangers or remove yy passangers
- then we go through the TreeMap or `new int[1000]` to check if the initial capacity is enough

#### [LC][Medium] 1762. Buildings With an Ocean View
https://leetcode.com/problems/buildings-with-an-ocean-view/

This is very simple, just traverse from right to left and record the current max value.

Be careful to convert List<Integer> to int[]:  
```java
int[] arr = list.stream().mapToInt(i -> i).toArray();
```