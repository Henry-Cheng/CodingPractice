// https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
class RandomizedCollection {

    HashMap<Integer, HashSet<Integer>> map;
    ArrayList<Integer> list;
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        list.add(val);
        HashSet<Integer> posList = map.getOrDefault(val, new HashSet<>());
        boolean notExists = posList.isEmpty();
        posList.add(list.size() - 1);
        map.put(val, posList);
        //System.out.println("insert " + val + ", list is " + list + ", map is "+ map);
        return notExists ? true : false;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        HashSet<Integer> posList = map.getOrDefault(val, new HashSet<>());
        if (posList.isEmpty()) {
            //System.out.println("remove " + val + ", list is " + list + ", map is "+ map);
            return false;
        } else {
            int pos = posList.iterator().next();
            int end = list.size() - 1;
            swap(pos, end, list, map);
            list.remove(end);
            posList.remove(end);
            map.put(val, posList);
            //System.out.println("remove " + val + ", list is " + list + ", map is "+ map);
            return true;
        }
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        //System.out.println("getRandom() list is " + list + ", map is "+ map);
        int random = new Random().nextInt(list.size());
        return list.get(random);
    }
    
    private void swap(int from, int to, List<Integer> list, HashMap<Integer, HashSet<Integer>>map) {
        if (from == to || list.get(from) == list.get(to)) {
            return; // from already at the end
        }
        //System.out.println("swap from " + from + " to " + to);
        //System.out.println("before swap, list is " + list + ", map is " + map);
        int fromVal = list.get(from);
        int toVal = list.get(to);
        list.set(from, toVal);
        list.set(to, fromVal);
        
        HashSet fromSet = map.get(fromVal);
        fromSet.remove(from);
        fromSet.add(to);
        
        HashSet toSet = map.get(toVal);
        toSet.remove(to);
        toSet.add(from);
        
        //System.out.println("after swap, list is " + list + ", map is " + map);
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */