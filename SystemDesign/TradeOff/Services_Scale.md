# Scale of common services

- Nginx：能轻松的处理100k问题，内存越大，能处理的并发量越高
- Redis: https://redis.io/topics/benchmarks 表明，对于GET/SET来说，QPS 10-100k没啥大问题
- MySQL: https://www.mysql.com/why-mysql/benchmarks/ 表明，对于只读，QPS 几百k没啥问题，对于写，MySQL 5.7 QPS 100k 几乎是上限
- PG: https://www.percona.com/blog/2017/01/06/millions-queries-per-second-postgresql-and-mysql-peaceful-battle-at-modern-demanding-workloads/ 也是差不多


最简单粗暴也是实践中最常用的应对方案就是：升级机器、加机器(所以架构的时候要考虑好水平扩展)
大多数应用都是读大于写，解决方案很简单：加缓存+读写分离
对于写大于读的方案，见 (关系型)数据库优化总结
Nginx：能轻松的处理c100k问题，内存越大，能处理的并发量越高
Redis: https://redis.io/topics/benchmarks 表明，对于GET/SET来说，QPS 10-100k没啥大问题
MySQL: https://www.mysql.com/why-mysql/benchmarks/ 表明，对于只读，QPS 几百k没啥问题，对于写，MySQL 5.7 QPS 100k 几乎是上限
PG: https://www.percona.com/blog/2017/01/06/millions-queries-per-second-postgresql-and-mysql-peaceful-battle-at-modern-demanding-workloads/ 也是差不多
B, M, G, T, PB之间的关系换算要清楚
需要长时间处理的任务或者是强依赖网络(而网络不确定性大的问题)，妥妥的用队列，例如消息推送
性能优化套路：加机器 - 加缓存 - 优化数据库索引 - 垂直拆数据库表 - 水平拆数据库表 - 垂直分库 - 水平分库