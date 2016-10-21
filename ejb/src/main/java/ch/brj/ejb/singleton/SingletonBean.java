package ch.brj.ejb.singleton;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
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
@DeclareRoles({"testRole1", "testRole2"})
public class SingletonBean {
    int counter;

    @Resource
    SessionContext ctx;

    @PostConstruct  // Protected
    protected void initialize() {
        System.out.println("*** SingletonBean created.");
    }

    @PermitAll
    public String greeting(final String inName) {
        String theMessage;
        Principal principal = ctx.getCallerPrincipal();

        if (ctx.isCallerInRole("testRole2")) {
            counter++;
            theMessage = "Hello " + inName + " from SingletonBean, invocations = " + counter + " Caller: " + principal.getName();
        } else {
            theMessage = "Caller " + principal.getName() + " is not allowed";
        }
        return theMessage;
    }
}
