// https://leetcode.com/problems/n-th-tribonacci-number/
class Solution {
    public int tribonacci(int n) {
        int[] array = new int[n + 1];
        Arrays.fill(array, -1);
        return tribonacci(n, array);
    }
    
    public int tribonacci(int n, int[] array) {
        // corner case
        if (n == 0 || n == 1) {
            array[n] = n;
            return n;
        } else if (n == 2) {
            array[2] = 1;
            return 1;
        }
        int result = 0;
        if (array[n-1] == -1) {
            array[n-1] = tribonacci(n-1, array);
        }
        result += array[n-1];
        if (array[n-2] == -1) {
            array[n-2] = tribonacci(n-2, array);
        }
        result += array[n-2];
        if (array[n-3] == -1) {
            array[n-3] = tribonacci(n-3, array);  
        }
        result += array[n-3];
        return result;
    }
}