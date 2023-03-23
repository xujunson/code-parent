package com.atu.utils;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/7/29 10:09 上午
 * @Description:
 */
public class RuleUtils {
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    public static void main(String[] args) throws Exception {
        initFlowRules();
        while (true) {
            Thread.sleep(2000);
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                /*您的业务逻辑-开始*/
                System.out.println("hello world");
                /*您的业务逻辑-结束*/
            } catch (BlockException e1) {
                /*流控逻辑处理-开始*/
                System.out.println("block!");
                /*流控逻辑处理-结束*/
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }
}
