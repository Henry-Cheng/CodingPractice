// https://leetcode.com/problems/all-oone-data-structure/
class AllOne {
    
    HashMap<String, Layer> keyToLayerMap;
    Layer min = null;
    Layer max = null;
    
    private static class Layer {
        public int count; // count of the keys at this layer
        public HashSet<String> keys; // hashset to store key, so that get/remove are O(1)
        public Layer next;
        public Layer prev;
        public Layer(int count) {
            this.count = count;
            this.keys = new HashSet<>();
        }
    }
    
    /** Initialize your data structure here. */
    public AllOne() {
        keyToLayerMap = new HashMap<>();
        min = new Layer(0); // set dummy head
        max = new Layer(0); // set dummy tail
        min.next = max;
        max.prev = min;
    }
    
    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        //System.out.println("incr " + key);
        
        Layer layer = keyToLayerMap.get(key);
        if (layer == null) { // key not exists, add it to layer1
            if (min.next.count == 1) { // layer1 exists
                min.next.keys.add(key);
                keyToLayerMap.put(key, min.next);
            } else { // layer1 not exists
                Layer newLayer = new Layer(1);
                newLayer.keys.add(key);
                keyToLayerMap.put(key, newLayer);
                // insert newLayer in to double-ended list
                Layer next = min.next;
                min.next = newLayer;
                newLayer.prev = min;
                newLayer.next = next;
                next.prev = newLayer;
            }
        } else { // key exists, move key to next layer, and remove key from current layer
            Layer next = layer.next;
            if (next.count != layer.count+1) { // layer "count+1" not exists
                Layer newLayer = new Layer(layer.count+1);
                newLayer.keys.add(key);
                keyToLayerMap.put(key, newLayer);
                // insert newLayer into double-ended list
                layer.next = newLayer;
                newLayer.prev = layer;
                newLayer.next = next;
                next.prev = newLayer;
            } else { // layer "count+1" exists
                next.keys.add(key);
                keyToLayerMap.put(key, next);
            }
            // remove key from current layer
            layer.keys.remove(key);
            if (layer.keys.isEmpty()) { // remove this layer
                layer.prev.next = layer.next;
                layer.next.prev = layer.prev;
            }
        }

        //System.out.println("min is " + min.next.keys.toString() + " with count " + min.next.count);
        //System.out.println("max is " + max.prev.keys.toString() + " with count " + max.prev.count);
    }
    
    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        //System.out.println("decr " + key);
        Layer layer = keyToLayerMap.get(key);
        if (layer.count == 1) { // remove the key from the map
            keyToLayerMap.remove(key);
        } else { // add key to prev layer
            if (layer.prev.count != layer.count-1) { // layer "count-1" does not exist
                Layer newLayer = new Layer(layer.count-1);
                newLayer.keys.add(key);
                keyToLayerMap.put(key, newLayer);
                // insert newLayer into double-ended list
                Layer prev = layer.prev;
                prev.next = newLayer;
                newLayer.prev = prev;
                newLayer.next = layer;
                layer.prev = newLayer;
            } else { // layer "count-1" exists
                layer.prev.keys.add(key);
                keyToLayerMap.put(key, layer.prev);
            }
        }

        // remove key from current layer
        layer.keys.remove(key);
        if (layer.keys.isEmpty()) {// remove layer
            layer.prev.next = layer.next;
            layer.next.prev = layer.prev;
        }
        
        //System.out.println("min is " + min.next.keys.toString() + " with count " + min.next.count);
        //System.out.println("max is " + max.prev.keys.toString() + " with count " + max.prev.count);
    }
    
    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        return (max.prev.count == 0) ? "" : max.prev.keys.iterator().next();
    }
    
    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        return (min.next.count == 0) ? "" : min.next.keys.iterator().next();
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */