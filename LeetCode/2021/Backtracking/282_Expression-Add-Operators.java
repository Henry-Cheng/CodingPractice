// https://leetcode.com/problems/expression-add-operators/
class Solution {
    private List<String> result;
    public List<String> addOperators(String num, int target) {
        result = new LinkedList<>();
        // base cae
        if (num.length() == 1) {
            if (Integer.valueOf(num) == target) {
                result.add(num);
            }
            return result;
        }
        
        // backtrack to find a formula
        StringBuilder sb = new StringBuilder();
        sb.append(num.charAt(0));
        backtrack(num, 1, target, sb);
        // return result
        return result;
    }
    
    private void backtrack(String num, int pos, int target, StringBuilder sb) {
        if (pos == num.length()) {
            if (isValid(sb, target)) {
                result.add(sb.toString());
            }
            return;
        }
        // option1: add nothing
        sb.append(num.charAt(pos));
        backtrack(num, pos + 1, target, sb);
        sb.deleteCharAt(sb.length() - 1);
        
        // option2: +
        sb.append('+');
        sb.append(num.charAt(pos));
        backtrack(num, pos + 1, target, sb);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        // option3: -
        sb.append('-');
        sb.append(num.charAt(pos));
        backtrack(num, pos + 1, target, sb);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        // option4: *
        sb.append('*');
        sb.append(num.charAt(pos));
        backtrack(num, pos + 1, target, sb);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
    }

    // it's like LC "227 Basic Calculator II"
    private boolean isValid(StringBuilder sb, long target) {
        char[] array = sb.toString().toCharArray();
        Deque<Long> stack = new LinkedList<>();
        long currentNum = 0;
        StringBuilder currentNumChar = new StringBuilder();
        int i = 0;
        //System.out.println("String is: " + sb.toString());

        while(i < array.length && isDigit(array[i])) {
            currentNumChar.append(array[i]);
            i++;
        }
        // skip "05" as num
        if (currentNumChar.length() > 1 && currentNumChar.charAt(0) == '0') {
            return false;
        }
        currentNum = Long.valueOf(currentNumChar.toString());
        //System.out.println("get currentNum " + currentNum);
        currentNumChar = new StringBuilder();// TODO clear to reset
        stack.push(currentNum);
        
        while(i < array.length) {
            if (array[i] == '+') {
                i++;
                while(i < array.length && isDigit(array[i])) {
                    currentNumChar.append(array[i]);
                    i++;
                }
                // skip "05" as num
                if (currentNumChar.length() > 1 && currentNumChar.charAt(0) == '0') {
                    return false;
                }
                currentNum = Long.valueOf(currentNumChar.toString());
                //System.out.println("get currentNum after + " + currentNum);
                currentNumChar = new StringBuilder();// TODO clear to reset
                stack.push(currentNum);
                //System.out.println("now i is " + i + ", array.length is " + array.length);
            } else if (array[i] == '-') {
                i++;
                while(i < array.length && isDigit(array[i])) {
                    currentNumChar.append(array[i]);
                    i++;
                }
                // skip "05" as num
                if (currentNumChar.length() > 1 && currentNumChar.charAt(0) == '0') {
                    return false;
                }
                currentNum = Long.valueOf(currentNumChar.toString());
                currentNumChar = new StringBuilder();// TODO clear to reset
                stack.push(currentNum * -1);
            } else if (array[i] == '*') {
                i++;
                while(i < array.length && isDigit(array[i])) {
                    currentNumChar.append(array[i]);
                    i++;
                }
                // skip "05" as num
                if (currentNumChar.length() > 1 && currentNumChar.charAt(0) == '0') {
                    return false;
                }
                currentNum = Long.valueOf(currentNumChar.toString());
                currentNumChar = new StringBuilder();// TODO clear to reset
                stack.push(currentNum * stack.pop());
            }
        }
        long sum = 0;
        while(!stack.isEmpty()) {
            long num = stack.pop();
            sum += num;
            //System.out.println("add " + num + ", sum is " + sum);
        }
        return sum == target;
        
    }
    
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}