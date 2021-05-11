// https://leetcode.com/problems/find-the-duplicate-number/
class Solution {
    public int findDuplicate(int[] nums) {
        int tortoise = nums[0];
        int hare = nums[0];
        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while(tortoise != hare);
        
        // find the entrace of the cycle
        tortoise = nums[0]; // tortoise start from beginning
        // hare starts from entrance
        while(tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        return hare; // or tortoise
    }
}