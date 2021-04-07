// https://leetcode.com/problems/read-n-characters-given-read4/
/**
 * The read4 API is defined in the parent class Reader4.
 *     int read4(char[] buf4);
 */

public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    
    public int read(char[] buf, int n) {
        char[] carryOver = new char[4]; // stores carryOver or new data from read4
        int carryOverValidSize = 0;
        int carryOverValidIndex = 0;
        
        int processed = 0;
        while (processed < n) {
            if (carryOverValidSize == 0) { // no carryOver, pull new data from file
                carryOverValidSize = read4(carryOver);
                carryOverValidIndex = 0;
            }
            if (carryOverValidSize == 0) {
                break; // no content from file any more
            }
            buf[processed] = carryOver[carryOverValidIndex];
            processed++;
            carryOverValidIndex++;
            carryOverValidSize--;
        }
        return processed;
    }
}