// https://leetcode.com/problems/k-closest-points-to-origin/submissions/
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a,b)->{
            int distA = a[0] * a[0] + a[1] * a[1];
            int distB = b[0] * b[0] + b[1] * b[1];
            return distB-distA; // descending, max-heap
        });
        for (int[] point : points) {
            heap.offer(point);
            if (heap.size() > k) { // maintain k elements in heap
                heap.poll();
            }
        }
        //System.out.println(heap.size());
        int[][] result = new int[k][2];
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            int[] point = heap.poll();
            //System.out.println("i is " + i + ", point: "+ point[0] + " " + point[1]);
            result[i] = point;
        }
        return result;
    }
}