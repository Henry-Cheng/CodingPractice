// https://leetcode.com/problems/two-sum/

import java.util.*;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = null;
        Map<Integer, Integer> pos = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            pos.put(nums[i], i);
        }
        for(int i = 0; i < nums.length; i++) {
            int expected = target - nums[i];
            Integer anotherPos = pos.get(expected);
            // NOTE: be careful the two positions cannot be the same
            if (anotherPos != null && anotherPos != i) {
                result = new int[2];
                result[0] = i;
                result[1] = anotherPos;
                break;
            }
        }
        return result;
    }
}