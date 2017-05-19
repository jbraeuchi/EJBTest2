package ch.brj.ejb.client;


import ch.brj.jms.JMSService;
import ch.brj.jms.Message;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
    JMSService jmsService;

    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {

        try (PrintWriter out = inResponse.getWriter()) {
            out.println("Browsing JMS");
            String queueName = null;

            if (inRequest.getParameterMap().keySet().contains("queue")) {
                queueName = inRequest.getParameterMap().get("queue")[0];
                out.println("Queue=" + queueName);

                List<Message> messages = jmsService.browseQueue(queueName, null);
                out.println(messages);

                jmsService.moveMessage(messages.get(0).getId(), queueName, "java:/jms/queue/test");
                out.println("MOVED " + messages.get(0).getId());
            }
        }
    }
}