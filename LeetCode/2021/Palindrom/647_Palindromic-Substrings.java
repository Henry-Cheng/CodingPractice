// https://leetcode.com/problems/palindromic-substrings/
class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int j = 0; j < s.length(); j++) {
            for (int i = 0; i <= j; i++) {
                if(i == j) {
                    dp[i][j] = true;
                } else if (i == j-1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
                }
                if (dp[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}