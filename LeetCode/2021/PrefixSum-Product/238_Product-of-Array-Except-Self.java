// https://leetcode.com/problems/product-of-array-except-self/
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        
        //    0   1   2   3
        //    1   2   3   4
        // 0  1   2   3   4
        // 1  1   2   6   24
        //    0   1   2   3   4
        //    24  24  12  4   1
        
        // product from left to right
        int[] leftToRight = new int[nums.length + 1];
        leftToRight[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            leftToRight[i + 1] = leftToRight[i] * nums[i];
        }
        // product from right to left
        int[] rightToLeft = new int[nums.length + 1];
        rightToLeft[nums.length] = 1;
        for (int j = nums.length - 1; j >=0; j--) {
            rightToLeft[j] = rightToLeft[j + 1] * nums[j];
        }
        // now set result
        for (int i = 0; i < nums.length; i++) {
            result[i] = leftToRight[i] * rightToLeft[i + 1];
        }
        return result;
    }
}