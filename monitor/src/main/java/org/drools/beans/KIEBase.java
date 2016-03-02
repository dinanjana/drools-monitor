package org.drools.beans;

import org.drools.core.management.KnowledgeBaseMonitoring;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

import java.util.HashMap;

/**
 * Created by Dinanjana on 27/02/2016.
 */
public class KIEBase {
    private String kieBaseName;
    private KieBase kieBase;
    private HashMap<String,KieSession> kieSessionHashMap = new HashMap<String, KieSession>();

    public void addKieSession(KieSession kieSession){
        kieSessionHashMap.put(String.valueOf(kieSession.getIdentifier()),kieSession);
    }

    public String getKieBaseName() {
        return kieBaseName;
    }

    public void setKieBaseName(String kieBaseName) {
        this.kieBaseName = kieBaseName;
    }

    public KieBase getKieBase() {
        return kieBase;
    }

    public void setKieBase(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    public HashMap<String, KieSession> getKieSessionHashMap() {
        return kieSessionHashMap;
    }

    public void setKieSessionHashMap(HashMap<String, KieSession> kieSessionHashMap) {
        this.kieSessionHashMap = kieSessionHashMap;
    }
}
