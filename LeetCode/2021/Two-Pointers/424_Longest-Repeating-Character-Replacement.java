// https://leetcode.com/problems/longest-repeating-character-replacement/
class Solution {
    public int characterReplacement(String s, int k) {
        /**
        it equals to: find longest substring that has only k different char
             e c e b a --> k ==2
             e         --> 0 <= 2, record 1, expand
             e c       --> 1 <= 2, record 2, expand
             e c e     --> 1 <= 2, record 3, expand
             e c e b   --> 2 <= 2, record 4, expand
             e c e b a --> 3 > 2, shrink
               c e b a --> 4 > 2, shrink
                 e b a --> 3 > 2, shrink
                   b a --> 2 <= 2 record 2, cannot expand, end
        **/
        if (s.length() == 0 || s.length() == 1) {
            return s.length();
        }
        char[] array = s.toCharArray();
        int left = 0;
        int right = 1;
        int result = 1;
        HashMap<Character, Integer> countMap = new HashMap<>();
        // initialization
        int mostCountSoFar = incrementCountMap(countMap, array[left]);
        mostCountSoFar = Math.max(incrementCountMap(countMap, array[right]), mostCountSoFar);
        while(right < s.length()) {// O(N), worst case is 2*N
            // check diff
            int diff = right - left + 1 - mostCountSoFar;
            //System.out.println("substring is " + s.substring(left, right + 1) + ", mostCountSoFar is " + mostCountSoFar + ", diff is " + diff + ", size is " + (right - left + 1));
            if (diff <= k) { // expand
                //System.out.println("size " + (right - left + 1) + " is valid");
                result = Math.max(result, right - left + 1);
                right++;
                if (right < s.length()) {
                    mostCountSoFar = Math.max(incrementCountMap(countMap, array[right]), mostCountSoFar);
                }
            } else { // shrink
                // NOTE!!!
                // no need to update mostCountSoFar when decrementing, the reason is that mostCountSoFar recrods the most char count in the history, unless we find a bigger window in the future and increase the mostCountSoFar, we cannot do better that current max result which is based on mostCountSoFar
                decrementCountMap(countMap, array[left]);  
                left++;
            }
            //System.out.println("Now A is " + countMap.get('A') + ", B is " + countMap.get('B'));
        }
        return result;
    }
    
    private int incrementCountMap(HashMap<Character, Integer> countMap, char c) {
        Integer count = countMap.getOrDefault(c, 0);
        count = count + 1;
        countMap.put(c, count);
        return count;
    }
    
    private void decrementCountMap(HashMap<Character, Integer> countMap, char c) {
        countMap.put(c, countMap.get(c) - 1);
    }
    
    private int getDiff(String s, int i, int j) { // O(N)
        // find all distinct characters, count the character wtih most count as c1, count the other characters as c2, diff = c2
        // e.g. aaabc, c1=3,c2=2
        HashMap<Character, Integer> countMap = new HashMap<>();
        int c1 = Integer.MIN_VALUE; // count of most frequent char
        int cTotal = j - i + 1;
        for (int k = i; k <= j; k++) {
            Integer count = countMap.getOrDefault(s.charAt(k), 0);
            count = count + 1;
            c1 = Math.max(c1, count);
            countMap.put(s.charAt(k), count);
        }
        int diff = cTotal - c1;
        return diff;
    }
}