// https://leetcode.com/problems/number-of-provinces/
class Solution {
    public int findCircleNum(int[][] isConnected) {
        /**     
                 0 1 2 3
                 
             0   1 1 0 0
             1   1 1 0 1
             2   0 0 1 0
             3   0 1 0 1
             
             for above graph there are 2 province
             the search space can be reduced to be:
                 0 1 2 3               
             0   1 1 0 0
             1     1 0 1
             2       1 0
             3         1
        **/
        
    
        // corner case
        if (isConnected.length == 1) {
            return 1;
        }
        // maintain list of hashset
        int count = 0;
        HashSet<Integer> visited = new HashSet<>();
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited.contains(i)) {
                HashSet<Integer> path = new HashSet<>();
                dfs(isConnected, i, path);
                if (path.size() > 0) {
                    visited.addAll(path);
                    count++;
                }
            }
        }
        return count;
    }
    public void dfs(int[][] isConnected, int i, HashSet<Integer> path) {
        for (int j = 0; j < isConnected.length; j++) {
            if (!path.contains(j)) {
                // visite i,j
                if (isConnected[i][j] == 1) {
                    path.add(j);
                    dfs(isConnected, j, path);
                }
            }
        }
    }
 
    // LC solution for union-find
    // Time complexity : O(n^3). We traverse over the complete matrix once. Union and find operations take O(n) time in the worst case.
    // Space complexity : O(n). parent array of size n is used.
    int find(int parent[], int i) {
        if (parent[i] == -1)
            return i;
        return find(parent, parent[i]);
    }

    void union(int parent[], int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        if (xset != yset)
            parent[xset] = yset;
    }
    public int findCircleNumLC(int[][] M) {
        int[] parent = new int[M.length];
        Arrays.fill(parent, -1);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if (M[i][j] == 1 && i != j) {
                    union(parent, i, j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1)
                count++;
        }
        return count;
    }
}