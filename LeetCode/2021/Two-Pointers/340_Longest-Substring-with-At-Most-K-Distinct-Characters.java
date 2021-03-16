// https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

class Solution {
    public int maxDist = Integer.MIN_VALUE;
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // e c e b a --> k ==2
        // e --> 1 <= 2, record 1, expand
        // e c --> 2 <= 2, record 2, expand
        // e c e --> 2 <= 2, record 3, expand
        // e c e b --> 3 > 2, shrink
        //   c e b --> 3 > 2, shrink
        //     e b --> 2 <=2, record 2, expand
        //     e b a --> 3 >= 2 shrink
        //       b a --> 2 <= 2, record 2, end
        
        // base case
        if (k == 0) {
            return 0;
        }
        // aba
        int left = 0;
        int right = left;
        Map<Character, Integer> countMap = new HashMap<>();
        countMap.put(s.charAt(left), 1);
        while(left <= right && right < s.length()) {// a
            // count distinct char in substring
            int distinctNum = countMap.keySet().size();//a-1,b-1
            // if distinct num <= k, move right
            if (distinctNum <= k) {
                maxDist = Math.max(maxDist, right - left + 1);//0-0+1=1
                right++; 
                if (right < s.length()) {
                    char c = s.charAt(right);
                    Integer count = countMap.get(c);
                    if (count == null) {
                        countMap.put(c, 1);//a-1,b-1
                    } else {
                        countMap.put(c, count + 1);
                    } 
                }
            } else { // else, move left
                if (left < s.length()) {
                    char c = s.charAt(left);
                    Integer count = countMap.get(c);
                    if (count - 1 == 0) {
                        countMap.remove(c);
                    } else {
                        countMap.put(c, count - 1);
                    } 
                }
                left++;
            }
        }
        return maxDist;
    }
}

// LC solution with sliding window and hashmap
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int n = s.length();
        if (n * k == 0) {
            return 0;
        }
        int left = 0;
        int right = 0;

        Map<Character, Integer> rightmostPosition = new HashMap<>();

        int maxLength = 1;

        while (right < n) {
            rightmostPosition.put(s.charAt(right), right++);

            if (rightmostPosition.size() == k + 1) {
                int lowestIndex = Collections.min(rightmostPosition.values());
                rightmostPosition.remove(s.charAt(lowestIndex));
                left = lowestIndex + 1;
            }

            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }
}

// LC solution with sliding window and LinkedHashMap (ordered dictionary)
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int n = s.length();
        if (n * k == 0) {
            return 0;
        }
        int left = 0;
        int right = 0;

        Map<Character, Integer> rightmostPosition = new LinkedHashMap<>();

        int maxLength = 1;

        while (right < n) {
            Character character = s.charAt(right);
            if (rightmostPosition.containsKey(character)) {
                rightmostPosition.remove(character);
            }
            rightmostPosition.put(character, right++);

            if (rightmostPosition.size() == k + 1) {
                Map.Entry<Character, Integer> leftmost =
                    rightmostPosition.entrySet().iterator().next();
                rightmostPosition.remove(leftmost.getKey());
                left = leftmost.getValue() + 1;
            }

            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }
}