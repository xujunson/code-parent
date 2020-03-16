2、ThreadLocal
2.1两大使用场景————ThreadLocal的用途
典型场景1：每个线程需要一个独享的对象(通常是工具类，典型需要使用的类有SimpleDateFormat和Random)；
 1)、每个Thread内有自己的实例副本，各个线程之间的实例是不共享的
 2)、比喻：教材只有一本，一起做笔记有线程安全问题，复印后没问题
 3)、ThreadLocal进化之路 ThreadLocalNormalUsage00——ThreadLocalNormalUsage04
  a)、2个线程分别用自己的SimpleDateFormat，这没问题
  b)、后来延伸出10个，那就有10个线程和10个SimpleDateFormat，这虽然写法不优雅（应该复用对象），但勉强可以接受
  c)、但是当需求变成了1000个，那么必然要用线程池（否则消耗内存太多了）
  d)、所有的线程都共用同一个simpleDateFormat对象
  e)、出现的重复日期问题，可以用synchronized关键字解决————问题是效率就变低了————解决：使用ThreadLocal
  最终版：ThreadLocalNormalUsage05.java

2.2 典型场景2：每个线程内需要保存全局变量(例如在拦截器中获取用户信息)，可以让不同方法直接使用，避免参数传递的麻烦；

2.2.1 实例：当前用户信息需要被线程内所有方法共享
一个比较繁琐的解决方案是把user作为参数层层传递，从service-1()传到service-2()，再从service-2()传到service-3()，以此类推，
但是这样做会导致代码冗余且不易维护；
目标：每个线程内需要保存全局变量，可以让不同方法直接使用，避免参数传递的麻烦；

当多线程同时工作时，我们需要保证线程安全，可以用synchronized，也可以用ConcurrentHashMap，但无论用什么，都会对性能有所影响。
更好的办法是使用ThreadLocal，这样无需synchronized，可以在不影响性能的情况下，也无需层层传递参数，就可以达到保存当前线程对应的用户信息的目的；

在线程声明周期内，都通过这个静态ThreadLocal实例的get()方法取得自己set过的那个对象，避免了将这个对象（例如user对象）作为参数传递的麻烦；

2.2.2 ThreadLocal方法
1)、它所强调的是同一个请求内（同一个线程内）不同方法间的共享；
2)、不需重写initialValue()方法，但是必须手动调用set()方法；

2.3 总结
2.3.1 ThreadLocal的两个作用
1)、让某个需要用到的对象在线程间隔离(每个线程都有自己的独立的对象)；
2)、可以在任何方法中都可以轻松获取到该对象

根据共享对象的生成时机不同，选择initialValue或set来保存对象；
2.3.2 场景一：initialValue
在ThreadLocal第一次get的时候把对象给初始化出来，对象的初始化时机可以由我们控制

2.3.3 场景二：set
如果需要保存到ThreadLocal里的对象生成时机不由我们随意控制，例如拦截器生成的用户信息，用ThreadLocal.set直接放到我们的ThreadLocal中去，以便后续使用。

2.3.4 使用ThreadLocal带来的好处
1)、达到线程安全
2)、不需要加锁，提高执行效率
3)、更高效地利用内存，节省开销：相比于每个任务都新建一个SimpleDateFormat，显然用ThreadLocal可以节省内存和开销；
4)、免去传参的繁琐：无论是场景一的工具类，还是场景二的用户名，都可以在任何地方直接通过ThreadLocal拿到，再也不需要每次都穿同样的参数。
ThreadLocal使得代码耦合度更低，更优雅。

2.4 ThreadLocal原理
搞清楚Thread、ThreadLocal以及ThreadLocalMap三者之间的关系
1)、每个Thread对象中都持有一个ThreadLocalMap成员变量，1对1关系；
2)、每个ThreadLocalMap存储很多个ThreadLocal，1对多；因为一个线程可能拥有多个ThreadLocal对象。

2.5 ThreadLocal主要方法介绍
2.5.1 T initialValue()：初始化
1)、该方法会返回当前线程对应的"初始值"，这是一个延迟加载的方法，只有在调用get()的时候，才会触发；
2)、当线程第一次使用get方法访问变量时，将调用此方法，除非先前调用了set方法，在这种情况下，不会为线程调用本initialValue方法；
3)、通常，每个线程最多调用一次此方法，但如果已经调用了remove()后，在调用get()，则可以再次调用此方法；
4)、如果不重写本方法，这个方法会返回null。一般使用匿名内部类的方法来重写initialValue()方法，以便在后续使用中可以初始化副本对象。

2.5.2 void set(T t)：为这个线程设置一个新值

2.5.3 T get()：得到这个线程对应的value。如果是首次调用get()，则会调用initialize来得到这个值
1)、get方法是先取出当前线程的ThreadLocalMap，然后调用map.getEntry方法，把本ThreadLocal的引用作为参数传入，取出map中属于本ThreadLocal的value；
2)、注意，这个map已经map中的key和value都是保存在线程中的，而不是保存在ThreadLocal中；

2.5.4 void remove()：删除对应这个线程的值

2.6 ThreadLocalMap 类
2.6.1 ThreadLocalMap类，也就是Thread.threadLocals

2.6.2 ThreadLocalMap类是每个线程Thread类里面的变量，里面最重要的是一个键值对数组 Entry[] table，可以认为是一个map，键值对：
键：这个ThreadLocal
值：实际需要的成员变量，比如user或者simpleDateFormat对象

2.7 ThreadLocal注意点
2.7.1 内存泄露
1)、什么是内存泄露(OOM)：某个对象不再有用，但是占用的内存却不能被回收；
弱引用：特点是，如果这个对象只被弱引用关联（没有任何强引用关联），那么这个对象就可以被回收；
强引用：通常写的赋值就是强引用；

2)、value的泄露
ThreadLocalMap的每个Entry都是一个对key的弱引用，同时每个Entry都包含了一个对value的强引用；

正常情况下，当线程终止，保存在ThreadLocal里的value会被垃圾回收，因为没有任何强引用了；

但是，如果线程不终止（比如线程需要保持很久），那么key对应的value就不能被回收，
因为有以下的调用链：Thread -> ThreadLocalMap -> Entry(key 为null) -> Value
因为value和Thread之间还存在这个强引用链路，所以导致value无法回收，就可能会出现OOM。

JDK已经考虑到了这个问题，所以在set、remove、rehash方法中会扫描key为null的Entry，
并把对应的value设置为null，这样value对象就可以被回收。

但是如果一个ThreadLocal不被使用，那么实际上set、remove、rehash方法也不会被调用，如果同时线程又不停止，那么调用链就一直存在，
那么就导致了value的内存泄露。

3)、如何避免内存泄露（阿里规约）
调用remove方法，就会删除对应的Entry对象，可以避免内存泄露，所以使用完ThreadLocal之后，应该调用remove方法；

2.7.2 空指针异常 ：ThreadLocalNPE.java
在进行get之前，必须先set，否则可能会报空指针异常？
并不是，是由于装箱拆箱导致的。

2.7.3 共享对象
如果在每个线程中ThreadLocal.set()进去的东西本来就是多线程共享的同一个对象，比如static对象，
那么多个线程的ThreadLocal.get()取得的还是这个共享对象本身，还是有并发访问问题。

2.7.4 如果可以不使用ThreadLocal就解决问题，那么就不要强行使用
比如说，我们在任务数很少的时候，在局部变量中新建对象就可以解决问题，那么就不需要使用到ThreadLocal；

2.7.5 优先使用框架的支持，而不是自己创造
例如在Spring中，如果可以使用RequestContextHolder，那么就不需要自己维护ThreadLocal，因为自己可能会忘记调用remove()方法等，造成内存泄露。

2.8 实际应用场景————在Spring中的实例分析
1)、DateTimeContextHolder类，看到里面用了ThreadLocal；
2)、每次HTTP请求都对应一个线程，线程之间相互隔离，这就是ThreadLocal的典型应用场景；
