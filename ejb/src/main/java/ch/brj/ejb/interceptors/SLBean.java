package ch.brj.ejb.interceptors;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 * Created by jakob on 10.10.2016.
 */
@Stateless
@LocalBean
public class SLBean {

    @Resource(name = "greeting")
    String greeting = "Hello from Bean";

    @Interceptors({InterceptorA.class})
    public String greeting(String name) {
        try {
            // read "greeting" using JNDI
            Context ctx = new InitialContext();
            String greeting2 = (String) ctx.lookup("java:comp/env/greeting");
            System.out.println("*** env greeting: " + greeting2);
        } catch (NamingException e) {
            System.out.println("*** cannot read env " + e.getMessage());
        }

        Date theCurrentTime = new Date();
        String theMessage = greeting + " " + name + " from interceptors.SLBean. The time is now: " + theCurrentTime;
        return theMessage;
    }

    @Interceptors({InterceptorB.class})
    public String greeting2(String name) {
        Date theCurrentTime = new Date();
        String theMessage = greeting + " " + name + " from interceptors.SLBean. The time is now: " + theCurrentTime;
        return theMessage;
    }

}
