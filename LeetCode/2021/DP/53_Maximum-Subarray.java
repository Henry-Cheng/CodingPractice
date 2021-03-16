// https://leetcode.com/problems/maximum-subarray/solution/
class Solution {
    public int maxSubArray(int[] nums) {
        int maxIfWeUseThisElement = nums[0]; // initially the max value is the 1st element
        int maxInTotal = maxIfWeUseThisElement;
        if (nums.length == 1) {
            return maxInTotal;
        }
        for (int i = 1; i < nums.length; i++) { // NOTE: start from 2nd element
            // if we have to use nums[i], do we include previous accumulated result or just throw them away?
            maxIfWeUseThisElement = Math.max(maxIfWeUseThisElement + nums[i], nums[i]);
            // maxInTotal only records the max profit we have so far
            maxInTotal = Math.max(maxIfWeUseThisElement, maxInTotal);
        }
        return maxInTotal;
    }
}