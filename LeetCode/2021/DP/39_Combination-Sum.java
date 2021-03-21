// https://leetcode.com/problems/combination-sum/submissions/
class Solution {
    // DP top-down
    List<List<Integer>>[][] memory;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // sort the candidates
        Arrays.sort(candidates);
        memory = new LinkedList[candidates.length + 1][target + 1];
        return topDown(candidates, 0, target);
    }
    public List<List<Integer>> topDown(int[] candidates, int i, int target) {
        List<List<Integer>> combos = new LinkedList<>();
        if (i >= candidates.length) {
            return combos;
        }
        if (target < candidates[i]) {
            return combos;
        }
        if (memory[i][target] != null) {
            return memory[i][target];
        }
        if (target == candidates[i]) {
            List<Integer> combo = new LinkedList<>();
            combo.add(target);
            combos.add(combo);
            memory[i][target] = deepCopy(combos);
            return combos;
        }
        
        // originally (1234, 5)
        // scenario 1: include 1 --> 1 + (1234,4)
        List<List<Integer>> includeCombos = topDown(candidates, i, target - candidates[i]);
        if (includeCombos.size() > 0) {
            for (List<Integer> includeCombo : includeCombos) {
                includeCombo.add(candidates[i]);
            }
            combos.addAll(includeCombos);
        }
        // scenario 2: exclude 1 --> 1 + (234,5)
        List<List<Integer>> excludeCombos = topDown(candidates, i + 1, target);
        if (excludeCombos.size() > 0) {
            combos.addAll(excludeCombos);
        }
        memory[i][target] = deepCopy(combos);
        return combos;
    }
    
    public List<List<Integer>> deepCopy(List<List<Integer>> source) {
        List<List<Integer>> copy = new LinkedList<>();
        for (List<Integer> list : source) {
            copy.add(new LinkedList<>(list));
        }
        return copy;
    }
    public List<List<Integer>> result = new LinkedList<>();
    /**
    split target into an array
    dp[0, 1 , 2, 3, .., target - 1, target]
    dp[i] points to all the combination whose sum is i
    e.g.
    candidates = [2,3,5], target = 8
    1. solution1 to build dp array from 1 to 8
        dp[0] = [] // dummy head node
        dp[1] = [] // no combination
        dp[2] = [[2]] // (dp[1] + dp[1]), dp[2]
        dp[3] = [[3]] // (dp[1] + dp[2]), dp[3]
        dp[4] = [[2,2]] // (dp[1] + dp[3]), (dp[2] + dp[2]), dp[4]
        dp[5] = [[2,3],[5]] // dp[1] + dp[4], dp[2] + dp[3], dp[5]
        dp[6] = [[2,2,2],[3,3]] // 
        dp[7] = [[2,2,3],[2,5]] // dp[2] + dp[5], dp[3]+dp[4], has duplicate for [2,2,3]
        dp[8] = [[2,2,2,2],[2,3,3],[3,5]] // could have duplicate, dp[3]+dp[5] has duplicate with dp[2] + dp[6] for [2,3,3]
        for duplicate combination, we can use HashSet<List<Integer>> to cover it
    
    2. solution2 from LC discussion:
        instead of traverseing from 1 to 8, we can traverse from min candidate to highest candidate
    **/
    public List<List<Integer>> combinationSumBottomUp(int[] candidates, int target) {
        // sort the candidates so that we can build bottom-up
        Arrays.sort(candidates); // O(nlogn)
        // build a dp arary to record: for num i, what are the combinations, here we use hashset
        HashSet<List<Integer>>[] dp = new HashSet[target + 1];
        for (int candidate : candidates) { // O(k)
            if (candidate <= target) { 
                List<Integer> list = new LinkedList<>();
                list.add(candidate);
                dp[candidate] = new HashSet<List<Integer>>();
                dp[candidate].add(list);
            }
        }
        for (int i = 1; i < target + 1; i++) { // O(n)
            // initialize dp array if it's not initialized by candidate
            if (dp[i] == null) {
                dp[i] = new HashSet<List<Integer>>();
            }
            int left = 1;
            int right = i - 1;
            while (left <= right) {//for dp[2], left==1, right==1
                if (dp[left].size() > 0 && dp[right].size() > 0) {// found combination in both
                    // merge dp[left] and dp[right]
                    for (List<Integer> leftList : dp[left]) {
                        for (List<Integer> rightList: dp[right]) {
                            List<Integer> mergedList = new LinkedList<>(leftList);
                            mergedList.addAll(rightList);
                            Collections.sort(mergedList); // this is important to keep it ascending, and make it unique in hashset
                            dp[i].add(mergedList);// dp[i] is a hashset, it dedupes mergedList
                        }
                    }
                }
                left++;
                right--;
            }
        }
        return dp[target].stream().map(list->list).collect(Collectors.toList());
    }
    
    // LC solution
    public List<List<Integer>> combinationSumLC(int[] candidates, int target) {
        List<List<Integer>>[] dp = new List[target + 1];
        for (int i = 0; i <= target; i++)
            dp[i] = new ArrayList<>();
        dp[0].add(new ArrayList<>());
        for (int candidate: candidates) {
            for (int j = candidate; j <= target; j++) {                
                for (List<Integer> comb: dp[j - candidate]) {
                    List<Integer> newComb = new ArrayList(comb);
                    newComb.add(candidate);
                    dp[j].add(newComb);
                }                    
            }
        }
        return dp[target];
    }
    
    // search range is 0 ~ end in candidates since we can reuse
    public void dfs(int[] candidates, int start, int target, List<Integer> prevList) {
        for (int i = start; i < candidates.length; i++) {
            if (candidates[start] == target) { // found the end
                prevList.add(candidates[start]);
                result.add(prevList);
                return;
            } else if (candidates[start] < target) {
                prevList.add(candidates[start]);
                target = target - candidates[start];
            } else { // if candidates[start] > target, backtrack
                int lastNum = prevList.get(prevList.size() - 1);
                prevList.remove(prevList.size() - 1);
                dfs(candidates, start, target + lastNum, prevList);
            }
        }
    }
}