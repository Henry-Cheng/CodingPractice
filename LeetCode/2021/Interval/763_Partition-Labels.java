// https://leetcode.com/problems/partition-labels/
class Solution {
    public List<Integer> partitionLabels(String s) {
        /**
                 9
        ababcbacadefegdehijhklij
        a -> 0~8
        b -> 1~5
        c -> 4~7
        d -> 9~14
        e -> 10~15
        f -> 11
        g -> 13
        h -> 16~19
        i -> 17~22
        j -> 18~23
        k -> 20
        l -> 21
        **/
        // count and prepare intervals
        Integer[][] pos = new Integer[26][2];
        for (int i = 0; i < s.length(); i++) {
            int key = s.charAt(i) - 'a';
            if (pos[key][0] == null) {
                pos[key][0] = i;
            } else {
                pos[key][1] = i;
            }
        }
        // dump intervals into heap
        PriorityQueue<Integer[]> heap = new PriorityQueue<>((a,b)->{
            return a[0]-b[0];
        });
        for (int i = 0; i < pos.length; i++) {
            Integer[] pair = pos[i];
            if (pair[0] != null) {
                if (pair[1] == null) {
                    pair[1] = pair[0]; 
                }
                //System.out.println((char)(i+'a') + " is at " + pair[0] + "~" + pair[1]);
                heap.offer(pair);
            }
        }
        List<Integer> list = new LinkedList<>();
        Integer[] prev = heap.poll();
        while(!heap.isEmpty()) {
            Integer[] current = heap.poll();
            //System.out.println("prev is at " + prev[0] + "~" + prev[1]);
            //System.out.println("current is at " + current[0] + "~" + current[1]);
            if (current[0] > prev[1]) {
                list.add(prev[1] - prev[0] + 1);// record a paritioing pos
                prev = current;
            } else {
                prev[1] = Math.max(prev[1], current[1]); // expand the ending pos
            }
        }
        list.add(prev[1]-prev[0] + 1);
        return list;
    }
}