// https://leetcode.com/problems/frog-jump/
class Solution {
    public boolean canCross(int[] stones) {
        /**
        if we have 7 stones in total, then frog can only make up to 7 steps
            1 2 3 4 5 6 7 steps
 stone  1   1
        3     2
        5     2
        6   1
        8     2 3
        12        4
        17          5
        **/
        // define a two-dimensional boolean array
        int n = stones.length;
        // NOTE: first jump is 1, does not mean first stone is also 1 distance
        if (n == 2 && stones[1] == 1) {
            return true;
        }
        if (stones[1] != 1) { // even the first step would fail
            return false;
        } 
        
        boolean[][] hit = new boolean[n][n];
        hit[1][1] = true;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < i + 1; j++) {
                if (hit[i][j]) {
                    // used j steps to reach i,j, next step could be j-1, j, j+1
                    // traverse the following stones
                    for (int k = i + 1; k < n; k++) {
                        // try j - 1 step
                        int dist = stones[k] - stones[i];
                        if (j - 1 > 0 && dist == j - 1) {
                            hit[k][j-1] = true;
                            if (k == n - 1) {
                                return true;
                            }
                        }
                        if (dist == j) {
                            hit[k][j] = true;
                            if (k == n - 1) {
                                return true;
                            }
                        }
                        if (dist == j + 1) {
                            hit[k][j+1] = true;
                            if (k == n - 1) {
                                return true;
                            }
                        }
                        if (dist > j + 1) { // no need to continue
                            break;
                        }
                    }
                }
            }
        }
        for (int j = 0; j < n; j++) { // this is for special use case [0,1]
            if (hit[n-1][j]) {
                return true;
            }
        }
        return false;
        
    }
    
    // LC solutin
    public boolean canCrossLC(int[] stones) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<Integer>());
        }
        map.get(0).add(0);
        for (int i = 0; i < stones.length; i++) {
            for (int k : map.get(stones[i])) {
                for (int step = k - 1; step <= k + 1; step++) {
                    if (step > 0 && map.containsKey(stones[i] + step)) {
                        map.get(stones[i] + step).add(step);
                    }
                }
            }
        }
        return map.get(stones[stones.length - 1]).size() > 0;
    }
}