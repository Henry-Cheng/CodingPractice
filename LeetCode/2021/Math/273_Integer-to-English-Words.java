// https://leetcode.com/problems/integer-to-english-words/submissions/
class Solution {
    public HashMap<Integer, String> map = provideHashMap();;
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        StringBuilder sb = new StringBuilder();
        // divide the num by billion/million/thousand
        num = divideNum(num, sb, 1000000000, "Billion");
        num = divideNum(num, sb, 1000000, "Million");
        num = divideNum(num, sb, 1000, "Thousand");
        sb.append(getWordsLessThan1000(num));
        return sb.toString().trim();
    }
    
    private int divideNum(int num, StringBuilder sb, int levelNum, String levelString) {
        if (num > 0) {
            int divided = num / levelNum;
            if (divided > 0) {
                sb.append(getWordsLessThan1000(divided));
                sb.append(levelString + " ");
            }
            num = num % levelNum;
        }
        return num;
    }
    private String getWordsLessThan1000(int num) {
        // num is less than 1000
        // e.g. 913 --> nine hundred thirteen
        if (num == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int num100 = num / 100;
        if (num100 > 0) {
            sb.append(map.get(num100) + " ");
            sb.append("Hundred ");
        }
        int num100Reminder = num % 100;
        if (num100Reminder > 0) { // num100Reminder is less than 100
            if (num100Reminder <= 20) {
                sb.append(map.get(num100Reminder) + " ");
            } else {
                int num10 = num100Reminder / 10;
                if (num10 > 0) {
                    sb.append(map.get(num10 * 10) + " ");
                }
                int num10Reminder = num100Reminder % 10;
                if (num10Reminder > 0) {
                    sb.append(map.get(num10Reminder) + " ");
                }
            }
        }
        return sb.toString();
    }
    
    private HashMap<Integer, String> provideHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");
        map.put(5, "Five");
        map.put(6, "Six");
        map.put(7, "Seven");
        map.put(8, "Eight");
        map.put(9, "Nine");
        map.put(10, "Ten");
        map.put(11, "Eleven");
        map.put(12, "Twelve");
        map.put(13, "Thirteen");
        map.put(14, "Fourteen");
        map.put(15, "Fifteen");
        map.put(16, "Sixteen");
        map.put(17, "Seventeen");
        map.put(18, "Eighteen");
        map.put(19, "Nineteen");
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        map.put(40, "Forty");
        map.put(50, "Fifty");
        map.put(60, "Sixty");
        map.put(70, "Seventy");
        map.put(80, "Eighty");
        map.put(90, "Ninety");
        return map;
    }
}