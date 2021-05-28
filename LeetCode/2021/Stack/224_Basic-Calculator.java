// https://leetcode.com/problems/basic-calculator/
class Solution {
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int sign = 1;
        int currentNum = 0;
        int result = 0;
        s = s + " "; // make sure we can collect the last num
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
            } else {
                result += currentNum * sign;
                currentNum = 0; // reset currentNum
                if (c == '+') {
                    sign = 1;
                } else if (c == '-') {
                    sign = -1;
                } else if (c == '(') { // only push when entering parentheses
                    stack.push(result);
                    stack.push(sign);
                    sign = 1; // reset when entering new parentheses
                    result = 0; // reset when entering new parentheses
                } else if (c == ')') { // only pop when ext parenthese
                    int prevSign = stack.pop(); // first must be sign
                    int prevResult = stack.pop();
                    result = prevResult + result * prevSign; // multiple the sign before bracket to result
                }
            }
        }
        return result;
    }
}