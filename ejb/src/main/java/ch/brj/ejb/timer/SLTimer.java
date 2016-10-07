package ch.brj.ejb.timer;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.Date;

/**
 * Created by jakob on 07.10.2016.
 */
@LocalBean
@Stateless
public class SLTimer {

    @Schedule(hour = "*", minute = "*", second = "10, 40")
    void timer() {
        System.out.println("*** SLTimer fired " + new Date());
    }

    public String greeting(final String inName) {
        Date theCurrentTime = new Date();
        String theMessage = "Hello " + inName + " from SLTimer " +
                ". The time is now: " + theCurrentTime;
        return theMessage;
    }
}
