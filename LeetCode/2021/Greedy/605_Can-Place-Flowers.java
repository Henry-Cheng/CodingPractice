// https://leetcode.com/problems/can-place-flowers/
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0) {
            return true;
        }
        int count = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0) {
                // check left
                boolean leftOk = false;
                if (i - 1 < 0) {
                    leftOk = true;
                } else {
                    leftOk = (flowerbed[i-1] == 0 ? true : false);
                }
                // check right
                boolean rightOk = false;
                if (i + 1 >= flowerbed.length) {
                    rightOk = true;
                } else {
                    rightOk = (flowerbed[i+1] == 0 ? true : false);
                }
                if (leftOk && rightOk) {
                    flowerbed[i] = 1;
                    count++;
                }
            }
        }
        return count >= n;
    }
}