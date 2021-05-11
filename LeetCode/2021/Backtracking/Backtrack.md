## Backtracking

https://www.jianshu.com/p/3ef0e4e1114d

https://zhuanlan.zhihu.com/p/112926891


NOTE:
The `state/spot` concept of the following questions could also be used into DP idea, something like like `dp[stateI] = dp[stateI-1] + nums[i]`

backtrack + DFS:

- 39. Combination Sum 
- 40. Combination Sum II 
- 46. Permutations
- 47. Permutations II 
- 78. Subsets
- 90. Subsets II 
- 131. Palindrome Partitioning
- 132. Palindrome Partitioning II 
- 254. Factor Combinations
- 93. Restore IP Addresses  
- 320. Generalized Abbreviation
- 784. Letter Case Permutation

backtrack + BFS:

- 127. Word Ladder --> since String is final, no need to reset here, it's more like a pure BFS solution


### Default

Difference between Recursion and Backtracking:
- In recursion, the function calls itself, until it reaches a `base case`. 
- In backtracking, we use recursion to explore all the possibilities, until we get the best result for the problem
  - usually used for 2D-array-like problem:
    - the final solution consists of multiple `spots/levels/state` (you can think about `spot` as the current value we can append to `path`)
      - e.g. 78. Subsets
        - `spot/level` is the # of values in each set
      - e.g. 46. Permutations
        - `level` points to a unique `path.toString()`
      - for each `spot/level/state`, there would be multiple `options` in searching space
      - each `option` can be selected when it meets `searching conditions`
        - e.g. left parentheses is less than total available parentheses
        - e.g. the option is not visited before
    - after one `option` is executed, call recusive function (with void return type, and updated `search conditions`) to move to next `spot/level/state`
      - do pruning if possible before moving to next `spot/level/state`
    - need to remove the effect of current `option` in `path` and `searching conditions` before moving to next `option`
    - there is a `terminationCondition` when a solution meet target
      - e.g. path length equals to expected string length

Algorithm Pseudocode: 

```java
List<List> result; // global variable
void backtrack(int[] nums, int stateIndex, List<Integer> path, boolean[] visited) {
  if (path meets terminationCondition) {
    result.add(new LinkedList<>(path)); // deep copy!!!
  }
  // try all options in current level
  for (int i = stateIndex; i < nums.length; i++) {
    // try if meets searchingCondition
    if (!visited[i]) {
      // 1. try nums[i]
      path.add(nums[i]);
      visited[i] = true; 
      // 2. TODO prune if possible
      // 3. move to next level, updaet levelIndex
      backtrack(nums, stateIndex + 1, path);
      // 4. reset the effect of current option, then try other options
      path.remove(path.size() - 1);
      visited[i] = false;
    }
  }
}
```
NOTE1:

- `stateIndex` tells where we are now
  - this can be combined with `path`, like to use length of `path` to tell which `spot/state` we are working on
    - e.g. it could be the index in a map-like search space
- `for (int i = stateIndex; i < nums.length; i++)` is to try all options at `stateIndex`, it may not be a for loop sometimes
- `visited/searchingCondition` tells available and valid `options` for this `state/spot`
  - e.g. it could be a visited array
  - e.g. it could be the number of left parantheses in current status 
- `terminationCondition` tells when can we add path to final result list
  - deep copy of `path` could be required in questions like "78. Subsets"
    - e.g. path lenght meet target length
    - e.g. found exit of maze
- `prune` can speed up the searching
  - e.g. if the sum of subarray is already larger than target, no need to do this 
- `reset` would back out the effect of current `option` in `path`, sometimes we also need to back out the `status` or `searching conditions`


Example:
1. options
 － for "22. Generate Parentheses", options are "(" or ")"
2. restraints 
 - for "22. Generate Parentheses", when "(" is less than allowed num, can try "("; when "(" is more than ")", can try ")" 
3. termination condition
 - for "22. Generate Parentheses", when # of parenthese reaches 2 * n

#### [LC] 17. Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/

It can also be done by recursion + hashmap. But backtracking is faster than pure recursion.
Remember to use `StringBuilder` to remove character by `currentSolution.deleteCharAt(currentSolution.length() - 1);`.

#### [LC] 22. Generate Parentheses
https://leetcode.com/problems/generate-parentheses/submissions/

#### [LC] 37. Sudoku Solver
https://leetcode.com/problems/sudoku-solver/

Be careful the rowNums, colNums, sectionNums need to be intialized no matter it has filled number or not.

For the output board (which is also input), we cannot reassign board, but can only replace board element in-place.

Convert int to char `char c = (char) (i + 48);`

To split num by digits
```java
    public int getRow(int spot) {
        return (int)(spot / 10) - 1;
    }
            
    public int getCol(int spot) {
        return spot % 10 - 1;
    }
```

#### [LC] 46. Permutations
https://leetcode.com/problems/permutations/

- think about the problem in this way:
- there are different `state` for the `path` (eahc `state` points to a "path.toString()")
  - e.g. the followings are all different states
    - [1]
    - [2]
    - [3]
    - [1, 2]
    - [1,3]
    - ...
- here we use `boolean[] visited` to replace `stateIndex`
- for each state, there are be options 1, 2, 3
  - the option is eligible if `visited` is false
- when resetting, we reset the `path` and `visited`

#### [LC] 47. Permutations II
https://leetcode.com/problems/permutations-ii/

Similiar like `46. Permutations`, but we need extra 2 steps here:  
1. sort the array first, which is to help put duplicate num together
2. not only checking `visited`, we need to check if `nums[i] == prev` at this `state`, if yes the skip this `nums[i]`
  - we need to make sure at each `state` points to a unique `path.toString()`, the same `nums[i]` is only used once
 
#### [LC] 51. N-Queens
https://leetcode.com/problems/n-queens/

Be careful that string is immutable, using StringBuilder to make it mutable.

When checking the diagonal, it has 4 scenarios `row--, col--`, `row++,col++`, `row--,col++`, `row++,col--`.

#### [LC] 78. Subsets
https://leetcode.com/problems/subsets/

This is a special/variant backtrack problem. 

- `spot/state` here is the current value we can append to `path`
  - e.g. if `path` is `[1]`, then the next spot is to append 2 or 3 to the path 
  - `spot/state` is the # of values in each set
- the initial `spot/state` is an empty set with no num at all 

#### [LC] 90. Subsets II
https://leetcode.com/problems/subsets-ii/

This is a followup of "78. Subsets", since it could have duplicate now, we need to do 2 more things than "78. Subsets"

1. put duplicate nums together -- we can sort the array to arrange duplicate nums together
2. skip the backtrack function call with the same `startIndex` and the same `path`

#### [LC] 282. Expression Add Operators
https://leetcode.com/problems/expression-add-operators/

For `123`, try all options for `+`, `-` and `*` like `1+2+3`.  
- backtracking to try all options
- the termination condition to put string into global result list is to validate the operation
  - the validation is the same way like `227 Basic Calculator II `
    - traverse string from left to right, if number, add to currentNumber variable, if next is “+” or “-“, push to stack.
    - , find next number, push to stack (if it is “-“ before it, push -1*num to stack).
    - continue, find “*” or “/“, pop the previous number, calculate it, and push the calculated result into stack.
    - finally clean up the stack by adding up all the rest number there

NOTE:
- when validate the operation, we can skip numbers like "05"

#### [LC] 301. Remove Invalid Parentheses
https://leetcode.com/problems/remove-invalid-parentheses/

1. find num of invalid left and right parenthesis
2. do backtrack for all char in string, try to remove "(" when left>0 (or remove ")" when right>0)
3. inside backtrack method, if `left==0 && right==0 && isValid(s)`, add the string to result set
  - be careful the result could be duplicated, so we can use HashSet to dedup (an improvement here is to try only removing the last bracket once for consecutive ones)
  - to remove a char in string, use `s.substring(0,i) + s.substring(i+1, s.length())`

#### [LC] 399. Evaluate Division
https://leetcode.com/problems/evaluate-division/

Convert it to be a problem like this:  
- given two nodes, we are asked to check if there exists a path between them. If so, we should return the cumulative products along the path as the result

NOTE:
- we can use nested map to represent the graph, intead of insisting using 2D array


#### [LC][Hard] 691. Stickers to Spell Word
https://leetcode.com/problems/stickers-to-spell-word/

This is not as hard as I thought, don't be panic.   
We can use backtrack idea here:  
- for each sticker, try to use it on the target str (target str would be shorter in recursive function)
  - if the `target.charAt(0)` can by used by this sticker, then remove all chracters in target str that can match target str
  - generate a new target str after leverage the sticker, recursively check this new string
- can use memorization to record substring sticker count

#### [LC] 784. Letter Case Permutation
https://leetcode.com/problems/letter-case-permutation/

Similar like "78. Subsets", but be careful this time to move state, it is not `state+1` in backtrack function but the `i+1` since `i` is the current letter position.

Be familiar with java `Character` funtions like
- isUpperCase()
- isLowerCase()
- toUpperCase()
- toLowerCase()
- isLetter()
- isDigit()