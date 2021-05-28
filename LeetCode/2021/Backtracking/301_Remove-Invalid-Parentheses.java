//https://leetcode.com/problems/remove-invalid-parentheses/
class Solution {
    HashSet<String> result = new HashSet<>(); 
    HashSet<String> visited = new HashSet<>();
    public List<String> removeInvalidParentheses(String s) {
        // 0. get invalid left and right count
        int[] invalidLeftAndRight = getInvalidLeftRight(s);
        
        // 1. backtracking/dfs to delete '(' or ')'' until left and right be 0
        backtrack(s, invalidLeftAndRight[0], invalidLeftAndRight[1], visited);
        return result.stream().collect(Collectors.toList());
    }
    
    private void backtrack(String s, int left, int right, HashSet<String> visited) {
        // if left and right are 0, still need to check if s is valid 
        // e.g. ()( -> )(
        visited.add(s);
        if (left == 0 && right == 0) {
            if (isValid(s)) {
                result.add(s);
            }
            return;
        }
        // try to remove any left or right brackets 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (left > 0) {
                    // try remove this one
                    String newString = s.substring(0,i) + s.substring(i+1,s.length());
                    if (!visited.contains(newString)) {
                        backtrack(newString, left - 1, right, visited);
                    }
                }
            } else if (c == ')') {
                if (right > 0) {
                    // try remove this one
                    String newString = s.substring(0,i) + s.substring(i+1,s.length());
                    if (!visited.contains(newString)) {
                        backtrack(newString, left, right - 1, visited);
                    }
                }
            }
        }
    }
    
    private boolean isValid(String s) {
        int[] invalidCount = getInvalidLeftRight(s);
        return invalidCount[0] == 0 && invalidCount[1] == 0;
    }
    
    private int[] getInvalidLeftRight(String s) {
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
        return new int[]{left, right};
    }
}