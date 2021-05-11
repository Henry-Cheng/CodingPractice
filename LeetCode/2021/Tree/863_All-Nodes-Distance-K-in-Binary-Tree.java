// https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        // the idea is to build it like a graph, then we can using BFS to find the K-dist node
        List<Integer> result = new LinkedList<>();
        if (K == 0) {
            result.add(target.val);
            return result;
        }
        HashMap<TreeNode, List<TreeNode>> graph = new HashMap<>();
        dfs(root, null, graph); // build graph
        //printGraph(graph);
        bfs(graph, target, K, result); // find K-dist
        return result;
    }
    
    private void dfs(TreeNode root, TreeNode parent, HashMap<TreeNode, List<TreeNode>> graph) {
        if (root == null) {
            return;
        }
        List<TreeNode> list = graph.getOrDefault(root, new LinkedList<>());
        if (root.left != null) {
            list.add(root.left);
            dfs(root.left, root, graph);
        }
        if (root.right != null) {
            list.add(root.right);
            dfs(root.right, root, graph);
        }
        if (parent != null) {
            list.add(parent);
        }
        graph.put(root, list);
    }
    
    private void bfs(HashMap<TreeNode, List<TreeNode>> graph, TreeNode target, int K, List<Integer> result) {
        HashSet<TreeNode> visited = new HashSet<>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(target);
        visited.add(target);
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                //System.out.println("at level " + level + ", see node " + node.val);
                if (level == K) {
                    result.add(node.val);
                }
                // visit all neightbors of node
                for (TreeNode neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
            if (level == K) {
                break;
            }
            level++;
        }
    }
    
    private void printGraph(HashMap<TreeNode, List<TreeNode>> graph) {
        for (TreeNode node : graph.keySet()) {
            System.out.println("root: " + node.val);
            for (TreeNode neighbor : graph.get(node)) {
                System.out.print("-->" + neighbor.val);
            }
            System.out.println();
        }
    }
}