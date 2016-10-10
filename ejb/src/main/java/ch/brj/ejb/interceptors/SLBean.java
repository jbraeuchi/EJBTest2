package ch.brj.ejb.interceptors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;

/**
 * Created by jakob on 10.10.2016.
 */
@Stateless
@LocalBean
public class SLBean {

    @Interceptors({InterceptorA.class})
    public String greeting(String name) {
        Date theCurrentTime = new Date();
        String theMessage = "Hello " + name + " from interceptors.SLBean. The time is now: " + theCurrentTime;
        return theMessage;
    }

    @Interceptors({InterceptorB.class})
    public String greeting2(String name) {
        Date theCurrentTime = new Date();
        String theMessage = "Hello " + name + " from interceptors.SLBean. The time is now: " + theCurrentTime;
        return theMessage;
    }

}
