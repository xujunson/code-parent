5、CAS原理
5.1、什么是CAS
5.1.1、什么是CAS：先比较后操作
运用的场合一定是并发的，是一种思想，一种算法，用来实现线程安全的算法；同时也是一种CPU指令；

思路：我认为V的值是A，如果是的话那我就把它改成B，如果不是A(说明被别人修改过了)，那我就不修改了，避免多人同时修改导致出错。

CAS有三个操作数：内存值V，预期值A，要修改的值B，当且仅当预期值A和内存值V相同时，才将内存值修改为B，否则什么都不做。
最后返回现在的V值。

CAS实际上最终是利用了CPU的特殊指令了，因为，这些指令由CPU保证了原子性；其次，一个指令就可以做好几件事情，
比如说：先比较，在更新。并且由于CPU保证了原子性，所以不会出现线程安全问题。

5.1.2、CAS的等价代码
SimulatedCAS.java

5.2、案例演示
两个线程竞争，其中一个落败

5.3、应用场景
乐观锁：底层使用CAS实现
数据库：利用版本号形式修改，而不需要锁着这个表
并发容器：ConcurrentHashMap
原子类

5.3.1、分析在Java中是如何利用CAS实现原子操作的？
1)、AtomicInteger加载Unsafe工具，用来直接操作内存数据；
2)、用Unsafe来实现底层操作；
3)、用volatile修饰value字段，保证可见性；
4)、Unsafe类 Unsafe是CAS的核心类。Java无法直接访问底层操作系统，而是通过本地（native）方法来访问。不过尽管如此，JVM还是开了一个后门，
JDK中有一个类Unsafe，它提供了硬件级别的原子操作。  

5.4、以AtomicInteger为例，分析在Java中是如何利用CAS实现原子操作的？
源码分析过程 让我们来看看AtomicInteger是如何通过CAS实现并发下的累加操作的，以AtomicInteger的getAndAdd方法为突破口。  
1 getAndAdd方法  
public final int getAndAdd(int delta) {
    return unsafe.getAndAddInt(this, valueOffset, delta);
}
可以看出，这里使用了Unsafe这个类，这里需要简要介绍一下Unsafe类：  

2 Unsafe类 Unsafe是CAS的核心类。
Java无法直接访问底层操作系统，而是通过本地（native）方法来访问。不过尽管如此，JVM还是开了一个后门，JDK中有一个类Unsafe，它提供了硬件级别的原子操作。  

3 AtomicInteger加载Unsafe工具，用来直接操作内存数据  
public class AtomicInteger extends Number implements java.io.Serializable {
    // setup to use Unsafe.compareAndSwapInt for updates     
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;
    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
    private volatile int value;
    public final int get() {
        return value;
    }
} 

在AtomicInteger数据定义的部分，我们还获取了unsafe实例，并且定义了valueOffset。
再看到static块，懂类加载过程的都知道，static块的加载发生于类加载的时候，是最先初始化的，
这时候我们调用unsafe的objectFieldOffset从Atomic类文件中获取value的偏移量，那么valueOffset其实就是记录value的偏移量的。  
valueOffset表示的是变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的原值的，这样我们就能通过unsafe来实现CAS了。
value是用volatile修饰的，保证了多线程之间看到的value值是同一份。  
4 接下来继续看Unsafe的getAndAddInt方法的实现  
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
       var5 = this.getIntVolatile(var1, var2);
    } while (!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    return var5;
} 
我们看var5获取的是什么，通过调用unsafe的getIntVolatile(var1, var2)，这是个native方法，其实就是获取var1中，var2偏移量处的值。
var1就是AtomicInteger，var2就是我们前面提到的valueOffset,这样我们就从内存里获取到现在valueOffset处的值了。 
现在重点来了，compareAndSwapInt（var1, var2, var5, var5 + var4）其实换成compareAndSwapInt（obj, offset, expect, update）比较清楚，
意思就是如果obj内的value和expect相等，就证明没有其他线程改变过这个变量，那么就更新它为update，如果这一步的CAS没有成功，那就采用自旋的方式继续进行CAS操作。  
Unsafe的getAndAddInt方法分析：自旋 + CAS，在这个过程中，通过compareAndSwapInt比较并更新value值，如果更新失败，重新获取，然后再次更新，直到更新成功。

5.5、缺点
1)、ABA问题：在compareAndSwap的时候，先检查；只是检查是不是和我这个值相等；而并不是检查再次期间释放被修改过；
比如：我拿到这个值等于5，然后我去计算；在我计算的过程中，如果有一个线程把5改成了7，又有另一个线程把7改回5；
而我此时再去判断这个值还是5，这样我就会认为此时的值，没有被修改过，这样就可能会导致一些问题发生；

解决：和数据库一样，在用乐观锁的时候添加一个版本号，这样一来，直接对比版本号就更靠谱一些；

2)、自旋时间过长：有一些操作是死循环的，那么在这种情况下是一个很长时间的自旋；那如果在此期间竞争非常激烈，
或者锁一直都拿不到，那这个自旋是很消耗CPU的，对性能是一个很大的消耗。