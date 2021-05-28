## DP

我们将动态规划的常见类型分为如下几种：
* 矩阵型
* 序列型
* 双序列型
* 划分型
* 区间型
* 背包型
* 状态压缩型
* 树型
其中，在技术面试中经常出现的是矩阵型，序列型和双序列型。划分型，区间型和背包型偶尔出现。状态压缩和树型基本不会出现（一般在算法竞赛中才会出现）。

矩阵型动态规划为例，一般题目会给你一个矩阵，告诉你有一个小人在上面走动，每次只能向右和向下走，然后问你比如有多少种方案从左上走到右下 (http://www.lintcode.com/problem/unique-paths/)。这种类型状态表示的特点一般是使用坐标作为状态，如f[i][j]表示走到(i,j)这个位置的时候，一共有多少种方案。状态的转移则是考虑是从哪儿走到(i,j)这个坐标的。

而序列型的动态规划，一般是告诉你一个序列

双序列的动态规划一般是告诉你两个字符串或者两个序列。

可以使用动态规划的问题一般都有一些特点可以遵循。如题目的问法一般是三种方式：
1. 求最大值/最小值
2. 求可不可行
3. 求方案总数
如果你碰到一个问题，是问你这三个问题之一的，那么有90%的概率是使用动态规划来求解。

要重点说明的是，如果一个问题让你求出“所有的”方案和结果，则肯定不是使用动态规划。


### Default
https://blog.csdn.net/weixin_26723981/article/details/108892305

https://www.jianshu.com/p/4e4ad368ae15

Dynamic Programming (DP) has 2 implementations
- Top-down
  - the same as DFS with `memorization`, which relies on revusion
  - Top-down DP is nothing else than ordinary recursion, enhanced with memorizing the solutions for intermediate sub-problems. When a given sub-problem arises second (third, fourth...) time, it is not solved from scratch, but instead the previously memorized solution is used right away. This technique is known under the name memoization (no 'r' before 'i').
  - e.g. Fibonacci. 
    - Just use the recursive formula for Fibonacci sequence, but build the table of fib(i) values along the way, and you get a Top-down DP algorithm for this problem (so that, for example, if you need to calculate fib(5) second time, you get it from the table instead of calculating it again).
- Bottom-top
  - it's an improvement on Top-down to avoid stack overflow, which relies on iteration and `tabulation`
  - Bottom-top DP is also based on storing sub-solutions in memory, but they are solved in a different order (from smaller to bigger), and the resultant general structure of the algorithm is not recursive. 
  - Bottom-top DP algorithms are usually more efficient, but they are generally harder (**and sometimes impossible**) to build, since it is not always easy to predict which primitive sub-problems you are going to need to solve the whole original problem, and which path you have to take from small sub-problems to get to the final solution in the most efficient way.
  - e.g. Longest common subsequence (LCS) problem is a classic Bottom-top DP example.

### DP - Two Sequence   
For this type of questions would give you 2 sequences, and the dp array would be 2D and dp[i][j] ususally means the ith spot in sequence1 and jth spot in sequence2.  
Then we try to find relationship of dp[i][j] with dp[i-1][j-1], dp[i][j-1] and dp[i-1][j].

e.g.  
- 72. Edit Distance
- 44. Wildcard Matching
- 10. Regular Expression Matching



#### [LC] 39. Combination Sum
https://leetcode.com/problems/combination-sum/

```java
    split target into an array
    dp[0, 1 , 2, 3, .., target - 1, target]
    dp[i] points to all the combination whose sum is i
    e.g.
    candidates = [2,3,5], target = 8
    now build dp array from 1 to 8
        dp[0] = [] // dummy head node
        dp[1] = [] // no combination
        dp[2] = [[2]] // (dp[1] + dp[1]), dp[2]
        dp[3] = [[3]] // (dp[1] + dp[2]), dp[3]
        dp[4] = [[2,2]] // (dp[1] + dp[3]), (dp[2] + dp[2]), dp[4]
        dp[5] = [[2,3],[5]] // dp[1] + dp[4], dp[2] + dp[3], dp[5]
        dp[6] = [[2,2,2],[3,3]] // 
        dp[7] = [[2,2,3],[2,5]] // dp[2] + dp[5], dp[3]+dp[4], has duplicate for [2,2,3]
        dp[8] = [[2,2,2,2],[2,3,3],[3,5]] // could have duplicate, dp[3]+dp[5] has duplicate with dp[2] + dp[6] for [2,3,3]
        for duplicate combination, we can use HashSet<List<Integer>> to cover it
```

#### [LC] 40. Combination Sum II
https://leetcode.com/problems/combination-sum-ii/

NOTE:
- using HashSet to dedupe List<Integer>
- convert hashset to list by `hashset.stream().collect(Collectors.toList())`

#### [LC] 53. Maximum Subarray
https://leetcode.com/problems/maximum-subarray/

[Kadane's algorithm](https://leetcode.com/problems/maximum-subarray/discuss/369797/kadanes-algorithm-with-detailed-explanation-and-example-python)

```java
int currentMax = nums[0]
for (int i = 1; i < nums.length; i++) { // NOTE: start from 2nd element
    // if we have to use nums[i], do we include previous accumulated result or just throw them away?
    currentMax = Math.max(currentMax + nums[i], nums[i]);
    // maxInTotal only records the max profit we have so far
    maxInTotal = Math.max(maxIfWeUseThisElement, maxInTotal);
}
```

#### [LC] 62. Unique Paths
https://leetcode.com/problems/unique-paths/

- `T(m*n)`
- `S(m*n)`

#### [LC] 64. Minimum Path Sum
https://leetcode.com/problems/minimum-path-sum/

- backtracking/brute-force
  - T(2 ^ (m+n)) since for each move we have at most 2 options
  - S(m+n) since recusion depth is m + n
- top-down DP with memorization
  - T(mn) since each node would have recursive stack push
  - S(mn) since we use a dp matrix
- bottom-up DP with in-place matrix update
  - T(mn) since each node would be processed
  - S(1) since no extra space


Be careful to check boundary scenarios when `i == -1 && j == -1`.

#### [LC][Hard] 72. Edit Distance
https://leetcode.com/problems/edit-distance/

Very classical two-sequence DP problem. We firstly think about recursion way, and then derive into DP.

1. Recursion/backtracking
If we have two pointers traversing both string at the same time, we will see two situations:  
  1. word1.charAt(i) == word2.charAt(j) --> the dist is 0
  2. cword1.charAt(i) != word2.charAt(j) --> now we can do 3 things (NOTE: we only change word1 here, it's the same effect as we only change word2)
    1. replace charAt(i) to charAt(j) --> dist++, and recursive look at i+1 and j+1
    2. insert char j to word1 --> dist++, and recursive look at i and j+1
    3. delete char i in word1 --> dist++, and recursive look at i+1 and j
    ==> now we just choose 1 of the 3 options that has min dist

2. DP
DP is the reverse direction of recursion.  
dp[i][j] means the current dist till i in word1 and j in word2
- if (word1.charAt(i) == word2.charAt(j)) 
  - `dp[i][j] = dp[i-1][j-1]` --> no need to update dist
- if (word1.charAt(i) != word2.charAt(j)) 
  - `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])` --> it represents replace/insert/delete to word1


```java
...
for (int i = 0; i <= word1.length(); i++) {
    dp[i][0] = i; // when word2 is empty, the distance is just length of word1
}
for (int j = 0; j <= word2.length(); j++) {
    dp[0][j] = j; // when word1 is empty, the distance is just length of word2
}
...

if (c1 == c2) {
    dp[i][j] = dp[i-1][j-1];
} else {
    int replace = dp[i-1][j-1]; // replace
    int insert = dp[i-1][j]; // insert into word1
    int delete = dp[i][j-1]; // delete from word2
    int min = Math.min(replace, insert); 
    dp[i][j] = Math.min(min, delete) + 1;
}

```

#### [LC][Hard] 44. Wildcard Matching
https://leetcode.com/problems/wildcard-matching/

Very classical two-sequence DP problem like `72. Edit Distance`.

```java
                j1  j2 j3  j4
                0   a   b   *
        i1  0   T   F   F   F
        i2  a   F   T   F   F
        i3  b   F   F   T   T
        i4  c   F   F   F   T
        i5  d   F   F   F   T
```

1. what does dp mean? --> dp[i][j] means when ith char in s and jth char in p, if it's matched 
2. initialize --> dp[i][0] is always false, dp[0][j] depends on whether p[j] is `*` and dp[0][j-1] is true
3. state transition
  - if `s[i] == p[j]`, just check dp[i-1][j-1]
  - if `s[i] != p[j]`, there are 2 scenarios
    1. if `p[j] == '*'`, then there could be 3 cases:
      - when we are comparin abcd->ab*
      - if * is empty,                    e.g. abc->ab  ==> check dp[i-1][j-1]
      - if * matches 1 char s[i],         e.g. abcd->ab ==> check dp[i][j-1]
      - if * matches all previous char,   e.g. abc->ab* ==> check dp[i-1][j]
      - dp[i][j] =  dp[i-1][j-1] || dp[i][j-1] || dp[i-1][j]
    2. if `p[j] != '*'`, then dp[i][j] must be false, we can do nothing

The time complexity is O(SP), and space complexity is O(SP)


#### [LC] 10. Regular Expression Matching
https://leetcode.com/problems/regular-expression-matching/

Similar like `44. Wildcard Matching`, but a more complicated version, since now `*` does not mean any substring, but used together with previous character, e.g. `c*` means any length of duplicate `c` (including 0-length).

The trick here is to treat `c*` as a whole, and whenever we check s[i] and p[j], we always check if p[j+1] is `*` or not, if yes then we skip p[j] but consider p[j+1] directly.

Be carful 

1. if `c` matches, we consider 3 scenario: `c*` is empty, `c*` matches 1 char, `c*` matches all previous char
  - in this situation, we need to jump j at 2-span every time and skip `c` position
  - e.g. `dp[i][j+1] = dp[i][j-1] || dp[i-1][j-1] || dp[i-1][j+1];` we don't use `j` at all!!! it's always j+1 or j-1
2. if `c` does not math, we only consider `c*` is empty


```java
                j1  j2 j3  j4  j5   j6
                0   [c  *] [a  *]   b
        i1  0   T       T      T    F
        i2  a   F       F      T    F
        i3  a   F       F      T    F
        i4  b   F       F      F    T
```

#### [LC] 77. Combinations
https://leetcode.com/problems/combinations/

Pseudo code:
```
combo(candidates=1234, k = 3) = 1 * combo(candidates=234, k = 2) + combo (candidates=234, k = 3);
```
- be careful when using memory in recusion!! Need to do deep copy of list!!!
- you can use memory like this `List<List<Integer>>[][] memory = new LinkedList[n+2][k+2];`


For similar question like [39. Combination Sum](https://leetcode.com/problems/combination-sum/), we can have this pseudo code (each num can be reused):
```
(candidates=1234, sum=5) = 1 * (candiates=1234, sum=4) + (candidates=234, sum=5)
```

#### [LC] 96. Unique Binary Search Trees
https://leetcode.com/problems/unique-binary-search-trees/

#### [LC] 121. Best Time to Buy and Sell Stock
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

Also using Kadane's algorithm, by converting the prices array into gap array, and treate it as max subarray problem:
```java
convert to Kadane's algorithm by calculating the gaps
prices:  7   1   5   3   6   4
  gaps:  -6  4   -2  3   -2

meaning: -6: buy at day 0 and sell at day 1
          4: buy at day 1 and sell at day 2
     -6 + 4: buy at day 0 and sell at day 2 (buy and sell same day would off-set)   
convert it to find max sum of subarray
``` 

#### [LC][Medium] 139. Word Break
https://leetcode.com/problems/word-break/

dp[i] = dp[j] && substring(j,i) in wordDict

- Time complexity : O(n^3). There are two nested loops, and substring computation at each iteration. Overall that results in O(n^3) time complexity.
- Space complexity : O(n). Length of dp array is n+1

```java
        boolean dp[] = new boolean[n + 1];
        dp[0] = true; // dp[0] is a dummy node at beginning
        for (int i = 1; i < n + 1; i++) { // check if dp[i] could be true, i points to i-1 in s
            for (int j = 0; j < i; j++) { // check whether 0~i is true
                if (dp[j] && wordDict.contains(s.substring(j,i))) {
                    dp[i] = true;
                }
            }
        }
```

#### [LC][Hard] 140. Word Break II
https://leetcode.com/problems/word-break-ii/

Using the same recursion way as 139, but add a beautiful step to append current found word to all of the previous found word list.

NOTE:
-  using hashmap computeIfAbsent: `allWordPos.computeIfAbsent(i, words-> new ArrayList<String>());`, if key `i` does not exist, put i and new object there


#### [LC] 152. Maximum Product Subarray
https://leetcode.com/problems/maximum-product-subarray/

Similar like `53. Maximum Subarray`, but we cannot use Kadane's algorithm directly, since the product could have positive and negative
- we can extend Kadane's algorithm, using `int[][] dp = new int[nums.length][2]` to record both max (positive) and min (negative) value, and the state trainsition function could be:

```java
        dp[i][0] = max (dp[i-1][0] * nums[i], dp[i-1][1]*nums[i], nums[i])
        dp[i][1] = min (dp[i-1][0] * nums[i], dp[i-1][1]*nums[i], nums[i])
``` 

An improvement to reduce space complexity:
- we don't need to using array to record all `dp[i][0]` and `dp[i][1]`, considering the dp[i] is only related with dp[i-1], we can using two int numbers to represent current max and min value, which makes the time complexity drops to O(1)

#### [LC] 174. Dungeon Game

https://leetcode.com/problems/dungeon-game/

The followings are copied from https://yutianx.info/2015/04/10/2015-04-10-leetcode-Dungeon-Game/

- dp[i][j]，那么它是从dp[i+1][j]或者dp[i][j+1]走过来的
- 需要将[i+1][j],[i][j+1]中选一个需要血量少的，减去dungeon[i][j]，再和1比较大小
- 我们需要从下标由大到小遍历数组

```java
dp[i][j] = max(
  1, 
  min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]
  )
)
```

#### [LC] 300. Longest Increasing Subsequence
https://leetcode.com/problems/longest-increasing-subsequence/

The followings are copied from https://soulmachine.gitbooks.io/algorithm-essentials/content/java/dp/longest-increasing-subsequence.html

我们来尝试用DP来解决这题。最重要的是要定义出状态。首先从状态扩展这方面看，对于数组中的一个元素，它往后走，凡是比它大的元素，都可以作为下一步，因此这里找不到突破口。

我们换一个角度，从结果来入手，我们要求的最长递增子序列，一个递增子序列，肯定是有首尾两个端点的，假设我们定义 f[i] 为以第i个元素为起点的最长递增子序列，那么 f[i]和f[j]之间没有必然联系，这个状态不好用。

定义f[i]为以第i个元素为终点的最长递增子序列，那么如果`i<j`且`nums[i]<nums[j]`，则f[i]一定是f[j]的前缀。这个状态能表示子问题之间的关系，可以接着深入下去。

现在正式开始定义，我们定义状态f[i]为第i个元素为终点的最长递增子序列的长度，那么状态转移方程是

`f[j] = max{f[i], 0 <= i < j && f[i] < f[j]} + 1`

时间复杂度O(n^2)，空间复杂度O(n)

NOTE: initialize all dp to be 1!!!!

```java
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // this is important!!!
        int result = 1;
        for (int j = 1; j < nums.length; j++) {
            for (int i = 0; i < j; i++) {
                if (nums[i] < nums[j]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
            result = Math.max(result, dp[j]);
        }
        return result;
```

#### [LC][Medium] 337. House Robber III
https://leetcode.com/problems/house-robber-iii/

A very classic way of using DP in a tree.  

1. if the parent of root is robbed
  - cannot rob root
  - check root.left and root.right with `isParentRobbed = false`
2. if the parent of root is not robbed, there are 2 options
  1. rob root, then set `isParentRobbed = false` for root.left and root.right
  2. not rob root, then we can check root.left and root.right with `isParentRobbed = false`
  3. return the max of #2.1 and #2.2 

When using memorization, we can use separate hashmap for robbed and notRobbed memo.


#### [LC] 377. Combination Sum IV
https://leetcode.com/problems/combination-sum-iv/

top-down idea: recusion + memory

- whenever we choose one element from array, we can minus the element from target, then recusive call the function.
- when `nums[i] == target`, we reach the end, do `count++`
- using memory to speed up the recursion


#### [LC] 403. Frog Jump
https://leetcode.com/problems/frog-jump/

Define a bi-dimensional boolean array to record on stone i whether by step j we can reach it.  
e.g.   
if we have 7 stones, then we can at most make 7 distance per one jump, so we can create a `7*7` array to record true/false we can reach it.

NOTE:  
- though there were 3-layer for loop, the time complexity is acutally O(n^2)


#### [LC][Medium] 983. Minimum Cost For Tickets
https://leetcode.com/problems/minimum-cost-for-tickets/

```java
int nextDayOf1DayAgoBy1Pass = (i - 1 >= 0 ? dp[i-1] + costs[0] : costs[0]);
int nextDayOf7DayAgoBy7Pass = (i - 7 >= 0 ? dp[i-7] + costs[1] : costs[1]);
int nextDayOf30DayAgoBy30Pass = (i - 30 >= 0 ? dp[i-30] + costs[2] : costs[2]);
int min = Math.min(nextDayOf1DayAgoBy1Pass, nextDayOf7DayAgoBy7Pass);
min = Math.min(min, nextDayOf30DayAgoBy30Pass);
dp[i] = min;
```

#### [LC][Medium] 322. Coin Change
https://leetcode.com/problems/coin-change/

Similar like `[Medium] 983. Minimum Cost For Tickets`, but this time we will try all coins instead of all passes, and we count the number of coins instead of the price.  
Be careful that if amount could not be made, we need to set it to be -1.

#### [LC][Medium] 494. Target Sum
https://leetcode.com/problems/target-sum/

This is a standard DP problem.  

The recusion + memorization ideas is simple, for each num in the array, try `num * 1` and `num * -1` on the target, then do the recursion.  

NOTE: the memorization could be 2D array like `new int[nums.length][target + maxSum(nums[i])]`

The bottom up idea is a reversed version of recusion + memorization.  
--> We can use `dp[i][j]` to present the count of operations if using nums array `0~i` to reach `target j` 


#### [LC] 1155. Number of Dice Rolls With Target Sum
https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/

https://blog.csdn.net/Tc_To_Top/article/details/100186048

`O(d*f*target)`