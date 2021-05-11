## Trie/Prefix Tree/Digital Tree

Trie is used in following scenarios:
1. Autocomplete
2. Spell checker
3. IP routing
4. T9 keyboard predictive text
5. Sovling word games (like `212. word search II`)

The time complexity is `O(m)`, in which `m` means the length of the word.

Comparison with HashTable
- HashTable is not ideal when all searching keys have common prefix
- HashTable may have confliction, which also result in O(n)

### Default
https://en.wikipedia.org/wiki/Trie

[LC] 208. Implement Trie
[LC] 211. Design Add and Search Words Data Structure
[LC] 212. Word Search II
[LC] 425. Word Square
[LC] 642. Design Search Autocomplete System
[LC] 676. Implement Magic Dictionary
[LC] 745. Prefix and Suffix Search
[LC] 1032. Stream of Characters
[LC] 1233. Remove Sub-Folders from the Filesystem
[LC] 421. Maximum XOR of Two Numbers in an Array
[LC] 1707. Maximum XOR With an Element From Array

```java
// each TrieNode would have 26 child nodes, TrieNode itself is empty, it only makes sense when being used to check child links (check if links array has the index)
class TrieNode {

    // R links to node children
    private TrieNode[] links;

    private final int R = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}
```

```java
class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
           char curLetter = word.charAt(i);
           if (node.containsKey(curLetter)) {
               node = node.get(curLetter);
           } else {
               return null;
           }
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node != null && node.isEnd();
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
```

#### [LC] 211. Design Add and Search Words Data Structure
https://leetcode.com/problems/design-add-and-search-words-data-structure/

Since it supports "." to match any character, we can make search() a recursive function to try every trie.children when seeing "."

- Time complexity: `O(M)` for the "well-defined" words without dots, where M is the key length, and N is a number of keys, and `O(N*26 ^ M)` for the "undefined" words. 
- Space complexity: `O(1)` for the search of "well-defined" words without dots, and up to `O(M)` for the "undefined" words, to keep the recursion stack.

#### [LC] 212. Word Search II
https://leetcode.com/problems/word-search-ii/

NOTE:
1. Using hashmap to simplify the trie build
2. when building trie
  - don't forget to move node to its child for next letter in word
  - don't forget to set the word in `TrieNode` when find complete word
3. stll using the backtracking template: start with all entry points, using visited array to record visited node, remember to reset the visited array, remember to try all 4 directions
4. using hashset to maintain global solution, since there could be duplicate words
5. in backtracking method, do not return immediately after finding the word, since we could search further sometimes, e.g. `dict = {oa, oaa} `

```
    private static class Trie{
        public HashMap<Character,Trie> children = new HashMap<>();
        public String word = null;
        public Trie() {}
    }
```

#### [LC] 648. Replace Words
https://leetcode.com/problems/replace-words/

- use trie
- Time Complexity: O(N) where N is the length of the sentence. Every query of a word is in linear time.
- Space Complexity: O(N), the size of our trie.