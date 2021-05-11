// https://leetcode.com/problems/maximum-swap/
class Solution {
    public int maximumSwap(int num) {
        if (num <= 11) {
            return num;
        }
        char[] arr = String.valueOf(num).toCharArray();
        int[] lastAppearIndexArr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            lastAppearIndexArr[arr[i] - '0'] = i;
        }
        for(int i = 0; i < arr.length; i++) {
            for (int j = 9; j > arr[i] - '0'; j--) {
                if (lastAppearIndexArr[j] > i) { // if find a larger num appears after i
                    char tmp = arr[i];
                    arr[i] = arr[lastAppearIndexArr[j]];
                    arr[lastAppearIndexArr[j]] = tmp;
                    return Integer.valueOf(new String(arr));
                }
            }
        }
        
        return num;
    }
}