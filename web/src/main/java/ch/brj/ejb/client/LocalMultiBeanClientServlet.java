package ch.brj.ejb.client;


import ch.brj.ejb.multi.SL1;
import ch.brj.ejb.multi.SL2;
import ch.brj.ejb.multi.SLRemote;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@WebServlet("/multi.do")
public class LocalMultiBeanClientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private SL1 mSL1;

    @EJB
    private SL2 mSL2;

    @EJB
    private SLRemote mSLRemote;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        String theResponse1 = mSL1.greetingSL1("Anonymous 1");
        theResponseWriter.println("Response from the SL1: " + theResponse1);

        String theResponse2 = mSL2.greetingSL2("Anonymous 2");
        theResponseWriter.println("Response from the SL2: " + theResponse2);

        String theResponse3 = mSLRemote.greeting("Anonymous 3");
        theResponseWriter.println("Response from the SLRemote: " + theResponse3);


        // Test: lookup injected Bean
        // When name is not given for @EJB annotation,
        // a default name consisting of <package name>.<ClassName>/<variableName> is assumed by the container
        try {
            Context ctx = new InitialContext();
            SLRemote bean = (SLRemote) ctx.lookup("java:comp/env/ch.brj.ejb.client.LocalMultiBeanClientServlet/mSLRemote");
            String theResponse4 = bean.greeting("Anonymous 4");
            theResponseWriter.println("Response from the looked up SLRemote: " + theResponse4);
        } catch (NamingException ex) {
            throw new ServletException(ex);
        }
    }
}