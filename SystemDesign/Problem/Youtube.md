# Youtube / Netflix

## Requirements

### Critical requirements

- upload videos.
- share and view videos.
- searches based on video titles.
- record stats of videos, e.g., likes/dislikes, total number of views, etc.
- Users should be able to add and view comments on videos

### Performance requirements

- highly reliable --> not lose video
- highly available --> Consistency can take a hit --> if a user doesn’t see a video for a while, it should be fine
- low latency --> real time experience while watching videos and should not feel any lag

## Scale estimation

- 1.5 billion total users, in which 800 millions are DAU
- assume each user watches 5 videos per day
  - Traffic estimation:
    - read:
      - average: 
        - 800 million * 5 / 1 day = 40000million/100K = 40K TPS
      - peak (assume 3 times of average)
        - 40K TPS * 3 = 120 TPS
    - write (assume upload:view ratio is 1:200):
      - average: 
        - 40K / 200 = 200 TPS
      - peak (assume 3 times of average):
	    - 200 TPS * 3 = 600 TPS
- assume each video is about 100 MB (ignoring video compression, etc.)
  - Throughput (can be converted into bandwidth):
    - ingress (peak write):
      - 600 * 100 MB = 60 GB/s
    - egress (peak read):
      - 120K * 100 MB = 12000 GB /s = 12 TB/s
- assume each video needs to be stored forever
  - assume each video would be stored in x formats:
    - MP4
    - AVI
    - WMV
  - assume each video would be stored in y qualities:
    - 1080p
    - 720p
    －480p
  - basically, without consider replication, each video would have `x*y` copies in system
    - assume x=1 and y=1
    - Storage for a year would be (average write TPS):
      - 200 TPS * (1 * 1) * 30M seconds in one year * 100MB size = 600 PB  

## High-level System Design

```python
Client (chunker) <--> WebServer (LB) <--> Application Server  <--> processing queue <--> Encoder
	^					^					^     ^    										^
	|					|					|	  |	   										|
	V 					V					V     V    										V
	CDN		  video/image cache			user DB  metadata DB 						file storage

```

## Persistency

### DB

Metadata table --> video id, title, thumbnail, user  
Comment table --> comment id
User table --> userId

### file storage
S3/HDFS/GlusterFS


### Sharding/Partitioning

1. By userId
  - pros:
    - easy to querying for a user
  - cons:
    - What if a user becomes popular? There could be a lot of queries on the server holding that user, creating a performance bottleneck. This will affect the overall performance of our service.
  - Over time, some users can end up storing a lot of videos compared to others. Maintaining a uniform distribution of growing user’s data is quite difficult.”
2. VideoID
  - pros:
    - evenly distributed
  - cons:
    - need an aggregation component to aggregate for a user

## Application Server

1. Separate read and write
2. for read
  - we can split the video by chunks, each chunk would stay in different storage servers, so we can return 1st 100 chunks first to let user watch it faster
  - we can also have clients to make multiple http requests for chunks in parallel, clients can initlaly gives one http request to get 100 chunks and an groupId, and then initial 20 requests for the rest of the chunks in parallel with groupId
3. for write
  - using queue to achieve async storing




### Where would thumbnails be stored


- Thumbnails are small files, say maximum 5KB each.
- Read traffic for thumbnails will be huge compared to videos
  - Users will be watching one video at a time, but they might be looking at a page that has 20 thumbnails of other videos


“Bigtable can be a reasonable choice here, as it combines multiple files into one block to store on the disk and is very efficient in reading a small amount of data. Both of these are the two biggest requirements of our service. Keeping hot thumbnails in the cache will also help in improving the latencies, and given that thumbnails files are small in size, we can easily cache a large number of such files in memory.”


### Video upload resume

Client to resend the not accepted chunks again

### when will video be ready to view?

After all chunks are done, encoder server would tells application server it is ready

--> can use a life cycle management service

## Load balancing

Some videos are too hot ?
--> auto replicate the hot videos on seaparte storage server, and load balance between them to split the traffic


## Cache

1. For metadata

20% data in memory cache

2. For videos/images

A CDN is a system of distributed servers that deliver web content to a user based on the geographic locations of the user, the origin of the web page and a content delivery server.

Our service can move most popular videos to CDNs:

● 	CDNs replicate content in multiple places. There’s a better chance of videos being closer to the user and with fewer hops, videos will stream from a friendlier network.
● 	CDN machines make heavy use of caching and can mostly serve videos out of memory

As for Less popular videos, aka long-tailed videos (1-20 views per day) that are not cached by CDNs can be served by our servers in various data centers.”



## Fault Tolerance
We should use *Consistent Hashing* for distribution among database servers. Consistent hashing will not only help in replacing a dead server but also help in distributing load among servers.”





