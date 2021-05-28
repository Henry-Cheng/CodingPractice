// https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        // 1. set the index of "num-1" to be negative
        for (int i = 0; i < nums.length; i++) {
            int pos = Math.abs(nums[i]);
            nums[pos-1] = 0 - Math.abs(nums[pos-1]);
        }
        // 2. check which num is not negative which means not visited
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i+1);
            } else {
                nums[i] = 0 - nums[i]; // recover original nums
            }
        }
        return result;
    }
    
    public List<Integer> findDisappearedNumbers_move_num(int[] nums) {
        // 1. move num to index "num-1"
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            while (nums[curr-1] != curr) {
                int reserve = nums[curr-1];
                nums[curr-1] = curr;
                curr = reserve;
            }
        }
        // 2. check which num is not at pos "num-1"
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i+1) {
                result.add(i+1);
            }
        }
        return result;
    }
}