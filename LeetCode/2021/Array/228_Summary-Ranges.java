// https://leetcode.com/problems/summary-ranges/
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new LinkedList<>();
        if (nums.length == 0) {
            return result;
        }
        
        int prev = nums[0];
        int anchor = prev;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == prev+1) {
                prev = nums[i];
            } else {
                if (anchor == prev) {
                    // add single to result list
                    result.add(String.valueOf(anchor));
                } else {
                    // add range to result list
                    result.add(String.format("%s->%s", anchor, prev));
                }
                prev = nums[i];
                anchor = prev;
            }
        }
        if (anchor == prev) {
            // add single to result list
            result.add(String.valueOf(anchor));
        } else {
            // add range to result list
            result.add(String.format("%s->%s", anchor, prev));
        }
        return result;
    }
}