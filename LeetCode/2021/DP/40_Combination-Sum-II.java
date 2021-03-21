// https://leetcode.com/problems/combination-sum-ii/submissions/
class Solution {
    HashSet<List<Integer>>[][] dp;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // sort candidates
        Arrays.sort(candidates);
        // candidates = 1 2 3 4 5, target = 5
        // (12345,5) = include 1 --> (2345, 4) + exclude 1 --> (2345,5)
        dp = new HashSet[candidates.length + 1][target + 1];
        return recursion(candidates, 0, target).stream().collect(Collectors.toList());
    }
    
    public HashSet<List<Integer>> recursion(int[] candidates, int i, int target) {
        HashSet<List<Integer>> result = new HashSet<>();
        // base case
        if (i >= candidates.length || target < candidates[i] || target < 0) {
            return result;
        } else if (target == candidates[i]) {
            List<Integer> combo = new LinkedList<>();
            combo.add(target);
            result.add(combo);
            dp[i][target] = deepCopy(result);
            return result;
        }
        if (dp[i][target] != null) {
            return dp[i][target];
        }
        int fixNum = candidates[i];
        HashSet<List<Integer>> includeResult = recursion(candidates, i + 1, target - fixNum);
        if (includeResult.size() > 0) {
            for (List<Integer> combo : includeResult) {
                combo.add(fixNum);
            }
            result.addAll(includeResult);
        }
        HashSet<List<Integer>> excludeResult = recursion(candidates, i + 1, target);
        if (excludeResult.size() > 0) {
            result.addAll(excludeResult);
        }
        //System.out.println("i: " + i + ", target: " + target + ", result " + result.toString());
        dp[i][target] = deepCopy(result);
        return result;
    }
    
    protected HashSet<List<Integer>> deepCopy(HashSet<List<Integer>> source) {
        HashSet<List<Integer>> copy = new HashSet<>();
        for(List<Integer> combo : source) {
            copy.add(new LinkedList<>(combo));
        }
        return copy;
    }
}