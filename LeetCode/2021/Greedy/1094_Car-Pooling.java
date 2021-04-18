// https://leetcode.com/problems/car-pooling/
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {     
        int[] pickOrDrop = new int[1000];
        for (int i = 0; i < trips.length; i++) {
            int ppl = trips[i][0];
            int startPos = trips[i][1];
            int endPos = trips[i][2];
            
            pickOrDrop[startPos] -= ppl; // reduce capacity
            pickOrDrop[endPos] += ppl; // increase capacity
        }
        
        // for (int i = 0; i < pickOrDrop.length; i++) {
        //     if (pickOrDrop[i] != 0) {
        //         System.out.println(i + " --> " + pickOrDrop[i]);
        //     }
        // }
        
        for (int i = 0; i < pickOrDrop.length; i++) {
            capacity += pickOrDrop[i];
            if (capacity < 0) {
                return false;
            }
        }
        return true;
    }
}