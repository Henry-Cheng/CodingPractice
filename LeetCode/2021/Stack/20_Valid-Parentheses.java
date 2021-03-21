// https://leetcode.com/problems/valid-parentheses/submissions/
class Solution {
    public boolean isValid(String s) {
        if (s.length() == 1) {
            return false;
        }
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char bracket = s.charAt(i);
            if (bracket == '(' || bracket == '{' || bracket == '[') {
                stack.push(bracket);
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    char leftBracket = stack.peek();
                    if (bracket == '}' && leftBracket == '{') {
                        stack.pop();
                    } else if (bracket == ']' && leftBracket == '[') {
                        stack.pop();
                    } else if (bracket == ')' && leftBracket == '(') {
                        stack.pop();
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }
}