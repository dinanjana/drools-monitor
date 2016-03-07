package org.drools.beans.deprecated;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dinanjanag
 * on 2/29/2016.
 */
public class KIEContainerRegister {

    private static Queue<KIEContainer> kieContainerQueue = new LinkedBlockingQueue<KIEContainer>();

    public void registerKIEContainer(KieContainer kieContainer){
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

    public void addKIEContainer(KIEContainer kieContainer){
        kieContainerQueue.add(kieContainer);
    }
}
