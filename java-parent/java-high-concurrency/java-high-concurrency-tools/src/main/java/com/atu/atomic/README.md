4、atomic包【一刻也不能分割】
4.1、什么是原子类，有什么作用？
特点：不可分割，一个操作是不可中断的，即便是多线程的情况下也可以保证。
java.util.concurrent.atomic

作用：原子类的作用和锁类似，是为了保证并发情况下的线程安全。
不过原子类相比于锁，有一定的优势：
1)、锁的粒度更细：原子变量可以把竞争范围缩小到变量级别，这是我们可以获得的最细粒度的情况了，通常锁的粒度都要大于原子变量的粒度；
2)、效率更高：通常，使用原子类的效率会比使用锁的效率更高，除了高度竞争的情况。

4.2、6类原子类纵览
![binaryTree](../img/6类原子类纵览.png "binaryTree")

4.3、Atomic*基本类型原子类，以AtomicInteger为例
4.3.1 AtomicInteger：原子类
1)、get(); //获取当前的值
2)、getAndSet(int newValue); //获取当前的值，并设置新的值
3)、getAndIncrement(); //获取当前的值，并自增
4)、getAndDecrement(); //获取当前的值，并自减
5)、getAndAdd(int delta); //获取当前的值，并加上预期值
6)、compareAndSet(int expect, int update); //如果输入的值等于预期值，则以原子方式将该值设置为输入值(update)

AtomicIntegerDemo1.java

如果说在做账务系统或者财务管理的时候，经常会有并发的修改，这种修改需要保证原子性，可以利用AtomicIntegerArray来保障线程安全。

4.3.2 AtomicLong：原子类
4.3.3 AtomicBoolean：布尔型原子类

4.4、Atomic*Array数组类型原子类
AtomicArrayDemo.java

4.5、Atomic*Reference引用类型原子类
4.5.1、AtomicReference：AtomicReference类的作用，课AtomicInteger并没有本质区别。AtomicInteger可以让一个整数保证原子性，而AtomicReference可以让一个对象保证原子性，
当然，AtomicReference的功能明显比AtomicInteger强，因为一个对象里可以包含很多属性。用法和AtomicInteger类似。
SpinLock.java

4.6、把普通变量升级为原子类，用AtomicIntegerFieldUpdater升级原有变量
4.6.1、AtomicIntegerFieldUpdater对普通变量进行升级
适用场景：
1)、如果一个类不是我们编写的，我们没有权利去修改它，此时可以使用AtomicIntegerFieldUpdater;
2)、偶尔需要一个原子get-set操作；

4.6.2、AtomicIntegerFieldUpdater注意点
1)、所能修改的是具有一个可见范围的；
2)、不支持被static修饰的变量

4.7、Adder累加器
4.8、Accumulator累加器
