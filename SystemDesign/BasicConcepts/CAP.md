# CAP

- Consistency
- Availability
- Partition tolerance
  - 大多数分布式系统都分布在多个子网络。每个子网络就叫做一个区（partition）。分区容错的意思是，区间通信可能失败。比如，一台服务器放在中国，另一台服务器放在美国，这就是两个区，它们之间可能无法通信。

一般来说，distribtued system肯定有多余1个server/partition, 所以Partition error一定会发生，所以必须实现partition tolerance。 
So under real distirbuted system，we can choose only one between C and A :

- Consistency: 
  - data on different paritions are always consistent
  - it means data cannot always be availabile, or we can say cannot do write and read at the same time.  
    - b/c data-write must be locked, and read cannot happen before all partions are consistent
  - e.g.
    - unique global id genearter 
    - dynamoDB strong consistency read can cause delay
- Availability: 
  - data can always be read immediately on any partition
  - it means data on different partition must be different, aka not consistent
  - e.g.
    - social network can allow dirty read and eventually consistency.

### Another type of understanding:

- C：代表状态一致
- A：代表同一时间
- P：代表不同空间
- CP:不同空间中的数据，如果要求他们所有状态一致，则必然不在同一时间。
  - e.g.
    - Zookeeper，因为zookeeper的master节点挂掉后，会重新通过选举策略选举，在选举期间，服务是不可用的。
- AP:不同空间中，如果要求同一时间都可以从任意的空间拿到数据，则必然数据的状态不一致。
- CA:不同空间的数据，如果要求任意时间都可以从任意空间拿到状态一致的数据，则空间数必然为1.
  - single server

