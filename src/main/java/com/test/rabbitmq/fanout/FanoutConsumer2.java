package com.test.rabbitmq.fanout;

import com.rabbitmq.client.*;
import com.test.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer2 {

    private static final String EXCHANGE_NAME = "exchange-fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        //绑定queue
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("消费者2接收到客户端消息：" + message);
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
