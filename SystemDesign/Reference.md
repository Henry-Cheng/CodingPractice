System design interview is a happy show-off of our knowledge on technologies and their tradeoffs. Ideally, keep talking what the interviewer expect throughout the interview, before they ask questions.

[A good system design blog](https://www.raychase.net/6312)

[grokking-the-system-design-interview](https://www.educative.io/courses/grokking-the-system-design-interview?aid=5082902844932096&utm_source=google&utm_medium=cpc&utm_campaign=grokking-manual&gclid=CjwKCAjwm7mEBhBsEiwA_of-TI90yan7J8EpoWdYQ0S_qit4h2DnCU9vsLvUk1oPU97drQv_c8aEgBoC-aYQAvD_BwE)


[100 open source Big Data architecture papers for data professionals](https://www.linkedin.com/pulse/100-open-source-big-data-architecture-papers-anil-madan)


[Crack the System Design Interview](https://tianpan.co/notes/2016-02-13-crack-the-system-design-interview)


[system-design-primer](https://github.com/donnemartin/system-design-primer)


[System Design Cheatsheet](https://gist.github.com/vasanthk/485d1c25737e8e72759f)

[cheatsheet-systemdesign](https://cheatsheet.dennyzhang.com/cheatsheet-systemdesign-a4)

[A good diagram shows DevOps tools](https://www.ctl.io/developers/blog/post/60-best-open-source-tools-to-do-devops/) (another one in Chinese [DevOps Tools](https://www.gaott.top/toolkit/))
![Image](https://github.com/Henry-Cheng/CodingPractice/raw/master/SystemDesign/Images/Cloud-Application-Manager-DevOps_Open_Source_Tools.png)
- Configuration Management
  - [Puppet](https://puppet.com/) – Puppet is an open-source configuration management tool.
  - [SaltStack](https://www.vmware.com/support/acquisitions/saltstack.html) - package distribution/installation
  - [AWS OpsWorks](https://aws.amazon.com/opsworks/) -- supports Chef and Puppet
  - [Ansible](https://www.ansible.com/) - Radically simple configuration-management, application deployment, task-execution, and multi-node orchestration engine.
- Monitoring／Alerting/Analytics (e.g. https://www.oschina.net/translate/7-monitoring-tools-to-prevent-the-next-doomsday?cmp&p=1)
  - [sensu](http://sensu.io/) – monitoring platform 
  - Kibana
- Logging
  - [StatsD](https://github.com/statsd/statsd) – Metrics collecting and monitoring
  - LogStash
- Continuous integration/continuous Delivery (CICD)
  - [Jenkins](https://www.jenkins.io/)
- Version Control
  - Git
- Build & Test & Automation
  - Ant
  - Maven
- Container
  - Docker
- Microservice
  - Kubernates
  - Fargate
  - AWS Batch


[SNAKE Principles](https://zhuanlan.zhihu.com/p/20712931)
- Scenario
- Necessary
- Application
- Kilobit
- Evolve


[4S Steps](https://jiayi797.github.io/2018/02/03/%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1-%E8%AE%BE%E8%AE%A1%E7%9F%AD%E7%BD%91%E5%9D%80%E7%B3%BB%E7%BB%9FTiny-URL/)
- ask question --> <Scenario> --> analyze functional requrement, TPS, storage size, etc.
- draw diagram --> <Service+Storage> --> design MVP
- evolve       --> <Scale> --> based on different concern/problem, scale and improve system

Avoid doing the following stuff:  
1. system must be very huge
2. must use NoSQL
3. must be distributed 

