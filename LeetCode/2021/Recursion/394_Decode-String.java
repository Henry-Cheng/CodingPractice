// https://leetcode.com/problems/decode-string/
class Solution {
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        String current = "";
        int times = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                current += c;
            }
            if (Character.isDigit(c) || c == '[' || i == s.length() - 1){
                sb.append(current);
                current = "";
                if (Character.isDigit(c)) {
                    times = times * 10 + c - '0';
                } else if (c == '[') {
                    int pos = findRightBracket(s, i);
                    String str = decodeString(s.substring(i+1, pos));
                    if (times == 0) {
                        sb.append(str);
                    } else {
                        for (int j = 0; j < times; j++) {
                            sb.append(str);
                        }
                    }
                    times = 0;
                    i = pos;
                }
            }
        }
        return sb.toString();
    }
    
    private int findRightBracket(String s, int start) {
        int left = 1;
        int right = 0;
        while(right < left) {
            start++;
            if (s.charAt(start) == ']') {
                right++;
            } else if (s.charAt(start) == '[') {
                left++;
            }
        }
        return start;
    }
}