// https://leetcode.com/problems/search-insert-position/
class Solution {
    public int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        while(l <= r) {
            int mid = l + (r-l)/2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                l = mid+1;
            } else if (nums[mid] > target) {
                r = mid-1;
            }
        } 
        // 0 1 2 3
        // 1 3 5 6
        // l     r
        //   m
        //     l r
        //     m
        //       l
        return l;
    }
}