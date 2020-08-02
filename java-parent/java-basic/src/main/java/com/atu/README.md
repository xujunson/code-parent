<<剑指Java面试-Offer直通车>>

## 一.计算机网络面试核心
### 1.网络基础知识讲解
#### 1.1 OSI开放式互联参考模型
第一层 物理层：机械、电子、定时接口通信信道上的比特流传输。
解决两台物理机之间的通讯需求：机器A往机器B发送比特流，机器B收到这些比特流，这便是物理层要做的事;
物理层主要定义了物理设备的标准。如：网线的类型、光纤的接口类型、各种传输介质的传输速率等。
它的主要作用是传输比特流(比特流：0101-二进制数据),将它们转化为电流强弱来进行传输,
到达目的后在转化为0101的机器码——也就是我们常说的数模转换与模数转换。
这层的数据叫做比特,网卡就是工作在这层里面的。

第二层 数据链路层：物理寻址,同时将原始比特流转变为逻辑传输线路。
在传输比特流的过程中会产生错传、数据传输不完整的可能，因此数据链路层应运而生。

数据链路层定义了如何格式化数据以进行传输,以及如何让控制对物理介质的访问。
这层通常还提供错误检测和纠正以确保数据传输的可靠性。
本层将比特数据组成了帧。交换机工作在这层里面,对帧解码,并根据帧中包含的信息把数据发送到正确的接受收方。

随着网络节点的不断增加，点对点通讯的时候是需要经过多个节点的。那么如何找到目标节点，如何选择最佳路径便成了首要需求。
此时便有了网络层。

第三层 网络层：控制子网的运行，如逻辑编址、分组传输、路由选择。
主要功能是将网络地址翻译成对应的物理地址,并决定如何从发送方路由到接受方。
网络层通过综合考虑：发送优先权、网络拥塞程度、服务质量以及可选路由的花费来决定，
从一个网络中节点A到另一个网络中节点B的最佳路径。
由于网络层处理并智能指导数据传送路由器连接网络各段，所以路由器属于网络层。
此层的数据我们称之为数据包，本层我们需要关注的协议主要是TCP/IP协议里面的IP协议。
随着网络通信需求的进一步扩大，通信过程中需要发送大量的数据。
如海量文件传输的，可能需要很长时间，而网络在通信的过程中会中断好多次，
此时为了保证传输大量文件时的准确性，需要对发出的数据进行切分，切割为一个一个的段落(segment)进行发送。
那么其中一个段落丢失了怎么办，要不要重传？每个段落要按照顺序到达吗？这个便是传输层要考虑的问题了。

第四层 传输层：接受上一层的数据，在必要额度时候把数据进行分割，并将这些数据交给网络层，且保证这些数据段有效到达对端。
传输层解决了主机间的数据传输，数据域间的传输可以是不同网络的，并且传输层解决了传输质量的问题，该层称之为OSI模型中最重要的一层了。
传输协议同时进行流量控制或是基于接收方可接受数据的快慢程度规定适当的发送速率。
除此之外传输层按照网络能处理的最大尺寸将较长的数据包进行强制分隔，
例如以太网无法接收大于1500字节的数据包，发送方节点的传输层将数据分割成较小的数据片，
同时对每一数据片安排一序列号以便数据到达接收方节点的传输层时，能以正确的顺序重组，
该过程成为排序。
传输层中需要关注的协议有TCP/IP协议中的TCP协议和UDP协议。
现在我们已经保证给正确的计算机发送正确的封装之后的信息了，但是用户级别的体验好不好，
难道我每次去调用TCP去打包，然后调用IP协议去找路由，自己去发？
当然不行，所以我们需要建一个自动收发包，自动寻址的功能，于是发明了会话层。

第五层 会话层：不同机器上的用户之间建立及管理会话
会话层的作用就是建立和管理应用程序之间的通信。
现在我能保证应用程序自动收发包和寻址了。但我要用Linux给Windows收发包，
两个系统语法不一致，就像安装包一样，exe是不能在Linux上运行的。
于是就有了表示层。

第六层 表示层：信息的语法语义以及它们的关联，如加密解密、转换翻译、压缩解压缩
表示层帮我们解决不同系统之间的通信语法的问题。在表示层数据将按照网络能理解的方案进行格式化。
这种格式化也因所使用网络的不同而不同，此时虽然发送方知道自己发送的是什么东西，
转换成字节数组之后有多长，但接收方肯定不知道。

第七层 应用层
所以应用层的网络协议诞生了，它规定发送方和接收方必须使用一个固定长度的消息头，
消息头必须使用某种固定的组成。而且消息头里必须记录消息体的长度等一系列信息。
以方便接收方能够正确的解析发送方发送的数据。应用层旨在让你更方便的应用从网络中接收到的数据。
至于数据的传递没有该层你也可以直接在两台电脑间开杠，只不过传来传去就是一个1和0组成的字节数组。
该层需要重点关注的是与之相对应的TCP/IP协议中的http协议。

![binaryTree](../atu/img/OSI开放式互联参考模型.png "binaryTree")
OSI参考模型并不是一个标准，而是一个制定标准是所使用的概念型框架。
事实的标准是TCP/IP四层架构参考模型。
![binaryTree](../atu/img/TCP_IP.png "binaryTree")

### 2.TCP的三次握手
1.IP协议：IP协议是无连接的通信协议，它不会占用两个正在通信的计算机的通信线路，这样IP
就降低了对网络线路的需求。每条线可以同时满足许多不同计算机之间的通信需要，
通过IP，消息或者其他数据会被分割为较小的独立的包，并通过因特网在计算机之间传送，
IP负责将每个包路由至它的目的地，但IP协议没有做任何事情来确认数据包是否按顺序发送，
或者包是否被破坏，所以IP数据包是不可靠的，需要由它的上层协议来做控制。

2.传输控制协议TCP属于传输层的协议
TCP(Transmission Control Protocol)简介：
1).面向连接的、可靠的、基于字节流的传输层通信协议；
2).数据传输时，应用层向TCP发送数据流，然后TCP把数据流分隔成适当长度的报文段，
此后TCP把结果包传给IP层，由它通过网络将包传送给目标节点的TCP层；
3).TCP为了保证不丢失包就给每个包一个序号(Sequence Number)，同时序号也保证了传送到目标节点的
包的按序处理，然后接收端实体对已成功收到的包发送ACK-已成功确认，如果发送端实体在合理的往返时延未收到确认，
那么对应的数据包就被假设为已丢失并且将会对其进行重传；
4).TCP用一个校验和函数来检验数据在传输过程中是否有误，在发送和接收时都要计算校验和。

### 3.TCP三次握手
3.1 TCP报文头
![binaryTree](../atu/img/TCP报文头.png "binaryTree")
1)、Source Port: 源端口，占2个字节
2)、Destination Port: 目的端口 占2个字节
TCP不包含IP地址信息，TCP和UDP均会有源端口和目的端口。端口是属于传输层知识范畴的。
两个进程在计算机内部进行通信，可以由管道、内存共享、信号量、消息队列等方法进行通信。
而两个进程如果需要进行通信最基本的前提是唯一能够标识一个进程，通过这个标识找到对应进程。
在本地进程通信中，我们可以使用pid(进程标识符)来唯一标识一个进程，但pid只在本地唯一，
如果把两个进程放在不同的两台计算机，它们要进行通信的话pid就不够用了。
这样就需要另外一个手段——在传输层中使用协议端口号(protocal port number)，
IP层的ip地址可以唯一标识一个主机，而TCP协议和端口号可以唯一标识主机中的一个进程，
这样我们可以利用IP地址+协议+端口号去标识网络中的一个进程。
在一些场合把这个唯一标识的模式称为套接字(Socket)。虽然通讯的重点是应用进程，
但我们只要把要传送的报文加上目的主机的某一个合适的端口，剩下的工作就由TCP来完成。
3)、Sequence Number(序号)：4个字节，TCP连接中，传送的字节流中的每个字节都按顺序去编号，
例如一段报文的序号字段值就是107，而携带的数据共有100个字段，那么如果有下一个报文段的话，
其序号就应该是从207(107+100)开始。
4)、ACK确认号：4个字节 期望收到对方下一个报文的第一个数据字节的序号。
例如B收到了A发送过来的报文，其序列号字段是301，而数据长度是200字节，
这表明了B正确的收到了A发送的到序号500(301+200-1)为止的数据，因此B期望收到A的下一个数据序号是501，
于是B在发送给A的确认号段中会把ACK确认号置为501。
5)、Offset(数据偏移)：由于头部有可选字段，长度不固定，因此它指出TCP报文的数据距离TCP起始处有多远，
6)、Preserved：保留域
7)、TCP Flags：控制位，主要由8个标志位组成，每个标志位表示一个控制功能。
 a、URG：紧急指针标志，当它为1时，表示紧急指针有效；为0则忽略紧急指针
 b、ACK(*)：确认序号标志，为1时表示确认号有效，为0表示报文中不含确认信息忽略确认号字段；
 c、PSH：push标志，为1时表示是带有push标志的数据，指示接收方在接收该报文段以后应尽快
  将这个报文段交个应用程序而不是在缓冲区排队；
 d、RST：重置连接标志，用于重置由于主机崩溃，或其他原因而出现错误的连接，
  或者用于拒绝非法的报文段，和拒绝连接请求；
 e、SYN(*)：同步序号，用于建立连接过程，在连接请求中SYN=1和ACK=0表示该数据段没使用捎带的确认域，
  而连接应答捎带的确认即SYN=1和ACK=1;
 f、FIN(*)：finish标志，用于释放连接，为1时表示发送方已经没有数据发送了，即关闭本方数据流。

8)、window窗口：表示滑动窗口的大小，用来告知发送端接收端的缓冲大小，以此控制发送端发送数据的速率，
 从而达到流量控制
9)、Checksum检验和：既有校验，此校验和是对整个的TCP报文段，包括TCP头部和TCP数据以16为进行计算所得，
 由发送端计算和存储，并有接收端进行验证
10)、Urgent Pointer(紧急指针)：只有当TCP中的URG为1的时候才有效
11)、TCP Options：可选项，定义一些其他的可选参数

当应用程序希望通过TCP与另一个应用程序通信时，它会发送一个通信请求，这个请求必须被送到一个确切的地址，
在双方握手之后TCP将在两个应用之间建立全双通的通信，这个全双通的通信将占用两个计算机的通信线路，
直到它被对方或双方关闭为止。

3.2 TCP三次握手流程图
![binaryTree](../atu/img/TCP三次握手.png "binaryTree")
"握手"是为了建立连接，TCP三次握手的流程如下：
在 TCP/IP 协议中，TCP协议提供可靠的连接服务，采用三次握手建立一个连接。
1)、第一次握手：建立连接时，客户端发送SYN包[syn=j]到服务器，并进入SYN_SEND状态，等待服务器确认；
2)、第二次握手：服务器收到SYN包，必须确认客户的SYN(ack=j+1)，同时自己也发送一个SYN包(syn=k)，即SYN+ACK包，
 此时服务器进入SYN_RECV状态；
3)、第三次握手：客户端收到服务器的SYN+ACK包，向服务器发送确认包ACK(ack=k+1)，此包发送完毕，客户端和服务器进入ESTABLISHED状态，
 完成三次握手。

3.3 为什么需要三次握手才能建立起连接
 为了初始化Sequence Number的初始值。
 通信的双方要互相通知对方自己的初始化的Sequence Number(seq)，这个号要作为以后额度数据通信的序号，
 以保证应用层接收到的数据，不会英文网络上的传输问题而乱序，即TCP会用这个序号来拼接数据。因此，
 在服务器回发它的seq(即第二次握手之后)，还需要发送确认报文给服务器，告诉服务器说，客户端已经收到你的初始化的seq了。
3.4 首次握手的隐患——SYN超时
问题起因分析：
 1)、Server端收到Client的SYN，回复SYN-ACK的时候未收到ACK确认，此时的连接处于一个中间状态——未成功 也没有失败
 2)、Server不断重试直至超时，Linux默认等待63s才断开连接
针对SYN Flood的防护措施
 1)、SYN队列满后，通过tcp_syncookies参数回发SYN Cookie
 2)、若为正常连接则Client会回发SYN Cookie，直接建立连接
建立连接后，Client出现故障怎么办？
 保活机制
 1)、向对方发送保活探测报文，如果未收到响应则继续发送
 2)、直到尝试次数达到保活探测数仍未收到响应则中断连接

### 4.TCP四次挥手
"挥手"是为了终止TCP连接，TCP四次挥手的流程图如下：
![binaryTree](../atu/img/TCP四次挥手.png "binaryTree")
1)、第一次挥手：Client发送一个FIN，用来关闭Client到Server的数据传送，Client进入FIN_WAIT_1状态；
2)、第二次挥手：Server收到FIN包之后，会发送一个ACK给Client，确认序号为收到序号+1(与SYN相同，一个FIN占用一个序号)，Server进入CLOSE_WAIT状态；
3)、第三次挥手：Server发送一个FIN，用来关闭Server到Client的数据传送，Server进入LAST_ACK状态；
4)、第四次挥手：Client收到FIN后，Client进入TIME_WAIT状态，接着发送一个ACK给Server，确认序号为收到序号+1，Server进入CLOSED状态，完成四次挥手。

4.1、为什么会有TIME_WAIT状态？
原因：
1)、TIME_WAIT状态是为了确保有足够的时间让对方收到ACK，如果被动关闭的一方没有收到ACK就会触发被动端重发FIN包，
一来一去正好是2个MSL；
2)、有足够的时间让这个连接，不会跟后面的连接混合在一起，因为有些路由器会缓存IP数据包，如果连接被__，那么这些延迟被收到的包就有可能会跟新链接混在一起。

4.2、为什么需要四次握手才能断开连接？
因为TCP是全双工的，发送方和接收方都需要FIN报文和ACK报文，也就是说发送方和接收方各只需两次挥手即可，
只不过有一方是被动的，所以看上去就成了所谓额度四次挥手。

4.3、服务器出现大量CLOSE_WAIT状态的原因
客户端一直在请求，但是返回给客户端的信息是异常的，服务端压根就没有收到请求。
在对方关闭socket连接，我方忙于读或写，没有及时关闭连接。
解决：
1)、检查代码，特别是释放资源的代码;
2)、检查配置，特别是处理请求的现成配置。

### 5.TCP和UDP的区别
5.1、UDP简介：用户数据报协议(User Datagram Protocol)
![binaryTree](../atu/img/UDP报文结构.png "binaryTree")
源端口
目标端口
数据包长度
既有校验值
用户数据

5.2、UDP特点
1)、面向非连接，传输数据之前源端和终断不建立连接，当它想传送时就简单抓取来自应用程序的数据，
并尽可能快的把它扔到网络上，在发送端UDP传送数据的速度仅仅是受应用程序生成数据的速度、计算机的能力、和传输带宽的限制，
在接收端UDP把每个消息段放到队列中，应用程序每次从队列中读取一个消息段；
2)、由于传输数据不建立连接，因此也就不需要维护连接状态，因此一台服务器可同时向多个客户端传输相同的消息；
3)、UDP数据包报头只有8个字节，相对于TCP额外开销较小；
4)、吞吐量只受应用软件生成数据的速率、传输带宽、源端和终端主机性能的限制；
5)、UDP尽最大努力交付，不保证可靠交付，不需要维持负载的链接状态表；
6)、UDP是面向报文的，发送方的UDP对应用程序交互下来的报文再添加守护后，向下交互给IP层，既不拆分也不合并而是保留这些报文的边界，
因此应用程序需要选择合适的报文大小；

5.3、TCP和UDP的区别
TCP和UDP是OSI模型中的运输层中的协议。TCP提供可靠的通信传输，而UDP则常被用于让广播和细节控制交给应用的通信传输。
二者区别如下：
1)、TCP面向连接，UDP面向无连接。TCP有三次握手的连接过程，UDP适合消息的多拨发布，
从单个点向多个点传输信息；
2)、可靠性：TCP是比较可靠的，是利用握手、确认和重传机制提供了可靠性保证，而UDP则可能会丢失，
没有不知道到底有没有被接收；
3)、有序性：TCP利用序列号保证了消息包的顺序交互，到达可能无序，但TCP最终会排序，
而UDP是不具备有序性的；
4)、速度：TCP速度较慢吗，因为要创建连接保证消息的可靠性和有序性需要做额外的很多事情，UDP则更适合对速度比较敏感的应用，
比如：在线视频媒体、电视广播、多元在线游戏等；
5)、量级：TCP属于重量级的，UDP属于轻量级的体现在源数据的头大小