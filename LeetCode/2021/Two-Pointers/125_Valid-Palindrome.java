// https://leetcode.com/problems/valid-palindrome/
class Solution {
    public boolean isPalindrome(String s) {
        // two pointers
        if (s.length() == 1) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        while(left < right) {
            while(left < right && !isAlphaNumeric(s.charAt(left))) { // be careful the boundary!!!
                left++;
            }
            while(left< right && !isAlphaNumeric(s.charAt(right))) { // be careful the boundary!!!
                right--;
            }
            if (left < right) {
                //System.out.println("left: " + s.charAt(left) + ", right: " + s.charAt(right));
                if (isSameChar(s.charAt(left), s.charAt(right))) {
                    left++;
                    right--;
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    protected boolean isAlphaNumeric(char c) {
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        }
        return false;
    }
    
    protected boolean isSameChar(char a, char b) {
        if (Character.toLowerCase(a) == Character.toLowerCase(b)) {
            return true;
        } else {
            return false;
        }
    }
}