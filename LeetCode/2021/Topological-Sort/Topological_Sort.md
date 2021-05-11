## Topological Sort

In a Directed Acyclic Graph (DAG), there must exist Topological Sort. 
 - 且若在有向图 G 中从顶点 u -> v有一条路径，则在拓扑排序中顶点 u 一定在顶点 v 之前，而因为在DAG图中没有环，所以按照DAG图的拓扑排序进行序列最短路径的更新，一定能求出最短路径。
 - if each vertex has weight, we can convert it into edge weight
 - then Topological Sort can find a shortest edge weighted path.

Topological Sort 也应用了BFS遍历思想、以达成按依赖关系排序的目的.

### Default
Psudocode:
```
1. prepare node to ingressCountMap, and node to egreeDependencyMap
2. traverse once for all possible nodes, and find 0-ingress node (the node that is not in ingressCountMap), record the node in a queue
3. while queue is not empty, poll each node, output the node, and check all dependencies of the node and prune the ingress of the dependency; if found new 0-ingress node, enqueue it
```

NOTE:
- Remember to use hashmap.getOrDefault() to cover possible not existing value in the map
- prepare egressMap from `from-node` to `list of to-node`
- preapare ingressMap from `node` to `ingress-count`
- maintain a queue to store all `0-ingress node` 
- maintain a result list to store final result in order

```java

// put ingressMap
Integer count = ingressMap.getOrDefault(to, 0);
ingressMap.put(to, count + 1);//0->1//1->0
// put egressMap
List<Integer> egressArcs = egressMap.getOrDefault(from, new LinkedList<>());
egressArcs.add(to);
egressMap.put(from, egressArcs);


while (!queue.isEmpty()) {
    Character c = queue.poll();
    result.add(c);
    for (Character to : egressArcs.get(c)) {
        ingressMap.put(to, ingressMap.get(next) - 1);
        if (ingressMap.get(to).equals(0)) {
            queue.add(to);
        }
    }
}
```

- the question does not require to return a "patial path", so either return full path or empty (if there is cycle inside)
- convert List<Integer> to int[] by `resultList.stream().mapToInt(i->i).toArray()`

#### [LC] 207. Course Schedule
https://leetcode.com/problems/course-schedule/

#### [LC] 210. Course Schedule II
https://leetcode.com/problems/course-schedule-ii/

#### [LC] 269. Alien Dictionary
https://leetcode.com/problems/alien-dictionary/

Convert the order between words into adjacencyList or egressMap.

#### [LC] 445. Add Two Numbers II
https://leetcode.com/problems/add-two-numbers-ii/

Use stack to reverse list and finally reverse it back.