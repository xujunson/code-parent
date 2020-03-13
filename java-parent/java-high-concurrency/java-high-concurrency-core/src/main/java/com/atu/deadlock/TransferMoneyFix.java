package com.atu.deadlock;

/**
 * @author: Tom
 * @date: 2020-03-13 15:11
 * @description: 转账时遇到死锁
 * 一旦打开注释 便会发生死锁
 */
public class TransferMoneyFix implements Runnable {
    int flag = 1;

    static Account a = new Account(500);
    static Account b = new Account(500);

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        TransferMoneyFix r1 = new TransferMoneyFix();
        TransferMoneyFix r2 = new TransferMoneyFix();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a的余额" + a.balance);
        System.out.println("b的余额" + b.balance);
    }

    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        }

        if (flag == 0) {
            transferMoney(b, a, 200);
        }
    }

    public static void transferMoney(Account from, Account to, int amount) {
        class Helper {
            public void transfer() {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，转账失败。");
                }
                from.balance -= amount;
                to.balance = to.balance + amount;
                System.out.println("成功转账" + amount + "元");

            }
        }

        //利用hash排序————hash值只要对象不变，hash值就不会变
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else { //考虑hash碰撞 两个hash值相等(极少出现)
            synchronized (lock) {
                synchronized (to) {
                    synchronized (from) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    static class Account {
        public Account(int balance) {
            this.balance = balance;
        }

        int balance;

    }
}
