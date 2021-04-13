// https://leetcode.com/problems/letter-case-permutation/submissions/
class Solution {
    List<String> result;
    public List<String> letterCasePermutation(String S) {
        result = new LinkedList<>();
        backtrack(S, 0);
        return result;
    }
    private void backtrack(String S, int state) {
        // no termination condition, just add to result set
        result.add(S);
        // traverse each option (upper or lower)
        for (int i = state; i < S.length(); i++) {
            while(i < S.length() && !Character.isLetter(S.charAt(i))) {
                i++;
            }
            if (i < S.length()) { // in case no letter at all
                char c = S.charAt(i);
                StringBuilder sb = new StringBuilder();
                sb.append(S.substring(0, i)); // prefix
                if (Character.isLowerCase(c)) { // if lower case, make it upper case
                    sb.append(Character.toUpperCase(c));
                } else { // vice versa
                    sb.append(Character.toLowerCase(c));
                }
                if (i < S.length() - 1) {
                    sb.append(S.substring(i + 1, S.length())); // postfix
                }
                
                String newS = sb.toString();

                backtrack(sb.toString(), i + 1); // NOTE!!! it's i+1 instead of state+1
                
                // reset --> do nothing, since S is still S
            }
        }
    }
}