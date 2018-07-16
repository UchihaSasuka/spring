package com.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        HelloWorld helloWorld =
                (HelloWorld) context.getBean("springHelloWorld");

        helloWorld.hello();

    }
}
