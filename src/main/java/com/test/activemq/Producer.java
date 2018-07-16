package com.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
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
                //创建生产者
                MessageProducer messageProducer = session.createProducer(destination);
                //设置持久化对象。如果没有持久化，在MQ重启的时候消息会丢失
                //可以采用kahdb/leverldb/jdbc的方式
                messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                //定义消息对象，并发送，这里也需要使用Session来创建
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("Hello, ActiveMQ");
                messageProducer.send(textMessage);
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
