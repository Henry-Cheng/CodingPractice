# News Feed for Facebook/Twitter/Instgram
## Description
Newsfeed allows users to view posts, photos, videos and status updates from his/her followees or pages. 

## Ask Requirements
### 1. Functional Requirement

#### What are the functionalities:

- A user can create new feeds with text, image or videos
- A user can follow friends/groups/pages
- A user can see feeds from people or page he follows

#### sort the functionalities and implement the core ones

#### ask do we need to consider front end?

### 2. Performance Requirement

- SLA on latency?
  - generate newsfeed in 2 seconds
  - a newly created feeds is viewed by followers in 5 seconds 
- Required Throughput? 
  - How many users online?
  	- 300M daily active users (DAU)
  	- 600M monthly active users (MAU) -- usually `MAU ≈ DAU * 2`
  - What's the average daily requests per user?
    - read: 5 times
    - write: 1 times
  - What's the average size of a post?
    - text: 1KB
    - picture: 5 MB
    - video: 10 MB
  - What's average number of followings per people?
    - Friends: 300
    - Pages: 200

#### Estimation

Now based on performance requirements, we can do the following estimations:

- Traffic estimation
  - read
    - average: 300M * 5 / 1 day = 17.5K TPS
    - peak: average * (2 ~ 9 times) = 52.5k TPS (assume it's 3 times)
  - write
    - average: 300M * 1 / 1 day = 6K TPS
    - peak: average * (2 ~ 9 times) = 18k TPS (assume it's 3 times)
- Storage estimation
    - disk:
	  - text: 1KB * peak write traffic which is 18K * 1 month = 46 TB/month
	- memory:
	  - text: assume we keep 7 days of feeds in memory, which is 1/4 of disk space which is 15TB memory / month

NOTE:
- Assume one server can handle 100GB memory, we would need 150 machiens to support the memory estimation.
- If using UUID as key (each UUID is 16 characters), there would be 46TB * (16/1000) = 736 GB of keys
  - if using DynamoDB, since each partition (server) can only support 10 GB of keys, we need 74 partitions (servers)
  - each DynamoDB partition can support 3,000 RCUs or 1,000 WCUs (per [DynamoDB Doc](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-partition-key-design.html))

#### Service benchmark:
Choose webserver:

- TPS < 100：1 single general server is enough
- TPS < 1K: a good server with better hardware (need to consider single point of failure)
- TPS >= 1M: cluster

Relationship of webserver and DB:
- 1 webserver can support 1K TPS
- 1 SQL DB can support 1K TPS
- 1 NoSQL DB (e.g. dynamoDB) can support 4K TPS (3K read and 1K write)
- 1 NoSQL caching DB (e.g. memcached) can support 1M TPS


Actually the real bench heavily relies on the hardware, we can only assume here.


### 3. Other business requirements
- multi-tenant
- multi-domain
- multi-region
- financial

### 4. Tech Requirements

- Scalability
- Availability
  - failover
  - single point of failure
  - peak traffic
- Security
  - Authentication & Authorization -- access control
  - Data encryption
  - DDos -- abnormal traffic detection


## High-level design

### Architecture

Let's draw a very high-level architecture, it follows the general web application architecture:

- client layer
  - client uses browser or mobile app to access backend system
- web layer 
  - web server, which is the entrypoint to take clients requests
  - but based on our scenario, one web server is not enough (1K TPS), so we would need a cluster of webservers
    - load balancer
      - to distributes incoming client requests among a group of servers, like AWS ELB (a scalable LB can supports millions of TPS)
      - can also do something like `session persistence`, which helps send all requests from a particular client to the same server
      - we can also have DNS load blancing like Route53
    - reverse proxy
      - reverse proxy is something like Nginx, can forward a request to one of the backend application server (it can also do LB but not as scalable as ELB, we can talk about it latter)
      - security improvement -- e.g. IP block for DDos
      - web acceleration -- e.g. SSL termination, compression, caching
      - assume each Nginx webserver can support 1K TPS, we need about 80 Nginx servers (if we consider multi-AZ and redundance policy, we will need more)
  - notification server
    - if we need to notify users, this is also required, like AWS SNS
- application layer
  - application server maintains the major business logic, it's something like Tomcat, or Amazon coral server BobCat 
  - based on business logic, we can split application layer by components (in the future as the system grows, we can split the following components as micro-services to split ownership):
    - User component
      - login
      - register
    - News component
      - publish news
      - store news
      - form timeline
    - Friendship component
      - follow friends
      - unfollow friends
    - Media component
      - upload picture
      - upload video
    - data persistence layer
  - assume each application server can support 200 TPS, we can allocate at least 5 application servers behind one webserver (we need more considering multi-AZ)
- data persistence layer
  - Meta data
    - Users, pages
  - Posts data
    - posts
  - Media data
    - picture
    - videos
  - front-end data (if we need to consider front-end)
    - static html/css/js files

Between each layers, we would need multiple caching layers in between, considering the latency SLA we need to meet. We can talk about it as detailed component-level design.

### Component - persistence
For persistence, let's look at what kind of data we need to persist and their features:

- Meta data (users, pages, who follows who)
  - structured data, fixed/stable fields
  - may have complex index: there are relationship between data models
  - good to use SQL DB
    - e.g. AWS RDS (supports MySQL, Postgres)
- Posts data (text/twitts)
  - unstructure data (could have new fields in the future)
  - straightforward indexing like key-value
  - good to use NoSQL
    - e.g. AWS DynamoDB, MongoDB
- Media data (pictures/videos)
  - file/document
  - can use NoSQL DB like DynamoDB that supports document
  - but usually we store a document key in NoSQL, and put the raw data in fle system
    - e.g. AWS S3

It is good to separate database (SQL or NoSQL) for different data types, that makes the persistence layer more modular and splits the traffic on different business logic.


#### Data model / DB schema
Let's use some examples to design data model:

- User
  - userId -- UUID, or auto incremental id like MySQL (but it's hard to maintain global unique, need some workaround)
  - userName
  - password
- Relationship
  - fromUserId -- primary key
  - toUserId -- foreign key
- Post
  - postId
  - userId -- GSI (global secondary index)
  - text
  - pictureId
  - videoId
  - creatTimestamp
  - lastUpdateTimeStamp

### Component -- application
The other application components are striaightforward, like CRUD operation on posts or user information.  
But the major news feeding logic is not straightforward.  
Based on which side proactively pulls the data, we have 3 options:  

- “Pull” model or Fan-out-on-load: This method involves keeping all the recent feed data in memory so that users can pull it from the server whenever they need it. Clients can pull the feed data on a regular basis or manually whenever they need it. Possible problems with this approach are a) New data might not be shown to the users until they issue a pull request, b) It’s hard to find the right pull cadence, as most of the time pull requests will result in an empty response if there is no new data, causing waste of resources.
- “Push” model or Fan-out-on-write: For a push system, once a user has published a post, we can immediately push this post to all the followers. The advantage is that when fetching feed, you don’t need to go through your friend’s list and get feeds for each of them. It significantly reduces read operations. To efficiently handle this, users have to maintain a Long Poll 1 request with the server for receiving the updates. A possible problem with this approach is that when a user has millions of followers (a celebrity-user), the server has to push updates to a lot of people.
- Hybrid: An alternate method to handle feed data could be to use a hybrid approach, i.e., to do a combination of fan-out-on-write and fan-out-on-load. Specifically, we can stop pushing posts from users with a high number of followers (a celebrity user) and only push data for those users who have a few hundred (or thousand) followers. For celebrity users, we can let the followers pull the updates. Since the push operation can be extremely costly for users who have a lot of friends or followers, therefore, by disabling fanout for them, we can save a huge number of resources. Another alternate approach could be that once a user publishes a post; we can limit the fanout to only her online friends. Also, to get benefits from both the approaches, a combination of push to notify and pull for serving end users is a great way to go. Purely push or pull model is less versatile.

Pull模型：读扩散
Pull模型：用户查看news feed时，获取每个好友前100条微博，合并出前100条

通常情况下，内存处理的时间往往要远远小于 DB 访问的时间，因此通常可以忽略不计。在系统设计的考察中，会弱化算法实现的考察，并且算法执行其实在系统设计中，对程序或者系统的执行时间的影响是有限的。

主动读取：用户需要的时候再算
K路归并算法：(时间复杂度为O(nlogk))
使用堆
每两两个链表合并，自下而上
类似归并排序，自上而下


时间复杂度：
News Feed ➔ 假如有N个关注对象，则复杂度 = N次DB Reads的时间（O(100N)） + K路归并时间（可忽略，由于数据获取是瓶颈）
Post a tweet ➔ 1次DB Write的时间
Read的慢是用户可感知的


Pull模型原理如下：

用户发送请求消息
获取关注列表
获取关注列表的前100条Tweets
merge之后返回
Pull模型的缺陷：N次DB Reads非常慢 且发生在用户获得News Feed的请求过程中， Read的慢是用户可感知的慢
但是，这一模型有一个很明显的缺陷：那就是，当用户想要拉取自己订阅的新鲜事列表时，需要执行较多的 DB 操作，而这些操作恰恰也是阻塞的，也就意味着用户需要等待这一系列 DB 操作执行完成，系统才回将新鲜事列表返回给客户端显示出来，相比较而言，用户需要等待较长的时间。


Push模型：写扩散


Push模型：

一个News Feed Table存储所有用户的News Feed
为每个用户建一个List存储他的News Feed信息(虚拟的list，从表中根据ower_id filter出list)
用户发一个Tweet之后，将该推文逐个推送到每个用户的News Feed List中(关键词:Fanout)
用户需要查看News Feed时，只需要从该News Feed List中读取最新的100条即可 (可放入cache中)
“Disk is cheap”，不要怕浪费数据库存储，为了加速查询，多存一些东西是没关系的。
复杂度分析：

News feed => 1次DB Read
在 Needs Feed Table 可以建 composite index


Post a tweet => N个粉丝，需要N次DB Writes
用户只需要知道有没有发成功
好处是Fanout过程可以用异步任务在后台执行，无需用户等待

复杂度分析：

﻿

当用户在刷新自己订阅的新鲜事列表时，只需要 1 次 DB 读取即可。

﻿

当用户发一条新鲜事时，情况会稍复杂一些，如果该用户被 N 个用户关注，那么则需要执行 N 次 DB 写入。这一操作的好处是，可以做成异步的，在用户的请求到达服务端之后，服务端可以先返回给用户“已经发送成功”的消息，用户无需等待所有数据插入完成，这一操作可以在系统异步地执行，这一操作有一个名词，叫做【Fanout】。


Push模型原理如下：

用户发布一个新的tweet
把tweet写入数据库
同时把新的tweet更新到好友的信息流中，新建一个异步任务，放入message queue
获得粉丝列表
Fanout：把新的tweet写入到粉丝的news feed中


Push模型的缺陷：

不及时。粉丝数目可能很大，导致 Fanout 过程很长，从而导致用户刷到新鲜事有延迟。
浪费系统资源去为很多僵尸粉创建新鲜事记录。
明星发帖会在短时间内为系统带来很大的处理压力。

缺陷：

﻿

浪费数据库空间：我们可以看到同一条新鲜事会在数据库表中存储多条（尽管这一问题可以通过在 fanout 的表中，只记录新鲜事 id 来优化，但是相比较而言，这一方案是更浪费数据库存储空间的）；

新鲜事更新可能会不及时：比如说一个明星有 1M 的粉丝量，整个 Fanout 的过程可能要持续一段时间，有一些粉丝可能已经收到这个明星发布的新鲜事了，但是有的粉丝可能半个小时之后才收到，这是影响用户体验的，一个吃瓜群众肯定是希望第一时间吃到瓜的。

https://xie.infoq.cn/article/95367c76774da55a158967b9f

Poll vs Push
大厂大部分都是 Pull 或者 Pull + Push 因为要考虑明星的问题，小厂一般直接 Push，因为代码简单，容易维护。如果没有明星问题的产品，如朋友圈，也可以用 Push，因为不会有明星问题。
使用Poll 和 Push 都可以，遇到问题可以尝试优化。

热门Social App的模型：

Facebook – Pull
Instagram – Push + Pull
Twitter – Pull
误区：
1）不坚定想法，摇摆不定；
2）不能表现出Tradeoff的能力
3）无法解决特定的问题



解决Pull的缺陷
由于最慢的部分发生在用户读请求时，那么可以:

在DB访问之前加入Cache，常用会使用memcached，相当于是数据库的cache
Cache每个用户的timeline：
N次DB请求 ➔ N次Cache请求 (N是你关注的好友个数), 从数据库中读取改为从Cache中读取，而数据库的读取速度和Cache的读取速度的差是100～1000倍
Trade off: 可以只考虑最近的1000条之类的（其实200条也可以了），不要太浪费内存


Cache每个用户的News feed：
没有Cache News Feed的用户：归并N个用户最近的100条tweets，然后取出结果的前100条。不是时时有新的更新。
有Cache News Feed的用户：归并N个用户在某个时间戳之后的所有tweets


解决Push缺陷
浪费Disk空间
但其实没啥, Disk is cheap


不活跃用户
粉丝排序。但也没啥作用


大量粉丝问题，无解。因此可以尝试在现有的模型下进行优化(加几个机器)。还可以对长期增长进行估计，并评估是否值得转化整个模型。
做个trade off: Pull + Push vs Pull


Push 结合 Pull 的优化方案
算法过程：

普通的用户仍然 Push
将 Lady Gaga 这类的用户，标记为明星用户
对于明星用户，不 Push 到用户的 News Feed 中
当用户需要的时候，来明星用户的 Timeline 里取，并合并到 News Feed 里
明星用户： 是不是明星不能在线动态计算，要离线计算（为 User 增加一个 is_superstar 的属性）

Pull vs Push
为什么既然大家都用Pull，我们仍然要学习Push?
系统设计不是选择一个最好的方案
而是选择一个最合适的方案
如果你没有很大的流量，Push是最经济最省力的做法


什么时候用 Push?
资源少
想偷懒，少写代码
实时性要求不高
用户发帖比较少
双向好友关系，没有明星问题(比如朋友圈)


什么时候用 Pull ?
资源充足
实时性要求高
用户发帖很多
单向好友关系，有明星问题
(如Facebook，有复杂的要求时)


## Optimization


一些额外优化的点：

Memory Cache 中每个时间线只保存几百条数据
Memory Cache 只存储活跃用户的时间线
Tweet Info 和 User Info 都只缓存储活跃用户


https://zhuanlan.zhihu.com/p/103484396

Maintenance
鲁棒性 Robust
如果有一台服务器/数据库挂了怎么办


扩展性 Scalability
如果有流量暴增，如何扩展


Q1：关注和取关
Follow & Unfollow：无论使用什么模型，都需要在数据库中创建/删除记录
对于Push model：
Follow 一个用户之后，异步地将他的 Timeline 合并到你的 News Feed 中
Unfollow 一个用户之后，异步地将他发的 Tweets 从你的 News Feed 中移除


需要异步 Async 的原因：
因为这个过程一点都不快呀，(有很多的数据)


异步的好处：
用户迅速得到反馈，似乎马上就 follow / unfollow 成功了


异步的坏处：
Unfollow 之后刷新 News Feed，发现好像他的信息还在， 有延迟
不过最终还是会被删掉的


Q2：如何存储likes
如何在 News Feed 中同时得到每个帖子被点赞、评论和转发的次数？ 有两种方法：Normalize 和 Denormalize

Normalize 获得点赞数的方式:
1

优点:标准化，最准确。
缺点:炒鸡慢，会增加 O(N) 个 SQL Queries(对于某一页的 Tweets，每个都得来这么一句查询)
Denormalize 获得点赞数的方式:
Denormalize 是在 Like Table 中也还是继续新增一条记录，并且 update TweetTable 里的 like_count。 （对于关注和取关的问题，也是有记录他的粉丝人数和关注的人数也是使用de-normalize记录）
当有人点赞的时候:
1
当有人取消赞的时候:
1

想要获得一个 Tweet 的点赞数时，因为 num_of_likes 就存在 tweet 里，故无需额外的 SQL Queries
时间复杂度为O(1)，虽然记录了冗余信息，但是使用了空间换取了时间


Q3：惊群现象
惊群现象(Thundering Herd)：对于同一条数据短时间出现大量的请求。因为访问的是同一个数据，sharding 机制无论如何都会 sharding 到同一个机器上。此时 sharding 不能做到分摊流量的作用。➔数据库承受不住压力

我们通常会使用缓存来作为数据库的“挡箭牌”，优化一些经常读取的数据的访问速度。即，在访问这些数据时，会先看看是否在缓存中，如果在，就直接读取缓存中的数据，如果不在，就从数据库中读取之后，写入缓存并返回。
那么在高并发的情况下，如果一条非常热的数据，因为缓存过期或者被淘汰算法淘汰等原因，被踢出缓存之后，会导致短时间内(<1s)，大量的数据请求会出现缓存穿透 (Cache miss)，因为数据从 DB 回填到 Cache 需要时间。从而这些请求都会去访问数据库，导致数据库处理不过来而崩溃，从而影响到其他数据的访问而导致整个网站崩溃。
解决办法及参考资料：

Memcache Lease Get - 《Scaling Memcache at Facebook》
给普通的memcache增加lease get功能：对于读取同一条数据，只有第一个请求去访问数据库，把数据从DB中取出，然后回填到cache； 其他请求都在等着。（取肉夹馍例子）

Q4：僵尸粉会带来哪些问题？
﻿

我们可以来定义一下“僵尸粉”，该类用户主要表示的是在系统中长期不活跃的用户，但是他们也有许多关注的用户，也会有少量的粉丝。而这种用户有时会给系统带来一些不必要的负载，从而影响到整个系统的性能。

﻿

其实，我们在系统设计过程中，可以单独针对这一类用户单独处理。比如，我们在系统中对这些长期不活跃的用户进行标记，系统如果使用的是 Push 模型的话，在 Fanout 的过程中，将该类用户设置优先级，放到该过程的最后来执行。


Q5：如何查询共同好友？
﻿

当你去访问一个 Facebook 的个人页面的时候，Facebook 会告诉你，你和他的共同好友有哪些人。其实，不仅仅 Facebook，QQ空间等社交媒体也有这一功能，包括微博也会有查看共同关注这一功能。我们该如何设计这一功能呢？

﻿

常规的思路是：

﻿

其实针对这一问题的实际需求，没有一个系统会让你展现出来你所有的好友与你之间的共同好友的，一般的系统可能只会展示出来你和另一用户之间共同好友的 Top10 或者 Top20。基于这一需求来考虑，其实是可以简化系统设计的。

﻿

如果每次都去请求好友的好友列表进行一次遍历，复杂度将会是 O(N * max(N,M)) ，N 是这个用户的好友数，M 是他的好友的好友数量，响应时间会比较慢，但是其实是可以提升响应速度而牺牲一点精确度（其实对用户体验的牺牲也并没那么大），因为基于我们的需求其实只需要 TOP10 就可以的，最终结果差别一两名也影响不大。

﻿

针对这一方案的话，也是有对应的优化策略的，比如说：

﻿

每个用户都额外使用一个表来记录与自己共同好友较多的 Top10 用户列表， 每次用户请求 Top10 时直接返回；

当新加一个好友时，异步使用上述算法去求两个用户之间的共同好友，并且使用共同好友 Top10 表里面的最少共同好友的记录去比较，看是否需要更新结果。 同时，异步地更新这两个好友的共同好友列表，因为有可能由于这次操作他们直接多了一个共同好友。

﻿

还有一个比较暴力的方法，但是也很科学：

﻿

比如说，对于 Facebook 来说是有好友上线的，目前的 Facebook 好友上线是 5000。其实针对系统来说，就算把一个用户的所有好友的 id 存放到缓存（比如 Memcached）中的话也只有 80 KB左右，如果计算交集，即便是不使用 HashMap ，计算量的数量级大概在 10^7，系统的计算反应时间也是毫秒级的，其实直接使用缓存系统 + 暴力也是可行的。因为其实查询共同好友这一功能并不是一个非常常用的功能，通常也不会出现缓存雪崩或者*缓存失效*的情况。

﻿

Q6：朋友圈的可见/不可见是如何实现的？
﻿

TODO.

﻿

这一部分内容其实比较繁琐，需要针对具体业务需求来分析。后续的补充内容应该会针对“微信朋友圈”的策略来做具体分析，敬请关注。


Data Partitioning
a. Sharding posts and metadata
Since we have a huge number of new posts every day and our read load is extremely high too, we need to distribute our data onto multiple machines such that we can read/write it efficiently. For sharding our databases that are storing posts and their metadata, we can have a similar design as discussed under Designing Twitter 3.

b. Sharding feed data
For feed data, which is being stored in memory, we can partition it based on UserID. We can try storing all the data of a user on one server. When storing, we can pass the UserID to our hash function that will map the user to a cache server where we will store the user’s feed objects. Also, for any given user, since we don’t expect to store more than 500 FeedItmeIDs, we will not run into a scenario where feed data for a user doesn’t fit on a single server. To get the feed of a user, we would always have to query only one server. For future growth and replication, we must use Consistent Hashing 


## Set limitation on service

How many feed items should we store in memory for a user’s feed? Initially, we can decide to store 500 feed items per user, but this number can be adjusted later based on the usage pattern. For example, if we assume that one page of user’s feed has 20 posts and most of the users never browse more than ten pages of their feed, we can decide to store only 200 posts per user. For any user, who wants to see more posts (more than what is stored in memory) we can always query backend servers.
Should we generate (and keep in memory) newsfeed for all users? There will be a lot of users that don’t login frequently. Here are a few things we can do to handle this. A more straightforward approach could be to use an LRU based cache that can remove users from memory that haven’t accessed their newsfeed for a long time. A smarter solution can figure out the login pattern of users to pre-generate their newsfeed, e.g., At what time of the day a user is active? Which days of the week a user accesses their newsfeed? etc.

How many feed items can we return to the client in each request? We should have a maximum limit for the number of items a user can fetch in one request (say 20). But we should let clients choose to specify how many feed items they want with each request, as the user may like to fetch a different number of posts depending on the device (mobile vs. desktop).
Should we always notify users if there are new posts available for their newsfeed? It could be useful for users to get notified whenever new data is available. However, on mobile devices, where data usage is relatively expensive, it can consume unnecessary bandwidth. Hence, at least for mobile devices, we can choose not to push data, instead, let users “Pull to Refresh” to get new posts.


Feed Ranking
The most straightforward way to rank posts in a newsfeed is by the creation time of the posts. But today’s ranking algorithms are doing a lot more than that to ensure “important” posts are ranked higher. The high-level idea of ranking is first to select key “signals” that make a post important and then find out how to combine them to calculate a final ranking score.
More specifically, we can select features that are relevant to the importance of any feed item, e.g., number of likes, comments, shares, time of the update, whether the post has images/videos, etc., and then, a score can be calculated using these features. This is generally enough for a simple ranking system. A better ranking system can significantly improve itself by constantly evaluating if we are making progress in user stickiness, retention, ads revenue, etc.



## FAQ
Cache

Cache是一个相对概念
可以是在内存中
可以是在磁盘上
可以是在CPU里（L1 Cache / L2 Cache)
可以是在服务器端
可以是在客户端（如浏览器cache）
在浏览器中访问某个网页时，第一次比较慢，再访问一次后会比较快，因为浏览器会把一些图片等文件cache在浏览器中




内存中的 Cache 可以理解为一个 Hash Table，是一种 key-value 的结构
常用的Cache工具/服务器有：Memcached，Redis
我们通常把经常访问的数据放在Cache里来加速访问速度， （访问内存比访问数据库快很多）
Cache 因为空间受限制，因此需要淘汰掉一些不常用的数据
常见的淘汰算法有LRU（Least Recently Used）是算法面试中常见的考点


为什么不全放Cache里?
内存中的数据断电就会丢失
Cache 比硬盘贵


消息队列

消息队列是什么
消息队列是 进程间通信 或 同一进程的不同线程间的通信方式
简单的说就是一个先进先出的任务队列列表，队列里放的是任务信息(PID之类)
做任务的worker进程共享同一个列表 (worker监听消息队列，一旦有新的任务就取下来然后执行，执行结果一般不是通过消息队列返回，而是可以通过把结果写入到数据库中，web server通过时不时看一下数据库或者通过用户刷新来读取任务执行情况，通过这样的方式来完成web server和数据库之间协同工作)
Workers从列表中获得任务去做，做完之后反馈给队列服务器
队列服务器是做异步任务必须有的组成部分


什么时候使用消息队列
“生产者消费者”模型 里面使用到的queue, 这个queue起到了缓冲的作用
需要缓冲的原因：处理消息和发送消息（产生消息）的速率不一致
例子：(下单网购和送快递)，OJ交代码和评测机评测，发激活邮件(使用第三方API，由于和远程机器通信故速度慢，且如果失败了会重新发送)，12306订票
使用场景：
任务执行比较慢
使用重试机制
用户愿意去等待(如使用Push模型时关注/取关一个用户)




哪些工具可以做消息队列
最常用的有 RabbitMQ, Redis, AWS SQS, ZeroMQ, Kafk


其他机制(optional)：
两个worker同时工作时，不会取到一样的任务去执行
镜像机制（double master）
有些任务是有优先级（里面有子queue）


NewsFeed 如何实现 Pagination?
问：是不是不管push还是pull模型，如果翻页的话都得pull?
翻页是用户主动操作的过程，所以肯定是由client 发给 server，肯定是一个pull的过程。

问：假设前100条中最早的timestamp是T，就分别请求follow的人在T之前的100条feed，然后再进行合并？
答：对

问：如果恰好有几条feed的timestamp一样该如何处理？
答：首先不会有帖子的timestamp一样，timestamp的精度很高的（微秒级别）

通常来说，翻页这个完全可以作为一道单独的系统设计面试题来问你。翻页并不是简单的1-100，101-200这样去翻页。因为当你在翻页的时候，你的news feed可能已经添加了新的 内容，这个时候你再去索引最新的101-200可能和你的1-100就有重叠了。

通常的做法是，拿第101个帖子的timestamp作为下一页的起始位置，也就是说，当用户在看到第一页的前100个帖子的时候，他还有第101个帖子的timestamp信息（隐藏在你看不到的地方），然后你请求下一页的时候，会带上这个timestamp的信息，server端会去数据库里请求 >= timestamp 的前101个帖子，然后也同样把第101个帖子作为下一页的timestamp。这个方法比直接用第100个帖子的timestamp好的地方是，你如果读不到第101个帖子，说明没有下一页了，如果你刚才只有100个帖子的话，用第100个帖子的timestamp的坏处是，你会有一次空翻。

Web服务器 Web Server

在浏览器输入网址后，这个请求到达的网站提供方那边的一台或者若干台机器。这（些）机器提供HTTP/HTTPS服务。
HTTP/HTTPS 是叫 超文本协议， 主要用于网页浏览器(Web Browser)和网站服务器(Web Server)之间沟通的协议
协议 == 约定好的沟通说话方式 == 传输数据的格式


一台性能好的Web Server大概每秒可以服务1k次访问请求 (粗略的预估) (企业级)
(自己搭建的服务器每秒能服务10台就不错啦)
(要考虑查询是否复杂)
(server有多少个cpu…, 企业的服务器比较好…)


Web Server有时候也叫 Application Server
1个request -> 返回1个response (有点像函数调用)


数据库

存储数据的仓库（数据比如，用户信息，用户发的帖子等）
数据库是内网中才能访问的，一般不会对公网提供发问权限 (为了安全性)
数据库一般和Web Server打交道
数据库支持对数据的增删查改，且提供丰富的查询接口
如查询所有2018年入学的算法成绩 < 60 分的同学信息


一个合理的架构中，Web Server 和 Database Server 是两台不同的机器 （一旦有东西出错了，可以方便的定位到是什么出错了；否则放在一起会相互影响，出错了后很难debug）
数据库适合存储结构化数据（Structured Data) -> （能够写成一个class，里面对应的一些attributes）
常见的软件是MySQL

文件系统 File System

操作系统的组成部分之一
一般是目录结构，（访问文件系统需要提供文件目录地址）
数据库系统是基于文件系统而存在的，（数据库系统无法单独存在，数据库的数据最终还是写在文件系统中的一个一个文件里，只不过这些文件怎么组织，格式是什么，我们无需关心，数据库系统会处理好，我们只需要关心数据库的表是什么，里面有什么属性）
文件系统的接口比较单一
读某个文件从某个位置开始的多少字节的数据
在某个文件的某个位置起写入多少字节的数据
（不符合复杂查询需求，所以数据库系统对文件系统进行封装，提供接口给用户使用）


断电之后数据依然存在 -> 持久化
非机构化的数据适合直接存储在文件系统中
如 .avi, .jpg （直接是文件）（如果需要查询文件里面的东西，需要构建新的查询系统专门服务这个查询）


主键和外键
主键(Primary Key): 用于唯一和完整标记这条数据的数据列或属性的集合。（ e.g., user_id）一个数据表只能有一个主键，且不能缺失，也不能为空。
(SQL型数据库通常不用自己定义主键，因为它默认每个表都带id, 且id是线性增长)
(NoSQL型数据库主键不是一个整数，而是一个哈希码字符串，为了可以支持分布式数据库，同时各自创建新的数据，而不用依赖一个结构去创建ID)
外键(Foreign Key): 在新的表中，用某个表的主键表示那个表的信息，不需要存冗余信息，（有点像存了reference）

id会由于数据太大 而溢出， 所以现在 id 一般要用 Bigint（64位）才不会担心溢出。

数据库索引

Index是什么
类比书的目录 -> 快速查询
有序性


为什么需要Index
可以对数据库表单的任何一项建立索引，用来加速这一项的查询
相当于数据库建了个专门存index的表， 这个index表按照一定顺序很快地查询到待查值所在的位置
加速这两类查询：某个个column等于某个值， 某个column在某个范围（range query）
没有index只能用for循环 -> 超级慢


index的原因
文件上的“有序”列表
传统的关系型数据库一般采用 B+ Tree 做为Index的数据结构
B+树是一棵“排序”多叉树，类似排序二叉树（Binary Search Tree）
通过“多叉”减少树的高度，减少磁盘寻道（Disk Seek）次数




怎么建index & 常用的几类index
（建索引语法可以显查）
普通索引
唯一索引（Unique Index）如Primary Key自带的索引，有唯一性。例子User Table 中的Username，Email
联合索引（Composite Index）涉及到两个或多个column的查询，需要建联合索引
条件索引（Condition Index）只对满足条件的数据建索引，查询也是对满足条件的数据做查询

