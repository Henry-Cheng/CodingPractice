// https://leetcode.com/problems/course-schedule-ii/
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        HashMap<Integer, Integer> ingressMap = new HashMap<>();
        HashMap<Integer, List<Integer>> egressMap = new HashMap<>();
        // 1. build ingress map
        for (int i = 0 ; i < numCourses; i++) {
            ingressMap.put(i, 0);
        }
        // 2. build egress map
        for(int i = 0; i < prerequisites.length; i++) {
            List<Integer> egressList = egressMap.getOrDefault(prerequisites[i][1], new LinkedList<>());
            egressList.add(prerequisites[i][0]);
            egressMap.put(prerequisites[i][1], egressList);
            // also set ingress map
            ingressMap.put(prerequisites[i][0], ingressMap.get(prerequisites[i][0]) + 1);
        }
        // 2. topo sort
        List<Integer> result = new LinkedList<>();
        Deque<Integer> queue = new LinkedList<>();
        // initially put all 0-ingress node as entry points
        for (Map.Entry<Integer,Integer> entry : ingressMap.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        // now traverse until queue empty
        while(!queue.isEmpty()) {
            Integer node = queue.poll();
            result.add(node);
            // check in egress map
            if (egressMap.containsKey(node)) {
                for (Integer next : egressMap.get(node)) {
                    // decrement in ingress map
                    int count = ingressMap.get(next) - 1;
                    ingressMap.put(next, count);
                    if (count == 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        if (result.size() < numCourses) {
            return new int[0];
        } else {
            return result.stream().mapToInt(i -> i).toArray();
        }
    }
}