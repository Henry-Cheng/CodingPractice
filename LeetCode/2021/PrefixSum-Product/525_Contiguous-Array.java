// https://leetcode.com/problems/contiguous-array/
class Solution {
    public int findMaxLength(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        // do prefix sum
        int[] prefixSum = new int[nums.length + 1];
        prefixSum[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i+1] = (nums[i] == 0 ? -1 : 1)  + prefixSum[i];
        }
        // now find the longest dist of same prefixSum
        // nums:        -1 1 -1 -1 -1   1 1
        // prefixSum: 0 -1 0 -1 -2 -3 -2 -1 
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < prefixSum.length; i++) {
            int sum = prefixSum[i];
            if (map.containsKey(sum)) {
                max = Math.max(max, i - map.get(sum) );
            } else {
                map.put(sum, i);
            }
        }
        return max == Integer.MIN_VALUE ? 0 : max;
    }
}