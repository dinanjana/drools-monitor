package org.drools.configuration;

import org.drools.beans.KIEBase;
import org.drools.beans.KIEContainer;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Dinanjana
 * on 27/02/2016.
 */
public class Configuration {
    private static boolean isAutomatic= false;
    private static Queue<KIEContainer> kieContainerQueue = new LinkedBlockingQueue<KIEContainer>();
    KieServices ks = KieServices.Factory.get();

    public void getKIEContainer(){
        KieContainer kieContainer=ks.getKieClasspathContainer();
        addKIEContainer(kieContainer);
    }

    public void addKIEContainer(KieContainer kieContainer){
        KIEContainer container = new KIEContainer();
        Set<String> kieBaseNames = (Set<String>) kieContainer.getKieBaseNames();

        for(String kieBasename : kieBaseNames){
            System.out.println("KIE BASE NAME " +kieBasename );
            KIEBase kieBase = new KIEBase();
            kieBase.setKieBaseName(kieBasename);
            kieBase.setKieBase(kieContainer.getKieBase(kieBasename));
            List<KieSession> kieSessionList = (List<KieSession>) kieContainer
                    .getKieBase(kieBasename)
                    .getKieSessions();

            for(KieSession kieSession:kieSessionList){
                kieBase.addKieSession(kieSession);
            }

            container.addKIEBase(kieBase);
        }
        kieContainerQueue.add(container);

    }

    public Queue<KIEContainer> getKieContainerQueue() {
        return kieContainerQueue;
    }
}
