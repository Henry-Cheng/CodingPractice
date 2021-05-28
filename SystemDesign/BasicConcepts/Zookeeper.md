https://juejin.cn/post/6844903976719106056

Zookeeper is designed for `eventually consistence`, 

Scenario: 
1. Distributed Coordination -- like step function / herd to maintain lifecycle
  - A client submits a request, request firstly reaches systemA, then forward the request to systemB through mq. Since the communication between systemA and systemB are async, how could systemA knows when systemB completes the job?
    - `Zookeeper`: have systemA to register a listener in zookeeper for request, once systemB complets the request, systemB updates the status of the listener in zookeeper, and it notifies systemA
      - systemA and systemB need to be both onboarded to zookeeper
      - but what if client wants to know the result? we cannot onboard clients to zookeeper, that couples client and system (server)
      - zookeeper is more like on-demand coordination, not like step function and herd which handles the full lifecycle
    - SNS + lambda callback: client implements callback lambda to be notified by metrics/alarms generated inside systemA or systemB
2. Distributed Lock -- like [dynamoDB distributed lock](https://aws.amazon.com/blogs/database/building-distributed-locks-with-the-dynamodb-lock-client/)
  - SystemA and systemB both want to access/modify a resource, how to maintain consistence?
    - `Zookeeper`: whenever systemA or systemB acess a resource, it needs to create an ephemeral／temporary znode under [/lock znode](https://www.zhihu.com/question/65852003). e.g. SystemA generates node `/locks/id_000001`, SystemB generates node `/locks/id_00002`, the system with smaller number would have the lock and execute stuff, another system needs to wait for the smaller ephemeral／temporary znode to be deleted and then re-calculate who can get next lock. (NOTE: by zookeeper design, each znode can notify parent node)
3. Metadata Configuration -- like salt
4. Load balancing -- like ELB and Nginx