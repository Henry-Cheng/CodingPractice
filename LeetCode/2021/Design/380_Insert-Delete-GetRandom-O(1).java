// https://leetcode.com/problems/insert-delete-getrandom-o1/
class RandomizedSet {

    private ArrayList<Integer> list; // index (i) --> val (list.get(i))
    private HashMap<Integer, Integer> map; // val (key) --> index (value)
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        list.add(val);
        map.put(val, list.size() - 1);
        //System.out.println("insert " + val + ", now list is" + list);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        int index = map.get(val);
        // swap index with last element in list, them remove from list
        //System.out.println("start removing " + val);
        int lastValue = list.get(list.size() - 1);
        //System.out.println("get lastValue " + lastValue + " ,list is " + list);
        list.set(index, lastValue);
        map.put(lastValue, index); // whenever list sets, map puts
        //System.out.println("set lastValue " + lastValue + " ,list is " + list);
        list.set(list.size() - 1, val);
        map.put(val, list.size() - 1); // whenever list sets, map puts
        list.remove(list.size() - 1); // O(1) to remove last index
        map.remove(val);// whenever list remove, map remove
        
        //System.out.println("set last index ,list is " + list);
        //System.out.println("remove " + val + ", now list is" + list);
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        //System.out.println(list.size() + ", " + list.get(list.size() - 1));
        // return 0 ~ list.size() - 1, both end inclusive
        int randomIndex = (int) (Math.random() * list.size());
        // get val from list
        return list.get(randomIndex); 
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */