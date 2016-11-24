import java.util.*;
public class SegmentTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {2, 5, 1, 4, 9, 3, -1};
		int height = (int)Math.ceil(log(nums.length, 2));
		Integer[] tree = new Integer[2 * (int) Math.pow(2, height) - 1];
		int min = build(nums, tree, 0, 0, nums.length - 1);
		System.out.println(min);
		print(tree);
	}

	public static double log(int value, int base) {
		return Math.log(value) / Math.log(base);
	}
	
	public static int build(int[] nums, Integer[] tree, int current, int start, int end) {
		if (start == end) {
			tree[current] = nums[start];
		} else {
			int mid = start + (end - start) / 2;
			tree[current] = Math.min(build(nums, tree, current*2+1, start, mid), 
					build(nums, tree, current*2+2, mid + 1, end));
		}
		return tree[current];
	}
	
	/*
	 * Find the minimum element in interval [start, end]
	 */
	public static Integer find(Integer[] tree, int current, int tStart, int tEnd, int qStart, int qEnd) {
		//  tStart [0, 1, 2, 3, 4, 5] tEnd
		//     qStart [1,       4] qEnd
		if(qEnd < tStart || qStart > tEnd) {
			return Integer.MAX_VALUE;
		} else if (qStart <= tStart && qEnd >= tEnd){
			return tree[current];
		} else {
			int mid = tStart + (tEnd - tStart)/2;
			return Math.min(find(tree, current*2+1, tStart, mid, qStart, qEnd), 
					find(tree, current*2+2, mid+1, tEnd, qStart, qEnd));
		}
	}
	
	public static void print(Integer[] nums) {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i] + " ");
		}
		System.out.println();
	}
}
