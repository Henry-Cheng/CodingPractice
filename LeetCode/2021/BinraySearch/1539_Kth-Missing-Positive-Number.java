// https://leetcode.com/problems/kth-missing-positive-number/
class Solution {
    /**
    0  1 2 3 4
    [2,3,4,7,11], k=5
    l         r 
         mid --> 4 - 3 = 1 missed, move right
           l  r
           mid --> 7 - 4 = 3 missed 3, move right
              l --> 7 - 4 = 3 missed, must be at left-hand side or right-hand side
         
    0  1 2 3 4
    [2,3,4,7,11], k=1
    l        r
         m --> 4 - 3 = 1 missed, move left
    l    r
       m --> 3 - 2 = 1 missed, move left
    l  r
    m --> 2 - 1 = 1 missed, move left
    r
    must be left-hand side of l   
    
    
    **/
    public int findKthPositive(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] - mid - 1 >= k) { // find leftMost
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // now left = right, 
        System.out.println("left is " + arr[left] + ", right is " + arr[right]);
        // the missing num is either between left and right, or <left or > right
        int leftMissed = arr[left] - left - 1;
        System.out.println("leftMissed is " + leftMissed);
        if (k <= leftMissed) {
            return arr[left] - (leftMissed - k) - 1;
        } else { // k > rightMissed, must be right hand side
            return k - leftMissed + arr[left];
        }
    }
    
    public int findKthPositive_brute_force(int[] arr, int k) {
        boolean[] visited = new boolean[2001];
        for (int i = 0; i < arr.length; i++) {
            visited[arr[i]] = true;
        }
        int count = 0;
        for (int j = 1; j < visited.length; j++) {
            if (!visited[j]) {
                count++;
            }
            if (count == k) {
                return j;
            }
        }
        return -1;
    }
}