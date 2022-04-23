package ch.brj.ejb.client;


import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Add the following lines to <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
 * <p>
 * <jms-queue name="testQueue" entries="java:/jms/queue/test"/>
 * <jms-topic name="testTopic" entries="java:/jms/topic/test"/>
 * <p>
 * JMSContext ist JMS 2.0
 */
@WebServlet("/mdb.do")
public class MdbClientServlet extends HttpServlet {
    @Inject
    private JMSContext context;  // JMS 2.0

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/jms/queue/test")
    private Queue queue;

    //    @Resource(lookup = "java:/jms/topic/test")
    @Resource(name = "java:/jms/topic/test")
    private Topic topic;

    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {

        try (PrintWriter out = inResponse.getWriter()) {
            out.println("JMS with WildFly");
            Destination destination = null;
            String param = null;

            if (inRequest.getParameterMap().keySet().contains("topic")) {
                destination = topic;
                param = inRequest.getParameterMap().get("topic")[0];
            } else if (inRequest.getParameterMap().keySet().contains("queue")) {
                destination = queue;
                param = inRequest.getParameterMap().get("queue")[0];
            }

            if (destination != null) {
                out.println("Sending messages to " + destination);
                String text = "This is the message '" + param + "' " + System.currentTimeMillis();
                out.println("Message: " + text);

                sendJMS11(destination, text);
    //            sendJMS20(destination, text);
            }
        }
    }

    private void sendJMS20(Destination dest, String msg) {
        context.createProducer().send(dest, msg);
    }

    private void sendJMS11(Destination dest, String msg) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage(msg);
            message.setIntProperty("myInteger", 42);
            message.setObjectProperty("myObject", Double.valueOf("142.45"));
            session.createProducer(dest).send(message);
        } catch (JMSException e) {
            throw new EJBException(e);
        }
    }
}
