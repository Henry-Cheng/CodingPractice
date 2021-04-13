// https://leetcode.com/problems/word-ladder/
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // treat each char could have options to be 26 letters
        // from begin to end
        Deque<String> fromQueue = new LinkedList<>();
        fromQueue.offer(beginWord); // entry point
        // from end to begin
        // Deque<String> toQueue = new LinkedList<>();
        // toQueue.offer(endWord); // entry point
        
        // pattern (a*z) --> words (abz, acz, adz, ...)
        HashMap<String, HashSet<String>> transitionMap = new HashMap<>();
        generateTransitionMap(wordList, transitionMap);
        HashSet<String> visited = new HashSet<>();

        int level = 1;
        boolean found = false;
        while(!fromQueue.isEmpty()) {
            int fromSize = fromQueue.size();
            
            for (int i = 0; i < fromSize; i++) {
                String word = fromQueue.poll();
                //System.out.println("check " + word);
                if (word.equals(endWord)) {
                    found = true;
                    break;
                }
                visited.add(word); // add to visited map
                // try all options for the word
                for (int j = 0; j < word.length(); j++) {
                    StringBuilder sb = new StringBuilder();
                    if (j > 0) { // prefix
                        sb.append(word.substring(0, j));
                    }
                    sb.append("*");
                    if (j < word.length() - 1) { // postfix
                        sb.append(word.substring(j + 1, word.length()));
                    }
                    String wordPattern = sb.toString();
                    
                    if (transitionMap.containsKey(wordPattern)) {
                        for (String option : transitionMap.get(wordPattern)) {
                            if (!visited.contains(option)) {
                                fromQueue.offer(option);
                                // reset --> no need since we final String
                            }
                        }
                    }
                    
                }
            }
            if (found) {
                break;
            }
            level++;
        }
        return found ? level : 0;
    }
    
    private void generateTransitionMap(List<String> wordList, HashMap<String, HashSet<String>> transitionMap) {
        for (String word : wordList) {
            for (int j = 0; j < word.length(); j++) {
                StringBuilder sb = new StringBuilder();
                if (j > 0) { // prefix
                    sb.append(word.substring(0, j));
                }
                sb.append("*");
                if (j < word.length() - 1) { // postfix
                    sb.append(word.substring(j + 1, word.length()));
                }
                String wordPattern = sb.toString();
                HashSet<String> set = transitionMap.getOrDefault(wordPattern, new HashSet<>());
                set.add(word);
                transitionMap.put(wordPattern, set);
            }
        }
    }
}
