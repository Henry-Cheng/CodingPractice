// https://leetcode.com/problems/max-stack/
class MaxStack {

    Deque<Integer> numStack;
    Deque<Integer> maxStack;
    
    /** initialize your data structure here. */
    public MaxStack() {
        numStack = new LinkedList<>();
        maxStack = new LinkedList<>();
    }
    
    public void push(int x) {
        numStack.push(x);
        if (maxStack.isEmpty()) {
            maxStack.push(x);
        } else {
           maxStack.push(Math.max(maxStack.peek(), x)); 
        }
    }
    
    public int pop() {
        maxStack.pop();
        return numStack.pop();
    }
    
    public int top() {
        return numStack.peek();
    }
    
    public int peekMax() {
        return maxStack.peek();
    }
    
    public int popMax() {        
        Deque<Integer> tmpStack = new LinkedList<>();
        // NOTE: Integer cannot compare with Integer, must use equals()
        while(!maxStack.peek().equals(numStack.peek())) {
            tmpStack.push(numStack.pop());
            maxStack.pop();
        }
        int max = numStack.pop();
        maxStack.pop();
        // now push back
        while(!tmpStack.isEmpty()) {
            push(tmpStack.pop());
        }
        return max;
    }
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */