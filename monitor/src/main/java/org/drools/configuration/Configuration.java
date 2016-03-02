package org.drools.configuration;

import org.drools.beans.KIEBase;
import org.drools.beans.KIEContainer;
import org.drools.beans.KIEContainerRegister;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;

/**
 * Created by Dinanjana
 * on 27/02/2016.
 */
public class Configuration {
    private static boolean isAutomatic= false;
    KIEContainerRegister kieContainerRegister = new KIEContainerRegister();
    KieServices ks = KieServices.Factory.get();

    public void startRuleMonitor(){
        isAutomatic=true;
        KieContainer kieContainer=ks.getKieClasspathContainer();
        addKIEContainer(kieContainer);
    }

    public void addKIEContainer(KieContainer kieContainer){
         kieContainerRegister.registerKIEContainer(kieContainer);
    }

}
