// https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/submissions/
class Solution {
    public int shortestSubarray(int[] A, int K) {
        // prepare prefix-sum array
        // NOTE: using long here since Integer.MAX_VALUE is 2.1*10^9, but A[i] * A.length < 5*10^9
        long[] prefixSum = new long[A.length + 1];
        prefixSum[0] = 0; // set it to be 0, which means sum before index 0 is 0
        for (int i = 0; i < A.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + A[i];
        }
        int minRange = Integer.MAX_VALUE;
        Deque<Integer> queue = new LinkedList<>();
        
        // find smallest j-i that makes prefixSum[j]-prefixSum[i] >= k
        for (int  = 0; j < prefixSum.length; j++) {
            while(!queue.isEmpty() && prefixSum[j] < prefixSum[queue.getLast()]) {
                // it means the sum from j to queue.getLast() is negative
                // no need to consider queue.getLast() any more
                queue.removeLast();
            }
            while(!queue.isEmpty() && prefixSum[j] - prefixSum[queue.getFirst()] >= K) {
                // it means prefixSum.getFirst() --> j is valid
                // now we can remove prefixSum.getFirst() to try a shorter range
                minRange = Math.min(minRange, j - queue.getFirst());
                queue.removeFirst();
            }
            queue.addLast(j);
        }
        
        return minRange == Integer.MAX_VALUE ? -1 : minRange;
    }
}

// LC solution:
public class LCSolution {
    public int shortestSubarray(int[] A, int K) {
        int N = A.length;
        long[] P = new long[N+1];
        for (int i = 0; i < N; ++i)
            P[i+1] = P[i] + (long) A[i];

        // Want smallest y-x with P[y] - P[x] >= K
        int ans = N+1; // N+1 is impossible
        Deque<Integer> monoq = new LinkedList(); //opt(y) candidates, as indices of P

        for (int y = 0; y < P.length; ++y) {
            // Want opt(y) = largest x with P[x] <= P[y] - K;
            while (!monoq.isEmpty() && P[y] <= P[monoq.getLast()])
                monoq.removeLast();
            while (!monoq.isEmpty() && P[y] >= P[monoq.getFirst()] + K)
                ans = Math.min(ans, y - monoq.removeFirst());

            monoq.addLast(y);
        }

        return ans < N+1 ? ans : -1;
    }
}