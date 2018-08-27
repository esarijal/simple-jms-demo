package com.mitrais.bootcamp.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener {
    public void onMessage(Message message) {
        try{
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(textMessage.getText());
            }
        } catch (JMSException ex){
            ex.getStackTrace();
        }
    }
}
