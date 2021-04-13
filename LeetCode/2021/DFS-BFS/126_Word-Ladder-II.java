// https://leetcode.com/problems/word-ladder-ii/
public class Solution {
    List<List<String>> result;

    public List<List<String>> findLadders(String start, String end, List<String> words) {
        result = new ArrayList<>();
        HashMap<String, HashSet<String>> graph = new HashMap<>();
        
        //int level = constructGraph(start, end, graph, new HashMap<>(), new HashSet<>(words));
        
        int level = bfs(start, end, graph, new HashMap<>(), new HashSet<>(words));

        // to print paths do DFS from start till level level.getOrDefault(end, 0)
        if(level > 0) {
            //dfs(graph, level, end, start, 1, new LinkedList<>(), new HashMap<>());
            
            // start with all entrypoints
            List<String> path = new LinkedList<>();
            path.add(start);
            HashSet<String> visited = new HashSet<>();
            visited.add(start);
            dfs(start, end, level, 1, graph, path, visited, new HashMap<>());
        }
        return result;
    }

    // DFS with memory
    private boolean dfs(String beginWord, String endWord, int minLevel, int currentLevel, HashMap<String, HashSet<String>> adjacenceMap, List<String> path, HashSet<String> visited, HashMap<String, Boolean> memory) {
        if (path.size() == minLevel) { // termination condition
            if (beginWord.equals(endWord)) {
                result.add(new LinkedList<>(path));
                return true;
            }
            return false;
        }
        // try all options
        boolean foundByBeginWord = false;
        if (adjacenceMap.containsKey(beginWord)) {
            for (String nextWord : adjacenceMap.get(beginWord)) {
                
                if (!visited.contains(nextWord) && memory.getOrDefault(nextWord, true)) {
                    
                    // try nextWord
                    path.add(nextWord);
                    visited.add(nextWord);
                    
                    boolean foundPath = dfs(nextWord, endWord, minLevel, currentLevel+1, adjacenceMap, path, visited, memory);
                    
                    memory.put(nextWord, foundPath);
                    foundByBeginWord = foundByBeginWord | foundPath;
                    
                    // reset nextWord
                    path.remove(path.size() - 1);
                    visited.remove(nextWord);
                }
            }
        } 
        memory.put(beginWord, foundByBeginWord);
        return foundByBeginWord;
    }
    
//     private boolean dfs(HashMap<String, HashSet<String>> graph, int maxLevel, String end, String start, int currLevel, LinkedList<String> paths, HashMap<String, Boolean> memory) {
//         paths.add(start);
//         if (currLevel == maxLevel) {
//             if (start.equals(end)) {
//                 result.add(new ArrayList<>(paths));
//                 paths.removeLast();
//                 return true;
//             }
//             paths.removeLast();
//             return false;
//         }

//         boolean ans = false;
//         for (String node : graph.get(start)) {
//             if(!memory.containsKey(node)) {
//                 boolean b = dfs(graph, maxLevel, end, node, currLevel + 1, paths, memory);
//                 ans = ans || b;
//                 memory.put(node, b);
//             } else if(memory.get(node)) {
//                 ans = true;
//                 dfs(graph, maxLevel, end, node, currLevel + 1, paths, memory);
//             }
//         }
//         paths.removeLast();
//         memory.put(start, ans);
//         return memory.get(start);
//     }

    private int bfs(String beginWord, String endWord, HashMap<String, HashSet<String>> forDFS, HashMap<String, Integer> levelRecorder, HashSet<String> dict) {
        int level = 1;
        Deque<String> queue = new LinkedList<>();
        queue.offer(beginWord); // entry point
        levelRecorder.put(beginWord, level); // record the 1st time level you see the word
        
        boolean found = false;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                // prepare forDFS map: word --> set of next available words
                HashSet<String> nextAvailable = forDFS.getOrDefault(word, new HashSet<>());
                //System.out.println("check " + word);
                if (word.equals(endWord)) {
                    found = true; 
                    continue; // finish all valid endWord at this level
                }

                // try all options for the word
                char[] wordArr = word.toCharArray();
                for (int j = 0; j < wordArr.length; j++) {
                    char tmp = wordArr[j];
                    for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                        wordArr[j] = newChar;
                        String option = new String(wordArr);

                        if (dict.contains(option)) {
                            // record the shortest level
                            Integer optionLevel = levelRecorder.get(option);
                            if (optionLevel == null) { //1st time see the option, enqueue, add to levelRecorder
                                queue.offer(option); // enque
                                
                                nextAvailable.add(option);
                                levelRecorder.put(option, level + 1);
                            } else { // seen option before, no need to enqueue
                                if (optionLevel > level) { // find a shorter path to reach "option"
                                    nextAvailable.add(option);
                                    levelRecorder.put(option, level + 1);
                                }
                            }
                        }
                    }
                    // reset the wordArr before trying other options
                    wordArr[j] = tmp;
                }
                // put level+word --> next level words mapping
                forDFS.put(word, nextAvailable);
            }
            
            if (found) {
                break;
            }
            level++;
        }
        return found ? level : 0;
    }



//     private int constructGraph(String start, String end, HashMap<String, HashSet<String>> graph, HashMap<String, Integer> levelHelper, HashSet<String> dict) {
//         // BFS for making graph
//         Queue<String> queue = new LinkedList<>();
        
//         queue.add(start);
//         levelHelper.put(start, 1);
        
//         int level = 0;
//         boolean found = false;
//         while (!queue.isEmpty() && !found) {
//             level++;
//             for (int size = queue.size(); size > 0; size--) {
//                 String s = queue.remove();
//                 if (s.equals(end)) {
//                     found = true;
//                     break;
//                 }
//                 HashSet<String> nextSet = graph.getOrDefault(s, new HashSet<>());

//                 char[] ch = s.toCharArray();
//                 for(int i = 0; i < ch.length; i++) {
//                     char t = ch[i];
//                     for(char j = 'a'; j <= 'z'; j++) {
//                         if(ch[i] == j) continue;
//                         ch[i] = j;
//                         String option = String.valueOf(ch);
//                         if(dict.contains(option)) {
//                             Integer optionLevel = levelHelper.get(option);
//                             if (optionLevel == null) { // 1st time see it
//                                 levelHelper.put(option, level + 1);
//                                 queue.offer(option); // enqueue
//                                 nextSet.add(option);
//                             } else if (optionLevel > level) { // find a shorter path
//                                 nextSet.add(option);
//                             } else { // optionLevel <= level
//                                 // do nothing
//                             }
//                         }
//                     }
//                     ch[i] = t;
//                 }
//                 graph.put(s, nextSet);
//             }
//         }
//         return found ? level : 0;
//     }
}