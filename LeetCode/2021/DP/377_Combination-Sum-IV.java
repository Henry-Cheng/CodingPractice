// https://leetcode.com/problems/combination-sum-iv/
class Solution {
    /**
     1 2 3 --> 4
     count += [1 2 3] 3
        --> [1 2 3] 2
            --> [1 2 3] 1
                --> [1 2 3] 0
            --> [1 2 3] 0
        --> [1 2 3] 1
            --> [1 2 3] 0
        --> [1 2 3] 0
     count += [1 2 3] 2
        --> [1 2 3] 1
                --> [1 2 3] 0 
        --> [1 2 3] 0
     count += [1 2 3] 1
        --> [1 2 3] 0
    **/
    HashMap<Integer, Integer> memory = new HashMap<>();
    public int combinationSum4(int[] nums, int target) {
        if (memory.containsKey(target)) {
            return memory.get(target);
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i]) {
                count++;
            } else if (target < nums[i]){
                continue;
            } else { // target > nums[i]
                count += combinationSum4(nums, target - nums[i]);
            }
        }
        memory.put(target, count);
        return count;
    }
}