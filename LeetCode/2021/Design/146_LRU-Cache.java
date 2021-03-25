/**
6 scenarios in total:
1. cache is empty
2. cache has only 1 element
3. cache has only 2 element2
4. cache has > 2 elements, put node is at first
5. cache has > 2 elements, put node is at last
6. cache has > 2 elements, put node is at middle
**/
class LRUCache {

    LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
    int totalCapacity = 0;
    public LRUCache(int capacity) {
        this.totalCapacity = capacity;
    }
    
    public int get(int key) {
        Integer value = map.get(key);
        if (value != null) {
            // move (key,value) to bottom since it's visited
            moveToLast(key, value);
            return value;
        } else {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        Integer existingValue = map.get(key);
        if (existingValue != null) {
            // update it, move to last since it's visited
            moveToLast(key, value);
        } else {
            // check capacity
            if (map.size() == totalCapacity) {
                int keyToRemove = -1;
                for (Integer theKey : map.keySet()) {
                    keyToRemove = theKey;
                    break;
                }
                map.remove(keyToRemove);
            }
            map.put(key, value);
        }
    }
    
    public void moveToLast(int key, int value) {
        map.remove(key);
        map.put(key, value);
    }
    
    // public void print() {
    //     Node tmpFist = first;
    //     Node tmpLast = last;
    //     StringBuilder sb = new StringBuilder();
    //     while(tmpFist != null && tmpFist != tmpLast) {
    //         sb.append(tmpFist.value + " ");
    //         tmpFist = tmpFist.next;
    //     }
    //     if (tmpLast != null) {
    //         sb.append(tmpLast.value);
    //     }
    //     System.out.println("list is: " + sb.toString());
    // }
    
    public static class Node {
        public Integer value;
        public Node prev;
        public Node next;
        public Node(Integer value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */