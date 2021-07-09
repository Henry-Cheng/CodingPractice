# caching

2 types of caching
1. client side cacheing
 - data stored in a browser
2. server side caching
  - 

## AWS caching options

1. CloudFront (CDN)
  - can also be usd for DDos mitigation
  - 3 ways to expire contents
    - TTl
    - change object name
    - invalidate object
2. caching web server sessions
  - ELB session management --> sticky session
  - DDB for state information
    - eg. online gaming site
3. databse caching --> elastic cache

