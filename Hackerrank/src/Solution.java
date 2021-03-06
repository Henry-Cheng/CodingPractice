import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = getOnlyStrings(scanner.nextLine());
        int k = Integer.parseInt(scanner.nextLine());
        List<String> tokens = new ArrayList<String>(); 
        for (int i = 0; i < k; i++) {
            tokens.add(scanner.nextLine());
        }
        // now start
        Map<String, Integer> tokenMap = new HashMap<String, Integer>();
        for (int i = 1; i <= tokens.size(); i++) {
            tokenMap.put(tokens.get(i-1).toUpperCase(), i);
        }
        k = tokenMap.keySet().size(); // NOTE: the k tokens may have duplicate words
        
        String[] wordArray = text.split("\\s+");
        Map<Integer, String> tokenWordMap = new HashMap<Integer,String>();
        List<Integer> tokenWordPosList = new ArrayList<Integer>(); // we need a sequential list to go through the list
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < wordArray.length; i++) {
            if (tokenMap.containsKey(wordArray[i].toUpperCase())) {
                tokenWordMap.put(i, wordArray[i].toUpperCase());
                tokenWordPosList.add(i);
                set.add(wordArray[i].toUpperCase());
            }
        }
        if (set.size() == k) {
            // it means we at least have a sublist -- at least the tokenWordPosList[0] ~ tokenWordPosList[length] is a candidate one
            int i = 0;
            int j = tokenWordPosList.size() - 1;
            while(true) {
                boolean doSth = false;
                if (existInRestList(tokenWordMap, tokenWordPosList.get(i))) {
                    doSth = true;
                    i++;
                } 
                if (!doSth) {
                    break;
                }
            }
            while(true) {
                boolean doSth = false;
                if (existInRestList(tokenWordMap, tokenWordPosList.get(j))) {
                    doSth = true;
                    j--;
                }
                if (!doSth) {
                    break;
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int index =0; index < wordArray.length; index++) {
                if (index >= tokenWordPosList.get(i) && index <= tokenWordPosList.get(j)) {
                    builder.append(wordArray[index]);
                    if (index != tokenWordPosList.get(j)) {
                        builder.append(" ");
                    }
                }
            }
            System.out.println(builder.toString());
        } else {
            System.out.println("NO SUBSEGMENT FOUND");
        }
    }
    
    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
     }
     public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String text = matcher.replaceAll("");
        return text;
     }
    
    public static boolean existInRestList(Map<Integer, String> tokenWordMap, Integer key) {
        String tokenWord = tokenWordMap.get(key);
        tokenWordMap.remove(key);
        if (tokenWordMap.containsValue(tokenWord)) {
            return true;
        } else {
            tokenWordMap.put(key, tokenWord); // put it back
            return false;
        }
    }
}