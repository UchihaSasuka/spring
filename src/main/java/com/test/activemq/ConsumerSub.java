package com.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerSub {
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
                Destination destination = session.createTopic("topic1");
                //创建消费者,在订阅模式下，如果不设置持久化订阅，消费者必须比生产者先启动，不然是无法接收到队列中的消息的
                MessageConsumer messageConsumer = session.createConsumer(destination);
                //获取消息
                TextMessage textMessage;
                while (true){
                    Thread.sleep(100);
                    textMessage = (TextMessage) messageConsumer.receive();
                    if(textMessage != null){
                        System.out.println(textMessage.getText());
                    }
                }

            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
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
