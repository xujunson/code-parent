package com.atu.web;

import com.atu.config.ConfigBean;
import com.atu.config.ConfigTestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author ：Tom
 * @ 2020/2/18 9:40
 * @ Description：
 */
@RestController
public class UserController {

    //1、使用@Value 注入对应的值
    /*@Value("${com.atu.name}")
    private  String name;
    @Value("${com.atu.want}")
    private  String want;

    @RequestMapping("/")
    public String hexo(){
        return name+","+want;
    }*/

    //2、使用ConfigurationProperties
    @Autowired
    ConfigBean configBean;

    @RequestMapping("/")
    public String hexo(){
        return configBean.getName()+configBean.getWant();
    }

    //3. 自定义配置文件
   /*@Autowired
    ConfigTestBean configTestBean;

    @RequestMapping("/")
    public String hexo(){
        return configTestBean.getName()+configTestBean.getWant();
    }*/
}
