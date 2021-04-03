// https://leetcode.com/problems/maximum-product-subarray/submissions/
class Solution {
    public int maxProduct(int[] nums) {
        // define dp[i][2]
        // dp[i][0] means the positive max product by index i
        // dp[i][1] means the negative min product by index i
        
        // dp[i][0] = max (dp[i-1][0] * nums[i], dp[i-1][1]*nums[i], nums[i])
        // dp[i][1] = min (dp[i-1][0] * nums[i], dp[i-1][1]*nums[i], nums[i])

        int[][] dp = new int[nums.length][2];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dp[0][0] = nums[0];
                dp[0][1] = nums[0];
            } else {
                dp[i][0] = Math.max(Math.max(dp[i-1][0] * nums[i], dp[i-1][1] * nums[i]), nums[i]);
                dp[i][1] = Math.min(Math.min(dp[i-1][0] * nums[i], dp[i-1][1] * nums[i]), nums[i]);
            }
            max = Math.max(max, dp[i][0]);
        }
        return max;
    }
}