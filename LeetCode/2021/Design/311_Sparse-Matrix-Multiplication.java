// https://leetcode.com/problems/sparse-matrix-multiplication/
class Solution {
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int m = mat1.length;
        int k = mat1[0].length; // equals to mat2.length, otherwise invalid
        int n = mat2[0].length;
        
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // (i, j) means cell in result matrix
                // (i,j) = mat[i][] * mat2[][j]
                result[i][j] = multiplyRowAndColumn(mat1, i, mat2, j, k);
            }
        }
        return result;
    }
    
    protected int multiplyRowAndColumn(int[][] mat1, int row, int[][] mat2, int col, int k) {
        int num = 0;
        for (int i = 0; i < k; i++) {
            num += mat1[row][i] * mat2[i][col];
        }
        return num;
    }
}