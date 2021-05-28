# Facebook Messenger

## Requirements

- one to one chat
- group chat
- online/offline status of users
- perisstent storage of chat history
- push notifications


## Persistence Layer

### Data model:

- User table
- Chat table
- Thread table

### Database choice

We cannot use RDBMS like MySQL or NoSQL like MongoDB  
--> because we cannot afford to read/write a row from the database every time a user receives/sends a message. 
--> This will make not only basic operations of our service to run with high latency but also create a huge load on databases.

The above saying are from "Grokking system design", but could be wrong if we only async write to database.  

It is recommended to wide-column database solution like HBase/Cassandra.  
e.g.  
- HBase is a column-oriented key-value NoSQL database that can store multiple values against one key into multiple columns.  
- HBase is modeled after Google’s BigTable and runs on top of Hadoop Distributed File System (HDFS).   
- HBase groups data together to store new data in a memory buffer and once the buffer is full, it dumps the data to the disk.  
- This way of storage not only helps storing a lot of small data quickly but also fetching rows by the key or scanning ranges of rows.  
- HBase is also an efficient database to store variable size data, which is also required by our service.



### Partitioning
Partitioning based on:
- ChatId
  - pros:
    - id could be unique
  - cons:
    - one user's chats could be spreaded on different servers, slow to combine them into chat history
- UserId
  - pros:
    - all user's data on the same machine
  - cons: 
    - the paritioning needs some mechanism
    - 1. range based partitioning
      - un-even, since some userId prefix is more common
    - 2. hash
      - assuming we have 1000 DB servers, using `partitionId = hash(userId) % 1000`
      - when add/remove machines, the hash mapping could change
    - 3. Consistent hash
      - makes sure whenever add/remove machines, only one node would be migrated and all the others hash mapping are still valid
- ThreadId
  - pros:
    - id itself is unique enough, e.g. UUID
  - cons:
    - thread would be spread on different machines


## Application Layer

### Flow Chart of use case

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


### How to receive message?

- Option1:
  - pull
    - pros:
      - server side is simple
    - cons:
      - server side needs to maintain all messages before client pulls
      - client needs to frequently pulls, waste resource even without message
- Option2:
  - push
    - pros:
      - only push when new message arrives, saves resource
    - cons:
      - server needs to maintain connection with clients

### How to maintain connections between client and server?  

- option1:
  - long polling
    - If the server has no new data for the client when the poll is received, instead of sending an empty response, the server holds the request open and waits for response information to become available. 
    - Once it does have new information, the server immediately sends the response to the client, completing the open request. 
    - Upon receipt of the server response, the client can immediately issue another server request for future updates. 
    - The long polling request can timeout or can receive a disconnect from the server, in that case, the client has to open a new request
    - pros:
      - 
    - cons:
      - the request may temporaily closed, then wait a short period of time for the client to re-initiate the request
  - websocket
    - pros:
      - bi-directional communication, clients can use it to send message also
      - clients can also send status change to server
    - cons:
      - 

### How to know which server connects with which client? 

We need a dedicated component in front of application servers (not in web layer!!) as "load balancer" to map client to server id, and then all servers could check that "load balancer" to find the message receiver's server.


### how to maintain status?

- option1:
  - pull
    - each user periodically pull all friend's status
    - cons:
      - waster too much resource
  - push
    - whenever user status changes, broadcast it to a topic (each user has his own topic), and any friend needs to subscribe to the topic to get notified 
    - pros: saves resource

### What if receiver goes offline when sender sends the message?
- server can send ACK to sender abotut "delivery failure"
- If it is a temporary disconnect, e.g., the receiver’s long-poll request just timed out, then server could do the retry


### How to maintain the sequencing of the messages

If userA sends to userB, and in parallel userB sends to userA, because of the delay, they could see different orders at their sides.  
-->  
We can maintain a thread table for each user, and inside thread table we can store the sequenceId of each chat 
--> even though userA and userB could have different thread (aka different message sequence), but at least for their own devices the sequence is the same.


### How to async store message in DB?

- Option1
  - background thread to do the job
    - pros:
      - simple to implement
    - cons: 
      - we never know if it fails
- Option2
  - async request with message queue
    - pros:
      - message queue can gurantee at least one time delivery
      - supports retry if delivery failed
      - can have DLQ or log failed message
      - can persist data if server is down
      - can avoid spike traffic to database since we can read from queue with constant speed
    - cons:
      - need some effrot to design such a message queue






