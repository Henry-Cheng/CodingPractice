// https://leetcode.com/problems/meeting-rooms-ii/submissions/
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 1) {
            return 1;
        }
        // sort intervals by start time
        Arrays.sort(intervals, (a,b)->{
            return a[0]-b[0]; // ascending
        });
        // originally: 25 69 67 13
        // now:        13 25 69 67
        
        // define a min-heap (PriorityQueue), so that the interval that "ends earlier" will be polled first
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
            return a[1]-b[1]; // ascending order
        });
        // enqueue first one
        pq.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] prev = pq.poll(); // poll the one with earliest end time
            int[] now = intervals[i];
            // check overlap
            if (prev[1] <= now[0]) { // no overlap
                prev[1] = now[1]; // extend the end time, since they can share a room
                pq.offer(prev); // put back into pq
            } else { // found overlap
                pq.offer(prev);
                pq.offer(now); // put both into pq, pq would sort again using min-heap
            }
        }
        return pq.size();
    }
}