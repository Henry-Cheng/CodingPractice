// https://leetcode.com/problems/search-a-2d-matrix-ii/
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        /**
            1, 4, 7, 11,15
            2, 5, 8, 12,19
            3, 6, 9, 16,22
            10,13,14,17,24
            18,21,23,26,30
        **/
        // Option1: Search Space Reduction
        int i = matrix.length - 1;
        int j = 0;
        while(i >= 0 && i <= matrix.length-1 && j >= 0 && j <= matrix[0].length-1) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                i--;
            } else if (matrix[i][j] < target) {
                j++;
            }
        }
        return false;
    }
}