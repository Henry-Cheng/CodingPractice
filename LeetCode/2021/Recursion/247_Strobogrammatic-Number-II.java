// https://leetcode.com/problems/strobogrammatic-number-ii/
class Solution {
    public List<String> findStrobogrammatic(int n) {
        return helper(n, false);
    }
    
    public List<String> helper(int n, boolean shouldAddZero) {
        if (n == 0) {
            return Arrays.asList("");
        } else if (n == 1) {
            return Arrays.asList("0", "1", "8");
        }
        List<String> newList = new LinkedList<>();
        List<String> prevResult = helper(n - 2, true);
        for (String num : prevResult) {
            newList.add("6" + num + "9");
            newList.add("9" + num + "6");
            newList.add("1" + num + "1");
            newList.add("8" + num + "8"); 
            if (shouldAddZero) {
                newList.add("0" + num + "0");
            }
        }
        return newList;
    }
    
    public List<String> findStrobogrammatic_dp(int n) {
        /**
        n == 1
        0, 1, 8
        
        **/
        List<String>[] dp = new List[n + 1];
        dp[0] = Arrays.asList("");
        if (n == 0) {
            return dp[0];
        }
        dp[1] = Arrays.asList("0", "1", "8");
        if (n == 1) {
            return dp[1];
        }
        int start = n % 2; // if even, starts from 0; if odd, starts from 
        for (int i = start + 2; i <= n; i+=2) { 
            dp[i] = fill(dp[i - 2], i != n);
        }
        Collections.sort(dp[n]);
        return dp[n];
    }
    
    private List<String> fill(List<String> list, boolean shouldFillZero) {
        List<String> newList = new LinkedList<>();
        for (String num : list) {
            newList.add("6" + num + "9");
            newList.add("9" + num + "6");
            newList.add("1" + num + "1");
            newList.add("8" + num + "8"); 
            if (shouldFillZero) {
                newList.add("0" + num + "0");
            }
        }
        return newList;
    }
    
}