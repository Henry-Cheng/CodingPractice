// https://leetcode.com/problems/valid-number/
class Solution {
    public boolean isNumber(String s) {
        String[] parts;
        if (s.contains("e")) {
            parts = s.split("e", 2); // gurantee array size is 2
            return (isDecimal(parts[0]) || isInteger(parts[0])) && isInteger(parts[1]);
        } else if (s.contains("E")) {
            parts = s.split("E", 2); // gurantee array size is 2
            return (isDecimal(parts[0]) || isInteger(parts[0])) && isInteger(parts[1]);
        } else {
            return isDecimal(s) || isInteger(s);
        }
    }
    
    public boolean isDecimal(String s) {
        if (s.length() == 0) {
            return false;
        }
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            s = s.substring(1, s.length());
        }
        if (s.length() == 0 || !s.contains(".")) {
            return false;
        }
        String[] array = s.split("\\.", 2);
        if (array[0].length() == 0 && array[1].length() == 0) {
            return false;
        }
        return (array[0].length() == 0 || allDecimalInt(array[0])) && (array[1].length() == 0 || allDecimalInt(array[1]));
        
    }
    
    public boolean isInteger(String s) {
        if (s.length() == 0) {
            return false;
        }
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            s = s.substring(1, s.length());
        }
        if (s.length() == 0) {
            return false;
        }
        return allDecimalInt(s);
    }
    
    public boolean allDecimalInt(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}