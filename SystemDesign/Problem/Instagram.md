# Design Instagram


“One simple approach for storing the above schema would be to use an RDBMS like MySQL since we require joins. But relational databases come with their challenges, especially when we need to scale them. For details, please take a look at SQL vs. NoSQL.”

“Writes or photo uploads could be slow as they have to go to the disk, whereas reads could be faster, especially, if they are being served from cache.”

“we can run a redundant secondary copy of the service that is not serving any traffic but whenever primary has any problem it can take control after the failover.”

## Data Sharding

### 1. Partitioning based on UserID

Cons:
- How would we handle hot users? Several people follow such hot users, and any photo they upload is seen by a lot of other people.
- Some users will have a lot of photos compared to others, thus making a non-uniform distribution of storage.
- What if we cannot store all pictures of a user on one shard? If we distribute photos of a user onto multiple shards, will it cause higher latencies?
- Storing all pictures of a user on one shard can cause issues like unavailability of all of the user’s data if that shard is down or higher latency if it is serving high load etc.

#### 2. Partitioning based on PhotoID 

If we can generate unique PhotoIDs first and then find shard number through PhotoID % 200, this can solve above problems. We would not need to append ShardID with PhotoID in this case as PhotoID will itself be unique throughout the system.

How can we generate PhotoIDs? 
- Here we cannot have an auto-incrementing sequence in each shard to define PhotoID since we need to have PhotoID first to find the shard where it will be stored. 

1. dedicate a separate database instance to generate auto-incrementing IDs.
2. using UUID directly

## How can we plan for future growth of our system? 

We can have a large number of logical partitions to accommodate future data growth, 

So whenever we feel that a certain database server has a lot of data, we can migrate some logical partitions from it to another server. 

We can maintain a config file (or a separate database) that can map our logical partitions to database servers; this will enable us to move partitions around easily. Whenever we want to move a partition, we just have to update the config file to announce the change.

## Ranking and Timeline Generation

1. Fetch at run time
- For simplicity, let’s assume we need to fetch top 100 photos for a user’s timeline. 
- Our application server will first get a list of people the user follows and then fetches metadata info of latest 100 photos from each user
- In the final step, the server will submit all these photos to our ranking algorithm which will determine the top 100 photos (based on recency, likeness, etc.) to be returned to the user. 

pros: easy
cons: latency high

2. Pre-generating the timeline --> it likes to cache the data in database

We can have dedicated servers that are continuously generating users’ timelines and storing them in a ‘UserTimeline’ table. So whenever any user needs the latest photos for their timeline, we will simply query this table and return the results to the user.

## What are the different approaches for sending timeline data to the users?

1. Pull  
Clients can pull the timeline data from the server on a regular basis or manually whenever they need it. 

pros: 
- less work at server side, server just wait for API call

cons: 
- New data might not be shown to the users until clients issue a pull request
- Most of the time pull requests will result in an empty response if there is no new data

2. Push: 

Servers can push new data to the users as soon as it is available. 

pros:
- guarantee there is new data

cons:
- efforts for users to maintain a Long Poll request with the server for receiving the updates, or using service side pushing or websocket
- when a user has a lot of follows or a celebrity user who has millions of followers; in this case, the server has to push updates quite frequently.

3. Hybrid: 

We can adopt a hybrid approach. 

option1:  
- for users with high followings --> pull mode 
- for users who have a few hundred (or thousand) follows --> push mode 

option2:
- server pushes updates to all the users not more than a certain frequency
- letting users with a lot of follows/updates to regularly pull data


For a detailed discussion about timeline generation, take a look at *Designing Facebook’s Newsfeed*


## How to maintain the time sequence of photos?

This can be done efficiently if we can make photo creation time part of the PhotoID. Since we will have a primary index on PhotoID, it will be quite quick to find latest PhotoIDs.

We can use epoch time for this. 

Let’s say our PhotoID will have two parts
- 1st part will be representing epoch seconds
- 2nd part will be an auto-incrementing sequence. 

So to make a new PhotoID, we can take the current epoch time and append an auto incrementing ID from our key generating DB. We can figure out shard number from this PhotoID ( PhotoID % 200) and store the photo there.”


We will discuss more details about this technique under ‘Data Sharding’ in *Designing Twitter* 


## Cache and Load balancing

“To serve globally distributed users, our service needs a massive-scale photo delivery system. Our service should push its content closer to the user using a large number of geographically distributed photo cache servers and use CDNs (for details see Caching).”



 


