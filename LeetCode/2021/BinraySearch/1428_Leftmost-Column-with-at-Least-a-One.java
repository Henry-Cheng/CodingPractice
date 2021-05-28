//https://leetcode.com/problems/leftmost-column-with-at-least-a-one/
/**
 * // This is the BinaryMatrix's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface BinaryMatrix {
 *     public int get(int row, int col) {}
 *     public List<Integer> dimensions {}
 * };
 */

class Solution {
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> len = binaryMatrix.dimensions();
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < len.get(0); i++) {
            int leftMost1 = binarySearch(binaryMatrix, i, 0, result == Integer.MAX_VALUE ? len.get(1) - 1 : result, len.get(1));
            if (leftMost1 < len.get(1)) { // found 1
                result = Math.min(leftMost1, result);
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int binarySearch(BinaryMatrix binaryMatrix, int row, int left, int right, int colLen) {
        while(left <= right) {
            int mid = left + (right - left)/2;
            //System.out.println("get row " + row + " col " + mid);
            int val = binaryMatrix.get(row, mid);
            if (val == 0) {
                left = mid+1;
            } else if (val == 1) {
                right = mid-1;
            }
        }
        /**
            0 0 0 1 1
                  l
                r
            0 0 0 0 0
                      l
                    r
            1 1 1 1 1
            l
           r
        **/
        return left;
    }
}