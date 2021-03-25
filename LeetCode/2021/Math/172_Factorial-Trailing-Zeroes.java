// https://leetcode.com/problems/factorial-trailing-zeroes/solution/
class Solution {
    // option1: using BigInteger
    // option2: count factors of 5 and 2, then use min(# of 5, # of 2)
    // option3: only count 5, since 2 is always more than 5
    // option4: only count 5, and not traverse 1 to n, traverse at 5 interval
    // option5: count how many "n /= 5" exists, result in O(logn)
    public int trailingZeroes(int n) {
        if (n == 0) {
            return 0;
        }
        int count5 = 0;
        int count2 = 0;
        for (int i = 1; i<= n; i++) {
            if (i % 5 == 0) {
                //System.out.println("found " + i);
                int j = i;
                while(j % 5 == 0 && j != 0) {
                    count5++;
                    j = j / 5;
                    //System.out.println("j mod 5 = " + j);
                }
            }
            if (i % 2 == 0) {
                //System.out.println("found " + i);
                int j = i;
                while (j % 2 == 0 && j != 0) {
                    count2++;
                    j = j / 2;
                    //System.out.println("j mod 2 = " + j);
                }
            }
        }
        return Math.min(count5, count2);
    }
    
    // LC solution
    public int trailingZeroesLC(int n) {
        int zeroCount = 0;
        long currentMultiple = 5;
        while (n > 0) {
            n /= 5;
            zeroCount += n;
        }
        return zeroCount;
    }
}