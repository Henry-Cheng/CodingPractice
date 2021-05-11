// https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            // dedupe nums
            while(left + 1 < nums.length && nums[left+1] == nums[left]) {
                left++;
            }
            if (left == right) {
                return nums[left];
            }
            while(right - 1 >= 0 && nums[right -1] == nums[right]) {
                right--;
            }
            if (left == right) {
                return nums[left];
            }
            // now it's the standard binary search
            int mid = left + (right - left)/2;
            if (nums[mid] < nums[right]) { // right side is in order
                right = mid;
            } else if (nums[mid] > nums[right]) {// left side is in order
                left = mid + 1;
            }
        }
        // finally, it would be
        /**
         [4,5,6,7,0,1,4]
          l     m     r
                  l
                    m
                  l r
                  r
         [0 1 2 3 ]
          l     r
            m
            r
          m
          lr
          [1 2 3 0]
           l     r
             m
               l
               m
                 l
        **/
        return nums[left];
    }
}