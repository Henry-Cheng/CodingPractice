// https://leetcode.com/problems/random-pick-with-weight/
class Solution {

    //        w =    1    2      3       4 
    //prefiSum  = 0  1    3      6       10
    //percentage= 0% 10%  30%    60%     100%
    // 100% --> random <= 10  --> 6 < random <= 10 --> 40% chance --> 4
    // 60%  --> random <= 6   --> 3 < random <= 6  --> 30% chance --> 3
    // 30%  --> random <= 3   --> 1 < random <= 3  --> 20% chance --> 2
    // 10%  --> random <= 1   --> 0 < random <= 1  --> 10% chance --> 1
    private int[] prefixSum;
    private int[] w;
    private int sum;
    public Solution(int[] w) {
        this.w = w;
        this.prefixSum = new int[w.length + 1];
        prefixSum[0] = 0;
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = w[i-1] + prefixSum[i-1];
        }
        this.sum = prefixSum[prefixSum.length - 1];
    }
    
    public int pickIndex() {
        if (w.length == 1) {
            return 0;
        }
        // random = [0, sum]
        int random = getRandom(1, sum);
        int foundIndex = -1;
        // linear search
        // foundIndex = linearSearch(prefixSum, random);
        // binrary search
        foundIndex = binarySearch(prefixSum, random, 0, prefixSum.length - 1);
        //System.out.println("Random is: " + random + ", foundIndex is: " + foundIndex + ", prefixSum[right]: " + prefixSum[foundIndex+1] + ", prefixSum[left]: " + prefixSum[foundIndex]);
        return foundIndex;
    }
    //
    // w:             1 2 3  4  5  6
    // index:      0  1 2 3  4  5  6
    // prefix sum: 0  1 3 6 10 15 21
    // random: 12
    // left = 0, right =6, mid = 3, prefixSum[mid]=6
    // left = 3, right =6, mid = 4, prefixSum[mid]=10
    // left = 4, right =6, mid = 5, prefixSum[mid]=15
    // left = 4, right =5
    
    protected int binarySearch(int[] prefixSum, int random, int left, int right) {
        while(left < right - 1) {
            int mid = (left + right) / 2;
            if (random == prefixSum[mid]) {
                return mid - 1;
            } else if (random < prefixSum[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        // now left == right -1
        return left;
    }
    
    protected int linearSearch(int[] prefixSum, int random) {
        for (int i = 0; i < prefixSum.length - 1; i++) {
            if (random > prefixSum[i] && random <= prefixSum[i+1]) {
                return i;
            }
        }
        return -1; // never hit here
    }
    // both end inclusive
    protected int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */