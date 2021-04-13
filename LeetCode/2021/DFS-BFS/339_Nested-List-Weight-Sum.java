// https://leetcode.com/problems/nested-list-weight-sum/submissions/
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
class Solution {
    // LC recommended solution
    public int depthSum(List<NestedInteger> nestedList) {
        Deque<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        int sum = 0;
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { // this controls that we only traverse this level at size, we can continue offering the queue in the loop without impacting
                NestedInteger node = queue.poll();
                if (node.isInteger()) {
                    sum += node.getInteger() * level;
                } else {
                    for (NestedInteger nestedNode : node.getList()) {
                        queue.offer(nestedNode);
                    }
                }
            }
            level++;
        }
        return sum;
    }
    
    public int depthSum_stupid_rigid_solution(List<NestedInteger> nestedList) {
        /**
        using BFS
        
        [[1,[3, 4]],2,[5,[6, 7]]]
        2
        1, 5
        3, 4, 6, 7
        **/
        Deque<NestedInteger> queue = new LinkedList<>();
        HashSet<NestedInteger> visited = new HashSet<>();
        List<NestedInteger> pendingVisit = new LinkedList<>();
        // enqueue all entrypoints
        for (NestedInteger nestedInteger : nestedList) {
            if (nestedInteger.isInteger()) {
                queue.offer(nestedInteger);
                visited.add(nestedInteger);
            } else {
                pendingVisit.add(nestedInteger);
            }
        }
        // BFS
        int level = 1;
        int result = 0;
        while (!queue.isEmpty() || !pendingVisit.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger nestedInteger = queue.poll();
                result += nestedInteger.getInteger() * level;
            }
            // find more entrypoints
            List<NestedInteger> tmpPendingVisit = new LinkedList<>();
            for (NestedInteger node : pendingVisit) {
                for (NestedInteger nestedNode : node.getList()) {
                    if (nestedNode.isInteger()) {
                        queue.offer(nestedNode);
                    } else {
                        tmpPendingVisit.add(nestedNode);
                    }
                }
            }
            pendingVisit = tmpPendingVisit;
            // increment level
            level++;
        }
        return result;
    }
}