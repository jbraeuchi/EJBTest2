package ch.brj.ejb;

import ch.brj.ejb.multi.SLRemote;
import ch.brj.ejb.singleton.SingletonBean;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    //
    //    java:global/ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote
    //    java:app/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote
    //    java:module/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote
    //    java:jboss/exported/ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote
    //    ejb:ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote
    //    java:global/ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean
    //    java:app/ejb/StatelessSessionRemoteBean
    //    java:module/StatelessSessionRemoteBean

    @Test
    public void test_ejbNaming() throws Exception {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        String name1 = "ejb:ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote";

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

        String name1 = "/ear-0.0.1-SNAPSHOT/ejb/StatelessSessionRemoteBean!ch.brj.ejb.StatelessSessionRemote";

        Context ctx = new InitialContext(env);
        StatelessSessionRemote bean = (StatelessSessionRemote) ctx.lookup(name1);
        String message = bean.greeting("Client");
        System.out.println(message);

//        String name2 = "ejb:EJBTest/web/StatefulSession1Bean!ch.brj.ejb.StatefulSession1Bean?stateful";
//        ctx.lookupLink(name2);
    }

    @Test
    public void test_SLRemote() throws Exception {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        String name1 = "ejb:ear-0.0.1-SNAPSHOT/ejb/SLMultiBean!ch.brj.ejb.multi.SLRemote";

        Context ctx = new InitialContext(env);
        SLRemote bean = (SLRemote) ctx.lookup(name1);
        String message = bean.greeting("Client");
        System.out.println(message);
    }

    @Test
    @Disabled
    public void test_EmbeddedContainer() throws NamingException {
        String name1 = "ejb:EJBTest/ejb/SingletonBean";

        Properties props = new Properties();
        props.setProperty(EJBContainer.APP_NAME, "EJBTest");
        EJBContainer ec = EJBContainer.createEJBContainer(props);

        Context ctx = ec.getContext();
        SingletonBean bean = (SingletonBean) ctx.lookup(name1);
        String message = bean.greeting("Client");
        System.out.println(message);
    }
}
