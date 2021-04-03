// https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
class Solution {
    public String minRemoveToMakeValid(String s) {
        Deque<Pos> stack = new LinkedList<>();
        HashSet<Integer> keptBracketPos = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')') {
                // try to find '(' in stack
                while (!stack.isEmpty()) { // e.g. (ab(cde
                    Pos top = stack.pop();
                    if (top.c == '(') {
                        keptBracketPos.add(top.index); // add index of left bracket
                        keptBracketPos.add(i);// add right bracket
                        break; 
                    }
                } 
            } else { // if it is letter or '('
                stack.push(new Pos(i, c));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')' || c == '(') {
                if (keptBracketPos.contains(i)) {
                    sb.append(c);
                } else {
                    continue;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    private static class Pos {
        public int index;
        public char c;
        public Pos(int index, char c) {
            this.index = index;
            this.c = c;
        }
    }
}