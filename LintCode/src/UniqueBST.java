import java.util.*;
public class UniqueBST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(numTrees(4));
	}
    /*
     * actually it is permunation of number (#) and null (n) 
     * For n = 1,2,3,4
     * ==> # # # # n n n
     * since 1st # is fixed, how many pernunation it has for # # # n n n ?
     */
    public static int numTrees(int n) {
        if (n == 1 || n == 0) {
            return n;
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int[] nums = new int[(n-1)*2];
        for (int i = 0; i < n - 1; i++) {
            nums[i] = 0; // means it is null 
        }
        for (int i = n-1; i < nums.length; i++) {
            nums[i] = 1; // means it is a number
        }
        permute(nums, result, new ArrayList<Integer>(), new boolean[nums.length]);
        int count = 0;
        for (List<Integer> list : result) {
            list.add(0, 1);
            count += deserializeTree(list) ? 1 : 0; // add 1 to starting pos of each list as root node
        }
        return count;
    }
    
    public static void permute(int[] nums, List<List<Integer>> result, List<Integer> list, boolean[] used) {
        if (list.size() == nums.length) {
            result.add(new ArrayList(list));
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) {
                continue;
            }
            used[i] = true;
            list.add(nums[i]);
            permute(nums, result, list, used);
            used[i] = false;
            list.remove(list.size() - 1);
        }
    }
    
    public static boolean isValidTree(List<Integer> list) {
        for (int i = list.size() -1; i > 0; i--) {
            if (list.get(i) == 1) {
                int parent = (int) Math.floor((i-1)/2);
                if (list.get(parent) == 0) { // if a tree node's parent is null
                    return false;
                }
            }
        }
        return true;
    }

    /*
        #   0  1  0  0  1  1
    ==>
                #
         null        1
                null   null
    this is a invalid tree
    */
    public static boolean deserializeTree(List<Integer> list) {
        // the 1st node must be 1
        TreeNode root = null;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int i = 0;
        while(!queue.isEmpty() && i < list.size()) {
            TreeNode node = queue.poll();
            if (list.get(i) == 0) {
                node = null;
            } else {
                node = new TreeNode(list.get(i));
            }
            if (node != null) {
                queue.add(node.left);
                queue.add(node.right);
            }
            i++;
        }
        if (i == list.size()) {
            return true;
        } else if (list.subList(i+1, list.size()).contains(1)) {
            // we have build the tree, let's check whether the tree uses all numbers in list
            return false;
        }
        return true;
    }
    
}
