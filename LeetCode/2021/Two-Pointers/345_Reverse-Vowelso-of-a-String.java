// https://leetcode.com/problems/reverse-vowels-of-a-string/
class Solution {
    public String reverseVowels(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }
        char[] vowelsArray = {'a','e','i','o','u','A','E','I','O','U'};
        Map<Character, Integer> vowels = new HashMap<>();
        vowels.put('a', 1);
        vowels.put('e', 1);
        vowels.put('i', 1);
        vowels.put('o', 1);
        vowels.put('u', 1);
        vowels.put('A', 1);
        vowels.put('E', 1);
        vowels.put('I', 1);
        vowels.put('O', 1);
        vowels.put('U', 1);
        
        char[] array = s.toCharArray();
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            while (left < right) {
                if (vowels.get(array[left]) != null) {
                    break;
                } else {
                    left++;
                }
            }
            while(left < right) {
                if (vowels.get(array[right]) != null) {
                    break;
                } else {
                    right--;
                }
            }
            if (left != right) {
                char tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
            }
            left++;
            right--;
        }
        
        return new String(array);
    }
}