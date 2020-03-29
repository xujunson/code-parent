基于Spring Cloud微服务架构 广告系统设计与实现（2020新版）

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