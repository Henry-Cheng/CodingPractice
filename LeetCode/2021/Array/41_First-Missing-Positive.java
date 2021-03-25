// https://leetcode.com/problems/first-missing-positive/
class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) {
            return 1;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length) {
                nums[i] = -1;
            }
        }
        // now all nums are from 1 ~ nums.length or -1
        //     i   0 1  2 3
        //nums[i]  3 4 -1 1
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0) {
                if (nums[i] - i == 1) { // already matched
                    break;
                } else { // swap
                    int tmp = nums[nums[i] - 1];
                    if (tmp == nums[i]) {
                        break; // same number, skip here in case infinite loop
                    }
                    nums[nums[i] - 1] = nums[i];
                    nums[i] = tmp;
                }
            }
        }
        // now nums[i] should equal to i + 1
        //     i  0 1 2 3
        //nums[i] 1 2 3 4
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return i + 1;
    }
    
    public int firstMissingPositiveExtraSpace(int[] nums) {
        if (nums.length == 0) {
            return 1;
        }
        boolean[] exists = new boolean[nums.length + 2];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length + 1) {
                continue;
            } else { // the value is from 1 to n+1
                exists[nums[i]] = true;
            }
        }
        for (int i = 1; i < exists.length; i++) {
            if (!exists[i]) {
                return i;
            }
        }
        return 1;
    }
    
    // LC solution
    public int firstMissingPositiveLC(int[] nums) {
        int n = nums.length;

        // Base case.
        int contains = 0;
        for (int i = 0; i < n; i++)
          if (nums[i] == 1) {
            contains++;
            break;
          }

        if (contains == 0)
          return 1;

        // nums = [1]
        if (n == 1)
          return 2;

        // Replace negative numbers, zeros,
        // and numbers larger than n by 1s.
        // After this convertion nums will contain 
        // only positive numbers.
        for (int i = 0; i < n; i++)
          if ((nums[i] <= 0) || (nums[i] > n))
            nums[i] = 1;

        // Use index as a hash key and number sign as a presence detector.
        // For example, if nums[1] is negative that means that number `1`
        // is present in the array. 
        // If nums[2] is positive - number 2 is missing.
        for (int i = 0; i < n; i++) {
          int a = Math.abs(nums[i]);
          // If you meet number a in the array - change the sign of a-th element.
          // Be careful with duplicates : do it only once.
          if (a == n)
            nums[0] = - Math.abs(nums[0]);
          else
            nums[a] = - Math.abs(nums[a]);
        }

        // Now the index of the first positive number 
        // is equal to first missing positive.
        for (int i = 1; i < n; i++) {
          if (nums[i] > 0)
            return i;
        }

        if (nums[0] > 0)
          return n;

        return n + 1;
  }
}