// https://leetcode.com/problems/longest-increasing-subsequence/
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums.length;
        }
        // dp[j] records the "length" of such an sequence that ends at nums[j] 
        // if i < j, and nums[i] < nums[j], dp[i] is the prefix of dp[j]
        // dp[j] = max{dp[i], 0 <= i < j && nums[i] < nums[j]} + 1
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // this is important!!!
        int result = 1;
        for (int j = 1; j < nums.length; j++) {
            for (int i = 0; i < j; i++) {
                if (nums[i] < nums[j]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
            result = Math.max(result, dp[j]);
        }
        return result;
    }
}