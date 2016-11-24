import java.util.*;

public class Permutation {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {1, 2, 3};
		List<List<Integer>> results = permute(nums);
		System.out.println(results);
	}
	
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums.length != 0) {
            List<Integer> list = covertArrayToList(nums);
            results = recursion(list);
        }
        return results;
    }
    
    public static List<List<Integer>> recursion(List<Integer> list) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (list.size() == 1) {
            List<Integer> result = new ArrayList<Integer>(list); // argument would be int(capacity), or list(copy)
            results = new ArrayList<List<Integer>>();
            results.add(result);
        } else {
            for (int i = 0; i < list.size(); i++) {
                swap(list, 0, i);
                // "from" inclusive, but "to" exclusive
                List<Integer> subList = new ArrayList<Integer>(list.subList(1, list.size()));
                // need to new a subList, b/c list.subList() uses reference of part of list
                List<List<Integer>> tmpResults = recursion(subList);
                for (List<Integer> tmpResult : tmpResults) {
                    tmpResult.add(0, list.get(0));
                }
                results.addAll(tmpResults);
            }
        }
        return results;
    }
    
    public static void swap(List<Integer> list, int from, int to) {
        int tmp = list.get(from);
        list.set(from, list.get(to));
        list.set(to, tmp);
    }
    
    public static List<Integer> covertArrayToList(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        return list;
    }
    
    public static int[] converListToArray(List<Integer> list) {
        int[] nums = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }
        return nums;
    }
}