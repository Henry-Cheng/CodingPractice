// https://leetcode.com/problems/koko-eating-bananas/
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // 30 11 23 4 20
        // k * h >= sum
        /**
        30 / k + 30 % k > 0 ? 1 : 0
        11 / k + 11 % k > 0 ? 1 : 0
        
        max k is max piles[i]
        min k is 1
        
        
        binary search to find k tha makes hour <= h
        
        h = 4
        nums: 1 3 | 5 7 9
                r   l
        **/
        int max = 0;
        for (int i = 0; i < piles.length; i++) {
            max = Math.max(max, piles[i]);
        }
        // k is between 1 and max
        int l = 1;
        int r = max;
        while(l <= r) {
            int k = l + (r - l)/2;
            
            if (k == 0) { // in case getHourByK fail
                break;
            }
            int hour = getHourByK(piles, k);
            //System.out.println("l " + l + "-->" + (lHour >= h) + "" + ", r " + r + "-->" + (rHour >= h));
            if (hour > h) { // eat too slow, need increase k
                l = k + 1;
            } else if (hour < h) { // eat too fast, need decrease k
                r = k - 1; 
            } else if (hour == h) { // hour == h
                r = k - 1; // move left, find left most
            }
        }
        /**
        the final situation is the l = r + 1, but lH and rH are reversed, since the larger the k is, the smaller the hour would be, so we should return r finally 
        
        h = 4, 
                r   <    l
        nums: 1 3   |    5  7 9
                lH  kH  rH
        
        h = 5
                r   <    l
        nums: 1 3   |    5  7 9
                lH  kH   rH
        
        h = 1
        nums:   |    3  5 7 9 11
             lH kH  rH
        **/
        //System.out.println("finally l " + l + "-->" + (lHour >= h) + "" + ", r " + r + "-->" + (rHour >= h));
        if (r == 0) {
            return 1;
        }
        System.out.println("l is " + l + ", r is " + r);
        int hourOfR = getHourByK(piles, r);
        return hourOfR == h ? r : l;
    }
    
    private int getHourByK(int[] piles, int k) {
        int sum = 0;
        for (int i = 0; i < piles.length; i++) {
            int reminder = piles[i] % k > 0 ? 1 : 0;
            int quotient = piles[i] / k;
            sum += reminder+ quotient;
        }
        return sum;
    }
}