
// https://leetcode.com/problems/find-pivot-index/
class Solution {
    public int pivotIndex(int[] nums) {
        int[] prefixSumLeft = new int[nums.length]; 
        prefixSumLeft[0] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            prefixSumLeft[i] = prefixSumLeft[i-1] + nums[i];
        }
        int[] prefixSumRight = new int[nums.length];
        prefixSumRight[nums.length-1] = nums[nums.length-1];
        for(int j = nums.length -2; j>= 0; j--) {
            prefixSumRight[j] = prefixSumRight[j+1] + nums[j];
        }
        for (int k = 0; k < prefixSumLeft.length; k++) {
            if (prefixSumLeft[k] == prefixSumRight[k]) {
                return k;
            }
        }
        return -1;
    }
}