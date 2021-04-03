// https://leetcode.com/problems/multiply-strings/submissions/
class Solution {
    public String multiply(String num1, String num2) {
        /**
        123 * 456
        = 123 * 6 * 10^0 + 123 * 5 * 10^1 + 123 * 4 * 10^2
        **/
        // brute force T(M*N), S(M+N)
        int pos2 = num2.length() - 1;
        int multiple = 0;
        String result = "0";
        while(pos2 >= 0) {
            String tmpResult = multiplyHelper(num1, num2.charAt(pos2) - '0', multiple);
            result = addTwoNumber(result, tmpResult);
            multiple++;
            pos2--;
        }
        return new StringBuilder(result).reverse().toString();
    }
    
    private String multiplyHelper(String num1, int num2, int multiple) {
        if (num1.equals("0") || num2 == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int pos1 = num1.length() - 1;
        int carryOver = 0;
        while(pos1 >= 0) {
            int num = (num1.charAt(pos1) - '0') * num2 + carryOver;
            sb.append(num % 10);
            carryOver = num / 10;
            pos1--;
        }
        if (carryOver > 0) {
            sb.append(carryOver);
        }
        while(multiple > 0) {
            sb.insert(0,'0'); // O(N)
            multiple--;
        }
        return sb.toString();
    }
    
    private String addTwoNumber(String num1, String num2) {
        int pos1 = 0;
        int pos2 = 0;
        int carryOver = 0;
        StringBuilder sb = new StringBuilder();
        while(pos1 <= num1.length() - 1 || pos2 <= num2.length() - 1) {
            if (pos1 > num1.length() - 1) {
                while(pos2 <= num2.length() - 1) {
                    int num = num2.charAt(pos2) - '0' + carryOver;
                    sb.append(num  % 10);
                    carryOver = num / 10;
                    pos2++;
                }
            } else if (pos2 > num2.length() - 1) {
                while(pos1 <= num1.length() - 1) {
                    int num = num1.charAt(pos1) - '0' + carryOver;
                    sb.append(num  % 10);
                    carryOver = num / 10;
                    pos1++;
                }  
            } else {
                int num = (num1.charAt(pos1) - '0') + (num2.charAt(pos2) - '0') + carryOver;
                sb.append(num  % 10);
                carryOver = num / 10;
                pos1++;
                pos2++;
            }
        }
        if (carryOver > 0) {
            sb.append(carryOver);
        }
        return sb.toString();
    }
}