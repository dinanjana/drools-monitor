package org.drools.logic;

import org.drools.beans.KIEContainer;
import org.drools.beans.KIEContainerRegister;

/**
 * Created by dinanjanag
 * on 2/29/2016.
 */
public class DataFetcher {
    private static KIEContainerRegister kieContainerRegister = new KIEContainerRegister();

    public static KIEContainer getKIEContainer(){
        if(kieContainerRegister.getKieContainerQueue().size() > 0){
            KIEContainer kieContainer = kieContainerRegister.getKieContainerQueue().remove();
            kieContainerRegister.addKIEContainer(kieContainer);
            return kieContainer;
        }
        return null;
    }
}
