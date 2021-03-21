// https://leetcode.com/problems/first-missing-positive/
class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) {
            return 1;
        }
        boolean[] exists = new boolean[nums.length + 2];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length + 1) {
                continue;
            } else { // the value is from 1 to n+1
                exists[nums[i]] = true;
            }
        }
        for (int i = 1; i < exists.length; i++) {
            if (!exists[i]) {
                return i;
            }
        }
        return 1;
    }
}