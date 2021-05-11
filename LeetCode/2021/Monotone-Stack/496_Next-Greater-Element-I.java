// https://leetcode.com/problems/next-greater-element-i/
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // traverse nums2 to build nextGreater map with monotone stack
        HashMap<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new LinkedList<>();
        for(int i = 0; i < nums2.length; i++) {
            while(!stack.isEmpty()) {
                if (nums2[stack.peek()] < nums2[i]) {
                    nextGreater.put(nums2[stack.peek()], nums2[i]);
                    stack.pop();
                } else {
                    break;
                }
            }
            if (i + 1 < nums2.length) {
                if (nums2[i] < nums2[i+1]) {
                    nextGreater.put(nums2[i], nums2[i+1]);
                } else {
                    stack.push(i);
                }
            } else {
                nextGreater.put(nums2[i], -1);
            }
        }
        // traverse nums1 to build result
        int[] result = new int[nums1.length];
        for (int j = 0; j < nums1.length; j++) {
            result[j] = nextGreater.getOrDefault(nums1[j], -1);
        }
        return result;
    }
}