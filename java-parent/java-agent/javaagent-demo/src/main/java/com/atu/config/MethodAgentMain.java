package com.atu.config;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * @author: Tom
 * @date: 2023/4/9 17:32
 * @description: TODO
 **/


public class MethodAgentMain {

    /**
     * 被转换的类
     */
    public static final String TRANSFORM_CLASS = "com.atu.AgentTest";

    /**
     * 静态加载。Java agent指定的premain方法，会在main方法之前被调用
     */
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("premain start!");
        addTransformer(instrumentation);
        System.out.println("premain end!");
    }

    /**
     * 动态加载。Java agent指定的premain方法，会在main方法之前被调用
     */
    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("agentmain start!");
        addTransformer(instrumentation);
        Class<?>[] classes = instrumentation.getAllLoadedClasses();
        if (classes != null) {
            for (Class<?> c : classes) {
                if (c.isInterface() || c.isAnnotation() || c.isArray() || c.isEnum()) {
                    continue;
                }
                if (c.getName().equals(TRANSFORM_CLASS)) {
                    try {
                        System.out.println("retransformClasses start, class: " + c.getName());
                        /*
                         * retransformClasses()对JVM已经加载的类重新触发类加载。使用的就是上面注册的Transformer。
                         * retransformClasses()可以修改方法体，但是不能变更方法签名、增加和删除方法/类的成员属性
                         */
                        instrumentation.retransformClasses(c);
                        System.out.println("retransformClasses end, class: " + c.getName());
                    } catch (UnmodifiableClassException e) {
                        System.out.println("retransformClasses error, class: " + c.getName() + ", ex:" + e);
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("agentmain end!");
    }

    private static void addTransformer(Instrumentation instrumentation) {
        /* Instrumentation提供的addTransformer方法，在类加载时会回调ClassFileTransformer接口 */
        instrumentation.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader l, String className, Class<?> c, ProtectionDomain pd, byte[] b) {
                try {
                    className = className.replace("/", ".");
                    if (className.equals(TRANSFORM_CLASS)) {
                        final ClassPool classPool = ClassPool.getDefault();
                        final CtClass clazz = classPool.get(TRANSFORM_CLASS);

                        for (CtMethod method : clazz.getMethods()) {
                            /*
                             * Modifier.isNative(methods[i].getModifiers())过滤本地方法,否则会报
                             * javassist.CannotCompileException: no method body  at javassist.CtBehavior.addLocalVariable()
                             * 报错原因如下
                             * 来自Stack Overflow网友解答
                             * Native methods cannot be instrumented because they have no bytecodes.
                             * However if native method prefix is supported ( Transformer.isNativeMethodPrefixSupported() )
                             * then you can use Transformer.setNativeMethodPrefix() to wrap a native method call inside a non-native call
                             * which can then be instrumented
                             */
                            if (Modifier.isNative(method.getModifiers())) {
                                continue;
                            }

                            method.insertBefore("System.out.println(\"" + clazz.getSimpleName() + "."
                                    + method.getName() + " start.\");");
                            method.insertAfter("System.out.println(\"" + clazz.getSimpleName() + "."
                                    + method.getName() + " end.\");", false);
                        }

                        return clazz.toBytecode();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }, true);
    }
}
