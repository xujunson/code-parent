package com.atu.rabbitmq;

import com.atu.model.User;
import com.atu.rabbit.object.ObjectSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {

    @Autowired
    private ObjectSender sender;

    @Test
    public void sendObject() throws Exception {
        User user = new User();
        user.setName("atu");
        user.setPass("123456");
        sender.send(user);
    }

}