package com.atu;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.atu.controller.UserController;
import com.atu.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/7/29 10:06 上午
 * @Description:
 */
@SpringBootApplication
public class SentinelDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelDemoApplication.class, args);
        initFlowQpsRule();
    }

    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(UserController.RESOURCE_NAME);
        // set limit qps to 2
        rule.setCount(2);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);

        FlowRule rule2 = new FlowRule(UserController.RESOURCE_NAME_QUERY_USER_BY_ID);
        // set limit qps to 2
        rule2.setCount(2);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setLimitApp("default");
        rules.add(rule2);

        FlowRule rule3= new FlowRule(UserService.RESOURCE_NAME_QUERY_USER_BY_NAME);
        // set limit qps to 2
        rule3.setCount(2);
        rule3.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule3.setLimitApp("default");
        rules.add(rule3);
        FlowRuleManager.loadRules(rules);
    }
}