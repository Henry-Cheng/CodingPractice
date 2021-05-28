# Twitter Search

## Requirement Analysis

### Critical requirements

- The search query will consist of multiple words combined with AND/OR.


## High-level system design

```python

client --> LB --> Application Server --> storage
                       						|
                       						V
                       						lambda
                       						|
                       						V
                     index server (Elastic Search)
```

## Application Layer

### API

Signature:
`List<Twitter> getRecommendation(api_dev_token, search_terms, page_size, page_token)`

Response would be JSON tempalte with list of twitts.   

## Sharding

1. Shard based on word
- pros:
  - fast searching since all twitts are on the same server
- cons:
  - unbalance resource
  - unbalance traffic

2. Shard based on twitter id
- pros:
  - balanced resource nad traffic
- cons:
  - need an "aggregator" since each search request would hit all servers, and each searver would return partial data, and we can aggregate them back to one response to user


## Fault tolerance

### What if primary ES server is down?

Using redundant server for each partiion, each one has full copy 

- option1: active-passive mode
  - pros:
    - ...
  - cons:
    - waste resource of passive server
    - "cold start" when redirect traffic 
- option2: active-active mode
  - pros:
    - can warm both/multiple servers all the time
  - cons:
    - still waste some resources on each server

### What if all servers in a partition are down, how to recover?

1. short-term: using snapshot 
- pros:
  - recover fast to avoid user impact
- cons:
  - stale data

2. long-term: backfill from database (after shor-term is used)
- pros:
  - latest data
- cons:
  - If we use twitterId to partition, then it's hard to tell which twitts are used to store on the server --> need to store reverse indexing --> store serverId of each twitter on database so that we can easily tell which twitt is missed

### Rank based on some criterias

Rank/sort the search results by social graph distance, popularity, relevance.



