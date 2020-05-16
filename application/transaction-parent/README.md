分布式事务实践
1、事务原则与实现
1.1、事务
 1.1.1、事务是什么？
   事务是以一种可靠、一致的方式，访问和操作数据库中数据的程序单元。
 1.1.2、事务四大特性——ACID
   a、原子性(A)：这一个事务当中的多个操作，要么都完成，要么都不完成；它不会出现只完成其中一部分这样的情况。
   b、一致性(C)：指的是我这个事务完成了以后它状态的改变是一致的，它的结果是完整的。
   c、隔离性(I)：在不同的事务，它们试图操作同样的数据的时候，它们之间的隔离性是什么样的。
   比如说一个事务中对数据的修改没有提交的情况下，我在另一个事务是否能看到这个未提交的数据。
   d、持久性(D)：当事务提交以后，数据操作的结果会进入数据库进行永久保存。
   
   ![binaryTree](img/mysql实现事务.png "binaryTree")
   
   ![binaryTree](img/jdbc事务管理.png "binaryTree")
 1.1.3、事务原则与实现：SQL事务
 START TRANSACTION; #开启事务
 UPDATE t_user SET amount = amount + 100 WHERE username = 'SuperMan';
 UPDATE t_user SET amount = amount - 100 WHERE username = 'BatMan ';
 COMMIT;
 
 MySQL默认隔离级别： SELECT @@GLOBAL.tx_isolation, @@tx_isolation;——REPEATABLE-READ 可重复读
 
 四种隔离级别：
  a、脏读——READ UNCOMMITTED：读取到脏数据，即使是没有commit的数据我也能够读取到
  b、READ COMMITTED：只要别人提交了，就可以读取；第一次读和第二次读可能是不一样的；
  c、可重复读——REPEATABLE-READ：在一个事务内，重复读多次一条数据，读到的结果应该是一样的；第一次读和第二次读肯定是一样的；
  d、SERIALIZABLE——线性读：所有的事务操作必须是线性执行，想当于排队执行，隔离级别最高。
 
 设置脏读：SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
[Spring 事务机制详解](https://juejin.im/post/5a3b1dc4f265da43333e9049)
[Java中的事务——JDBC事务和JTA事务](http://www.hollischuang.com/archives/1658)
[MySQL 笔记 - 事务&锁](https://juejin.im/post/5b76938de51d45664715fba8)

2、Docker
2.1、Docker介绍
  Docker是一种轻量级的容器。它是使用操作系统级别的虚拟化技术对进程、软件系统和网络之类的资源，进行封装和隔离。
  看起来就好像一个独立的系统，所以它也叫做容器。
2.2、与传统的虚拟机异同
  比如VM，它其实是在宿主机器上，去虚拟出一套硬件。然后在这套硬件上面去安装一套操作系统。在这套操作系统上再去安装我们要使用的软件。
  而Docker是直接在操作系统上面把一部分的进程、软件系统、网络等隔离出来。让你觉得好像它是一个独立的操作系统。
  但是实际上它只是隔离了一个容器而已。它是运行在docker的引擎上面的，它不需要虚拟硬件，也不需要安装额外的操作系统。
  所以它非常的轻量，然后使用起来非常方便。
2.3、几个概念
 1)、Docker镜像-docker资源库
   它是保存在docker资源库里面的
 2)、Docker容器——Dockerfile
   定义使用的镜像，设置环境变量等
 3)、Docker服务——docker-compose
   把多个docker容器定义在一个文件
 4)、Docker集群-swam、Kubernetes
 
a、假设我需要部署一个Java应用，首先我需要一个Java的运行环境，所以说我在docker资源库里面找一个jdk的运行环境；
b、然后我想自动去设置java的编译、运行等等。那我就写一个Dockerfile文件，然后用Dockerfile文件定义出一个容器来，
在这个容器里面去运行java应用；
在Dockerfile文件里面需要指定要用的jdk镜像、设置源文件在本地的地址和需要映射到容器的地址、在镜像中安装Maven、映射网络端口，访问服务。
c、假设我的java应用要使用mysql或者其他的数据库，我现在在本地想把这些都设置到同一个docker服务的docker-compose里面，
那我就可以在docker-compose里面定义两个服务，其中一个是mysql，我可以设置mysql的镜像，另一个是java应用的docker，
两个docker容器就可以共享一个网络，相互之间有一个统一的关系等等，可以统一管理；
d、集群：假设我这个java应用可以进行分布式部署，可以部署在多台机器上就可以配置一个集群。

3、Spring事务机制
3.1、Spring事务机制：事务抽象、事务传播、事务隔离
3.1.1、Spring事务管理
a、提供统一的API接口支持不同的资源
b、提供声明式事务管理
c、方便的与Spring框架集成
d、多个资源的事务管理、同步

3.1.2、Spring事务抽象
a、 ：
提供事务管理器的接口，不管使用的是什么样的事务管理器的实现，但是我们都可以用这个接口来进行事务的管理。
包括：事务的开启、提交、回滚等操作。
![binaryTree](img/事务管理器.png "binaryTree")

b、TransactionDefinition
事务的定义。我们可以创建一个TransactionDefinition，然后给它设置一些事务的属性，
包括：传播属性、隔离属性等等。在通过这个定义创建一个具体的实例。
![binaryTree](img/事务定义.png "binaryTree")
![binaryTree](img/事务隔离机制.png "binaryTree")
![binaryTree](img/事务传播机制.png "binaryTree")

c、TransactionStatus
事务的运行状态，或者是运行的有状态的事务。
![binaryTree](img/TransactionStatus.png "binaryTree")

3.2、代码方式与标签方式的事务实现
![binaryTree](img/Transactional代码方式实现.png "binaryTree")
CustomerServiceTxInAnnotation.java

![binaryTree](img/Transactional标签方式实现.png "binaryTree")
CustomerServiceTxInCode.java

![binaryTree](img/PlatformTransactionManager的常见实现.png "binaryTree")

3.3、JPA、JMS事务实例
3.3.1、Spring事务实例
c3-1-spring-trans-jpa
1)、代码方式、标签方式实现事务
2)、JPA事务管理
3)、使用H2数据库(支持事务)

3.3.2、Spring JMS事务实例
[Spring整合JMS(消息中间件)](https://blog.csdn.net/suifeng3051/article/details/51718675)
[Spring整合JMS(消息中间件)实例](https://blog.csdn.net/suifeng3051/article/details/51721141)
3.3.2.1、Spring JMS Session
1)、通过Session进行事务管理
是Spring来访问MQ服务器的时候，用的session的对象。数据库读、写、提交、失误都是通过Session来进行操作的。
所以说JMS内部是支持事务的，有一个session级别的事务管理。每次读消息的时候，读完消息，它都会有所谓的一个提交，
确保这个消息已经读完。如果失败了，它会重新触发读。
2)、Session是thread-bound
由于Session可能是共用的，在应用里面它不会每次都重新建一个session或者说重新建一个连接。
每次在我新接收请求的时候，对于一个新的线程，它可能会共用以前的session或者说新建一个session来用。
每一个session在每一个线程里面它会有自己的一个事务，这个事务的生命周期就是session在thread里面的生命周期。
也就是说当我在我的应用里面读一个消息，然后处理，处理完往另外一个队列里面写消息的时候，
这个线程的操作就是 先读；然后处理；然后在写消息。这个session在这个过程中使用的是同一个session。
在进行事务管理的时候，读和写两个操作是在一个事务当中。只有所有这些操作都完成没有错误的情况下，然后它最后才会做一个commit操作，
把这个最后提交上去。
3)、事务上下文：在一个线程中的一个Session

3.3.2.2、Spring JMS事务类型
1)、Session管理的事务——原生事务
![binaryTree](img/session原生事务.png "binaryTree")
 
2)、Jms-session实例——c3-2-spring-trans-jms
对于session管理的jms事务而言，它的事务是作用在线程内 的生命周期上的。
对于listener触发的方法，listener用session去读了消息以后，触发了handle方法。
在handle里面触发了convertAndSend()。直到方法结束后这个listener才会去调用session的commit()去提交session管理的事务。
但是此时因为有一个异常，所以做了回滚操作。
   
如果直接调用handle方法的话，不通过listener，这个session事务的生命周期就只在convertAndSend()方法内部起作用，而不是整个handle方法起作用
所以就直接提交上去没有回滚。
a)、Spring Boot中使用JMS
b)、Spring Boot ActiveMQ Starter
c)、内置的可运行的ActiveMQ服务器
d)、实现读写ActiveMQ的事务

3.3.2.3、外部管理的事务：JmsTransactionManager、JTA
![binaryTree](img/JmsTransactionManager事务.png "binaryTree")

3.3.4、Spring外部事务与JTA
3.3.4.1、Spring内部事务与外部事务
1)、本地事务
 a、Spring容器管理事务的生命周期
 b、通过Spring事务接口调用
 c、业务代码与具体事务的实现无关
 d、![binaryTree](img/本地事务.png "binaryTree")
2)、外部(全局)事务
 a、外部事务管理器提供事务管理
 b、通过Spring事务接口，调用外部管理器
 c、使用JNDI等方式获取外部事务管理器的实例
 d、外部事务管理器一般由应用服务器提供，如Jboss等
 e、外部事务管理器提供JTA事务管理
 f、JTA事务管理器可以管理多个数据资源
 g、通过"两阶段提交"实现多数据源的事务
 
 两阶段提交:当我在一个JTA的事务里面，使用两个数据库的时候，我进行两阶段提交，也就是第一阶段先做一个第一阶段的提交；
  等所有的数据库都提交返回，没有问题的时候，然后在进行第二阶段的提交。
  第二阶段提交完成了以后数据才真正写进去相当于持久化。事务才算完成。
  如果在第一阶段提交完以后，某一些数据库返回了错误或者说没有及时返回，那么第二阶段没有及时提交而是直接rollback。
 ![binaryTree](img/外部(全局)事务.png "binaryTree")
 ![binaryTree](img/外部(全局)事务-不使用应用服务器.png "binaryTree")
 
3.3.4.2、JTA与Spring JTA实现
1)、JTA事务管理的用途
解决一个java服务访问多个数据源的时候，怎么样能够保证，满足他的事务性。 

3.3.4.3、JTA与XA
1)、XA
XA：是由X/Open提出的分布式事务的规范，这个规范定义了一个全局的事务管理器的接口-Transaction Manager
XA Resource
两阶段提交

2)、JTA
JTA实际上是XA规范在Java中的实现，JTA(Java Transaction API)。
 a、TransactionManager
 b、XAResource
 c、XID

3)、JTA事务管理的弊端
 a、两阶段提交
 b、事务时间太长、锁数据的时间太长
 c、低性能、低吞吐量

4)、不使用JTA实现多数据源的事务管理-Spring事务同步
 a、Spring事务同步机制
 b、多个数据源上实现近似事务一致性
 c、高性能、高吞吐量

3.3.4.4、JTA分布式事务实例
[JTA分布式事务处理](https://www.jianshu.com/p/029f28c060f6)

4、分布式系统
1)、定义与介绍
将不同的组件分布在不同的服务器上，给用户提供一个可靠、统一的服务。
分布：不同
用户：可靠、统一的服务

2)、分布式系统的原则
CAP原则
C：一致性
A：可用性
P：分区容错性 

3)、如何实现原则
分区容错性：多级部署，通过服务拆分，把一个服务部署在一个机器上；
可用性：在多级部署的基础上通过一些负载均衡或者服务注册等等技术，保证对于用户提供的服务是可用的；
不会因为某一个服务器的出错导致整个系统不可访问；
一致性(*)：
 a、强一致性：在分布式系统中对多个数据源、多个数据库的操作是原子性完成的，数据满足一致性要求（几乎是不可能的）；
 b、弱一致性：当一个业务请求要调用不同的服务时，是一个一个调用的，每一个服务的调用都是在这个服务内去满足一致性要求；
    对于用户来说他看到的结果有可能是一个中间结果，等业务正常完成时数据即可达到一致性要求。
 但是对于弱一致性来说，如果出错了，业务应该如何回滚？(*)
 c、最终一致性：在弱一致性的基础上，先允许它出错，但是我可以通过一些重试或者定时任务去扫描，然后针对这种出错的情况进行取消；
 甚至是通过一些人工干预的方式能够让这些数据达到最终一致性。(实现起来比较容易，而且性能也很好，开发和维护的成本也比较低，实际开发中大多数使用)

4)、BASE理论
 BASE: Basically Available(基本可用)、Soft state(软状态)和 Eventually consistent(最终一致性)
 相当于CAP原则中的一致性和可用性的权衡结果。

5)、分布式系统的形式
 a、多数据源：在一个java系统中使用多个数据库
 ![binaryTree](img/多数据源.png "binaryTree")
 b、多服务：一个系统把服务分成多个子服务，然后每个服务部署多个实例
 ![binaryTree](img/多实例.png "binaryTree")
 c、SOA
 ![binaryTree](img/SOA.png "binaryTree")
 d、微服务架构  
 ![binaryTree](img/微服务架构.png "binaryTree")

6)、分布式系统需要考虑的问题
 a、服务拆分
 b、数据拆分
 c、计算拆分
 d、服务状态以及异常处理
 
7)、微服务架构
 a、服务发现与注册
 b、服务网关与负载均衡
 c、监控与熔断机制
 d、配置、消息等
 ![binaryTree](img/SpringCloud微服务架构.png "binaryTree")

4.1 Spring Cloud微服务架构
 1)、微服务架构组成
    a、服务
    b、服务注册中心
    c、网关：提供统一调用入口，可以负载均衡转发服务请求
    d、服务间调用协议：http方式 rest、rpc
 2)、Spring Cloud微服务架构
    a、Spring Cloud Netflix 微服务架构
    b、Spring Cloud Config 配置服务器
    c、Spring Cloud Bus 事件总线
 3)、Spring Cloud Netflix 微服务架构
    a、服务注册中心(Eureka)：，服务注册和服务缓存
    b、网关(Zuul)：网关从服务注册中心取到所有服务的注册的配置,根据url找到相应的服务id;
    然后根据服务id注册的多个实例,通过一定的负载均衡的规则去把这个请求转发到相应的实例上面。
    网关也可以监控服务性能，可以查看每一个服务调用的时间。
    c、声明式Rest调用接口(Feign Client)：是一个服务间调用的接口,通过Http Rest的方式去进行服务间的调用;
    它可以通过标签的方式注明我们某一个方法它调用的接口是远程服务的哪一个接口等等。
    d、负载均衡：Ribbon，服务间调用时通过Ribbon进行负载均衡；
    e、监控、荣怒单组件：Hystrix与Hystrix Dashboard
 4)、Spring Cloud Config 配置服务器
    配置中心,在微服务分布式系统中,每个服务使用的配置可以在配置中心进行配置;
    当配置需要修改的时候,只需要在配置中心修改，通过特定刷新机制同步给各个服务去更新。
    a、配置：文件夹、Git、SVN、DB
    b、配置服务器
    c、服务 -> 配置服务器 -> 配置
    d、配置加密
4.2 Spring Cloud Netflix实战
 1)、User服务、Order服务
 2)、网关
 3)、服务注册中心、启用安全
 4)、服务间调用
 5)、Hystrix监控




