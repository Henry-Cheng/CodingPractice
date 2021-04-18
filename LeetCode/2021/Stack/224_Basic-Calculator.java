// https://leetcode.com/problems/basic-calculator/
class Solution {
    /**
    (1-(4+5+2)-3)+(6+8)
    1 - 
        4+5+2
              -2
    **/
    public int calculate(String s) {
        s = s + " "; // make sure we can reach "not a digit" branch to cover the last digit in string, e.g. "1+1"
        Deque<Integer> stack = new LinkedList<>();
        int currentResult = 0; // result in current parenthesis
        int currentNum = 0; // current continuous digits 
        int currentSign = 1; // current sign
        for (int i = 0; i < s.length(); i++) {
            //System.out.println("i: " + i + ", currentNum: " + currentNum + ", currentSign: " + currentSign + ", currentResult: " + currentResult);
            char c = s.charAt(i);
            if (Character.isDigit(c)) { // always start with digits
                //System.out.println("currentNum is " + currentNum);
                currentNum = currentNum*10 + c - '0'; // cummulate currentNum
            } else { // if not digits, then we can addup currentResult
                //System.out.println("add up current result from " + currentResult);
                currentResult = currentResult + currentNum * currentSign;
                //System.out.println("to " + currentResult);
                currentNum = 0;// reset currentNum
                if (c == ' ') {
                    continue; // skip space
                } else if (c == '+' || c == '-') {
                    currentSign = (c == '+' ? 1 : -1);
                } else if (c == '(') { // push currentResult to stack
                    //System.out.println("push " + currentResult + " and " + currentSign);
                    stack.push(currentResult);
                    stack.push(currentSign); // sign for next parenthesis
                    currentResult = 0; // reset currentResult for next parenthesis
                    currentSign = 1; // reset current sign in new parenthesis
                } else if (c == ')') {
                    int prevSign = stack.pop(); // the first one must be sign
                    int prevNum = stack.pop();
                    //System.out.println("pop sign " + prevSign + " and num " + prevNum + ", currentResult is " + currentResult);
                    currentResult = prevNum + currentResult * prevSign;
                }
            }
        }
        return currentResult;
    }
}