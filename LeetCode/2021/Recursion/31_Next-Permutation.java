// https://leetcode.com/problems/next-permutation/submissions/
class Solution {
    public void nextPermutation(int[] nums) {
        if (nums.length <= 1) {
            // do nothing
            return;
        } 
        /**
        example 1
                        j-1  j
               i  i+1     
            6  2   5  4  3   1
        --> 6 [3]  5  4 [2]  1
        --> 6  3  [1  2  4   5]
        
        
        example2
                  j-1 j
               i  i+1
            6  4   5  3 2 1
        --> 6 [5] [4] 3 2 1
        --> 6  5  [1  2 3 4]
        **/
        // traverse from right to left, find the first acending 2 ~ 5
        int i = nums.length - 2;
        for (; i >= 0; i--) {
            //System.out.println("nums[i] " + nums[i] + " and nums[i+1] " + nums[i+1]);
            if (nums[i] < nums[i+1]) {
                break;
            }
        }
        //System.out.println("i is " + i);
        // now i is the last acending position, like the pos of 2 in example1
        if (i == -1) { // not found
            reverse(nums, 0, nums.length-1);
            return;
        }
        
        
        int j = i + 1;
        for (; j < nums.length; j++) { 
            // find the next smallest num that is strictly larger than nums[i], cannot be >=
            if (nums[j] <= nums[i]) {
                break;
            }
        }
        //System.out.println("found nums[j-1] " + nums[j-1]);
        // now j-1 is the min index larger than nums[i] 
        //System.out.println("before swap " + Arrays.toString(nums));
        swap(nums, i, j-1);
        //System.out.println("after swap " + Arrays.toString(nums));

        // reverse the range from i+1 to end
        reverse(nums, i+1, nums.length-1);
    }
    
    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
    
    private void reverse(int[] nums, int left, int right) {
        if (left < right) {
            swap(nums, left, right);
            reverse(nums, left + 1, right - 1); 
        }
    }
}