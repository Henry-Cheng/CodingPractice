// https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        Arrays.fill(result, -1);
        // base case
        if (nums.length == 0) {
            return result;
        }
        // find left most
        // 0 1 2 3 4 5
        // 5 7 7 8 8 10
        // l         r
        //       m
        //   m
        //     
        // binary search
        int start = -1;
        int end = -1;
        int left = 0;
        int right = nums.length - 1;
        // first round is to find leftmost target
        while(left < right) {
            int mid = (left + right)/2; // try find mid close to left
            if (nums[mid] >= target) {
                right = mid; // find the leftMost
            } else {
                left = mid + 1;
            }
        }
        // now left == right
        if (nums[right] != target) { // not found
            return result;
        }
        result[0] = right;
        // find right most
        // 0 1 2 3 4 5
        // 5 7 7 8 8 10
        //       l   r
        //         m
        //   m
        // 
        right = nums.length - 1; // reset right
        while(left < right) {
            int mid = (left + right + 1)/2; // try find mid close to right
            if (nums[mid] <= target) {
                left = mid; // find the rightmost
            } else {
                right = mid - 1;
            }
        }
        // now left == right
        result[1] = left;
        return result;
    }
}