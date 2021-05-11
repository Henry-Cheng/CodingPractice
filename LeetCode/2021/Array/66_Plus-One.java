// https://leetcode.com/problems/plus-one/
class Solution {
    public int[] plusOne(int[] digits) {
        int[] result = new int[digits.length + 1];
        int carryOver = 0;
        for (int i = digits.length-1; i>=0; i--) {
            int num = digits[i];
            if (i == digits.length-1) {
                num = num + 1;
            } else {
                num = num + carryOver;
            }
            int reminder = num % 10;
            carryOver = num / 10;
            result[i + 1] = reminder;
        }
        if (carryOver > 0) {
            result[0] = carryOver;
            return result;
        } else {
            return Arrays.copyOfRange(result, 1, result.length);
        }
    }
}