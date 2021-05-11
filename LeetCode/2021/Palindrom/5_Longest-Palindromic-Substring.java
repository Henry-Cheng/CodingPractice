// https://leetcode.com/problems/longest-palindromic-substring/
class Solution {
    public String longestPalindrome(String s) {
        // dp[i][j] means substring(i, j+1) is palindrome
        Boolean[][] dp = new Boolean[s.length()][s.length()];
        String result = "";
        for (int j = 0; j < s.length(); j++) {
            for (int i = 0; i <= j; i++) {
                if (i == j) { // initially, all single char are true
                    dp[i][j] = true;
                } else if (i == j - 1) { // initially, all 1-step chars depends
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
                }
                if (dp[i][j] && j - i + 1 > result.length()) {
                    result = s.substring(i, j+1);
                }
            }
        }
        return result;
    }
}