// https://leetcode.com/problems/minimum-size-subarray-sum/submissions/
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        // base case
        if (nums.length == 1) {
            return nums[0] >= target ? nums[0] : 0;
        }
        // define two pointers
        int left = 0;
        int right = left;
        // 2,3,1,2,4,3 --> 7
        // 2 --> < target, move right
        // 2,3 --> < target, move right
        // 2,3,1 --> < target, move right
        // 2,3,1,2 --> record it, >= target, move left
        //   3,1,2 --> < target, move right
        //   3,1,2,4 --> record it, >= target, move left
        //     1,2,4 --> record it, >= target, move left
        //       2,4 --> < target, move right
        //       2,4,3 --> record it, >= target, move left
        //         4,3 --> record it, >= target, move left
        //           3 --> < target, at the end
        int currentSum = nums[0]; // sum by current window
        int minDist = Integer.MAX_VALUE; // total minum distance
        while(left <= right && right < nums.length) { 
            if (currentSum >= target) { // 2<7,5<7,6<7,7>=7,6<7,10>7,7>=7,6<7,9>=7,7>=7
                minDist = Math.min(right - left + 1, minDist);//4,4,3,3,2
                if (left < nums.length) {
                    currentSum -= nums[left];//312=6,124=7,24=6,43=7,3
                }
                left++; 
            } else {
                right++; // 23, 231,2312,3124,243
                if (right < nums.length) {
                    currentSum += nums[right]; //23=5,231=6,2312=7,3124=10,243=9
                }
            }
        }
        return minDist == Integer.MAX_VALUE ? 0 : minDist;
    }
    
}

// LC solution by two pointers, T(n), S(1)
int minSubArrayLen(int s, vector<int>& nums)
{
    int n = nums.size();
    int ans = INT_MAX;
    int left = 0;
    int sum = 0;
    for (int i = 0; i < n; i++) {
        sum += nums[i];
        while (sum >= s) {
            ans = min(ans, i + 1 - left);
            sum -= nums[left++];
        }
    }
    return (ans != INT_MAX) ? ans : 0;
}

// LC solution by binary search, T(nlogn), S(n)

int minSubArrayLen(int s, vector<int>& nums)
{
    int n = nums.size();
    if (n == 0)
        return 0;
    int ans = INT_MAX;
    vector<int> sums(n + 1, 0); //size = n+1 for easier calculations
    //sums[0]=0 : Meaning that it is the sum of first 0 elements
    //sums[1]=A[0] : Sum of first 1 elements
    //ans so on...
    for (int i = 1; i <= n; i++)
        sums[i] = sums[i - 1] + nums[i - 1];
    for (int i = 1; i <= n; i++) {
        int to_find = s + sums[i - 1];
        auto bound = lower_bound(sums.begin(), sums.end(), to_find);
        if (bound != sums.end()) {
            ans = min(ans, static_cast<int>(bound - (sums.begin() + i - 1)));
        }
    }
    return (ans != INT_MAX) ? ans : 0;
}