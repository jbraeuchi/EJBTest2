package ch.brj.ejb.client;


import ch.brj.ejb.multi.SL1;
import ch.brj.ejb.multi.SL2;
import ch.brj.ejb.multi.SLRemote;

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

        String theResponse2 = mSL2.greetingSL2("Anonymous 1");
        theResponseWriter.println("Response from the SL2: " + theResponse2);

        String theResponse3 = mSLRemote.greeting("Anonymous");
        theResponseWriter.println("Response from the SLRemote: " + theResponse3);


    }
}