package com.test.rabbitmq.derect;

import com.rabbitmq.client.*;
import com.test.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) {
        Connection connection = null;
        //获取连接以及MQ通道
        try {
            connection = ConnectionUtil.getConnection();
            //建立信道
            final Channel channel = connection.createChannel();
            //声明交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();
            //绑定到交换机
            channel.queueBind(queueName, EXCHANGE_NAME, "update");
            channel.queueBind(queueName, EXCHANGE_NAME, "delete");



            //获取消息
            while(true){
                Consumer consumer = new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        System.out.println("消费的路由键：" + routingKey);
                        System.out.println("消费的内容类型：" + contentType);
                        System.out.println("消费消息的内容：");
                        String message = new String(body, "UTF-8");
                        System.out.println(message);
                    }
                };
                //消息确认
                channel.basicConsume(queueName, true, consumer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
