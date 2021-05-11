// https://leetcode.com/problems/wildcard-matching/
class Solution {
    public boolean isMatch(String s, String p) {
        /**
                j1  j2 j3  j4
                0   a   b   *
        i1  0   T   F   F   F
        i2  a   F   T   F   F
        i3  b   F   F   T   T
        i4  c   F   F   F   T
        i5  d   F   F   F   T
        **/
        // 1. waht does dp mean? dp[i][j] means when ith char in s and jth char in p, if it's matched 
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // 2. initialize
        // dp[i][0] is always false
        // dp[0][j] would depends
        dp[0][0] = true;
        for(int j = 1; j <= p.length(); j++) {
            if (p.charAt(j-1) == '*' && dp[0][j-1]) {
                dp[0][j] = true;
            }
        }
        // 3. state transition
        for (int i = 1; i <= s.length(); i++) {
            char charS = s.charAt(i-1);
            for (int j = 1; j <= p.length(); j++) {
                char charP = p.charAt(j - 1);
                if (charS == charP || charP == '?') { // if equals, just check i-1,j-1
                    dp[i][j] = dp[i-1][j-1];
                } else if (charP == '*') {
                    // if s->p is abcd->ab*
                    // then the 3 represents abc->ab, abcd->ab, abc->ab*
                    // each means: * is empty, * matches current char, * matches previous char
                    dp[i][j] = dp[i-1][j-1] || dp[i][j-1] || dp[i-1][j];
                } else { // totally different
                    dp[i][j] = false; // actually we can do nothing here
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}