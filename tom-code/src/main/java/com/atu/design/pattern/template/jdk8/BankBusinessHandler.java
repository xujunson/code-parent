package com.atu.design.pattern.template.jdk8;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: Tom
 * @create: 2023-01-30 17:01
 * @Description: Java 8以后的模板方法
 */
public class BankBusinessHandler {
    /**
     * 模板方法
     */

    protected void execute(Supplier<String> supplier, Consumer<BigDecimal> consumer) {

        String number = supplier.get();

        System.out.println(number);


        if (number.startsWith("vip")) {

            //Vip号分配到VIP柜台

            System.out.println("Assign To Vip Counter");

        } else if (number.startsWith("reservation")) {

            //预约号分配到专属客户经理

            System.out.println("Assign To Exclusive Customer Manager");

        } else {

            //默认分配到普通柜台

            System.out.println("Assign To Usual Manager");

        }

        consumer.accept(null);

        judge();

    }

    private void getNumber() {
        System.out.println("number-00" + new Random().nextInt());
    }

    private void judge() {
        System.out.println("give a praised");
    }

    /**
     * 存钱 业务
     *
     * @param amount
     */
    public void saveVip(BigDecimal amount) {

        execute(() -> "vipNumber-00" + new Random().nextInt(), a -> System.out.println("save " + amount));

    }

    public void save(BigDecimal amount) {

        execute(() -> "number-00" + new Random().nextInt(), a -> System.out.println("save " + amount));

    }
    public void saveReservation(BigDecimal amount) {

        execute(() -> "reservationNumber-00" + new Random().nextInt(), a -> System.out.println("save " + amount));

    }

    /**
     * 取钱
     *
     * @param amount
     */
    public void draw(BigDecimal amount) {
        execute(() -> "reservationNumber-00" + new Random().nextInt(),a -> System.out.println("draw " + amount));
    }

    /**
     * 理财
     *
     * @param amount
     */
    public void moneyManage(BigDecimal amount) {
        execute(() -> "reservationNumber-00" + new Random().nextInt(), a -> System.out.println("moneyManage " + amount));
    }

    public static void main(String[] args) {
        BankBusinessHandler businessHandler = new BankBusinessHandler();
        businessHandler.save(new BigDecimal("1000"));
        businessHandler.saveVip(new BigDecimal("1000"));
        businessHandler.saveReservation(new BigDecimal("1000"));

    }
}
