// https://leetcode.com/problems/find-median-from-data-stream/
class MedianFinder {

    /** initialize your data structure here. */
    PriorityQueue<Integer> minHeap; // stores the max half num
    PriorityQueue<Integer> maxHeap; // stores the min half num
    
    public MedianFinder() {
        minHeap = new PriorityQueue<Integer>((a, b) -> {
            return a-b; 
        }); 
        maxHeap = new PriorityQueue<Integer>((a, b) -> {
            return b-a; 
        });
    }
    // min heap: 4 5 6
    // max heap: 3 2 1
    public void addNum(int num) {
        if (minHeap.size() == 0 && maxHeap.size() == 0) { // add to any heap
            minHeap.offer(num);
        } else if (minHeap.size() > 0 && num >= minHeap.peek()) {
            minHeap.offer(num);
        } else {
            maxHeap.offer(num);
        }
        // rebalance the two heap
        if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        } else if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }
    }
    
    public double findMedian() {
        // corner case
        //System.out.println("minHeap is " + minHeap.toString());
        //System.out.println("maxHeap is " + maxHeap.toString());
        if (minHeap.size() == 0 && maxHeap.size() == 0) {
            return 0; // TODO
        } else if (minHeap.size() != 0 && maxHeap.size() == 0) {
            return minHeap.peek();
        } else if (minHeap.size() == 0 && maxHeap.size() != 0) {
            return maxHeap.peek();
        } 
        
        // if size is the same, return avg
        if (minHeap.size() == maxHeap.size()) {
            return (double)(minHeap.peek() + maxHeap.peek()) / 2;
        } else {
            return minHeap.size() > maxHeap.size() ? minHeap.peek() : maxHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */