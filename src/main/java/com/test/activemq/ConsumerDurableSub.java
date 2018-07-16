package com.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerDurableSub {
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
                String consumerId = "consumer-1";
                //持久化订阅需要在连接上设置持久化ID来标识消费者;
                connection.setClientID(consumerId);
                connection.start();
                //创建Session
                Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

                //创建Destination,PTP模式下设置为队列，pub/sub模式下为topic
                Topic topic = session.createTopic("topic2");
                //创建消费者,在订阅模式下，如果不设置持久化订阅，消费者必须比生产者先启动，不然是无法接收到队列中的消息的
                MessageConsumer messageConsumer = session.createDurableSubscriber(topic, consumerId);
                //获取消息
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                int count = 1;
                while(textMessage != null){
                    System.out.println("第"+count+"条订阅消息: "+textMessage.getText());
                    count++;
                    textMessage = (TextMessage) messageConsumer.receive();
                }

            } catch (JMSException e) {
                e.printStackTrace();
            }  finally {
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
