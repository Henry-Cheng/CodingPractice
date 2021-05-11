// https://leetcode.com/problems/construct-binary-tree-from-string/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
// O(nlogn)
class Solution {
    public TreeNode str2tree(String s) {
        //System.out.println("string is " + s);
        if (s.isEmpty()) {
            return null;
        }
        int sign = 1;
        int currentNum = 0;
        boolean isLeftDone = false;
        TreeNode root = null;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '-') {
                sign = -1;
            } else if (Character.isDigit(s.charAt(i))) {
                currentNum = currentNum * 10 + s.charAt(i) - '0';
            }
            if(i == s.length() -1 || s.charAt(i) == '(') { // see left bracket
                root = new TreeNode(currentNum * sign);
                if (i < s.length() - 1) { // if left exists
                    int j = findCorrespondingRightBracket(s, i); 
                    // now j is the ")", j+1 is the next "("
                    //System.out.println("find left j for " + j + "-->" + s.charAt(j));
                    root.left = str2tree(s.substring(i + 1, j));
                    if (j+1 < s.length() -1) { // if right child exists
                        //System.out.println("looing for right by " + (j+1) + "-->"+ s.charAt(j+1));
                        int k = findCorrespondingRightBracket(s, j + 1);
                        //System.out.println("find right k for " + k + "-->" + s.charAt(k));
                        root.right = str2tree(s.substring(j +2, k)); 
                        
                    }
                }
                break;
            }
        }
        //System.out.println("root is " + (root != null? root.val : "null"));
        //System.out.println("root.left is " + (root.left != null? root.left.val : "null"));
        //System.out.println("root.right is " + (root.right != null? root.right.val : "null"));
        return root;
    }
    
    private int findCorrespondingRightBracket(String s, int i) {
        int leftBrackets = 1; // i is already the left bracket
        int rightBrackets = 0;
        // find the corresponding right bracket
        int j = i + 1;
        while(j < s.length() && leftBrackets != rightBrackets) {
            if (s.charAt(j) == '(') {
                leftBrackets++;
            } else if (s.charAt(j) == ')') {
                rightBrackets++;
            }
            j++;
        }
        return j - 1; // j-1 is the right bracket
    }
}