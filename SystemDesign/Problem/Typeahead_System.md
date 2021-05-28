# Typeahead System

This problem is more like a feature design instead of system design.  

Reference:
https://www.youtube.com/watch?v=uIqvbYVBiCI&t=1s
https://medium.com/@prefixyteam/how-we-built-prefixy-a-scalable-prefix-search-service-for-powering-autocomplete-c20f98e2eff1
http://www.noteanddata.com/interview-problems-system-design-learning-notes-autocomplete.html
https://www.raychase.net/6299
https://www.1point3acres.com/bbs/thread-558127-1-1.html
[ElasticSearch uses trie](https://www.sysapi.com/article/608647.html)


## Real situation of Google auto-complete

- if you type in the search textbox by "what is the first"
- now your can open the developer tool to see whenever a char is typed, google would send a http request to server
- each http response includes top 10 recommended terms
- the response also has "expires" and "Cache-Control(max-age=3600)" to indicate how long this data can be cached in browser 

## Requirements

### Critical functional requirements

- user types in prefix, service should suggest top 10 terms by prefix
  - "top 10" is evaluated by "relevant score"
  - we can assume "relevant score" is the frequence for simplificity 
- where are the term comes from?
  - search logs

### Performance requirement

- 200ms SLA

### Non-critical functional requirements (optional)
- case senstive words
- personalization ?
- spelling mistakes ?
- newly added account appear instantly in the scope of the search
  - some hot topics happened, but not search logs have not processed it yet, how to apply?
- not for “query auto complete”(like the Google search-box dropdown)
  - but for displaying actual search results including generic typeahead:
    - network-agnostic: results from a global ranking schema, like popularity 
    - network typeahead: results from user’s 1st and 2nd-degree network connections and People You May Know scores

## Scale estimation

Assume there are 5 billion search per day like Google
--> Average TPS = 5 billion / 100K = 50K /s
--> Peak traffic = 50K * 3 = 150 K /s

Assume each request size is about 500 bytes
 - the payload should be small since it's a simple sentence/word, but it may contains some other information like user tokens etc., so assume it's 500 bytes
Assume the response size is about 5KB

- Peak throughput
  - ingress: 150k TPS * 500 bytes = 75MB/s
  - egress: 150k TPS * 5KB = 750MB /s 


Storage:
- log data?

NOTE:
1. Storage persistence is hard to measure for now, since we don't know the data structure yet, we can come back to it after analyze the data structure.  
2. If using unicode, each character is 2 byte


## High level system design

Data flow chart: 


This type of quesiton is read-heavy and availability  

```python

<online>
								cache
								^
								|
client --> webserver(LB) --> application server 			--> persistence layer
                                |									^
                                |									|
                                v									|
<offline> 						  
								log puller --> mapp reducer --> scorer 


```


### Basic Data structure

- Option1: General Prefix-Tree/Trie (online)
  - each node stores the current character and HashMap to all child characters
  - pros:
    - space efficient
  - cons:
    - high latency, since it is `O(L) + O(N) + O(NlogK)`
    - in which 
      - L is the length of prefix, it is the time we need to find the prefix node
      - N is the number of complete words, assume we have N words for the prefix
      - K is the `top K` terms we need to return, here NlogK is to use heap to find `top K`
- Option2: Trie with limited L and K (online)
  - same as option1, if we limit the longest prefix we can support, and the top K words we can return, let's say they are 20 and 5
  - the time complexity would be: `O(20) + O(N) + O(Nlog5) = O(N) + O(1)`
- Option3: Trie + offline preparation (online + offline)
  - instead of searching `N terms` online, we can store the `N terms` together with frequence at each prefix node
    - pros: 
      - saves effort to search for `N terms`, we can get a list of terms immediately after reaching a node
      - time complexity is `O(L) + O(NlogK)`
        - if we limit L and K size it would be `O(N) + O(1)`
    - cons:
      - duplicate words being stored at each node
        - but we can sacrifice the space for speed
  - we can do even further to prepare the `top K` offline, so that the `NlogK` can be saved
    - pros:
      - saves some space on each node, since we only store `top K` now, though it's still duplicates..
      - now time complexity is only O(L)
    - cons:
      - offline work would be more efforts, but it's offline, we don't care
- Option4: HashMap
  - Now option3 is good enough to support `O(L)`, we can do even better to `O(1)` by storing all "prefix to top K mapping" into hashamp
  - pros:
    - `O(1)` read
    - can leverage NoSQL key-value pair easily, and also Redis key-value pair to store
  - cons:
    - hard to say if it's real `O(1)` and faster than option3
      - since hashmap needs to generate hashValue for each prefix string, it must go through each charaters, which makes no difference to option3 when traversing in Trie.  
      - but we can verify by load testing on option3 and option4
    - offline job would need duplicate calculating `top K` since we loose the prefix relationship 
      - for option3, we can traverse from root to each compelte word, count the frequency and then recursively go back to parent node to calculate `top K`; in this way, each parent node can use the calculatoin result of child node
      - for option4, each prefix cannot leverage calculation result of child node, it must re-calculate everything for each prefix, that is much more efforts

Basically, if we use option3, it's `O(L)` or `O(1)` is L is limited, and we can store it as file in persistency layer, so that whenever serve restarts, we can reuse the data from file and calculate the `diff` by timestamp.  

If we use option4, it's `O(1)` but much more space required, and we can store it in NoSQL database. 

### API

Signature:
`List<Term> getRecommendation(api_dev_token, prefix, page_size, page_token)`

Response would be JSON tempalte with top recommended objects.  

NOTE:
- this API should not only return `top K strings`, since in some system design like `LinkedIn`, we should return `top K relevant contacts`; so here we just say we return a list of terms in order.

### How to find top suggestion?

Map-reduce to parse logs, extract terms, and then using heap to calculate top K.  

Actually the top suggestion may not be based "frequency", we can build a component called "scorer" to calculate the releveant scores of each term.

### What if we have hot topics?


### Partitioning

Here the paritioning is at cache layer instead of persistence layer.  
Since the cache storage is at GB level, we can achieve it at 

1. Partition basd on static range
  - prefix starts with 'a' would be in sever1, 'b' would be in server2
  - pros:
    - easy to achieve
  - cons:
    - unbalanced traffic and resource required on each server
2. Partition based on maximum capacity of server
  - We can keep storing data on a server as long as it has memory available. Whenever a sub-tree cannot fit into a server, we break our partition there to assign that range to this server and move on the next server to repeat this process.
  - pros:
    - balanced resource
  - cons:
    - data split on multiple servers, need to have `aggregator` to collect all of them
    - still have unbalanced traffic
3. Partition based on hash of the prefix
  - using hash function to map each prefix to hashValue which represents a server id
  - can use consistency hash to support add/delete servers
  - pros:
    - balanced traffic
  - cons:
    - data still splits on multiple servers, need to have `aggregator`


### Hot topic 

Can have both `pull` and `push` mode.  
- When hot topic happens, monitor it and push event to proactively trigger "log puller". 
- add weight on different complete words when calcualting relevant scores




