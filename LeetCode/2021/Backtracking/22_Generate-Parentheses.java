// https://leetcode.com/problems/generate-parentheses/submissions/
class Solution {
    public List<String> result = new LinkedList<>();

    public List<String> generateParenthesis(int n) {
        if (n == 1) {
            result.add("()");
            return result;
        }
        // assume n = 3
        // level 1: '(' ')'
        // level 2: '(' ')'
        // level 3: '(' ')'
        // search condition: # of left brackets >= # of right bracketrs
        recursion(n, new StringBuilder(), 0, 0);
        //backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    // where is the index concept?
    public void recursion(int n, StringBuilder path, int leftCount, int rightCount) {
        if (path.length() == n * 2) {
            result.add(path.toString());
            return;
        }
        // for 2 options
        // try option1: "("
        if (leftCount < n) {
            path.append("(");
            leftCount++;
            recursion(n, path, leftCount, rightCount);
            path.deleteCharAt(path.length() - 1);
            leftCount--;
        }
        // try option2: ")"
        if (leftCount > rightCount) {
            path.append(")");
            rightCount++;
            recursion(n, path, leftCount, rightCount);
            path.deleteCharAt(path.length() - 1);
            rightCount--;
        }
    }
    
    public void backtrack(List<String> ans, StringBuilder path, int leftCount, int rightCount, int n){
        if (path.length() == n * 2) {
            ans.add(path.toString());
            return;
        }

        if (leftCount < n) {
            backtrack(ans, path.append("("), leftCount+1, rightCount, n);
        }
        if (rightCount < leftCount) {
            backtrack(ans, path.append(")"), leftCount, rightCount+1, n);
        }
    }
}