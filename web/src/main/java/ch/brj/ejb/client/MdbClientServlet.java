package ch.brj.ejb.client;


import javax.annotation.Resource;
import javax.ejb.Remote;
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
 * Servlet implementing a local EJB client.
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
        PrintWriter out = inResponse.getWriter();

        out.println("JMS with WildFly");
        try {
            Destination destination;
            if (inRequest.getParameterMap().keySet().contains("topic")) {
                destination = topic;
            } else {
                destination = queue;
            }
            out.println("Sending messages to " + destination);
            String text = "This is the message.";
            context.createProducer().send(destination, text);
            out.println("Message: " + text);
            out.println("Go to your WildFly Server console or Server log to see the result of messages processing");
        }catch (Exception e) {
            out.println(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}