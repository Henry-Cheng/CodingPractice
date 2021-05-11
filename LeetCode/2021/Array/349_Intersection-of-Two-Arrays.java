// https://leetcode.com/problems/intersection-of-two-arrays/
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet(Arrays.stream(nums1).boxed().collect(Collectors.toList()));
        HashSet<Integer> set2 = new HashSet(Arrays.stream(nums2).boxed().collect(Collectors.toList()));
        List<Integer> result = new LinkedList<>();
        for(Integer num : set1) {
            if (set2.contains(num)) {
                result.add(num);
            }
        }
        return result.stream().mapToInt(i->i).toArray();
    }
}