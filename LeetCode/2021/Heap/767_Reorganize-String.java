// https://leetcode.com/problems/reorganize-string/
class Solution {
    public String reorganizeString(String S) {
        // aaaccc --> acacac
        // aaacccc --> cacacac
        // aaaccccc --> ""
        // find most common letter, then split it by rest of the letters
        // if difference >= 2, not possible
        
        // option1: count all unique letters (O(N)), sort them (O(MlogM) if M is the # of unique letters) , then asembling string, total time complexity is O(N + MlogM)
        // option2: count and sort all unique letters by TreeMap (O(NlogM)), then asembling string
        // option3: count all unique letters (O(N)), building max-heap to maintain the max at heap top (O(MlogM)); when asembling string, we will do N times pulling from heap, and each pulling takes O(logM) for the heap to reorganize, so the asembling time is O(NlogM).
        // option4: bucket sort, maintain a 500 list of characters
        // since we would have most have 26 characters, so the M could be fixed and at most 26, which makes all the options close to O(N)
        
        // use map to count
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            Integer count = map.getOrDefault(S.charAt(i), 0);
            map.put(S.charAt(i), ++count);
        }
        // define heap to store
        PriorityQueue<Map.Entry<Character,Integer>> pq = new PriorityQueue<>((a,b) -> {
            return b.getValue() - a.getValue(); // descending
        });
        // move all map entries into pq to build max heap
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            // optimization: fail fast
            if (entry.getValue() * 2 - S.length() >= 2 ) {
                return "";
            }
            pq.offer(entry);
        }
        // now re-asmebling string
        StringBuilder sb = new StringBuilder();
        while(pq.size() >= 2) {
            Map.Entry<Character, Integer> max = pq.poll();
            Map.Entry<Character, Integer> maxSecond = pq.poll();
            while(maxSecond.getValue() > 0) {
                sb.append(max.getKey());
                max.setValue(max.getValue() - 1);
                sb.append(maxSecond.getKey());
                maxSecond.setValue(maxSecond.getValue() - 1);
            }
            if (max.getValue() > 0) {
                pq.offer(max);
            }
        }
        // now pq.size() == 1 or 0
        if (pq.size() > 0) {
            Map.Entry<Character, Integer> lastOne = pq.poll();
            for (int i = 0; i < lastOne.getValue(); i++) {// lastOne must have only one character instead of multiple
                sb.append(lastOne.getKey()); 
            }  
        }
        return sb.toString();
    }
}