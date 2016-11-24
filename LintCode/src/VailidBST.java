
  
public class VailidBST {
    /**
     * @param root: The root of binary tree.
     * @return: True if the binary tree is BST, or false
     */
    public boolean isValidBST(TreeNode root) {
        // write your code here
        return validate(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }
    public boolean validate(TreeNode root, int upper, int lower) {
        if (root == null) {
            return true;
        }
        boolean isRootValid = true;
        if (root.val > upper || root.val < lower) {
            return false;
        }
        boolean isLeftValid = true;
        if (root.left != null) {
            if (root.left.val < root.val) {
                isLeftValid = validate(root.left, root.val, lower);
            } else {
                return false;
            }
        }
        boolean isRightValid = true;
        if (root.right != null) {
            if (root.right.val > root.val) {
                isRightValid = validate(root.right, upper, root.val);
            } else {
                return false;
            }
        }
        return isRootValid && isLeftValid && isRightValid;
    }
}