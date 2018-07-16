package com.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) {
        System.out.println(
                ActiveMQConnectionFactory.DEFAULT_USER + "," +
                        ActiveMQConnectionFactory.DEFAULT_PASSWORD + "," +
                        ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL
        );

        //创建连接Connection
        Connection connection = null;

        {
            try {
                connection = connectionFactory.createConnection();
                connection.start();

                //创建Session
                Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

                //创建Destination,PTP模式下设置为队列，pub/sub模式下为topic
                Destination destination = session.createQueue("queue1");
                //创建消费者
                MessageConsumer messageConsumer = session.createConsumer(destination);
                //获取消息
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                if(textMessage != null){
                    System.out.println(textMessage.getText());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
