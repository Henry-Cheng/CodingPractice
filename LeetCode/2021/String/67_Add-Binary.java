// https://leetcode.com/problems/add-binary/
class Solution {
    public String addBinary(String a, String b) {
        int posA = a.length() - 1;
        int posB = b.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carryOver = 0;
        while(posA >= 0 || posB >= 0) {
            if (posA < 0) {
                while (posB >= 0) {
                    int num = b.charAt(posB) - '0' + carryOver;
                    sb.append(num % 2);
                    carryOver = num / 2;
                    posB--;
                    //System.out.println("sb.toString() is " + sb.toString());
                }
            } else if (posB < 0) {
                while (posA >= 0) {
                    int num = a.charAt(posA) - '0' + carryOver;
                    sb.append(num % 2);
                    carryOver = num / 2;
                    posA--;
                    //System.out.println("sb.toString() is " + sb.toString());
                } 
            } else { // both posA and posB are valid
                int num = (a.charAt(posA) - '0') + (b.charAt(posB) - '0') + carryOver;
                sb.append(num % 2);
                carryOver = num / 2;
                posA--;
                posB--;
                //System.out.println("sb.toString() is " + sb.toString());
            }
        }
        if (carryOver == 1) {
            sb.append(1);
            //System.out.println("sb.toString() is " + sb.toString());
        }
        return sb.reverse().toString();
    }
    
}