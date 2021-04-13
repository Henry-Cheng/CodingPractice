// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/

class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        /**
               3
           5        1
        6    2   0     8
           7   4
        **/
        
        // 1. find the height of p and q
        Node pParent = p;
        int pHeight = 1;
        while(pParent.parent != null) {
            pParent = pParent.parent;
            pHeight++;
        }
        Node qParent = q;
        int qHeight = 1;
        while(qParent.parent != null) {
            qParent = qParent.parent;
            qHeight++;
        }
        //System.out.println("pHeight is " + pHeight + ", qHeight is " + qHeight);
        
        // 2. if p is lower, move p up to the same level as q
        while(pHeight > qHeight) {
            p = p.parent;
            pHeight--;
        }
        //System.out.println("after moving p, pHeight is " + pHeight + ", qHeight is " + qHeight);
        
        // 3. if q is lower, move q up to the same level as p
        while(qHeight > pHeight) {
            q = q.parent;
            qHeight--;
        }
        //System.out.println("after moving q, pHeight is " + pHeight + ", qHeight is " + qHeight);
        
        // 4. now p and q are at same height
        if (p == q) {
            return q;
        } else {
            // move p and q up to the same parent 
            while (p.parent != q.parent) {
                p = p.parent;
                q = q.parent;
            }
            // now p and q must be the same
            return p.parent;
        }
    }
}