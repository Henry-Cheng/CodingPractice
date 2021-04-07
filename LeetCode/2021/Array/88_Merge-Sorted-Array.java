// https://leetcode.com/problems/merge-sorted-array/submissions/
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // option1, merge and sort, O((m+n)log(m+n))
        // for (int i = 1; i <= n; i++) {
        //     nums1[nums1.length - i] = nums2[nums2.length - i];
        // }
        // Arrays.sort(nums1);
        
        // option2, copy one of the array and then put back one by one (O(m+n))
        if (nums2.length == 0 || nums1.length == 0) {
            return;
        }
        int[] nums1Copy = new int[m];
        for (int i = 0; i < m; i++) {
            nums1Copy[i] = nums1[i];
        }
        //printArr(nums1Copy);
        //printArr(nums2);
        //printArr(nums1);
        int p1 = 0; // pointer in nums1Copy
        int p2 = 0; // pointer in nums2
        int p3 = 0; // pointer in nums1
        while(p1 < nums1Copy.length || p2 < nums2.length) {
            if (p1 == nums1Copy.length) {
                while (p2 < nums2.length) {
                    nums1[p3] = nums2[p2];
                    p3++;
                    p2++;
                }
                break;
            } else if (p2 == nums2.length) {
                while (p1 < nums1Copy.length) {
                    nums1[p3] = nums1Copy[p1];
                    p3++;
                    p1++;
                }
                break;
            }
            if (nums1Copy[p1] <= nums2[p2]) {
                nums1[p3] = nums1Copy[p1];
                p1++;
            } else {
                nums1[p3] = nums2[p2];
                p2++;
            }
            p3++;
        }
    }
    
    private void printArr(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
        System.out.print("\n");
    }
}