// https://leetcode.com/problems/backspace-string-compare/
class Solution {
    public boolean backspaceCompare(String s, String t) {
        Deque<Character> stackS = new LinkedList<>();
        for(char c : s.toCharArray()) {
            if (c != '#') {
                stackS.push(c);
            } else {
                if (!stackS.isEmpty()) {
                    stackS.pop();
                }
            }
        }
        Deque<Character> stackT = new LinkedList<>();
        for(char c : t.toCharArray()) {
            if (c != '#') {
                stackT.push(c);
            } else {
                if (!stackT.isEmpty()) {
                    stackT.pop();
                }
            }
        }
        if (stackS.size() != stackT.size()) {
            return false;
        } else {
            while(!stackS.isEmpty()) {
                if (!stackS.pop().equals(stackT.pop())) {
                    return false;
                }
            }
        }
        return true;
    }
}