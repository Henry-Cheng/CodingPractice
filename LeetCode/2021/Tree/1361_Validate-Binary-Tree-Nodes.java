// https://leetcode.com/problems/validate-binary-tree-nodes/
class Solution {
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        // check if it is connected, and whether we have cycle
        // find the root and then start dfs
        boolean[] visitedNode = new boolean[n];
        for (int i = 0; i < leftChild.length; i++) {
            if (leftChild[i] != -1) {
                if (visitedNode[leftChild[i]]) { // already visited, has small cycle 
                    return false;
                } else {
                    visitedNode[leftChild[i]] = true;
                }
            }
            if (rightChild[i] != -1) {
                if (visitedNode[rightChild[i]]) { // already visited, has small cycle
                    return false;
                } else {
                    visitedNode[rightChild[i]] = true;
                }
            }
        }
        int root = -1;
        for (int i = 0; i < visitedNode.length; i++) {
            if (!visitedNode[i]) {
                if (root == -1) {
                    root = i;
                } else { // find multiple roots
                    return false;
                }
            }
        }
        if (root == -1) { // all nodes can be reached, it's a big cycle
            return false;
        }
        
        /**
        1 --> 0 --> 2   3
        <-----------
        **/
        //still need to run dfs in case it has cycle or not connected
        HashSet<Integer> visited = new HashSet<>();
        boolean result = dfs(root, leftChild, rightChild, visited);
        if (!result) { // has cycle
            return false;
        }
        if (visited.size() < n) {
            return false;
        }
        return true;
    }
    
    private boolean dfs(int root, int[] leftChild, int[] rightChild, HashSet<Integer> visited) {
        if (!visited.contains(root)) {
            visited.add(root);
            boolean result = true;
            if(leftChild[root] != -1) { // left child exists
                result = result && dfs(leftChild[root], leftChild, rightChild, visited);
            }
            if (rightChild[root] != -1) { // right child exists
                result = result && dfs(rightChild[root], leftChild, rightChild, visited);
            }
            return result;
        } else {
            return false;
        }
    }
}