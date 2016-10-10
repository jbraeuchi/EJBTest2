package ch.brj.ejb.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Created by jakob on 10.10.2016.
 */
public class InterceptorB {
    @AroundInvoke
    private Object log(InvocationContext ic) throws Exception {
        System.out.println("InterceptorB before bean method: " + ic.getMethod());

        return "InterceptorB: always returns 42";
    }
}
