// https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
class Solution {
    public int maxDistance(int[] nums1, int[] nums2) {
        /**
        [55,30,5,4,2]
            i
        [100,20,10,10,5]
                    j
        **/
        int max = 0;
        for(int i=0; i<nums1.length; i++){
            if (i < nums2.length && nums1[i] <= nums2[i]) {
                int pos = binarySearch(nums2, nums1[i], i, nums2.length-1);
                //System.out.println("check " + nums1[i] + " from " + i + " to " + (nums2.length-1) + ", found " + pos + " which is " + nums2[pos]);
                max = Math.max(max, pos-i);
            }
        }
        return max;
    }
    
    private int binarySearch(int[] nums, int target, int leftBound, int rightBound) {
        int l = leftBound;
        int r = rightBound;
        while(l <= r) {
            int mid = l + (r - l)/2;
            //System.out.println("l is " + l + " r is " + r + " and mis is" + nums[mid]);
            if (nums[mid] == target) {
                l = mid + 1;
            } else if (nums[mid] < target) {
                r = mid - 1;
            } else if (nums[mid] > target) {
                l = mid + 1;
            }
        }
        /**
            5 3 3 1, target=3
                l
              r
                     target=5
          r l
                     target=1
                r l
                     target=0
            5 4 2 1
            l     r
                l r
                  l
                  r
                    l
                  r
                     
        **/
        // 5 4 2 1, target=3
        //     l
        //   r
        return r < leftBound ? l : r;
    }
}