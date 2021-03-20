// https://leetcode.com/problems/word-break-ii/solution/
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        // "apple","pen","applepen","pine","pineapple"
        // pineapplepenapple
        // pine apple pen apple
        // pine applepen apple
        // pineapple pen apple
        
        int n = s.length();
        LinkedHashMap<Integer, Record> dp = new LinkedHashMap<>();
        dp.put(0, new Record(true, new LinkedList<>()));
        for (int i = 1; i < n + 1; i++) {
            Record record = new Record(false, new LinkedList<>());
            for (int j = 0; j < i; j++) {
                if (dp.get(j).canBreak) {
                    String strPendingCompare = s.substring(j, i);
                    //System.out.println("pending Compare is: " + strPendingCompare);
                    if (wordDict.contains(strPendingCompare)) {
                        //System.out.println("found it ");
                        record.canBreak = true;
                        List<List<String>> prevWordList = dp.get(j).wordsList;
                        if (prevWordList.isEmpty()) {
                            LinkedList<String> newWords = new LinkedList<>();
                            newWords.add(strPendingCompare);
                            record.wordsList.add(newWords);
                        } else {
                            for (List<String> words : dp.get(j).wordsList) {
                                LinkedList<String> newWords = new LinkedList<>(words);
                                newWords.add(strPendingCompare);
                                record.wordsList.add(newWords);
                            } 
                        }

                    }
                }
            }
            dp.put(i, record);
            //System.out.println(i + ", \n" + record);
        }

        if (dp.get(n).canBreak) {
            return dp.get(n).wordsList.stream().map(list -> {
                StringBuilder sb = new StringBuilder();
                for (String word : list) {
                    sb.append(word + " ");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }).collect(Collectors.toList());
        } else {
            return new LinkedList<>();
        }

    }
    
    public static class Record {
        public boolean canBreak;
        public List<List<String>> wordsList;
        public Record(boolean canBreak, List<List<String>> wordsList) {
            this.canBreak = canBreak;
            this.wordsList = wordsList;
        }
        @Override
        public String toString() {
            return wordsList.toString();
        }
    }
    
    // LC solution
    public List<String> wordBreakLC(String s, List<String> wordDict) {
        Set<String> wordsDic = new HashSet<String>(wordDict);
        
        // Keep track of all posibilities ending at an idx
        // <idx (actual index+1, aka i), word possibilities list ending at idx>
        Map<Integer, List<String>> allWordPos = new HashMap<Integer, List<String>>();
        allWordPos.put(0, new ArrayList<String>());
        
        for (int i = 1; i < s.length() + 1; i++) { // substring right border non-inclusive
            for (int j = 0; j < i; j++) {
                String sub = s.substring(j, i);
                if (wordDict.contains(sub) && allWordPos.containsKey(j)) {
                    allWordPos.computeIfAbsent(i, words-> new ArrayList<String>());
                    List<String> endsInI = allWordPos.get(i);
                    
                    if (j == 0) {
                        endsInI.add(sub);
                    } else {
                        // construct for all string combinations ending in i from j
                        List<String> endsInJ = allWordPos.get(j);
                        for (String strJ : endsInJ) {
                            endsInI.add(strJ + " " + sub);
                        }
                    }
                }
            }
        }
        
        return allWordPos.getOrDefault(s.length(), new ArrayList<String>());
    }

}