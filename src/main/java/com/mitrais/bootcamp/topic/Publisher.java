package com.mitrais.bootcamp.topic;

import org.apache.activemq.ActiveMQConnection;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Publisher {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;


    private static String topic = "dynamicTopics/testTopic";

    public static void main(String[] args) throws NamingException, JMSException, IOException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        InitialContext ctx = new InitialContext(props);
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        TopicConnection con = connectionFactory.createTopicConnection();
        con.start();

        TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic t = (Topic) ctx.lookup(topic);

        TopicPublisher publisher = session.createPublisher(t);
        TextMessage msg = session.createTextMessage();

        BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            System.out.println("Enter Msg, end to terminate:");
            String s=b.readLine();
            if (s.equals("end"))
                break;
            msg.setText(s);
            //7) send message
            publisher.publish(msg);
            System.out.println("Message successfully sent.");
        }
        //8) connection close
        con.close();

    }
}
