// https://leetcode.com/problems/remove-invalid-parentheses/
class Solution {
    Set<String> result = new HashSet<>(); 
    boolean[] deleted;
    public List<String> removeInvalidParentheses(String s) {
        // 0. initialize boolean array
        deleted = new boolean[s.length()];
        // 1. find num of invalid left and right parenthese
        List<Integer> invalidLeftAndRight = getInvalidLeftRight(s);
        int left = invalidLeftAndRight.get(0);
        int right = invalidLeftAndRight.get(1);
        if (left == 0 && right == 0) {
            result.add(s);
            return result.stream().collect(Collectors.toList());
        }
        // 2. backtracking/dfs to delete '(' or ')'' until left and right be 0
        backtrack(s, 0, left, right);
        return result.stream().collect(Collectors.toList());
    }
    
    // if left and right are 0, it does not mean s is valid already e.g. ()()((
    
    private void backtrack(String s, int start, int left, int right) {
        // check if s is already valid
        if (left == 0 && right == 0) {
            List<Integer> invalidLeftAndRight = getInvalidLeftRight(s);
            if (invalidLeftAndRight.get(0) == 0 && invalidLeftAndRight.get(1) == 0) {
                result.add(s);
            }
        }
        // remove the left or right parenthesis
        for (int i = start; i < s.length(); i++) {
            if (!deleted[i]) {
                char c = s.charAt(i);
                if (c == ')') { // remove right parentheses first
                    if (right > 0) {
                        // try remove this one
                        String newString = s.substring(0,i) + s.substring(i+1,s.length());
                        backtrack(newString, i, left, right - 1);
                        // now come back to try other options
                    }
                } else if (c == '(') {
                    if (left > 0) {
                        // try remove this one
                        String newString = s.substring(0,i) + s.substring(i+1,s.length());
                        backtrack(newString, i, left - 1, right);
                        // now come back to try other options
                    }
                } 
            }
        }
    }
    
    private List<Integer> getInvalidLeftRight(String s) {
        int left = 0;
        int right = 0;
        // (()))(
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                left++;
            } else if (c == ')'){
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }   
        }
        
        List<Integer> invalidLeftAndRight = new LinkedList<>();
        invalidLeftAndRight.add(left);
        invalidLeftAndRight.add(right);
        return invalidLeftAndRight;
    }
}