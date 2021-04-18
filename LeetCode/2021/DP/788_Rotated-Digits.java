// https://leetcode.com/problems/rotated-digits/
class Solution {
    public int rotatedDigits(int N) {
        HashSet<Integer> validDiff = new HashSet<>();
        validDiff.add(2);
        validDiff.add(5);
        validDiff.add(6);
        validDiff.add(9);
        HashSet<Integer> validSame = new HashSet<>();
        validSame.add(0);
        validSame.add(1);
        validSame.add(8);
        int count = 0;
        for (int i = 1; i <= N; i++) {
            if(validNum(i, validDiff, validSame)) {
                count++;
            }
        }
        return count;
    }
    
    private boolean validNum(int i, HashSet<Integer> validDiff, HashSet<Integer> validSame) {
        boolean isValidDiff = false;
        for (int j = 0; j < 5; j++) {
            int digit = i % 10;
            i = i / 10;
            // check digit
            if (!validDiff.contains(digit) && !validSame.contains(digit)) {
                return false;
            } else {
                if (validDiff.contains(digit)) {
                    isValidDiff = true;
                }
            }
            if (i == 0) {
                break;
            }
        }
        return isValidDiff;
    }
}