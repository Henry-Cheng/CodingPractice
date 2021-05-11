// https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
class Solution {
    public int minAddToMakeValid(String S) {
        int leftCount = 0;
        int rightCount = 0;
        int result = 0;
        for(char c : S.toCharArray()) {
            if (c == '(') {
                leftCount++;
            } else if (c ==')') {
                if (leftCount == 0) {
                    result++;
                } else {
                    leftCount--;
                }
            }
        }
        return result + leftCount;
    }
}