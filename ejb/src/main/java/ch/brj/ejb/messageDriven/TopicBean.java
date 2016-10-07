package ch.brj.ejb.messageDriven;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by jakob on 07.10.2016.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/test"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class TopicBean implements MessageListener {
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            System.out.println("*** TopicBean message received: " + rcvMessage);
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                System.out.println("*** Received Message from topic: >" + msg.getText() + "<");
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
