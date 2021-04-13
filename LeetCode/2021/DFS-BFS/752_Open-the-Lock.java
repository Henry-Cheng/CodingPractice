// https://leetcode.com/problems/open-the-lock/
class Solution {
    public int openLock(String[] deadends, String target) {
        // finding shortest path is something like BFS problem
        // we can think that each node would have 8 positions to move to:
        //  -1  -1  -1  -1
        //  []  []  []  []
        //  +1  +1  +1  +1
        // and the deadends are like obstacles in graph
        Deque<String> queue = new LinkedList<>();
        HashSet<String> deadendsSet = new HashSet<>();
        for (String deadend : deadends) {
            deadendsSet.add(deadend);
        }
        
        String head = "0000"; // initially it is 0000
        if (deadendsSet.contains(head)) { // if 0000 itself is forbidden
            return -1;
        }
        HashSet<String> visited = new HashSet<>();
        queue.offer(head);
        visited.add(head);
        int[] moves = {1, -1};
        int level = 0;
        boolean reachTarget = false;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String node = queue.poll();
                //System.out.println("level " + level + ", node " + i + " is " + node);
                if (target.equals(node)) { // reach target
                    reachTarget = true;
                    break;
                }
                // search in 8 directions
                for (int j = 0; j < node.length(); j++) {
                    for (int k = 0; k < moves.length; k++) {
                        String newNode = moveNode(node, j, moves[k]);
                        if (!visited.contains(newNode) && !deadendsSet.contains(newNode)) {
                            if (newNode.equals(target)) {
                                //System.out.println("deadendsSet is " + deadendsSet + ", enqueue " + newNode);
                            }
                            queue.offer(newNode);
                            visited.add(newNode);
                        }
                    }
                }
            }
            if (reachTarget) {
                break;
            }
            level++;
        }
        return reachTarget ? level : -1;
    }

    private String moveNode(String node, int pos, int move) {
        //System.out.println("move node " + node + " at pos " + pos + " by " + move);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < node.length(); i++) {
            if (i != pos) {
                sb.append(node.charAt(i));
            } else {
                char c = node.charAt(i);
                int cNum = c - '0' + move;
                //System.out.println("cNum is " + cNum);
                if (cNum == -1) {
                    cNum = 9;
                } else if (cNum == 10) {
                    cNum = 0;
                }
                sb.append(cNum);
            }
        }
        String newNode = sb.toString();
        //System.out.println("newNode is " + newNode);
        return newNode;
    }
}