// https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
class Solution {
    public int findMin(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        /**
        3 4 5 1 2
        l       r
            m
              l r
              m
            r

        
        3 1 2
        l   r
          m
        l r
        m
          lr
          
        **/
        /**
        4 5 6 7 0 1 2
        l           r
              m
                l
                  m
                l r
                m
                lr
              
        2 3 4 1
        l     r
          m
            l r
            m
              lr
              
            
        2 3 4 5 1
        l       r
            m
              l r
              m
                lr
              r l 
              
        1 2 3 4
        l     r
          m
        l r
        lr
      
        4 5 1 2 3
        l       r
            m
        l   r
          m
            lr
        **/ 
        int l = 0;
        int r = nums.length - 1;
        while(l <= r) {
            int mid = l + (r - l)/2;
            if (nums[mid] < nums[r]) { // min is at left side or mid itself
                r = mid;
            } else if (nums[mid] > nums[r]) { // min is at right side
                l = mid + 1;
            } else if (nums[mid] == nums[r]) { // find min
                break;
            }
        }
        return nums[l];
    }
}