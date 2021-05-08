package com.atu.design.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/5/6 11:05 上午
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        List<Melon> melons = new ArrayList<>();
        melons.add(new Melon("羊角蜜", 1, "泰国"));
        melons.add(new Melon("西瓜", 2, "三亚"));
        melons.add(new Melon("黄河蜜", 3, "兰州"));


        //注意：lambda表达式只能在函数式接口的上下文中使用
        List<Melon> watermelons = Filters.filter(melons, (Melon m) ->
                "西瓜".equalsIgnoreCase(m.getType())
        );
        System.out.println(watermelons);

        //--------------------------------------------------------

        //数字
        List<Integer> numbers = Arrays.asList(1, 13, 16, 2, 6, 88, 99);
        List<Integer> smallThanTen = Filters.filter(numbers,(Integer i) -> i < 10);
        System.out.println(smallThanTen);
    }
}
