// https://leetcode.com/problems/task-scheduler/
class Solution {
    public int leastInterval(char[] tasks, int n) {
        /**
        only consider the char with max frequence:
        A4 B4 C3 D3 E2
        n=3
        A _ _ _ A _ _ _ A _ _ _ A 
        we have possible max (4 - 1) * 3 = 9 idle slots, we need to fill the slots by other char
        check B: B can contribute 3 to fill slots (B has one more left, but that can be executed after last A, so no worry)
        check C: C can contribute 3
        check D: D can contribute 3
        check E: E can contirbute 2
        so in total we have 9 - 3 - 3 - 3 - 2 = -2 idel slots left, it means we can fill all idle, and the rest chars can be executed after last A by one permutation
        the total unit of time we need would be max(0, -2) + tasks.length = 16
        **/
        // corner case
        if (n == 0) {
            return tasks.length;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : tasks) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        List<Map.Entry<Character, Integer>> list = map.entrySet().stream().collect(Collectors.toList());
        // sort list
        Collections.sort(list, (a,b) -> {
            return b.getValue() - a.getValue(); // sort descending
        });
        // check how many idle slots we have
        int maxFrequence = list.get(0).getValue();
        int totalIdelSlots = (maxFrequence - 1) * n;
        // check how can we fill idle slots by non-max-freq-char
        for (int i = 1; i < list.size(); i++) {
            Map.Entry<Character, Integer> element = list.get(i);
            totalIdelSlots -= Math.min(element.getValue(), maxFrequence - 1);
        }
        // NOTE: totalIdelSlots could < 0
        int totalSlots = tasks.length + Math.max(0, totalIdelSlots);
        return totalSlots;
    }
}