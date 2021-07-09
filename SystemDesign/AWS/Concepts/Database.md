# Database

What to consider when building a databse layer?
- scalability
  - how much throughput do you need?
  - will the solution you choose be able to scale up later if needed?
- Total storage requirements
  - how large does our database need to be?
  - will we have GB, TB, or PB of data?
- Object size and type
  - do we need to store simple data structures, large data objects, or both?
- Durability
  - what level of data durability, data availability, and recoverability do you require?
  - do you have a related regulatory obligation?


SQL vs NoSQL:

SQL:

(ISBN,   Title,    Author,    Format)
                    |   |
                    V   |
(sales, revenue,Autor)  |
						|
						V
					(Author, Accountnumber)  


NoSQL:

key-value pair

```
{
	ISBN: blabla,
	Title: blabla,
	Author: blabla,
	Format: blabbla
}
```


| SQL        | NoSQL  |
| ------------- |-----:|
| Data stored in rows and colums      | Data stored in key-value pairs, documents, and graphs |
| Fixed schema      |   Dynamic schemas |
| SQL-based querying |    NoSQL-basd querying or another type of querying that focuses on a collection of documents|


## database managed on-premises

You need to do all the following
- App optimization
- scaling
- high availability
- database backups
- DB s/w patches
- DB s/w installs
- OS patches
- OS installation
- server maintenance
- rack andstack
- power, HVAC, net


## database on AWS EC2

Same like above list, but AWS handles the items from "power, HVAC, net" to OS installation"

## databse by using AWS RDS

Same liek above list, but AWS handles everything from "power, HVAC, net" to "scaling", you only need to worry about "App optimization"


## RDS

RDS cheat sheet
- fully managed relational databse service
- provisions new instances in a few minutes
- scales vertically with a few clicks
- supports mySQL/ mariaDb, postgresSQL, SQL Server, Oracle, and AMazon Aurora

Advantage
- up to 5x the throughput of MySQl
- up to 3x the throuput of postgresSQL
- replication in 6 ways across 3 AZ
- very little change to your existing application
- available as serverles, which enables you to run your database in the cloud without managing database instance
- with aurora global db, allows you to run a single aurora database instance spanning multiple AWS regions

### RDS use case: analytics

```
<collecting data from users> --> S3 --> EC2 (data transformation) --> RDS (data analysis)
```

## DynamoDB

DDB cheat sheet:
- fully manage non-relational db service
- event-driven programming (severless computing)
- extreme horizontal scaling capability

### DDB use case: leaderboards and scoring

```
player interacting with game and generated data --> game server <--> DDB
```

### DDB use case: temporary data (shopping cart)

```
users put items into their shopping carts --> web store --> DDB (shopping cart table) --> order processing 
```

### consistency options

- eventually consistent
  - uses .5x read capacity unit (RCU)
- strong consistent
  - uses 1x read capacity unit (RCU)

## security

- Access control, 
- encryption at rest
- encryption on transit (SSL/TLS)
- event notification

## AWS databas migration service (AWS DMS)

AWS DMS has a Snowball edge intergeratoin point

You can migrate one or more database using the snoball edge device
- mutil-terabyte storage
- without using network bandwidth



