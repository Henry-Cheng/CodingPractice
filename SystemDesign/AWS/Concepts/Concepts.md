# Quiz

- Decouple servics: SQS / SNS / ELB
- AWS account root user is for administrator privileges
- "Fault tolerance" is the ability of an infrastructure component to handle failures
- services that are appropriate when building serverless architectures: API Gateway / lambda / ddb / s3
- what services are used to improve reliability: ELB
- high "availability" is the condition when a service is going to keep at uptime
- which LB is designed to handle HTTP and HTTPS traffic: Classic LB / Application LB
- description of services
  - EC2 -- hosting web and application servers
  - S3 -- hosting media and other files for application to access
  - VPC -- hosting multi-tier web applications
  - EBS -- locally attached storage for a web or application server
  - ELB -- splitting traffic between web or application servers
  - DynamoDB -- database access taht doesn't require joins or other complex operations between tables
  - RDS -- database access that requires joins or other complex operations betweeen tables
  - Auto Scaling -- providing automated elasticity for your web and application servers
  - CloudFront -- CDN
- it's best to start your VPC with a "large" IP range and "divide" as necessary, resrving more IPs for "private" subnets than for "public" subnets
- "security groups" are stateful and control inbound and outbound traffic


# Advantage of using AWS

1. Programmable resources
2. Dynamic abilities (auto scaling)
3. Pay as you go

Expense advantage
- pay as you consume resources
- reduce upfront capital costs

Scalability advantage
- Leverage large economies of scale
  - lower cost than on your own
    - specialized hardware and software
    - large volume hardware purchasing

Capacity advantage
- stop guessing about capacity
  - scale up and down as needed
  - don't need to overprovision

Speed advantage
- no need to wait for hardware to be delivered and set up
  - new IT resources are just a click away
  - reduce resource development time

Focus on what matters

Go global in minutes


# 5 Pillars for AWS well-architectured framework

- Security
  - identity foundation
    - are you following the principle of "least privilege"
    - are you enforcing separation of duties with the appropriate authorization for each interaction with your AWS resources?
    - are you centralizaing privilege management?
    - are you reducing or even eliminating reliance on long-term credentials?
  - enable traceability
    - are you monitoring, auditing and providing alerts about actions and changes to your environment in real time?
    - are you integrating logs and metrics with systems to automatically respond and take action?
  - seurity at all layers
    - are you just focusing on protecting a single outer layer?
    - or are you securing all layers (e.g. edge network, VPC, subnet, LB, every instance, os and appication)
  - Risk assessment and mitigation strategies
    - are you prepared for an incident by having an incident management process that aligns to your organizatoinal requirements?
    - are you able to run incident response simulations and use tools with automation to increase your speed for detection, investigation and recovery?
- Reliability
  - dynamically acquire computing resources to meet demand
  - recover quickly from infrastructure or service failures
  - mitigate disruptions such as
    - misconfigurations
    - transient network issues
  - Q
    - are you waiting for your resources to be over-provisiond before adding more capacity, or are you acquiring resources dynamically?
    - are you monitoring your systems for key performance indicators so that you can trigger automation when a threshold is breached?
    - are you KPIs a measure of business value, not of the technical aspects of the operation of the service?
    - this allows for automatic notification and tracking of failures, and for automated recovery processes that work around or repair the failure. With more sophisticated automation, it is possible to anticipate and remediate failures before the occur
- Cost optimization
  - are you measuring the business output of your systems and the costs associated with delivering them? Use this measure to understand the gains you make from increasing output and reducing cost
  - in the cloud, managed services remove the operational burden of maintaining servers for tasks like sending email or managing database
  - since managed service soperate at cloud scale, they can offer a lower cost per transaction or service
- Operational Excellence
  - the ability to run and monitor system
  - to continually improve supporting process and procedures
- Performance Efficiency
  - choose efficient resources and maintain their efficiency as demand changes
  - democratize advanced technologies
  - mechanical sympathy
  - Q
    - are you choosing the resources that are most suited to your workloads and access patterns?
    - as demand and access patterns change, are you maintaining efficiency?



 SQL vs NoSQL
 - are you focused on mechanical sympathy? are you designing your infrastructure to support how your application actually use it?
 - use the technology approach that aligns best to what your are trying to achieve

# AWS Data centers
- a single datacenter typically houses tens of thousands of servers
- all data centers are online, not "cold"
- AWS custom network equipment
  - multi-ODM sourced
  - Customized network protocol stack

Each availability zone is
- made up of one or more data centers
- designed for fault isolation
- interconnected with other availability zones using high-speed private links

You can choose your availability zones, and AWS recommends replicating across AZ for resiliency


Each AWS region is made up of 2 or more AZs
- AWS has 19 regions worldwide
- You enable and control data replication across regions
- communication between regions use AWS backbone network infrastructure

AWS Edge locations
- if your server is at London, but your customer is at NY, we can cache the data in NY edge locations






