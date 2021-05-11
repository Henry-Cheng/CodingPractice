// https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/
class Solution {
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder, (a,b)->{
            return a.compareTo(b); // ascending order
        });
        List<String> result = new LinkedList<>();
        result.add(folder[0]);
        for (int i = 1; i < folder.length; i++) {
            String prefix = result.get(result.size()-1);
            String current = folder[i];
            if (current.startsWith(prefix) && current.charAt(prefix.length()) == '/') {
                continue; // found subfolder
            } else {
                result.add(current);
            }
        }
        return result;
    }
}