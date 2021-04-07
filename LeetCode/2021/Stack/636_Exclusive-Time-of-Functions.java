// https://leetcode.com/problems/exclusive-time-of-functions/
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        // similar like valid parentheses
        // the task started latter would end earlier
        // the task pushed latter would be poped earlier
        /** 
             ________________
            |   ___________  |
            |  |   _    _  | |
            |  |  | |  | | | |
            0  1  3 4  5 6 7 8
        **/
        int[] result = new int[n];
        Deque<List<Integer>> stack = new LinkedList<>(); // list(0) is id, list(1) is start time, list(2) is the durationToBeRemoved
        for (int i = 0; i < logs.size(); i++) { 
            String[] info = logs.get(i).split(":");
            int id = Integer.valueOf(info[0]);
            String action = info[1];
            int timestamp = Integer.valueOf(info[2]);
            
            if (action.equals("start")) {
                List<Integer> process = new LinkedList<>();
                process.add(id);
                process.add(timestamp);
                process.add(0); // initially durationToBeRemoved is 0
                stack.push(process);
            } else {
                List<Integer> previousRecord = stack.pop();
                int previousId = previousRecord.get(0); // must be the same as id
                int previousTimestamp = previousRecord.get(1);
                int durationToBeRemoved = previousRecord.get(2);
                
                int duration = timestamp - previousTimestamp + 1;
                result[id] += duration - durationToBeRemoved;
                
                // increment the durationToBeRemoved of the peek process in stack
                if (!stack.isEmpty()) {
                    List<Integer> peekProcess = stack.pop();
                    peekProcess.set(2, peekProcess.get(2) + duration);
                    stack.push(peekProcess);
                }
                
            }
        }
        return result;
    }
}