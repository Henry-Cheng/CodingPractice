// https://leetcode.com/problems/find-peak-element/
class Solution {
    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        } 
        int l = 0;
        int r = nums.length-1;
        while(l <= r) {
            int mid = l + (r - l)/2;
            if (mid + 1 >= nums.length) {
                return l;
            }
            if (nums[mid] > nums[mid+1]) {
                r = mid-1;
            } else if (nums[mid] < nums[mid+1]) {
                l = mid+1;
            } else if (nums[mid] == nums[mid+1]) {
                l = mid+1;
            }
        }
        return l;
    }
}