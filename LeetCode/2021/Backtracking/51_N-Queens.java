// https://leetcode.com/problems/n-queens/solution/
class Solution {
    
    // gloabl variable to store solution set
    List<List<String>> result = new LinkedList<>();
    public List<List<String>> solveNQueens(int n) {
        // initialize a board
        List<StringBuilder> board = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < n; j++) {
                row.append('.');
            }
            board.add(row);
        }
        // assume n == 4
        // then we can put 4 queens in total, each at one row
        // [row 0] 0 1 2 3
        // [row 1]   1 2 3
        // [row 2]   1   3
        // [row 3]   1   3
        backtrack(board, 0);
        // return
        return result;
    }
    
    public void backtrack(List<StringBuilder> board, int spot) { // 0
        if (spot > board.size() - 1) {// 0 < 3 // 1<3 // 2<3
            List<String> validBoard = new LinkedList<>();
            for (StringBuilder row : board) {
                validBoard.add(row.toString());
            }
            result.add(validBoard);
            return;
        }
        // traverse all possible options
        for (int i = 0; i < board.size(); i++) {
            // check if row spot and col i valid
            if (validatePos(board, spot, i)) { //00 10 20
                // fix this option
                board.get(spot).setCharAt(i, 'Q'); //00, 12, 
                // backtrack and move to spot + 1
                backtrack(board, spot + 1);
                board.get(spot).setCharAt(i, '.');
            }
        }
    }
    
    public boolean validatePos(List<StringBuilder> board, int row, int col) {
        // check the possible 4 directions around the <row,col>
        // check row
        for(int j = 0; j < board.size(); j++) {
            if (board.get(row).charAt(j) == 'Q') {
                return false;
            }
        }
        // check col
        for(int i = 0; i < board.size(); i++) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        // check oblique
        int rowPointer = row;
        int colPointer = col;
        while(rowPointer >= 0 && rowPointer < board.size() && colPointer >= 0 && colPointer < board.size()) {
            if (board.get(rowPointer).charAt(colPointer) == 'Q') {
                return false;
            }
            rowPointer--;
            colPointer--;
        }
        
        rowPointer = row;
        colPointer = col;
        while(rowPointer >= 0 && rowPointer < board.size() && colPointer >= 0 && colPointer < board.size()) {
            if (board.get(rowPointer).charAt(colPointer) == 'Q') {
                return false;
            }
            rowPointer++;
            colPointer++;
        }
        rowPointer = row;
        colPointer = col;
        while(rowPointer >= 0 && rowPointer < board.size() && colPointer >= 0 && colPointer < board.size()) {
            if (board.get(rowPointer).charAt(colPointer) == 'Q') {
                return false;
            }
            rowPointer++;
            colPointer--;
        }
        rowPointer = row;
        colPointer = col;
        while(rowPointer >= 0 && rowPointer < board.size() && colPointer >= 0 && colPointer < board.size()) {
            if (board.get(rowPointer).charAt(colPointer) == 'Q') {
                return false;
            }
            rowPointer--;
            colPointer++;
        }
        return true;
    }
}

// LC solution
class Solution {
  int rows[];
  // "hill" diagonals
  int hills[];
  // "dale" diagonals
  int dales[];
  int n;
  // output
  List<List<String>> output = new ArrayList();
  // queens positions
  int queens[];

  public boolean isNotUnderAttack(int row, int col) {
    int res = rows[col] + hills[row - col + 2 * n] + dales[row + col];
    return (res == 0) ? true : false;
  }

  public void placeQueen(int row, int col) {
    queens[row] = col;
    rows[col] = 1;
    hills[row - col + 2 * n] = 1;  // "hill" diagonals
    dales[row + col] = 1;   //"dale" diagonals
  }

  public void removeQueen(int row, int col) {
    queens[row] = 0;
    rows[col] = 0;
    hills[row - col + 2 * n] = 0;
    dales[row + col] = 0;
  }

  public void addSolution() {
    List<String> solution = new ArrayList<String>();
    for (int i = 0; i < n; ++i) {
      int col = queens[i];
      StringBuilder sb = new StringBuilder();
      for(int j = 0; j < col; ++j) sb.append(".");
      sb.append("Q");
      for(int j = 0; j < n - col - 1; ++j) sb.append(".");
      solution.add(sb.toString());
    }
    output.add(solution);
  }

  public void backtrack(int row) {
    for (int col = 0; col < n; col++) {
      if (isNotUnderAttack(row, col)) {
        placeQueen(row, col);
        // if n queens are already placed
        if (row + 1 == n) addSolution();
          // if not proceed to place the rest
        else backtrack(row + 1);
        // backtrack
        removeQueen(row, col);
      }
    }
  }

  public List<List<String>> solveNQueens(int n) {
    this.n = n;
    rows = new int[n];
    hills = new int[4 * n - 1];
    dales = new int[2 * n - 1];
    queens = new int[n];

    backtrack(0);
    return output;
  }
}