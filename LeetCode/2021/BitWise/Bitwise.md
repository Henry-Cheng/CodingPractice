## BitWise
### Default
#### 201. Bitwise AND of Numbers Range
https://leetcode.com/problems/bitwise-and-of-numbers-range/

- option1: stupid way: `Integer.toBinaryString(int i)`
- option2:

```java
  public int rangeBitwiseAnd(int m, int n) {
    int shift = 0;
    // find the common 1-bits
    while (m != n) { // remove all different bits in m and n, until they are the same (so that 1 & 1 or  0 & 0 would have no change)
      m = m >>1; 
      n = n >>1;
      ++shift;
    }
    return m << shift; // now shift back
  }
```

#### [LC][Medium] 393. UTF-8 Validation
https://leetcode.com/problems/utf-8-validation/

Using `Integer.toBinaryString(int i)` to get the binary string.  
Be careful that the string would only start with the first `1`.  

e.g.
```java
1 --> 1
30 --> 11110
145 --> 10010001
```

In this question, since each number is less than 256, it means the max num is less than 2^8, so that the max string length would be 8.  

In this quesiton, we just need to check 
- if the string starts with 0 (if it's less than length 8) --> 1-byte
- if prefix 1 is more than 4 --> invalid
- if prefix 1 is less than 4 (let's say n) --> check the # of strings including this one is n, if not then it's invalid

#### [LC][Medium] 338. Counting Bits
https://leetcode.com/problems/counting-bits/

This is very interesting.  
It looks like a bitwise problem, but actually is a DP problem.  

1. if dp[i] is odd, dp[i] = dp[i-1] + 1;
  - e.g. 2 --> 10, 3 --> 11
2. if dp[i] is even, dp[i] = dp[i/2];
  - e.g. 10 --> 1010, dividend by 2 equals to shift right, which results in 101 --> 5




