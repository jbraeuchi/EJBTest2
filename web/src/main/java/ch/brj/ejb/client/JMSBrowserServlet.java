package ch.brj.ejb.client;


import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Add the following lines to <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
 * <p>
 * <jms-queue name="testQueue" entries="java:/jms/queue/test"/>
 * <jms-topic name="testTopic" entries="java:/jms/topic/test"/>
 * <p>
 * JMSContext ist JMS 2.0
 */
@WebServlet("/jms.do")
public class JMSBrowserServlet extends HttpServlet {
    @Inject
    private JMSContext context;  // JMS 2.0

    @Resource
    private ConnectionFactory connectionFactory;

    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {

        try (PrintWriter out = inResponse.getWriter()) {
            out.println("Browsing JMS");
            Queue queue = null;
            String param = null;

            if (inRequest.getParameterMap().keySet().contains("queue")) {
                param = inRequest.getParameterMap().get("queue")[0];
                out.println("Queue=" + param);
                try {
                    queue = (Queue) new InitialContext().lookup(param);
                } catch (NamingException e) {
                    throw new EJBException(e);
                }
            }

            String messages = browse(queue);
            out.println(messages);
        }
    }

    private String browse(Queue queue) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            QueueBrowser browser = session.createBrowser(queue);

            StringBuilder sb = new StringBuilder();
            Enumeration messageEnum = browser.getEnumeration();
            while (messageEnum.hasMoreElements()) {
                TextMessage message = (TextMessage) messageEnum.nextElement();

                sb.append("\nID=");
                sb.append(message.getJMSMessageID());
                sb.append(" Text=");
                sb.append(message.getText());
            }

            browser.close();
            return sb.toString();
        } catch (JMSException e) {
            throw new EJBException(e);
        }
    }
}