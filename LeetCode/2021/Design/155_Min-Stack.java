// https://leetcode.com/problems/min-stack/
class MinStack {

    Deque<int[]> stack;
    
    /** initialize your data structure here. */
    public MinStack() {
        stack = new LinkedList<>(); 
    }
    
    public void push(int val) {
        int[] pair = new int[2];
        pair[0] = val;
        if (stack.isEmpty()) {
            pair[1] = val;
        } else {
            pair[1] = Math.min(stack.peek()[1], val);
        }
        stack.push(pair);
    }
    
    public void pop() {
        stack.pop();
    }
    
    public int top() {
        return stack.peek()[0];
    }
    
    public int getMin() {
        return stack.peek()[1];
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */