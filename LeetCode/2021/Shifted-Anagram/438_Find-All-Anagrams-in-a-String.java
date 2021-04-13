// https://leetcode.com/problems/find-all-anagrams-in-a-string/
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new LinkedList<>();
        if (p.length() > s.length()) {
            return result;
        }
        //    0123
        // s: abcd
        // p: abc
        for (int i = 0; i <= s.length() - p.length(); i++) {
            String sub = s.substring(i, i + p.length());
            //System.out.println("sub is " + sub);
            if (isAnagram(sub, p)) {
                result.add(i);
            }
        }
        return result;
    }
    
    private boolean isAnagram(String a, String b) {
        int[] dict1 = new int[26];
        int[] dict2 = new int[26];
        for (int i = 0; i < a.length(); i++) {
            dict1[a.charAt(i) - 'a']++;
            dict2[b.charAt(i) - 'a']++;
        }
        for (int j = 0; j < 26; j++) {
            if (dict1[j] != dict2[j]) {
                return false;
            }
        }
        return true;
    }

    // LC solution
    public List<Integer> findAnagrams(String s, String p) {
        int ns = s.length(), np = p.length();
        if (ns < np) return new ArrayList();

        int [] pCount = new int[26];
        int [] sCount = new int[26];
        // build reference array using string p
        for (char ch : p.toCharArray()) {
          pCount[(int)(ch - 'a')]++;
        }

        List<Integer> output = new ArrayList();
        // sliding window on the string s
        for (int i = 0; i < ns; ++i) {
          // add one more letter 
          // on the right side of the window
          sCount[(int)(s.charAt(i) - 'a')]++;
          // remove one letter 
          // from the left side of the window
          if (i >= np) {
            sCount[(int)(s.charAt(i - np) - 'a')]--;
          }
          // compare array in the sliding window
          // with the reference array
          if (Arrays.equals(pCount, sCount)) {
            output.add(i - np + 1);
          }
        }
        return output;
      }
}