// https://leetcode.com/problems/valid-word-abbreviation/
class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0;
        int j = 0;
        boolean isPrevInteger = false;
        while (i < word.length() && j < abbr.length()) {
            char w = word.charAt(i);
            char a = abbr.charAt(j);
            if (Character.isLetter(a)) {
                if (w != a) {
                    return false;
                } else {
                    i++;
                    j++;
                }
                isPrevInteger = false;
            } else {
                if (isPrevInteger) {
                    return false; // the abbr itself is invalid
                }
                /**
                 0
                "i nternational iz atio n"
                "i 12 iz 4 n"

                **/
                int start = j;
                int end = start;
                while (end < abbr.length() && Character.isDigit(abbr.charAt(end))) {
                    end++;
                }
                String countStr = abbr.substring(start, end);
                if (countStr.startsWith("0")) {
                    return false;
                }
                int count = Integer.valueOf(countStr);
                //System.out.println("before i is " + i + ", j is " + j);
                i += count;
                j = end;
                System.out.println("after i is " + i + ", j is " + j);
                isPrevInteger = true;
            }
        }
        
        if (i == word.length() && j == abbr.length()) {
            return true;
        }
        return false;
    }
}