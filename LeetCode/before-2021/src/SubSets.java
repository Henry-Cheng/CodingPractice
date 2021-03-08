import java.util.*;

public class SubSets {
	public static void main(String[] args) {
		int[] nums = {1,2,3};
		System.out.println(subsets(nums));
	}
    // f(i) = each items in f(i-1) add nums[i]
    // f(-1) = []
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        return recursion(nums, nums.length - 1, result);
    }
    
    public static List<List<Integer>> recursion(int[] nums, int pos, List<List<Integer>> result) {
        if (pos == -1) {
            result.add(new ArrayList<Integer>());
            return result;
        }
        List<List<Integer>> previousResult = recursion(nums, pos - 1, result);
        // cannot directly traverse to previousResult.size(), previousResult is reference to result
        // it would increase too
        int size = previousResult.size();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> newList = new ArrayList<Integer>(previousResult.get(i));
            newList.add(nums[pos]);
            result.add(newList);
        }
        return result;
    }
}