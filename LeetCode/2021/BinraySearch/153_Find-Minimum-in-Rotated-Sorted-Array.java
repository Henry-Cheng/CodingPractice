// https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
class Solution {
    public int findMin(int[] nums) {
        /**
        0 1 2
        3 1 2
        l   r
          m
          r
        l
        m
        **/
        int l = 0;
        int r = nums.length - 1;
        while(l <= r) {
            int mid = l + (r-l)/2;
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else if (nums[mid] < nums[r]) {
                r = mid;
            } else if (nums[mid] == nums[r]) {
                // since it's all unique, now l == r
                return nums[l];
            }
        }
        return r >= 0 ? nums[r] : nums[l];
    }
}