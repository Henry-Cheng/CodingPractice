// https://leetcode.com/problems/verifying-an-alien-dictionary/
class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        if (words.length == 1) {
            return true;
        }
        // make order a dict by hashmap 
        HashMap<Character, Integer> dict = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            dict.put(order.charAt(i), i);
        }
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int pos = 0;
            while (pos < word1.length() && pos < word2.length()) {
                int index1 = dict.get(word1.charAt(pos));
                int index2 = dict.get(word2.charAt(pos));
                if (index1 < index2) {
                    break;
                } else if (index1 > index2) {
                    return false;
                } else { // index1 == index2
                    // continue
                }
                pos++;
            }
            // check if word1 or word2 has leftover characters
            if (pos == word2.length() && pos < word1.length()) { //e.g. apple, app
                return false;
            }
        }
        return true;
    }
}