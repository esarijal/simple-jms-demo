package com.mitrais.bootcamp.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Subscriber {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;


    private static String topic = "dynamicTopics/testTopic";

    public static void main(String[] args) throws JMSException, NamingException {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            InitialContext ctx = new InitialContext(props);

            TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            TopicConnection connection = connectionFactory.createTopicConnection();
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber subscriber = session.createSubscriber((Topic) ctx.lookup(topic));

            MyListener listener = new MyListener();
            subscriber.setMessageListener(listener);


            System.out.println("Subscriber 1 ready,waiting ....");
            System.out.println("Press ctrl+c to shutdown");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
