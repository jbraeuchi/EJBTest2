package ch.brj.ejb.singleton;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.Date;

/**
 * Created by jakob on 07.10.2016.
 */
@Singleton
@LocalBean
public class SingletonBean {
    int counter;

    public String greeting(final String inName) {
        counter++;
        String theMessage = "Hello " + inName + " from SingletonBean, invocations = " + counter;
        return theMessage;
    }
}
