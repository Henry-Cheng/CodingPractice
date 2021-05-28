# Tiny URL

## Background

### how does the browser work?

When user typing http://t.cn/RlB2PdD in browswer:
1. DNS server would lookup http://t.cn, resolve it to be IP address 74.125.225.72 and return it to client (browswer or app) --> for more details please see "What happened after input url in browser?"
2. Now browser/app has the IP address of the server, send HTTP GET request to access http://t.cn/RlB2PdD
3. Now the server http://t.cn will find the corresponding long URL by short code `RlB2PdD`
4. Here the server can respond HTTP request with `location=<long URL>` and `Status Code = 301`
5. Since 301 is "permanant redirect", browser/app would send another HTTP request for the long URL 

### what is the difference of 301, 302 and 303? 

- 301
  - temporary redirect
  - browser would record the long URL and reuse it in the future
  - search engine would use the long URL
  - tinyURL company can only see unique customer usage data
  - 301 can be used together with timeout
- 302
  - permenant redirect
  - browser would record short URL next time
  - search engine would use short URL
  - tinyURL company can recrod more data
- 303
  - 303 is part of http1.1 (while 301 and 302 are http1.0)
  - 303 allows to to change HTTP request after redicting (e.g. change POST to be GET), while 301 and 302 are not allow by protocol (even though many browser supports it...)


Browser by default use 302.

### what scenario to use 301/302?

- 301
  - http to https
    - e.g. when ssl certificate is added to http domain, redirect user to https domain
  - secondary domain jumps to main domain
    - e.g. http://www.abc.com to http://abc.com
  - 404 redirects to new page
    - transfer the weight of the page to new one
  - old domain is deprecated, using new domain

301 is good for google search engine and crawl spider, since the weight would be transferred.

- 302
  - usually happened when website is under maintainance, in case it impacts user experience, redirects to new page temporaily.
  - temporary one-time validation-use url sent by text/weibo/twitter which has 140 char length limit


### security concern

302 can lead to URL hijacking



### which one should we choose? 301 or 302?

这也是一个有意思的问题。这个问题主要是考察你对301和302的理解，以及浏览器缓存机制的理解。

301是永久重定向，302是临时重定向。短地址一经生成就不会变化，所以用301是符合http语义的。但是如果用了301， Google，百度等搜索引擎，搜索的时候会直接展示真实地址，那我们就无法统计到短地址被点击的次数了，也无法收集用户的Cookie, User Agent 等信息，这些信息可以用来做很多有意思的大数据分析，也是短网址服务商的主要盈利来源。

所以，正确答案是302重定向。

Another option is to use 301 with timeout: https://stackoverflow.com/questions/1562367/how-do-short-urls-services-work


it is indeed HTTP 301 that is used, however, with a timestamp. This turns it into a Moved not Moved Permanently. This is a subtle difference. By adding the timestamp, the browser considers it should check whether the resource is changed or not when this timeout it reached. Others, like is.gd, use a normal 301 Moved Permanently and the browser doesn't need to re-check (but often will). Finally, services like url4.eu do not redirect at all, but show you an advertisement first. With the 301 the services can still count unique visitors, but not all hits.

## Requirement analysis

### Special requirement

1. Is the API used externally?
2. Is the non-registered user allowed to access?


### 1 to 1 or 1 to many?
https://soulmachine.gitbooks.io/system-design/content/cn/tinyurl.html

一对一还是一对多映射？
一个长网址，对应一个短网址，还是可以对应多个短网址？ 这也是个重大选择问题

一般而言，一个长网址，在不同的地点，不同的用户等情况下，生成的短网址应该不一样，这样，在后端数据库中，可以更好的进行数据分析。如果一个长网址与一个短网址一一对应，那么在数据库中，仅有一行数据，无法区分不同的来源，就无法做数据分析了。

以这个7位长度的短网址作为唯一ID，这个ID下可以挂各种信息，比如生成该网址的用户名，所在网站，HTTP头部的 User Agent等信息，收集了这些信息，才有可能在后面做大数据分析，挖掘数据的价值。短网址服务商的一大盈利来源就是这些数据。

正确答案：一对多

## Scale Analysis

5.27 billion of website in the world: https://www.worldwidewebsize.com/


## Converting Algorithm

### 0. length of the tiny url
https://soulmachine.gitbooks.io/system-design/content/cn/tinyurl.html

短网址的长度
当前互联网上的网页总数大概是 45亿(参考 http://www.worldwidewebsize.com)，45亿超过了 2^{32}=42949672962
​32
​​ =4294967296，但远远小于64位整数的上限值，那么用一个64位整数足够了。

微博的短网址服务用的是长度为7的字符串，这个字符串可以看做是62进制的数，那么最大能表示{62}^7=352161460620862
​7
​​ =3521614606208个网址，远远大于45亿。所以长度为7就足够了。

一个64位整数如何转化为字符串呢？，假设我们只是用大小写字母加数字，那么可以看做是62进制数，log_{62} {(2^{64}-1)}=10.7log
​62
​​ (2
​64
​​ −1)=10.7，即字符串最长11就足够了。

实际生产中，还可以再短一点，比如新浪微博采用的长度就是7，因为 62^7=352161460620862
​7
​​ =3521614606208，这个量级远远超过互联网上的URL总数了，绝对够用了。

现代的web服务器（例如Apache, Nginx）大部分都区分URL里的大小写了，所以用大小写字母来区分不同的URL是没问题的。

因此，正确答案：长度不超过7的字符串，由大小写字母加数字共62个字母组成


### 1. Online Algorithm

1. Unique Hash (MD5/SHA1/SHA256)


md5把一个string转化成128位二进制数，一般用32位十六进制数(16byte)表示：

http://site.douban.com/chuan -> c93a360dc7f3eb093ab6e304db516653

sha1把一个string转化成160位二进制数，一般用40位十六进制数(20byte)表示：

http://site.douban.com/chuan -> dff85871a72c73c3eae09e39ffe97aea63047094

The hashed result can be Base64 encoded to make it display-able.  


If we use the MD5 algorithm as our hash function, it’ll produce a 128-bit hash value.  
Then after base64 encoding, we’ll get a string having more than 20 characters, how will we chose our key then? 
 - We can take the first 6 (or 8) letters for the key. This could result in key duplication though, upon which we can choose some other characters out of the encoding string or swap some characters.


MD5 and SHA256 可以保证哈希值分布很随机，但是冲突是不可避免的，任何一个哈希算法都不可避免有冲突。

- pros:
  - 简单，可以根据long_url直接生成；假设一个url中一个char占两个字节，平均长度为30的话，原url占大小60byte,hash之后要16byte。我们可以取md5的前6位,这样就更节省。
- cons：
  - 难以保证哈希算法没有冲突
  - workaround
    1. 拿(long_url + timestamp)来哈希；
    2.冲突的话，重试(timestamp会变，会生成新的hash)

综上，流量不多时，可行；但是，当url超过了假设1 billion的时候，冲突会非常多，效率非常低。


2. base62:
将六位的short_url看做是一个62进制数(0-9,a-z,A-Z)，可以表示62^6=570亿个数。整个互联网的网页数在trillion级别，即一万亿这个级别。6位足够。
每个short_url对应一个十进制整数，这个整数就可以是sql数据库中的自增id，即auto_increment_id。

可以对原URL采用unique hash算法（MD5/SHA256等），然后对hash值进行编码为可以显示的编码格式（base36 ([a-z ,0-9]) or base62 ([A-Z, a-z, 0-9]) or base64([A-Z, a-z, 0-9，+，/]) ）。
如果采用base64编码，那么6个字符的key可以包含的数量：64^6 = 687亿，（远大于上面5年最长极限的300亿）。

编码步骤：
MD5 hash生成16个字节（128bit）hash值
对hash值进行Base64编码生成至少21个字符（base64是对6位数据进行编码128/6=21）
从21个字符中截取6个，这可能会导致出现重复的情况，可以考虑选取一些编码字符串外的其他字符，或者将其中一些位置对调以产生一个不重复的
编码机制存在的其他问题
多个用户输入同一个URL，将产生同一个key，违反唯一性原则
方案1 URL添加自增计数，此方案可能的问题是可能会溢出，且对性能可能产生影响
方案2 URL添加用户唯一id



