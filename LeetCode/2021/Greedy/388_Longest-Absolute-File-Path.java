// https://leetcode.com/problems/longest-absolute-file-path/
class Solution {
    public int lengthLongestPath(String input) {
        /**
        dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext
        dir
            \tsubdir1
                \t\tfile1.ext
                \t\tsubsubdir1
            \tsubdir2
                \t\tsubsubdir2
                    \t\t\tfile2.ext
        dir2
            \tfile3.ext
        file4.ext
        
        using hashmap to store the level to length mapping:
        1 --> dir
        2 --> subdir1
        3 --> file1.ext  --> record path length
        3 --> subsubdir1 --> it will override level3, but doesn't matter!!
        2 --> subsidr2 --> it will override level2, but doesn't matter!
        3 --> subsubdir2 
        4 --> file2.ext --> record path length
        1 --> dir2
        2 --> file2.ext --> record path length
        1 --> file4.ext --> record path length
        
        **/
        HashMap<Integer, Integer> pathLength = new HashMap<>();
        pathLength.put(-1, 0);
        int result = Integer.MIN_VALUE;
        for (String level : Arrays.asList(input.split("\n"))) {
            int shift = level.lastIndexOf('\t'); // if not found, shift is -1
            String name = level.substring(shift + 1);
            int currentPathLength = pathLength.get(shift) + name.length();
            if (name.contains(".")) { // found file
                result = Math.max(result, currentPathLength);
                // no need to push to map since it would be override soon
            } else { // found dir
                pathLength.put(shift + 1, currentPathLength + 1); // add "/" to the end
            }
        }
        return Math.max(result, 0); // in case no file in the path at all
    }
}