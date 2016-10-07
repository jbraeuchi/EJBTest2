package ch.brj.ejb.client;


import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Add the following lines to <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
 *
 * <jms-queue name="testQueue" entries="jms/queue/test java:jboss/exported/jms/queue/test"/>
 * <jms-topic name="testTopic" entries="jms/topic/test java:jboss/exported/jms/topic/test"/>
 */
@WebServlet("/mdb.do")
public class MdbClientServlet extends HttpServlet {
    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/test")
    private Queue queue;

    @Resource(mappedName = "java:/jms/topic/test")
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
                context.createProducer().send(destination, text);
                out.println("Message: " + text);
            }
        }
    }

}