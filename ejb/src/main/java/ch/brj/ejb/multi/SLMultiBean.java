package ch.brj.ejb.multi;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import java.util.Date;

/**
 * Created by jakob on 10.10.2016.
 */
@Stateless
@Local({SL1.class, SL2.class})
@Remote(SLRemote.class)
public class SLMultiBean implements SL1, SL2, SLRemote {

    @Resource
    SessionContext ctx;

    @Override
    public String greetingSL1(String name) {
        String ifName = ctx.getInvokedBusinessInterface().getCanonicalName();

        Date theCurrentTime = new Date();
        String theMessage = "Hello1 " + name + " from " + ifName + ". The time is now: " + theCurrentTime;
        return theMessage;
    }

    @Override
    public String greetingSL2(String name) {
        String ifName = ctx.getInvokedBusinessInterface().getCanonicalName();

        Date theCurrentTime = new Date();
        String theMessage = "Hello2 " + name + " from " + ifName + ". The time is now: " + theCurrentTime;
        return theMessage;
    }

    @Override
    public String greeting(String name) {
        String ifName = ctx.getInvokedBusinessInterface().getCanonicalName();

        Date theCurrentTime = new Date();
        String theMessage = "Hello Remote " + name + " from " + ifName + ". The time is now: " + theCurrentTime;
        return theMessage;

    }
}
