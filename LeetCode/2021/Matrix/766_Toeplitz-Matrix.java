// https://leetcode.com/problems/toeplitz-matrix/
class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        /**
                   n          
         00 01 02 03
         10 11 12 13
     m   20 21 22 23 
     
        **/
        int i = matrix.length-1;
        int j = 0;
        while(i-1 >= 0) {
            i = i -1;
            //System.out.println("try i " + i);
            if (!check(matrix, i, j)) {
                return false;
            }
        }
        while(j + 1 <= matrix[0].length -1) {
            j = j + 1;
            //System.out.println("try j " + j);
            if (!check(matrix, i, j)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean check(int[][] matrix, int i, int j) {
        int num = matrix[i][j];
        while(i + 1 < matrix.length && j + 1< matrix[0].length) {
            i++;
            j++;
            //System.out.println("compare " + num + " with " + matrix[i][j]);
            if (num != matrix[i][j]) {
                return false;
            }
        }
        return true;
    }
}