-----------------------------2019-01-07--------------------------------
1、@RequestBody ：参考：https://www.cnblogs.com/qiankun-site/p/5774300.html
@requestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，
比如说：application/json或者是application/xml等。
一般情况下来说常用其来处理application/json类型。
通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上，当然，也可以将其分别绑定到对应的字符串上。
　　　　例如说以下情况：
　　　　$.ajax({
　　　　　　　　url:"/login",
　　　　　　　　type:"POST",
　　　　　　　　data:'{"userName":"admin","pwd","admin123"}',
　　　　　　　　content-type:"application/json charset=utf-8",
　　　　　　　　success:function(data){
　　　　　　　　　　alert("request success ! ");
　　　　　　　　}
　　　　});

　　　　@requestMapping("/login")
　　　　public void login(@requestBody String userName,@requestBody String pwd){
　　　　　　System.out.println(userName+" ："+pwd);
　　　　}
　　　　这种情况是将JSON字符串中的两个变量的值分别赋予了两个字符串，但是呢假如我有一个User类，拥有如下字段：
　　　　　　String userName;
　　　　　　String pwd;
　　　　那么上述参数可以改为以下形式：@requestBody User user 这种形式会将JSON字符串中的值赋予user中对应的属性上
　　　　需要注意的是，JSON字符串中的key必须对应user中的属性名，否则是请求不过去的。

2、@RequestParam ：参考：http://www.cnblogs.com/likaileek/p/7218252.html
 用于将请求参数区数据映射到功能处理方法的参数上。
 可以通过required=false或者true来要求@RequestParam配置的前端参数是否一定要传。
 required=false表示不传的话，会给参数赋值为null，required=true就是必须要有.
 如果@requestParam注解的参数是int类型，并且required=false，此时如果不传参数的话，会报错。
 原因是，required=false时，不传参数的话，会给参数赋值null，这样就会把null赋值给了int，因此会报错。

3、Spring上下文 ：参考：https://www.cnblogs.com/chenbenbuyi/p/8166304.html
https://www.jianshu.com/p/b3eab5acc7f4
https://www.cnblogs.com/brolanda/p/4265597.html

4、Spring Boot使用ServletFileUpload上传文件失败
Spring Boot中有默认的文件上传组件，在使用ServletFileUpload时需要关闭Spring Boot的默认配置 ，
所以在配置文件中添加：servlet:multipart:enabled: false.

-----------------------------2019-01-08--------------------------------
5、JSON ：参考：https://www.cnblogs.com/kuikui/p/3176806.html
JSON(JavaScript Object Notation(记号、标记)) 是一种轻量级的数据交换格式。
它基于JavaScript（Standard ECMA-262 3rd Edition - December 1999）的一个子集。 
JSON采用完全独立于语言的文本格式，但是也使用了类似于C语言家族的习惯（包括C, C++, C#, Java, JavaScript, Perl, Python等）。
这些特性使JSON成为理想的数据交换语言。JSON易于人阅读和编写，同时也易于机器解析和生成。
简单地说，JSON 可以将 JavaScript 对象中表示的一组数据转换为字符串，然后就可以在函数之间轻松地传递这个字符串，
或者在异步应用程序中将字符串从 Web 客户机传递给服务器端程序。

6、序列化与反序列化：参考：https://www.cnblogs.com/xdp-gacl/p/3777987.html

序列化：把对象转换为字节序列的过程称为对象的序列化。
反序列化：把字节序列恢复为对象的过程称为对象的反序列化。

当两个进程在进行远程通信时，彼此可以发送各种类型的数据。无论是何种类型的数据，都会以二进制序列的形式在网络上传送。
发送方需要把这个Java对象转换为字节序列，才能在网络上传送；接收方则需要把字节序列再恢复为Java对象。


-----------------------------2019-01-10--------------------------------

8、 ORACLE WITH AS 用法 ：参考：https://www.cnblogs.com/mingforyou/p/8295239.html
9、JDBCTemplate 用法：参考：https://blog.csdn.net/h294590501/article/details/80428192
10、Java web项目系统间的通信的三种方式：参考：https://blog.csdn.net/jinhaijing/article/details/81167240
11、Spring构造器注入原理：https://mp.weixin.qq.com/s/OfjBweQ9Ur8792rmKjOoiA  
https://www.cnblogs.com/shijiaoyun/p/7458341.html

-----------------------------2019-01-11--------------------------------

12、Nginx 相关介绍
参考：https://www.cnblogs.com/wcwnina/p/8728391.html

-----------------------------2019-01-26--------------------------------

1、阅读：为什么一定要用消息中间件？
http://mp.weixin.qq.com/s?__biz=MzI1NDQ3MjQxNA==&mid=2247488261&idx=1&sn=45e262953a11ddb3cfabca0430f2944b&chksm=e9c5eab4deb263a25370c50556e33e0fa3b7d92b447097d519c522dad738fcc307b4497e26c0&scene=0#rd

-----------------------------2019-01-29--------------------------------

1、阅读：List相关知识。
https://mp.weixin.qq.com/s/U9cRDO4P5noUBN2Sak5G5Q

3、避免空指针。
http://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247488192&idx=1&sn=258dff0d6b10266ba3e1a2adec4c0fdb&chksm=eb5397f6dc241ee0780f31dfc41945e24a689204f6d4b4277aaceb1ba027bcc5159dcab9621d&scene=0#rd
4、ThreadPoolTaskExecutor、Optional

-----------------------------2019-02-18--------------------------------
1、 SQL函数：
   1.1 nvl()函数的格式如下：NVL(expr1,expr2);
       如果oracle第一个参数为空那么显示第二个参数的值，如果第一个参数的值不为空，则显示第一个参数本来的值。
   1.2 case when 
   1.3 decode函数 --- 流程控制函数 DECODE http://www.cnblogs.com/qianyuliang/p/6841881.html
   1.4 || 拼接字符串

-----------------------------2019-02-25--------------------------------
1、cookie和session的区别和用法：https://www.cnblogs.com/xxtalhr/p/9053906.html
 cookie：
在网站中，http请求是无状态的。也就是说即使第一次和服务器连接后并且登录成功后，第二次请求服务器依然不能知道当前请求是哪个用户。cookie的出现就是为了解决这个问题，第一次登录后服务器返回一些数据（cookie）给浏览器，然后浏览器保存在本地，当该用户发送第二次请求的时候，就会自动的把上次请求存储的cookie数据自动的携带给服务器，服务器通过浏览器携带的数据就能判断当前用户是哪个了。cookie存储的数据量有限，不同的浏览器有不同的存储大小，但一般不超过4KB。因此使用cookie只能存储一些小量的数据。

 session:
session和cookie的作用有点类似，都是为了存储用户相关的信息。不同的是，cookie是存储在本地浏览器，而session存储在服务器。存储在服务器的数据会更加的安全，不容易被窃取。但存储在服务器也有一定的弊端，就是会占用服务器的资源，但现在服务器已经发展至今，一些session信息还是绰绰有余的。

-----------------------------2019-02-26--------------------------------
1、Iterator 的使用:https://blog.csdn.net/weixin_41670928/article/details/80108085s

-----------------------------2019-02-28--------------------------------
一、Nginx相关介绍。

Nginx同Apache一样都是一种WEB服务器。基于REST架构风格，以统一资源描述符(Uniform Resources Identifier)URI或者统一资源定位符(Uniform 
Resources Locator)URL作为沟通依据，通过HTTP协议提供各种网络服务。

Apache优点:稳定、开源、跨平台
Apache不足之处：重量级的，不支持高并发的服务器。在Apache上运行数以万计的并发访问，会导致服务器消耗大量内存。操作系统对其进行进程或线程间的切换也消耗了大量的CPU资源，导致HTTP请求的平均响应速度降低。

轻量级高并发服务器Nginx就应运而生。

Nginx用武之地：
 Nginx是一款自由的、开源的、高性能的HTTP服务器和反向代理服务器；同时也是一个IMAP、POP3、SMTP代理服务器；Nginx可以作为一个HTTP服务器进行网站的发布处理，另外Nginx可以作为反向代理进行负载均衡的实现。

Nginx能做什么：
 参考链接： 
 	Nginx 相关介绍(Nginx是什么?能干嘛?)：https://www.cnblogs.com/wcwnina/p/8728391.html
 	如何给女朋友解释什么是反向代理？：https://mp.weixin.qq.com/s/YQfk9lqHbepG2om7mEGTSg
 	如何给女朋友解释什么是负载均衡：https://mp.weixin.qq.com/s/73igJheiKDvyQR2kftA1dA
 	仅需这一篇，吃透「负载均衡」妥妥的：https://mp.weixin.qq.com/s/SO1ZLSPaRkk8WwUl9u-JPA
 	Nginx 极简教程，覆盖了常用场景：https://mp.weixin.qq.com/s/vHkxYfpuiAteMNSrpNWdsw

 1.正向代理、 2.反向代理、3.负载均衡、4.HTTP服务器（包含动静分离）

 1、正向代理
  1.1 特点：正向代理最大的特点是客户端非常明确要访问的服务器地址；服务器只清楚请求来自哪个代理服务器，而不清楚来自哪个具体的客户端；正向代理模式屏蔽或者隐藏了真实客户端信息。

  1.2 注意：客户端必须设置正向代理服务器，当然前提是要知道正向代理服务器的IP地址，还有代理程序的端口。

  1.3 总结来说：正向代理，"它代理的是客户端"，是一个位于客户端和原始服务器(origin server)之间的服务器，为了从原始服务器取得内容，客户端向代理发送一个请求并指定目标(原始服务器)，然后代理向原始服务器转交请求并将获得的内容返回给客户端。客户端必须要进行一些特别的设置才能使用正向代理。
  正向代理，其实是"代理服务器"代理了"客户端"，去和"目标服务器"进行交互
  1.4 正向代理的用途：
	（1）突破访问限制 ，访问原来无法访问的资源，如Google
	（2）提高访问速度， 可以做缓存，加速访问资源
	（3）对客户端访问授权，上网进行认证
	（4）隐藏客户端真实IP，代理可以记录用户访问记录（上网行为管理），对外隐藏用户信息

  2、反向代理
   2.1 特点：多个客户端给服务器发送的请求，Nginx服务器接收到之后，按照一定的规则分发给了后端的业务处理服务器进行处理了。此时~请求的来源也就是客
   		户端是明确的，但是请求具体由哪台服务器处理的并不明确了，Nginx扮演的就是一个反向代理角色。

   		客户端是无感知代理的存在的，反向代理对外都是透明的，访问者并不知道自己访问的是一个代理。因为客户端不需要任何配置就可以访问。

   		反向代理，"它代理的是服务端"，主要用于服务器集群分布式部署的情况下，反向代理隐藏了服务器的信息。
   		反向代理，其实是"代理服务器"代理了"目标服务器"，去和"客户端"进行交互。
   2.2 反向代理的作用：
	（1）隐藏服务器真实IP，保证内网的安全，通常将反向代理作为公网访问地址，Web服务器是内网
	（2）负载均衡，通过反向代理服务器来优化网站的负载，根据所有真实服务器的负载情况，将客户端请求分发到不同的真实服务器上。
	（3）提高访问速度，反向代理服务器可以对于静态内容及短时间内有大量访问请求的动态内容提供缓存服务，提高访问速度。
	（4）提供安全保障，反向代理服务器可以作为应用层防火墙，为网站提供对基于Web的攻击行为（例如DoS/DDoS）的防护，更容易排查恶意软件等。还可以为后端服务器统一提供加密和SSL加速（如SSL终端代理），提供HTTP访问认证等。
   
   2.3 正向代理和反向代理的区别：
   	虽然正向代理服务器和反向代理服务器所处的位置都是客户端和真实服务器之间，所做的事情也都是把客户端的请求转发给服务器，再把服务器的响应转发给客户端，但是二者之间还是有一定的差异的。

   	1、正向代理其实是客户端的代理，帮助客户端访问其无法访问的服务器资源。反向代理则是服务器的代理，帮助服务器做负载均衡，安全防护等。
   	2、正向代理一般是客户端架设的，比如在自己的机器上安装一个代理软件。而反向代理一般是服务器架设的，
   	 比如在自己的机器集群中部署一个反向代理服务器。
   	3、正向代理中，服务器不知道真正的客户端到底是谁，以为访问自己的就是真实的客户端。而在反向代理中，客户端不知道真正的服务器是谁，以为自己访问的就是真实的服务端。
   	4、正向代理和反向代理的作用和目的不同。正向代理主要是用来解决访问限制问题。而反向代理则是提供负载均衡、安全防护等作用。二者均能提高访问速度。

   3、负载均衡
   	客户端发送的、Nginx反向代理服务器接收到的请求数量，就是我们说的 负载量。
    请求数量按照一定的规则进行分发到不同的服务器处理的规则，就是一种 均衡规则。

    所以~将服务器接收到的请求按照规则分发的过程，称为负载均衡。

    软件负载均衡是利用现有的技术结合主机硬件实现的一种消息队列分发机制。
    概念：Load balancing，即负载均衡，是一种计算机技术，用来在多个计算机（计算机集群）、网络连接、CPU、磁盘驱动器或其他资源中分配负载，以达到最优化资源使用、最大化吞吐率、最小化响应时间、同时避免过载的目的。

    负载均衡（Load Balance），意思是将负载（工作任务，访问请求）进行平衡、分摊到多个操作单元（服务器，组件）上进行执行。是解决高性能，单点故障（高可用），扩展性（水平伸缩）的终极解决方案。

    1、负载均衡核心：
     负载均衡服务器在决定将请求转发到具体哪台真实服务器的时候，是通过负载均衡算法来实现的。负载均衡算法，是一个负载均衡服务器的核心。

 	2、负载均衡算法：-------------------------------------------------待研究
 	 负载均衡算法可以分为两类：静态负载均衡算法和动态负载均衡算法。

 	 静态负载均衡算法包括：轮询，比率，优先权

	 动态负载均衡算法包括: 最少连接数,最快响应速度，观察方法，预测法，动态性能分配，动态服务器补充，服务质量，服务类型，规则模式。

    3、总结：负载均衡的本质---将请求或者说流量，以期望的规则分摊到多个操作单元上进行执行。
    通过它可以实现横向扩展（scale out），将冗余的作用发挥为「高可用」。另外，还可以物尽其用，提升资源使用率。




二、JSON解析器。
  
  参考：实现一个JSON解析器，有那么难吗？：https://mp.weixin.qq.com/s?__biz=MzAxNjM2MTk0Ng==&mid=2247486453&idx=1&sn=88e02e94bf56435d5343e148a9c5e5ba&chksm=9bf4bb40ac8332566caaa5775cb1e8a7f123a22d3303c66eb14f7f59563d3bba33f6778d2b8b&scene=0&xtrack=1#rd

  JSON介绍 在js中的使用：https://www.cnblogs.com/kuikui/p/3176806.html

  JSON(JavaScript Object Notation, JS 对象简谱) 是一种轻量级的数据交换格式。易于人阅读和编写。同时也易于机器解析和生成。采用完全独立于语言的文本格式，但是也使用了类似于C语言家族的习惯（包括C, C++, C#, Java, JavaScript, Perl, Python等）。这些特性使JSON成为理想的数据交换语言。

  1、JSON的两种结构
  	（1）对象
  		“名称/值”对的集合不同的语言中，它被理解为对象（object），纪录（record），结构（struct），字典（dictionary），哈希表（hash table），有键列表（keyed list），或者关联数组 （associative array）。

		对象是一个无序的“‘名称/值’对”集合。一个对象以“{”（左括号）开始，“}”（右括号）结束。每个“名称”后跟一个“:”（冒号）；“‘名称/值’ 对”之间使用“,”（逗号）分隔。

		例如：{"姓名": "张三", "年龄": "18"}

	（2）数组
		值的有序列表（An ordered list of values）。在大部分语言中，它被理解为数组（array）。

		数组是值（value）的有序集合。一个数组以“[”（左中括号）开始，“]”（右中括号）结束。值之间使用“,”（逗号）分隔。

		值（value）可以是双引号括起来的字符串（string）、数值(number)、true、false、 null、对象（object）或者数组（array）。这些结构可以嵌套。

		例如：
		[
    		{ 
   			 "姓名": "张三",          
    		 "年龄":"18"    
   			 },

   			{        
    		"姓名": "里斯",          
    		"年龄":"19"   

   			 }
		]

三、理解分布式、高并发与多线程

参考：你分得清分布式、高并发与多线程吗？：https://mp.weixin.qq.com/s/rCQRarmVrSKl4k1NsRXU-w
	
	1、什么是分布式？
		是为了解决单个物理服务器容量和性能瓶颈问题而采用的优化手段。

	从理念上讲，分布式的实现有两种形式：
		水平扩展：当一台机器扛不住流量时，就通过添加机器的方式，将流量平分到所有服务器上，所有机器都可以提供相当的服务；

		垂直拆分：前端有多种查询需求时，一台机器扛不住，可以将不同的需求分发到不同的机器上，比如A机器处理余票查询的请求，
		B机器处理支付的请求。

	2、什么是高并发？
		相对于分布式来讲，高并发在解决的问题上会集中一些，其反应的是同时有多少量：比如在线直播服务，同时有上万人观看。

	高并发可以通过分布式技术去解决，将并发流量分不到不同的物理服务器上。但除此之外，还可以有很多其他优化手段：比如使用缓存系统，
	将所有的，静态内容放到CDN等；还可以使用多线程技术将一台服务器的服务能力最大化。

	3、什么是多线程？
		多线程是指从软件或者硬件上实现多个线程并发执行的技术，它更多的是解决CPU调度多个进程的问题，
		从而让这些进程看上去是同时执行（实际是交替运行的）。
总结：
	●分布式是从物理资源的角度去将不同的机器组成一个整体对外服务，技术范围非常管且难度非常大，有了这个基础，高并发、高吞吐等系统很容易构建；
	● 高并发是从业务角度去描述系统的能力，实现高并发的手段可以采用分布式，也可以采用诸如缓存、CDN等，当然也包括多线程；
	● 多线程则聚焦于如何使用编程语言将CPU调度能力最大化。


-----------------------------2019-03-05--------------------------------

一、SQL中IN和EXISTS用法的区别。
参考：https://www.cnblogs.com/clarke157/p/7912871.html

二、Oracle 笔记---基本的Sql编写注意事项
http://www.blogjava.net/kiant/articles/234781.html

-----------------------------2019-03-12--------------------------------
一、Java高并发，如何解决，什么方式解决？
https://www.cnblogs.com/lr393993507/p/5909804.html
1、同步和异步？
	同步就是一件事一件事的做。
	异步就是，做一件事情，不影响做其他事情。
2、如何处理并发和同步？
	处理并发和同同步问题主要是通过锁机制，一种是代码层次上的，
	如java中的同步锁，典型的就是同步关键字synchronized，
	另外一种是数据库层次上的，比较典型的就是悲观锁和乐观锁。
3、常见并发同步案例分析

案例一:订票系统案例，某航班只有一张机票，假定有1w个人打开你的网站来订票，问你如何解决并发问题(可扩展到任何高并发网站要考虑的并发读写问题)

问题，1w个人来访问，票没出去前要保证大家都能看到有票，不可能一个人在看到票的时候别人就不能看了。到底谁能抢到，那得看这个人的“运气”（网络快慢等）其次考虑的问题，并发，1w个人同时点击购买，到底谁能成交？总共只有一张票。

首先我们容易想到和并发相关的几个方案 ：

锁同步同步更多指的是应用程序的层面，多个线程进来，只能一个一个的访问，java中指的是syncrinized关键字。锁也有2个层面，一个是java中谈到的对象锁，用于线程同步；另外一个层面是数据库的锁；如果是分布式的系统，显然只能利用数据库端的锁来实现。假定我们采用了同步机制或者数据库物理锁机制，如何保证1w个人还能同时看到有票，显然会牺牲性能，在高并发网站中是不可取的。

4、常见的提高高并发下访问的效率的手段

首先要了解高并发的的瓶颈在哪里？

可能是服务器网络带宽不够

可能web线程连接数不够

可能数据库连接查询上不去。

根据不同的情况，解决思路也不同。

像第一种情况可以增加网络带宽，DNS域名解析分发多台服务器。

负载均衡，前置代理服务器nginx、apache等等

数据库查询优化，读写分离，分表等等


三、JAVA中this用法小结
https://www.cnblogs.com/jpfss/p/8625196.html

-----------------------------2019-03-13--------------------------------

   1、DUAL
-----------------------------2019-05-09--------------------------------
   1、LISTAGG WITHIN GROUP(ORDER BY 。。) 列变行

-----------------------------2019-06-12--------------------------------
1、Ajax
 https://www.cnblogs.com/tylerdonet/p/3520862.html
 https://blog.csdn.net/weixin_39194176/article/details/80933777

2、request、response
https://www.cnblogs.com/MrzhangKk/p/5334259.html

3、Kotlin 教程
https://www.runoob.com/kotlin/kotlin-tutorial.html

4、Java多线程
https://blog.csdn.net/wanliguodu/article/details/81005560

-----------------------------2019-07-08--------------------------------
1、List分组
  1) Map<String, List<T>> map = new HashMap<>();
  Iterator iterator = refundGroupBank.map().iterator();
  while (iterator.hasNext()) {...}
  
  2)Collectors.groupingBy
  Map<String, List<Map<String, Object>>> glist = mapList.stream().collect(Collectors.groupingBy(e -> e.get("XXX").toString() + e.get("BBB")));
  
2、Spring IOC
	控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。
	在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）。
https://www.cnblogs.com/wang-meng/p/5597490.html

-----------------------------2019-07-15--------------------------------
1、事务（Transactional）
    默认情况下，只有来自外部的方法调用才会被AOP代理捕获，
也就是，类内部方法调用本类内部的其他方法并不会引起事务行为，
即使被调用方法使用@Transactional注解进行修饰。
status.isNewTransaction()如果是新的事务，才会提交！！

2、死锁
--查看锁表sql
select sess.sid, 
    sess.serial#, 
    lo.oracle_username, 
    lo.os_user_name, 
    ao.object_name, 
    lo.locked_mode,
    logon_time
    from v$locked_object lo, 
    dba_objects ao, 
    v$session sess 
where ao.object_id = lo.object_id and lo.session_id = sess.sid;

--杀掉进程
alter system kill session '7927, 5231';

oracle中“ORA-00060: 等待资源时检测到死锁” 或存储过程编译卡死 解决方法：
https://www.cnblogs.com/xielong/p/10716640.html

-----------------------------2019-07-31--------------------------------
https://mp.weixin.qq.com/s/dlVXW6aW4wLcLpey9NxPig
https://mp.weixin.qq.com/s/9LnAcITnQM7O8A3ziLqZDA
https://mp.weixin.qq.com/s?__biz=MjM5NzMyMjAwMA==&mid=2651482688&idx=1&sn=e2b51a4597c461ba1a2eb2c715733c96&chksm=bd25063f8a528f2974540cc9461bd2d45c6546029a612fb864b5e5bf6a0ad0e97db812ba5dd5&scene=0#rd
https://mp.weixin.qq.com/s/NGwrWwazycqtbFOZSV1Arw
https://mp.weixin.qq.com/s/g41txKIWYmEUWwR_xCF71Q
https://mp.weixin.qq.com/s/M8RNnmHS-N8pZC9TbY2IJw
https://mp.weixin.qq.com/s/dYzClrr65L_p_deFDvCwVQ
https://mp.weixin.qq.com/s/S9jiO_e-_CKRgNnzAU5Z0Q
https://mp.weixin.qq.com/s/evYERBgR5vV_hir2r_N85Q


多线程：
https://mp.weixin.qq.com/s/WNrcvlfJr8sYF9uuQFFHvQ
https://mp.weixin.qq.com/s/KfIQYdvH0lxJYIxMILq9fQ
https://mp.weixin.qq.com/s/dYzClrr65L_p_deFDvCwVQ
