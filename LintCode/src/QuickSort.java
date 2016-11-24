
public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {4, 5, 6, 3,4,1,5,2};
		quickSort(nums, 0, nums.length - 1);
	}

	public static void quickSort(int[] nums, int start, int end) {
		if (start < end) {
			int pivote = findPivote(nums, start, end);
			if (pivote > start +1) {
				quickSort(nums, start, pivote -1);
			}
			if (pivote < end -1) {
				quickSort(nums, pivote + 1, end);
			}
		}
		System.out.print(start + " " + end + " : ");
		printArray(nums);
	}
	
	public static int findPivote(int[] nums, int start, int end) {
		int pivote = start + (end - start) / 2;
		while(start < end) {
			while(nums[start] < nums[pivote]) {
				start++;
			}
			while(nums[end] > nums[pivote]) {
				end--;
			}
			swap(nums, start, end);
			if (pivote == start || pivote == end) {
				pivote = pivote == start ? end : start; // NOTE!!!
			}
			start++;
			end--;
		}
		return pivote;
	}
	
	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
	
	public static void printArray(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i] + " ");
		}
		System.out.println();
	}
}
