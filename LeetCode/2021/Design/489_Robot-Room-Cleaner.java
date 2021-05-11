// https://leetcode.com/problems/robot-room-cleaner/
// https://blog.csdn.net/qq_21201267/article/details/107581006
/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */


class Solution {
    public void cleanRoom(Robot robot) {
        // we use relative position, can initally set it to be [0,0], can also set it anything like (-500,199)
        backtrack(robot, new HashSet<>(), 0, 0, 0);
    }
    
    // must in sequence up->right->down->left, since we always turn right in backtrack()
    private static int[][] DIR = {
        {-1,0},{0,1},{1,0},{0,-1}
    };
    
    private void backtrack(Robot robot, HashSet<String> visited, int i, int j, int direction) {
        // visit this spot
        robot.clean();
        visited.add(i + "-" + j);
        
        // try all 4 directions (k is useless)
        for (int k = 0; k < 4; k++) {
            // use the same direction, always move untill stop
            int newI = i + DIR[direction][0];
            int newJ = j + DIR[direction][1];
            
            // if not visited, and can move()
            if (!visited.contains(newI + "-" + newJ) && robot.move()) { 
                backtrack(robot, visited, newI, newJ, direction);
                // reset
                goBack(robot);
            } 
            // turn right, since it matches with DIR
            robot.turnRight();
            // also set the direction to ruen right
            direction = (direction + 1) % 4;
        }
    }
    
    private void goBack(Robot robot) {
        // turn 180
        robot.turnRight();
        robot.turnRight();
        robot.move();
        // turn 180
        robot.turnRight();
        robot.turnRight();
    }
}