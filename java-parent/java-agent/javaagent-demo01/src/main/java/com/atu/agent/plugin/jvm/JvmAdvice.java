package com.atu.agent.plugin.jvm;

import com.atu.util.JvmStack;
import net.bytebuddy.asm.Advice;

/**
 * @author: Tom
 * @date: 2023/4/15 13:49
 * @description: TODO
 **/
public class JvmAdvice {

    @Advice.OnMethodExit()
    public static void exit() {
        JvmStack.printMemoryInfo();
        JvmStack.printGCInfo();
    }
}
