// https://leetcode.com/problems/merge-intervals/
class Solution {
    public int[][] merge(int[][] intervals) {
        // base case
        if (intervals.length == 1) {
            return intervals;
        }
        // sort by starti, O(nlogn) if n is interval.length
        Arrays.sort(intervals, new Comparator<int[]>(){
            @Override
            public int compare(int[] pair1, int[] pair2) {
                return pair1[0] - pair2[0];
            }
        });
        // define stack to merge intervals
        Deque<Integer> stack = new LinkedList<>();
        List<int[]> result = new LinkedList<>();
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            //System.out.println("start " + start + ", end " + end);
            //System.out.println(stack);
            if (stack.isEmpty()) {
                stack.push(start);
                stack.push(end);
            } else if (start <= stack.peek()) {
                // check end
                if (end <= stack.peek()) {
                    // ignore this pair
                } else {
                    stack.pop(); // remove top one
                    stack.push(end); // push a new end
                }
            } else if (start > stack.peek()) {
                // pop out previous pair and store in output
                int[] pair = new int[2];
                pair[1] = stack.pop();
                pair[0] = stack.pop();
                result.add(pair);
                // push a new pair
                stack.push(start);
                stack.push(end);
            }
        }
        // finally pop up the rest pair
        int[] pair = new int[2];
        pair[1] = stack.pop();
        pair[0] = stack.pop();
        result.add(pair); 
        
        // output result
        int[][] resultArray = new int[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            resultArray[i] = result.get(i);
        }
        return resultArray;
    }
    
    // LC solution T(nlog), S(logn)
    // using the LinkedList<int[]> result to act as the stack, since it's all sequenctially access
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> result = new LinkedList<>();
        for (int[] interval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (result.isEmpty() || result.getLast()[1] < interval[0]) {
                result.add(interval);
            }
            // otherwise, there is overlap, so we merge the current and previous
            // intervals.
            else {
                result.getLast()[1] = Math.max(result.getLast()[1], interval[1]);
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}