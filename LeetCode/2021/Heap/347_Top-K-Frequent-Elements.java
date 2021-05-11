// https://leetcode.com/problems/top-k-frequent-elements/
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> intToCountMap = new HashMap<>();
        for(int num : nums) {
            intToCountMap.put(num, intToCountMap.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer,Integer>> heap = new PriorityQueue<>((a,b)->{
            return a.getValue() - b.getValue(); // ascending, min-heap
        });
        for(Map.Entry<Integer,Integer> entry : intToCountMap.entrySet()) {
            if (heap.size() < k) {
                heap.offer(entry);
            } else {
                if (entry.getValue() > heap.peek().getValue()) {
                    heap.poll();
                    heap.offer(entry);
                }
            }
        }
        int[] result = new int[k];
        int index = 0;
        while(!heap.isEmpty()) {
            result[index] = heap.poll().getKey();
            index++;
        }
        return result;
    }
}