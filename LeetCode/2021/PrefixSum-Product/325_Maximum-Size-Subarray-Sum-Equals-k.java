// https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/submissions/
class Solution {
    public int maxSubArrayLen(int[] nums, int k) {

        //    0   1 -1     5      -2         3
        //   []   a  b     c       d         e
        //   []   a a+b  a+b+c  a+b+c+d  a+b+c+d+e
        //    0   1  0     5       3         6
        //    0   1  2     3       4         5
        
        // k = -1
        // nums         1 0 -1
        // prefixSum  0 1 1  0
        //   index    0 1 2  3

        int[] prefixSum = new int[nums.length + 1];
        prefixSum[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        HashMap<Integer,Integer> remainderMap = new HashMap<>();
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < prefixSum.length; i++) {
            if (prefixSum[i] == k) {
                max = Math.max(max, i);
            }
            
            if (remainderMap.containsKey(prefixSum[i] - k)) {
                int previousPos = remainderMap.get(prefixSum[i] - k);
                max = Math.max(max, i - previousPos);
            }
            if (!remainderMap.containsKey(prefixSum[i])) { // if there are duplicate prefixSum, we only record the previous index
                remainderMap.put(prefixSum[i], i);
            }
        }
        return Math.max(max, 0);
    }
}