// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
class Solution {
    public String removeDuplicates(String S) {
        Deque<Character> stack = new LinkedList<>();
        for (char c : S.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(c);
            } else {
                if (stack.peek().equals(c)) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}