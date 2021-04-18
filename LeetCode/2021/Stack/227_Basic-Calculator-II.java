// https://leetcode.com/problems/basic-calculator-ii/submissions/
class Solution {
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<>();
        char[] arr = s.toCharArray();
        int currentNum = 0;
        int sign = 1; // 1 is +, -1 is -
        char ops = '+'; // null is +
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (Character.isDigit(c)) { 
                currentNum = 10 * currentNum + c - '0';
            } 
            // using this if to totally ignore space
            if (i == (arr.length - 1) || c == '+' || c == '-' || c == '*' || c =='/') {
                
                // check which ops
                if (ops == '*') {
                    stack.push(stack.pop()*currentNum);
                } else if (ops == '/') {
                    stack.push((int)(stack.pop() / currentNum));
                } else if (ops == '+') {
                    stack.push(currentNum * sign);
                }

                // reset everything
                ops = '+'; // reset ops
                currentNum = 0; // reset currentNum
                sign = 1; // reset sign
                
                // check current symbol
                if (c == '+') {
                    sign = 1;
                } else if (c == '-') {
                    sign = -1;
                } else if (c == '*' || c == '/') {
                    ops = c;
                }
            }
        }

        
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;         
    } 
    
    public int calculate_old(String s) {
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