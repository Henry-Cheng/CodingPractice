// https://leetcode.com/problems/search-a-2d-matrix/
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // find row
        int l = 0;
        int r = matrix.length - 1;
        while(l <= r) {
            int mid = l + (r - l) / 2;
            int num = matrix[mid][0];
            if (num == target) {
                return true;
            } else if (num < target) {
                l = mid + 1;
            } else if (num > target) {
                r = mid - 1;
            }
        }
        /**
         r 1 3
         l
        **/
        if (r < 0) {
            return false;
        }
        int row = r;
        l = 0;
        r = matrix[0].length - 1;
        while(l <= r) {
            int mid = l + (r - l) / 2;
            int num = matrix[row][mid];
            if (num == target) {
                return true;
            } else if (num < target) {
                l = mid + 1;
            } else if (num > target) {
                r = mid - 1;
            }
        }
        return false;
    }
}