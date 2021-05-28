# Step to Step Guide

## 1. Requirements clarifications

Ask questions about the problem to figure out the following stuff:

### 1.1 Functionality requirement (critical use case)
  - e.g. for twitter
    - post tweets and follow other people?
    - create and display user’s timeline?
    - contain photos and videos?
    - search tweets?
    - rank based on timestamp or hot trending topics?
    - notification?

### 1.2 Performance requirement
  - e.g. for twitter
    - latency SLA: 200ms for timeline generation
    - if a user doesn’t see a tweet for a while, it should be fine
    - highly available
    - scalable

### 1.3 Extended requirements (non-critical use case)
  - e.g. for twitter
    - mark favourite tweets
    - recommendation of followers?
    - tagging other users
  - some other tech requirements
    - multi-tenant
    - multi-domain
    - multi-region

### 1.4 Design specific requirements
  - e.g. 
    - backend only or also front-end?
    - API is external usable or only for internal use?
    - financial requirement?

## 2. Scale estimation

### 2.1 Scale of use case

e.g. for twitter

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

Now we cal tell it is a read-heavy system!!!

### 2.2 ballpark figures
Now based on performance requirements, we can get ballpart figures:

NOTE: `throughput = traffic * package size`  
- At application layer, we care traffic (or can be converted into concurrency rate), it tells how many server we need to support business logic.  
- At network layer, we care the throughput, which tells us how much bandwidth we need to buy


- Traffic estimation (`how many server we need for business logic` --> TPS)
  - read
    - average: 300M * 5 / 1 day = 17.5K TPS
    - peak: average * (2 ~ 9 times) = 52.5k TPS (assume it's 3 times)
  - write
    - average: 300M * 1 / 1 day = 6K TPS
    - peak: average * (2 ~ 9 times) = 18k TPS (assume it's 3 times)

- Throughput estimation (`how much network IO bandwidth we need` --> GB/s)
  - egress (read) 
    - text:
      - average: 17.5k TPS * 1KB = 17.5MB /s
    - picture --> assume it's 1/10 traffic of text
      - average: 17.5k TPS / 10 * 5MB = 8.5 GB /s
    - video: --> assume it's 1/100 traffic of text
      - average: 17.5k TPS / 100 * 10MB = 1.7GB /s
  - ingress (write)
    - text: 
      - average: 6K * 1KB = 6 MB/s
    - picture:
      - average: 6K / 10 * 5MB = 3GB /s
    - video: 
      - average: 6K / 100 * 10MB = 600 MB/s

- Storage estimation (`how much memory/disk we need`)
  - disk:
    - text: 1KB * average write traffic which is 6K * 1 month = 18 TB/month
    - picture:
    - video:
  - memory --> assume we keep 20% data in memory
    - text: 25% disk space which is 4.5TB memory / month
    - picture:
    - video: 

NOTE: 
1. the actual traffic for picture/video should be much lower, we can assume like 1/10 and 1/100 of text traffic
2. do not use peak all the time, just mention it once how we handle peak, and then use avearge
3. do not calcualte all text/picture/video all the time, that would take too much time, just using an average number here to show that "you can calculate!"
4. Trick1:
  - 1 day = 86,400 seconds ~ 100k seconds
  - 1 month = 2,629,746 seconds ~ 3M seconds
  - 1 year = 31536000 seconds ~ 30M seconds
5. Trick2:
  - Thousand -->  KB
  - Million -->   MB
  - Billion -->   GB
  - Trillion -->  TB
  - Quadrillion --> PB
  - Quintillion --> EB
  - Sextillion --> ZB

### 2.3 Server number estimation by benchmark 

The actual resources would be pending load testing, here we just estimat based on rough benchmark:  

- Application Server
  - Nginx: assume it can support 1K TPS per server (depends on hardware)
  - So
    - we need average 17 servers deployed in 3 AZ (considering 3 AZ policy)
    - scale 30%, we would need 17*1.3=24 servers
    - considering peak traffic, we will need to setup autoscaling to reach 24*3=72 servers
- Cache:
  - Redis: Setting maxmemory to 0 results into no memory limits. 
    - This is the default behavior for 64 bit systems, 
    - While 32 bit systems use an implicit memory limit of 3 GB.
  - So
    - If we use 64-bit server and each with 100 GB RAM, we need 150 servers
- Disk:
  - DyanmoDB: 
    - each partition (server) can only support 10 GB of keys
    - each partition can support 3,000 RCUs or 1,000 WCUs (per [DynamoDB Doc](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-partition-key-design.html))
  - So
    - If using UUID as key (each UUID is 16 characters), there would be 18 TB * (16/1000) = 288 GB of keys
    - DynamoDB needs to setup 29 partitions

#### Common benchmark

Usually:

- TPS < 100：1 single general server is enough
- TPS < 1K: a good server with better hardware (need to consider single point of failure)
- TPS >= 1M: cluster


- webserver (e.g. Nginx)
  - 1k TPS
  - for laptop usually it is 100 TPS
- SQL server (e.g. MySQL)
  - 1k TPS
- NoSQL (e.g. MongoDB, Cassandra, DyanmoDB)
  - 10k TPS
  - for DynamoDB, it is 4K TPS per partition (3K read and 1K write)
- Cache (e.g. redis, memcached)
  - 1M TPS


## 3. High-level Design / System View

                                            Notification
                                               |
                                               V
Client -- <LB> -- Webserver -- <LB+Cache> -- Application Server -- <LB> -- Persistence (DB/File)
                                               ^
                                               |
                                               data trail (Monitoring/Auditing/DW Analysis)

Can talk about more details like:
1. separate read and write use case
2. separate large file (picture/video/file) by distributed file storage system
3. async process like notification (subscribe/publisher)
4. load balancer is required since we analyzed that how many servers are required
5. cache would be required since we have latency SLA 

NOTE:
- Just list out possible blocks, and tell interviewer we will analyze details in component level design

## 4. Component level Design

NOTE:
- You can ask interviewers which component he/she wants you to explain further. 
- Talk about options and pros and cons
- Find as many bottlenecks as possible, discuss different approaches to mitigate them.
  - any single point of failure 
    - --> how to mitigate 
  - data replica
    - --> can still serve users even losing some 
  - server redundant 
    - --> some server down would not cause total system down
  - instrument/monitoring/ticketing the performance 
    - --> Do we get alerts whenever critical components fail or their performance degrade

### 4.1. Persistence Layer

#### Data schema

Data model can help guide towards data partitioning and management. 
Candidate should be able to identify various entities of the system, how they will interact with each other and different aspect of data management like storage, transportation, encryption,

- e.g. for twitter
  - User: UserID, Name, Email, DoB, CreationData, LastLogin, etc.
  - Tweet: TweetID, Content, TweetLocation, NumberOfLikes, TimeStamp, etc.
  - UserFollowos: UserdID1, UserID2
  - FavoriteTweets: UserID, TweetID, TimeStamp

- Which database system should we use? 
  - Would NoSQL like Cassandra best fits our needs, or we should use MySQL-like solution. 
  - What kind of block storage should we use to store photos and videos

#### SQL vs NoSQL

- 

#### DB Partitioning


#### Distributed File System



### 4.2. Application Layer


#### API design

- CRUD API + business API
- flow chart of each use case

- e.g. for twitter API
  - postTweet(user_id, tweet_data, tweet_location, user_location, timestamp, …)
  - generateTimeline(user_id, current_time, user_location, …)
  - markTweetFavorite(user_id, tweet_id, timestamp, …)
- e.g. for messenger flow chart
  1. userA is connected with server1
  2. userA wants to send message to userB
  3. server1 would lookup in "lookup component" to find server2 that is connected with userB
  4. server1 forwards message to server2
  5. server1 returns ACK to userA --> "sent"
  6. server1 stores message in DB asynchronously
  7. server2 sends message to userB
  8. userB returns ACK to server2
  9. server2 forwards ACK to server1
  10. serve1 send ACK to userA again --> "received"

#### Business Logic

We cannot use too much template, since each use case in each problem is different.  
Here are some core problem in different problems:

### 4.3 WebServer Layer 

Not much to design here, since it's only a gateway+LB, and it is usually coverred by use case already.  

Can refer to "Load_Balancer.md" to see how we use Nginx + ELB here.

### 4.4 Client Layer

Only touch here if we need client layer to do something:
1. In DropBox design, client need to upload/chunk file, maintain metadata, etc.
2. 


## Other topics

### Some questions the interviewer may want to clarify during your talk:
- e.g. sample problem to store a huge amount of data
  - how should we partition our data to distribute it to multiple databases? 
  - Should we try to store all the data of a user on the same database? What issue can it cause?
- e.g. sample problem to handle hot users  
  - How would we handle hot users, who tweet a lot or follow lots of people?
- e.g. some other questions
  - Since user’s timeline will contain most recent (and relevant) tweets, should we try to store our data in such a way that is optimized to scan latest tweets?
  - How much and at which layer should we introduce cache to speed things up?
  - What components need better load balancing?


### Something you may want to talk proactively during the design:

- Scalability
- Availability
  - failover
  - single point of failure
  - peak traffic
  - replication
  - fault tolerance
- Security
  - Authentication & Authorization -- access control
  - Data encryption
  - DDos -- abnormal traffic detection
- Transaction
  - ACID operation

### Some high level thoughts on system
- which CAP principle is given the priority, which is sacrified?
- what is the KPI successful criteria
- what metrics to monitor 
 

