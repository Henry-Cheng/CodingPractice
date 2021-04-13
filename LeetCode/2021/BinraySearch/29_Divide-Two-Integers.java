// https://leetcode.com/problems/divide-two-integers/submissions/
class Solution {
    public int divide(int dividend, int divisor) {
        // NOTE: must convert to long first!!! otherwise MIN_VALUE * -1 would be overflow
        long dividendCopy = dividend;
        long divisorCopy = divisor;
        
        // NOTE: Math.abs() would not work since it only works for int
        long dividendAbs = dividendCopy > 0 ? dividendCopy : (dividendCopy * -1);
        long divisorAbs = divisorCopy > 0 ? divisorCopy : (divisorCopy * -1);

        long quotient = 0;
        while (dividendAbs >= divisorAbs) {
            long divisorAbsMultiple = divisorAbs;
            long multiple = 1;
            while (dividendAbs >=  divisorAbsMultiple) {
                if (dividendAbs < divisorAbsMultiple + divisorAbsMultiple) {
                    break;
                } else {
                    divisorAbsMultiple += divisorAbsMultiple;
                    multiple += multiple;
                }
            }
            dividendAbs -= divisorAbsMultiple;

            quotient+=multiple;
        }
        if (isSameSymbol(dividend, divisor)) {
            // 8 / 3 = 2.xx
            return quotient > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) quotient;
        } else {
            // -8 / 2 = -2.xx
            return (int) (quotient * -1);
        }
    }
    
    private boolean isSameSymbol(int dividend, int divisor) {
        if ((dividend >= 0 && divisor >= 0) || (dividend < 0 && divisor < 0)) {
            return true;
        } else {
            return false;
        }
    }
}