package ch.brj.ejb.singleton;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import java.security.Principal;

/**
 * Created by jakob on 07.10.2016.
 */
@Singleton
@LocalBean
public class SingletonBean {
    int counter;

    @Resource
    SessionContext ctx;

    @PostConstruct  // Protected
    protected void initialize() {
        System.out.println("*** SingletonBean created.");
    }

    @RolesAllowed("testRole2")
    public String greeting(final String inName) {

        Principal principal = ctx.getCallerPrincipal();

        counter++;
        String theMessage = "Hello " + inName + " from SingletonBean, invocations = " + counter + " Caller: " + principal.getName();
        return theMessage;
    }
}
