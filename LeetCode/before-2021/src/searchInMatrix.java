
public class searchInMatrix {
	public static void main(String[] args) {
		//int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,50}};
		int[][] matrix = {{1,3}};
		System.out.println(searchMatrix(matrix, 3));
	}
	// for special matrix: the 1st element of (i+1)th line is larger than last element of ith line
	public static boolean searchMatrixFastest(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int start = 0, rows = matrix.length, cols = matrix[0].length;
        int end = rows * cols - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (matrix[mid / cols][mid % cols] == target) {
                return true;
            } 
            if (matrix[mid / cols][mid % cols] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
	}
    public static boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        // search in rows
        int low = 0;
        int high = rows - 1;
        boolean find = false;
        while(low <= high) {
            int mid = low + (high - low)/2;
            if (target > matrix[mid][0]) {
                low = mid + 1;
            } else if (target < matrix[mid][0]) {
                high = mid - 1;
            } else {
                find = true;
                break;
            }
        }
        if (find) {
            return find;
        }
        if (high < 0) {
            return false;
        }
        if(low > 0) {
            low--;
        }

        int line = low;//NOTE!!!!
        // search in columns
        low = 0;
        high = columns - 1;
        while(low <= high) {
            int mid = (low + high)/2;
            if (target > matrix[line][mid]) {
                low = mid + 1;
            } else if (target < matrix[line][mid]) {
                high = mid - 1;
            } else {
                find = true;
                break;
            }
        }
        return find;
    }
}