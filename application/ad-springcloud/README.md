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