// https://leetcode.com/problems/valid-palindrome-ii/
class Solution {
    public boolean validPalindrome(String s) {
        // base case
        if (s.length() == 1 || s.length() == 2) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        return validPalindrome(s, left, right);
    }
    
    boolean chanceUsed = false;// have we removed one char?
    protected boolean validPalindrome(String s, int left, int right) {
        while(left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
                continue;
            } else {
                if (chanceUsed) {
                    return false;
                } else {
                    chanceUsed = true;
                    return validPalindrome(s, left + 1, right) || validPalindrome(s, left, right-1);  
                }
            }
        }
        return true;
    }
}