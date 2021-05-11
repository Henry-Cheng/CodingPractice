// https://leetcode.com/problems/move-zeroes/
class Solution {
    public void moveZeroes(int[] nums) {
        int nonZeroPos = 0;
        int zeroPos = 0;
        while(nonZeroPos < nums.length && zeroPos < nums.length) {
            while(nonZeroPos < nums.length && nums[nonZeroPos] == 0) {
                nonZeroPos++;
            }
            while(zeroPos < nums.length && nums[zeroPos] != 0) {
                zeroPos++;
            }
            if (nonZeroPos >= nums.length || zeroPos >= nums.length) {
                break;
            } else {
                if (zeroPos < nonZeroPos) {
                    nums[zeroPos] = nums[nonZeroPos];
                    nums[nonZeroPos] = 0;
                    nonZeroPos++;
                    zeroPos++;
                } else {
                    nonZeroPos++;
                }

            }
        }
    }
}