// https://leetcode.com/problems/design-add-and-search-words-data-structure/
class WordDictionary {

    TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
        }
        node.word = word;
    }
    
    public boolean search(String word) {
        return searchWord(word.toCharArray(), 0, root);
    }
    
    private boolean searchWord(char[] array, int start, TrieNode root) {
        TrieNode node = root;
        for (int i = start; i < array.length; i++) {
            char c = array[i];
            if (c == '.') { // try any children
                for (char dictC : node.children.keySet()) {
                    if (searchWord(array, i+1, node.children.get(dictC))) {
                        return true;
                    }
                }
                return false;
            } else {
                if (node.children.containsKey(c)) {
                    node = node.children.get(c);
                } else {
                    return false;
                }
            }
        }
        return node.word != null;
    }
    
    private static class TrieNode {
        public HashMap<Character, TrieNode> children;
        public String word;
        public TrieNode() {
            this.children = new HashMap<>();
        }
    }
}


/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */