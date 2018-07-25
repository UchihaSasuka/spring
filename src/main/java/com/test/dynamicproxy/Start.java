package com.test.dynamicproxy;

import com.test.activemq.BaseJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Start extends BaseJunitTest{
    @Autowired
    TestService testService;

    @Test
    public void test(){
        System.out.println(testService.sayHello());
    }
}
