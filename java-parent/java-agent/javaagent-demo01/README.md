https://github.com/fuzhengwei/CodeGuide/tree/master/docs/md/bytecode/agent

https://juejin.cn/post/7157684112122183693#heading-13
https://juejin.cn/post/7086026013498408973#heading-0
https://juejin.cn/post/7110113494447423518#heading-8

# Java agent
Java Agent 是在 JDK1.5 引入的，是一种可以动态修改 Java 字节码的技术。直译为 Java 代理，也常常被称为 Java 探针技术。

可以通过这种机制（Instrumentation）可以加载 class 文件之前修改方法的字节码（此时字节码尚未加入JVM），动态更改类方法实现虚拟机级别的AOP功能，
提供监控服务如：方法调用时长、可利用率、内存等。

这种方式一个典型的优势就是无代码侵入。

## Java Agent 功能介绍

Java Agent 主要有以下功能：
- Java Agent 能够在加载 Java 字节码之前拦截并对字节码进行修改;
- Java Agent 能够在 Jvm 运行期间修改已经加载的字节码，1.6以后提供；原理是通过Attach API动态地加载 Java agent。

Java Agent 的应用场景:
- IDE 的调试功能，例如 Eclipse、IntelliJ IDEA ；
- 热部署功能，例如 JRebel、XRebel、spring-loaded；
- 各种线上诊断工具，例如 Btrace、Greys，还有阿里的 Arthas；
- 各种性能分析工具，例如 Visual VM、JConsole 等；
- 全链路性能检测工具，例如 Skywalking、Pinpoint等；

## 如何让 java 代码执行时进入agent方法？

通过配置 `-javaagent:xxx.jar` 后，在 java 程序启动时候会执行 premain 方法。

## Instrumentation
Instrumentation是Java提供的JVM接口，该接口提供了一系列查看和操作Java类定义的方法，
例如修改类的字节码、向 classLoader 的 classpath 下加入jar文件等。
使得开发者可以通过Java语言来操作和监控JVM内部的一些状态，进而实现Java程序的监控分析，甚至实现一些特殊功能（如AOP、热部署）。

最常用的方法是addTransformer(ClassFileTransformer transformer)，这个方法可以在类加载时做拦截，对输入的类的字节码进行修改。

工作原理都是借助 JVMTI 来进行完成。

## JVMTI
JVMTI（JVM Tool Interface）是 Java 虚拟机对外提供的 Native 编程接口，通过 JVMTI ，外部进程可以获取到运行时JVM的诸多信息，比如线程、GC等。

## Attach API
Attach机制是JVM提供一种JVM进程间通信的能力，能让一个进程传命令给另外一个进程，并让它执行内部的一些操作。

日常很多工作都是通过 Attach API 实现的，示例：
 - JDK 自带的一些命令，如：jstack打印线程栈、jps列出Java进程、jmap做内存dump等功能
 - Arthas、Greys、btrace 等监控诊断产品，通过 attach 目标 JVM 进程发送指定命令，可以实现方法调用等方面的监控。

# javassist

> Javassist是一个开源的分析、编辑和创建Java字节码的类库。是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。
> 它已加入了开放源代码JBoss应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。

> 关于java字节码的处理，目前有很多工具，如bcel，asm。不过这些都需要直接跟虚拟机指令打交道。
> 如果你不想了解虚拟机指令，可以采用javassist。
> javassist是jboss的一个子项目，其主要的优点，在于简单，而且快速。直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。

## 使用javassist字节码增强的方式，来监控方法程序的执行耗时
```java
public class MyMonitorTransformer implements ClassFileTransformer {

    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("org.itstack.demo.test.ApiTest");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            String currentClassName = className.replaceAll("/", ".");
            if (!classNameSet.contains(currentClassName)) { // 提升classNameSet中含有的类
                return null;
            }
            System.out.println("transform: [" + currentClassName + "]");

            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;

    }


    private void enhanceMethod(CtBehavior method) throws Exception {
        if (method.isEmpty()) {
            return;
        }
        String methodName = method.getName();
        if ("main".equalsIgnoreCase(methodName)) {
            return;
        }

        final StringBuilder source = new StringBuilder();
        // 前置增强: 打入时间戳
        // 保留原有的代码处理逻辑
        source.append("{")
                .append("long start = System.nanoTime();\n") //前置增强: 打入时间戳
                .append("$_ = $proceed($$);\n")              //调用原有代码，类似于method();($$)表示所有的参数
                .append("System.out.print(\"method:[")
                .append(methodName).append("]\");").append("\n")
                .append("System.out.println(\" cost:[\" +(System.nanoTime() - start)+ \"ns]\");") // 后置增强，计算输出方法执行耗时
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        method.instrument(editor);
    }

}
```

# ByteBuddy
ByteBuddy是一个代码生成和操作的库，用于在 Java 应用程序运行时创建和修改 Java 类，而无需编译器的帮助。类似于cglib、javassist。

那么为什么选用该库呢？

javassist更偏向底层，比较难于使用并且在动态组合字符串以实现更复杂的逻辑时很容易出错，
而cglib现在维护的则相当慢了，基本处于无人维护的阶段了，而这些缺点ByteBuddy都没有，并且ByteBuddy性能相对来说在三者中是最优的。

### Java Agent 常见问题
1、字节码插桩

用通俗的话来讲，插桩就是将一段代码通过某种策略插入到另一段代码，或替换另一段代码。
这里的代码可以分为源码和字节码，而我们所说的插桩一般指字节码插桩。

[Java 动态代理和Java agent区别](http://lanixzcj.github.io/java-dynamic-proxy/#tocAnchor-1-1-2)
