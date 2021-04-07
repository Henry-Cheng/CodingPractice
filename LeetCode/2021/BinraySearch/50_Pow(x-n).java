// https://leetcode.com/problems/powx-n/
class Solution {
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        } 
        double newX = n > 0 ? x : 1/x;
        long N = n;
        return pow(newX, Math.abs(N));
    }
    
    private double pow(double x, long n) {
        double result = 1.0;
        long i = n;
        while (i >= 1) {
            if (i % 2 == 0) {
                double product = pow(x, i / 2);
                result *= product * product;
                break;
            } else {
                result *= x;
                i--;
            }
        }
        return result;
    }
}