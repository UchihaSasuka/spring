package com.test.activemq;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProducerTest extends BaseJunitTest{

    @Autowired
    private ActiveMQProducer activeMQProducer;

    @Test
    public void send(){
        activeMQProducer.sendMessage("i am producer");
    }
}
