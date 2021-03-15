// https://leetcode.com/problems/permutations/
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        // base case
        int n = nums.length;
        List<Integer> availableIndexList = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            availableIndexList.add(i);
        }
        List<Deque<Integer>> result = permuteHelper(nums, availableIndexList);
        return result.stream().map(deque -> new LinkedList<Integer>(deque)).collect(Collectors.toList());
    }
    
    // fix 1, permute 2 3
    //        fix 2, permute 3
    //        fix 3, permute 2
    // fix 2, permute 1 3
    // fix 3, permute 1 2
    public List<Deque<Integer>> permuteHelper(int[] nums, List<Integer> availableIndexList) {
        // base logic
        List<Deque<Integer>> result = new LinkedList<>();
        if (availableIndexList.size() == 1) {
            Deque<Integer> list = new LinkedList<>();
            list.add(nums[availableIndexList.get(0)]);
            result.add(list);
            return result;
        }
        // for each available index
        // NOTE: traverse from end to the beginning
        for (int index = availableIndexList.size() - 1; index >= 0; index--) {
            // fix index
            int fixedNum = nums[availableIndexList.get(index)];
            // remove index from availableIndexList
            List<Integer> newIndexList = new LinkedList<>(availableIndexList);
            newIndexList.remove(index);
            // get permute of rest available index
            List<Deque<Integer>> restResultList = permuteHelper(nums,newIndexList);
            for (Deque<Integer> restResult : restResultList) {
                restResult.addLast(fixedNum); // make sure it's in ascending order
                result.add(restResult);
            }
        }
        return result;
    }
}