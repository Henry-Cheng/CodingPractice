
import java.util.*;
import java.lang.*;
import java.io.*;
class Template
 {
	public static void main (String[] args)
	 {
        Scanner scanner = new Scanner(System.in);
        int nums = Integer.parseInt(scanner.nextLine());
        List<int[]> testcases = new ArrayList<int[]>();
        for (int i = 1; i <= nums; i++) {
            String size = scanner.nextLine();
            testcases.add(convert(scanner.nextLine()));
        }
        for (int[] testcase : testcases) {
            solve(testcase);
        }
	 }

	 public static int[] convert(String str) {
	     String[] wordArray = str.split("\\s+");
	     int[] intArray = new int[wordArray.length];
	     for (int i = 0; i < wordArray.length; i++) {
	         intArray[i] = Integer.parseInt(wordArray[i]);
	     }
	     return intArray;
	 }
	 
	 public static void solve(int[] nums) {
	     
	 }
}