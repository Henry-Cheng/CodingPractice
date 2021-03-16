// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
import java.lang.Math;

class Solution {
    // Kadane's algorithm
    public int maxProfit(int[] prices) {
        // convert to Kadane's algorithm by calculating the gaps
        // prices:  7   1   5   3   6   4
        //   gaps:  -6  4   -2  3   -2
        // 
        // meaning: -6: buy at day 0 and sell at day 1
        //           4: buy at day 1 and sell at day 2
        //      -6 + 4: buy at day 0 and sell at day 2 (buy and sell same day would off-set)   
        // convert it to find max sum of subarray
        if (prices.length == 1) {
            return 0;
        }
        int maxProfitIfSellNextDay = prices[1] - prices[0];
        int maxProfileInTotal = maxProfitIfSellNextDay;
        for (int i = 1; i < prices.length - 1; i++) {
            maxProfitIfSellNextDay = Math.max(prices[i + 1] - prices[i], prices[i + 1] - prices[i] + maxProfitIfSellNextDay);
            maxProfileInTotal = Math.max(maxProfitIfSellNextDay, maxProfileInTotal);
        }
        return maxProfileInTotal < 0? 0 : maxProfileInTotal;
    }
    // 
    public int maxProfitByTwoRound(int[] prices) {
        // corner case
        int n = prices.length;
        if (n == 1) {
            return 0;
        }
       
        // origin: 3 2 6 5 0 3
        // if we sell at this point, what would be the lowest buy price
        // left:   3 2 2 2 0 0
        // if we buy at this point, what would be the highest sell price
        // right:  6 6 6 5 3 3
        int left = 0;
        int right = n - 1;
        // from left to right
        int minLeft = Integer.MAX_VALUE;
        int[] minLeftArray = new int[n];
        int i = 0;
        while(left < n) {
            minLeft = Math.min(prices[left], minLeft);
            left++;
            minLeftArray[i] = minLeft;
            i++;
        }
        // from right to left
        int maxRight = Integer.MIN_VALUE;
        int[] maxRightArray = new int[n];
        int j = n -1;
        while(right >= 0) {
            maxRight = Math.max(prices[right], maxRight);
            right--;
            maxRightArray[j] = maxRight;
            j--;
        }
        int earn = 0;
        for(int k = 0; k < n; k++) {
            earn = Math.max(maxRightArray[k] - minLeftArray[k], earn);
        }
        return earn;
    }
}