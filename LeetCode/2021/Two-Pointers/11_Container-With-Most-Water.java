//https://leetcode.com/problems/container-with-most-water/

// look for longest distance * height, it is front/end pointers
class Solution {
    public int maxArea(int[] height) {
        // have two pointers front and end
        int left = 0;
        int right = height.length - 1;
        // NOTE: height.length >= 2
        // NOTE: height.length * n <= 10^9 <= Integer.MAX_VALUE = 2.149*10^9
        // e.g. 1, 4, 2
        // e.g. 1, 2, 1
        // e.g. 2, 2, 6, 7, 3, 2
        int maxArea = 0; 
        while(left < right) {
            int distance = right - left;
            int currentArea = 0;
            if (height[left] < height[right]) { // move the smaller pointer
                currentArea = height[left] * distance;
                left++;
            } else if (height[left] > height[right]) {// move the smaller pointer
                currentArea = height[right] * distance;
                right--;
            } else { // equals -- move both pointers, since no better chance by using current height
                currentArea = height[right] * distance;
                left++;
                right--;
            }
            maxArea = maxArea >= currentArea ? maxArea : currentArea;
        }
        return maxArea;
        
    }
}