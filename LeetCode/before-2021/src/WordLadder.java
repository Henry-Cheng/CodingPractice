import java.util.*;

public class WordLadder {

	/*
	 * "hit", "cog", ["hot","cog","dot","dog","hit","lot","log"]
"hit", "cog", ["hot","hit","cog","dot","dog"]
"red", "tax", ["ted","tex","red","tax","tad","den","rex","pee"]
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String beginWord = "red";
		//String endWord = "tax";
		//Set<String> list = new HashSet<String>(Arrays.asList("ted","tex","red","tax","tad","den","rex","pee"));
//		String beginWord = "a";
//		String endWord = "c";
//		Set<String> list = new HashSet<String>(Arrays.asList("a","b","c"));
		String beginWord = "hot";
		String endWord = "dog";
		Set<String> list = new HashSet<String>(Arrays.asList("hot","dog","dot"));
		System.out.println(ladderLength(beginWord, endWord, list));
	}
    public static int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        if (beginWord == null || endWord == null || beginWord.length() != endWord.length() || beginWord.equals(endWord)) {
            return 0;
        }
        /*
        since one 1 word change per time, at most <number of char> times, at least <num of different char>-1 word
        i.e.  for hig -> cog
        3 times:  hig -> hit -> cit -> cot -> cog
        2 times:  hig -> cig -> cog
        */
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String word : wordList) {
            map.put(word, 0);
        }
        map.put(beginWord, 1);
        List<String> result = new ArrayList<String>();
        List<String> intermediate = new ArrayList<String>();
        intermediate.add(beginWord);
        DFS(beginWord, endWord, map, result, intermediate);
        if (result.isEmpty()) { // no chance to do result.addAll(intermediate)
        	return 0;
        } else {
            return result.size() - 1;
        }

    }
    
    public static boolean DFS(String beginWord, String endWord, Map<String, Integer>map, List<String> result, List<String> intermediate) {
        if (intermediate.get(intermediate.size()-1).equals(endWord)) { // if end condition satisfy
            return true;
        } else {
            // traverse all neighbor nodes (n*26) of beginWord
            for (int index = 0; index < beginWord.length(); index++) {
                char c = beginWord.charAt(index);
                for (int j = 0; j < 26; j++) {
                	if (j == c - 'a') {
                		continue;
                	}
                    char newC = (char) (j + 'a');
                    String newBeginWord = null;
                    if (index == 0) {
                        newBeginWord = newC + (beginWord.length()-1 > 0 ? beginWord.substring(index+1, beginWord.length()) : "");
                    } else if (index == beginWord.length()-1) {
                        newBeginWord = beginWord.substring(0, index) + newC;
                    } else {
                        newBeginWord = beginWord.substring(0, index) + newC + beginWord.substring(index+1, beginWord.length());
                    }
                    Integer search = map.get(newBeginWord);
                    if (search != null && search == 0) { // if not null or 1, continue DFS
                        map.put(newBeginWord, 1);
                        intermediate.add(newBeginWord);
                        if (DFS(newBeginWord, endWord, map, result, intermediate)) { // if found, save the result if min-length got
                            if (result.isEmpty() || result.size() > intermediate.size()) {
                            	result.clear();
                            	result.addAll(intermediate); //NOTE
                            }
                        }
                        // remove the recording of this step, in case impacting other traversings
                        map.put(newBeginWord, 0);
                        intermediate.remove(newBeginWord);
                    }
                }
            }
        }
        return false;
    }
}