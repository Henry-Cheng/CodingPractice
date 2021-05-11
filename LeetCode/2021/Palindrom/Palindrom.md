# Palindrom

### Default

#### [LC] 5. Longest Palindromic Substring
https://leetcode.com/problems/longest-palindromic-substring/

Very standard DP solution:  
- if we fixed position j in string, then try position i all the way from 0 to j, to check if `dp[i][j]` is palindrom:

```java

 b    a    b     a  d
                j=a
i=b
     i=a
          i=b
                i=a
  
```


```java
for (int j = 0; j < s.length(); j++) {
    for (int i = 0; i <= j; i++) {
        if (i == j) { // initially, all single char are true
            dp[i][j] = true;
        } else if (i == j - 1) { // initially, all 1-step chars depends
            dp[i][j] = (s.charAt(i) == s.charAt(j));
        } else {
            dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
        }
    }
 }
```

#### [LC] 647. Palindromic Substrings
https://leetcode.com/problems/palindromic-substrings/

This is the same as `5. Longest Palindromic Substring`, but this time we do not return the longest palindrom, but count the number of palindrom we can have.



#### [LC] 516. Longest Palindromic Subsequence
https://leetcode.com/problems/longest-palindromic-subsequence/

Similar like `5. Longest Palindromic Substring`, but is harder since the char could be not-consecutive.  

The first 2 chicks are the same as `5. Longest Palindromic Substring`
```java
if (i == j)   dp[i][j] = 1;
if (i == j-1) dp[i][j] = 1;
if (i <  j-1) dp[i][j] = dp[i+1][j-1] + 2 (when s[i]==s[j])
                    or = max(dp[i+1][j], dp[i][j-1]) (when s[i]!=s[j])
```
The `max(dp[i+1][j], dp[i][j-1])` part is hard to understand....

it means, when s[i]!=s[j], we lose the chance to add 2 char, but we can still try to add 1 char to make the sub-sequence longer --> so we try to add s[i] or s[j] to the subsequence.  

Since we need to know `dp[i+1][j]` beforehand, we can write the for loop to start from right-hand side to left:

```java
for(int i = n; i >=0; i--) {
    for(int j = i; j <= n; j++) {
        dp[i][j] = ...
    }
}

```

#### [LC] 1216. Valid Palindrome III
https://leetcode.com/problems/valid-palindrome-iii/

The same as `516. Longest Palindromic Subsequence`, after we find the longest palindrom, we can know the number of char we can remove from string, and compare it with k.



