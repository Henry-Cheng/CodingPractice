// https://leetcode.com/problems/flatten-nested-list-iterator/submissions/
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {

    private List<Integer> nums;
    private int currentPos;
        
    public NestedIterator(List<NestedInteger> nestedList) {
        nums = new LinkedList<>();
        currentPos = 0;
        flattern(nestedList);
    }

    private void flattern(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return;
        }
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) {
                nums.add(ni.getInteger());
            } else {
                flattern(ni.getList());
            }
        }
    }
    
    @Override
    public Integer next() {
        Integer num = nums.get(currentPos);
        currentPos++;
        return num;
    }

    @Override
    public boolean hasNext() {
        return currentPos < nums.size();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */


// LC solution with two stack

// import java.util.NoSuchElementException;

// public class NestedIterator implements Iterator<Integer> {

//     private Deque<List<NestedInteger>> listStack = new ArrayDeque<>();
//     private Deque<Integer> indexStack = new ArrayDeque<>();
    
//     public NestedIterator(List<NestedInteger> nestedList) {
//         listStack.addFirst(nestedList);
//         indexStack.addFirst(0);
//     }
        
//     @Override
//     public Integer next() {
//         if (!hasNext()) throw new NoSuchElementException();
//         int currentPosition = indexStack.removeFirst();
//         indexStack.addFirst(currentPosition + 1);
//         return listStack.peekFirst().get(currentPosition).getInteger();
//     }

    
//     @Override
//     public boolean hasNext() {
//         makeStackTopAnInteger();
//         return !indexStack.isEmpty();
//     }


//     private void makeStackTopAnInteger() {
    
//         while (!indexStack.isEmpty()) {
                        
//             // If the top list is used up, pop it and its index.
//             if (indexStack.peekFirst() >= listStack.peekFirst().size()) {
//                 indexStack.removeFirst();
//                 listStack.removeFirst();
//                 continue;
//             }

//             // Otherwise, if it's already an integer, we don't need to do anything.
//             if (listStack.peekFirst().get(indexStack.peekFirst()).isInteger()) {
//                 break;
//             }

//             // Otherwise, it must be a list. We need to update the previous index
//             // and then add the new list with an index of 0.
//             listStack.addFirst(listStack.peekFirst().get(indexStack.peekFirst()).getList());
//             indexStack.addFirst(indexStack.removeFirst() + 1);
//             indexStack.addFirst(0);
//         }
//     }
// }