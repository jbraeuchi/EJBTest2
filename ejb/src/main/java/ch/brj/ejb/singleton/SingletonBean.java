package ch.brj.ejb.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Created by jakob on 07.10.2016.
 */
@Singleton
@LocalBean
public class SingletonBean {
    int counter;

    @PostConstruct  // Protected
    protected void initialize() {
        System.out.println("*** SingletonBean created.");
    }

    public String greeting(final String inName) {
        counter++;
        String theMessage = "Hello " + inName + " from SingletonBean, invocations = " + counter;
        return theMessage;
    }
}
