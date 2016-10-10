package ch.brj.ejb.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Created by jakob on 10.10.2016.
 */
public class InterceptorA {
    @AroundInvoke
    private Object log(InvocationContext ic) throws Exception {
        System.out.println("InterceptorA before bean method: " + ic.getMethod());
        Object obj = ic.proceed(); //call the bean method.         
        System.out.println("InterceptorA after bean method: " + ic.getMethod());
        return "InterceptorA: " + obj;
    }
}
