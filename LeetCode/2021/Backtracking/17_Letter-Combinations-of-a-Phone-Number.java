// https://leetcode.com/problems/letter-combinations-of-a-phone-number/solution/
class Solution {
    // example
    // 2 -- a b c
    // 3 -- d e f
    // 4 -- g h i
    // ...
    // 7 -- p q r s
    // 8 -- t u v
    // 9 -- w x y z
    
    // backtracking way
    // global variables
    private List<String> globalSolution = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        // If the input is empty, immediately return an empty answer array
        if (digits.length() == 0) {
            return globalSolution;
        }
        
        char[][] board = {
            {'a', 'b', 'c', '0'},
            {'d', 'e', 'f', '0'},
            {'g', 'h', 'i', '0'},
            {'j', 'k', 'l', '0'},
            {'m', 'n', 'o', '0'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v', '0'},
            {'w', 'x', 'y', 'z'}
        };
        char[] input = digits.toCharArray(); // 2 3 4
        StringBuilder currentSolution = new StringBuilder();
        // start at level 0
        backTrack(board, input, 0, currentSolution);
        return globalSolution;
    }
    
    // define void recursive function
    public void backTrack(char[][] board, char[] input, int level, StringBuilder currentSolution) {
        // termination condition
        if (currentSolution.length() == input.length) {
            globalSolution.add(currentSolution.toString());
            return;
        }
        
        // process the current level
        char currentLevelNum = input[level];
        char[] currentLevelOptions = board[currentLevelNum - 50]; // '2' - 48 -2 = 0
        // traverse each option
        for (char option : currentLevelOptions) {
            if (option != '0') {
                // process this option
                currentSolution.append(option);
                // recursion function called for level + 1
                backTrack(board, input, level + 1, currentSolution);
                // prune effect of current level
                currentSolution.deleteCharAt(currentSolution.length() - 1);
            }
        }
    }
    
    // recursion way
    public List<String> letterCombinationsRecursion(String digits) {
        List<String> result = new LinkedList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        // prepare phone keyboard mapping
        // 2 -- a b c
        // 3 -- d e f
        // 4 -- g h i
        // ...
        // 7 -- p q r s
        // 8 -- t u v
        // 9 -- w x y z
        //
        // 2 3 4
        // a + combinations of 3 4
        //     d + combinations of 4
        //     e + combinations of 4
        //     f + combinations of 4
        // b + combinations of 3 4
        //     d + combinations of 4
        //     e + combinations of 4
        //     f + combinations of 4
        // c + combinations of 3 4
        //     d + combinations of 4
        //     e + combinations of 4
        //     f + combinations of 4
        char[][] board = {
            {'a', 'b', 'c', '0'},
            {'d', 'e', 'f', '0'},
            {'g', 'h', 'i', '0'},
            {'j', 'k', 'l', '0'},
            {'m', 'n', 'o', '0'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v', '0'},
            {'w', 'x', 'y', 'z'}
        };
        char[] input = digits.toCharArray(); // 2 3 4
        Map<String, List<String>> cache = new HashMap<>();
        return combination(board, digits, cache);
    }
    
    public List<String> combination(char[][] board, String subString, Map<String, List<String>> cache) {
        // check in cache
        List<String> cacheResult = cache.get(subString);
        if (cacheResult != null) {
            return cacheResult;
        }
        // termination status
        List<String> result = new LinkedList<>();
        if (subString.length() == 1) {
            char[] array = board[subString.charAt(0) - 50];
            for (char c : array) {
                if (c == '0') {
                    continue;
                }
                result.add(c + "");
            }
            // store in cache
            cache.put(subString, result);
            // return result
            return result;
        }
        // use recursion
        List<String> nextResult = combination(board, subString.substring(1, subString.length()), cache);
        // work at current recursion level
        char[] array = board[subString.charAt(0) - 50];
        for (char c : array) {
            if (c == '0') {
                continue;
            }
            for (String s : nextResult) {
                result.add(c + s);
            }
        }
        // store in cache
        cache.put(subString, result);
        // return result
        return result;
    }

}