package ch.brj.ejb.client;


import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Add the following lines to <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
 * <p>
 * <jms-queue name="testQueue" entries="java:/jms/queue/test"/>
 * <jms-topic name="testTopic" entries="java:/jms/topic/test"/>
 */
@WebServlet("/mdb.do")
public class MdbClientServlet extends HttpServlet {
    @Inject
    private JMSContext context;

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

                sendWithContext(destination, text);
//               sendWithConnectionFactory(destination, text);
            }
        }
    }

    private void sendWithContext(Destination dest, String msg) {
        context.createProducer().send(dest, msg);
    }

    private void sendWithConnectionFactory(Destination dest, String msg) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage(msg);
            session.createProducer(dest).send(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}