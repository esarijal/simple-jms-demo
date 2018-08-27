package com.mitrais.bootcamp.queue;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Producer {
    public Producer() throws JMSException, NamingException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
        InitialContext jndi = new InitialContext(props);

        ConnectionFactory connectionFactory = (ConnectionFactory) jndi.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        try{
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = (Destination) jndi.lookup("dynamicQueues/testQ");

            MessageProducer producer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage("Hello World");

            producer.send(textMessage);
        }finally {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        try {
            new Producer();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
