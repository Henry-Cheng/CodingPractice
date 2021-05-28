// https://leetcode.com/problems/find-all-duplicates-in-an-array/
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            int pos = Math.abs(nums[i]);
            if (nums[pos-1] < 0) {
                result.add(pos);
            }
            nums[pos-1] = 0 - Math.abs(nums[pos-1]); // set negative
        }
        return result;
    }
}