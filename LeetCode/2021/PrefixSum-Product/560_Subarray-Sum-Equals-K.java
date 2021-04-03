// https://leetcode.com/problems/subarray-sum-equals-k/submissions/
class Solution {
    public int subarraySum(int[] nums, int k) {
        // prepare prefix sum arary
        // since nums.length * nums[i] < 2 * 10 ^ 7 is less than Integer.MAX_VALUE, we can use int instead of long
        int[] preSum = new int[nums.length + 1];
        // map to maintain mapping from preSum[i] + k to count
        Map<Integer, Integer> map = new HashMap<>(); 
        preSum[0] = 0; // preSum before nums index 0 is 0
        map.put(k, 1); // important!!!!
        // k == 1
        // nums:        1 -2  3 -1 1
        // preSum:   0  1 -1  2  1 2
        // delete k: -1 0 -2  1  0 1
        // find i and j where preSum[j] - preSum[i] == k
        int totalCount = 0;
        for (int i = 0; i < nums.length; i++) {
            // prefix sum
            preSum[i + 1] = nums[i] + preSum[i];
            // System.out.println(preSum[i + 1]);

            // check if preSum exists in map before adding to map!!
            Integer checkI = map.get(preSum[i + 1]);
            if (checkI != null) {
                totalCount += checkI; // if multiple occurence of num + k, count all of them
            }
            
            // add to map
            Integer count = map.get(preSum[i + 1] + k);
            if (count == null) {
                map.put(preSum[i + 1] + k, 1);
            } else {
                map.put(preSum[i + 1] + k, count + 1);
            }
        }
        return totalCount;
    }
}

// LC solution with hashmap
// public class Solution {
//     public int subarraySum(int[] nums, int k) {
//         int count = 0, sum = 0;
//         HashMap < Integer, Integer > map = new HashMap < > ();
//         map.put(0, 1);
//         for (int i = 0; i < nums.length; i++) {
//             sum += nums[i];
//             if (map.containsKey(sum - k))
//                 count += map.get(sum - k);
//             map.put(sum, map.getOrDefault(sum, 0) + 1);
//         }
//         return count;
//     }
// }