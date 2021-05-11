// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
class Solution {
    public int numRollsToTarget(int d, int f, int target) {
        /**      
        when i-th dice get k, then previous i-1 dices need to give us j-k
        dp[i][j] = sum(dp[i-1][j-k]) (1<=k<=f)
        d = 2, f = 3, target = 4    
        target      0  1  2  3   4
          dice  0   1  0  0  0   0
          dice  1   0  1  1  1  -1
          dice  2   0 -1  1  2   2   
          
        NOTE: dp[0][0] means using 0 dice to get 0, the # of ways are 1
        **/
        int[][] dp = new int[d+1][target+1];
        dp[0][0] = 1; // initial/special state
        for (int i = 1; i < d + 1; i++) {
            for (int j = 1; j <= target; j++) {
                for (int k = 1; k <= Math.min(f,j); k++) {
                    // if i-th dice get k, then previous i-1 dice need to give us j - k
                    dp[i][j] = (dp[i][j] + dp[i-1][j-k]) % 1000000007;
                    //System.out.println("dp[" + i + "][" + j + "] is " + dp[i][j]);
                }
            }
        }
        return dp[d][target];
    }
    
    HashMap<String, Integer> memory = new HashMap<>();
    public int numRollsToTarget_topdown_memory(int d, int f, int target) {
        /**    
        3 6 4
        1 --> 2 6 3
            1 --> 1 6 2
                1 --> 0 6 1
                2 --> 0 6 0
            2 --> 1 6 1
                1 --> 0 6 0 
            3 --> 1 6 0 --> x
        2 --> 2 6 2
            1 --> 1 6 1
                1 --> 0 6 0
            2 --> 1 6 0 --> x
        3 --> 2 6 1
            1 --> 1 6 0 --> x
        4 --> 2 6 0 --> x
        
        **/
        String key = String.format("%d-%d", d, target);
        if (memory.containsKey(key)) {
            return memory.get(key);
        }
        if (d == 0 && target == 0) {
            memory.put(key, 1);
            return 1;
        } else if (d == 0 || target == 0) {
            memory.put(key, 0);
            return 0;
        } else if (d * f < target || target < d) {
            memory.put(key, 0);
            return 0;
        } else {
            int sum = 0;
            for (int i = 1; i <= f; i++) {
                sum = (sum + numRollsToTarget(d - 1, f, target - i)) % 1000000007; // remember this one!!!
            } 
            memory.put(key, sum);
            return sum;
        }
    }
}