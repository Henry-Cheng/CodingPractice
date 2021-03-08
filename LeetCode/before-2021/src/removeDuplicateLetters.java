import java.util.*;

public class removeDuplicateLetters {
	public static void main(String[] args) {
		System.out.println(removeDuplicateLetters("cbacdcbc"));//cbacdcbc
		System.out.println(removeDuplicateLettersByStack("cbacdcbc"));//cbacdcbc
	}
    public static String removeDuplicateLettersByStack(String string) {
        int[] count = new int[26]; //will contain number of occurences of character (i+'a')
        boolean[] visited = new boolean[26]; //will contain if character (i+'a') is present in current result Stack
        char[] array = string.toCharArray();
        for(char c: array){  //count number of occurences of character 
        	count[c-'a']++;
        }
        Stack<Character> stack = new Stack<>(); // answer stack
        int index;
        for(char c:array){ 
            index= c-'a';
            count[index]--;   //decrement number of characters remaining in the string to be analysed
            if(visited[index]) //if character is already present in stack, dont bother
                continue;
            //if current character is smaller than last character in stack which occurs later in the string again
            //it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
            while(!stack.isEmpty() && c<stack.peek() && count[stack.peek()-'a']!=0){ 
                visited[stack.pop()-'a']=false;
            }
            stack.push(c); //add current character and mark it as visited
            visited[index]=true;
        }

        StringBuilder sb = new StringBuilder();
        //pop character from stack and build answer string from back
        while(!stack.isEmpty()){
            sb.insert(0,stack.pop());
        }
        return sb.toString();
    }
    
    public static String removeDuplicateLetters(String s) {
        if (s.isEmpty()) {
            return s;
        }
        List<Character> result = new ArrayList<Character>(); // result
        Map<Character, Boolean> selectedMap = new HashMap<Character, Boolean>(); // record which char has been selected 
        // traverse all chars to get the counting
        for (int i = 0; i < s.length(); i++) {
        	selectedMap.put(s.charAt(i), false);
        }
        recursion(s, selectedMap, result);
        StringBuilder builder = new StringBuilder();
        for (Character c : result) {
            builder.append(c);
        }
        return builder.toString();
    }
    
    public static void recursion(String s, Map<Character, Boolean> selectedMap, List<Character> result) {
    	// NOTE: we need to re-count chars everytime we shorten the string
        Map<Character, Integer> countMap = new HashMap<Character, Integer>(); // count occurence of all chars
        // traverse all chars to get the counting
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (!countMap.keySet().contains(c)) {
                countMap.put(c, 1);
            } else {
                int count = countMap.get(c) + 1;
                countMap.put(c, count);
            }
        }
    	int startPos = 0; // start pos record the smallest char index in this loop
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (c < s.charAt(startPos)) {
                startPos = i;
            }
            // update countMap
            int count = countMap.get(c) - 1;
            countMap.put(c, count);
            if (count == 0) { // if this char has no duplicate to right
                break;
            }
        }
        // select startPos as the leftMost(smallest) char and put it in list and map
        Character selectedChar = s.charAt(startPos);
        result.add(selectedChar);
        selectedMap.put(s.charAt(startPos), true);
        // recursion
        if (startPos + 1 < s.length()) { // the replace() function is used to remove all selected char
            recursion(s.substring(startPos + 1).replace("" + selectedChar, ""), selectedMap, result);
        } 
    }
}