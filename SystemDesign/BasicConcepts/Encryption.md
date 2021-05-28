# Encryption and Encoding

https://segmentfault.com/a/1190000024469259

Common encryption algorithms:
https://www.cnblogs.com/littleatp/p/6192864.html


## what Tardis uses? -- Message digest algorithm
https://zhuanlan.zhihu.com/p/68455533


1. 消息摘要算法的主要特征是加密过程不需要密钥，并且经过加密的数据无法被解密.

只有输入相同的明文数据经过相同的消息摘要算法才能得到相同的密文。

消息摘要算法主要应用在“数字签名”领域，作为对明文的摘要算法。

著名的摘要算法有RSA公司的MD5算法和SHA-1算法及其大量的变体。

2. 特点：
- 无论输入的消息有多长，计算出来的消息摘要的长度总是固定的。
- 消息摘要看起来是“伪随机的”。也就是说对相同的信息求摘要结果相同。
- 消息轻微改变生成的摘要变化会很大
- 只能进行正向的信息摘要，而无法从摘要中恢复出任何的消息，甚至根本就找不到任何与原信息相关的信息

## what is encoding?

Base64 is encoding, it can be decoded also.  
Base64 can convert any string (or you can say binary sequence) into 64 ASCII char (A-Za-Z0-9+/), so that it can be printed out.
After Base64, the new string is longer than original sequence, the new length would be `original length / 3 * 4`