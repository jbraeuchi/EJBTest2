package ch.brj.jms;

import javax.inject.Inject;
import javax.jms.*;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jakob on 19.05.2017.
 */
public class JMSService {
    @Inject
    private JMSContext context;  // JMS 2.0

    public List<JMSMessage> browseQueue(String queueName, String selector) {
        try {
            Queue queue = (Queue) new InitialContext().lookup(queueName);

            List<JMSMessage> result = new ArrayList<>();
            QueueBrowser browser = context.createBrowser(queue, selector);

            Enumeration messageEnum = browser.getEnumeration();
            while (messageEnum.hasMoreElements()) {
                TextMessage tm = (TextMessage) messageEnum.nextElement();

                JMSMessage msg = new JMSMessage();
                msg.setId(tm.getJMSMessageID());
                msg.setText(tm.getText());

                Enumeration<String> props = tm.getPropertyNames();
                while (props.hasMoreElements()) {
                    String name = props.nextElement();
                    Object val = tm.getObjectProperty(name);
                    msg.getProperties().put(name, val);
                }

                result.add(msg);
            }
            browser.close();

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void moveMessage(String msgId, String fromName, String toName) {
        try {
            Queue fromQueue = (Queue) new InitialContext().lookup(fromName);
            Queue toQueue = (Queue) new InitialContext().lookup(toName);

            String selector = String.format("JMSMessageID = '%s'", msgId);
            JMSConsumer consumer = context.createConsumer(fromQueue, selector);
            Message message = consumer.receiveNoWait();

            if (message != null) {
                JMSProducer producer = context.createProducer();
                producer.send(toQueue, message);
            }
            consumer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void moveMessages(List<String> msgIds, String fromName, String toName) {
        try {
            Queue fromQueue = (Queue) new InitialContext().lookup(fromName);
            Queue toQueue = (Queue) new InitialContext().lookup(toName);

            String selector = createInSelector(msgIds);
            JMSConsumer consumer = context.createConsumer(fromQueue, selector);
            JMSProducer producer = context.createProducer();

            Message message = consumer.receiveNoWait();
            while (message != null) {
                producer.send(toQueue, message);
                message = consumer.receiveNoWait();
            }
            consumer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createInSelector(List<String> msgIds) {
        StringJoiner sj = new StringJoiner("','", "JMSMessageID in ('", "')");
        msgIds.forEach(i -> sj.add(i));
        return sj.toString();
    }
}
