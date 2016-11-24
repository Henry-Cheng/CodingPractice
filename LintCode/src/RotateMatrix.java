import java.util.*;

public class RotateMatrix {
    /* 
        1  2  3  4
        5  6  7  8
        9  10 11 12
        13 14 15 16
    */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
		rotate(matrix);
	}
	
    public static void rotate(int[][] matrix) {
        int n = matrix[0].length;
        if (n == 1) {
            return;
        }
        // now n >= 2
        // 0,0       0,n-1
        // n-1,0     n-1,n-1
        
        // 0,1       1, n-1
        // n-2,0     n-1,n-2
        int circles = (int) Math.ceil((double)n/2);
        for (int circle = 1; circle <= circles; circle++) {
            if (n % 2 == 1 && circle == circles) {
                break; // only 1 node in middle
            }
            for (int step = 0; step <= n - circle -1; step++) { // step in current circle
                int leftUpX = circle - 1; // fix in current circle
                int leftUpY = circle - 1 + step;
                int rightUpX = circle - 1 + step;
                int rightUpY = n - circle; // fix in current circle
                int leftDownX = n - circle - step;
                int leftDownY = circle - 1; // fix in current circle
                int rightDownX = n - circle; // fix in current circle
                int rightDownY = n - circle - step;
                
                int tmp = matrix[leftUpX][leftUpY];
                matrix[leftUpX][leftUpY] = matrix[rightUpX][rightUpY];
                matrix[rightUpX][rightUpY] = matrix[rightDownX][rightDownY];
                matrix[rightDownX][rightDownY] = matrix[leftDownX][leftDownY];
                matrix[leftDownX][leftDownY] = tmp;
            }
        }
    }
}