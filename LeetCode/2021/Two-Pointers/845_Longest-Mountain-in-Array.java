// https://leetcode.com/problems/longest-mountain-in-array/
class Solution {
    public int longestMountain(int[] arr) {
        if (arr.length < 3) {
            return 0;
        }
        int left = 0;
        int right = 1;
        int result = 0;
        int uphillLength = 0;
        int downHillLength = 0;
        /**
        [2,1,4,7,3,2,5]
          d u u d d u
        **/
        while (right < arr.length) {
            if (arr[left] < arr[right]) { // uphill
                if (downHillLength > 0) { // finish a mountain
                    result = Math.max(result, uphillLength + downHillLength + 1);
                    downHillLength = 0;
                    uphillLength = 0;
                }
                uphillLength++;
            } else if (arr[left] > arr[right]) { // downhill
                if (uphillLength > 0) { // found top
                    downHillLength++;
                }
            } else if (arr[left] == arr[right]) {
                if (downHillLength > 0) { // finish a mountain
                    result = Math.max(result, uphillLength + downHillLength + 1);
                }
                downHillLength = 0;
                uphillLength = 0;
            }
            
            left++;
            right++;
        }
        if (downHillLength > 0) {
            result = Math.max(result, uphillLength + downHillLength + 1);
        }
        return result;
    }
}