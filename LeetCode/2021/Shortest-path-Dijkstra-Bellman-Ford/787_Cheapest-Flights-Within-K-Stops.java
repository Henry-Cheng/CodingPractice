// https://leetcode.com/problems/cheapest-flights-within-k-stops/
// https://github.com/cherryljr/LeetCode/blob/master/Cheapest%20Flights%20Within%20K%20Stops.java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int j = 1; j <= K + 1; j++) {// relax K+1 times
            int[] backup = Arrays.copyOf(dist, n+1);
            for (int[] flight : flights) {
                int from = flight[0], to = flight[1], weight = flight[2];
                if (backup[from] != Integer.MAX_VALUE) {
                    // we update dist[to], but using the last round result (which is backup[from]), since we need to make sure only relax once at one stop, otherwise we may relax multiple times at this round and goes above j stops 
                    dist[to] = Math.min(dist[to], backup[from] + weight);
                }
            }
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}