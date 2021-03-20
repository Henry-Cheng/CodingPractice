// https://leetcode.com/problems/add-bold-tag-in-string/
class Solution {
    public String addBoldTag(String s, String[] dict) {
        // 1st round: record positions of <b> and </b> to the string
        // cannot use treemap here, since there could be overlap/duplicate position
        List<Pos> positions = new LinkedList<>();
        // O(MNK)
        for (String key : dict) { // O(K)
            int index = 0;
            while(index >= 0 && index < s.length()) { // O(MN) // NOTE: it is s.length() not key.length()
                //System.out.println("lookup " + key + " by index " + index);
                int foundIndex = s.indexOf(key, index);
                if (foundIndex != -1) {
                    // store the position
                    positions.add(new Pos(foundIndex, true));// add left
                    positions.add(new Pos(foundIndex + key.length(), false));// add right
                    //System.out.println("Found " + key + " from " + foundIndex + " to " + (foundIndex + key.length()));
                    // move foundIndex to next char
                    foundIndex += 1;
                }
                index = foundIndex;
                //System.out.println("now index is " + index);
            }
        }
        // 2nd round: sort the positions
        Collections.sort(positions, new Comparator<Pos>() {
            @Override
            public int compare(Pos p1, Pos p2) {
                if (p1.index > p2.index) {
                    return 1; // p2 before p1
                } else if (p1.index == p2.index) {
                    if (p1.isLeft) {
                        return -1; // p1 before p2
                    } else {
                        return 1; // p2 before p1
                    }
                } else {
                    return -1; // p1 before p2
                }
            }
        });
        
        // 3rd round: merge overlap labels by traversing the positions
        Deque<Integer> stack = new LinkedList<>();
        List<List<Integer>> pairResult = new LinkedList<>();
        //System.out.println(positions);
        for (Pos pos : positions) {
            if (pos.isLeft) {
                stack.push(pos.index);
                //System.out.println("push stack: " + stack);
            } else {
                Integer leftIndex = stack.pop();
                //System.out.println("pop stack: " + stack);
                if (stack.isEmpty()) {
                    // found the widest parenthese, left is leftIndex, right is pos.index
                    List<Integer> pair = new LinkedList<>();
                    pair.add(leftIndex);
                    pair.add(pos.index);
                    pairResult.add(pair);
                }
            } 
        }
        //System.out.println(pairResult);
        
        //4th round: add the bold label to the string
        StringBuilder sb = new StringBuilder(s);
        int offSet = 0;
        for (List<Integer> pair : pairResult) {
            int left = pair.get(0);
            int right = pair.get(1);
            sb.insert(left + offSet, "<b>");
            offSet += 3;
            sb.insert(right + offSet, "</b>");
            offSet += 4;
        }
        return sb.toString();
    }
    
    public static class Pos {
        public int index;
        public boolean isLeft;
        public Pos(int index, boolean isLeft) {
            this.index = index;
            this.isLeft = isLeft;
        }
        @Override
        public String toString() {
            return index + " " + isLeft;
        }
    }
    
    // LC solution
    // using a boolean array to record whether a char in string is in dict
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        for (String d: dict) {
            for (int i = 0; i <= s.length() - d.length(); i++) {
                if (s.substring(i, i + d.length()).equals(d)) {
                    for (int j = i; j < i + d.length(); j++)
                        bold[j] = true;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length();) {
            if (bold[i]) {
                res.append("<b>");
                while (i < s.length() && bold[i])
                    res.append(s.charAt(i++));
                res.append("</b>");
            } else
                res.append(s.charAt(i++));
        }
        return res.toString();
    }
}