// https://leetcode.com/problems/assign-cookies/
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        // 1 2 3
        // 1 1
        if (s.length == 0) {
            return 0;
        }
        g = Arrays.stream(g).boxed().sorted((a, b) -> {return b-a;}).mapToInt(i -> i).toArray();
        s = Arrays.stream(s).boxed().sorted((a, b) -> {return b-a;}).mapToInt(i -> i).toArray();
        int i = 0;
        int j = 0;
        int contentCount = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                contentCount++;
                i++;
                j++;
            } else {
                i++;
            }
        }
        return contentCount;
    }
}