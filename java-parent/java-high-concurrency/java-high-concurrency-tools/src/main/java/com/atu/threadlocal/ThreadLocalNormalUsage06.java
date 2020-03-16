package com.atu.threadlocal;

/**
 * @author: Tom
 * @date: 2020-03-16 10:33
 * @description: 典型应用场景2：避免传递参数的麻烦
 */
public class ThreadLocalNormalUsage06 {

    public static void main(String[] args) {
        new Service1().process();
    }

}

//生成user对象
class Service1 {
    public void process() {
        User user = new User("超哥");
        //把user放到holder中
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到用户名：" + user.name);
        UserContextHolder.holder.remove();
        user = new User("张姐");
        UserContextHolder.holder.set(user);
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);

        UserContextHolder.holder.remove();//主动remove，避免OOM
    }
}


class UserContextHolder {
    //定义成静态的，可以在多个方法中直接取到它
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}
