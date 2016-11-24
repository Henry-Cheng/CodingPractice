import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * For the ith element in array
 * Parent(i) = Math.floor((i-1)/2);
 * Left(i) = 2i + 1;
 * Right(i) = 2i + 2;
 * s.t. 
 * when i = length - 1, parent(length -1) = Math.floor((length-1-1)/2) 
 * = Math.floor(length/2 -1), which is the last "parent" node in array
 * From Math.floor(length/2 -1) to length-1, they are all leaves
 * From 0 to Math.floor(length/2 -1), they are all non-leaves
 */
public class Heap {
	public static int[] data;
	public static int length;

	public static void main(String[] args) {
		int[] data = {3, 1, 2, 7, 6};
		maxHeapSort(data);
		printArray(data);
		minHeapSort(data);
		printArray(data);
	}
	
	public Heap(int[] data) {
		this.data = data;
	}
	
	public Heap() {
		 this.length=20;
		 this.data=new int[length];
		 Random random=new Random(System.currentTimeMillis());
		 for(int i=0;i<length;i++){
		     data[i]=Math.abs(random.nextInt()%50);
		 }//for
	}
	
	// O(nlgn)
	public static void construcHeapMax(int[] data) {
		for (int i = (int) Math.floor(data.length/2 -1); i >= 0; i--) {
			max_heapify(data, i);
		}
	}
	
	//O(lgn)
	public static void max_heapify(int[] data, int i) {
		if (i < data.length) {
			int left = i * 2 + 1;
			int right = i * 2 + 2;
			int target = i;
			if (left < data.length && data[left] > data[i]) {
				target = left;
			}
			if (right < data.length && data[right] > data[i]) {
				target = right;
			}
			if (target != i) {
				exchange(data, i, target);
				max_heapify(data, target);
			}
		}
	}
	
	public static void min_heapify(int[] data, int i) {
		if (i < data.length) {
			int left = i * 2 + 1;
			int right = i * 2 + 2;
			int target = i;
			if (left < data.length && data[left] < data[i]) {
				target = left;
			}
			if (right < data.length && data[right] < data[i]) {
				target = right;
			}
			if (target != i) {
				exchange(data, i, target);
				min_heapify(data, target);
			}
		}
	}
	
	public static void insertMaxHeap(int[] data, int x) {
		// array to list and list to array
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < data.length; i++) {
			list.add(data[i]);
		}
		list.add(x);
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		//shift the bottom value up
		shiftUp(data, array.length -1);
	}
	
	public static int removeMaxHeap(int[] data) {
		// array to list and list to array
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < data.length; i++) {
			list.add(data[i]);
		}
		int top = list.get(0);
		list.set(0, list.get(list.size()-1));
		list.remove(list.size()-1);
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		//shift the top value down
		max_heapify(data, 0);
		return top;
	}
	
	public static void shiftUp(int[] data, int i) {
		while(i > 0 && data[i] > data[(int) Math.floor((i - 1)/2)] ) {
			exchange(data, i, (int) Math.floor((i - 1)/2));
			i = (int) Math.floor((i - 1)/2);
		}
	}
	
	public static void exchange(int[] data, int x, int y) {
		int tmp = data[x];
		data[x] = data[y];
		data[y] = tmp;
	}
	
	public static void maxHeapSort(int[] data) {
		// the bottom leaf node has no child, so we start from leaf's parent
		for (int i = (int) Math.floor(((data.length - 1) - 1) / 2); i >= 0; i--) {
			max_heapify(data, i);
		}
	}

	public static void minHeapSort(int[] data) {
		// the bottom leaf node has no child, so we start from leaf's parent
		for (int i = (int) Math.floor(((data.length - 1) - 1) / 2); i >= 0; i--) {
			min_heapify(data, i);
		}
	}
	
	public static void printArray(int[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + ", ");
		}
		System.out.println();
	}
}
