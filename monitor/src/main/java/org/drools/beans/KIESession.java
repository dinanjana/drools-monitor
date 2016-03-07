package org.drools.beans;

import org.kie.api.management.KieSessionMonitoringMBean;

import java.util.HashSet;

/**
 * Created by dinanjanag
 * on 3/2/2016.
 */
public class KIESession {
    private KieSessionMonitoringMBean kieSessionMonitoringMBean;
    private HashSet<String> ruleSet = new HashSet<String>();

    public KieSessionMonitoringMBean getKieSessionMonitoringMBean() {
        return kieSessionMonitoringMBean;
    }

    public void setKieSessionMonitoringMBean(KieSessionMonitoringMBean kieSessionMonitoringMBean) {
        this.kieSessionMonitoringMBean = kieSessionMonitoringMBean;
    }

    public void addRule(String rule){
        ruleSet.add(rule);
    }
}
