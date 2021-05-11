// https://leetcode.com/problems/confusing-number/
class Solution {
    public boolean confusingNumber(int N) {
        char[] arr = String.valueOf(N).toCharArray();
        int l = 0;
        int r = arr.length - 1;
        boolean isSameNum = true;
        while(l <= r) {
            if (!isValid(arr[l]) || !isValid(arr[r])) {
                return false;
            }
            if (l == r) {
                if (!isSame(arr[l])) {
                    isSameNum = false;
                }
            } else {
                if (!isSameAfterRotation(arr[l], arr[r])) {
                    isSameNum = false;
                }
            }
            l++;
            r--;
        }
        return !isSameNum;
    }
    
    private boolean isValid(char c) {
        return isSame(c) || c == '6' || c == '9';
    }
    private boolean isSameAfterRotation(char c1, char c2) {
        if ((c1 == '6' && c2 == '9') || (c1 == '9' && c2 == '6') || (c1 == '1' && c2 == '1') || (c1 == '0' && c2 == '0') || (c1 == '8' && c2 == '8')) {
            return true;
        }
        return false;
    }
    private boolean isSame(char c) {
        return c == '0' || c == '1' || c == '8';
    }
}