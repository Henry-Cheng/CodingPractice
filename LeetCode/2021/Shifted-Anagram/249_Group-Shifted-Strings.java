// https://leetcode.com/problems/group-shifted-strings/submissions/
class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        // the map stores "character distance" to "list of original string" mapping
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            StringBuilder sb = new StringBuilder();
            int length = s.length();
            sb.append(length + "-");
            if (length == 1) {
                // do nothing, since if size is 1, it could be shifted to any other characters
            } else {
                for (int i = 0; i < s.length() - 1; i++) {
                    char prev = s.charAt(i);
                    char after = s.charAt(i + 1);
                    int diff = getDistance(prev, after);
                    sb.append(diff + "-");
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new LinkedList<>());
            list.add(s);
            map.put(key, list);
        }
        //System.out.println(map);
        return map.values().stream().collect(Collectors.toList());
    }
    
    // NOTE: it could have rotation
    //dist:   2 2       2 2       2 2       2 2       2 2       2 2       2 2
    //combo: a c e --> v x z --> w y a --> x z b --> y a c --> z b d --> a c e
    
    // a  z -> b a
    // 1 26 -> 2 1
    private int getDistance(char c1, char c2) {
        int num1 = c1 - 'a';
        int num2 = c2 - 'a';
        //  w    y    a   --> x    z    b
        // 23   25    1       24   26   2
        //    2    2             2    2
        if (num2 < num1) {
            return num2 - num1 + 26;
        } else {
            return num2 - num1;
        }
    }
}