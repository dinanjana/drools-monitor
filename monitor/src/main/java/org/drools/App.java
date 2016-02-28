package org.drools;

import org.drools.beans.KIEBase;
import org.drools.beans.KIEContainer;
import org.drools.configuration.Configuration;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.management.KieSessionMonitoringImpl;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;

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
    KieSessionMonitoringImpl kieSessionMonitoring;

    public void play(String kieBaseName){
        Configuration configuration = new Configuration();
        KIEContainer kieContainer1 = configuration.getKieContainerQueue().remove();
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
        this.kieSessionMonitoring = new KieSessionMonitoringImpl((InternalWorkingMemory) kieSession);

    }

    public void print(){
        System.out.println(kieSessionMonitoring.getTotalFactCount() + " " +kieSessionMonitoring.getTotalMatchesCreated());
        System.out.println(kieSessionMonitoring.getStatsByRule().toString());
    }
}
