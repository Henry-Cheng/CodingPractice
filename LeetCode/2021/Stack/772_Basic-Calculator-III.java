// https://leetcode.com/problems/basic-calculator-iii/
class Solution {
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int currentNum = 0;
        int sign = 1;
        char ops = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //System.out.println("c is " + c + ", stack is " + stack);
            if (c == '(') {
                int j = i + 1;
                int leftCount = 1;
                int rightCount = 0;
                while(rightCount != leftCount) {
                    if (s.charAt(j) == '(') {
                        leftCount++;
                    } else if (s.charAt(j) == ')') {
                        rightCount++;
                    }
                    j++;
                }
                // j-1 is the ")"
                currentNum = calculate(s.substring(i+1,j-1));
                i = j-1; // move i
            }
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + c - '0';
            }
            if (i == s.length() - 1 || c == '+' || c == '-' || c == '*' || c == '/') {
                // check ops
                if (ops == '+') {
                    stack.push(currentNum * sign);
                } else if (ops == '*') {
                    stack.push(stack.pop() * currentNum);
                } else if (ops == '/') {
                    stack.push((int)(stack.pop() / currentNum));
                }
                // reset
                currentNum = 0;
                sign = 1;
                ops = '+';
                // check sign and ops
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
        while(!stack.isEmpty()) {
            result += stack.pop();
        }
        //System.out.println("when s is " + s + ", result is " + result);
        return result;
    }
}