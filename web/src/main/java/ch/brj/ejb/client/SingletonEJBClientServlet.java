package ch.brj.ejb.client;


import ch.brj.ejb.StatefulSession1Bean;
import ch.brj.ejb.StatelessSessionRemote;
import ch.brj.ejb.singleton.SingletonBean;

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
@WebServlet("/singleton.do")
public class SingletonEJBClientServlet extends HttpServlet {

    @EJB
    private SingletonBean mSingleton;

    @PostConstruct
    private void initialize() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        String theResponse = mSingleton.greeting("Anonymous");
        theResponseWriter.println("Response from SingletonBean EJB: " + theResponse);
    }
}