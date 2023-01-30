package com.atu.design.pattern.template.general;

import java.util.Random;

/**
 * @author: Tom
 * @create: 2023-01-30 16:48
 * @Description: 模板方法设计模式的抽象类
 * 去银行的营业厅办理业务需要以下步骤：1.取号、2.办业务、3.评价。
 * 三个步骤中取号和评价都是固定的流程，每个人要做的事儿都是一样的。
 * 但是办业务这个步骤根据每个人要办的事情不同所以需要有不同的实现。
 * 我们可以将整个办业务这件事儿封装成一个抽象类
 */
public abstract class AbstractBusinessHandler {
    /**
     * 模板方法
     */
    public final void execute() {
        if(!isVip()){//如果顾客是vip，则不用排队
            getRowNumber();
        }
        handle();
        judge();
    }

    public abstract boolean isVip(); //抽象的钩子方法，由子类实现

    /**
     * 取号
     *
     * @return
     */
    private void getRowNumber() {
        System.out.println("rowNumber-00" + new Random().nextInt());
    }

    /**
     * 办理业务
     */
    public abstract void handle(); //抽象的办理业务方法，由子类实现

    /**
     * 评价
     */
    private void judge() {
        System.out.println("give a praised");
    }
}
