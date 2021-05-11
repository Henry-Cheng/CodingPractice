// https://leetcode.com/problems/next-greater-element-ii/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        /**
         length = 3
         0 1 2   3 4 5
         0 1 2   0 1 2
         1 2 1 | 1 2 1
         i j
             i   j
                 i j
        **/
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new LinkedList<>();
        for(int j = 0; j < nums.length * 2; j++) {
            int i = (j >= nums.length ? j-nums.length : j);
            int iPlus = (j == nums.length-1 ? 0 : i+1);
            //System.out.println("i = " + i + " iPlus is " + iPlus);
            while(!stack.isEmpty()) {
                if (nums[i] > nums[stack.peek()]) {
                    result[stack.peek()] = nums[i];
                    stack.pop();
                } else {
                    break;
                }
            }
            if (iPlus < nums.length) {
                if (nums[i] < nums[iPlus]) {
                    result[i] = nums[iPlus];
                } else {
                    stack.push(i);
                }
            }
        }
        return result;
    }
}