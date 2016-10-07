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
@WebServlet("/test.do")
public class LocalEJBClientServlet extends HttpServlet {
    /* Constant(s): */
    private static final long serialVersionUID = 1L;
    /* Instance variable(s): */
    @EJB
    private StatefulSession1Bean mLocalSessionBean;

    @EJB
    private StatelessSessionRemote mRemoteSessionBean;

    private StatelessSessionRemote slBean;

    @EJB
    private SingletonBean mSingleton;

    @PostConstruct
    public void initialize() {
        try {
            InitialContext theInitialContext = new InitialContext();
            slBean = (StatelessSessionRemote) theInitialContext.lookup("java:app/ejb/StatelessSessionRemoteBean");
        } catch (NamingException theException) {
            theException.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest inRequest,
                         HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();

        String theResponse1 = mLocalSessionBean.greeting("Anonymous 1");
        theResponseWriter.println("Response from the StatefulSession1Bean EJB: " + theResponse1);

        String theResponse2 = mRemoteSessionBean.greeting("Anonymous 2");
        theResponseWriter.println("Response from the StatelessSessionRemoteBean EJB: " + theResponse2);

        String theResponse3 = slBean.greeting("Anonymous 3");
        theResponseWriter.println("Response from looked up StatelessSessionRemoteBean EJB: " + theResponse3);

        String theResponse4 = mSingleton.greeting("Anonymous 4");
        theResponseWriter.println("Response from SingletonBean EJB: " + theResponse4);

/* Process a list to examine parameter passing semantics. */
        List<String> theList = new ArrayList<String>();
        theList.add("string 1");
        theList.add("string 2");
        theList.add("last string");
        mLocalSessionBean.processList(theList);
/* Output list after EJB invocation. */
        String theListStrings = "";
        for (String theString : theList) {
            theListStrings += theString + ", ";
        }
        System.out.println("\nList after having invoked EJB processList: [" +
                theListStrings + "]");
    }
}