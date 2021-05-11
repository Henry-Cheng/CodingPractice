// https://leetcode.com/problems/utf-8-validation/
class Solution {
    // data[i] <= 255 --> 2^8 --> at most 8 char in binary string
    public boolean validUtf8(int[] data) {
        int nByte = 0;
        int nByteCount = 0;
        for(int i = 0; i < data.length; i++) {
            String str = Integer.toBinaryString(data[i]);
            //System.out.println(str);
            if(str.length() < 8) { // first bit must be 0, it is 1-byte
                if (nByte > 0 && nByteCount != nByte) {
                    return false;
                }
            } else {
                // 1st c must be 1, check how many 1
                int count = 0;
                for(char c: str.toCharArray()) {
                    if (c == '1') {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count > 4) {
                    return false;
                }
                nByteCount++;
                if (nByte == 0) { // start couint nByte
                    nByte = count; 
                } else if (nByte > 0) {
                    if (nByteCount == nByte) { // found nByte
                        nByte = 0;
                        nByteCount = 0;
                    }
                }
            }
        }
        return nByteCount == 0;
    }
}