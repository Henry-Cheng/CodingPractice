// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
import java.lang.Math;

class Solution {
    public int maxProfit(int[] prices) {
        // corner case
        int n = prices.length;
        if (n == 1) {
            return 0;
        }
        // origin: 3 2 6 5 0 3
        // left:   3 2 2 2 0 0
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