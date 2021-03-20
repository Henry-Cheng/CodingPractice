// https://leetcode.com/problems/course-schedule-ii/
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // corner case
        if (prerequisites == null || prerequisites.length == 0) {
            int[] result = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                result[i] = i;
            }
            return result;
        }
        // using topological sort
        // 1. prepare ingress count map and egree dependency map
        Map<Integer, Integer> ingressMap = new HashMap<>();
        Map<Integer, List<Integer>> egressMap = new HashMap<>();
        for (int[] arc : prerequisites) {
            int to = arc[0];//0//1
            int from = arc[1];//1//0
            // put ingressMap
            Integer count = ingressMap.getOrDefault(to, 0);
            ingressMap.put(to, count + 1);//0->1//1->0
            // put egressMap
            List<Integer> egressArcs = egressMap.getOrDefault(from, new LinkedList<>());
            egressArcs.add(to);
            egressMap.put(from, egressArcs);//1->0//0->1
        }
        // 2. find out all 0-ingress node (if it's not in ingress map)
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!ingressMap.containsKey(i)) {
                queue.offer(i);
            }
        }
        // 3. check each 0-ingress node in queue, prune its dependencies' ingress, if found new 0-ingress node then inqueue
        List<Integer> resultList = new LinkedList<>();
        while(!queue.isEmpty()) {
            // visit dequeued node
            Integer node = queue.poll();
            resultList.add(node);
            // prune ingress of dependency
            List<Integer> dependencies = egressMap.get(node);
            if (dependencies != null) { // counld happen for node without any relationship
                for (Integer dependency : dependencies) {
                    Integer ingressVal = ingressMap.get(dependency);
                    if (ingressVal != null) { // safeguard code, should never be null
                        ingressMap.put(dependency, ingressVal - 1);
                        if (ingressVal - 1 == 0) { // found a new 0-ingress node
                            queue.offer(dependency);
                        }
                    }
                }
            }
        }
        // need to return empty array if not all courses can be completed, cannot return partially working courses
        int[] emptyResult = new int[0];
        return resultList.size() >= numCourses ? resultList.stream().mapToInt(i->i).toArray() : emptyResult;
    }
}