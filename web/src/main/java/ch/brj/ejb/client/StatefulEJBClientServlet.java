package ch.brj.ejb.client;


import ch.brj.ejb.StatefulSession1Bean;

import javax.ejb.EJB;
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
@WebServlet("/stateful.do")
public class StatefulEJBClientServlet extends HttpServlet {
    @EJB
    private StatefulSession1Bean mLocalSessionBean;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        String theResponse1 = mLocalSessionBean.greeting("Anonymous 1");
        theResponseWriter.println("Response from the StatefulSession1Bean EJB: " + theResponse1);

        mLocalSessionBean.remove();
        theResponseWriter.println("Bean removed");

    }
}