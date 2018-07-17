package com.test.rabbitmq.derect;

import com.rabbitmq.client.*;
import com.test.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    private static final String QUEUE_NAME = "test_queue_direct_1";

    public static void main(String[] args) {
        Connection connection = null;
        //获取连接以及MQ通道
        try {
            connection = ConnectionUtil.getConnection();
            //建立信道
            final Channel channel = connection.createChannel();
            //声明交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            //绑定到交换机
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");



            //获取消息
            while(true){
                boolean autoAck = false;
                String consumerTag = "";
                channel.basicConsume(QUEUE_NAME, autoAck, consumerTag, new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        System.out.println("消费的路由键：" + routingKey);
                        System.out.println("消费的内容类型：" + contentType);
                        long deliverTag = envelope.getDeliveryTag();
                        //消息确认
                        channel.basicAck(deliverTag, false);
                        System.out.println("消费消息的内容：");
                        String message = new String(body, "UTF-8");
                        System.out.println(message);
                    }
                });
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
