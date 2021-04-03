// https://leetcode.com/problems/dot-product-of-two-sparse-vectors/
class SparseVector {
    public ArrayList<NonZeroNum> list = new ArrayList<>();
    SparseVector(int[] nums) {
        //System.out.println("prepare list");
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                //System.out.println("Found value " + nums[i] + " at " + i);
                list.add(new NonZeroNum(nums[i], i));
            }
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        ArrayList<NonZeroNum> list2 = vec.list;
        int index1 = 0;
        int index2 = 0;
        int sum = 0;
        while(index1 < list.size() && index2 < list2.size()) {
            NonZeroNum num1 = list.get(index1);
            NonZeroNum num2 = list2.get(index2);
            if (num1.index < num2.index) {
                index1++;
            } else if (num1.index > num2.index) {
                index2++;
            } else { // equals
                sum += num1.value * num2.value;
                index1++;
                index2++;
            }
        }
        return sum;
    }
    
    private static class NonZeroNum {
        public int value;
        public int index;
        public NonZeroNum(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}

// Your SparseVector object will be instantiated and called as such:
// SparseVector v1 = new SparseVector(nums1);
// SparseVector v2 = new SparseVector(nums2);
// int ans = v1.dotProduct(v2);