// https://leetcode.com/problems/continuous-subarray-sum/
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> remainderMap = new HashMap<>();
        int prefixSum = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            int reminder = prefixSum % k;
            if (reminder == 0 && i > 0) {
                return true;
            } else if (remainderMap.containsKey(reminder)) {
                if (i - remainderMap.get(reminder)> 1) {
                    return true;
                }
            } else {
                //System.out.println("put reminder to map " + reminder);
                remainderMap.put(reminder, i);
            }
        }
        return false;
    }
}