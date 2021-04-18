// https://leetcode.com/problems/longest-substring-without-repeating-characters/
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        HashMap<Character, Integer> charToPos = new HashMap<>();
        int l = 0;
        charToPos.put(s.charAt(l), l);
        int r = 1;
        int length = 1;
        while (r < s.length()) {
            char c = s.charAt(r);
            if (charToPos.containsKey(c)) {
                int foundPos = charToPos.get(c);
                // NOTE: no need to clean up hashmap, since next time we see existing char, the foundPos is either invlid (far away from l) or we just jump again to foundPos + 1
                while (l <= foundPos) {
                    charToPos.remove(s.charAt(l));
                    l++;
                }
                // now l = foundPos + 1;
            }
            length = Math.max(r - l + 1, length);
            charToPos.put(c, r);
            r++;
        }
        return length;
    }
}