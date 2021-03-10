// https://leetcode.com/problems/trapping-rain-water/

import java.lang.Math;

class Solution {
    public int trap(int[] height) {
        // 0     1 0 2 1 0 1 3 2 1 2 1
        //  null   
        // find wall that is higher than 0, and it's left and right are lower than it
        // detect lower number between 2 walls
        // 1 0 2, 2 1 0 1 3, 3 2 1 2, 1
        // for 1 0 2, find the lower wall 1, size is 1-0=1
        // for 2 1 0 1 3, find the lower wall 2, size is (2-1)+(2-0)+(2-1)=1+2+1=4
        // for 3 2 1 2, find the lower wall 2, size is (2-3) ignore and (2-2)+(2-1)=1
        // finally it is 1+4+1=6;
        
        //basic scenrio
        int n = height.length;
        if (n < 3) { // at least 3 numbers to represent 
            return 0;
        }

        int left = 0;
        int right = n -1;
        int size = 0;
        while(left < right) {
            int lowerWall = Math.min(height[left], height[right]);
            if (height[left] == lowerWall) {
                left++;
                while(left < right && height[left] <= lowerWall) {
                    size += lowerWall - height[left];
                    left++;
                }
            } else {
                right--;
                while(left < right && height[right] <= lowerWall) {
                    size += lowerWall - height[right];
                    right--;
                }
            }
        }
        return size;
    }
}