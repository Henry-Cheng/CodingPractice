// https://leetcode.com/problems/reverse-words-in-a-string/
class Solution {
    public String reverseWords(String s) {
        String[] arr = s.split(" ");
        List<String> list = new LinkedList();
        for(int i = arr.length-1; i >=0; i--) {
            if (!arr[i].isEmpty() && !arr[i].equals(" ")) {
                list.add(arr[i]);
            }
        }
        return String.join(" ", list);
    }
}