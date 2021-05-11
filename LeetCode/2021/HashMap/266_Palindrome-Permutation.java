// https://leetcode.com/problems/palindrome-permutation/
class Solution {
    public boolean canPermutePalindrome(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) +1);
        }
        int oddCount = 0;
        for (Character key : map.keySet()) {
            if (map.get(key) % 2 != 0) {
                oddCount++;
                if (oddCount > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}