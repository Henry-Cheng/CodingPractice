// https://leetcode.com/problems/combinations/
class Solution {
    List<List<Integer>>[][] memory;
    public List<List<Integer>> combine(int n, int k) {
        // n =4, k = 3
        // candidates --> 1 2 3 4
        // combo(candidates=1234, k = 3) = 
        //      1 * combo(candidates=234, k = 2) + combo (candidates=234, k = 3);
        // think it as: if we include 1, then rest of the candidates can form 2-element-combo,
        //              if we exclude 1, then rest of the candidates can form 3-element-combo
        memory = new LinkedList[n+2][k+2];
        return recursion(n, 1, k);
    }
    
    // 1234 --> k=3
    // 1 + 234 --> k=2
    //   234 --> k=3
    public List<List<Integer>> recursion(int n, int i, int k) {
        //System.out.println("i is " + i + ", k is " + k);
        // basic case
        List<List<Integer>> result = new LinkedList<>();
        if (i > n) {
            return null;
        }
        if (k == 1) {
            for (int j = i; j <= n; j++) {
                List<Integer> list = new LinkedList<>();
                list.add(j);
                result.add(list);
            }
            // add to memory
            memory[i][k] = deepCopy(result);
            return result; 
        }
        
        // using memory before recusion
        List<List<Integer>> sameKResult;
        if (memory[i + 1][k] != null) {
            sameKResult = memory[i + 1][k];
        } else {
            sameKResult = recursion(n, i + 1, k);
        }
        // using memory before recusion
        List<List<Integer>> decrementKResult;
        if (memory[i + 1][k-1] != null) {
            decrementKResult = memory[i + 1][k-1];
        } else {
            decrementKResult = recursion(n, i + 1, k - 1);
        }
        
        if (decrementKResult != null) {
            for (List<Integer> list : decrementKResult) {
                list.add(i); // merge i into the list
            } 
            result.addAll(decrementKResult);
        }
        if (sameKResult != null) {
            result.addAll(sameKResult);
        }
        // add to memory
        memory[i][k] = deepCopy(result);
        return result;
    }
    
    public List<List<Integer>> deepCopy(List<List<Integer>> result) {
        List<List<Integer>> copy = new LinkedList<>();
        for (List<Integer> list : result) {
            copy.add(new LinkedList<>(list));
        }
        return copy;
    }
}