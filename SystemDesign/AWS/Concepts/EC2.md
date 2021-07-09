# EC2

EC2 instance is used as the primary web server. It's publicly available and it leverages static assets stored in S3 bucket as well as a mounted networked fiel system through Amazon EFS.

Amazon EFS mount target
- this is the endpoint within your VPC where your file system on Amazon EFS is mounted. Your EC2 instance in this architecture accesses its file system on Amazon EFS through this mount target.

EC2 can solve some problems that are more difficult with an on-premises server.
- when using disposable resources
  - data-driven decision
    - with the ability to spin up new servers on demand, you can make sure you're pursuing the best projects, not just the ones that will conform to the hardware you have
  - quick iterations
    - deploy and test new versions of your applications as quickly as you can request the resources. Test across a fleet of instances instead of just one or two. Speed up your entire development lifecycle by using Amazon EC2.
  - free to make mistakes
    - virtual machines make experimentation much easier and less risky. If an experiment doesn't work out, shut down the resources it was using and stop paying for it immediately, instead of buying hardware.


## AMI

A single AMI (Amazon Machine Image) can be usd in multile EC2 instances. The AMI could just be the operating system, or it could also include pre-installed applicaitions and/or pre-set configurations

e.g. AMI can include
- a tempalte for the root volume (copy of theboot drive)
- launch permissinos (who is going to launch it)
- a block device mapping


3 ways to get your AMI
- pre-built
- marketplace 
- create your own


How do AMIs help?
- repeatability
- reusability
- recoverability
- marketplace solutions

### EC2 image builder

EC2 image builder simplifies the creation, maintenance, validation, sharing and deployment of Linux or Windows Serve images.

source image --> EC2 Image buildr --> customize software installed on the image --> source image iwth AWS provided or custom templates --> Test images --> distribute golden images


AMI --> user data (with custom bash scripts) --> running EC2 instace
          |
          V
```
#!/bin/bash
yum update -y
service httpd start
chkconfig httpd on
hostname = $(curl -s http://169.254.169.254/latest/meta-data/public-hostname)
```

## Amazon EBS --> Amazon Elastic Block Store

What problems can Amazon EBS solve?

- Application needs block-level storage
- instance storage is epherneral
- need data to persist through shutdowns
- need to be able to back up data volumes

Keep in mind: Amazon EBS can only be linked to one instance at a time. It must be in the same availability zone as the volume

### regular EBS instance

Inside regular EBS, there are couple of instance types
1. Solid-State Backed
	1. general purpose SSD (GP2)
	  - genearl purpose SSD volume that balances prices and perforamnce for a wide variety of workloads
	  - use case is for most workloads
	2. Privisioned IOPS SSD
	  - highest-perforamnce SSD volume for misson-critical low-latency or high-throughput workloads
	  - use case is for cirtical business applications that require sustained IOPS performance, or for large database workloads
2. Hard-Disk Backed
    1. Throughput optimized HDD
      - Low cost HDD volume designed for frequently accessed, throuput-intensive workloads
      - e.g.
        - streaming workloads
        - big data
        - data warehouse
        - log processing
        - cannot be a boot volume
    2. Cold HDD
      - Lowest cost HDD volume designed for less frequently acessed workloads
      - e.g.
        - throughput-oriented storage for large volumes of data that is infrequently acessed
        - scenarios where the lowest storage cost is important
        - cannot by a boot volume


### EBS optimized instance
- optimized configuration stack
- additional dedicated capacity for Amazon EBS I/O
- Minimizes contention between Amazon EBS and other traffic
- options between 425 Mbps and 14,000 Mbps

advantage:
1. more dedicated I/O capacity
2. better performance
3. dedicated bandwidth


### Shared File Systems

What if I have multiple instances that need to use the same storage?

- Amazon EBS only attaches to one instance at a time by design, so in some scenarios EBS may not work for your scenario
- S3 is an option but is not ideal
- Amazon EFS and FSx are perfect for this task


#### Amazon EFS and Amazon FSx

EFS cheat sheet:
- regional file storage service
- resides within and across multi AZ
- can share file between thousands of EC2 instances and on-premises servers via AWS Direct Connect or AWS Virtual Private Network (AWS VPN)


Compare:

1. Amazon EFS (Linux Workloads) -- NFSv4 file system
  - Shared Across
    - AZ
    - region
    - VPC
    Account
2. Amazon FSx (Windows Workloads) -- NTFS file system
  - Shared across
    - AZ


## EC2 Instance Types

https://instances.vantage.sh/

e.g.
m5.large
--> m is the family name
--> 5 is the genration number
--> large is the size of the instance

   
key take aways:
1. Your choice of instance type impacts how efficiently you'll be using your instance as well as the cost of the compute portion of your infrastructure
2. Instances don't just come in different types. Each type comes in different sizes, with different allotments of cVPUs and memory. CHoosing the right size is cirtical to using your instances efficiently.
3. An instances's full type consists of its family name, followed by the generation number, and then the size. So an m5.xlarge is an m-type instance from the 5th generation of m-type instances, in the extra-large size deployment


All current EC2 instance types includes
- Intel AES-NI: reduces perforamnce hit due to encryption
- Intel AVX (AVX2, AVX-512) improve floating-point performance. Only available on HVM deployments.

Some EC2 instace types include:
- Intel Turbo Boost: runs cores faster than bas clock speed when needed
- Intel TSX: uses multiple threads or single thread depending on need
- P state and C state control: fine-tune performance and leep state of each core
- Intel Deep Learning Boost: embedded performance acceleration for AI workloads. Up to 30x performance improvement for intererence workloads compared to previous generation.


Latest geneartion of Intel Xeon processors up to (on 2019 year):
- 28 cores per CPU
- 6 memeory channels
- 48 PCIe lanes of bandwidth/throughput
- 100 Gbps network bandwidth (C5n.16xlarge)

## EC2 pricing

1. on-demand instances
  - pay for compute capacity per second or by the hour, depending on OS
  - no long-term commitments
  - no upfront payments
2. Reserved
  - pre-pay for capacity
  - standard reserved instances (RIs): provide the most significant discount; best suited for ready state usage; least flexible type of RI
  - convertible RIs; moderate discount; able to change the attributes of the RI
  - Schedule RIs: RIs that launch in a time window of your choice, allowing you to match your capacity needs
  - pay upfront with 3 different method
  - can be shared between multiple accounts
3. Spot
  - bid for unused capacity
  - prices controlled by AWS based on supply and demand
  - termination notice provied 2 min prior to termination
  - can provide the best discounts, but your workloads need to be able to tolerate sudden starting and stopping


### Dedicated instaces vs dedicated hosts

Dedicated instances gives you instance-level isolation, but dedicated hosts gives you host-level isolation.

tenancy:
1. default tenancy
  - runs on shared hardware
2. dedicated instaces 
  - runs on a non-specific pieces of hardware (but still shared host)
  - AWS gurantees the data would not be accsesible across dedicated instance
3. dedicated host
  - runs on a specific piece of harware of your hossing, over which you receieve greater control (one tenancy own an entire machine)
  - e.g. US congress has requirements to only have one tenancy on a piece of machine 


| Dedicated instaces        | Dedicated Hosts  |
| ------------- |-----:|
| instance-level isolation from other AWS accounts      | Physical server (host)-level isolation from other AWS accounts |
| per-instance billing      |   per-host billing |
| no visibility of sockets, cores, and host ID |    visibility of sockets, cores and host ID |
| host and instance affinity not supported |    allows you to consistenly deploy your instance to the same physical server over time |
| targeted instance placement not supported |    provides additional visibility and control over how instances are placed on a physical server |
| automatic instance recovery supported |    Automatic instance recovery not supported |
| bring your own license (BYOL) not supported |    bring your own license (BYOL) supported |


AWS compute optimizer recommends optimial AWS compute resources for your workloads to reduce costs and improve perforamnce by using ml to analyze historical utilization metrics
e.g.
1. lower costs by up to 25%
2. optimized performance with actionable recommendations


### EC2 best practices and considerations

Does your compute layer require the lowest latency and highest packet-per-second network performance possible?
--> using cluster placement groups
--> so that you don't have to cross-AZ, just using multi hosts in same rack

Do you have application that have a small number of critical instances that should be kept separate from each other?
--> using spread placement groups
--> designed to push them far away to be disaster safe


### tag your instance to easily identify them in the future

## Q & A

AMI benefit:
1. Marketplace activities
  - Rather than create their own mongoDB AMI, they used on sold by another company
2. Repeatability
  - the user launched their new application onto a fleet of identical instances all at once
3. reusability
  - the company expanded its application into a new region with the same AMI 
4. recoverability
  - when the primary SQL serve went down, they replaced it with a new identical instance
5. backups
  - even though the db was lost, they only lost one day of data due to the AMI

Amazon EBS is a "block"-level storage service


Match the AWS storage service with why it should or should not be used with multiple instances that need to use same read/write storage:
- Only attaches to 1 instance at a time --> EBS
- object-store system, not block store, not ideal for file system --> S3
- perfect solutinon for this requirement --> EFS


You have a regular batch processing workload. The job runs once a week and takes only a few hours and can be interrupted. Which Amazon EC2 pricing option would work best for this?
--> spot instance

Your applciatoin has suddenly become extremely popular and you need to scale out very quickly
--> on-demand instance

Your application would run for another 5 years, and you know how much resources erquired since it has run 2 years
--> reservice instances


