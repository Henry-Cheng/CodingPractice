// https://leetcode.com/problems/alien-dictionary/
class Solution {
    public String alienOrder(String[] words) {
        if (words.length == 1) {
            return words[0]; // cannot tell any order by one word
        }
        // build map
        HashMap<Character, Integer> ingressCount = new HashMap<>();
        int[][] egressEgdes = new int[26][26];
        for (int i = 0; i < words.length - 1; i++) {
            String from = words[i];
            String to = words[i + 1];
            if (from.length() > to.length() && from.startsWith(to)) {
                return "";
            }
            //System.out.println("compare " + from + " and " + to);
            int j = 0;
            boolean foundPair = false;
            for (; j < Math.min(from.length(), to.length()); j++) {
                ingressCount.put(from.charAt(j), ingressCount.getOrDefault(from.charAt(j), 0));
                ingressCount.put(to.charAt(j), ingressCount.getOrDefault(to.charAt(j), 0));
                if (from.charAt(j) != to.charAt(j)) {
                    if (egressEgdes[from.charAt(j) - 'a'][to.charAt(j) - 'a'] == 0) { // only add new edge, ignore already found edges
                        //System.out.println("found " + from.charAt(j) + " --> " + to.charAt(j));
                        egressEgdes[from.charAt(j) - 'a'][to.charAt(j) - 'a']++;
                        ingressCount.put(to.charAt(j), ingressCount.getOrDefault(to.charAt(j), 0) + 1);
                    }  
                    break; // stop here after finding first pair of different chat
                }
            }
            if (j < from.length()) { // from is not completed yet
                for (int k = j; k < from.length(); k++) {
                    ingressCount.put(from.charAt(k), ingressCount.getOrDefault(from.charAt(k), 0));
                }
            }
            if (j < to.length()) {// to is not completed yet
                for (int k = j; k < to.length(); k++) {
                    ingressCount.put(to.charAt(k), ingressCount.getOrDefault(to.charAt(k), 0));
                }
            }
        }
 
        // topological sort!!!
        //System.out.println("ingressCount is " + ingressCount);
        //print2DArray(egressEgdes);
        // find out all ingree-0 nodes
        List<Character> zeroIngressList = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : ingressCount.entrySet()) {
            if (entry.getValue() == 0) {
                zeroIngressList.add(entry.getKey());
            }
        }
        
        for (int i = 0; i < zeroIngressList.size(); i++) {
            char fromChar = zeroIngressList.get(i);
            int fromIndex = fromChar - 'a';
            for (int toIndex = 0; toIndex < egressEgdes[fromIndex].length; toIndex++) {
                char toChar = (char)(toIndex + 'a');
                if (egressEgdes[fromIndex][toIndex] > 0) {
                    ingressCount.put(toChar, ingressCount.get(toChar) - 1);
                    if (ingressCount.get(toChar) == 0) {
                        zeroIngressList.add(toChar); // adding zeroIngressList in loop
                    }
                    egressEgdes[fromIndex][toIndex]--;
                }
            }
        }
        if (zeroIngressList.size() == ingressCount.size()) {
            // no cycle
            return zeroIngressList.stream().map(e -> e.toString()).collect(Collectors.joining());
        } else {
            // found cycle
            return "";
        }
    }
    
    private void print2DArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] > 0) {
                    System.out.println((char)(i + 'a') + "-->" + (char)(j + 'a'));
                }
            }
        }
    }
    
    // LC solution
    public String alienOrder_LC(String[] words) {
    
        // Step 0: Create data structures and find all unique letters.
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c).equals(0)) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next).equals(0)) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }
}

