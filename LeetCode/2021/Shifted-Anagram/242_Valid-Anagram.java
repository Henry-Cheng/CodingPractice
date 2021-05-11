// https://leetcode.com/problems/valid-anagram/
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] sArr = new int[26];
        int[] tArr = new int[26];
        for (int i = 0; i < s.length() ; i++) {
            sArr[s.charAt(i) - 'a']++;
            tArr[t.charAt(i) - 'a']++;
        }
        return Arrays.equals(sArr, tArr);
    }
}