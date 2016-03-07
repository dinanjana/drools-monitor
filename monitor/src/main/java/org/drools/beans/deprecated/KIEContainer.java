package org.drools.beans.deprecated;

import java.util.HashMap;

/**
 * Created by Dinanjana
 * on 27/02/2016.
 */
public class KIEContainer {

    private static HashMap<String,KIEBase> kieBaseHashMap = new HashMap<String, KIEBase>();

    public void addKIEBase(KIEBase kieBase){
        kieBaseHashMap.put(kieBase.getKieBaseName(),kieBase);
    }

    public HashMap<String, KIEBase> getKieBaseHashMap() {
        return kieBaseHashMap;
    }

}
