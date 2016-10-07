package ch.brj.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.Date;


@Stateless
@Remote(StatelessSessionRemote.class)
public class StatelessSessionRemoteBean implements StatelessSessionRemote {

    @PostConstruct
    public void initialize() {
        System.out.println("*** StatelessSessionRemoteBean created.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("*** StatelessSessionRemoteBean destroyed.");
    }

    /**
     * Creates a greeting to the person with the supplied name.
     *
     * @param inName Name of person to greet.
     * @return Greeting.
     */
    public String greeting(final String inName) {
        Date theCurrentTime = new Date();
        String theMessage = "Hello " + inName + ", I am stateless session bean " +
                ". The time is now: " + theCurrentTime;
        return theMessage;
    }

}