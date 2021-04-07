// https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
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
    

    char[] carryOver = new char[4]; // stores carryOver or new data from read4
    int carryOverValidSize = 0;
    int carryOverValidIndex = 0;
    public int read(char[] buf, int n) {

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
    
    
    
    private char[] buff4OrCarryOver = new char[4]; // we can only carryOver 3 char at most, but we set the space to be 4 to reuse it for read4
    public int read_complicated(char[] buf, int n) {
        if (n == 0) {
            return 0;
        }
        //System.out.println("0. buff4OrCarryOver is " + printBuf(buff4OrCarryOver));
        // 1. process carryOver if we have carryOver char
        int reminder = n;
        int processed = 0;
        int validSize = validSize(buff4OrCarryOver);
        if (validSize > 0) { // we have carryOver
            processed = Math.min(validSize, n);
            copyBuff(buff4OrCarryOver, buf, 0, processed);
            shift(buff4OrCarryOver, processed);
        }
        reminder -= processed;   
        if (reminder <= 0) {
            return processed;
        }
        //System.out.println("1. buff4OrCarryOver is " + printBuf(buff4OrCarryOver));
        // 2. we still have "reminder" character, and buff4OrCarryOver is clean now
        while (reminder > 0) {
            //System.out.println("reminder is " + reminder);
            int count = read4(buff4OrCarryOver);
            //System.out.println("read4 is " + printBuf(buff4OrCarryOver));
            if (count == 0) { // no content in file
                break;
            }
            int needProcessed = Math.min(count, reminder);
            //System.out.println("need copy " + needProcessed);
            copyBuff(buff4OrCarryOver, buf, processed, needProcessed);
            //System.out.println("now buf is " + printBuf(buf));
            //System.out.println("now buff4OrCarryOver is " + printBuf(buff4OrCarryOver));
            shift(buff4OrCarryOver, needProcessed);
            //System.out.println("after shift " + needProcessed + ", buff4OrCarryOver is " + printBuf(buff4OrCarryOver));
            processed += needProcessed;
            reminder -= needProcessed;
        }
        //System.out.println("2. buff4OrCarryOver is " + printBuf(buff4OrCarryOver));
        return processed;
    }
    
    private void copyBuff(char[] from, char[] to, int start, int num) {
        if (start >= 0 && start < to.length && num > 0 && num <= from.length) {
            for (int i = 0; i < num; i++) {
                to[start + i] = from[i];
            }
        }
    }
    
    // 0 1 2
    // a b c --> offset 1
    // 
    private void shift(char[] buf, int offset) {
        //System.out.println("now shifting " + offset + " in total " + buf.length);
        if (offset > 0 && offset <= buf.length)  {
            Arrays.fill(buf, 0, offset, '_');
            for (int i = offset; i < buf.length; i++) {
                //System.out.println("buf " + (i - offset) + " is buf " + buf.length);
                buf[i - offset] = buf[i];
                buf[i] = '_'; // put an invalid char
                //System.out.println("shifting... " + printBuf(buf));
            } 
        }
    }
    
    private boolean isValidChar(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
            return true;
        } else {
            return false;
        }
    }
    
    private int validSize(char[] buf) {
        for (int i = 0; i < buf.length; i++) {
            if (!isValidChar(buf[i])) {
                return i;
            }
        }
        return buf.length;
    }
    
    private String printBuf(char[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            sb.append(buf[i]);
        }
        return sb.toString();
    }
}