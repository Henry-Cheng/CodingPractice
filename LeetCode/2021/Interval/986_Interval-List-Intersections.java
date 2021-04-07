// https://leetcode.com/problems/interval-list-intersections/submissions/
class Solution {
    // NOTE: only need to compare p1 and p2, no need to compare with last element in result list -- they would never have overlap since we always move the pointer of smaller interval
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if (firstList.length == 0 || secondList.length == 0) {
            return new int[0][0];
        }
        List<int[]> result = new LinkedList<>();
        int p1 = 0;
        int p2 = 0;
        while(p1 < firstList.length && p2 < secondList.length) {
            int low = Math.max(firstList[p1][0], secondList[p2][0]);
            int high = Math.min(firstList[p1][1], secondList[p2][1]);
            if (low <= high) {
                int[] intersection = new int[2];
                intersection[0] = low;
                intersection[1] = high;
                result.add(intersection);
            }
            if (firstList[p1][1] <= secondList[p2][1]) {
                p1++;
            } else {
                p2++;
            }
        }
        // convert result list to array
        int[][] resultArr = new int[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            resultArr[i] = result.get(i);
        }
        return resultArr;
    }

//     public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
//         if (firstList.length == 0 || secondList.length == 0) {
//             return new int[0][0];
//         }
//         List<int[]> result = new LinkedList<>();
//         int p1 = 0;
//         int p2 = 0;
//         while(p1 < firstList.length || p2 < secondList.length) {
//             if (p1 == firstList.length) {
//                 addIntersection(secondList[p2], result, false);
//                 break;
//             } else if (p2 == secondList.length) {
//                 addIntersection(firstList[p1], result, false);
//                 break;  
//             }
//             // scenario1:
//             // 1 6  9 10
//             // 2 3  4 5   6 7  8 9

//             // scenario2:
//             // 13  46
//             //   45  78
//             int[] intersection = getIntersection(firstList[p1], secondList[p2]);
//             if (intersection != null) {
//                 if (result.size() == 0) {
//                     result.add(intersection);
//                 } else {
//                     addIntersection(intersection, result, true);
//                 }
//             }
//             // move smaller interval pointer
//             if (firstList[p1][1] <= secondList[p2][1]) {
//                 p1++;
//             } else {
//                 p2++;
//             }
//         }
        
//         // convert result to array
//         int[][] resultArr = new int[result.size()][2];
//         for (int i = 0; i < result.size(); i++) {
//             resultArr[i] = result.get(i);
//         }
//         return resultArr;
//     }
    
//     private void addIntersection(int[] interval, List<int[]> result, boolean inclusive) {
//         int[] newIntersection = getIntersection(result.get(result.size()-1), interval);
//         if (newIntersection != null) {
//             result.remove(result.size() - 1);
//             result.add(newIntersection);  
//         } else {
//             if (inclusive) {
//                 result.add(interval);
//             }
//         }
//     }
//     /**
//         3 5   a 
//      12       b
//      1   4
//          45
//          4  6
//             67
//     **/
//     private int[] getIntersection(int[] a, int[] b) {
//         int[] intersection = new int[2];
//         if (isIntersection(a, b[0]) || isIntersection(a, b[1]) ||isIntersection(b, a[0]) ||isIntersection(b, a[1])) {
//             intersection[0] = Math.max(a[0], b[0]);
//             intersection[1] = Math.min(a[1],b[1]);
//             return intersection;
//         }
//         return null;
//     }
    
//     private boolean isIntersection(int[] a, int num) {
//         if (num >= a[0] && num <= a[1]) {
//             return true;
//         }
//         return false;
//     }
}