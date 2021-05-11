// https://leetcode.com/problems/reverse-integer/
class Solution {
    public int reverse(int x) {
        Deque<Integer> stack = new LinkedList<>();
        long sum = 0;
        while(true) {
            int digit = x % 10; // sign is auto considered
            sum = sum * 10 + digit; 
            x = x / 10;
            if (x == 0) {
                break;
            }
        }
        if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
            return 0;
        } else {
            return (int)sum ;
        }
    }
}