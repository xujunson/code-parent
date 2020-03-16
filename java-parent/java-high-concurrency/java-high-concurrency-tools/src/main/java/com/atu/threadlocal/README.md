2、ThreadLocal
两大使用场景————ThreadLocal的用途
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

典型场景2：每个线程内需要保存全局变量(例如在拦截器中获取用户信息)，可以让不同方法直接使用，避免参数传递的麻烦；