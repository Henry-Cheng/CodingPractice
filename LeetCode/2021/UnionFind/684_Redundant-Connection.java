// https://leetcode.com/problems/redundant-connection/
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        if (edges == null || edges.length == 0) {
            return new int[0];
        }
        DJS djs = new DJS(edges.length + 1); // at most edges.length nodes in graph, due to 0-index we need to set edges.length+1
        int maxIndexOfEdge = Integer.MIN_VALUE;
        for (int i = 0; i < edges.length; i++) {
            boolean alreadyUnioned = djs.union(edges[i][0], edges[i][1]);
            if (alreadyUnioned) {
                maxIndexOfEdge = Math.max(maxIndexOfEdge, i);
            }
        }
        if (maxIndexOfEdge == Integer.MIN_VALUE) {
            return new int[0];
        } else {
            return edges[maxIndexOfEdge];
        }
    }
    
    private static class DJS { // disjoint-set
        int[] parent;
        int[] rank; // relative height of the parent node
        public int disjointCount = 0;
        public DJS(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i; // initialize parent of each node to be itself
            }
            rank = new int[size]; // initialize rank array
            disjointCount = size; // initialize disjointCount to be size
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]); // path compression 
            }
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            //System.out.println(x + "-->" + parentX + ", " + y + "-->" + parentY);
            if (parentX == parentY) { 
                return true; // already unioned
            } else {
                if (rank[parentX] < rank[parentY]) { // union x to y
                    //System.out.println("x<y, parent[" + x + "] = " + parentY);
                    parent[parentX] = parentY;
                } else if (rank[parentX] > rank[parentY]) { // union y to x
                    //System.out.println("x>y, parent[" + y + "] = " + parentX);
                    parent[parentY] = parentX;
                } else {
                    //System.out.println("equals, parent[" + y + "] = " + parentX);
                    parent[parentY] = parentX; // can choose either x or y here
                    rank[parentX]++; // since we choose x as parent, increment x
                    //System.out.println("rank[" + parentX + "] = " + rank[parentX]);
                }
                disjointCount--;
                return false; // new union is built
            }
        }
    }
}