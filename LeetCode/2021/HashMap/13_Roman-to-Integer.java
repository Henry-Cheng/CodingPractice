// https://leetcode.com/problems/roman-to-integer/
class Solution {
    public int romanToInt(String s) {
        LinkedHashMap<String, Integer> map = buildMap();
        int result = 0;
        while(s.length() > 0) {
            for (Map.Entry<String, Integer> entry: map.entrySet()) {
                String key = entry.getKey();
                //System.out.println();
                if (s.startsWith(key)) {
                    result+=entry.getValue();
                    if (key.length() == s.length()) {
                        s = "";
                    } else {
                        s = s.substring(key.length(), s.length());
                    }
                    break;
                }
            }
        }
        return result;
    }
    

    private LinkedHashMap<String, Integer> buildMap() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("III", 3);
        map.put("II", 2);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);  
        
        return map;
    }
}