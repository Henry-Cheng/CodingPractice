// https://leetcode.com/problems/daily-temperatures/
class Solution {
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        // build monotone decreasing stack to record the index of the element
        Deque<int[]> stack = new LinkedList<>();
        for(int i = 0; i < T.length; i++) {
            // NOTE: compare with stack.peek() first, otherwise this node may be pushed to stack already
            while (!stack.isEmpty()) {
                int[] peek = stack.peek();
                if (T[i] > peek[1]) {
                    result[peek[0]] = i-peek[0];
                    stack.pop();
                } else {
                    break;
                }
            }
            if (i + 1 < T.length) {
                if (T[i] < T[i+1]) {
                    result[i] = 1;
                } else {
                    stack.push(new int[]{i, T[i]});
                }
            } else {
                result[i] = 0;
            }
        }
        while (!stack.isEmpty()) {
            result[stack.pop()[0]] = 0;
        }
        return result;
    }
}