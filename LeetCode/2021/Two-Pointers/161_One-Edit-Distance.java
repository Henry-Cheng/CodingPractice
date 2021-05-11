// https://leetcode.com/problems/one-edit-distance/
class Solution {
    public boolean isOneEditDistance(String s, String t) {
        if (s.equals(t) || Math.abs(s.length() - t.length()) > 1) {
            return false;
        }
        // option1: recursion: TLE
        //return isOneEditDistance(s, t, 0, 0) == 1;
        
        // option2: using the length of string to decide how to move pointers
        int i = 0;
        int j = 0;
        boolean foundMismatch = false;
        while(i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                if (!foundMismatch) {
                    foundMismatch = true;
                } else {
                    return false;
                }
                if (s.length() > t.length()) {
                    i++;
                } else if (s.length() < t.length()) {
                    j++;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return true;
    }
    
    public int isOneEditDistance(String s, String t, int i, int j) {
        int dist = 0;
        while(i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                if (dist > 1) {
                    return 2;
                } else {
                    dist++;
                    // 5 options
                    // delete s.charAt(i) from s
                    // delete t.charAt(j) from t
                    // insert s.charAt(i) to t
                    // insert t.charAt(j) to s
                    // replace
                    int dist1 = isOneEditDistance(s, t, i+1,j);
                    int dist2 = isOneEditDistance(s, t, i,j+1);
                    int dist3 = isOneEditDistance(s, t, i+1,j+1);
                    if (dist1 == 0 || dist2 == 0 || dist3 == 0) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            }
        }
        // i 0 1 2 3 --> 4
        if (i < s.length()) {
            return dist + s.length() - i;
        } else if (j < t.length()) {
            return dist + t.length() - j;
        } else {
            return dist;
        }
    }
}