package com.atu.deadlock;

/**
 * @author: Tom
 * @date: 2020-03-13 17:33
 * @description: 改变一个哲学家拿叉子的顺序
 */
public class DiningPhilosophersFix {
    public static class Philosopher implements Runnable {

        private Object leftChopstick;
        private Object rightChopstick;

        public Philosopher(Object leftChopstick, Object rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        @Override
        public void run() {
            while (true) {
                //思考
                try {
                    doAction("Thinking");
                    //吃饭
                    //拿起左边筷子，拿起右边筷子 放下右边筷子 放下左边筷子

                    synchronized (leftChopstick) {
                        doAction("Picked up left chopstick");
                        synchronized (rightChopstick) {
                            doAction("Picked up right chopstick -eating");

                            doAction("Put down right chopstick");
                        }
                        doAction("Put down left chopstick");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void doAction(String action) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep((long) Math.random() * 10);

        }
    }

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] chopsticks = new Object[philosophers.length];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            Object leftChopstick = chopsticks[i];
            Object rightChopstick = chopsticks[(i + 1) % chopsticks.length];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightChopstick, leftChopstick);
            } else {
                philosophers[i] = new Philosopher(leftChopstick, rightChopstick);

            }
            new Thread(philosophers[i], "哲学家" + (i + 1) + "号").start();
        }
    }
}
