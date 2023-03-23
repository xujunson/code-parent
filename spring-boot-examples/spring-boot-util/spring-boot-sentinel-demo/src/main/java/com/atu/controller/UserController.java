package com.atu.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.atu.service.UserService;
import com.atu.vo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/7/29 10:53 上午
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    //资源名 称
    public static final String RESOURCE_NAME = "userList";

    /**
     * 抛出异常方式
     *
     * @return
     */
    @RequestMapping("/list")
    public List<User> getUserList() {
        List<User> userList = null;
        Entry entry = null;
        try { // 被保护的业务逻
            entry = SphU.entry(RESOURCE_NAME);
            userList = userService.getList();
        } catch (BlockException e) {
            // 资源访问阻止，被限流或被降级
            return Collections.singletonList(new User("xxx", "资源访问被限流", 0));
        } catch (Exception e) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
        } finally {
            // 务必保证 exit，务必保证每个 entry 与 exit 配对
            if (entry != null) {
                entry.exit();
            }
        }
        return userList;
    }

    public static final String RESOURCE_NAME_QUERY_USER_BY_ID = "queryUserById";

    /**
     * 返回布尔值方式
     *
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    public String queryUserById(@PathVariable("id") String id) {
        if (SphO.entry(RESOURCE_NAME_QUERY_USER_BY_ID)) {
            try {
                //被保护的 逻辑
                //模拟数据库查询数据
                return JSONObject.toJSONString(new User(id, "Tom", 25));
            } finally { //关闭资源
                SphO.exit();
            }
        } else {
            //资源访问阻止，被限流或被降级
            return "Resource is Block!!!";
        }
    }

    /**
     * 注解实现
     * 降低侵入性
     *
     * @param userName
     * @return
     */
    @RequestMapping("/getUser")
    public User getUser(String userName) {
        return userService.queryByUserName(userName);
    }
}
