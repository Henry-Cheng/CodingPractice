// https://leetcode.com/problems/sliding-window-median/
class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> low = new PriorityQueue<Integer>(Collections.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<Integer>();

        // initially
        double[] result = new double[nums.length - k + 1];
        int left = 0;
        int right = 0;
        // now move the window
        int count = 0;
        while(right < nums.length) {
            while (right - left + 1 <= k) {
                //System.out.println("add " + nums[right]);
                add(nums[right], low, high);
                //System.out.println("low is " + low.toString() + ", high is " + high.toString() + " smallest in high is " + high.peek());
                right++;
            }
            // now we fills the window
            result[count] = getMedian(low, high);
            //System.out.println("now we at " + nums[left] + "-->" + nums[right-1] + "\n low is " + low.toString() + "\n high is " + high.toString() + "\n median is " + result[count]);
            // move left
            //System.out.println("remove " + nums[left]);
            remove(nums[left], low, high);
            //System.out.println("low is " + low.toString() + ", high is " + high.toString());
            left++;
            
            count++;
        }
        return result;
    }
    
    private void add(int num, PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        if (low.isEmpty()) {
            high.offer(num);
        } else if (high.isEmpty()){
            low.offer(num);
        } else if (num <= low.peek()) {
            low.offer(num);
        } else if (num >= high.peek()) {
            high.offer(num);
        } else { // add to any, we will rebalance it
            high.offer(num);
        }
        // rebalance 
        balance(low, high);
    }
    
    private void balance(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        // rebalance 
        if (low.size() - high.size() > 1) {
            high.offer(low.poll());
        } else if (high.size() - low.size() > 1) {
            low.offer(high.poll());
        }
    }
    private void remove(int num, PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        if (low.isEmpty() || num > low.peek()) {
            high.remove(num);
        } else {
            low.remove(num);
        }
        balance(low, high);
    }
    
    private double getMedian(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        if (low.isEmpty()) {
            return high.peek();
        } else if (high.isEmpty()) {
            return low.peek();
        }
        
        if (low.size() == high.size()) {
            return (double)low.peek()/2 + (double)high.peek()/2;
        } else if (low.size() > high.size()) {
            return (double) low.peek();
        } else {
            return (double) high.peek();
        }
    }
}