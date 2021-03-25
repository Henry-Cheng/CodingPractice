// https://leetcode.com/problems/unique-binary-search-trees/
class Solution {
    public int numTrees(int n) {
        if (n == 1) {
            return 1;
        }
        int[] count = new int[n+1];
        count[0] = 1;
        count[1] = 1;
        //          []      []      []
        //          j=1             i=3
        //  left=0           right=2
        for (int i = 2; i <=n ; i++) {
            for (int j = 1; j <= i; j++) {
                int left = j - 1;
                int right = i - j;
                count[i] += count[left] * count[right];
            }
        }
        return count[n];
    }
}