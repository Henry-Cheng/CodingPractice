// https://leetcode.com/problems/analyze-user-website-visit-pattern/
class Solution {
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        /**
        user visits at least 3
        
        1 2 3 --> C33 = 1 combine
        
        1 2 3 4 --> C43 = (4*3*2*1) / (3*2*1) = 4 combine
        
        1 2 3 --> C33 = 1 combine
        **/
        Map<String, TreeMap<Integer, Integer>> userToTimeMap = new HashMap<>();
        for (int i = 0; i < username.length; i++) {
            TreeMap<Integer, Integer> map = userToTimeMap.getOrDefault(username[i], new TreeMap<>());
            map.put(timestamp[i], i); // timestamp to index
            userToTimeMap.put(username[i], map);
        }
        //System.out.println("userToTimeMap is " + userToTimeMap);
        TreeMap<String, Integer> threeSequenceCountMap = new TreeMap<>();
        for (Map.Entry<String, TreeMap<Integer,Integer>> entry : userToTimeMap.entrySet()) {
            TreeMap<Integer,Integer> timestampToIndex = entry.getValue();
            if (timestampToIndex.size() < 3) {
                continue; // skip
            }
            //System.out.println("looking for 3 sequence for " + entry.getKey());
            List<String> threeSequence = getTreeSequence(timestampToIndex, website);
            //System.out.println("3 sequence is: " + threeSequence);
            for (String url : threeSequence) {
                threeSequenceCountMap.put(url, threeSequenceCountMap.getOrDefault(url, 0) + 1);
            }
        }
        //System.out.println("threeSequenceCountMap is " + threeSequenceCountMap);
        int max = Integer.MIN_VALUE;
        String finalThree = "";
        for (String key : threeSequenceCountMap.descendingKeySet()) {
            int count = threeSequenceCountMap.get(key);
            if (count >= max) {
                max = count;
                finalThree = key;
            }
        }
        //System.out.println("finalThree is " + finalThree);
        return new ArrayList<String>(Arrays.asList(finalThree.split("-")));
    }
    
    private List<String> getTreeSequence(TreeMap<Integer, Integer> timestampToIndex, String[] website) {
        List<String> list = new LinkedList<>();
        for (Integer key : timestampToIndex.keySet()) {
            int index = timestampToIndex.get(key);
            String url = website[index];
            list.add(url);
        }
        List<String> result = new LinkedList<>();
        if (list.size() == 3) {
            result.add(String.join("-", list));
            return result;
        } else {
            List<String> tmpResult = getCombinations(list, 3);
            HashSet<String> set = new HashSet<>(tmpResult);
            result.addAll(set);
        }
        //System.out.println("3 sequence is: " + result);
        return result;
    }
    
    // NOTE: because list.size()>=count is always true
    private List<String> getCombinations(List<String> list, int count) {
        //System.out.println("get combo " + count + " for " + list);
        List<String> result = new LinkedList<>();
        if (count == 1) {
            result.addAll(list);
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            String head = list.get(i);
            List<String> restList = getCombinations(list.subList(i+1, list.size()), count - 1);
            for (String rest: restList) {
                result.add(head + "-" + rest);
            }
        }
        //System.out.println("the combo is " + result);
        return result;
    }
}