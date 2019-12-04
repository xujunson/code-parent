package com.atu.senior.Reflect.simpledemo3;

/**
 * @Author: Tom
 * @Date: 2019/7/16 11:39
 * @Description:
 */
public class SuperMan extends Person implements ActionInterface {
    private boolean BlueBriefs;

    public void fly() {
        System.out.println("超人会飞耶～～");
    }

    public boolean isBlueBriefs() {
        return BlueBriefs;
    }

    public void setBlueBriefs(boolean blueBriefs) {
        BlueBriefs = blueBriefs;
    }

    @Override
    public void walk(int m) {
        // TODO Auto-generated method stub
        System.out.println("超人会走耶～～走了" + m + "米就走不动了！");
    }
}
