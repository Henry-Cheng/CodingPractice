// https://leetcode.com/problems/find-k-closest-elements/
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> arrList = Arrays.stream(arr).boxed().collect(Collectors.toList());
        if (x <= arr[0]) {
            return arrList.subList(0, k);
        } else if (x >= arr[arr.length-1]) {
            return arrList.subList(arr.length-k, arr.length);
        }
        // do binrary search to find element that is closest to x
        int left = 0;
        int right = arr.length-1;
        while(left <= right) {
            //System.out.println("left is " + left + ", right is " + right);
            int mid = left + (right-left)/2;
            if(arr[mid] == x) {
                left = mid + 1;
            } else if (arr[mid] < x) {
                left = mid+1;
            } else if (arr[mid] > x) {
                right = mid - 1;
            }
        }
        // 1 2 3 4 6
        //       l
        //     r
        int tmp = left;
        left = right;
        right = tmp;
        
        //System.out.println("left is " + left + ", right is " + right);
        // now using two pointers to search in range [xCloesetPos-k, xCloesetPos+k]
        int found = 0;
        while(found < k) {
            if (left < 0)  {
                while(found < k) {
                    found++;
                    right++;
                }
                break;
            } else if (right >= arr.length) {
                while(found < k) {
                    found++;
                    left--;
                }
                break;
            } else {
                if (x - arr[left] <= arr[right] - x) {
                    found++;
                    left--;
                } else if (x - arr[left] > arr[right] - x) {
                    found++;
                    right++;
                } 
            }
        }
        //System.out.println("finally left is " + left + ", right is " + right);
        return arrList.subList(left+1, right);
    }
}