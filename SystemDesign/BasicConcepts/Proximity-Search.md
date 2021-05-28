# GeoHash vs QuadTree

geohash是给没有spatial data structure的检索系统用的
比如说你只能用一个全文本的搜索引擎，geohash可以用来mimic spatial indexing
你可以把event wx4g0ec1 用所有的prefixes(w, wx, wx4...）索引
这样对任何一级的搜索都能找到

geohash就是一个很蹩脚的quadtree(essentially a trie), 并不efficient,
应该直接上spatial data structure.
一般来说r-tree is the best.


quadTree 是更精准更慢
geohash是不精准会有突变但更快

- 微软用quadTree
- 很多开源的如ElasticSearch, MongoDB用 geohash
- 还有Hilbert curve, 谷歌用的。



Quadtrees are a very straightforward spatial indexing technique. In a Quadtree, each node represents a bounding box covering some part of the space being indexed, with the root node covering the entire area. Each node is either a leaf node - in which case it contains one or more indexed points, and no children, or it is an internal node, in which case it has exactly four children, one for each quadrant obtained by dividing the area covered in half along both axes - hence the name.


Querying once we've thrown away the tree itself becomes a little more complex. Instead of refining our search set recursively, we need to construct a search set ahead of time.

