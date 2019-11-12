package com.atu;

import com.atu.service.TestAsyncService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncApplicationTests {

    @Autowired
    private TestAsyncService testAsyncService;
    @Test
    public void test() throws InterruptedException {
        testAsyncService.service1();
        testAsyncService.service2();
    }

}
