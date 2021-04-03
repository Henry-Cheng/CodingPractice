// https://leetcode.com/problems/leftmost-column-with-at-least-a-one/
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
        List<Integer> dimensions = binaryMatrix.dimensions();
        int rows = dimensions.get(0);
        int cols = dimensions.get(1);
        int leftMostCol = Integer.MAX_VALUE;
        // rows has not relationship between each other, need to traverse one by one
        for (int i = 0; i < rows; i++) {
            // do binary search to find 1
            int left = 0;
            int right = cols - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                int midElement = binaryMatrix.get(i, mid);
                if (midElement == 1) { 
                    right = mid; // find leftMost
                } else {
                    left = mid + 1;
                }
            }
            // now left == right
            if (binaryMatrix.get(i, left) == 1) {
                leftMostCol = Math.min(leftMostCol, left);
            }
        }
        return leftMostCol == Integer.MAX_VALUE ? -1 : leftMostCol;
    }
}