// https://leetcode.com/problems/group-anagrams/solution/
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String encode = encode(str);
            List<String> list = map.getOrDefault(encode, new LinkedList<>());
            list.add(str);
            map.put(encode, list);
        }
        return map.values().stream().collect(Collectors.toList());
    }
    private String encode(String str) {
        if (str.equals("")) {
            return "-1";
        }
        int[] dict = new int[26]; // could have duplicate char!!!
        for (char c : str.toCharArray()) {
            dict[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dict.length; i++) {
            if (dict[i] > 0) {
                sb.append(i + "-" + dict[i] + "#");
            }
        }
        return sb.toString();
    }
}