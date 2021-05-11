// https://leetcode.com/problems/missing-ranges/
class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new LinkedList<>();
        for(int num : nums) {
            if (lower < num) {
                if (lower == num-1) {
                    result.add(String.valueOf(lower));
                } else {
                    result.add(String.format("%s->%s", lower, num-1));
                }
                
            } 
            lower = num+1;
        }
        // 1 ~ 5 , lower 0, upper is 5
        if (lower > upper) {
            // ignore
        } else if (lower == upper) {
            result.add(String.valueOf(upper));
        } else if (lower < upper) {
            if (lower == upper) {
                result.add(String.valueOf(upper));
            } else {
                result.add(String.format("%s->%s", lower, upper));
            }
        }
        return result;
    }
}