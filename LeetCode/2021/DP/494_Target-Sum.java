// https://leetcode.com/problems/target-sum/
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        return recursive(nums, target, 0, new HashMap<String,Integer>());
    }
    
    public int recursive(int[] nums, int target, int pos, HashMap<String,Integer> memo) {
        if (pos >= nums.length) {
            return 0;
        } else if (pos == nums.length - 1) {
            if (Math.abs(nums[pos]) == Math.abs(target)) {
                if (target == 0) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        }
        String key = String.format("%s-%s", pos, target);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int count = 0;
        // try +
        count += recursive(nums, target - nums[pos], pos+1, memo);
        // try -
        count += recursive(nums, target + nums[pos], pos+1, memo);
        memo.put(key, count);
        return count;
    }
}