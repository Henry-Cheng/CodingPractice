// https://leetcode.com/problems/strobogrammatic-number/
class Solution {
    public boolean isStrobogrammatic(String num) {
        int l = 0;
        int r = num.length()-1;
        while(l <= r) {
            char lChar = num.charAt(l);
            char rChar = num.charAt(r);
            if (l == r) {
                if (lChar == '0' || lChar == '1' || lChar == '8') {
                    // do nothing
                } else {
                    return false;
                }
            } else {
                if ((lChar == '9' && rChar == '6') || (lChar == '6' && rChar == '9')) {
                    // do nothing
                } else if ((lChar == '1' && rChar == '1') || (lChar == '0' && rChar == '0') || (lChar == '8' && rChar == '8')) {
                    // do nothing
                } else {
                    return false;
                }
            }
            l++;
            r--;
        }
        return true;
    }
    
}