// https://leetcode.com/problems/subsets-ii/
class Solution {
    private List<List<Integer>> result;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        result = new LinkedList<>();
        Arrays.sort(nums); // must sort first so that the same num can be put together
        backtrack(nums, 0, new LinkedList<>());
        return result;
    }
    /**
    index: 0 1 2
    nums:  1 2 2
    
    f(0,[]) {
        add([]);
        start=0
        i = 0, nums[i] = 1, path=[1]
        f(i+1=1, [1]) {
            add([1])
            start=1
            i = 1, nums[1] = 2, path=[1,2]
            f(i+1=2, [1,2]) {
                add([1,2])
                start=2
                i = 2, nums[i] = 2, path=[1,2,2]
                f(i+1=3, [1,2,2]) {
                    add([1,2,2])
                    start=3
                    end
                }
            }
            i = 2, nums[2] = 2, skip
        }
        i = 1, nums[i] = 2, path=[2]
        f(i+1=2, [2]) {
            add([2])
            start=2
            i = 2, nums[i]=2, path=[2,2]
            f(i+1=3,[2,2]) {
                add([2,2])
                start=3
                end
            }
        }
        i = 2, nums[i] = 2, skip
    }
    **/

    
    private void backtrack(int[] nums, int start, List<Integer> path) {
        result.add(new LinkedList<>(path)); // deep copy!!
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) {
                continue; // do not call backtrack with same start and same path
            }
            path.add(nums[i]); // add this num
            backtrack(nums, i+1, path);
            path.remove(path.size() - 1); // reset the path
        }
    }
}