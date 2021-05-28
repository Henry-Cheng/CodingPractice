// https://leetcode.com/problems/valid-parenthesis-string/
class Solution {
    public boolean checkValidString(String s) {
        Deque<Integer> leftStack = new LinkedList<>();
        Deque<Integer> starStack = new LinkedList<>();
        // match right bracket by left bracket or *
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftStack.push(i);
            } else if (c == '*') {
                starStack.push(i);
            } else if (c == ')') {
                if (!leftStack.isEmpty()) {
                    leftStack.pop();
                } else if (!starStack.isEmpty()) {
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }
        // check the rest left bracket, whether we can use * to match it
        while(!leftStack.isEmpty()) {
            if (starStack.isEmpty()) {
                return false;
            } else {
                if (leftStack.peek() < starStack.peek()) {
                    leftStack.pop();
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}