package com.test.rabbitmq.derect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.test.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) {
        Connection connection = null;
        //获取连接以及MQ通道
        try {
             connection = ConnectionUtil.getConnection();
            //建立信道
            Channel channel = connection.createChannel();
            //声明EXCHANGE
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            //创建消息
            String message = "更新商品， id = 101";
            channel.basicPublish(EXCHANGE_NAME, "update1", null, message.getBytes());
            System.out.println("已发送消息：" + message);

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
