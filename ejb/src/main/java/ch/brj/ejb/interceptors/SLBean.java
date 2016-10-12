package ch.brj.ejb.interceptors;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
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
            Context ctx = new InitialContext();
            String greeting2 = (String) ctx.lookup("java:comp/env/greeting");
            System.out.println("*** Greeting 2: " + greeting2);
        } catch (Exception e) {
            throw new EJBException(e);
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
