// https://leetcode.com/problems/bitwise-and-of-numbers-range/
class Solution {
  public int rangeBitwiseAnd(int m, int n) {
    int shift = 0;
    // find the common 1-bits
    while (m != n) { // remove all different bits in m and n, until they are the same (so that 1 & 1 or  0 & 0 would have no change)
      m = m >>1; 
      n = n >>1;
      ++shift;
    }
    return m << shift; // now shift back
  }
}