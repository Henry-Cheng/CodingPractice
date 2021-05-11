// https://leetcode.com/problems/simplify-path/
class Solution {
    private static final String CURRENT_PATH = ".";
    private static final String PREV_PATH = "..";
    public String simplifyPath(String path) {
        /**
        e.g. /a/./b/../../c/
        -->
        a       .           b         ..    ..      c
        push   do nothing   push      pop   pop     push
        -->
        finall append reversed string in stack to stringbuilder and reverse all of them as output
        **/
        Deque<String> stack = new LinkedList<>();
        StringBuilder currentDir = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c != '/') { // always append check non-slash char
                currentDir.append(c);
            }
            if (c == '/' || i == path.length() - 1) {
                String currentDirStr = currentDir.toString();    
                if (currentDirStr.isEmpty()) {
                    // duplicate slash, ignore
                } else if (CURRENT_PATH.equals(currentDirStr)) {
                    // same path, ignore
                } else if (PREV_PATH.equals(currentDirStr)) {
                    // pop
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else { // real file or dir name
                    stack.push(currentDir.reverse().toString());
                }
                currentDir = new StringBuilder(); // clean up stringbuilder
                //System.out.println("found " + currentDirStr + " --> stack is " + stack);
            }
        }
        StringBuilder result = new StringBuilder();
        if (stack.isEmpty()) {
            result.append("/");
        } else {
            while(!stack.isEmpty()) {
                result.append(stack.pop() + "/");
            }
        }
        return result.reverse().toString();
    }
}