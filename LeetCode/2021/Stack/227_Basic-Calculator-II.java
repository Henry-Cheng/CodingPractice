// https://leetcode.com/problems/basic-calculator-ii/submissions/
class Solution {
    public int calculate(String s) {
        Deque<Long> stack = new LinkedList<>();
        
        StringBuilder sb = new StringBuilder();
        int pos = collectNum(s, 0, sb);
        long currentNum = Long.valueOf(sb.toString());
        stack.push(currentNum);
        sb.setLength(0); // reset sb
        
        while (pos < s.length()) {
            if (s.charAt(pos) == ' ') {
                pos++;
            } else if (s.charAt(pos) == '+') {
                pos++;
                pos = collectNum(s, pos, sb);
                currentNum = Long.valueOf(sb.toString());
                stack.push(currentNum);
                sb.setLength(0); // reset sb
            } else if (s.charAt(pos) == '-') {
                pos++;
                pos = collectNum(s, pos, sb);
                currentNum = Long.valueOf(sb.toString());
                stack.push(currentNum * -1);
                sb.setLength(0); // reset sb
            } else if (s.charAt(pos) == '*') {
                pos++;
                pos = collectNum(s, pos, sb);
                currentNum = Long.valueOf(sb.toString());
                stack.push(currentNum * stack.pop());
                sb.setLength(0); // reset sb
            } else if (s.charAt(pos) == '/') {
                pos++;
                pos = collectNum(s, pos, sb);
                currentNum = Long.valueOf(sb.toString());
                stack.push(stack.pop() / currentNum);
                sb.setLength(0); // reset sb
            }
        }
        long sum = 0;
        while(!stack.isEmpty()) {
            sum += stack.pop();
        }
        return (int) sum;
    }
    
    private int collectNum(String s, int pos, StringBuilder sb) {
        while (pos < s.length() && s.charAt(pos) == ' ') {
            pos++;
        }
        while(pos < s.length() && Character.isDigit(s.charAt(pos))) {
            sb.append(s.charAt(pos));
            pos++;
        }
        while (pos < s.length() && s.charAt(pos) == ' ') {
            pos++;
        }
        return pos;
    }
}