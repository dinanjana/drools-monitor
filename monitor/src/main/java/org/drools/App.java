package org.drools;

import org.drools.beans.KIEBase;
import org.drools.beans.KIEContainer;
import org.drools.beans.KIEContainerRegister;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.management.KieSessionMonitoringImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Hello world!
 *
 */
public class App 
{
//    public static void Play()
//    {
//        if(!Configuration.isAutomatic()){
//            KIESessionRegister kieSessionRegister = Configuration.getKieSessionRegister();
//            KieSessionMonitoringImpl kieSessionMonitoring = new KieSessionMonitoringImpl((InternalWorkingMemory) kieSessionRegister.getRegisteredKIESessions().poll());
//            System.out.println(kieSessionMonitoring.getTotalFactCount() + " " +kieSessionMonitoring.getTotalMatchesCreated());
//            System.out.println(kieSessionMonitoring.getStatsByRule().toString());
//
//        }
//        System.out.println( "Hello World!" );
//    }

//    public void play() {
//        if(!Configuration.isAutomatic()){
//            KIESessionRegister kieSessionRegister = Configuration.getKieSessionRegister();
//            KieSessionMonitoringImpl kieSessionMonitoring = new KieSessionMonitoringImpl((InternalWorkingMemory) kieSessionRegister.getRegisteredKIESessions().poll());
//            System.out.println(kieSessionMonitoring.getTotalFactCount() + " " +kieSessionMonitoring.getTotalMatchesCreated());
//            System.out.println(kieSessionMonitoring.getStatsByRule().toString());
//
//        }
//        System.out.println( "Hello World!" );
//    }
    static KieSessionMonitoringImpl kieSessionMonitoring;

    public void play(String kieBaseName){
        KIEContainerRegister kieContainerRegister = new KIEContainerRegister();
        KIEContainer kieContainer1 = kieContainerRegister.getKieContainerQueue().remove();
        System.out.println("KIE Base HashMap :" + kieContainer1.getKieBaseHashMap());
        KIEBase kieBase = kieContainer1.getKieBaseHashMap().get(kieBaseName);
        System.out.println("KIE BASE name:" + kieBase.getKieBaseName() );
        KieBase kieBase1 = kieBase.getKieBase();
        System.out.println("Session map"+kieBase.getKieSessionHashMap());
        List<KieSession> kieSessions = (List<KieSession>) kieBase1.getKieSessions();
        System.out.println("Entry point IDs:" + kieBase1.getEntryPointIds());
        System.out.println("Session"+kieSessions);
        System.out.println("Agenda"+kieSessions.get(0).getAgenda().toString());
        KieSession kieSession = kieSessions.get(0);
        kieSessionMonitoring = new KieSessionMonitoringImpl((InternalWorkingMemory) kieSession);

    }

    public void print(){
        System.out.println(kieSessionMonitoring.getTotalFactCount() + " " +kieSessionMonitoring.getTotalMatchesCreated());
        System.out.println(kieSessionMonitoring.getStatsByRule().toString());
    }

    public String getStat(){
        return kieSessionMonitoring.getStatsByRule().toString();
    }

    public static void startService(){

        Container container = null;
        try {
            container = new Container();
            JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "myapp.war");
            deployment.addClass(SessionDetailsController.class);
            deployment.addAllDependencies();
            System.out.println("Deploying..");
            container.start().deploy(deployment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String [] args){


        // Create an RMI connector client and
        // connect it to the RMI connector server
        //
        org.drools.connector.JMXConnector.connectToMbeanServer();


    }



    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
