package org.drools.beans;

import org.kie.api.management.KieSessionMonitoringMBean;

import javax.management.DynamicMBean;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by dinanjanag
 * on 3/1/2016.
 */
public class MBeanNamesRegister {
    private static HashMap<String,KIESession> kieSessionMap = new HashMap<String, KIESession>();
    private static HashSet<String> kieBaseMap = new HashSet<String>();


    public void addSession(String name,KIESession kieSession){
        kieSessionMap.put(name,kieSession);
    }

    public void addBase(String key){
        kieBaseMap.add(key);
    }

    public static HashMap<String, KIESession> getKieSessionMap() {
        return kieSessionMap;
    }

    public static HashSet<String> getKieBaseMap() {
        return kieBaseMap;
    }
}
