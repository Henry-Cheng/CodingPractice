# Web Crawler

## Requirements

### Critical Requirements

Crawl all webpages.

- Only crawl or also includes search engine?
  - it impacts the design, since it changes the way we store the crawling result
  - also decides whether we need to build revert index
- What to store after crawl?
  - generated webpage title
  - generated webpage abstraction
- HTML page only or includes media like audio/image/video
  - it would impact the "parseing module"
- HTTP protocol? How about FTP?
  - assume it's only HTTP, but the module can support multiple procotol
- what's the termination condition? we cannot crawl everything in the world
  - assume we only crawl 1 billion pages
- follow the Robots Exclusion Protocol?
  - each website would have a robots.txt file under its root directory, it tells which file/directory is allowed to fetch, and which is disallowed

### Performance

- Scalable to crawl entir web
- extensible to be modular

## Scale Estimation

Assume we want to crawl 1 billion pages in 4 weeks, we can calculate the TPS.  
Assume each HTML page is about 100KB, we can estimate the throughput.  
Assume we need to persiste forever, in total we need x PB data.


## High-level system design

General flow chart:

0. Prepare a list of seed url (the url would be popular sites by some ranking)
1. Pick a URL from the unvisited URL list.
2. Determine the IP Address of its host-name.
3. Establishing a connection to the host to download the corresponding document.
4. Parse the document contents to look for new URLs.
5. Add the new URLs to the list of unvisited URLs.
6. Process the downloaded document, e.g., store it or index its contents, etc.
7. Go back to step 1”


```python

trigger -->  scheduler (prioritizer)  <--  				url feeder/index  
				|													^						   
				|  												|			
				v													|			
		rate limiter(queue)			filter/deduper/validator --> url store
				|							^		
				v							|
         Page fetcher --> queue --> document analyzer 
				^							^
				|							|
				v							v 
			Internet				filter/deduper/validator --> document store
			   			   				
```

NOTE:
1. the rate limiter is to avoid overloading some websites, we can using FIFO queues to maintain the connection with one website, and avoid too much concurrent request to one site
2. to dudupe document and url
  - we can using 64-bit checksum by MD5 or SHA
3. we can consider build our own DNS server before accessing internet website
4. url deduper and validator can avoid circular access, it compares two sets of url to find duplicate
  - bloom filters
    - Bloom filters are a probabilistic data structure 
    - 空间复杂度是固定的常数O(m) --> m is the size of bit vector
    - 而检索时间复杂度是固定的常数O(k) --> k is the number of hash function
    - pros:
      - saves space a lot, can implement in memory
      - never has false negative
    - cons:
      - may result in false positive --> a url does not exists but still treated as visited
        - mitigation: using larger bit vector


### What algorithm/strategy

1. BFS or DFS?
- Usaully BFS
  - because the homepage of an website is more important, and if we have limited resource, we should prioritize the BFS
- but DFS is also important
  - to reduce the handshake overhead, we can use DFS to pull pages in same website

Can have a scheduler to balance between BFS and DFS 

2. Path ascending crawl

e.g. 
When given a seed URL of http://foo.com/a/b/page.html, it will attempt to crawl /a/b/, /a/, and /.

3. PageRank

Sort url by page rank, and then prioritize them differently

4. OIPC (Online Page Importance Computation)
在算法开始前，给所有页面一个相同的初始现金（现金）当下载了某个页面P之后，将P的现金分摊给所有从P中分析出的链接，并且将P的现金清空。对于待抓取URL队列中的所有页面按照现金数进行排序。

PageRank的的的每次需要迭代计算，而OPIC策略不需要迭代过程所以计算速度远远快与PageRank的的的，适合实时计算使用。


### Painpoint

1. Large volume of web pages
  - crawler needs to prioritize some pages
2. Pages change frequently or new pages frequently added to site
  - not always do the incremental crawling, can also re-pull everything from seed pages some times 
3. Crawler can take long time to complete, how to recover if failed in between
  - using url store to maintain snapshot, recover from snapshot


## Crawler Traps

1. Circular search --> using deduper
2. People intentionally build infinite loop to impact our crawler
  - people may make it on good purpose
  - e.g. Anti-spam traps are designed to catch crawlers used by spammers looking for email addresses
  - e.g. some sites use traps to catch search engine crawlers to boost their search ratings.”

AOPIC algorithm (Adaptive Online Page Importance Computation), can help mitigating common types of bot-traps. AOPIC solves this problem by using a credit system.

1. Start with a set of N seed pages.
2. Before crawling starts, allocate a fixed X amount of credit to each page.
3. Select a page P with the highest amount of credit (or select a random page if all pages have the same amount of credit).
4. Crawl page P (let’s say that P had 100 credits when it was crawled).
5. Extract all the links from page P (let’s say there are 10 of them).
6. Set the credits of P to 0.
7. Take a 10% “tax” and allocate it to a Lambda page.
8. Allocate an equal amount of credits each link found on page P from P’s original credit after subtracting the tax, so: (100 (P credits) - 10 (10% tax))/10 (links) = 9 credits per each link.
9. Repeat from step 3.


Since the Lambda page continuously collects the tax, eventually it will be the page with the largest amount of credit, and we’ll have to “crawl” it. By crawling the Lambda page, we just take its credits and distribute them equally to all the pages in our database.

Since bot traps only give internal links credits and they rarely get credit from the outside, they will continually leak credits (from taxation) to the Lambda page. The Lambda page will distribute that credits out to all the pages in the database evenly, and upon each cycle, the bot trap page will lose more and more credits until it has so little credits that it almost never gets crawled again. This will not happen with good pages because they often get credits from backlinks found on other pages.


