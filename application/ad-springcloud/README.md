基于Spring Cloud微服务架构 广告系统设计与实现（2020新版）
![binaryTree](/img/SpringCloud-Ad.jpg "binaryTree")
![binaryTree](/img/编码实践.png "binaryTree")
1、Maven基础
1.1 Maven是什么
maven是一个项目构建和管理的工具，它提供了帮助管理项目的构建、报告、文档、依赖、发布
等等方法，它可以方便的编译代码进行依赖管理，管理二进制库等等。

1.2 Maven的好处
在于可以将项目规范化、自动化、高效化以及强大的可扩展性；用Maven自身及其插件还可以获得代码检查报告，单元测试覆盖率、
实现持续集成等等。

1.3 Maven的工作方式
Maven去项目根目录下读取pom.xml，获得需要的配置信息：
pom文件中包含了项目的信息，和maven构建的项目所需要的配置信息，通常有版本成员、项目的依赖、插件和目标等等；
pom是可以继承的，通常对于一个大型的项目或者多个模块的项目，子模块的pom需要指定父模块的pom。
![binaryTree](../img/Maven的基本样式.png "binaryTree")

1.4 Maven坐标
Maven坐标是jar包的唯一标识，maven通过坐标在仓库中找到项目所需要的jar包，
![binaryTree](../img/Maven坐标.png "binaryTree")

1.5、Maven相关特性
1)、传递依赖与排除依赖
 传递依赖：如果我们的项目引用了一个jar包，而该jar包又引用了其他jar包。
 那么在默认情况下，项目编译时，Maven会把直接引用和间接引用的jar包都下载到本地；

2)、排除依赖：如果我们只想下载直接引用的jar包，那么需要在 pom.xml 中做如下配置(给出需要排除的坐标)：
<dependency>  
    <groupId>org.apache.hbase</groupId>  
    <artifactId>hbase</artifactId>  
    <version>0.94.17</version>  
        <exclusion>  
            <groupId>commons-logging</groupId>  
            <artifactId>commons-logging</artifactId>  
        </exclusion> 
</dependency> 

3)、依赖冲突
说明：若项目中多个jar同时引用了相同(相同版本)的jar时，会产生依赖冲突，但Maven采用了两种避免冲突的策略，因此在Maven中是不存在依赖冲突的：
 a、短路优先
  本项目 -> A.jar -> B.jar -> X.jar
  本项目 -> C.jar -> X.jar (√)
 
 b、声明优先
  若引用路径长度相同时，在pom.xml中谁先被声明就使用谁。

4)、多模块项目/聚合
![binaryTree](/img/多模块聚合.png "binaryTree")
![binaryTree](/img/多模块聚合2.png "binaryTree")

2、Spring Cloud微服务
2.1、Eureka——提供服务注册和服务发现
是一个基于Rest的服务，它定位服务来进行中间层服务器的负载均衡和故障转移；
Eureka采用C/S的架构，也就是说它有一个服务器和多个客户端；
Eureka-server作为服务注册功能的服务器，它是服务注册中心，而系统中其它的微服务，
它会使用Eureka客户端连接，去连接到Eureka-server上，并维持一个心跳连接告诉微服务说Eureka说我这个微服务还是存在着的，
这样的客户端就可以Eureka-server来监控系统中其他各个微服务是否正常运行。

https://coding.imooc.com/lesson/310.html#mid=21645
2.2 Zuul-网关
https://coding.imooc.com/lesson/310.html#mid=21646
2.2.1 可以通过Eureka-server来发现系统中其它的微服务，并执行相关的逻辑；

微服务是一种架构风格，一个大型复杂的软件由一个或者多个微服务构成；系统中的各个微服务可以被独立部署，各个微服务之间是松耦合的，
每个微服务仅关注于完成一件任务就能很好的完成这个任务，在所有情况下每个任务代表一个小的业务能力；
关于微服务架构的两种方式：
1)、点对点：服务之间直接调用，每个微服务都开放Rest API，并调用其他微服务的接口；
这在比较简单的情况下是可行的，而且实现比较简单，但是在业务场景比较复杂的情况下，会变得越来越不可以维护；

2)、API-网关方式：所有的业务端和消费端都可以统一的网关接入微服务，在网关层处理所有的非业务功能；
通常网关也提供Rest API(http的访问)，服务端通过API-网关去注册和管理服务，所有的业务接口通过API-网关暴露，
它是所有客户端进入的唯一接口；微服务之间也通过API-网关去通信。
优势：有能力为微服务接口提供网关层次的抽象，微服务的接口可以各种各样，在网关层可以对外暴露统一的规范接口，而且是轻量的消息路由以及格式转换、
统一控制安全、监控、限流等等非业务功能；每个微服务会变得更加轻量级，且非业务功能都在网关层统一处理；微服务只需要处理业务逻辑。

![binaryTree](/img/API-网关方式.png "binaryTree")

2.2.2 Zuul原理
![binaryTree](/img/Zuul的生命周期.png "binaryTree")

Zuul的大部分功能都是通过过滤器实现的，过滤器的类型会对于请求的生命周期；
1)、Pre filters：在请求被路由之前调用；我们可以利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等等；
2)、Routing filters：请求路由到微服务，这种过滤器用于构造发送给微服务的请求，并且使用apache等 请求微服务；
3)、Post filters：路由到微服务以后去执行，这种过滤器可以用来为响应添加标准的http header头、收集统计信息和指标、将响应从微服务发送个客户端等等；
4)、Error filters：当请求发生了错误去执行的过滤器
5)、Custom filters：可以创建自定义的过滤器类型

3、微服务通用模块开发【企业级开发常见抽象】
https://coding.imooc.com/lesson/310.html#mid=21837

4、广告投放系统的开发【打好基础，才能迎接将来的挑战】
4.1、Spring IOC和MVC基础知识
Spring是一个开源的应用程序框架，提供了一个简易的开发方式，通过这种开发方式它将避免那些可能致使代码变得繁杂混乱的大量业务工具组合在一起；
包括它的创建与销毁等等，它会去帮我们管理，在被管理对象与业务逻辑之间 Spring会通过控制反转(IOC)来架起使用的桥梁。
IOC也可以看做是Spring最核心最重要的思想。Spring在启动的时候会读取应用程序提供的bean配置信息，
并在Spring容器中，生成一份相应的bean配置注册表，然后根据这个注册表实例化各个bean，装配好bean之间的依赖关系，为上层应用提供准备就绪的应用环境。

![binaryTree](/img/Spring IOC原理拆解.png "binaryTree")

1)、读取Bean的配置信息
2)、根据bean注册表实例化bean
3)、将bean实例放到Spring容器中：
启动容器、读取配置文件、初始化各个bean，并完成bean之间的依赖注入；
4)、应用程序使用

4.2、Spring MVC
Spring MVC是Spring的一部分，主要用来开发web应用和网络接口；是Spring的一个模块；
通过一些预定义的组件，让web应用的开发变得更加的容易。

![binaryTree](/img/Spring MVC模块解析.png "binaryTree")

Spring MVC的运行原理：
1)、Http请求：首先客户端发起Http请求，请求最先到达DispatchServlet；
DispatchServlet是Spring提供的前端控制器，所有的请求都由它统一分发，我们可以认为它就是一个网关一样；
2)、寻找处理器：DispatchServlet将请求分发给Controller之前，需要借助于Spring提供的HandlerMapping去定位到具体的Controller；
HandlerMapping可以拆解开，第一步是handler，第二步是mapping；
handler就是需要找寻具体的处理方法，mapping就是找到对应的处理方法；
3)、调用处理器：将请求提交Controller，Controller需要处理用户的请求，需要保证是线程安全，并且是可重用的；
4)、调用业务处理服务：Controller调用业务处理逻辑Service;
5)、得到处理结果：Service返回ModelAndView给到前端控制器；ModelAndView包含两个东西，一个是model一个是view；
model是应用程序所需要的数据，view就是视图信息；
6)、处理视图映射：DispatchServlet得到处理结果之后，会去查询一个或者多个处理视图的映射 ViewResolver(视图解析器)；
7)、模型数据传递到view层：找到ModelAndView指定的视图，然后对Model数据进行渲染，得到view；
8)、Http响应：最后返回给客户端。

总结:DispatchServlet是整个Spring MVC的核心，它负责http请求，然后组织协调MVC的各个组件和各个组成部分；
它的主要功能有以下三项：
a、捕获符合特定格式的URL请求
b、初始化DispatchServlet上下文，然后执行各个逻辑
c、初始化Spring MVC的各个组成部件，装配到DispatchServlet中，以便完成操作。

4.3、SpringBoot分析——常用功能特性介绍——springboot-study
4.3.1 SpringBoot应用启动入口
4.3.2 容器启动之后执行的操作
4.3.3 Profile环境配置
4.3.4 配置信息封装成实体类
4.3.5 定时任务

4.4、广告投放系统介绍
https://coding.imooc.com/lesson/310.html#mid=21838

4.5、MySQL慢查询
https://coding.imooc.com/lesson/310.html#mid=21839

4.6、MySQL索引
https://coding.imooc.com/lesson/310.html#mid=21839

4.7、MySQL事务隔离级别
https://coding.imooc.com/lesson/310.html#mid=21841

5、SpringCloud服务调用
https://coding.imooc.com/lesson/310.html#mid=21842
![binaryTree](/img/SpringCloud服务调用.png "binaryTree")
Ribbon：基于Http和TCP的客户端的负载均衡器，它可以通过在客户端中配置Ribbon Server List 来设置服务端列表去轮询访问，
达到负载均衡作用，Ribbon的主要功能就是去Eureka Server上面拿取Eureka Client的配置信息，去轮询访问实现远程调用。

Feign：是一个声明式的WebService客户端，它使得编写WebService客户端更加简单，我们只需要Feign来创建一个接口，应用注解来配置它就可以完成；
具备可插拔的注解支持，包括Feign注解，以及lis注解；Feign也支持可插拔的编码和解码；Spring Cloud为Feign增加了对SpringMVC注解的支持，
还整合了Ribbon和Eureka来提供负载均衡的http客户端的实现，也就是说Feign是基于Ribbon实现的。实际工作中基本使用Feign实现微服务之间的调用而不是使用Ribbon。

6、广告检索系统——广告数据索引的设计与实现
6.1、正向索引
通过唯一键/主键生成与对象的映射关系。
在本系统应用在推广计划、推广单元、创意等实体类。

6.2、倒排索引
也被成作是反向索引，是一种索引方法，它的设计是为了存储在全文搜素下某个单词在一个文档或一组文档中存储位置的映射。是在文档检索系统中最常用的数据结构。
![binaryTree](/img/倒排索引.png "binaryTree")

6.3 全量索引+增量索引
全量索引：检索系统在启动时一次性读取当前数据库中(注意，不能直接从数据库中读取?)的所有数据，建立索引。
增量索引：系统运行过程中，监控数据库变化，即增量，实施加载更新，构建索引。

总结：
https://coding.imooc.com/lesson/310.html#mid=21843

7、广告检索系统 – 加载全量索引【对业务的理解要透彻，才能一气呵成】
总结：
https://coding.imooc.com/lesson/310.html#mid=21863

8、广告检索系统 – 监听 Binlog 构造增量数据【技能提升：难度指数5颗星】
Mysql有很多日志：错误日志、更新日志、二进制日志(Binlog)、查询日志、慢查询日志；更新日志是老版本的Mysql才有的，
目前的已经被二进制日志替代，在默认情况下呢，系统仅仅打开错误日志，关闭的其他日志，以达到尽可能减少IO损耗，
提高系统性能的目的。
8.1、什么是Binlog?
二进制日志，记录对数据发生或潜在发生更改的SQL语句，并以二进制的形式保存在磁盘中。

一般稍微重要一点的实际应用场景中，都需要打开Binlog开关，这是MySQL许多存储引擎进行增量备份的基础，
是MySQL实现复制的基本条件。
比如两个集群，主集群对我们的应用程序提供服务，它需要把写入数据不断同步到从集群，也就是master 到 slave的同步过程。

8.2、Binlog的作用是什么？
复制、恢复和审计。

8.3、Binlog相关变量
log_bin、binlog_format。
![binaryTree](/img/binlog相关变量.png "binaryTree")

8.4、Binlog日志的三种格式
ROW：记录每一行数据被修改的形式，然后再slave端对相同数据进行修改；ROW模式可以不记录执行的SQL语句是什么，
只需要记录哪条数据被修改成什么样子了，它不会因为某些语法复制出现问题，比如函数、触发器等等。
它的缺点是每行数据的修改都会记录下来，最明显的就是update语句，会导致更新多少条语句就会产生多少事件。
使Binlog文件变得很大，而复制数据需要通过网络传输，也就会影响性能。

STATEMENT：每一条修改数据的sql都会记录在Binlog中，slave端再根据sql语句去重现，它解决了ROW模式的缺点：
它不会产生大量的Binlog数据。它的缺点是为了让sql能够在slave端去正确的重现，需要记录sql执行的上下文信息。
另外一个问题就是在复制某些特殊的函数或者功能时会出现问题，比如：slip函数。

MIXED：是以上两种的结合，根据不同情况使用ROW模式或者是STATEMENT，保证Binlog记录能够正确表达。
![binaryTree](/img/Binlog日志的三种格式.png "binaryTree")

8.5、管理Binlog相关的SQL语句
![binaryTree](/img/管理Binlog相关的SQL语句.png "binaryTree")

8.6、Binlog中的Event_type
show binlog events
![binaryTree](/img/Binlog中的Event_type.png "binaryTree")

8.7、总结
https://coding.imooc.com/lesson/310.html#mid=22015

[Java8 Map computeIfAbsent方法说明](https://blog.csdn.net/weixin_38229356/article/details/81129320)

8.8、总结
[关于监听 Binlog 构造增量数据的介绍及作业](https://coding.imooc.com/lesson/310.html#mid=22017)

9、广告检索系统 – Binlog 增量数据的投递【企业级开发必备技能】
[Binlog 增量数据投递的介绍及作业](https://coding.imooc.com/lesson/310.html#mid=22018)

10、广告检索系统 – 广告检索服务【技能与业务思想再提升：难度指数5颗星】
[广告检索服务的介绍](https://coding.imooc.com/lesson/310.html#mid=22094)

11、Kafka 的安装与使用【不仅能应用在工作与面试中，还会用于架构优化】
11.1、Kafka基础知识的介绍
1)、消息系统
一个消息系统负责将数据从一个应用传递给另一个应用。应用只需要关注于数据，无需关注数据在两个应用间是如何传递的。
分布式的消息传递基于可靠的消息队列，在客户端应用和消息系统之间异步传递消息。
有两种主要的消息传递模式：
a、点对点消息系统：消息持久化到队列中，此时将有一个或多个消费者消费队列中的数据，但是一条消息只能被消费一次。
当有一个消费者消费了队列中的某条数据之后，该条数据则从消息队列中删除。这个模式即使有多个消费者同时消费数据，
也能够保证数据处理的顺序。

b、发布订阅消息系统：将消息持久化到topic中。它与点对点消息系统不同的是，消费者可以订阅一个或多个topic。
消费者可以消费这个topic中所有的数据。同一条数据可以被多个消费者消费，数据被消费后不会立马删除。
在发布订阅消息系统中，消息的生产者成为发布者，消费者成为订阅者。

2)、Kafka术语
apache kafka是一个分布式的发布订阅消息系统，它能够支撑海量数据的数据传递。在离线和实时的消息处理业务系统中，kafka都有广泛的应用。
kafka它会将消息持久化到磁盘中，对消息创建了备份，保证了消息的安全。kafka在保证了较高的处理速度同时，又能够保证数据处理的低延迟和数据的零丢失。

a、topic(主题)：在kafka中，使用一个类别属性来划分数据的所属类。划分数据的这个类就称为topic。
如果把kafka看做一个数据库呢，topic就可以理解为数据库的一张表。topic的名字也就是表的名字。

b、partition分区：topic中的数据被分割成一个或多个partition。每个topic至少有一个partition。每个partition的数据使用多个 segment文件(段文件)去存储。
partition中的数据是有序的，partition间的数据丢失了数据的顺序，也就是说对于一个partition来说，那么数据一定是有序的；多个partition之间的数据可能是没有顺序的。
那如果topic有多个partition，那消费数据时就不能保证数据的顺序。如果想严格的保证消息的消费顺序的场景，就需要将partition的数字设为1。

c、offset：每一条消息(msg)都会有一个当前partition下的、唯一的、64个字节的offset。它指明了这条消息的起始位置。
也就是说0这个数字就标识了第一条消息的起始位置。1就标识了第二条消息的起始位置。

d、partition的副本：就是partition的备份。副本不会被消费者消费，只用于防止数据丢失。

e、broker：kafka的集群包含一个或多个服务器，服务器节点就被称之为broker。broker存储了topic的数据，如果某个topic有n个partition，集群中有n个broker。
那么每个broker存储topic的一个partition。主要是维护整个系统的负载均衡。
broker就可以理解为集群中的每一台机器，或者服务器；服务器中会存储各个topic的partition——生产者生产的数据。

f、producer：生产数据的发布者，将消息发布到kafka的topic中。broker接收到生产者发送的消息之后，将该消息追加到当前用于追加数据的segment对应的文件中。
生产者发送的消息存储的以一个partition中。生产者也可以指定数据存储的partition。

g、consumer：消费者去消费生产者生产的数据，消费者可以从broker中读取数据，消费者可以消费多个topic中的数据，多个消费者构成了一个group，去消费不同的msg。

![binaryTree](/img/kafka术语.png "binaryTree")

3)、Kafka的安装和使用

4)、Kafka发送消息与消费消息
三种发送方式
a、只发送，不管返回值
b、同步发送代码
c、异步发送 等待回调

5)、Kafka Producer消息分区
key可空。
![binaryTree](/img/Kafka Producer消息分区.png "binaryTree")

6)、消费者与消费者组
Kafka消费者是消费者组的一部分，当多个消费者形成一个消费者组来消费topic时，每个消费者会收到不同分区的消息。
![binaryTree](/img/消费者组.png "binaryTree")
![binaryTree](/img/消费者组2.png "binaryTree")
![binaryTree](/img/消费者组3.png "binaryTree")
consumer的数量不应该比分区数多，kafka的特性就是只需要写入一次消息可以支持任意多个应用读取这个消息。
换句话说每个应用都能读到全量消息。
总结：如果应用需要读取全量消息，那么就应该为应用设置消费者组。如果应用的消费能力不足，可以给消费者组里面增加其他的消费者。

7)、消费消息
消费消息的方式：
a、自动提交位移
b、手动提交当前位移
c、手动异步提交当前位移
d、手动异步提交当前位移带回调
e、混合同步与异步提交位移
[kafka介绍](https://coding.imooc.com/lesson/310.html#mid=22095)

12、熔断监控Hystrix Dashboard【添加监控，完善系统】
12.1、Hystrix断路器
可以实现服务降级功能，也就是说我们定义的服务如果发生了错误就可以使用Hystrix定义错误之后的回退。
通常在Spring Cloud工程中去使用Hystrix组件是通过HystrixCommand注解。

12.2、总结
[Hystrix Dashboard 的介绍](https://coding.imooc.com/lesson/310.html#mid=22184)

13、广告系统的可用性测试【开发完功能，验证下是否好用】
13.1 关于广告系统可用性测试的介绍及说明
[关于广告系统可用性测试的介绍及说明](https://coding.imooc.com/lesson/310.html#mid=22186)

14、（彩蛋番外篇一）Spring事务处理机制【工作与面试，你都会遇到】
14.1、异常和事务基础 SpringTransactionImpl.java
![binaryTree](/img/异常的继承结构.png "binaryTree")
Throwable：Java中所有异常和错误的超类。

Error：出现了十分严重或者不可恢复的错误，在这种情况下应用程序只能终止运行，例如java虚拟机出现错误。
Error是一种 Unchecked Exception。编译器不会检查Error是否被处理，在程序中不用捕获Error程序的异常。
一般情况下在程序中我们也不应该去抛出Error类型的异常。

Runtime Exception：同样是Unchecked Exception。也就是说编译器不会检查程序是否对Runtime异常做了处理。
在程序中也不必去捕获Runtime类型的异常。也就是说我们没必要在方法体中声明抛出Runtime异常这个类。
Runtime发生时表示程序中出现了编程错误，所以应该去找出错误修改程序，而不是去捕获异常。

Checked Exception：表示无效，不是程序中可以预测的，比如无效的用户输入、文件不存在、网络或者数据库链接错误等。
这些都是外在原因，都不是程序内部可以控制的。那必须在代码中显示的进行处理，比如使用try catch处理，
或者给所在的方法上加上throw说明，将异常抛到调用栈的上一层。

Spring使用声明式的事务处理，在默认情况下如果被注解的数据库操作，操作中发生了Unchecked异常，所有的数据库操作将被回滚。
那如果发生的是Checked异常，在默认情况下数据库操作是会提交的。

14.2、事务的四个特性(ACID)
1)、原子性：事务是最小的执行单位，它不允许分割，事务的原子性确保这个动作要么全部完成，要么全部不起作用。
2)、一致性：执行事务的前后，数据需要保持一致。
3)、隔离性：在并发访问数据库的时候，一个用户的事务不能被其他事务所干扰。各个并发事务之间，数据库是独立的。
4)、持久性：一个事务被提交之后，它对数据库中数据的改变是持久的，也就是说数据库发生故障也不应该对其有任何影响。

14.3、Spring 事务的属性和使用入口
事务属性：传播行为、隔离级别、事务超时、只读事务、回滚规则
我们在使用Spring事务时，通常会使用声明式的事务处理，而不会使用编程式的事务处理。

使用入口——声明式
@Transactional

可以标记在类上、接口和方法上，如果标记在类上类中的所有方法都进行事务处理。如果标记在接口或者方法上这个方法会进行事务处理。
优先级方面，方法注解会大于类注解。
默认是事务传播机制——required。含义是：如果存在一个事务，则支持当前事务；如果没有事务，则开启一个新的事务。

1)传播机制
REQUIRED：如果存在一个事务，则支持当前事务；如果没有事务，则开启一个新的事务。
SUPPORTS：如果存在一个事务就去支持当前事务；如果没有事务，则就以非事务的形式去执行。
MANDATORY：如果存在一个事务就支持当前事务；如果没有一个活动事务，就对外抛出一个异常。
REQUIRES_NEW：总是会开启一个新事务，如果一个事务已经存在，则将这个事务挂起。
NOT_SUPPORTED：总是以非事务的形式去执行，并挂起任何存在的事务。
NEVER：总是以非事务的形式去执行，如果存在一个活动事务，则会抛出一个异常。
NESTED：如果一个活动的事务存在，则运行在一个嵌套的事务中；如果没有活动的事务，则按照REQUIRED属性执行。

2)、隔离级别
默认情况下会指向 DEFAULT 级别，它的意思是使用后端数据库默认的隔离级别。
Spring会定义事务的隔离级别，同样它会映射到数据库的隔离级别上面。
一共定义了5个隔离级别：
a、DEFAULT：
b、READ_UNCOMMITTED：
c、READ_COMMITTED：
d、REPEATABLE_READ：重复读
e、SERIALIZABLE：序列化

3)、timeout：超时时间，单位s
一个事务所允许执行的最长时间，如果超过这个时间事务还没有完成，则会自动回滚事务。
默认值是-1，指的是当前数据库默认的事务过期时间。

4)、readonly：定义事务是否是只读的，默认是false
由于只读事务不存在，因此数据库将会为只读事务提供一些优化手段。

5)、rollbackFor：回滚规则
用于设置需要进行回滚的异常类的数组。当方法中抛出指定了异常类的数组时，事务将进行回滚。
rollbackForClassName——设置需要进行回滚的异常类名称数据
noRollbackFor——设置不需要回滚的异常类数组
noRollbackForClassName——设置不需要进行回滚的异常类名称数据

使用@Transactional需要注意的地方：
a、该注解只能应用到public方法才有效
b、在默认配置中, Spring事务处理框架只会将Runtime Unchecked异常的事务标记为回滚，
也就是说，当事务中抛出的异常类是Runtime异常或者其子类这样的事务才会回滚。在默认情况下，
所有的Checked异常都不会引起事务的回滚。

[@Transactional注解解析](https://coding.imooc.com/lesson/310.html#mid=22706)
[Spring 事务管理接口](https://coding.imooc.com/lesson/310.html#mid=22711)
[Spring 阶段练习](https://coding.imooc.com/lesson/310.html#mid=22771)

[Spring 中 @Transactional 注解的限制](https://coding.imooc.com/lesson/310.html#mid=23215)

15、（彩蛋番外篇二）重构检索系统微服务【架构重新设计，优化系统性能】
15.1、再谈MySQL的Master/Slave协议
MySQL的主从复制，是一个异步的复制过程。数据库数据从一个MySQL数据库——Master复制到另一个MySQL数据库——Slave。
在Master与Slave之间，实现整个主从复制的过程是由三个线程去参与完成的。其中有两个线程在Slave端。
图中右边的部分，是MySQL的Slave。它参与MySQL主从复制有两个线程，一个是IO线程，另一个是SQL线程。
增删改对于MySQL的Master来说它会记录到数据文件里，同时由于我们更改了数据记录，Master会把它写入Binlog里面。
然后Slave和Master之间同步数据，利用这三个线程和Binlog完成同步。

主从复制过程：Master/Slave协议的工作过程
a、首先Slave服务器上去执行 "start Slave" 命令，去开启主从复制的开关；此时Slave服务器上的IO线程呢会通过Master服务器上授权的
有复制权限的用户去请求连接Master服务器，并请求从指定的Binlog日志文件的指定位置之后，发送Binlog的日志内容。
需要注意，日志的文件名和位置就是在配置主从复制的任务的时候，去执行 master命令的时候指定。
Slave想要从Master获取复制数据，由Slave告诉Master，需要哪个Binlog文件以及Binlog的位置等等，由Slave的IO线程去发起。

b、Master服务器接受到来自Slave服务器的IO线程的请求之后，Master服务器的IO线程会根据Slave服务器的IO线程的请求信息，
去读取指定的Binlog日志文件指定的位置之后的Binlog日志信息然后返回给Slave端的IO线程。返回的信息中除了Binlog的日志内容之外，
还有本次返回日志内容后在Master服务器端的新的Binlog文件的名字，以及在Binlog中的下一个指定的更新位置。

c、当Slave服务器的IO线程获取了来自Master服务器上的IO线程发送的Binlog的日志内容及日志文件的位置之后，
它会将Binlog的日志内容写入到Slave自己的 relay-log中，写入到这个文件的最末端。并将新的Binlog文件的名字和位置记录到Master info这个文件里面，
以便下一次去读取Master端新的Binlog日志时能告诉Master服务器，需要从新的Binlog的哪个文件的哪个位置，开始去请求新的Binlog日志内容。
核心解释就是说，Master发送一些信息之后，Slave需要对它的一些信息进行保存。那新的Binlog的文件以及位置由Slave通过IO线程保存到master-info文件里。
Master Binlog日志会由IO线程保存到relay-log里面。

d、Slave服务器端的SQL线程会实时检测本地的relay-log中新增加的日志内容，然后及时把relay-log文件中的内容，
解析成在Master端曾经执行的SQL语句的内容，并在自身Slave服务器上按语句的顺序执行，去应用这些SQL语句。
应用完毕之后会清理应用过的日志。

![binaryTree](/img/MySQL的Master&Slave协议.png "binaryTree")

[微服务包的设计思想](https://coding.imooc.com/lesson/310.html#mid=24888)
15.2、广告检索微服务架构设计优化

![binaryTree](/img/当前的检索服务实现.png "binaryTree")
![binaryTree](/img/优化后实现.png "binaryTree")

16、分布式日志收集系统【全方位的开发过程，扩充自身知识面】
16.1 【初识 ELK】ELK 的概念、功能和组织结构
1)、为什么需要日志系统
通常当系统发生故障的时候，工程师需要登录到各个服务器上，使用grip、sed、awk等等这样的Linux命令去日志里面查找故障的原因。
由于微服务比较多，而且一个微服务可能部署多个实例，所以需要登录各个服务器查找日志。
在没有日志系统的情况下，首先需要去定位处理请求的服务器。如果这台服务器部署了多个实例，则需要去每一个应用实例的日志目录下查找日志文件。
那每个应用实例一般会设置日志的滚动策略，比如每一天生成一个文件或者每一个小时生成一个日志文件等等。
此外还有日志的压缩存储，日志的归档等等策略。这样一系列流程下来对于我们取排查故障以及能够及时找到故障的原因就会造成比较大的麻烦。
因此如果我们能把这些日志去集中管理，并集中的检索功能，不仅可以提供整个的效率，同时对系统情况有一个全面的了解避免了时候救火的一个被动局面。

日志系统需求：
a、数据查找：通过检索日志信息，定位相应bug，我们在以此找到解决方案；
b、服务诊断：通过对日志信息进行统计、分析了解服务器的复核，以及服务运行的状态；
c、数据分析：我们可以在系统中打印一些比较特殊的日志，比如请求数、响应数等等，后来可以去统计这些日志做一些进一步的数据分析。

2)、什么是ELK?
E(Elasticsearch/ES)：它是一个开源的分布式的搜索引擎，它的特点是分布式、零配置、自动发现、RESTFul风格接口、多数据源以及自动搜索负载等等。
在日志系统中，常常把它作为对日志的存储以及日志的检索服务。
L(Logstash)：开源工具，它可以对日志进行收集、过滤、并将其存储供以后使用。
Logstash的核心用途就是接受我们服务的日志，并把它发送给ES。
K(Kibana)：开源工具，Kibana可以为Logstash和ES提供日志分析友好的一个web界面。可以帮助我们去汇总、分析和搜索重要的数据日志。
它还可以自定义各种图表等等。Kibana的主要功能就是对日志数据的展示，以及对数据的搜素功能。
[ELK 的下载、安装和配置](https://coding.imooc.com/lesson/310.html#mid=26063)

3)、将Logstash替换为Filebeat
Logstash的优点：Logstash的最重要的功能是对日志的收集，最大的优点是能够对日志进行一个预处理。这里的预处理指的是，我们通过将本地日志接收到Logstash里面，
之后Logstash对日志进行再加工。然后Logstash把加工后的日志向ES集群去发送。

Logstash的缺点：
a、基于ruby的配置
b、依赖于JDK
c、资源消耗大

Filebeat是用来替代Logstash的下一代日志收集器。它是为了更快速而且稳定，轻量的收集日志。它可以很方便的去Logstash。
或者直接与ES进行对接。Filebeat本身可以与Logstash进行结合，再由Logstash去向ES发送消息。它也可以直接去往ES里发送消息。
相对于Logstash而言，Filebeat会有三个特别重要的优势：
a、基于YAML配置：现在写的Spring项目都是基于YAML来做配置，那么简单易懂，在配置语法上几乎没有学习的成本；
b、Filebeat是二进制文件，不需要安装，没有依赖，直接运行就可以；
c、占用资源极少，它非常的轻量级，它只会用到10M或者几十兆的内存资源。

Filebeat缺点：不能对日志进行预处理，原样丢到ES里。

4)、ES 5.X支持Ingest Node
Ingest Node：预处理节点，是ES用于功能上命名的节点类型。可以通过在ES自己的配置文件里进行配置，来标识出集群中的某一个节点是否是Ingest Node。
node.ingest:true，默认情况下ES集群中所有的节点都具有预处理能力。这里的预处理就是对发送到ES的数据进行预处理。

5)、日志预处理
一般情况下，并不会将原始的日志直接去发送到ES集群里面。而是对原始的日志信息进行一个再加工。预处理之后的日志，再会发送到ES集群里面。
比如Logstash会支持多种解析器，比如json、data。
在ES没有提供Ingest Node功能时，我们想对存储在ES里的数据在存储之前进行一个加工处理的话，我们只能依赖Logstash。或者自定义插件来完成这个功能。
但是实现成本非常高。
ES中的两个概念：Pipeline，Processors

[日志收集系统的说明](https://coding.imooc.com/lesson/310.html#mid=25881)
[SpringBoot 项目的日志配置](https://coding.imooc.com/lesson/310.html#mid=25882)

17、（彩蛋番外篇四）常见问题解答【第一季】
17.1、SpringCloud相关问题
1)、SpringCloud和Dubbo的选择问题，Zuul和GateWay的选择问题？
技术选型的选择问题。
SpringCloud和Dubbo从原理和思想上来说，几乎是一样的，都是常用的微服务框架。

a、不纠结技术选型和技术实现，注重业务思想；
b、不需要特别在意框架或者工具的版本问题；
只要能够解决目前的问题，不必要去纠结这写，除非新版本提供的功能能够大大简化实现。

2)、Eureka单节点没有问题，但是多节点的Eureka出现问题？
单节点的三步骤：创建工程、配置好Maven依赖；编写启动程序；启动类添加注解
多节点的Eureka：情况就变得复杂很多，搭建多节点的Eureka是因为在实际的企业级开发中，不允许存在单节点服务的；
单点故障是不允许的；
多节点的三步骤：修改host；配置工程的配置文件；编译打包、执行命令

3)、Eureka Server中维护了Client的哪些信息？
![binaryTree](/img/DiscoveryClient_java.png "binaryTree")
![binaryTree](/img/DiscoveryClient_java2.png "binaryTree")

4)、Eureka Server又是怎样存储这些信息的？
![binaryTree](/img/ApplicationResource.png "binaryTree")
![binaryTree](/img/PeerAwareInstanceRegistry.png "binaryTree")

17.2、数据表与数据相关的问题
1)、对数据表分层级(逻辑分层——代码定义)的原因？
 a、表示表与表之间的依赖关系：Level3依赖于Level2，以此类推
 b、表的逻辑分层：简化处理相同的业务逻辑

2)、为什么要把全量数据导出到文件中，而不是服务直接从数据库中加载？
在启动检索系统之前，会把数据库中的数据导出到一份文件中。这份文件中的数据以行为单位，
而且每一行都是一条json数据，描述了当前数据库中存储的全量感冒数据。
全量数据文件应该方到公共文件系统上，例如NFS或FTP。
 a、由于多个实例存在，如果只有几个实例(2-3),可以考虑不需要导出到文件，直接连接数据库去读取全量数据。
 但是说有几十上百实例就会存在很大问题。给数据库造成巨大的压力。

3)、为什么广告数据要放在JVM内存中？广告数据太多内存放不下怎么办？
a、在做检索系统中，把广告数据放到了HashMap中，也就是保存到了Java自己的内存中——JVM。
原因就是快、快、快。

广告太多：
a、数据分区：把数据按照hash算法散列到不同机器上，每台机器只维护少量原信息和部分广告数据——现实开发不推荐
b、redis——现实开发推荐

4)、Binlog是什么？如果监听的过程中，MySQL宕机了，再次启动服务，会是什么情况？
二进制日志，记录对数据发生或潜在发生更改的SQL语句，并以二进制的形式保存在磁盘中。
Binlog记录了数据库所有的DDL和DML语句，以事件形式记录。主要用途是数据同步。

MySQL宕机了，可以在代码中记录 Binlog pos即可。每一次读取都会记录下，下次启动服务时，设置下这个pos即可。

5)、为什么会选用Binlog作为增量数据的收集方案？

6)、为什么要把Binlog的增量数据发送到kafka上，微服务再去订阅kafka的消息？
监听到Binlog数据之后，把数据投递到kafka中，检索系统再去监听kafka的topic从而得到增量数据。
这样做的好处就是降低数据库的压力，如果不使用kafka就需要所有的检索服务实例都去监听binlog。
那么每一个服务实例都会占用一个数据库连接线程。而且数据库传输都会消耗网络IO。加入到kafka之后，
只需要一个实例去监听binlog数据，并且把数据发送到kafka上。检索系统连接到Kafka，读取就可以了。
由于kafka是一个高性能的消息队列，我们做的就是数据消息在不同系统之间的传递。这是非常合理的设计方案。

17.3、Spring与基础工具相关问题
1)、为什么要使用JPA而不是MyBatis？
JPA和MyBatis都是优秀的ORM框架或规范；二者都有各自的优缺点：
JPA操作简单，只需要去定义数据表映射的实体类，在定义仓库接口就可以使用。
但是MyBatis却需要时候烦乱的xml去定义sql操作。

JPA做表与表之间的关联是很复杂的，但是MyBatis可以直接去xml编写sql语句灵活方便的操作表关联。

2)、pom中引入了依赖坐标，但是项目没有加载进来，可能的原因是什么？
依赖的三个坐标元素：groupId、artifactId、version
a、坐标填写的是否正确、pom文件是否存在错误、网络问题
b、通过mvn compile命令校验下pom文件和代码是否存在错误

3)、为什么 @Autowire写在构造函数上(第二种)，而不是类属性上(第一种)？
![binaryTree](/img/autowire.png "binaryTree")

4)、各个微服务是怎么部署的？
父模块定义的是pom，子模块定义的是Jar，实际部署只是子模块。
首先部署Eureka、其次是zuul，其它根据业务场景部署即可。

