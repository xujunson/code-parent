package com.atu.rabbitmq;

import com.atu.rabbit.many.AtuSender1;
import com.atu.rabbit.many.AtuSender2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {
    @Autowired
    private AtuSender1 atuSender1;

    @Autowired
    private AtuSender2 atuSender2;

    @Test
    public void oneToMany() throws Exception {
        for (int i = 0; i < 100; i++) {
            atuSender1.send(i);
        }
    }

    @Test
    public void manyToMany() throws Exception {
        for (int i = 0; i < 100; i++) {
            atuSender1.send(i);
            atuSender2.send(i);
        }
    }

}