// https://leetcode.com/problems/3sum-closest/

import java.util.Arrays;
import java.lang.Math;

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // basic scenario
        
        // originally: 0, 2, 1, -3
        // sort array - O(nlgn)
        Arrays.sort(nums); // -3, 0, 1, 2
        // traverse array
        int closestThreeSum = Integer.MAX_VALUE;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // fix i == 0 // i==1
            int left = i + 1; // left==1 // left==2
            int right = nums.length - 1; // right == 3,2,1 // right==3
            // make the total 3sum closest to target
            // min(target - nums[i] - nums[left] - nums[right])
            while(left < right) { // 1<3,1<2
                int threeSum = nums[i] + nums[left] + nums[right]; // -3+0+2=-1,-3+0+1=-2
                int distance = target - threeSum; // 1-(-1)=2,1-(-2)=3
                if (Math.abs(distance) < closestDistance) {// 2 < MAX,3>2
                    closestThreeSum = threeSum; // -1
                    closestDistance = Math.abs(distance); // 2
                }
                if (distance == 0) {
                    // found the data
                    break;
                } 
                if (threeSum < target) { // NOTE: compare threeSum and target
                    left++; // increase sum, 
                } else { // > 0
                    right--; // decerase sum
                }
            }
            if (closestThreeSum == target) {
                break;
            }
        }
        // return result
        return closestThreeSum;
    }
}