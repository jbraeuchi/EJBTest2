package ch.brj.ejb;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * Created by jakob on 01.10.2016.
 */
public class CallEJB {

    // ejb:
    // The ejb: namespace is provided by the jboss-ejb-client library. This protocol allows you to look up EJB's,
    // using their application name, module name, ejb name and interface type.
    //
    // For stateless beans:
    // ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>
    //
    //  For stateful beans:
    // ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>?stateful
    //
    // see https://blog.akquinet.de/2014/09/26/jboss-eap-wildfly-three-ways-to-invoke-remote-ejbs/

    @Test
    public void test_ejbNaming() throws Exception {
        final Properties env = new Properties();
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        String name1 = "ejb:EJBTest/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote";

        Context ctx = new InitialContext(env);
        StatelessSessionRemote bean = (StatelessSessionRemote) ctx.lookup(name1);
        String message = bean.greeting("Client");
        System.out.println(message);
    }

    @Test
    public void test_remoteNaming() throws Exception {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        String name1 = "/EJBTest/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote";

        Context ctx = new InitialContext(env);
        StatelessSessionRemote bean = (StatelessSessionRemote) ctx.lookup(name1);
        String message = bean.greeting("Client");
        System.out.println(message);

//        String name2 = "ejb:EJBTest/web/StatefulSession1Bean!ch.brj.ejb.StatefulSession1Bean?stateful";
//        ctx.lookupLink(name2);
    }
}
