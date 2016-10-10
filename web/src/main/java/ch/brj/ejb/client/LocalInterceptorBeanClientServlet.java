package ch.brj.ejb.client;


import ch.brj.ejb.interceptors.SLBean;

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
@WebServlet("/intercept.do")
public class LocalInterceptorBeanClientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private SLBean mSL1;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        String theResponse1 = mSL1.greeting("Anonymous 1");
        theResponseWriter.println("Response from the SLBean: " + theResponse1);

        String theResponse2 = mSL1.greeting2("Anonymous 1");
        theResponseWriter.println("Response from the SLBean: " + theResponse2);
    }
}