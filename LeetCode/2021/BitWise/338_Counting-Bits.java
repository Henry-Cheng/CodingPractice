// https://leetcode.com/problems/counting-bits/
class Solution {
    public int[] countBits(int n) {
        /**
        if dp[i] is odd, dp[i] = dp[i-1] + 1;
        e.g. 2 --> 10, 3 --> 11
        if dp[i] is even, dp[i] = dp[i/2];
        e.g. 10 --> 1010, dividend by 2 equals to shift right, which results in 101 --> 5
        **/
        int[] result = new int[n+1];
        result[0] = 0;
        for(int i = 1; i <= n; i++) {
            if (i % 2 == 0) { // even
                result[i] = result[i/2];
            } else { // odd
                result[i] = result[i-1] + 1;
            }
        }
        return result;
    }
}