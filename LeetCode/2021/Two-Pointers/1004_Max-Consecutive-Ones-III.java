// https://leetcode.com/problems/max-consecutive-ones-iii/
class Solution {
    public int longestOnes(int[] A, int K) {
        int left = 0;
        int right = 0;
        int currentZero = 0;
        int max = Integer.MIN_VALUE;
        while (right < A.length) {
            if (A[right] == 0) {
                currentZero++;
            }
            if (currentZero <= K) { // expand
                max = Math.max(max, right - left + 1);
                right++;
            } else { // move
                if(A[left] == 0) {
                    currentZero--;
                }
                left++;
                right++;
            }
        }
        return Math.max(max, 0);
    }
}