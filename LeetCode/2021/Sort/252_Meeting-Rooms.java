// https://leetcode.com/problems/meeting-rooms/
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length == 0) {
            return true;
        }
        /**
             3     6
         1 2
           1   4
           1          7
               4 5
               4      7
                      7 8
               
        **/
        // sort with O(nlogn)
        Arrays.sort(intervals, (a, b)->{
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
        for (int i = 0; i < intervals.length - 1; i++) {
            // compare i and i+1
            if (intervals[i][1] > intervals[i+1][0]) {
                return false;
            }
        }
        return true;
    }
}