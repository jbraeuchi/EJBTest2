package ch.brj.ejb.client;


import ch.brj.ejb.StatefulSession1Bean;
import ch.brj.ejb.StatelessSessionRemote;
import ch.brj.ejb.concurrent.Executor;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementing a local EJB client.
 */
@WebServlet("/exec.do")
public class ExecutorServlet extends HttpServlet {
    /* Constant(s): */
    private static final long serialVersionUID = 1L;
    /* Instance variable(s): */
    @EJB
    private Executor executor;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        List<Integer> response = executor.doConcurrrently();
        theResponseWriter.println("Response from the Executor EJB: " + response);

    }
}
