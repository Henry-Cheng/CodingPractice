// https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/
class Solution {
    public boolean splitString(String s) {
        if (s.length() == 1) {
            return false;
        }
        //000010
        // dp[i][j] means split at index i, 
        boolean result = false;
        int i = 0;
        while(i < s.length() && s.charAt(i) == '0') {
            i++;
        }
        if(i < s.length()) {
            // now try all possible options for the first number
            long num = 0;
            while(i < s.length() - 1) {
                num = num * 10 + (s.charAt(i) - '0');
                //System.out.println("try num " + num);
                result = result || canSplit(s.substring(i+1, s.length()), num);
                if (result) {
                    break;
                }
                i++;
            }
        } else {
            return false;
        }
        
        return result;
    }
    
    private boolean canSplit(String s, long prev) {
        if(prev == 0 && !s.isEmpty()) {
            return false;
        } 
        long current = prev - 1;
        String currentStr = String.valueOf(current);
        int i = 0;
        while(i < s.length() && s.charAt(i) == '0') {
            i++;
        }
        if(i < s.length()) {
            s = s.substring(i, s.length());
            if (s.startsWith(currentStr)) {
                if (currentStr.length() == s.length()) {
                    return true;
                } else {
                    return canSplit(s.substring(currentStr.length(), s.length()), current);
                }
            } else {
                return false;
            }
        } else {
            if (prev == 1) {
                return true;
            } else {
                return false;
            }
        }   
    }
}