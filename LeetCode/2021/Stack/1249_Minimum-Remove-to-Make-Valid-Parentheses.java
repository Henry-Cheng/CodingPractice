// https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
class Solution {
    public String minRemoveToMakeValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        int leftCount = 0;
        int rightCount = 0;
        // 1st pass left to right: remove extra right brackets 
        for (char c : s.toCharArray()) {
            if (c != '(' && c != ')') {
                stack.push(c);
            } else if (c == '(') {
                leftCount++;
                stack.push(c);
            } else if (c == ')') {
                if (rightCount + 1 > leftCount) {
                    continue; // ignore this one
                } else {
                    rightCount++;
                    stack.push(c);
                }
            }
        }
        // 2nd pass right to left: remove extra left brackets
        leftCount = 0;
        rightCount = 0;
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            Character c = stack.pop();
            if (c != '(' && c != ')') {
                sb.append(c);
            } else if (c == ')') {
                rightCount++;
                sb.append(c);
            } else if (c == '(') {
                if (leftCount + 1 > rightCount) {
                    continue; // ignore left bracket
                } else {
                    leftCount++;
                    sb.append(c);
                }
            }
        }
        // 3rd pass: reverse string builder
        return sb.reverse().toString();
    }
}