//https://www.lintcode.com/problem/850/ 

/**
 * Definition of Interval:
 * public class Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 * }
 */

public class Solution {
    /**
     * @param schedule: a list schedule of employees
     * @return: Return a list of finite intervals 
     */
    public List<Interval> employeeFreeTime(int[][] schedule) {
        // Write your code here
        // corner case
        if (schedule == null) {
            return null;
        }
        // brute force 
        // traverse each employee, dive the array into list of intervals
        // do union of any overlapped intervals
        // traverse anohter round for all unioned interval and find result
        // [[1,2,5,6],[1,3],[4,10]]
        // [1,2] [5,6] [1,3] [4,10]
        // [1,3] [5,6] [4,10]
        // [1,3] [4,10]
        // time complexity is O(n^2), n is the number of total interval
        // since for each interval we need to compare with all the others

        // now let's try a better idea by sorting first
        // [1,2] [5,6] [1,3] [4,10]
        // [1,2] [1,3] [4,10] [5,6]

        // prepare list of busy interval, O(n)
        List<Interval> busyInterval = new LinkedList<>();
        for (int i = 0; i < schedule.length; i++) {
            // TODO if j is not times of 2, need to validate here
            for (int j = 0; j < schedule[i].length; j = j+2) {
                busyInterval.add(new Interval(schedule[i][j], schedule[i][j+1]));
            }
        }

        // now sort all the intervals, O(nlgn)
        Collections.sort(busyInterval, new IntervalComparator());

        // now traverse again to find the free interval, O(n)
        // [1,3],[2,4],[6,7],[6,7],[6,8],[7,10]
        // prepare freeInterval 
        List<Interval> freeInterval = new LinkedList<>();
        Interval previousInterval = busyInterval.get(0);
        for (int i = 1; i < busyInterval.size(); i++) {
            Interval currentInterval = busyInterval.get(i);
            // previousInterval.start <= currentInterval.start
            // several siuations here
            // 1. previousInterval.end >= currentInterval.start
            // e.g. [1,3] [2,4] 
            if (previousInterval.end >= currentInterval.start) {
                previousInterval.end = Math.max(previousInterval.end, currentInterval.end); // enlarge previousInterval
            } else if (previousInterval.end < currentInterval.start) { // now we have gap
                freeInterval.add(new Interval(previousInterval.end, currentInterval.start));
                previousInterval = currentInterval;
            }
        }
        return freeInterval;
    }

    // implement a comparator in ascending order
    class IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
            if (a.start < b.start) {
                return -1; // a before b
            } else if (a.start > b.start) {
                return 1;
            } else {
                if (a.end < b.end) {
                    return -1; // a before b
                } else if (a.end > b.end) {
                    return 1; // b before a
                } else {
                    return 0; // it's a tie
                }
            }
        }
    }
}