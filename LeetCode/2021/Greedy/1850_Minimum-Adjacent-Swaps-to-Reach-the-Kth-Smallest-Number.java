// https://leetcode.com/contest/weekly-contest-239/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/
class Solution {
    // 543122
    // 543212
    // 543221
    /**
    65 3 5 4 21
    65 4 1235
    **/
    public int getMinSwaps(String num, int k) {
        // find next kth min
        String kthMin = num;
        for (int i = 0; i < k; i++) {
            kthMin = findNextMin(kthMin);
        }
        //System.out.println("kthMin is " + kthMin);
        // check how many swap we need，we can do brute force here, since it only swaps adjacent num
        int result = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == kthMin.charAt(i)) {
                continue;
            } else {
                // found the mismatch num, start brute force swap
                result = countSwap(num.substring(i, num.length()).toCharArray(), kthMin.substring(i, kthMin.length()).toCharArray());
                break;
            }
        }
        return result;
    }
    
    private int countSwap(char[] arr1, char[] arr2) {
        int count = 0;
        int p1 = 0;
        int p2 = 0;
        while(p2 < arr2.length) {
            while(arr1[p1] != arr2[p2]) {
                p1++;
            }
            while(p1 != p2) {
                swap(arr1, p1-1, p1);
                count++;
                p1 -= 1;
            }
            p2++;
            p1 = p2;
        }
        return count;
    }
    
    // https://leetcode.com/problems/next-permutation/
    private String findNextMin(String num) {
        char[] arr = num.toCharArray();
        int prev = -1;
        int i = arr.length -1;
        for (; i >=0; i--) {
            int cur = (int)(num.charAt(i) - '0');
            // look for descending pos
            if (cur < prev) { // found the pos i
                break;
            } else {
                prev = cur;
            }
        }
        // now look for minimum right hand side digit that is larger than arr[i]
        int j = i+1;
        int minIndex = j;
        for(; j < arr.length; j++) { // NOTE: arr[j] ~ arr[arr.length-1] is already descending order
            if (arr[j] > arr[i]) { 
                minIndex = j;
            }
        }
        // found minIndex, now we swap
        swap(arr, i, minIndex);
        
        // now we reverse i+1 ~ arr.length-1
        reverse(arr, i+1, arr.length-1);
        
        return new String(arr);
    }
    
    private void swap(char[] arr, int from, int to) {
        char tmp = arr[to];
        arr[to] = arr[from];
        arr[from] = tmp;
    }
    
    private void reverse(char[] arr, int from, int to) {
        if (from >= to) {
            return;
        }
        swap(arr, from, to);
        reverse(arr, from + 1, to-1);
    }
}