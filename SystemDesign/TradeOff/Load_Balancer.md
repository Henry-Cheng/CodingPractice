https://www.nginx.com/blog/aws-alb-vs-nginx-plus/

Difference of AWS ELB and Nginx

The following content are copied from

https://www.nginx.com/blog/nginx-plus-amazon-elastic-load-balancer-aws/

https://www.sumologic.com/blog/aws-elb-vs-nginx-load-balancer/

### LB can also be single pointer of failure

a load balancer must always itself be made highly available by some other means to achieve high-availability. 
- option1: 
  - using highly available LB, like AWS Elastic Load Balancing
- option2:
  - redundant lb by active-passive setup
    - The load-balancers then have some form of heart-beat between them, and if the active one fails, the passive will take over and become active.
- option3:
  - redundant lb by active-active setup
    - All LB are active. 所有的LBs都处理请求。 如果LB是直接面向外部的，DNS要知道所有的LB的地址。 如果LB是在内部的，内部应用的逻辑要知道所有的LB。
    - e.g.
      - Google AnyCast DNS service


### AWS ELB vs. Nginx Plus

- **Elastic Load Balancing (ELB)** automatically distributes your incoming application traffic across multiple targets, such as EC2 instances. It monitors the health of registered targets and routes traffic only to the healthy targets. 
  - Elastic Load Balancing supports two types of load balancers: Application Load Balancers and Classic Load Balancers.
- **Nginx Plus** is 
 - Load balancer – Distribute traffic efficiently across backend servers for reliable, high‑performance application delivery
 - Reverse proxy – Pass, modify, and manage requests between clients and backend servers
 - API gateway – Handle request routing, authentication, rate limiting, and SSL/TLS offload, and deliver API responses in real time (under 30 milliseconds)
 - Web server – Deliver content like the majority of the busiest sites on the Internet, with high performance, low resource demands, and a small memory footprint

Usually NLB and Nginx are used together (see [example](https://aws.amazon.com/blogs/opensource/network-load-balancer-nginx-ingress-controller-eks/)) 
－ A Network Load Balancer is capable of handling millions of requests per second while maintaining ultra-low latencies, making it ideal for load balancing TCP traffic. NLB is optimized to handle sudden and volatile traffic patterns while using a single static IP address per Availability Zone.

For example, in many stacks, ELB handles public-facing IPs and SSL termination, and balances traffic between multiple NGINX nodes across availability zones, while the NGINX servers handle caching and passing traffic to the actual application/service layer via fpm or as a reverse proxy—so you don’t necessarily need to think of ELB and NGINX as an either-or choice. You may best be served by using both.

![Image](https://github.com/Henry-Cheng/CodingPractice/raw/master/SystemDesign/Images/NGINX-with-ELB.png)


Route 53 Key Features ([source](https://ns1.com/resources/aws-dns))
- Traffic flow—routes end users to the endpoint that should provide the best user experience
- Latency-based routing—routes users to the AWS region that provides the lowest latency
- Geo DNS—routes users to an endpoint, depending on detected user geography
- Private DNS—for users of Amazon VPC, defines custom domain names without exposing DNS information publicly
- DNS failover—automatically redirects users to an alternative service in case of outage
- Health checks—monitors health and performance of applications
- Domain registration—AWS acts as a domain registrar, allowing you to select domain names and register for them with the AWS console
- Weighted round-robin load balancing—spreads traffic between several services via a round-robin algorithm


http://www.hangdaowangluo.com/archives/2816

具体的应用需求还得具体分析，如果是中小型的Web应用，比如日PV小于1000万，用Nginx就完全可以了；如果机器不少，可以用DNS轮询，LVS所耗费的机器还是比较多的；大型网站或重要的服务，且服务器比较多时，可以考虑用LVS。

Nginx的优点是：
工作在网络的7层之上，可以针对http应用做一些分流的策略，比如针对域名、目录结构，它的正则规则比HAProxy更为强大和灵活，这也是它目前广泛流行的主要原因之一，Nginx单凭这点可利用的场合就远多于LVS了。

Nginx的缺点是：
Nginx仅能支持http、https和Email协议，这样就在适用范围上面小些，这个是它的缺点。
对后端服务器的健康检查，只支持通过端口来检测，不支持通过url来检测。
不支持Session的直接保持，但能通过ip_hash来解决。


Linux Virtual Server (LVS): 
LVS的优点是：
抗负载能力强、是工作在网络4层之上仅作分发之用，没有流量的产生，这个特点也决定了它在负载均衡软件里的性能最强的，对内存和cpu资源消耗比较低。

LVS的缺点是：
软件本身不支持正则表达式处理，不能做动静分离；而现在许多网站在这方面都有较强的需求，这个是Nginx／HAProxy+Keepalived的优势所在。
如果是网站应用比较庞大的话，LVS/DR+Keepalived实施起来就比较复杂了，特别后面有Windows Server的机器的话，如果实施及配置还有维护过程就比较复杂了，相对而言，Nginx ／ HAProxy+Keepalived就简单多了


现在对网络负载均衡的使用是随着网站规模的提升根据不同的阶段来使用不同的技术：

第一阶段：利用Nginx或HAProxy进行单点的负载均衡，这一阶段服务器规模刚脱离开单服务器、单数据库的模式，需要一定的负载均衡，但是仍然规模较小没有专业的维护团队来进行维护，也没有需要进行大规模的网站部署。这样利用Nginx或HAproxy就是第一选择，此时这些东西上手快， 配置容易，在七层之上利用HTTP协议就可以。这时是第一选择。

第二阶段：随着网络服务进一步扩大，这时单点的Nginx已经不能满足，这时使用LVS或者商用Array就是首要选择，Nginx此时就作为LVS或者Array的节点来使用，具体LVS或Array的是选择是根据公司规模和预算来选择，Array的应用交付功能非常强大，本人在某项目中使用过，性价比也远高于F5，商用首选！但是一般来说这阶段相关人才跟不上业务的提升，所以购买商业负载均衡已经成为了必经之路。

第三阶段：这时网络服务已经成为主流产品，此时随着公司知名度也进一步扩展，相关人才的能力以及数量也随之提升，这时无论从开发适合自身产品的定制，以及降低成本来讲开源的LVS，已经成为首选，这时LVS会成为主流。

最终形成比较理想的基本架构为：Array/LVS — Nginx/Haproxy — Squid/Varnish — AppServer。
