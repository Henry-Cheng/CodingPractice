// https://leetcode.com/problems/diagonal-traverse/
class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        /**
                    n
        00 01 02 03   04
        10 11 12   13 14 
        20 21   22 23 24
        30   31 32 33 34   m
        **/
        int[] right = {0, 1}; // right
        int[] down = {1, 0}; // down
        int[] rightDiagonal = {-1, 1}; // right diagonal
        int[] leftDiagonal = {1, -1}; // left diagonal
        
        int[] result = new int[matrix.length * matrix[0].length];
        result[0] = matrix[0][0];
        int[] count = {1};
        
        int[] pos = new int[2];
        while(true) {
            //System.out.println("now at " + matrix[pos[0]][pos[1]] + " with count " + count[0]);
            // first row
            if (pos[0] == 0) { // try move right first
                // if can move right
                if (move(matrix, pos, right, count, result, "right")) {
                    move(matrix, pos, leftDiagonal, count, result, "left-diagonal");
                } else {
                    move(matrix, pos, down, count, result, "down");
                    move(matrix, pos, leftDiagonal, count, result, "left-diagonal");
                }
            }
            // last row
            if (pos[0] == matrix.length - 1) {
                move(matrix, pos, right, count, result, "right");
                move(matrix, pos, rightDiagonal, count, result, "right-diagnoal"); 
            }
            // first col
            if (pos[1] == 0) { // try move down first
                // if can move down
                if (move(matrix, pos, down, count, result, "down")) {
                   move(matrix, pos, rightDiagonal, count, result, "right-diagonal"); 
                } else {
                    move(matrix, pos, right, count, result, "right");
                    move(matrix, pos, rightDiagonal, count, result, "right-diagnoal"); 
                }
            } 
            // last col
            if (pos[1] == matrix[0].length -1) {
                move(matrix, pos, down, count, result, "down");
                move(matrix, pos, leftDiagonal, count, result, "left-diagonal");     
            }
            if(count[0] == matrix.length * matrix[0].length) {
                break;
            }
        }
        return result;
    }

    
    public boolean move(int[][] matrix, int[] pos, int[] diff, int[] count, int[] result, String direction) {
        int maxStep = Integer.MAX_VALUE;
        if (diff[0] == 0 && diff[1] == 1) {
            maxStep = 1;
        } else if (diff[0] == 1 && diff[1] == 0) {
            maxStep = 1;
        }
        int step = 0;
        boolean canMove = false;
        while (pos[0] + diff[0] >= 0 && pos[0] + diff[0] < matrix.length && 
           pos[1] + diff[1] >= 0 && pos[1] + diff[1] < matrix[0].length && step < maxStep) {
            pos[0] += diff[0];
            pos[1] += diff[1];
            result[count[0]] = matrix[pos[0]][pos[1]];
            count[0]++;
            step++;
            canMove = true;
        }
        //System.out.println("move " + direction + " by step " + step + ", count is " + count[0]);
        return canMove;
    }
}