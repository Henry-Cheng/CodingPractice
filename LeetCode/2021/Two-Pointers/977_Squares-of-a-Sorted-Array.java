// https://leetcode.com/problems/squares-of-a-sorted-array/
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] newArr = new int[nums.length];
        int left = 0;
        int right = nums.length-1;
        int pos = nums.length-1;
        while(pos >= 0) {
            int newLeft = nums[left] * nums[left];
            int newRight = nums[right] * nums[right];
            if (newLeft >= newRight) {
                newArr[pos] = newLeft;
                left++;
            } else {
                newArr[pos] = newRight;
                right--;
            }
            pos--;
        }
        return newArr;
    }
}