# VPC

cheat sheet
- private network in AWS cloud
- provides logical isolation for your workloads
- allows custom access controls and security settings for your resources
- enables you to designate specific CIDRs for your resources to occupty
- provides strict inbound and outbound traffic rules
- must be deployed into one AWS region (cannot be cross-region), but can host resources from any AZ within that region
- can have up to 5 VPCs per region per account by default (can request service limit increase through AWS support)
- supports bringing your own IP prefixes

Each account can have up to 5 VPC per account (soft limit).

## VPC and IP Addressing

- each VPC reservces a range of private IP address that you specify
- those private IP address can be used by resources deployed into the VPC
- the IP range is defined using Classless Inter-Domain Routing (CIDR) notation
- supports bringing your own IP prefixes

e.g.

10.0.0.0/16 = all IPs from 10.0.0.0 to 10.0.255.255

### CIDR example

```
0.0.0.0/0		--> All IPs
10.22.33.44/32	--> 10.22.33.44
10.22.33.0/24	--> 10.22.33.*
10.22.0.0/16	--> 10.22.*.*

CIDRs	--> Total IPs
/28		--> 16
...		--> ...
/20		--> 4096
/19		--> 8192
/18		--> 16384
...
```

## using subnets to divide your VPC

a subnet is a segment or partition of a VPC's IP address range where you can isolate a group of resources

e.g.
A VPC with CIDR /22
includes 1024 total IPs

Inside your VPC, we can divide it by subnets:  
- subnet1: 251 address
- subnet2: 251 address
- subnet3: 251 address
- subnet4: 251 address

NOTE:
- subnets are a subset of VPC CIDR block
- subnet CIDR blocks cannot overlap
- each subnet resides entirely iwthin one AZ
- an AZ can control multiple subnets
- AWS will reserve 5 IP address for each subnet

Each subnet can be public or private:
- public:
  - include a "routing table" entry to an "internet gateway" to support inbound/outbound access to the public internet
- privaate:
  - do not have routing table entry to an internet gateway
  - are not directly accessible from the public internet
  - typically use a "NAT gateway" to support restricted, outbound public internet access


## route tables: directing traffic between VPC resources

how does packets go from one subnet to another

Route tables:
- requred to direct traffic between VPC resources
- each VPC has a main (default) route table
- you can create custom route tables
- all subnets must have an associated route table

## How to connect public subnets to the internet

Using internet gateways
- allow communication between instances in our VPC and the internet
- are horizontally scaled, redundant, and highly available by default
- provide a target in your subnet route tables for internet-routable traffic


## recommendations on subnet

1. consider larger subnets over smaller ones (/24 and larger)
2. if you are unsure of the best way to set up your subnets, start with "1 public" and "1 private" subnet per AZ
3. nist architectures have significantly more private resources than public resources, so allocate substantially more IPs for privat subnets than for public subnets


## Elastic Network Interface (ENI)

ENI address the EC2 instances inside VPC.

An ENI is a virtual network interface that can be moved across EC2 instances in the same AZ.
- when moved to a new instance, a network interface maintains its
  - private IP address
  - Elastic IP address
  - MAC address

Why have more than 1 network interface on an instance?
- if you need to
  - create a management network
  - use network and security appliances in your VPC
  - create dual-homed instances with workloads/roles on distinct subnets

## Elastic IP address

- can be associated with an instance or a network interface
- able to re-associate and direct traffic immediately
- 5 allowed per AWS regin

## Security Groups

every EC2 instance runs inside a VPC must have a security groups
- virtual firewalls that control inbound and outboudn traffic into AWS resources
- traffic can be restricted by any IP protocol, port, or IP address
- rules are stateful

## Network Access Control Lists (ACLs)

recomended for "specific network security requirements" only

## on-premises network to AWS 

option1: Virtual Private Gateway (VGW)
- enables you to establish private connections (VPNs) between an Amazon VPC and another network
- It helps to extend on-premises network to AWS through VPN connections

option2: AWS direct connect (DX)
- instead of connecting through public VPN network, it uses dedicated physical fiber line between AWS and your private data center
- AWS Direct Connect (DX) provides you with a dedicated, private network connection of either 1 or 10 Gbps
- reduce data transfe costs
- improve applciation performance iwth predictable metrics

### use case for DX

- hybrid cloud architecture
- continually transferring large data sets
- network performance predictability
- security concerns

## VPC peering

inscances can communicate across a peering connection as if they were in the same network

only 1 peering resource between any 2 VPCs


## Transit gateway

if you need multiple VPC connections, VPC peering is not ideal, you can use transit gateway to achieve 5k VPCs and on-premises environemnts with a single gateway


## VPC Endpoints

privately connect your EC2 instances to services outside your VPC without leaving AWS.  
e.g. 
- MAWS (or any on-premises servive) want to lookup services/API in NAWS


2 types of endpoints
- interface endpoints (an elastic network interface with a private IP address that serves as an entry point for traffic destined to a supported service)
  - Cloudwatch logs
  - code build
  - EC2 API
  - ELB API
  - KMS
  - Kinesis
  - ... 
- Gateway endpoint (aa target for a specified route in your route table, used for traffic destined to a supported AWS ervice)
  - S3
  - dynamoDB


## ELB options

## Route 53

cheat sheet
- translates domain names into IP address
- enables purchasing and management of domain names
- automatically configures DNS settings for purchased domains
- health chaecks na region-level failover through DNS resolutions





