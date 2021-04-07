// https://leetcode.com/problems/making-a-large-island/
class Solution {
    private HashMap<Integer, Integer> labelSizeMap = new HashMap<>();
    private static int label = 2;
    int maxResult = Integer.MIN_VALUE;
    public int largestIsland(int[][] grid) {
        // start from all 1 and label them and record size in map and maxResult
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) { // not labelled
                    int size = dfs(grid, i, j, label);
                    maxResult = Math.max(maxResult, size);
                    label++;
                }
            }
        }
        //System.out.println(labelSizeMap);
        //printGrid(grid);
        /**
         1 0
         0 1
         
         2 3
         4 3
        **/
        // start from all 0 and record how many labels can be connected, return the max
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    int sizeOfThisZero = 0;
                    HashSet<Integer> visitedLabel = new HashSet<>();
                    // check up
                    if (i - 1 >= 0 && grid[i - 1][j] > 1) {// labelled
                        if (!visitedLabel.contains(grid[i - 1][j])) {
                            sizeOfThisZero+=labelSizeMap.get(grid[i - 1][j]);
                            visitedLabel.add(grid[i - 1][j]);
                        }
                    }
                    // check down
                    if (i + 1 < grid.length && grid[i + 1][j] > 1) {// labelled
                        if (!visitedLabel.contains(grid[i + 1][j])) {
                            sizeOfThisZero+=labelSizeMap.get(grid[i + 1][j]);
                            visitedLabel.add(grid[i + 1][j]);
                        }
                    }
                    // check left
                    if (j - 1 >= 0 && grid[i][j - 1] > 1) {// labelled
                        if (!visitedLabel.contains(grid[i][j - 1])) {
                            sizeOfThisZero+=labelSizeMap.get(grid[i][j - 1]);
                            visitedLabel.add(grid[i][j - 1]);
                        }
                    }
                    // check right
                    if (j + 1 < grid[i].length && grid[i][j + 1] > 1) {// labelled
                        if (!visitedLabel.contains(grid[i][j + 1])) {
                            sizeOfThisZero+=labelSizeMap.get(grid[i][j + 1]);
                            visitedLabel.add(grid[i][j + 1]);
                        }
                    }
                    // if finding neighbor label, increment and store in maxResult
                    sizeOfThisZero += 1; // add the 0 itself
                    
                    maxResult = Math.max(maxResult, sizeOfThisZero);
                }
            }
        }
        return maxResult;
    }
    
    public int dfs(int[][] grid, int i, int j, int label) {
        grid[i][j] = label;
        int size = 1;
        // check up
        if (i - 1 >= 0 && grid[i - 1][j] == 1) {// not labelled
            size += dfs(grid, i - 1, j, label);
        }
        // check down
        if (i + 1 < grid.length && grid[i + 1][j] == 1) {// not labelled
            size += dfs(grid, i + 1, j, label);
        }
        // check left
        if (j - 1 >= 0 && grid[i][j - 1] == 1) {// not labelled
            size += dfs(grid, i, j - 1, label);
        }
        // check right
        if (j + 1 < grid[i].length && grid[i][j + 1] == 1) {// not labelled
            size += dfs(grid, i, j + 1, label);
        }
        labelSizeMap.put(label, size);
        return size;
    }
    
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print("\n");
        }
    }
}