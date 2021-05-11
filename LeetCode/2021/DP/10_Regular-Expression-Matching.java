// https://leetcode.com/problems/regular-expression-matching/
class Solution {
    public boolean isMatch(String s, String p) {
        /**
                j1  j2 j3  j4  j5   j6
                0   [c  *] [a  *]   b
        i1  0   T       T      T    F
        i2  a   F       F      T    F
        i3  a   F       F      T    F
        i4  b   F       F      F    T
        **/
        //1. define dp[i][j] means when ith char in s and jth char in p, whether it's matched
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        
        //2. initialize
        // dp[i][0] is always false except dp[0][0]
        dp[0][0] = true;
        // dp[0][j] depends on * situation
        for(int j = 1; j <= p.length(); j++) {
            //System.out.println(p.charAt(j-1));
            if (j + 1 <= p.length() && p.charAt(j) == '*' && dp[0][j-1]) {
                // treat c* as one pos, and ignore dp[0][j]
                dp[0][j+1] = true;
                //System.out.println(p.charAt(j) + " is " + dp[0][j+1]);
            }
            
        }
        //3. state transition
        for (int i = 1; i <= s.length(); i++) {
            char charS = s.charAt(i - 1);
            //System.out.println("when s is " + charS);
            for (int j = 1; j <= p.length(); j++) {
                char charP = p.charAt(j-1);
                //System.out.println("when p is " + charP);
                if (j + 1 <= p.length() && p.charAt(j) == '*') { // see c*
                    // be careful we should not use j here, but either j-1 or j+1 !!!
                    if (charS == charP || charP == '.') {
                        // it means c* is empty, c* matches 1 char, c* matches previous char
                        dp[i][j+1] = dp[i][j-1] || dp[i-1][j-1] || dp[i-1][j+1];
                    } else { // can only treat as empty
                        dp[i][j+1] = dp[i][j-1];
                    }
                    //System.out.println(p.charAt(j) + " is " + dp[i][j+1]);
                    j++; // jump 2 pos
                } else {
                    if (charS == charP || charP == '.') {
                        dp[i][j] = dp[i-1][j-1]; // check i-1,j-1
                    } else {
                        dp[i][j] = false; // or do nothing
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}