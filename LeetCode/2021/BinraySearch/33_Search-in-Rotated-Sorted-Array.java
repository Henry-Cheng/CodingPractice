// https://leetcode.com/problems/search-in-rotated-sorted-array/
class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        while(l <= r) {
            int mid = l + (r - l)/2;
            if (nums[mid] == target) {
                return mid;
            } 
            
            if (nums[r] >= nums[mid]) {
                // right hand side is in order
                /**
                6,7,0,1,2,4,5
                **/
                if (nums[mid] < target && nums[r] >= target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else if (nums[r] < nums[mid]) {
                // left hand side is in order
                /**
                 4,5,6,7,0,1,2
                 l           r
                       m
                **/
                if (nums[mid] > target && nums[l] <= target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        return -1;
    }
}