import java.util.*;

public class LetterCombination {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(letterCombinations("23").toString());
	}
    public static List<String> letterCombinations(String digits) {
        if (digits == null) {
            return null;
        }
        String newDigits = digits.replace("1",""); // NOTE!!
        if (newDigits.isEmpty()) {
            return new ArrayList<String>();
        }
        
        Map<Character, String> map = new HashMap<Character, String>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        
        char[] array = newDigits.toCharArray();
        return getLetter(array, array.length -1, map);
    }
    
    public static List<String> getLetter(char[] array, int i, Map<Character, String> map) {
    	List<String> result = new ArrayList<String>();
        char c = array[i];
        String charSetStr = map.get(c);
        if (charSetStr == null) {
            return null;
        }
        char[] charSet = charSetStr.toCharArray();
        if (i == 0) {
            for (int j = 0; j < charSet.length; j++) {
            	result.add(charSet[j] + "");
            }
        } else {
        	List<String> preResults = getLetter(array, i-1, map);
        	if (preResults != null) {
                for (String preResult : preResults) {
                    for (int j = 0; j < charSet.length; j++) {
                    	result.add(preResult + charSet[j]); // NOTE!!
                    }
                }
        	}
        }
        return result;
    }
}
