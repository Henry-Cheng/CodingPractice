// https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45551/preorder-inorder-and-postorder-iteratively-summarization

// the first set of templates are good for general DFS 
// the preOrder and postOrder are easy to remember, inOrder is a bit different
class Solution {
  public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> output = new LinkedList<>();
    Deque<TreeNode> stack = new LinkedList<>();
    
    if (root == null) return output;

    stack.push(root);
    while (!stack.isEmpty()) {
      root = stack.pop();
      output.add(root.val); // visit root
      if (root.right != null) stack.push(root.right);
      if (root.left != null) stack.push(root.left);
    }
    return output;
  }
}

public class Solution {
    public List <Integer> inorderTraversal(TreeNode root) {
        List<Integer> output = new LinkedList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            output.add(root.val); // visit node
            root = root.right;
        }
        return output;
    }
}

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> output = new LinkedList();
        Deque<TreeNode> stack = new LinkedList();
        
        if (root == null) return output;

        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            output.addFirst(root.val); // visit, reverse with preOrder
            if (root.left != null) stack.push(root.left);
            if (root.right != null) stack.push(root.right);
        }

        return output;
    }
}


// the followings are another set of templates, they are easy to remember for all 3 orders
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(!stack.isEmpty() || root != null) {
        if(root != null) {
            stack.push(root);
            result.add(root.val);  // visit before going to children
            root = root.left;
        } else {
            root = stack.pop();
            root = root.right;   
        }
    }
    return result;
}

public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(!stack.isEmpty() || root != null) {
        if(root != null) {
            stack.push(root);
            root = root.left;
        } else {
            root = stack.pop();
            result.add(root.val);  // visite after all left children
            root = root.right;   
        }
    }
    return result;
}

public List<Integer> postorderTraversal(TreeNode root) {
    // define result
    Deque<Integer> result = new LinkedList<>();
    // define a stack
    Deque<TreeNode> stack = new LinkedList<>();
    while(!stack.isEmpty() || root != null) {
        if (root != null) {
            stack.push(root);
            // visit root
            result.addFirst(root.val); // reverse of preorder
            root = root.right; // reverse of preorder
        } else {
            root = stack.pop();
            root = root.left; // reverse of preorder
        }
    }
    return new ArrayList(Arrays.asList(result.toArray()));
}
