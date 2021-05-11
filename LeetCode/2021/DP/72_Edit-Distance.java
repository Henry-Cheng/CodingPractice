// https://leetcode.com/problems/edit-distance/
class Solution {
    public int minDistance(String word1, String word2) {
        // good video: https://www.bilibili.com/s/video/BV125411x7VB
        if (word1.equals(word2)) {
            return 0;
        }
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        // initialize dp
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i; // when word2 is empty, the distance is just length of word1
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j; // when word1 is empty, the distance is just length of word2
        }
        for (int i = 1; i <= word1.length(); i++) {
            char c1 = word1.charAt(i - 1);
            for (int j = 1; j <= word2.length(); j++) {
                char c2 = word2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int replace = dp[i-1][j-1]; // replace
                    int insert = dp[i-1][j]; // delete from word1
                    int delete = dp[i][j-1]; // insert into word2
                    int min = Math.min(replace, insert); 
                    dp[i][j] = Math.min(min, delete) + 1;
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}