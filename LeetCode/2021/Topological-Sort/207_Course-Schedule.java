// https://leetcode.com/problems/course-schedule/
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // numCourses = 4
        // 0 1 2 3
        // prerequisites
        // 0 <-- 1
        // 2 <-- 0
        // 3 <-- 1
        // 3 <-- 0
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        
        int arcNum = prerequisites.length;
        // topological sort
        // 1. prepare two hashmap to record ingress and egress
        Map<Integer, Integer> ingressCount = new HashMap<>();
        Map<Integer, List<Integer>> egressArcs = new HashMap<>();
        for (int i = 0; i < arcNum; i++) { // O(N+V)
            int from = prerequisites[i][1];
            int to = prerequisites[i][0];
            // count ingress number
            Integer count = ingressCount.getOrDefault(to, 0);
            ingressCount.put(to, count + 1);
            
            // record egress arcs
            List<Integer> arcs = egressArcs.getOrDefault(from, new LinkedList<>());
            arcs.add(to);
            egressArcs.put(from, arcs);
        }
        // 2. traverse all the nodes again for enqueue all 0-ingress-nodes
        // NOTE: it could be nodes that has no relatioship at all, or nodes in arcs but with 0 ingress
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            Integer count = ingressCount.get(i);
            if (count == null || count == 0) {
                queue.offer(i);
            }
        }
        // 3. while queue is not empty, output node in queue, prune ingress for dependency nodes and continue inqueue for 0-ingress-node
        List<Integer> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            // poll queue
            Integer node = queue.poll(); 
            // output node
            result.add(node); 
            // prune ingress for dependency node
            List<Integer> dependencies = egressArcs.get(node);
            if (dependencies != null) {
                for (Integer dependency : dependencies) {
                    // prune the ingress in ingressCount
                    Integer ingressVal = ingressCount.get(dependency);
                    if (ingressVal != null) { // actually it should never be null
                        ingressCount.put(dependency, ingressVal - 1);
                        if (ingressVal - 1 == 0) {
                            queue.offer(dependency);
                        }
                    }
                }
            }
        }
        return result.size() >= numCourses;
        
    }
}