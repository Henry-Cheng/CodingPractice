// https://leetcode.com/problems/clone-graph/
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/
// time complexity is O(M + N), space complexity is O(N)
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        //return BFS(node);
        DFS(node);
        return visited.get(node);
        
// the followings are my solution with two queues (one for original graph and one for clone graph) and a hashmap to store clone nodes        
//         Deque<Node> queue = new LinkedList<>();
//         queue.offer(node); // queue 2
        
//         Deque<Node> cloneQueue = new LinkedList<>();
//         Node cloneNode = new Node(node.val);
//         cloneQueue.offer(cloneNode);
        
//         // 1 --> 2, 4
//         // 2 --> 1, 3
//         // 3 --> 2, 4
//         // 4 --> 1 ,3
//         Set<Integer> visitedNode = new HashSet<>();
//         Map<Integer, Node> cloneNodeMap = new HashMap<>();
//         // store initial node to map
//         cloneNodeMap.put(cloneNode.val, cloneNode);
//         // store initial visited
//         visitedNode.add(cloneNode.val); // already visited, record it!!!
//         //      queue      = 1,-1,2,4,-2,3,-4,-3
//         // cloneQueue      = 1,-1,2,4,-2,3,-4,-3
//         // cloneNodeMap    = 1,2,4,3
//         //  visitedNode    = 1,2,3,4
//         while(!queue.isEmpty()) { 
//             Node currentNode = queue.poll();//1,2,4,3
//             Node currentCloneNode = cloneQueue.poll();//1,2,4,3
//             for (Node neighbor: currentNode.neighbors) {//2,4//1,3//1,3
//                 Node cloneNeighbor = cloneNodeMap.get(neighbor.val);
//                 if (cloneNeighbor == null) {
//                     cloneNeighbor = new Node(neighbor.val);
//                     cloneNodeMap.put(neighbor.val, cloneNeighbor);
//                 }
//                 currentCloneNode.neighbors.add(cloneNeighbor);//1->2,1->4,2->1,2->3,4->1,4->3,
//                 if (!visitedNode.contains(neighbor.val)) {
//                     queue.offer(neighbor);
//                     cloneQueue.offer(cloneNeighbor);
//                     visitedNode.add(neighbor.val); // alrady pushed to queue, record it!!!
//                 }
//             }
//         }
//         return cloneNode;
    }
    
    // LC solution for DFS
    // 1 --> 2, 4
    // 2 --> 1, 3
    // 3 --> 2, 4
    // 4 --> 1 ,3
    // DFS: 1,2,3,4
    // visited: 1+new,2+new,3+new,4+new,
    // connect: 2->1,3->2,4->1,4->3,3->4,2->3,1->2,1->4
    private HashMap <Node, Node> visited = new HashMap<>();
    public void DFS(Node node) {
        if (visited.containsKey(node)) {
            return;
        }
        // visit node
        visited.put(node, new Node(node.val));
        // traverse all neightbors
        for (Node neighbor : node.neighbors) {//1-->4
            DFS(neighbor);
            visited.get(node).neighbors.add(visited.get(neighbor));
        }
    }
    
    // LC solution for BFS
    public Node BFS(Node node) {
        if (node == null) {
            return node;
        }

        // Put the first node in the queue
        Deque<Node> queue = new LinkedList<Node>();
        queue.offer(node);

        // visit the fist node
        Map<Node,Node> visited = new HashMap<>();
        visited.put(node, new Node(node.val)); // initial visited node, record it!!!

        // Start BFS traversal
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            // do the processing here!!!!!
            for (Node neighbor: n.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    visited.put(neighbor, new Node(neighbor.val)); // already pushed to queue, record visited!!!
                }
                // add neighbor clone node to the current clone node
                // the neighbor clone node could be just created in above 2 lines
                visited.get(n).neighbors.add(visited.get(neighbor)); 
            }
        }
        return visited.get(node);
    }
   
}

