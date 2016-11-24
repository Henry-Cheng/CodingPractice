/*
 * https://discuss.leetcode.com/topic/46162/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partioning
 */
import java.util.*;

public class BackTracking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {1,2,3};
		System.out.println(permute(nums));
		System.out.println(anagrams("123"));
	}

	public static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		// Arrays.sort(nums); // not necessary
		backtrackpermute(list, new ArrayList<>(), nums);
		return list;
	}

	public static void backtrackpermute(List<List<Integer>> list, List<Integer> tempList, int[] nums) {
		if (tempList.size() == nums.length) {
			list.add(new ArrayList<>(tempList));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (tempList.contains(nums[i]))
					continue; // element already exists, skip
				tempList.add(nums[i]);
				backtrackpermute(list, tempList, nums);
				tempList.remove(tempList.size() - 1);
			}
		}
	}
	
	public static List<List<Integer>> permuteDuplicate(int[] nums) {
	    List<List<Integer>> list = new ArrayList<>();
	    Arrays.sort(nums); // NOTE!!!!
	    backtrackpermuteDuplicate(list, new ArrayList<>(), nums, new boolean[nums.length]);
	    return list;
	}

	public static void backtrackpermuteDuplicate(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
	    if(tempList.size() == nums.length){
	        list.add(new ArrayList<>(tempList));
	    } else{
	        for(int i = 0; i < nums.length; i++){
	        	// 用过了不行；
	        	// 没用过，看它是不是中间的,之前跟他一样不，之前那个用过不（之前那个没用过，这个就更不能用）
	            if(used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i - 1])) continue;
	            used[i] = true; 
	            tempList.add(nums[i]);
	            backtrackpermuteDuplicate(list, tempList, nums, used);
	            used[i] = false; 
	            tempList.remove(tempList.size() - 1);
	        }
	    }
	}
	
	public static List<String> anagrams (String input){
		char[] inp = input.toCharArray();
		List<String> result = new ArrayList();
		backtrackanagrams(inp,result,"");
	   	return result;
	} 
	public static void backtrackanagrams(char[] input,List result,String str) {
		if(str.length()==input.length){  // or can be input.length
			result.add(new String(str));
		}
		else{
			for(int i =0 ;i<input.length;i++){
				if(str.contains(""+input[i])){
					continue;
				}
				String newStr = str + input[i];
				backtrackanagrams(input,result,newStr);
			}
		}
	}
	
	public static void subset(int[] nums) {
		
	}
}
