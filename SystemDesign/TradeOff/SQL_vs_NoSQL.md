# SQL vs NoSQL

### Concept


BASE stands for:

- Basically Available
  - Rather than enforcing immediate consistency, BASE-modelled NoSQL databases will ensure availability of data by spreading and replicating it across the nodes of the database cluster.
- Soft State
  - Due to the lack of immediate consistency, data values may change over time. The BASE model breaks off with the concept of a database which enforces its own consistency, delegating that responsibility to developers.
- Eventually Consistent
  - The fact that BASE does not enforce immediate consistency does not mean that it never achieves it. However, until it does, data reads are still possible (even though they might not reflect the reality).


ACID stands for:

- Atomic
  – Each transaction is either properly carried out or the process halts and the database reverts back to the state before the transaction started. This ensures that all data in the database is valid.
- Consistent 
  – A processed transaction will never endanger the structural integrity of the database.
- Isolated 
  – Transactions cannot compromise the integrity of other transactions by interacting with them while they are still in progress.
- Durable 
  – The data related to the completed transaction will persist even in the cases of network or power outages. If a transaction fails, it will not impact the manipulated data.


Database Types
Database types depend on the way the data is stored.

- SQL has a table-based database. 
  -Table database stores data into tables with fixed rows and columns.
- NoSQL has 4 types of databases:
  - Key-value database 
    – Stores every data element as an attribute name or key together with its value.
    - e.g. Redis, *DynamoDB*
  - Document database 
    – Stores data in JSON, BSON, or XML documents.
    - e.g.  *DynamoDB*, MongoDB, CouchDB, Elasticsearch
  - Wide-column database 
    – Stores and groups data into columns instead of rows.
    - e.g. Cassandra, HBase, and CosmoDB
  - Graph database 
    – Optimized to capture and search the connections between data elements.
    - e.g. social networks often use graphs to store information about how their users are linked.
    - e.g. OrientDB, RedisGraph, and Neo4j

Wide-column database:
![Image](https://github.com/Henry-Cheng/CodingPractice/raw/master/SystemDesign/Images/column-oriented-database.png)


Usage comparison:
https://www.cnblogs.com/yanduanduan/p/10563678.html

- MongoDB --> general balanced read and write --> 用于对象及JSON 数据的存储 --> Prefix Tree or QuadTree
- HBase --> 依据Row key和Column key定位到的Value能够有随意数量的版本号值，版本号默认是单元格插入时的时间戳 --> 适用于插入比查询操作更频繁的情况：比如，对于历史记录表和日志文件。 --> everything is string
- Redis --> cache level throughput --> but is single thread....
- Cassandra --> 无中心的P2P架构，网络中的所有节点都是对等的 --> 存储海量增长的图片，小视频的时候，你要求数据不能丢失，你要求人工维护尽可能少，你要求能迅速通过添加机器扩充存储，那么毫无疑问，Cassandra现在是占据上风的

### How to choose

1. Transaction/ACID ?

NoSQL supports transaction with limitatoin. 
So it's still better to use SQL for transaction use case like bank/financial.
As for social network related use case, we can accepet some delay by "evenentually consistence".

e.g. MongoDB finally added single-shard ACID transaction support in the recently released 4.0 version. These *single-shard* transactions enforce ACID guarantees on updates across multiple documents as long as all the documents are present in the same shard (i.e. stored on the single primary of a Replica Set). True multi-shard transactions where developers are abstracted from the document-to-shard mapping are not yet supported. This means MongoDB Sharded Cluster deployments cannot leverage the multi-document transactions feature yet.
https://blog.yugabyte.com/are-mongodb-acid-transactions-ready-for-high-performance-applications/

e.g. DynamoDB recently announced support for transactions on “AWS re:Invent 2018 Recap” post. There are severe limitations in the current offering so developers have to take care not to trip up during the rush to release transactional apps powered by DynamoDB. Limitations include:
- Available for single-region tables only
- Limited to a maximum of 10 items or 4MB of data
- No client-controlled transactions
- No consistent secondary indexes even though transactions are supported


2. Complex relationship between objects and complex query?

SQL can do better by joining tables.  
NoSQL needs to create GSI with filters.

3. AUTO_INCREMENT ID?

MySQL can do auto_increment like 1, 2, 3, ...

NoSQL does not have built-in feature, can only use UUID to generate global unique ID in a distributed system.

4. TPS requirement?

NoSQL can do better on TPS:
- Memcached/Redis --> 1 million TPS
- MondoDB/DynamoDB --> 10k TPS
- MySQL --> 1K TPS

5. Scalability?

SQL --> usually vertically scale. Though we can still do horizonte scale, but it needs code change.
NoSQL --> NoSQL does the Query-driven Design, can do both horizontallly and vertically scale, has built-in sharding/replica 

6. Schema changes frequently?

SQL --> Predefined
NoSQL --> Dynamic


