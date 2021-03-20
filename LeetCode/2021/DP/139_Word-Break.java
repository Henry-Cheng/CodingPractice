// https://leetcode.com/problems/word-break/solution/
class Solution {
    /**
    Time complexity : O(n^3). There are two nested loops, and substring computation at each iteration. Overall that results in O(n^3) time complexity.
    **/
    public boolean wordBreak(String s, List<String> wordDict) {
        // corner case
        int n = s.length();
        if (n == 1) {
            return wordDict.contains(s);
        }
        // now s.length() >= 2
        // n = 13
        //  s     0 1 2 3 4 5 6 7 8 9 10 11 12
        //        a p p l e p e n a p  p  l  e
        // dp   0 1 2 3 4 5 6 7 8 9 10 11 12 13
        //      j i 
        //dp[i] = dp[j] && substring(j,i) in wordDict
        boolean dp[] = new boolean[n + 1];
        dp[0] = true; // dp[0] is a dummy node at beginning
        for (int i = 1; i < n + 1; i++) { // check if dp[i] could be true, i points to i-1 in s
            for (int j = 0; j < i; j++) { // check whether 0~i is true
                if (dp[j] && wordDict.contains(s.substring(j,i))) {
                    dp[i] = true;
                    break; // no need to count all possibilities
                }
            }
        }
        return dp[n];
    }
    
    // LC solution
    public boolean wordBreakLC(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}