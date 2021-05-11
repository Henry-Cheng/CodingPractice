// https://leetcode.com/problems/sliding-window-maximum/
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        TreeMap<Integer, HashSet<Integer>> numToCount = new TreeMap<>();
        // 1 2 3 4
        //   i
        int left = 0;
        int right = k - 1;
        for (int i = left; i <= right; i++) {
            HashSet<Integer> set = numToCount.getOrDefault(nums[i], new HashSet<>());
            set.add(i);
            numToCount.put(nums[i], set);
        }
        int[] result = new int[nums.length - k + 1];
        result[0] = numToCount.lastKey();
        left++;
        right++;
        int count = 1;
        while(right < nums.length) {
            // remove left -1
            HashSet<Integer> setToRemove = numToCount.get(nums[left-1]);
            setToRemove.remove(left - 1);
            if (setToRemove.isEmpty()) {
                numToCount.remove(nums[left-1]);
            }
            // add right
            HashSet<Integer> setToAdd = numToCount.getOrDefault(nums[right], new HashSet<>());
            setToAdd.add(right);
            numToCount.put(nums[right], setToAdd);
            // store max to result
            result[count] = numToCount.lastKey();
            // increment pointers
            count++;
            left++;
            right++;
        }
        return result;
    }
}