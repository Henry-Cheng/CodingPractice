import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyUtil {
	
	public static void swap(int[] nums, int from, int to) {
		int tmp = nums[from];
		nums[from] = nums[to];
		nums[to] = tmp;
	}
	
	public static void swap(List<Integer> nums, int from, int to) {
		int tmp = nums.get(from);
		nums.set(from, nums.get(to));
		nums.set(to, tmp);
	}
	
	public static List<String> readFromFile() throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader("data"));
	    String line = null;

	    List<String> list = new ArrayList<String>();
	    while ((line = br.readLine()) != null) {
	      String[] values = line.split(",");
	      for (String str : values) {
	    	  list.add(str);
	      }
	    }
	    br.close();
	    return list;
	}
	
	public static void listToArray(List<String> list, int[] nums) {
		int count = 0;
		for (String str : list) {
			nums[count] = Integer.parseInt(str);
			count++;
		}
	}
	
	public static List<Integer> strToInteger(List<String> list) {
		List<Integer> intList = new ArrayList<Integer>();
		for (String str : list) {
			intList.add(Integer.parseInt(str));
		}
		return intList;
	}
	
	public static int findNumInArray(int[] nums, int key) {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == key) {
				return i;
			}
		}
		return -1;
	}
	
	public static void printArray(int[] nums, int start, int end) {
		for (int i = start; i <= end; i++) {
			System.out.print(nums[i] + ", ");
		}
		System.out.println();
	}

	public static void printArray(List<Integer> nums, int start, int end) {
		for (int i = start; i <= end; i++) {
			System.out.print(nums.get(i) + ", ");
		}
		System.out.println();
	}
}
