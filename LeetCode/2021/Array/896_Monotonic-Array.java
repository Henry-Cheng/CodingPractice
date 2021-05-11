// https://leetcode.com/problems/monotonic-array/
class Solution {
    public boolean isMonotonic(int[] A) {
        if (A.length == 1) {
            return true;
        }

        Integer option = null;
        for (int i = 0; i+1 < A.length; i++) {
            if (A[i] == A[i+1]) {
                // do nothing
            } else if (A[i] < A[i+1]) {
                if (option == null) {
                    option = 0;
                } else if (option == 1) {
                    return false;
                }
            } else {
                if (option == null) {
                    option = 1;
                } else if (option == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}