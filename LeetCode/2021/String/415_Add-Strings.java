// https://leetcode.com/problems/add-strings/
 Solution {
    public String addStrings(String num1, String num2) {
        int pos1 = num1.length() - 1;
        int pos2 = num2.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carryOver = 0;
        while (pos1 >= 0 || pos2 >= 0) {
            if (pos1 < 0) {
                while (pos2 >= 0) {
                    int num = (num2.charAt(pos2) - '0') + carryOver;
                    sb.append(num % 10);
                    carryOver = num / 10;
                    pos2--;
                }
            } else if (pos2 < 0) {
                while(pos1 >= 0) {
                    int num = (num1.charAt(pos1) - '0') + carryOver;
                    sb.append(num % 10);
                    carryOver = num / 10;
                    pos1--;
                }
            } else {
                int num = (num1.charAt(pos1) - '0') + (num2.charAt(pos2) - '0') + carryOver;
                int reminder = num % 10;
                sb.append(reminder);
                carryOver = num / 10;
                //System.out.println("reminder is " + reminder + ", carryOver is " + carryOver);
                pos1--;
                pos2--;
            }

        }
        if (carryOver != 0) {
            sb.append(carryOver);
        }
        return sb.reverse().toString();
    }
}