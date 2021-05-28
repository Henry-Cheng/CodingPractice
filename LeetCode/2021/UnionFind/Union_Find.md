## Union-Find / Disjoint-Set
https://segmentfault.com/a/1190000022952886

### Default
Though most of UnionFind problem can be resolved by DFS, it is still good to remember this data structure, so that you don't need to convert each question into a graph problem.

NOTE:
- After all `union()` are called, we still need to call `find()` to traverse the DJS, since at the moment all `union()` are called, not every direct parent is the final parent.
- UnionFind would not maintain the same graph structure, it only maintains parent-to-child structure


Why not update rank when doing compression?
- rank is not the height of the tree, it's more like an upper bound (can be proved by `inverse Ackermann function`); the rank is allowed to get out of sync with the depth.
- https://stackoverflow.com/questions/25317156/why-path-compression-doesnt-change-rank-in-unionfind

What are the time complexity?
- after "path compression" and "merge by rank", disjoint-set can achieve `O(log* n)`
  - `log* n` is called [Iterated logarithm](https://en.wikipedia.org/wiki/Iterated_logarithm) which is much faster than general `O(logn)` and a bit slower than `O(1)`, usually we treat it as `amortized O(1)`
- another way to describe the time complexity is to call it `O(α(n))` where `α` points to [`inverse Ackermann function`](https://en.wikipedia.org/wiki/Disjoint-set_data_structure)
  - in this universe, `α(n) < 5 `, that is why `O(α(n))` almost equal to `O(1)`

Template:  
```java
class DJS { // disjoint-set
  int[] parent;
  int[] rank; // relative height of the parent node
  public int disjointCount = 0;
  public DJS(int size) {
      parent = new int[size];
      for (int i = 0; i < size; i++) {
          parent[i] = i; // initialize parent of each node to be itself
      }
      rank = new int[size]; // initialize rank array
      disjointCount = size; // initialize disjointCount to be size
  }

  public int find(int x) {
      if (x != parent[x]) {
          parent[x] = find(parent[x]); // path compression 
      }
      return parent[x];
  }
  
  public boolean union(int x, int y) { // NOTE: everything is done at parentX/parentY level
      int parentX = find(x);
      int parentY = find(y);
      if (parentX == parentY) { 
          return true; // already unioned
      } else {
          if (rank[parentX] < rank[parentY]) { // union x to y
              parent[parentX] = parentY;
          } else if (rank[parentX] > rank[parentY]) { // union y to x
              parent[parentY] = parentX;
          } else {
              parent[parentY] = parentX; // can choose either x or y here
              rank[parentX]++; // since we choose x as parent, increment x
          }
          disjointCount--;
          return false; // new union is built
      }
  }
```

Actually the template can also be implemented by HashMap like 
- `HashMap<String, String> parent`
- `HashMap<String, Integer> rank` 

As long as the key and value of `parent` is the same, we can even define a custom class `Node` to make it  
- `HashMap<Node, Node> parent`
- `HashMap<Node, Integer> rank`

```java
class DJS {
    public HashMap<String, String> parent = new HashMap<>();
    public HashMap<String, Integer> rank = new HashMap<>();
    public int disjointSize = 0;
    public DJS(List<List<String>> accounts) {
        for (List<String> account : accounts) {
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                parent.put(email, email); // initialize parent
                rank.put(email, 0); // initialize rank
            }
        }
        disjointSize = parent.size(); // initialize disjointSize
    }
    public String find(String x) {
        if (parent.get(x).equals(x)) {
            return x; // find parent email
        } else {
            parent.put(x, find(parent.get(x))); // compression
            return parent.get(x);
        }
    }
    public boolean union(String x, String y) {
        String parentX = find(x);
        String parentY = find(y);
        if (parentX.equals(parentY)) {
            return true; // already unioned
        } else {
            if (rank.get(parentX) < rank.get(parentY)) {
                parent.put(parentX, parentY);
            } else if (rank.get(parentX) > rank.get(parentY)) {
                parent.put(parentY, parentX);
            } else {
                parent.put(parentY, parentX);
                rank.put(parentX, rank.get(parentX) + 1);
            }
            return false;
        }
    }
}
```

#### [LC] 684. Redundant Connection
https://leetcode.com/problems/redundant-connection/

Be careful that in `union()` method, everythign is done at parentX/parentY level.

#### [LC][Medium] 721. Accounts Merge
https://leetcode.com/problems/accounts-merge/

Be careful that after all `union()` are called, we cannot directly use `parent[x]` since the parent may not be the finaly parent, we need to use `find(x)` to get parent when traversing.

Time Complexity: O(AlogA), where A = \sum a_iA=∑a .  this complexity improves to O(Aα(A))≈O(A), where \alphaα is the Inverse-Ackermann function.


