// https://leetcode.com/problems/first-bad-version/submissions/
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        if (n == 1) {
            return 1;
        }  
        int left = 1;
        int right = n;
        while(left < right) {
            // The reason for TLE when using (left + right)/2 is that mid becomes negative due to overflow, that will result in calling the API a lot of times without getting the actual result.
            int mid = left + (right - left) / 2; // do not use (left+right)/2 
            if (isBadVersion(mid)) { // check left-hand
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}