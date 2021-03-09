// https://leetcode.com/problems/3sum/submissions/
import java.util.Arrays;
    
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        if (nums.length < 3) {
            return result;
        }
        // sort the array by O(nlgn), so that we can use two pointers
        Arrays.sort(nums);
        // two-layer for loop result in O(n^2)
        for(int i = 0; i < nums.length; i++) {
            // NOTE: this is to avoid duplication
            if (i != 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            int target = 0 - nums[i];
            while (left < right) {
                boolean moveLeftPointer = false;
                boolean moveRightPointer = false;
                if (nums[left] + nums[right] == target) {
                    List<Integer> threeSum = new LinkedList<>();
                    threeSum.add(nums[i]);
                    threeSum.add(nums[left]);
                    threeSum.add(nums[right]);
                    result.add(threeSum);
                    
                    left++;
                    right--;
                    
                    moveLeftPointer = true;
                    moveRightPointer = false;
                    
                } else if (nums[left] + nums[right] > target) {
                    right--;
                    moveRightPointer = true;
                } else {
                    left++;
                    moveLeftPointer = true;
                }
                
                // NOTE: this is to avoid duplicate result
                while(left < right && moveLeftPointer && left - 1 >= 0 && nums[left] == nums[left-1]) {
                    left++;
                }
                while(left < right && moveRightPointer && right + 1 < nums.length && nums[right] == nums[right+1]) {
                    right--;
                }
                
            }
        }
        return result;
    }
}