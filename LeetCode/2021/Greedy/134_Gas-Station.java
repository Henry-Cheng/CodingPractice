// https://leetcode.com/problems/gas-station/
class Solution {
    /**
    If the starting point exists, it must start from the position where we lose the most of the gas, so that it can start to gain gas first to gather all the gas we need before we start losing.

Take an example like (here the number refers to the value gas[i] - cost[i])

[3,4,-12,4,-5,6]

The minimum tank value will happen at index 4, where the value is -5, because at there our tank is at the minimum value -6, which means if the result exists, it must start to gather gas at index 5 so that we can cover all the gas loss before we reach index 4.

    **/
    
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int prefixSum = 0;
        int minPrefixSum = prefixSum;
        int minGasPos = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            prefixSum += diff;
            if (prefixSum < minPrefixSum) { // find newer min gas station
                minPrefixSum = prefixSum;
                minGasPos = i + 1;
            }
        }
        if (prefixSum < 0) {
            return -1;
        } else {
            return minGasPos;
        }
    }
    
    public int canCompleteCircuit_LC_2(int[] gas, int[] cost) {
        int totalDiff = 0;
        int prefixSum = 0;
        int startingPos = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            totalDiff += diff;
            prefixSum += diff;
            if (prefixSum < 0) {
                startingPos = i + 1;// since adding i makes it negative
                prefixSum = 0;
            }
        }
        if (totalDiff < 0) {
            return -1;
        } else {
            return startingPos;
        }
    }
    
    public int canCompleteCircuit_N_square_pass(int[] gas, int[] cost) {
        /**        
         station:  0   1   2   3    4      0
             gas:  1   2   3   4    5      1
            cost:  3   4   5   1    2         
            diff: -2  -2  -2   3    3

                2  3  4
                3  4  3
                -1 -1 1
         
         [5, 1, 2, 3, 4]
         [4, 4, 1, 5, 1]
          1 -3  1 -2  3
        **/
        int[] diff = new int[gas.length];
        int sum = 0;
        for (int i = 0; i < gas.length; i++) {
            diff[i] = gas[i] - cost[i];
            sum += diff[i];
        }
        if (sum < 0) {
            return -1; // no available path
        }
        for (int j = 0; j < diff.length; j++) {
            if (diff[j] >= 0) { // possible entry point
                if (validTravel(diff, j)) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    /**
     5  1  2  3  4
     4  4  1  5  1
     1 -3  1 -2  3
    **/
    private boolean validTravel(int[] diff, int j) {
        int prefixSum = 0;
        for (int k = 0; k < diff.length; k++) {
            int pos = k + j;
            if (pos >= diff.length) {
                pos = pos - diff.length;
            }
            prefixSum += diff[pos];
            if (prefixSum < 0) {
                return false;
            }
        }
        return true;
    }
}

