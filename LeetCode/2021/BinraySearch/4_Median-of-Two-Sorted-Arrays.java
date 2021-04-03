// https://leetcode.com/problems/median-of-two-sorted-arrays/
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m == 0) {
            if (n % 2 == 0) { // even
                System.out.println("nums2[n / 2 - 1] is " + nums2[n / 2 - 1] + ", nums2[n / 2] is " + nums2[n / 2]);
                return ((double) (nums2[n / 2 - 1] + nums2[n / 2]) / 2);
            } else { // odd
                return nums2[n / 2];
            }
        } else if (n == 0) {
            if (m % 2 == 0) {
                System.out.println("nums1[m / 2 - 1] is " + nums1[m / 2 - 1] + ", nums1[m / 2] is " + nums1[m / 2]);
                return ((double) (nums1[m / 2 - 1] + nums1[m / 2]) / 2);
            } else {
                return nums1[m / 2];
            }
        }
        boolean isEven = ((m + n) % 2 == 0);
        int leftSizeTotal = isEven ? ((m + n) / 2) : ((m + n) / 2 + 1);// 13 --> left is 7, 14 --> left is 7
        //System.out.println("leftSizeTotal is " + leftSizeTotal);
        return binarySearch(nums1, 0, m, m/2, nums2, isEven, leftSizeTotal);
    }
    
    /**
    ```
    isEven == false
        (7 nums)         (6 nums)   
        a0 a1          | a2 a3 a4   
        b0 b1 b2 b3 b4 | b5 b6 b7  
    
        (7 nums)         (6 nums)  
        a0 a1 a2 a3 a4 | MAX_VALUE  
        b0 b1          | b2 b3 b4 b5 b6 b7 
    
        (7 nums)                (6 nums)  
        MIN_VALUE             | a0 a1 a2 a3 a4   
        b0 b1 b2 b3 b4 b5 b6  | b7 

    isEven == true
        (7 nums)      (7 nums)    
        a0 a1 a2    | a3 a4 a5  
        b0 b1 b2 b3 | b4 b5 b6 b7  
    
        (7 nums)            (7 nums)   
        a0 a1 a2 a3 a4 a5 | MAX_VALUE  
        b0                | b1 b2 b3 b4 b5 b6 b7 
        
        (7 nums)               (7 nums)  
        MIN_VALUE            | a0 a1 a2 a3 a4 a5  
        b0 b1 b2 b3 b4 b5 b6 | b7  
        
    **/
    private double binarySearch(int[] nums1, int start1, int end1, int partitionInA, int[] nums2, boolean isEven, int leftSizeTotal) {
        System.out.println("Start is " + start1 + ", end is " + end1);
        // find partition in A and B
        //boolean isAEven = ((end1-start1) % 2 == 0); // NOTE: put partitionA by even or odd scenarios
        // partition is in front of index partitionInA
        int partitionInB = leftSizeTotal - partitionInA; // in front of index partitionInB
        System.out.println("partitionInA is " + partitionInA + ", partitionInB is " + partitionInB);
        // set a left and a right
        int aLeft = 0;
        int aRight = 0;
        if (partitionInA <= 0 || partitionInA > nums1.length|| nums1.length == 0) { // NOTE: check if the array is empty
            aLeft = Integer.MIN_VALUE;
        } else {
            aLeft = nums1[partitionInA - 1];
        }
        if (partitionInA >= nums1.length || partitionInA < 0 || nums1.length == 0) { // NOTE: check if the array is empty
            aRight = Integer.MAX_VALUE;
        } else {
            aRight = nums1[partitionInA];
        }
        // set b left and b right
        int bLeft = 0;
        int bRight = 0;
        if (partitionInB <= 0 || partitionInB > nums2.length || nums2.length == 0) { // NOTE: check if the array is empty
            bLeft = Integer.MIN_VALUE;
        } else {
            bLeft = nums2[partitionInB - 1];
        }
        if (partitionInB < 0 || partitionInB >= nums2.length || nums2.length == 0) { // NOTE: check if the array is empty
            bRight = Integer.MAX_VALUE;
        } else {
            bRight = nums2[partitionInB];
        }
        System.out.println("aLeft is " + aLeft + ", aRight is " + aRight + ", bLeft is " + bLeft + ", bRight is " + bRight);
        
        // compare 
        if (aLeft <= bRight && bLeft <= aRight) { // found it
            if (isEven) {
                // return avg
                //System.out.println("return avg");
                return (double) (Math.max(aLeft, bLeft) + Math.min(aRight, bRight))/2;
            } else {
                //System.out.println("return max");
                return Math.max(aLeft, bLeft);
            }
        } else { // not found, and not hit boundary yet, move the partitionInA by moving start1 or end1
            // 1 2 3 | 4 5 6
            // 1 1 1 | 2 3 4
            if (aLeft > bRight) { // move partitionInA to left, partitionInA is the end (excisive)
                end1 = partitionInA;
                // int sum = start1 + end1;
                // int oldParitionInA = partitionInA;
                // partitionInA = sum / 2;
                // if (oldParitionInA == partitionInA) {
                //     partitionInA--;
                // }
                partitionInA--;
                //double mid = (double)((start1 + end1)/2);
                //partitionInA = (int) Math.floor(mid); // makes sure partitionInA moves left
            } 
            if (bLeft > aRight) { // move partitionInA to right, partitionInA is the start (inclusive)
                start1 = partitionInA;
                // int sum = start1 + end1;
                // // if (sum % 2 != 0) {
                // //     partitionInA = (sum - sum % 2) / 2 + 1;   
                // // } else {
                // //     partitionInA = (sum - sum % 2) / 2;   
                // // }
                // int oldParitionInA = partitionInA;
                // partitionInA = sum / 2;
                // if (partitionInA == oldParitionInA) {
                //     partitionInA++;
                // }
                partitionInA++;
                System.out.println("stat: " + start1 + ", end: " + end1 + ", partitionInA: " + partitionInA);
                //double mid = (double) ((start1 + end1)/2);
                //partitionInA = (int) Math.ceil(mid); // makes sure partitionInA moves right
            }
            // 1 2
            // 3 4
            return binarySearch(nums1, start1, end1, partitionInA, nums2, isEven, leftSizeTotal);
        }
    } 
}