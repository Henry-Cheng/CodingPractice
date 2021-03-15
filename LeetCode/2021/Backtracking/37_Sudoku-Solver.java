// https://leetcode.com/problems/sudoku-solver/solution/
class Solution {
    char[][] result = new char[9][9];
    
    public void solveSudoku(char[][] board) {
        // for each spot, there are options, we can find the options first
        List<Integer> pendingSpots = new LinkedList<>();
        Map<Integer, List<Character>> rowNumsMap = new HashMap<>();
        Map<Integer, List<Character>> colNumsMap = new HashMap<>();
        Map<Integer, List<Character>> sectionMap = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            // initialize colNums, no matter it is filled or not
            List<Character> rowNums = rowNumsMap.get(i);
            if (rowNums == null) {
                rowNums = new LinkedList<Character>();
                rowNumsMap.put(i, rowNums);
            }
            for (int j = 0; j < board[i].length; j++) {
                // initialize colNums, no matter it is filled or not
                List<Character> colNums = colNumsMap.get(j);
                if (colNums == null) {
                    colNums = new LinkedList<Character>();
                    colNumsMap.put(j, colNums);
                }
                int spot = getSpot(i, j);
                // initialize sectionMap, no matter it is filled or not
                int sectionPos = getSectionPos(i, j);
                List<Character> section = sectionMap.get(sectionPos);
                if (section == null) {
                    section = new LinkedList<Character>();
                    sectionMap.put(sectionPos, section);
                }
                // check if it is filled
                if (board[i][j] == '.') {
                    pendingSpots.add(spot);
                } else {
                    char filledNum = board[i][j];
                    rowNums.add(filledNum);
                    colNums.add(filledNum);
                    section.add(filledNum);
                }
            }
        }
        sudokuHelper(board, pendingSpots, 0, rowNumsMap, colNumsMap, sectionMap);
        // board = result; NOTE: cannot reassign board variable!!!!
        // for (int test = 0; test < 9; test++) {
        //     System.out.println(board[test]);
        // }
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                board[i][j] = result[i][j];
            }
        }
        

    }

    
    public void sudokuHelper(char[][] board, List<Integer> spots, int spotIndex, Map<Integer, List<Character>> rowNumsMap, Map<Integer, List<Character>> colNumsMap, Map<Integer, List<Character>> sectionMap) {
        if (spotIndex > spots.size() - 1) {
            // now the board has been filled
            // for (int i = 0; i < 9; i++) {
            //     System.out.println(rowNumsMap.get(i));
            // }
            // for (int test = 0; test < 9; test++) {
            //     System.out.println(board[test]);
            // }
            copyOutput(board);
            return;
        }
        //System.out.println(spots.size());
        int spotPos = spots.get(spotIndex);
        int row = getRow(spotPos);
        int col = getCol(spotPos);
        int sectionPos = getSectionPos(row, col);
        // System.out.println("row: " + row + ", col: " + col + ", rowNumsMap.keySet(): " + rowNumsMap.keySet() + " , colNumsMap.keySet(): " + colNumsMap.keySet() + ", sectionPos: " + sectionPos + ", sectionMap.keySet() " + sectionMap.keySet());
        // traverse each option for row-col
        for (int i = 1; i <= 9; i++) {
            char c = (char) (i + 48);
            if (!rowNumsMap.get(row).contains(c) && !colNumsMap.get(col).contains(c) && !sectionMap.get(sectionPos).contains(c)) {
                // try this option
                board[row][col] = c;
                rowNumsMap.get(row).add(c);
                colNumsMap.get(col).add(c);
                sectionMap.get(sectionPos).add(c);
                // move to next spot by recursion
                sudokuHelper(board, spots, spotIndex + 1, rowNumsMap, colNumsMap, sectionMap);
                // backtrack
                board[row][col] = '.';
                rowNumsMap.get(row).remove(rowNumsMap.get(row).size() - 1);
                colNumsMap.get(col).remove(colNumsMap.get(col).size() - 1);
                sectionMap.get(sectionPos).remove(sectionMap.get(sectionPos).size() - 1);
            }
        }
        
    }
    
    // 3-4 --> 45
    public int getSpot(int row, int col) {
        return (row + 1) * 10 + col + 1;
    }
            
    public int getRow(int spot) {
        return (int)(spot / 10) - 1;
    }
            
    public int getCol(int spot) {
        return spot % 10 - 1;
    }
            

    // 0-6 --> 1379
    public int getSectionPos(int row, int col) {
        int rowRangeLow = ((int) row / 3) * 3; // row 5, low 3// row 0, low 0
        int rowRangeHigh = rowRangeLow + 2; // row 5, high // row 0, high 2
        int colRangeLow = ((int) col / 3) * 3; // col 7, 6 // col 6, low 6
        int colRangeHigh = colRangeLow + 2; // col 7, 8 // col 6, high 8
        
        int sectionPos = (rowRangeLow + 1) * 1000 + (rowRangeHigh + 1) * 100 + (colRangeLow + 1) * 10 + (colRangeHigh + 1) * 1;
        return sectionPos;
    }
    
    public void copyOutput(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = board[i][j];
            }
        }
    }
}

// LC solution:
class Solution {
  // box size
  int n = 3;
  // row size
  int N = n * n;

  int [][] rows = new int[N][N + 1];
  int [][] columns = new int[N][N + 1];
  int [][] boxes = new int[N][N + 1];

  char[][] board;

  boolean sudokuSolved = false;

  public boolean couldPlace(int d, int row, int col) {
    /*
    Check if one could place a number d in (row, col) cell
    */
    int idx = (row / n ) * n + col / n;
    return rows[row][d] + columns[col][d] + boxes[idx][d] == 0;
  }

  public void placeNumber(int d, int row, int col) {
    /*
    Place a number d in (row, col) cell
    */
    int idx = (row / n ) * n + col / n;

    rows[row][d]++;
    columns[col][d]++;
    boxes[idx][d]++;
    board[row][col] = (char)(d + '0');
  }

  public void removeNumber(int d, int row, int col) {
    /*
    Remove a number which didn't lead to a solution
    */
    int idx = (row / n ) * n + col / n;
    rows[row][d]--;
    columns[col][d]--;
    boxes[idx][d]--;
    board[row][col] = '.';
  }

  public void placeNextNumbers(int row, int col) {
    /*
    Call backtrack function in recursion
    to continue to place numbers
    till the moment we have a solution
    */
    // if we're in the last cell
    // that means we have the solution
    if ((col == N - 1) && (row == N - 1)) {
      sudokuSolved = true;
    }
    // if not yet
    else {
      // if we're in the end of the row
      // go to the next row
      if (col == N - 1) backtrack(row + 1, 0);
        // go to the next column
      else backtrack(row, col + 1);
    }
  }

  public void backtrack(int row, int col) {
    /*
    Backtracking
    */
    // if the cell is empty
    if (board[row][col] == '.') {
      // iterate over all numbers from 1 to 9
      for (int d = 1; d < 10; d++) {
        if (couldPlace(d, row, col)) {
          placeNumber(d, row, col);
          placeNextNumbers(row, col);
          // if sudoku is solved, there is no need to backtrack
          // since the single unique solution is promised
          if (!sudokuSolved) removeNumber(d, row, col);
        }
      }
    }
    else placeNextNumbers(row, col);
  }

  public void solveSudoku(char[][] board) {
    this.board = board;

    // init rows, columns and boxes
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        char num = board[i][j];
        if (num != '.') {
          int d = Character.getNumericValue(num);
          placeNumber(d, i, j);
        }
      }
    }
    backtrack(0, 0);
  }
}