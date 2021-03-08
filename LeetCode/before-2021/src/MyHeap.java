import java.io.IOException;
import java.util.*;

// max heap
public class MyHeap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> strList = null;
		try {
			strList = MyUtil.readFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strList == null) {
			return;
		}
		List<Integer> nums = MyUtil.strToInteger(strList);
		MyHeap heap = new MyHeap();
		int k = 22;
		// Method 1
		heap.buildByShiftDown(nums);
		for (int i = 0; i < k-1; i++) {
			heap.remove(nums);
		}
		System.out.println(nums.get(0));
		//Method 2
		nums = MyUtil.strToInteger(strList);
		heap.heapSort(nums);
		System.out.println(nums.get(k - 1));
	}

	public void buildByShiftUp(List<Integer> nums) { // longer than buildByShiftDown();O(nlgn)
		for (int i = nums.size() -1; i >= 0; i--) {
			heapifyUp(nums, i);
		}
	}
	
	public void buildByShiftDown(List<Integer> nums) {// O(n)
		// NOTE: parent = (childPos - 1)/2
		for(int i = (int) Math.floor((nums.size() - 1 -1)/2); i >= 0; i--) {
			heapifyDown(nums, i);
		}
	}

	public void heapSort(List<Integer> nums) { // similar to remove(); sorting all nums, O(nlgn)
		Queue<Integer> queue = new LinkedList<Integer>();
		buildByShiftDown(nums);
		int counter = 0;
		while (counter < nums.size() -1) {
			queue.offer(remove(nums));
		}
		while(!queue.isEmpty()) {
			nums.add(queue.poll());
		}
	}
	
	public int remove(List<Integer> nums) {
		int tmp = nums.get(0);
		MyUtil.swap(nums, 0, nums.size()-1);
		nums.remove(nums.size() -1);
		heapifyDown(nums, 0);
		return tmp;
	}
	
	public void insert(List<Integer> nums, int value) {
		nums.add(value);
		heapifyUp(nums, nums.size() - 1);
	}
	
	public void heapifyUp(List<Integer> nums, int child) {
		int parent = (int) Math.floor((child - 1)/2);
		if (parent >= 0 && nums.get(parent) < nums.get(child)) {
			MyUtil.swap(nums, parent, child);
			heapifyUp(nums, parent);
		}
	}
	
	public void heapifyDown(List<Integer> nums, int parent) {
		int left = parent * 2 +1;
		int right = parent * 2 + 2;
		if (right < nums.size()) { // both child exists
			int child = nums.get(left) >= nums.get(right) ? left : right;
			if (nums.get(parent) < nums.get(child)) {
				MyUtil.swap(nums, parent, child);
				heapifyDown(nums, child);
			}
		} else if (right >= nums.size() && left < nums.size()) { // only left child exists
			if (nums.get(parent) < nums.get(left)) {
				MyUtil.swap(nums, parent, left);
				//heapifyDown(nums, left);
			}
		} else { // both invalid
			
		}
	}
}
