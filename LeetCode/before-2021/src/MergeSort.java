import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int[] nums = {3, 4, 6, 5, 7 , 2, 1};
		int[] tmp = new int[nums.length]; // NOTE!!!
		for (int i = 0 ; i < nums.length; i++) {
			tmp[i] = nums[i];
		}
		mergeSort(nums, 0, nums.length - 1, tmp);
	}

	public static void mergeSort(int[] nums, int start, int end, int[] tmp) {
		if (start >= end) {
			return;
		}
		int mid = start + (end - start)/2;
		// NOTE!!!
		mergeSort(tmp, start, mid, nums); // sort tmp based on nums
		mergeSort(tmp, mid + 1, end, nums); // sort tmp based on nums
		merge(nums, start, mid, end, tmp); // based on 2 sorted parts of tmp, to sort nums
		printArray(nums, start, end);
	}
	
	public static void merge(int[] nums, int start, int mid, int end, int[] tmp) {
		int i = start;
		int j = mid + 1;
		for (int k = start; k <= end; k++) {
			if (i <= mid && (j > end || tmp[i] < tmp[j])) { // NOTE!!!
				nums[k] = tmp[i];
				i++;
			} else {
				nums[k] = tmp[j];
				j++;
			}
		}
	}
	
	public static void printArray(int[] nums, int start, int end) {
		System.out.print(start + " " + end +  " : ");
		for (int i = 0 ; i < nums.length; i++) {
			System.out.print(nums[i] + " ");
		}
		System.out.println();
	}
}
