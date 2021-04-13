// https://leetcode.com/problems/range-sum-query-2d-immutable/
class NumMatrix {

    int[][] matrix;
    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        //System.out.println("[" + row1 + "," + col1 + "], " + "[" + row2 + "," + col2 + "]");
        int sum = 0;
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                //System.out.println("i is " + i + ", j is " + j + ", matrix[i][j] is " + matrix[i][j]);
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    // LC solution
	private int[][] dp;

	public NumMatrix(int[][] matrix) {
	    if (matrix.length == 0 || matrix[0].length == 0) return;
	    dp = new int[matrix.length + 1][matrix[0].length + 1];
	    for (int r = 0; r < matrix.length; r++) {
	        for (int c = 0; c < matrix[0].length; c++) {
	            dp[r + 1][c + 1] = dp[r + 1][c] + dp[r][c + 1] + matrix[r][c] - dp[r][c];
	        }
	    }
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
	    return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
	}
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */