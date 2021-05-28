# Uber

Reference: https://jiayi797.github.io/2018/01/21/%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1-%E8%AE%BE%E8%AE%A1Uber/

## Ccritical requirements

1. Location discovery
- driver to update location
- user to update location
- drvier can also update their status --> available to take order or not

2. Order for trip
- rider gives trip plan, then system notifies driver 
- if driver accepts, notify user
- if driver denies, continue notifying next (a few) available drivers

## Database

1. Location table
  - write heavy
2. Trip table
  - read heavy

## Cache

Cache is not for locations, since location is updated frequently.  
Cache is more used for map data.  


## Diagram



## How to lookup nearby 5 km drivers?

这个匹配机制有大致有这样两种实现方式（之前在这篇文章中都提到过）：
一种是使用 QuadTree，就是说把地图上任意一个区域都划分成四个子区域，每个区域如果节点超过一个阈值，就继续划分。
第二种是使用 Geohash，本质上就是降维。降维的原因是，一维的数据管理和查找起来要容易得多，二维的数据要做到高效查找比较困难。我们的查找条件是基于经纬度的，而不是一个单值；我们存储的数据也都是一个个经纬度形成的点，因此，Geohash 的办法就是把查找条件和存储的数据全部都变成一个个单值，这样就可以利用我们熟悉的一维数组区域查找的技术来高效实现（比如把它索引化，而索引化其实是可以通过 B+树来实现的，因此 Geohash 的查询时间复杂度和 QuadTree 是可以在同一个数量级的，都是 log n）。
由于写的频率太高，可能导致上面的树（无论哪一种）出现节点分裂和合并过于频繁，造成性能和资源竞争的压力，因此可以做两个优化：
这个 location 数据的写入，可以不非常实时，比如对于没有处于接单状态的司机，可以合并几次写入到一次再写入。
节点的分裂还是合并设置阈值，并且分裂阈值要大于合并阈值，阈值以内不发生节点变化。比如说，一个大节点的下一层，同样数量的司机，可能分布在 x 个子节点，也可能分布在 x+1 个子节点。



1. Lattitude/Longitude
  - range query based on lattitude/longitude
  - pros:
    - uniquely identify each driver
  - cons:
    - scan the full table exery time
2. GeoHash (https://aws.amazon.com/blogs/mobile/geo-library-for-amazon-dynamodb-part-1-table-structure/)
  - whole world is divided into rectangles
    - e.g. for US uses "9"
  - the string is unique across the whole world
  - it likes a prefix-tree, more character is more accurate
  - pros: 
    - reduce the dimension to be 1-d, using string to represent it
    - the more common prefix we have, the more close
      - e.g. 
        - geohash length即前缀5位相同的，差不多在2km内
        - 6位 geohash 的精度已经在一公里以内，对于 Uber 这类应用足够了
        - 4位 geohash 的精度在20公里以上了，再大就没意义了，你不会打20公里以外的车
    - can store like
      -  key = 9q9hvt, value = set of drivers in this location
  - cons:
    - 
3. Geospatial database
  - using r-tree to split the world into lots of rectange, using n-array search 
  - pros:
    - optimized index query based on distance/shape/etc.
  - cons:
    - 
4. Hilbert Curve


## DB selection for location table

1. SQL - MySQL
  - cons: slow
    - Like query 很慢，应该尽量避免；即便有index，也很慢
    - Uber 的应用中，Driver 需要实时 Update 自己的地理位置, 被index的column并不适合经常被修改, B+树不停变动，效率低
2. NoSQL – Cassandra 可以，但相对较慢
  - cons: slow
    - Driver 的地理位置信息更新频次很高, Column Key 是有 index 的，被 index 的 column 不适合经常被“修改”
3. NoSQL – Memcached 并不合适
  - cons:
    - no persistence, 一旦挂了，数据就丢失 
    - does not support set, 需要读出整个 set，添加一个新元素，然后再把整个set 赋回去
4. Redis
  - pros:
    - 数据可持久化
  - 原生支持list，set等结构
  - 读写速度接近内存访问速度 >100k QPS

## DW

DW(Hadoop) 
