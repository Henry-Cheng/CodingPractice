// https://leetcode.com/problems/kth-largest-element-in-an-array/
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // min heap, top/first is the minimum, so ascending in list
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> {
            return a - b; // ascending is min heap
        });
        for (int i = 0; i < nums.length; i++) {
            if (heap.size() < k) {
                heap.offer(nums[i]);
            } else {
                if (nums[i] >= heap.peek()) {
                    heap.poll();
                    heap.offer(nums[i]);
                }
            }
        }
        return heap.poll();
    }
}