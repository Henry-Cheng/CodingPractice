## MiniMax
### Default

So far 5 questions on LC:

https://blog.csdn.net/weixin_30755709/article/details/98892479

https://www.liuin.cn/2018/06/30/LeetCode%E6%80%BB%E7%BB%93%E2%80%94%E2%80%94Minimax%E7%AE%97%E6%B3%95/

#### [LC] 486 Predict the Winner  
https://leetcode.com/problems/predict-the-winner/

If the current turn belongs to, say Player 1, we pick up an element, say xx, from either end, and give the turn to Player 2. Thus, if we obtain the score for the remaining elements(leaving xx), this score, now belongs to Player 2. Thus, since Player 2 is competing against Player 1, this score should be subtracted from Player 1's current(local) score(xx) to obtain the effective score of Player 1 at the current instant.

Similar argument holds true for Player 2's turn as well i.e. we can subtract Player 1's score for the remaining subarray from Player 2's current score to obtain its effective score. By making use of this observation, we can omit the use of turnturn from winner to find the required result by making the slight change discussed above in the winner's implementation.

While returning the result from winner for the current function call, we return the larger of the effective scores possible by choosing either the first or the last element from the currently available subarray. Rest of the process remains the same as the last approach.

Now, in order to remove the duplicate function calls, we can make use of a 2-D memoization array, memomemo, such that we can store the result obtained for the function call winner for a subarray with starting and ending indices being ss and ee ] at memo[s][e]memo[s][e]. This helps to prune the search space to a great extent.


```
    public boolean PredictTheWinner(int[] nums) {
        Integer[][] memory = new Integer[nums.length][nums.length];
        return getMaxScoreDiff(nums, 0, nums.length - 1, memory) >= 0;
    }
    
    // find max diff for (player1_score - player2_score)
    public int getMaxScoreDiff(int[] nums, int i, int j, Integer[][] memory) {
        if (memory[i][j] != null) {
            return memory[i][j];
        }
        if (i == j) {
            memory[i][j] = nums[i];
            return nums[i];
        }

        int maxScoreDiff = Math.max(
            nums[i] - getMaxScoreDiff(nums, i + 1, j, memory), 
            nums[j] - getMaxScoreDiff(nums, i, j - 1, memory)
        );
        memory[i][j] = maxScoreDiff;
        return maxScoreDiff;
    }
```
