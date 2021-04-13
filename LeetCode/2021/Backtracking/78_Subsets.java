// https://leetcode.com/problems/subsets/
class Solution {	

    // option2: backtracking idea
    public List<List<Integer>> subsets(int[] nums) {
        // thinking we have n spots/scenarios, each spot is not a slot in nums, but means "the scenario that we will evaluate nums[i]", therefore in backtrack function, we need to use a for loop to traverse each num starting at spot i
        // each spot would have 2 options, which is to "include this num" or "exclude this num"
        List<List<Integer>> result = new LinkedList<>();
        backtrack(nums, 0, new LinkedList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int spot, List<Integer> path, List<List<Integer>> result) {
        // path will always meet the termination requirement, so we simply add it to result
        result.add(new LinkedList<>(path));
        // for each spot, try all options
        for (int i = spot; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(nums, i + 1, path, result);
            // reset the path before moving to other nums
            path.remove(path.size() - 1);
        }
    }
    // option1: DP-like idea, dp[i] = dp[i-1] + dp[i-1] * nums[i]
    // it's cumulative increasing the size, finally we will have a largest set with 2^n, and at level i we have largest set size 2^i,  so the total time complexity is O(n*2^n)
    public List<List<Integer>> subsets_dp_idea(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        result.add(new LinkedList<>()); // initialliy adding empty result
        for (int num : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> newSet = new LinkedList<>(result.get(i));
                newSet.add(num);
                result.add(newSet);
            }
        }
        return result;
    }
}